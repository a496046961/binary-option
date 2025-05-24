package org.exchange.bitgit;


import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import okhttp3.WebSocket;
import org.exchange.constant.KlineEnum;
import org.exchange.model.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class InitBitgit {

    private static final Logger log = LoggerFactory.getLogger(InitBitgit.class);

    private static final String WS_OP_SUBSCRIBE = "subscribe";

    private static final String WS_OP_UNSUBSCRIBE = "unsubscribe";

    @Resource
    BitgitWebSocketHandle bitgitWebSocketHandle;


    public void initBitgit(Symbol symbol) {

        bitgitWebSocketHandle.handle(symbol);

    }


}
