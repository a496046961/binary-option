package org.exchange.model;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class KlineQueryRequest implements Serializable {

    @NotNull(message = "symbol required fields cannot be empty")
    private String symbol; // 必选，交易对
    private Long startTime; // 可选，开始时间戳
    private Long endTime; // 可选，结束时间戳
    private Integer page = 1; // 可选，分页页码，默认第1页
    private Integer size = 200; // 可选，每页大小，默认20条
    private String interval;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}
