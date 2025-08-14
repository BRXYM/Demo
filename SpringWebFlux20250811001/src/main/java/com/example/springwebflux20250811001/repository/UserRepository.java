package com.example.springwebflux20250811001.repository;

import com.example.springwebflux20250811001.entity.User;
//import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

//    @Query("select * from users where name=:name")
//    public Mono<User> findByUsername(String name);
}
