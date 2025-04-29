package org.exchange.conf;

import jakarta.annotation.Resource;
import org.exchange.binance.InitBinance;
import org.exchange.bitgit.InitBitgit;
import org.exchange.model.Symbol;
import org.springframework.stereotype.Component;

import static org.exchange.constant.DataSource.BINANCE;
import static org.exchange.constant.DataSource.BITGIT;
import static org.exchange.constant.DataSource.INVESTING;


@Component
public class LoadDataConf {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoadDataConf.class);

    @Resource
    InitBinance initBinance;
    @Resource
    InitBitgit initBitgit;

    public void load(Symbol symbol) {
        if (symbol.getDataSource().equalsIgnoreCase(BINANCE.name())) { // 币安的数据源
            //initBinance.initBinance(symbol);
        } else if (symbol.getDataSource().equalsIgnoreCase(BITGIT.name())) { // bitgit
            initBitgit.initBitgit(symbol);
        } else if (symbol.getDataSource().equalsIgnoreCase(INVESTING.name())) { // 英为

        } else {
            log.error("暂时不支持 ：{}", symbol.getSymbol());
        }
    }

}
