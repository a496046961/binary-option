package org.exchange.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import org.exchange.constant.RedisKeyConstant;
import org.exchange.controller.request.NewsRequest;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class NewsTask {

    public static final String NEWS_URL = "https://www.binance.com/bapi/composite/v1/public/cms/flashContent/query?pageNo=1&pageSize=100&isTransform=true&lang=en";

    @Resource
    RedissonClient redissonClient;

    @Scheduled(initialDelay = 1, fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void loadNews() {
        String s = HttpUtil.get(NEWS_URL);

        JSONArray jsonArray = JSON.parseObject(s).getJSONObject("data").getJSONArray("contents");

        for (Object o : jsonArray) {
            JSONObject jsonObject = JSON.parseObject(o.toString());
            String title = jsonObject.getString("title");
            String body = jsonObject.getString("body");
            Long createTime = jsonObject.getLong("createTime");
            long id = jsonObject.getLong("id");

            Date date = new Date(createTime);
            String format = DateUtil.format(date, "yyyy-MM-dd HH:mm");

            NewsRequest newsRequest = new NewsRequest(title, body, format, id);

            RScoredSortedSet<NewsRequest> rScoredSortedSet = redissonClient.getScoredSortedSet(RedisKeyConstant.NEWS_CACHE);
            rScoredSortedSet.add(createTime, newsRequest);

            if (rScoredSortedSet.size() > 200) {
                rScoredSortedSet.removeRangeByRank(0, rScoredSortedSet.size() - 200 - 1);
            }
        }
    }

}
