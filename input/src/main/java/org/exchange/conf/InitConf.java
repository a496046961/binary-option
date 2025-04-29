package org.exchange.conf;

import jakarta.annotation.Resource;
import org.exchange.binance.InitBinance;
import org.exchange.model.Symbol;
import org.exchange.service.SymbolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


import java.util.List;

import static org.exchange.constant.DataSource.BINANCE;

@Component
public class InitConf implements InitializingBean {


    private static final Logger log = LoggerFactory.getLogger(InitConf.class);
    @Resource
    SymbolService symbolService;

    @Resource
    LoadDataConf loadDataConf;


    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            run();
        } catch (Exception e) {
            log.error("启动初始化异常：", e);
        }
    }

    public void run() throws Exception {
        List<Symbol> symbolList = symbolService.list();

        for (Symbol symbol : symbolList) {
            loadDataConf.load(symbol);
        }

    }

}
