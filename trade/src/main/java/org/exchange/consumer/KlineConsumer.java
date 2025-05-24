package org.exchange.consumer;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.exchange.constant.TopicConstant;
import org.exchange.model.Kline;
import org.exchange.model.MqttMsg;
import org.exchange.service.EmqxService;
import org.exchange.service.KlineService;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class KlineConsumer implements InitializingBean {

    private static final Logger log = LogManager.getLogger(KlineConsumer.class);

    @Resource
    RedissonClient redissonClient;
    @Resource
    EmqxService emqxService;

    @Resource
    KlineService klineService;

    @Resource
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

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

                emqxService.send(new MqttMsg(TopicConstant.kLINE, s));

                klineService.saveKline(msg); // 存储数据
            }
        });
    }
}
