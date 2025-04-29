package org.exchange.bitgit;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import jakarta.annotation.Resource;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.compressors.deflate64.Deflate64CompressorInputStream;
import org.apache.commons.lang3.StringUtils;
import org.exchange.adapter.BitgitTicker;
import org.exchange.adapter.Ticker;
import org.exchange.bitgit.entity.JsonBean;
import org.exchange.bitgit.entity.TickerData;
import org.exchange.constant.KlineEnum;
import org.exchange.constant.TopicConstant;
import org.exchange.model.Kline;
import org.jetbrains.annotations.NotNull;
import org.redisson.Redisson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class OkWebSocketHandle {

    private static final Logger log = LoggerFactory.getLogger(OkWebSocketHandle.class);
    public static String TICKER = "ticker";

    public static String KLINE = "candle";

    public static final String PUSH_URL = "wss://ws.bitget.com/mix/v1/stream";

    static WebSocket webSocket = null;

    @Resource
    Redisson redisson;

    public WebSocket initClient() {
        System.out.println("init Client");
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 7890)))
                .build();

        Request request = new Request.Builder()
                .url(PUSH_URL)
                .build();

        if (webSocket != null) {
            return webSocket;
        }
        webSocket = client.newWebSocket(request, new WebSocketListener() {

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {

            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                System.out.println("接收的数据是： " + text);
                try {
                    JsonBean jsonBean = JSON.parseObject(text, new TypeReference<JsonBean>() {
                    });
                    if (jsonBean.getArg().getChannel().equalsIgnoreCase(TICKER)) {
                        if (StringUtils.isNotBlank(jsonBean.getData())) {
                            List<TickerData> tickerDataList = JSON.parseArray(jsonBean.getData(), TickerData.class);
                            for (TickerData tickerData : tickerDataList) {
                                Ticker ticker = new BitgitTicker(tickerData);
                                redisson.getTopic(TopicConstant.TICKER).publish(ticker);
                            }
                        }
                    } else if (jsonBean.getArg().getChannel().contains(KLINE)) { // k线
                        JSONArray array = JSON.parseArray(jsonBean.getData());
                        if (CollectionUtils.isNotEmpty(array)) {
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
                            kline.setTimestamp(array.getLong(0));
                            kline.setOpen(array.getBigDecimal(1));
                            kline.setHigh(array.getBigDecimal(2));
                            kline.setLow(array.getBigDecimal(3));
                            kline.setClose(array.getBigDecimal(4));
                            kline.setVolume(array.getBigDecimal(5));
                            redisson.getTopic(TopicConstant.kLINE).publish(kline);
                        }
                    }
                } catch (Exception e) {
                    log.error("异常：", e);
                }
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                // super.onMessage(webSocket, bytes);
                String s = uncompress(bytes.toByteArray());
                System.out.println("bytes 接收的数据是 " + s);
            }
        });
        return webSocket;
    }

    // 解压函数
    private String uncompress(final byte[] bytes) {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
             final ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             final Deflate64CompressorInputStream zin = new Deflate64CompressorInputStream(in)) {

            final byte[] buffer = new byte[1024];
            int offset;
            while (-1 != (offset = zin.read(buffer))) {
                out.write(buffer, 0, offset);
            }
            return out.toString();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
