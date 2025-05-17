package org.exchange.bitgit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.bitget.openapi.dto.request.ws.SubscribeReq;
import com.bitget.openapi.dto.request.ws.WsBaseReq;
import com.bitget.openapi.ws.BitgetWsClient;
import com.bitget.openapi.ws.BitgetWsHandle;
import com.bitget.openapi.ws.BitgetWsListener;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.exchange.bitgit.entity.JsonBean;
import org.exchange.bitgit.entity.TickerData;
import org.exchange.constant.KlineEnum;
import org.exchange.constant.TopicConstant;
import org.exchange.model.Kline;
import org.exchange.model.Symbol;
import org.redisson.api.RedissonClient;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.exchange.utils.BigDecimalUtils.parseBigDecimal;

@Component
public class BitgitWebSocketHandle {

    public static final String PUSH_URL = "wss://ws.bitget.com/spot/v1/stream";
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BitgitWebSocketHandle.class);

    private static List<com.bitget.openapi.dto.request.ws.SubscribeReq> argList = new ArrayList<>(20);

    static BitgetWsClient wsClient = null;

    @Resource
    RedissonClient redissonClient;

    public static String TICKER = "ticker";

    public static String KLINE = "candle";

    public void handle(Symbol symbol) {
        if (wsClient == null) {
            wsClient = BitgetWsHandle.builder()
                    .pushUrl(PUSH_URL)
                    .isLogin(false)
                    .listener(response -> {
                        // log.info("接收到的数据是：{}", response);
                        JsonBean jsonBean = JSON.parseObject(response, new TypeReference<JsonBean>() {
                        });
                        if (jsonBean.getArg().getChannel().equalsIgnoreCase(TICKER)) {
                            if (StringUtils.isNotBlank(jsonBean.getData())) {
                                List<TickerData> tickerDataList = JSON.parseArray(jsonBean.getData(), TickerData.class);
                                for (TickerData tickerData : tickerDataList) {
                                    org.exchange.model.Ticker ticker1 = new org.exchange.model.Ticker();
                                    ticker1.setSymbol(tickerData.getInstId());
                                    ticker1.setLastPrice(parseBigDecimal(tickerData.getLast()));
                                    ticker1.setPriceChange(parseBigDecimal(tickerData.getChgUTC()));
                                    ticker1.setPriceChangePercent(parseBigDecimal(tickerData.getPriceChangePercent()));
                                    ticker1.setOpen(parseBigDecimal(tickerData.getOpenUtc()));
                                    ticker1.setClose(parseBigDecimal(tickerData.getLast()));
                                    ticker1.setVolume(parseBigDecimal(tickerData.getBaseVolume()));
                                    ticker1.setHighPrice(parseBigDecimal(tickerData.getHigh24h()));
                                    ticker1.setLowPrice(parseBigDecimal(tickerData.getLow24h()));
                                    ticker1.setBaseVolume(parseBigDecimal(tickerData.getBaseVolume()));
                                    ticker1.setQuoteVolume(parseBigDecimal(tickerData.getQuoteVolume()));
                                    ticker1.setIndexPrice(parseBigDecimal(tickerData.getIndexPrice()));

                                    redissonClient.getTopic(TopicConstant.TICKER).publish(ticker1);

                                }
                            }
                        } else if (jsonBean.getArg().getChannel().contains(KLINE)) { // k线
                            if (StringUtils.isBlank(jsonBean.getData())) {
                                return;
                            }
                            JSONArray array = JSON.parseArray(jsonBean.getData());
                            for (Object o : array) {
                                JSONArray array1 = JSON.parseArray(o.toString());
                                if (CollectionUtils.isNotEmpty(array1)) {

                                    String channel = jsonBean.getArg().getChannel();
                                    String interval = null;
                                    for (KlineEnum klineEnum : KlineEnum.values()) {
                                        if (klineEnum.getBitgitInterval().equalsIgnoreCase(channel)) {
                                            interval = klineEnum.getInterval();
                                        }
                                    }
                                    String instId = jsonBean.getArg().getInstId();
                                    Kline kline = new Kline();
                                    kline.setSymbol(instId);
                                    kline.setInterval(interval);
                                    kline.setTimestamp(array1.getLong(0));
                                    kline.setOpen(array1.getBigDecimal(1));
                                    kline.setHigh(array1.getBigDecimal(2));
                                    kline.setLow(array1.getBigDecimal(3));
                                    kline.setClose(array1.getBigDecimal(4));
                                    kline.setVolume(array1.getBigDecimal(5));
                                    redissonClient.getTopic(TopicConstant.kLINE).publish(kline);
                                }
                            }
                        }

                    }).errorListener(error -> {
                        log.error("连接错误: {}", error);
                    }).build();
        }

        if (!argList.isEmpty()) {
            com.bitget.openapi.dto.request.ws.WsBaseReq<com.bitget.openapi.dto.request.ws.SubscribeReq> wsBaseReq = new WsBaseReq<>();
            wsBaseReq.setOp("unsubscribe");
            wsBaseReq.setArgs(argList);
            wsClient.sendMessage(wsBaseReq);
        }

        SubscribeReq subscribeReq = new SubscribeReq();
        subscribeReq.setChannel("ticker");
        subscribeReq.setInstId(symbol.getFollowName());
        subscribeReq.setInstType("MC");
        wsClient.subscribe(List.of(subscribeReq));

        for (KlineEnum klineEnum : KlineEnum.values()) {
            subscribeReq.setChannel(klineEnum.getBitgitInterval());
            wsClient.subscribe(List.of(subscribeReq));
        }
    }

}
