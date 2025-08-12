package com.example.springwebflux20250811001.service;

import com.example.springwebflux20250811001.entity.TestXY;
import com.example.springwebflux20250811001.repository.TestXYRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class TestXYService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    TestXYRepository testXYRepository;

    @Resource
    ReactiveMongoTemplate reactiveMongoTemplate;

    /**
     * 传入的message变为TestXY类型，并保存到MongoDB中
     *
     * @param topic
     * @param message
     */
    public Mono<TestXY> saveMongoDB(String topic, MqttMessage message) {
        return Mono.fromCallable(
                () -> {
                    String payload = new String(message.getPayload());
                    System.out.println(payload);
                    TestXY testXY = objectMapper.readValue(payload, TestXY.class);
                    testXY.setTime(LocalDateTime.now());
                    return testXY;
                }
        ).flatMap(
                testXY -> {
                    String collectionName = "testXY_" + testXY.getTime().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                    return reactiveMongoTemplate.save(testXY, collectionName);
                }
        ).doOnNext(testXY -> {
            System.out.println(testXY.toString() + "保存成功" + topic);
        }).doOnError(throwable -> {
            System.out.println(throwable.getMessage() + "保存失败" + topic);
        });
    }

    public Flux<TestXY> getAllTestXY() {
        return testXYRepository.findAll();
    }

    public Flux<TestXY> getTestXYByDay(String time) {
        String collectionName = "testXY_" + time;
        return reactiveMongoTemplate.findAll(TestXY.class, collectionName);
    }

}
