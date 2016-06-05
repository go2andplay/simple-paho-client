package com.trvajjala.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQTTConfig {

    @Bean
    public MqttClient mqttClient() throws MqttException {
        // final String topic = "topic/trvajjala";
        // final String content = "Temparature at my home is 32 degree celsius";
        // final int qos = 2;
        //
        final String broker = "tcp://127.0.0.1:1883";
        final String clientId = "air-condition";
        final MemoryPersistence persistence = new MemoryPersistence();
        final MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
        final MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        sampleClient.connect(connOpts);

        return sampleClient;
    }

}
