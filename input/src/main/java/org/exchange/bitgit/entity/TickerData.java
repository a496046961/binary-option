package org.exchange.bitgit.entity;

public class TickerData {
    private String instId;
    private String last;
    private String bestAsk;
    private String bestBid;
    private String high24h;
    private String low24h;
    private String priceChangePercent;
    private String capitalRate;
    private long nextSettleTime;
    private long systemTime;
    private String markPrice;
    private String indexPrice;
    private String holding;
    private String baseVolume;
    private String quoteVolume;
    private String openUtc;
    private String chgUTC;
    private int symbolType;
    private String symbolId;
    private String deliveryPrice;
    private String bidSz;
    private String askSz;

    // Getters and Setters
    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getBestAsk() {
        return bestAsk;
    }

    public void setBestAsk(String bestAsk) {
        this.bestAsk = bestAsk;
    }

    public String getBestBid() {
        return bestBid;
    }

    public void setBestBid(String bestBid) {
        this.bestBid = bestBid;
    }

    public String getHigh24h() {
        return high24h;
    }

    public void setHigh24h(String high24h) {
        this.high24h = high24h;
    }

    public String getLow24h() {
        return low24h;
    }

    public void setLow24h(String low24h) {
        this.low24h = low24h;
    }

    public String getPriceChangePercent() {
        return priceChangePercent;
    }

    public void setPriceChangePercent(String priceChangePercent) {
        this.priceChangePercent = priceChangePercent;
    }

    public String getCapitalRate() {
        return capitalRate;
    }

    public void setCapitalRate(String capitalRate) {
        this.capitalRate = capitalRate;
    }

    public long getNextSettleTime() {
        return nextSettleTime;
    }

    public void setNextSettleTime(long nextSettleTime) {
        this.nextSettleTime = nextSettleTime;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public String getMarkPrice() {
        return markPrice;
    }

    public void setMarkPrice(String markPrice) {
        this.markPrice = markPrice;
    }

    public String getIndexPrice() {
        return indexPrice;
    }

    public void setIndexPrice(String indexPrice) {
        this.indexPrice = indexPrice;
    }

    public String getHolding() {
        return holding;
    }

    public void setHolding(String holding) {
        this.holding = holding;
    }

    public String getBaseVolume() {
        return baseVolume;
    }

    public void setBaseVolume(String baseVolume) {
        this.baseVolume = baseVolume;
    }

    public String getQuoteVolume() {
        return quoteVolume;
    }

    public void setQuoteVolume(String quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public String getOpenUtc() {
        return openUtc;
    }

    public void setOpenUtc(String openUtc) {
        this.openUtc = openUtc;
    }

    public String getChgUTC() {
        return chgUTC;
    }

    public void setChgUTC(String chgUTC) {
        this.chgUTC = chgUTC;
    }

    public int getSymbolType() {
        return symbolType;
    }

    public void setSymbolType(int symbolType) {
        this.symbolType = symbolType;
    }

    public String getSymbolId() {
        return symbolId;
    }

    public void setSymbolId(String symbolId) {
        this.symbolId = symbolId;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getBidSz() {
        return bidSz;
    }

    public void setBidSz(String bidSz) {
        this.bidSz = bidSz;
    }

    public String getAskSz() {
        return askSz;
    }

    public void setAskSz(String askSz) {
        this.askSz = askSz;
    }

    @Override
    public String toString() {
        return "TickerData{" +
                "instId='" + instId + '\'' +
                ", last='" + last + '\'' +
                ", bestAsk='" + bestAsk + '\'' +
                ", bestBid='" + bestBid + '\'' +
                ", high24h='" + high24h + '\'' +
                ", low24h='" + low24h + '\'' +
                ", priceChangePercent='" + priceChangePercent + '\'' +
                ", capitalRate='" + capitalRate + '\'' +
                ", nextSettleTime=" + nextSettleTime +
                ", systemTime=" + systemTime +
                ", markPrice='" + markPrice + '\'' +
                ", indexPrice='" + indexPrice + '\'' +
                ", holding='" + holding + '\'' +
                ", baseVolume='" + baseVolume + '\'' +
                ", quoteVolume='" + quoteVolume + '\'' +
                ", openUtc='" + openUtc + '\'' +
                ", chgUTC='" + chgUTC + '\'' +
                ", symbolType=" + symbolType +
                ", symbolId='" + symbolId + '\'' +
                ", deliveryPrice='" + deliveryPrice + '\'' +
                ", bidSz='" + bidSz + '\'' +
                ", askSz='" + askSz + '\'' +
                '}';
    }
}
