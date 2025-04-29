package org.exchange.adapter;

import java.math.BigDecimal;

public interface Ticker {

    String getSymbol();                // 获取交易对

    BigDecimal getLastPrice();        // 获取最新成交价格

    BigDecimal getPriceChange();      // 获取24小时价格变化

    BigDecimal getPriceChangePercent(); // 获取24小时价格变化百分比

    BigDecimal getHighPrice();        // 获取24小时最高价

    BigDecimal getLowPrice();         // 获取24小时最低价

    BigDecimal getVolume();           // 获取24小时成交量

    BigDecimal getOpen();

    BigDecimal getClose();

    BigDecimal getIndexPrice();

    BigDecimal getBaseVolume();

    BigDecimal getquoteVolume();
}
