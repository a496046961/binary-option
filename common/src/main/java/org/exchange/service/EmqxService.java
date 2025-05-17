package org.exchange.service;

import org.exchange.model.MqttMsg;

public interface EmqxService {

    void send(MqttMsg mqttMsg);

}
