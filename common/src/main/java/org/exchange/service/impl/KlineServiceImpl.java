package org.exchange.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.burukeyou.dataframe.iframe.SDFrame;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.exchange.constant.RedisKeyConstant;
import org.exchange.mapper.KlineMapper;
import org.exchange.model.Kline;
import org.exchange.model.KlineQueryRequest;
import org.exchange.service.KlineService;
import org.redisson.api.RMap;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@DS("slave_1")
public class KlineServiceImpl extends ServiceImpl<KlineMapper, Kline> implements KlineService {
    private static final Logger log = LoggerFactory.getLogger(KlineServiceImpl.class);

    private static final String PREFIX = "kline_";

    @Resource
    RedissonClient redissonClient;

    @Resource
    JdbcTemplate jdbcTemplate;


    public List<Kline> queryKlineData(KlineQueryRequest request) {
        if (request == null || request.getSymbol() == null) {
            throw new IllegalArgumentException("Symbol 不能为空");
        }

        if (!request.getSymbol().matches("^[A-Za-z0-9]+$")) {
            throw new IllegalArgumentException("Symbol 包含非法字符");
        }
        if (StringUtils.isBlank(request.getInterval())) {
            return null;
        }

        String tableName = PREFIX + request.getSymbol().toUpperCase();

        // 检查表是否存在
        if (!isTableExists(tableName)) {
            return new ArrayList<>(); // 表不存在，返回空列表
        }

        // 构建 SQL 查询
        StringBuilder sql = new StringBuilder(String.format("SELECT * FROM `binary-option-kline`.%s WHERE `interval` = ?", tableName));
        List<Object> params = new ArrayList<>();
        params.add(request.getInterval());

        // 添加时间范围过滤
        if (request.getStartTime() != null) {
            sql.append(" AND `timestamp` >= ?");
            params.add(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            sql.append(" AND `timestamp` <= ?");
            params.add(request.getEndTime());
        }

        // 添加排序和分页
        sql.append(" ORDER BY `timestamp` DESC");
        int page = Math.max(1, request.getPage());
        int size = Math.max(1, request.getSize());
        int offset = (page - 1) * size;
        sql.append(" LIMIT ?,?");
        params.add(offset);
        params.add(size);


        log.info("执行的k线：{}", sql.toString());
        log.info("参数：{}", params.toString());
        // 执行查询
        List<Kline> klineList = jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(Kline.class));
        log.info("size:{}", klineList.size());
        SDFrame<Kline> klines = SDFrame.read(klineList);

        String key = PREFIX.concat(request.getInterval()).concat(":").concat(request.getSymbol());
        RMap<Long, Kline> map = redissonClient.getMap(key);

        List<Kline> klines1 = map.values().stream().toList();
        klines = klines.union(klines1).distinct(Kline::getTimestamp);

        return klines.toLists();
    }

    @Override
    public void saveKline(Kline kline) {
        String key = PREFIX.concat(kline.getInterval()).concat(":").concat(kline.getSymbol());
        RMap<Long, Kline> map = redissonClient.getMap(key);

        map.put(kline.getTimestamp(), kline);

        if (map.size()>2) {
            for (Long l : map.keySet()) {
                if (l.equals(kline.getTimestamp())) {
                    continue;
                }
                Kline kline1 = map.get(l);
                this.saveOrUpdateKlineData(kline1);
                map.remove(l);
            }
        }
    }


    @Override
    public void create(String tableName) {
        try {
            String sql = String.format(
                    "CREATE TABLE `binary-option-kline`.%s (" +
                            "    id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                            "    `symbol` VARCHAR(50) NOT NULL," +
                            "    `timestamp` BIGINT NOT NULL," +
                            "    `close` DECIMAL(22,8) NOT NULL," +
                            "    `high` DECIMAL(22,8) NOT NULL," +
                            "    `interval` VARCHAR(50) NOT NULL," +
                            "    `low` DECIMAL(22,8) NOT NULL," +
                            "    `open` DECIMAL(22,8) NOT NULL," +
                            "    `volume` DECIMAL(22,8) NOT NULL," +
                            "     `amount` DECIMAL(22,8) ," +
                            "    INDEX idx_timestamp (`timestamp`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4",
                    tableName
            );
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            log.error("创建表异常：", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int saveOrUpdateKlineData(Kline kline) {
        try {
            if (kline == null || kline.getSymbol() == null || ObjectUtil.isEmpty(kline.getTimestamp())) {
                throw new IllegalArgumentException("Kline、symbol 或 timestamp 不能为空");
            }

            // 校验 symbol 格式
            if (!kline.getSymbol().matches("^[A-Za-z0-9]+$")) {
                throw new IllegalArgumentException("Symbol 包含非法字符");
            }

            String tableName = "kline_" + kline.getSymbol().toUpperCase();

            // 检查表是否存在
            if (!isTableExists(tableName)) {
                create(tableName);
            }

            // 检查记录是否存在
            String checkSql = String.format("SELECT COUNT(*) FROM `binary-option-kline`.%s WHERE `interval` = ? AND `timestamp` = ?", tableName);
            Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, kline.getInterval(), kline.getTimestamp());
            if (count != null && count > 0) {
                // 记录存在，更新
                String updateSql = String.format(
                        "UPDATE `binary-option-kline`.%s SET close = ?, high = ?, low = ?, `open` = ?, volume = ? ,`amount` = ?" +
                                " WHERE `interval` = ? AND `timestamp` = ?",
                        tableName
                );
                jdbcTemplate.update(updateSql,
                        kline.getClose(),
                        kline.getHigh(),
                        kline.getLow(),
                        kline.getOpen(),
                        kline.getVolume(),
                        kline.getAmount(),
                        kline.getInterval(),
                        kline.getTimestamp()
                );
            } else {
                // 记录不存在，插入
                String insertSql = String.format(
                        "INSERT INTO `binary-option-kline`.%s (close, high, `interval`, low, `open`, symbol, `timestamp`, volume,`amount`) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)",
                        tableName
                );
                jdbcTemplate.update(insertSql,
                        kline.getClose(),
                        kline.getHigh(),
                        kline.getInterval(),
                        kline.getLow(),
                        kline.getOpen(),
                        kline.getSymbol().toUpperCase(),
                        kline.getTimestamp(),
                        kline.getVolume(),
                        kline.getAmount()
                );
            }
            return 1;
        } catch (Exception e) {
            log.error("异常：", e);
        }
        return 0;
    }

    private boolean isTableExists(String tableName) {
        RMap<Object, Object> map = redissonClient.getMap(RedisKeyConstant.TABLE_NAME);
        if (map.containsKey(tableName)) {
            return true;
        }

        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, tableName);

        if (count != null && count > 0) {
            map.put(tableName, tableName);
            return true;
        }
        return false;
    }
}
