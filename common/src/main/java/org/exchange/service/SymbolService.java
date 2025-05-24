package org.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.exchange.model.MessageResult;
import org.exchange.model.Symbol;
import org.exchange.vo.SymbolVo;

import java.util.List;
import java.util.Optional;

public interface SymbolService extends IService<Symbol> {

    List<SymbolVo> getSymbols(int type);

    Optional<Symbol> findBySymbol(String symbol);

    MessageResult getSymbolsDetail(String symbol);
}
