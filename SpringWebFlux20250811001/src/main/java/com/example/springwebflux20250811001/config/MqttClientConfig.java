package com.example.springwebflux20250811001.config;

import com.example.springwebflux20250811001.entity.TestXY;
import com.example.springwebflux20250811001.service.MqttService;
import com.example.springwebflux20250811001.service.TaosService;
import com.example.springwebflux20250811001.service.TestXYService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MqttClientConfig {

    private final String brokerUrl = "tcp://127.0.0.1:1883";
    private final String clientId = "spring-webflux-client";
    private final String topic = "test";

    private MqttClient mqttClient;
    ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    TestXYService testXYService;

    @Resource
    TaosService taosService;

    public MqttClient createMqttClient() throws MqttException {
        mqttClient = new MqttClient(brokerUrl, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);  // 设置为 true，防止会话丢失
        options.setKeepAliveInterval(3);  // 设置心跳时间间隔为 3 秒
        options.setAutomaticReconnect(true);  // 启用自动重连

        // 设置 MQTT 客户端的回调
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost. Attempting to reconnect...");
                // 在连接丢失时可以执行一些操作，重新连接等
                try {
                    if (!mqttClient.isConnected()) {
                        mqttClient.connect(options);
                        System.out.println("Reconnected to the broker.");
                        mqttClient.subscribe(topic);
                    }
                } catch (MqttException e) {
                    System.out.println("Reconnection failed: " + e.getMessage());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    connectionLost(cause);
                }
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String payload = new String(message.getPayload());
                System.out.println(payload);
                taosService.saveTDengine(topic,message).subscribe();
//                testXYService.saveMongoDB(topic, message).subscribe();
//                String payload = new String(message.getPayload());
//                int qos = message.getQos();
//                boolean retained = message.isRetained();
//                int messageId = message.getId();
//                boolean isDuplicate = message.isDuplicate();
//
//                TestXY testXY = objectMapper.readerFor(TestXY.class).readValue(payload);
//
//                System.out.println("Received message:");
//                System.out.println("Topic: " + topic);
//                System.out.println("Message ID: " + messageId);
//                System.out.println("QoS: " + qos);
//                System.out.println("Retained: " + retained);
//                System.out.println("Duplicate: " + isDuplicate);
//                System.out.println("Payload: " + payload);
//                System.out.println("Client Connected: " + mqttClient.isConnected());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // 发送完成的回调
            }
        });

        mqttClient.connect(options);
        mqttClient.subscribe(topic);

        return mqttClient;
    }
}
