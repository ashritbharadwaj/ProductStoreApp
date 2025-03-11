//package com.gateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RequestPredicates;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import java.net.URI;
//import java.time.LocalDateTime;
//
//@Configuration
//public class RouteConfig {
//
////    @Bean
////    public RouteLocator productAppRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
////        return routeLocatorBuilder.routes()
////                .route(p -> p
////                        .path("/employeems/**")
////                        .filters( f -> f.rewritePath("/employeems/(?<segment>.*)",
////                                        "/${segment}")
////                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
////                                .circuitBreaker(config -> config.setName("mycircuitbreaker")
////                                        .setFallbackUri("forward:/fallback"))
////                        )
////                        .uri("lb://EMPAPP"))
////                .route(p -> p
////                        .path("/departmentms/**")
////                        .filters( f -> f.rewritePath("/departmentms/(?<segment>.*)","/${segment}")
////                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
////                        )
////                        .uri("lb://DEPTAPP"))
////                .build();
////    }
//
//    @Bean
//    public RouterFunction<ServerResponse> productServiceRoute() {
//        return RouterFunctions.route(RequestPredicates.path("/api/products"),
//                request -> ServerResponse.permanentRedirect(URI.create("http://localhost:8084/api/products")).build());
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> orderServiceRoute() {
//        return RouterFunctions.route(RequestPredicates.path("/api/orders"),
//                request -> ServerResponse.permanentRedirect(URI.create("http://localhost:8086/api/orders")).build());
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> inventoryServiceRoute() {
//        return RouterFunctions.route(RequestPredicates.path("/api/inventories"),
//                request -> ServerResponse.permanentRedirect(URI.create("http://localhost:8085/api/inventories")).build());
//    }
//
//    // Uncomment the following code if you want to add a fallback route
//    // @Bean
//    // public RouterFunction<ServerResponse> fallbackRoute() {
//    //     return RouterFunctions.route(RequestPredicates.GET("/fallbackRoute"),
//    //             request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
//    //                     .bodyValue("Service Unavailable, please try again later"));
//    // }
//
//}
//
//
