package org.exchange.controller;

import jakarta.annotation.Resource;
import org.exchange.model.KlineQueryRequest;
import org.exchange.model.MessageResult;
import org.exchange.service.KlineService;
import org.exchange.service.SymbolService;
import org.exchange.vo.SymbolVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("symbol")
public class SymbolController {

    @Resource
    SymbolService symbolService;

    @Resource
    KlineService klineService;

    @GetMapping("")
    public MessageResult getSymbol(int type) {
        List<SymbolVo> list = symbolService.getSymbols(type);
        return MessageResult.success(list);
    }

    @GetMapping("detail")
    public MessageResult getSymbolsDetail(String symbol) {
        return symbolService.getSymbolsDetail(symbol);
    }

    @GetMapping("history-kline")
    public MessageResult getKline( KlineQueryRequest request) {
        return MessageResult.success(klineService.queryKlineData(request));
    }

}
