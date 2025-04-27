package org.exchange.conf;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedissonConf {


    private static final Logger log = LoggerFactory.getLogger(RedissonConf.class);
    private String host;
    private Integer port;
    private String password;
    private Integer database = 0;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    @Bean
    public RedissonClient redissonClient() {
        log.info("init RedissonClient host:{}", getHost());
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + getHost() + ":" + getPort())
                .setPassword(getPassword())
                .setDatabase(getDatabase())
                .setTcpKeepAliveCount(50)
                .setTimeout(5000)
                .setConnectTimeout(5000);
        return Redisson.create(config);
    }

}
