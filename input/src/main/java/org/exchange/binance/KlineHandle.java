package org.exchange.binance;

import com.alibaba.fastjson.JSON;
import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import com.binance.connector.futures.client.utils.WebSocketCallback;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.exchange.binance.entity.BinanceKline;
import org.exchange.constant.KlineEnum;
import org.exchange.constant.TopicConstant;
import org.exchange.model.Kline;
import org.exchange.model.Symbol;
import org.redisson.Redisson;
import org.springframework.stereotype.Component;

import static org.exchange.utils.BigDecimalUtils.parseBigDecimal;

@Component
public class KlineHandle {

    @Resource
    Redisson redisson;

    public void loadKline(Symbol symbol, UMWebsocketClientImpl umWebsocketClient) {
        for (KlineEnum value : KlineEnum.values()) {
            umWebsocketClient.klineStream(symbol.getFollowName(), value.getBinanceInterval(), message -> {
                String k = JSON.parseObject(message).getString("k");

                if (StringUtils.isNotBlank(k)) {
                    BinanceKline binanceKline = JSON.parseObject(k, BinanceKline.class);
                    Kline kline = new Kline();
                    kline.setTimestamp(binanceKline.getBeginTime());
                    kline.setOpen(parseBigDecimal(binanceKline.getO()));
                    kline.setClose(parseBigDecimal(binanceKline.getC()));
                    kline.setHigh(parseBigDecimal(binanceKline.getH()));
                    kline.setLow(parseBigDecimal(binanceKline.getL()));
                    kline.setVolume(parseBigDecimal(binanceKline.getV()));
                    kline.setAmount(parseBigDecimal(binanceKline.getQ()));
                    kline.setInterval(binanceKline.getI());
                    kline.setSymbol(binanceKline.getS());
                    redisson.getTopic(TopicConstant.kLINE).publish(kline);
                }
            });
        }

    }

}
