package org.exchange.binance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import jakarta.annotation.Resource;
import org.exchange.binance.entity.Ticker;
import org.exchange.constant.TopicConstant;
import org.exchange.model.Symbol;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.exchange.utils.BigDecimalUtils.parseBigDecimal;

/**
 * Ticker转化器
 */
@Component
public class TickerHandle {
    @Resource
    RedissonClient redissonClient;

    private static final Logger log = LoggerFactory.getLogger(TickerHandle.class);

    public void loadTicker(Symbol symbol, UMWebsocketClientImpl umWebsocketClient) {
        umWebsocketClient.symbolTicker(symbol.getFollowName(), (message) -> {
            Ticker ticker = JSON.parseObject(message, new TypeReference<org.exchange.binance.entity.Ticker>() {
            });
            org.exchange.model.Ticker ticker1 = new org.exchange.model.Ticker();
            ticker1.setSymbol(ticker.getSymbol());
            ticker1.setLastPrice(ticker.getLastPrice());
            ticker1.setPriceChange(ticker.getPriceChange());
            ticker1.setPriceChangePercent(ticker.getPriceChangePercent());
            ticker1.setOpen(ticker.getOpenPrice());
            ticker1.setClose(ticker.getLastPrice());
            ticker1.setVolume(ticker.getBaseVolume());
            ticker1.setHighPrice(ticker.getHighPrice());
            ticker1.setLowPrice(ticker.getLowPrice());
            ticker1.setBaseVolume(ticker.getBaseVolume());
            ticker1.setQuoteVolume(ticker.getQuoteVolume());

            redissonClient.getTopic(TopicConstant.TICKER).publish(ticker1);
        });
    }


}
