package com.example.springwebflux20250811001.controller;


import com.example.springwebflux20250811001.entity.TestXY;
import com.example.springwebflux20250811001.entity.User;
import com.example.springwebflux20250811001.repository.TestXYRepository;
import com.example.springwebflux20250811001.service.TestXYService;
import com.example.springwebflux20250811001.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RestController
@RequestMapping("/test")
public class WebFluxController {


    @Resource
    private TestXYService testXYService;

    @Resource
    private UserService userService;

    @GetMapping("/testMono")
    public Mono<String> greeting() {
        return Mono.just("Hello World");
    }

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route().GET("/webFlux", request ->
                ok().bodyValue("Hello WebFlux")
        ).build();
    }

    @Bean
    public RouterFunction<ServerResponse> getAllTestXY() {
        return RouterFunctions.route()
                .GET("/allTestXY", request ->
                        ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(testXYService.getAllTestXY(), TestXY.class)
                )
                .build();
    }

    @GetMapping("/allTestXY")
    public Flux<TestXY> getAllTestXYFlux() {
        return testXYService.getAllTestXY();
    }

    @GetMapping("/allUsers")
    public Mono<User> getAllUsers(@Param("username") String username) {
        return userService.findByUsername(username);
    }

    @Bean
    public RouterFunction<ServerResponse> routeUser() {
        return RouterFunctions.route().GET("/users",request -> {
            String username = request.queryParam("username").orElse("");
            Mono<User> user = userService.findByUsername(username);
            return ok().body(user,User.class);
        }).build();
    }


}
