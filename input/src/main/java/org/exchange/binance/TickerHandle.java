package org.exchange.binance;

import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import org.exchange.model.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Ticker转化器
 */
@Component
public class TickerHandle {


    private static final Logger log = LoggerFactory.getLogger(TickerHandle.class);

    public void loadTicker(Symbol symbol, UMWebsocketClientImpl umWebsocketClient) {
        umWebsocketClient.symbolTicker(symbol.getFollowName() , (message) ->{
            log.info("接收的数据是：{}", message);
        });

    }

}
