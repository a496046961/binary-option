package org.exchange.constant;


/**
 * k线的枚举
 */

public enum KlineEnum {

    m_1("1m", "candle1m", "1m", "kline_1_min"),
    m_5("5m", "candle5m", "5m", "kline_5_min"),
    m_15("15m", "candle15m", "15m", "kline_15_min"),
    m_30("30m", "candle30m", "30m", "kline_30_min"),
    h_1("1h", "candle1H", "1H", "kline_1_h"),
    d_1("1d", "candle1D", "1D", "kline_1_d"),
    w_1("1w", "candle1W", "1W", "kline_1_w"),
    ;

    private String interval;

    private String bitgitInterval;

    private String bitgitInterval_api;

    private String investingInterval;

    private String binanceInterval;

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getBitgitInterval() {
        return bitgitInterval;
    }

    public void setBitgitInterval(String bitgitInterval) {
        this.bitgitInterval = bitgitInterval;
    }

    public String getBinanceInterval() {
        return binanceInterval;
    }

    public void setBinanceInterval(String binanceInterval) {
        this.binanceInterval = binanceInterval;
    }

    public String getBitgitInterval_api() {
        return bitgitInterval_api;
    }

    public void setBitgitInterval_api(String bitgitInterval_api) {
        this.bitgitInterval_api = bitgitInterval_api;
    }

    KlineEnum(String binanceInterval, String bitgitInterval, String bitgitInterval_api, String interval) {
        this.binanceInterval = binanceInterval;
        this.bitgitInterval = bitgitInterval;
        this.bitgitInterval_api = bitgitInterval_api;
        this.interval = interval;
    }
}
