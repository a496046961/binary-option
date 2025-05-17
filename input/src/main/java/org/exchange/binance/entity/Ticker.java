package org.exchange.binance.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Ticker {
    @JsonProperty("e")
    private  String eventType;

    @JsonProperty("E")
    private  long eventTime;

    @JsonProperty("s")
    private  String symbol;

    @JsonProperty("p")
    private  BigDecimal priceChange;

    @JsonProperty("P")
    private  BigDecimal priceChangePercent;

    @JsonProperty("w")
    private  BigDecimal weightedAveragePrice;

    @JsonProperty("c")
    private  BigDecimal lastPrice;

    @JsonProperty("Q")
    private  BigDecimal lastQuantity;

    @JsonProperty("o")
    private  BigDecimal openPrice;

    @JsonProperty("h")
    private  BigDecimal highPrice;

    @JsonProperty("l")
    private BigDecimal lowPrice;

    @JsonProperty("v")
    private  BigDecimal baseVolume;

    @JsonProperty("q")
    private  BigDecimal quoteVolume;

    @JsonProperty("O")
    private  long openTime;

    @JsonProperty("C")
    private  long closeTime;

    @JsonProperty("F")
    private  long firstTradeId;

    @JsonProperty("L")
    private  long lastTradeId;

    @JsonProperty("n")
    private  int numberOfTrades;

    public String getEventType() {
        return eventType;
    }

    public long getEventTime() {
        return eventTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPriceChange() {
        return priceChange;
    }

    public BigDecimal getPriceChangePercent() {
        return priceChangePercent;
    }

    public BigDecimal getWeightedAveragePrice() {
        return weightedAveragePrice;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public BigDecimal getLastQuantity() {
        return lastQuantity;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public BigDecimal getBaseVolume() {
        return baseVolume;
    }

    public BigDecimal getQuoteVolume() {
        return quoteVolume;
    }

    public long getOpenTime() {
        return openTime;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public long getFirstTradeId() {
        return firstTradeId;
    }

    public long getLastTradeId() {
        return lastTradeId;
    }

    public int getNumberOfTrades() {
        return numberOfTrades;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPriceChange(BigDecimal priceChange) {
        this.priceChange = priceChange;
    }

    public void setPriceChangePercent(BigDecimal priceChangePercent) {
        this.priceChangePercent = priceChangePercent;
    }

    public void setWeightedAveragePrice(BigDecimal weightedAveragePrice) {
        this.weightedAveragePrice = weightedAveragePrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public void setLastQuantity(BigDecimal lastQuantity) {
        this.lastQuantity = lastQuantity;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public void setBaseVolume(BigDecimal baseVolume) {
        this.baseVolume = baseVolume;
    }

    public void setQuoteVolume(BigDecimal quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public void setFirstTradeId(long firstTradeId) {
        this.firstTradeId = firstTradeId;
    }

    public void setLastTradeId(long lastTradeId) {
        this.lastTradeId = lastTradeId;
    }

    public void setNumberOfTrades(int numberOfTrades) {
        this.numberOfTrades = numberOfTrades;
    }

    public Ticker(){}

    public Ticker(
            @JsonProperty("e") String eventType,
            @JsonProperty("E") long eventTime,
            @JsonProperty("s") String symbol,
            @JsonProperty("p") BigDecimal priceChange,
            @JsonProperty("P") BigDecimal priceChangePercent,
            @JsonProperty("w") BigDecimal weightedAveragePrice,
            @JsonProperty("c") BigDecimal lastPrice,
            @JsonProperty("Q") BigDecimal lastQuantity,
            @JsonProperty("o") BigDecimal openPrice,
            @JsonProperty("h") BigDecimal highPrice,
            @JsonProperty("l") BigDecimal lowPrice,
            @JsonProperty("v") BigDecimal baseVolume,
            @JsonProperty("q") BigDecimal quoteVolume,
            @JsonProperty("O") long openTime,
            @JsonProperty("C") long closeTime,
            @JsonProperty("F") long firstTradeId,
            @JsonProperty("L") long lastTradeId,
            @JsonProperty("n") int numberOfTrades
    ) {
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.symbol = symbol;
        this.priceChange = priceChange;
        this.priceChangePercent = priceChangePercent;
        this.weightedAveragePrice = weightedAveragePrice;
        this.lastPrice = lastPrice;
        this.lastQuantity = lastQuantity;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.baseVolume = baseVolume;
        this.quoteVolume = quoteVolume;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.firstTradeId = firstTradeId;
        this.lastTradeId = lastTradeId;
        this.numberOfTrades = numberOfTrades;
    }

}
