package org.exchange.model;

public record MqttMsg(String topic, String payload) {
}
