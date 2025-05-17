package org.exchange.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public List<SymbolVo> getSymbols(int type) {
        LambdaQueryWrapper<Symbol> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Symbol::getType, type);
        List<Symbol> symbolList = this.list(queryWrapper);

        List<SymbolVo> list = new ArrayList<>(symbolList.size());

        for (Symbol symbol : symbolList) {
            SymbolVo symbolVo = new SymbolVo();
            BeanUtils.copyProperties(symbol, symbolVo);

            String key = RedisKeyConstant.TICKER.concat(symbol.getSymbol().toUpperCase());
            RBucket<String> bucket = redissonClient.getBucket(key);
            String s = bucket.get();

            Ticker ticker = JSON.parseObject(s, Ticker.class);

            symbolVo.setOpen(ticker.getOpen());
            symbolVo.setClose(ticker.getClose());
            symbolVo.setVolume(ticker.getVolume());
            symbolVo.setLowPrice(ticker.getLowPrice());
            symbolVo.setHighPrice(ticker.getHighPrice());
            symbolVo.setPriceChange(ticker.getPriceChange());
            symbolVo.setPriceChangePercent(ticker.getPriceChangePercent());
            list.add(symbolVo);
        }

        return list;
    }
}
