package org.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.exchange.model.Kline;
import org.exchange.model.KlineQueryRequest;

import java.util.List;

public interface KlineService extends IService<Kline> {

    void create(String tableName);

    int saveOrUpdateKlineData(Kline kline);

    List<Kline> queryKlineData(KlineQueryRequest request);

    void saveKline(Kline kline);

}
