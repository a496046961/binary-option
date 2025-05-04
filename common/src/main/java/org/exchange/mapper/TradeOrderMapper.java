package org.exchange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.exchange.model.TradeOrder;

@Mapper
public interface TradeOrderMapper extends BaseMapper<TradeOrder> {
}
