package org.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.exchange.constant.RedisKeyConstant;
import org.exchange.mapper.SymbolMapper;
import org.exchange.model.Symbol;
import org.exchange.model.Ticker;
import org.exchange.service.SymbolService;
import org.exchange.vo.SymbolVo;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SymbolServiceImpl extends ServiceImpl<SymbolMapper, Symbol> implements SymbolService {

    @Resource
    RedissonClient redissonClient;

    @Override
    public List<SymbolVo> getSymbols() {
        List<Symbol> symbolList = this.list();

        List<SymbolVo> list = new ArrayList<>(symbolList.size());

        for (Symbol symbol : symbolList) {
            SymbolVo symbolVo = new SymbolVo();
            BeanUtils.copyProperties(symbol, symbolVo);

            String key = RedisKeyConstant.TICKER.concat(symbol.getSymbol().toUpperCase());
            RBucket<Ticker> bucket = redissonClient.getBucket(key);
            Ticker ticker = bucket.get();

            symbolVo.setOpen(ticker.getOpen());
            symbolVo.setClose(ticker.getClose());
            symbolVo.setVolume(ticker.getVolume());
            symbolVo.setLowPrice(ticker.getLowPrice());
            symbolVo.setHighPrice(ticker.getHighPrice());
            symbolVo.setPriceChange(ticker.getPriceChange());
            symbolVo.setPriceChange(ticker.getPriceChange());
            list.add(symbolVo);
        }

        return list;
    }
}
