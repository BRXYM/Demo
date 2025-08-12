package com.example.springwebflux20250811001.repository;

import com.example.springwebflux20250811001.entity.TestXY;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TestXYRepository extends ReactiveMongoRepository<TestXY,String> {
}
