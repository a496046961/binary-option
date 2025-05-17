package org.exchange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.exchange.model.Kline;

@Mapper
public interface KlineMapper extends BaseMapper<Kline> {
}
