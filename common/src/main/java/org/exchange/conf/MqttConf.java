package org.exchange.conf;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.stereotype.Component;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Slf4j
@Component
public class MqttConf {

    @Value("${mqtt.host}")
    public String host;
    @Value("${mqtt.username}")
    public String username;
    @Value("${mqtt.password}")
    public String password;
    @Value("${mqtt.clientId}")
    public String clientId;
    @Value("${mqtt.timeout:30}")
    public int timeOut = 30;
    @Value("${mqtt.keepalive:30}")
    public int keepAlive = 30;

    @Value("${mqtt.clearSession:true}")
    public boolean clearSession = true;
    @Value("${mqtt.topic}")
    public String topic;

    // MQTT 客户端工厂
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{host});
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(clearSession);
        options.setConnectionTimeout(timeOut);
        options.setKeepAliveInterval(keepAlive);

        factory.setConnectionOptions(options);
        return factory;
    }

    // 出站通道（发布消息）
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId + "_outbound", mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(topic);
        messageHandler.setDefaultQos(1);
        return messageHandler;
    }

    // 出站通道
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

}
