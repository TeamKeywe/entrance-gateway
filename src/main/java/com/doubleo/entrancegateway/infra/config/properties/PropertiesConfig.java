package com.doubleo.entrancegateway.infra.config.properties;

import com.doubleo.entrancegateway.infra.config.jwt.JwtProperties;
import com.doubleo.entrancegateway.infra.config.redis.RedisProperties;
import com.doubleo.entrancegateway.infra.config.route.GatewayPathProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
    JwtProperties.class,
    RedisProperties.class,
    GatewayPathProperties.class
})
@Configuration
public class PropertiesConfig {}
