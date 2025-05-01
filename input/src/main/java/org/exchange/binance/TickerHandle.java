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
            log.info("接收的数据是：{}", message);
            Ticker ticker = JSON.parseObject(message, new TypeReference<org.exchange.binance.entity.Ticker>() {
            });
            org.exchange.model.Ticker ticker1 = new org.exchange.model.Ticker();
            ticker1.setSymbol(ticker.getS());
            ticker1.setLastPrice(parseBigDecimal(ticker.getC()));
            ticker1.setPriceChange(parseBigDecimal(ticker.getP()));
            ticker1.setPriceChangePercent(parseBigDecimal(ticker.getPPercent()));
            ticker1.setOpen(parseBigDecimal(ticker.getO()));
            ticker1.setClose(parseBigDecimal(ticker.getC()));
            ticker1.setVolume(parseBigDecimal(ticker.getV()));
            ticker1.setHighPrice(parseBigDecimal(ticker.getH()));
            ticker1.setLowPrice(parseBigDecimal(ticker.getL()));
            ticker1.setBaseVolume(parseBigDecimal(ticker.getV()));
            ticker1.setQuoteVolume(parseBigDecimal(ticker.getQ()));

            long l = redissonClient.getTopic(TopicConstant.TICKER).publish(ticker1);
            log.info("发送： {}", l);
        });
    }


}
