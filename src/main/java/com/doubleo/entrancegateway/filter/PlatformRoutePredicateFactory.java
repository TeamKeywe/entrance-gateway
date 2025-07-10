package com.doubleo.entrancegateway.filter;

import com.doubleo.entrancegateway.infra.config.jwt.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class PlatformRoutePredicateFactory
        extends AbstractRoutePredicateFactory<PlatformRoutePredicateFactory.Config> {

    private final JwtProperties jwtProperties;

    public PlatformRoutePredicateFactory(JwtProperties jwtProperties) {
        super(Config.class);
        this.jwtProperties = jwtProperties;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("platform");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {
            String authHeader =
                    exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) return false;

            try {
                String token = authHeader.substring(7);
                Claims claims =
                        Jwts.parserBuilder()
                                .setSigningKey(
                                        Keys.hmacShaKeyFor(
                                                jwtProperties
                                                        .accessTokenSecret()
                                                        .getBytes(StandardCharsets.UTF_8)))
                                .build()
                                .parseClaimsJws(token)
                                .getBody();

                String platform = claims.get("platform", String.class);
                return config.getPlatform().equalsIgnoreCase(platform);

            } catch (Exception e) {
                return false;
            }
        };
    }

    @Data
    public static class Config {
        private String platform;
    }
}
