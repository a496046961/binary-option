package org.exchange.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.generator.Generator;
import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.exchange.constant.RedisKeyConstant.CAPTCHA;

@RestController
@RequestMapping("captcha")
public class CaptchaController {

    @Resource
    RedissonClient redissonClient;

    private static final Logger log = LogManager.getLogger(CaptchaController.class);

    @GetMapping
    public void captcha(HttpServletRequest req, HttpServletResponse resp) {
        try {
            RandomGenerator randomGenerator = new RandomGenerator(RandomUtil.BASE_NUMBER, 4);
            ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 2);
            captcha.setGenerator(randomGenerator);

            RBucket<Object> bucket = redissonClient.getBucket(CAPTCHA.concat(req.getSession().getId()));
            bucket.set(captcha.getCode(), 5, TimeUnit.MINUTES);

            captcha.write(resp.getOutputStream());

        } catch (IOException e) {
            log.error("生成验证码图片异常:", e);
        }
    }

}
