package org.exchange.consumer;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.exchange.constant.TopicConstant;
import org.exchange.model.Kline;
import org.exchange.model.MqttMsg;
import org.exchange.service.EmqxService;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class KlineConsumer implements InitializingBean {

    private static final Logger log = LogManager.getLogger(KlineConsumer.class);

    @Resource
    RedissonClient redissonClient;
    @Resource
    EmqxService emqxService;

    @Override
    public void afterPropertiesSet() {
        try {
            this.consumer();
        } catch (Exception e) {
            log.error("异常:", e);
        }
    }

    public void consumer() {
        redissonClient.getTopic(TopicConstant.kLINE).addListener(Kline.class, new MessageListener<Kline>() {
            @Override
            public void onMessage(CharSequence channel, Kline msg) {
                String s = JSON.toJSONString(msg);
                log.info("kline： {}", s);
                emqxService.send(new MqttMsg(TopicConstant.kLINE, s));
            }
        });
    }
}
