package com.doubleo.entrancegateway.filter;

import com.doubleo.entrancegateway.infra.config.jwt.JwtProperties;
import com.doubleo.entrancegateway.infra.config.redis.BlackListTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import javax.crypto.SecretKey;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtProperties jwtProperties;
    private final BlackListTokenService blackListTokenService;

    @Override
    public @NonNull Mono<Void> filter(
            @NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);
        if (blackListTokenService.existsByToken(token)) {
            log.warn("Token is blacklisted: {}", token);
            return chain.filter(exchange);
        }

        try {
            SecretKey key =
                    Keys.hmacShaKeyFor(
                            jwtProperties.accessTokenSecret().getBytes(StandardCharsets.UTF_8));
            Claims claims =
                    Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

            String adminId = claims.getSubject();
            String tenantId = claims.get("tenantId", String.class);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(adminId, null, Collections.emptyList());

            ServerHttpRequest mutatedRequest =
                    exchange.getRequest()
                            .mutate()
                            .header("X-Admin-Id", adminId)
                            .header("X-Tenant-Id", tenantId)
                            .build();

            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

            return chain.filter(mutatedExchange)
                    .contextWrite(
                            ReactiveSecurityContextHolder.withSecurityContext(
                                    Mono.just(new SecurityContextImpl(auth))));

        } catch (JwtException e) {
            log.warn("JWT validation failed: {}", e.getMessage());
            return chain.filter(exchange);
        }
    }
}
