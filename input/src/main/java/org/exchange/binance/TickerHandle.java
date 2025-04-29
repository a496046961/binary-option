package org.exchange.binance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import jakarta.annotation.Resource;
import org.exchange.adapter.BinanceTicker;
import org.exchange.binance.entity.Ticker;
import org.exchange.constant.TopicConstant;
import org.exchange.model.Symbol;
import org.redisson.Redisson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Ticker转化器
 */
@Component
public class TickerHandle {
    @Resource
    Redisson redisson;

    private static final Logger log = LoggerFactory.getLogger(TickerHandle.class);

    public void loadTicker(Symbol symbol, UMWebsocketClientImpl umWebsocketClient) {
        umWebsocketClient.symbolTicker(symbol.getFollowName(), (message) -> {
            log.info("接收的数据是：{}", message);
            Ticker ticker = JSON.parseObject(message, new TypeReference<org.exchange.binance.entity.Ticker>() {
            });
            BinanceTicker binanceTicker = new BinanceTicker(ticker);
            redisson.getTopic(TopicConstant.TICKER).publish(binanceTicker);
        });
    }


}
