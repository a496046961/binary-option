package org.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.exchange.mapper.SymbolMapper;
import org.exchange.model.Symbol;
import org.exchange.service.SymbolService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SymbolServiceImpl extends ServiceImpl<SymbolMapper, Symbol> implements SymbolService {
}
