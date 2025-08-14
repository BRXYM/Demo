package com.example.springwebflux20250811001.service;


import com.example.springwebflux20250811001.entity.SpringStatus;
import com.example.springwebflux20250811001.entity.tdengine.TestXY;
import com.example.springwebflux20250811001.mapper.TaosStatusMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaosService {
    @Resource
    ObjectMapper objectMapper;

    @Resource
    TaosStatusMapper taosStatusMapper;

    private static final AtomicLong lastTs = new AtomicLong();

//    public Mono<TestXY> saveTDengine(String topic, MqttMessage message){
//        return Mono.fromCallable(()->{
//            String payload = new String(message.getPayload());
//            return objectMapper.readValue(payload,TestXY.class);
//        }).flatMap(testXY->{
//            if (!SpringStatus.TABLE_LIST.contains("testxy_"+testXY.getName())){
//                SpringStatus.TABLE_LIST.add(testXY.getName());
//                taosStatusMapper.createTable("testxy_"+testXY.getName(),"testxy", testXY.getName());
//            }
//            return Mono.just(testXY);
//        }).flatMap(testXY -> {
//            Instant now = Instant.now();
//            testXY.setTs(now.getEpochSecond() * 1_000_000 + now.getNano() / 1_000);
//            taosStatusMapper.saveToTable(testXY,"testxy_"+testXY.getName());
//            return Mono.just(testXY);
//        });
//    }

    public Mono<Void> saveTDengine(String topic, MqttMessage message) {
        return Mono.fromCallable(() -> {
                    String payload = new String(message.getPayload());
                    return objectMapper.readValue(payload, TestXY.class);
                })
                .flatMap(testXY -> {
                    if (!SpringStatus.TABLE_LIST.contains("testxy_" + testXY.getName())) {
                        SpringStatus.TABLE_LIST.add(testXY.getName());
                        return Mono.fromRunnable(() ->
                                        taosStatusMapper.createTable("testxy_" + testXY.getName(), "testxy", testXY.getName()))
                                .subscribeOn(Schedulers.boundedElastic())
                                .thenReturn(testXY);
                    }
                    return Mono.just(testXY);
                })
                .flatMap(testXY -> {
                    Instant now = Instant.now();
                    long us = now.getEpochSecond() * 1_000_000 + now.getNano() / 1_000;
                    testXY.setTs(lastTs.updateAndGet(prev -> Math.max(prev + 1, us)));
                    return Mono.fromRunnable(() ->
                                    taosStatusMapper.saveToTable(testXY, "testxy_" + testXY.getName()))
                            .subscribeOn(Schedulers.boundedElastic())
                            .then();
                })
                .doOnError(Throwable::printStackTrace);
    }

}
