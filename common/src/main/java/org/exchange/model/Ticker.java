package org.exchange.model;

import java.math.BigDecimal;

public class Ticker {

    String symbol;                // 获取交易对

    BigDecimal lastPrice;        // 获取最新成交价格

    BigDecimal priceChange;      // 获取24小时价格变化

    BigDecimal priceChangePercent; // 获取24小时价格变化百分比

    BigDecimal highPrice;        // 获取24小时最高价

    BigDecimal lowPrice;         // 获取24小时最低价

    BigDecimal volume;           // 获取24小时成交量

    BigDecimal open;

    BigDecimal close;

    BigDecimal indexPrice;

    BigDecimal baseVolume;

    BigDecimal quoteVolume;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(BigDecimal priceChange) {
        this.priceChange = priceChange;
    }

    public BigDecimal getPriceChangePercent() {
        return priceChangePercent;
    }

    public void setPriceChangePercent(BigDecimal priceChangePercent) {
        this.priceChangePercent = priceChangePercent;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getIndexPrice() {
        return indexPrice;
    }

    public void setIndexPrice(BigDecimal indexPrice) {
        this.indexPrice = indexPrice;
    }

    public BigDecimal getBaseVolume() {
        return baseVolume;
    }

    public void setBaseVolume(BigDecimal baseVolume) {
        this.baseVolume = baseVolume;
    }

    public BigDecimal getQuoteVolume() {
        return quoteVolume;
    }

    public void setQuoteVolume(BigDecimal quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public Ticker (){}
}
