package org.exchange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.exchange.model.Symbol;

@Mapper
public interface SymbolMapper extends BaseMapper<Symbol> {
}
