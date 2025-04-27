package org.exchange.conf;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.exchange.binance.InitBinance;
import org.exchange.model.Symbol;
import org.exchange.service.SymbolService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


import java.util.List;

import static org.exchange.constant.DataSource.BINANCE;


@Slf4j
@Component
public class InitConf implements InitializingBean {

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
