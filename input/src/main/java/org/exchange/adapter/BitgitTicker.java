package org.exchange.adapter;

import org.exchange.bitgit.entity.TickerData;
import static org.exchange.utils.BigDecimalUtils.parseBigDecimal;

import java.math.BigDecimal;


public class BitgitTicker implements Ticker {

    private TickerData tickerData;

    public BitgitTicker(TickerData tickerData) {
        this.tickerData = tickerData;
    }

    @Override
    public String getSymbol() {
        return tickerData.getInstId();
    }

    @Override
    public BigDecimal getLastPrice() {
        return parseBigDecimal(tickerData.getLast());
    }

    @Override
    public BigDecimal getPriceChange() {
        return parseBigDecimal(tickerData.getPriceChangePercent());
    }

    @Override
    public BigDecimal getPriceChangePercent() {
        return null;
    }

    @Override
    public BigDecimal getHighPrice() {
        return null;
    }

    @Override
    public BigDecimal getLowPrice() {
        return null;
    }

    @Override
    public BigDecimal getVolume() {
        return null;
    }

    @Override
    public BigDecimal getOpen() {
        return null;
    }

    @Override
    public BigDecimal getClose() {
        return null;
    }

    @Override
    public BigDecimal getIndexPrice() {
        return null;
    }

    @Override
    public BigDecimal getBaseVolume() {
        return null;
    }

    @Override
    public BigDecimal getquoteVolume() {
        return null;
    }
}
