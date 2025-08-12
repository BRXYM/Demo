package com.example.springwebflux20250811001.service;

import com.example.springwebflux20250811001.config.MqttClientConfig;
import com.example.springwebflux20250811001.entity.TestXY;
import com.example.springwebflux20250811001.repository.TestXYRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
public class MqttService {

    @Autowired
    private MqttClientConfig mqttClientConfig;


    @PostConstruct
    public void init() throws MqttException {
        // 启动 MQTT 客户端并订阅消息
        mqttClientConfig.createMqttClient();
    }

}
