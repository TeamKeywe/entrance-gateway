package com.doubleo.entrancegateway.config.route;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
    //
    //    @Bean
    //    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
    //        return builder.routes()
    //                .route("member-service", r -> r.path("/members/**")
    //                        .uri("lb://MEMBER-SERVICE"))
    //                .route("member-service", r -> r.path("/auth/**")
    //                            .uri("lb://MEMBER-SERVICE"))
    //                .route("did-service", r -> r.path("/dids/**")
    //                        .uri("lb://DID-SERVICE"))
    //                .route("admin-service", r -> r.path("/admins/**")
    //                        .uri("lb://ADMIN-SERVICE"))
    //                .route("hospital-service", r -> r.path("/hospitals/**")
    //                        .uri("lb://HOSPITAL-SERVICE"))
    //                .route("patient-service", r -> r.path("/patients/**")
    //                        .uri("lb://PATIENT-SERVICE"))
    //                .route("pass-service", r -> r.path("/passes/**")
    //                        .uri("lb://PASS-SERVICE"))
    //                //swagger routing
    //                .route("swagger_member", r -> r.path("/v3/api-docs/member")
    //                        .filters(f -> f.rewritePath("/v3/api-docs/member", "/v3/api-docs"))
    //                        .uri("lb://MEMBER-SERVICE"))
    //                .route("swagger_did", r -> r.path("/v3/api-docs/did")
    //                        .filters(f -> f.rewritePath("/v3/api-docs/did", "/v3/api-docs"))
    //                        .uri("lb://DID-SERVICE"))
    //                .route("swagger_admin", r -> r.path("/v3/api-docs/admin")
    //                        .filters(f -> f.rewritePath("/v3/api-docs/admin", "/v3/api-docs"))
    //                        .uri("lb://ADMIN-SERVICE"))
    //                .route("swagger_hospital", r -> r.path("/v3/api-docs/hospital")
    //                        .filters(f -> f.rewritePath("/v3/api-docs/hospital", "/v3/api-docs"))
    //                        .uri("lb://HOSPITAL-SERVICE"))
    //                .route("swagger_patient", r -> r.path("/v3/api-docs/log")
    //                        .filters(f -> f.rewritePath("/v3/api-docs/log", "/v3/api-docs"))
    //                        .uri("lb://PATIENT-SERVICE"))
    //                .route("swagger_pass", r -> r.path("/v3/api-docs/pass")
    //                        .filters(f -> f.rewritePath("/v3/api-docs/pass", "/v3/api-docs"))
    //                        .uri("lb://PASS-SERVICE"))
    //                .build();
    //    }
}
