package com.trvajjala.paho.subscriber;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSubScriber implements MqttCallback, InitializingBean {

    @Autowired
    MqttClient mqttClient;

    @Autowired
    private SimpMessagingTemplate template;

    public MessageSubScriber() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mqttClient.setCallback(this);
        mqttClient.subscribe("topic/trvajjala");
    }

    // @Scheduled(fixedDelay = 1000)
    // public void receiveMessage() throws MqttException {
    // mqttClient.subscribe("topic/trvajjala");
    // template.convertAndSend("/topic/web-subscriber", "Hello from WebSocket Message" + new Date());
    // }
    //

    @Override
    public void connectionLost(Throwable arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Topic name " + topic + " payload " + new String(message.getPayload()));
        template.convertAndSend("/topic/web-subscriber", "[ " + topic + " ]  ==>" + new String(message.getPayload()));
    }

}
