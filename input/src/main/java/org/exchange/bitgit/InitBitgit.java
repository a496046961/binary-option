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

    private static List<SubscribeReq> argList = new ArrayList<>(20);

    public static volatile Boolean STATUS = true;

    @Resource
    OkWebSocketHandle okWebSocketHandle;
    @Resource
    BitgitWebSocketHandle bitgitWebSocketHandle;


    public void initBitgit(Symbol symbol) {

     if (symbol != null) {
            argList.add(new SubscribeReq("MC", "ticker", symbol.getFollowName()));
            for (KlineEnum klineEnum : KlineEnum.values()) {
                argList.add(new SubscribeReq("MC", klineEnum.getBitgitInterval(), symbol.getFollowName())); //k线
            }
        }
        bitgitWebSocketHandle.handle(symbol);
  /*
        WebSocket webSocket = okWebSocketHandle.initClient();

        WsBaseReq<SubscribeReq> req = new WsBaseReq<>();
        req.setOp(WS_OP_UNSUBSCRIBE);
        req.setArgs(argList);
        String s = JSON.toJSONString(req);
        webSocket.send(s);

        s = null;
        req = null;

        req = new WsBaseReq<>();
        req.setOp(WS_OP_SUBSCRIBE);
        req.setArgs(argList);
        s = JSON.toJSONString(req);
        webSocket.send(s);*/

    }

    @Scheduled(fixedRate = 5000)
    public void checkStatus() throws InterruptedException {
        if (!STATUS) {
            log.info("需要重启了……");
            this.initBitgit(null);
            Thread.sleep(300);
            STATUS = true;
        }
    }


}
