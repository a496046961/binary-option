package org.exchange.service.impl;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.exchange.conf.MqttConf;
import org.exchange.model.MqttMsg;
import org.exchange.service.EmqxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Service
public class EmqxServiceImpl implements EmqxService {

    private static final Logger log = LoggerFactory.getLogger(EmqxServiceImpl.class);

    private static final String API_KEY = "ee26b0dd4af7e749";
    private static final String SECRET_KEY = "Wk9BzFL2rHPe4pzjf0pDBPLtZcDNQ2XfG6a6NWWuhaGB";

    @Override
    public void send(MqttMsg mqttMsg) {

        String authorization = Base64Encoder.encode(API_KEY.concat(":").concat(SECRET_KEY), Charset.forName("UTF-8"));

        HttpRequest request = HttpUtil.createPost("http://192.168.1.109:18083/api/v5/publish");

        request.header("authorization", "Basic ".concat(authorization) );
        request.header("content-type", "application/json");
        request.header("accept", "application/json");
        String jsonString = JSON.toJSONString(mqttMsg);
        request.body(jsonString);

        String s = request.execute().body();
    }
}
