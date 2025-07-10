package com.doubleo.entrancegateway.infra.config.route;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;

@Data
@ConfigurationProperties(prefix = "security.endpoints")
@RefreshScope
public class GatewayPathProperties {

    private List<Route> publicEndpoints;
    private List<Route> protectedEndpoints;

    @Data
    public static class Route {
        private String path;
        private List<HttpMethod> methods;
    }
}
