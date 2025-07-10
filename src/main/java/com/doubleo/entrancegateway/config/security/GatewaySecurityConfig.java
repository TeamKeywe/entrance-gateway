package com.doubleo.entrancegateway.config.security;

import com.doubleo.entrancegateway.filter.JwtAuthenticationFilter;
import com.doubleo.entrancegateway.infra.config.route.GatewayPathProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class GatewaySecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final GatewayPathProperties paths;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeExchange(
                        exchange -> {
                            // public endpoints
                            paths.getPublicEndpoints()
                                    .forEach(
                                            route -> {
                                                route.getMethods()
                                                        .forEach(
                                                                method ->
                                                                        exchange.pathMatchers(
                                                                                        method,
                                                                                        route
                                                                                                .getPath())
                                                                                .permitAll());
                                            });

                            // protected endpoints
                            paths.getProtectedEndpoints()
                                    .forEach(
                                            route -> {
                                                route.getMethods()
                                                        .forEach(
                                                                method ->
                                                                        exchange.pathMatchers(
                                                                                        method,
                                                                                        route
                                                                                                .getPath())
                                                                                .authenticated());
                                            });

                            // catch-all
                            exchange.anyExchange().denyAll();
                        });

        return http.addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.addAllowedHeader("*");
        cfg.addAllowedMethod("*");
        cfg.setAllowCredentials(true);
        cfg.addAllowedOriginPattern("http://localhost:5173");
        cfg.addAllowedOriginPattern("http://localhost");
        cfg.addAllowedOriginPattern("http://enter.keywe.info");
        cfg.addAllowedOriginPattern("https://enter.keywe.info");
        cfg.addAllowedOriginPattern("http://www.enter.keywe.info");
        cfg.addAllowedOriginPattern("https://www.enter.keywe.info");
        cfg.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
