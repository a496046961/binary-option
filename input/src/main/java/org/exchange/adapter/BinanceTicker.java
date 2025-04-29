package org.exchange.adapter;

import java.math.BigDecimal;

import static org.exchange.utils.BigDecimalUtils.parseBigDecimal;

public class BinanceTicker implements Ticker {
    private org.exchange.binance.entity.Ticker ticker;

    public BinanceTicker(org.exchange.binance.entity.Ticker ticker) {
        this.ticker = ticker;
    }

    @Override
    public String getSymbol() {
        return ticker.getS();
    }

    @Override
    public BigDecimal getLastPrice() {
        return parseBigDecimal(ticker.getC());
    }

    @Override
    public BigDecimal getPriceChange() {
        return parseBigDecimal(ticker.getP());
    }

    @Override
    public BigDecimal getPriceChangePercent() {
        return parseBigDecimal(ticker.getPPercent());
    }

    @Override
    public BigDecimal getHighPrice() {
        return parseBigDecimal(ticker.getH());
    }

    @Override
    public BigDecimal getLowPrice() {
        return parseBigDecimal(ticker.getL());
    }

    @Override
    public BigDecimal getVolume() {
        return parseBigDecimal(ticker.getV());
    }

    @Override
    public BigDecimal getOpen() {
        return parseBigDecimal(ticker.getO());
    }

    @Override
    public BigDecimal getClose() {
        return parseBigDecimal(ticker.getC());
    }

    @Override
    public BigDecimal getIndexPrice() {
        return parseBigDecimal("0");
    }

    @Override
    public BigDecimal getBaseVolume() {
        return parseBigDecimal(ticker.getV());
    }

    @Override
    public BigDecimal getquoteVolume() {
        return parseBigDecimal(ticker.getQ());
    }


}
