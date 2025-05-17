package org.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.exchange.model.Symbol;
import org.exchange.vo.SymbolVo;

import java.util.List;

public interface SymbolService extends IService<Symbol> {

    List<SymbolVo> getSymbols(int type);

}
