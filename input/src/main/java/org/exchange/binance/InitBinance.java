package org.exchange.binance;


import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.exchange.model.Symbol;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitBinance {

    private volatile static UMWebsocketClientImpl umWebsocketClient = new UMWebsocketClientImpl();


    @Resource
    TickerHandle tickerHandle;
    @Resource
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void initBinance(Symbol symbol) {
        Runnable runnable = () -> {
            tickerHandle.loadTicker(symbol, umWebsocketClient);
        };
        threadPoolTaskExecutor.execute(runnable);
    }

}
