package com.doubleo.entrancegateway.infra.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String accessTokenSecret) {}
