package org.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.exchange.mapper.TradeOrderMapper;
import org.exchange.model.TradeOrder;
import org.exchange.service.TradeOrderService;
import org.springframework.stereotype.Service;

@Service
public class TradeOrderServiceImpl extends ServiceImpl<TradeOrderMapper, TradeOrder> implements TradeOrderService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TradeOrderServiceImpl.class);




}
