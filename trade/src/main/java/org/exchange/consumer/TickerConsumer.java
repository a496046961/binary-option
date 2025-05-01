package org.exchange.consumer;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import org.exchange.model.Ticker;
import org.exchange.constant.TopicConstant;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class TickerConsumer implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(TickerConsumer.class);

    @Resource
    RedissonClient redissonClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        consume();
    }

    public void consume() {
        log.info("加载ticker数据");
        redissonClient.getTopic(TopicConstant.TICKER).addListener(Ticker.class, new MessageListener<Ticker>() {
            @Override
            public void onMessage(CharSequence charSequence, Ticker ticker) {
                log.info("receive ticker: {}", JSON.toJSONString(ticker));
            }
        });
    }
}
