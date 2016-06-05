package com.trvajjala.paho.publisher;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * This acts as a device and send data to the topic
 *
 * @author ThirupathiReddy V
 *
 */
@EnableScheduling
@Service
public class MessagePublisher {

    /** The logger instance */
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagePublisher.class);

    @Autowired
    MqttClient mqttClient;

    @Scheduled(fixedDelay = 2000)
    public void sendMessages() {

        try {
            final String topic = "topic/trvajjala"; // This is MQTT Topic
            final String content = "Temparature at my home is 32 degree celsius on this time ==>" + new Date();
            final int qos = 2;
            final MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            mqttClient.publish(topic, message);
            LOGGER.info("Publishing message to the broker on topic ");
        } catch (final MqttPersistenceException e) {
            LOGGER.error("Error while persisting the message ", e);
        } catch (final MqttException e) {
            LOGGER.error("Error while connecting the broker ", e);
        }
    }

    public static void test() throws MqttException {

        final String topic = "topic/trvajjala";
        String content = "Temparature at my home is 32 degree celsius";
        final int qos = 2;
        final String broker = "tcp://127.0.0.1:1883";
        final String clientId = "air-condition";
        final MemoryPersistence persistence = new MemoryPersistence();

        try {
            final MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            final MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            content += new Date();
            final MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch (final MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}
