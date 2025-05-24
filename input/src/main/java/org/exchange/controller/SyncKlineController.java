package org.exchange.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.bitget.openapi.common.client.BitgetRestClient;
import com.bitget.openapi.common.domain.ClientParameter;
import com.bitget.openapi.common.enums.SupportedLocaleEnum;
import com.bitget.openapi.dto.response.ResponseResult;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.exchange.constant.DataSource;
import org.exchange.constant.KlineEnum;
import org.exchange.model.Kline;
import org.exchange.model.Symbol;
import org.exchange.service.KlineService;
import org.exchange.service.SymbolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("sync")
public class SyncKlineController {

    private static final Logger log = LoggerFactory.getLogger(SyncKlineController.class);

    @Resource
    SymbolService symbolService;

    @Resource
    KlineService klineService;

    @SaIgnore
    @PostMapping("syncKline")
    public void syncKline(@RequestParam("symbol") String symbol) {
        try {
            Optional<Symbol> optional = symbolService.findBySymbol(symbol);
            if (optional.isPresent()) {
                Symbol symbol1 = optional.get();
                Date now = new Date();
                for (KlineEnum klineEnum : KlineEnum.values()) {
                    if (symbol1.getDataSource().equalsIgnoreCase(DataSource.BINANCE.name())) { // 币安
                        getBinanceKline(symbol1.getFollowName(), klineEnum, now, 1500);
                    } else if (symbol1.getDataSource().equalsIgnoreCase(DataSource.BITGIT.name())) { // bitgit
                        getBitgitKline(symbol1.getFollowName(), klineEnum, now, 200);
                    } else if (symbol1.getDataSource().equalsIgnoreCase(DataSource.INVESTING.name())) { // 英为

                    }
                }
            }
        } catch (Exception e) {
            log.error("同步k线异常：", e);
        }
    }


    public void getBinanceKline(String symbol, KlineEnum klineEnum, Date now, Integer limit) {
        try {
            UMFuturesClientImpl client = new UMFuturesClientImpl();

            long startTime = now.getTime();
            long endTime = now.getTime();
            for (int i = 1; i < 10; i++) {
                endTime = startTime;

                DateTime dateTime = DateUtil.offsetMinute(now, i * limit * -1);
                startTime = dateTime.getTime();
                log.info("startTime is {}, endTime is {}", startTime, endTime);
                LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("pair", symbol);
                parameters.put("interval", klineEnum.getBinanceInterval());
                parameters.put("startTime", startTime);
                parameters.put("endTime", endTime);
                parameters.put("contractType", "PERPETUAL");
                parameters.put("limit", limit);

                String s = client.market().continuousKlines(parameters);
                if (StringUtils.isBlank(s)) {
                    return;
                }

                JSONArray array = JSON.parseArray(s);

                if (array.size() < 0) {
                    return;
                }

                for (Object o : array) {
                    JSONArray array1 = JSON.parseArray(o.toString());

                    Kline kline = new Kline();
                    kline.setSymbol(symbol);
                    kline.setInterval(klineEnum.getInterval());
                    kline.setTimestamp(array1.getLong(0));
                    kline.setOpen(array1.getBigDecimal(1));
                    kline.setHigh(array1.getBigDecimal(2));
                    kline.setLow(array1.getBigDecimal(3));
                    kline.setClose(array1.getBigDecimal(4));
                    kline.setVolume(array1.getBigDecimal(5));
                    kline.setAmount(array1.getBigDecimal(7));
                    klineService.saveOrUpdateKlineData(kline);
                }
            }
        } catch (Exception e) {
            log.error("同步币安k线异常:", e);
        }
    }

    public void getBitgitKline(String symbol, KlineEnum klineEnum , Date now, Integer limit) {
        try {
            long startTime = now.getTime();
            long endTime = now.getTime();
            for (int i = 1; i < 30; i++) {
                endTime = startTime;

                DateTime dateTime = DateUtil.offsetMinute(now, i * limit * -1);
                startTime = dateTime.getTime();

                String url = """
                        https://api.bitget.com/api/mix/v1/market/history-candles?symbol=%s&granularity=%s&limit=%s&startTime=%s&endTime=%s
                        """.formatted(
                        symbol.toUpperCase().concat("_UMCBL"),
                        klineEnum.getBitgitInterval_api(),
                        limit,
                        startTime,
                        endTime
                );

                HttpRequest request = HttpUtil.createGet(url);
                String body = request.execute().body();

                JSONArray array = JSON.parseArray(body);
                if (array.size() < 0) {
                    return;
                }
                for (Object o : array) {
                    JSONArray array1 = JSON.parseArray(o.toString());
                    Kline kline = new Kline();
                    kline.setSymbol(symbol);
                    kline.setInterval(klineEnum.getInterval());
                    kline.setTimestamp(array1.getLong(0));
                    kline.setOpen(array1.getBigDecimal(1));
                    kline.setHigh(array1.getBigDecimal(2));
                    kline.setLow(array1.getBigDecimal(3));
                    kline.setClose(array1.getBigDecimal(4));
                    kline.setVolume(array1.getBigDecimal(5));
                    kline.setAmount(array1.getBigDecimal(6));
                    klineService.saveOrUpdateKlineData(kline);
                }
            }
        } catch (Exception e) {
            log.error("同步bitgit k线异常:", e);
        }
    }


}
