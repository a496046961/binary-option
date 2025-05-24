package org.exchange.service.impl;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.exchange.model.MqttMsg;
import org.exchange.service.EmqxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
public class EmqxServiceImpl implements EmqxService {

    private static final Logger log = LoggerFactory.getLogger(EmqxServiceImpl.class);

    private static final String API_KEY = "ee26b0dd4af7e749";
    private static final String SECRET_KEY = "Wk9BzFL2rHPe4pzjf0pDBPLtZcDNQ2XfG6a6NWWuhaGB";

    private static final String HOST = "http://192.168.1.108:18083/api/v5/publish";

    private static CloseableHttpClient httpClient = null;

    @Resource
    ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @PostConstruct
    public void init() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(20);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.of(5, TimeUnit.SECONDS))
               // .setSocketTimeout(Timeout.of(5, TimeUnit.SECONDS))
                .setConnectionRequestTimeout(Timeout.of(5, TimeUnit.SECONDS))
                .build();

        httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .evictIdleConnections(TimeValue.of(60, TimeUnit.SECONDS))
                .setConnectionManagerShared(true)
                .build();
    }

    @Override
    public void send(MqttMsg mqttMsg) {
        threadPoolTaskExecutor.execute(() -> {
            try {
                String authorization = Base64.getEncoder()
                        .encodeToString((API_KEY + ":" + SECRET_KEY).getBytes(StandardCharsets.UTF_8));

                HttpPost httpPost = new HttpPost(HOST);
                httpPost.setHeader("Authorization", "Basic " + authorization);
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setHeader("Accept", "application/json");

                String jsonString = JSON.toJSONString(mqttMsg);
                httpPost.setEntity(new StringEntity(jsonString, StandardCharsets.UTF_8));

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    // 处理响应
                } catch (Exception e) {
                    log.error("异常：", e);
                }
            } catch (Exception e) {
                log.error("异常:", e);
            }
        });
    }
}
