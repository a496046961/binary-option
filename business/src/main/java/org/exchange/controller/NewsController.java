package org.exchange.controller;


import jakarta.annotation.Resource;
import org.exchange.constant.RedisKeyConstant;
import org.exchange.controller.request.NewsRequest;
import org.exchange.model.MessageResult;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Comparator;

@RestController
@RequestMapping("news")
public class NewsController {

    @Resource
    RedissonClient redissonClient;


    @GetMapping("")
    public MessageResult getNews() {
        RScoredSortedSet<NewsRequest> rScoredSortedSet = redissonClient.getScoredSortedSet(RedisKeyConstant.NEWS_CACHE);
        Collection<NewsRequest> newsRequestsList = rScoredSortedSet.readAll();
        newsRequestsList.stream().sorted(Comparator.comparing(NewsRequest::getId));
        return MessageResult.success(newsRequestsList);
    }

}
