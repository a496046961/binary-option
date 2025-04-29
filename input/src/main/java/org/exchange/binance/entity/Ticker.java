package org.exchange.binance.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class Ticker {
    @JSONField(name = "e")
    private String e;  // 事件类型
    @JSONField(name = "E")
    private long tE;   // 事件时间
    private String s;  // 交易对
    private String p;  // 24小时价格变化
    private String P;  // 24小时价格变化(百分比)
    private String w;  // 平均价格
    private String c;  // 最新成交价格
    private String Q;  // 最新成交价格上的成交量
    private String o;  // 24小时内第一比成交的价格
    private String h;  // 24小时内最高成交价
    private String l;  // 24小时内最低成交价
    private String v;  // 24小时内成交量
    private String q;  // 24小时内成交额
    private long O;   // 统计开始时间

    private long F;   // 24小时内第一笔成交交易ID
    private long L;   // 24小时内最后一笔成交交易ID
    private long n;   // 24小时内成交数

    // Getters and Setters
    public String gete() {
        return e;
    }

    public void sete(String e) {
        this.e = e;
    }

    public long getTe() {
        return tE;
    }

    public void setTe(long E) {
        this.tE = E;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getPPercent() {
        return P;
    }

    public void setPPercent(String P) {
        this.P = P;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getQ() {
        return Q;
    }

    public void setQ(String Q) {
        this.Q = Q;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getQAmount() {
        return q;
    }

    public void setQAmount(String q) {
        this.q = q;
    }

    public long getOStartTime() {
        return O;
    }

    public void setOStartTime(long O) {
        this.O = O;
    }



    public long getFFirstTradeId() {
        return F;
    }

    public void setFFirstTradeId(long F) {
        this.F = F;
    }

    public long getLLastTradeId() {
        return L;
    }

    public void setLLastTradeId(long L) {
        this.L = L;
    }

    public long getNTradeCount() {
        return n;
    }

    public void setNTradeCount(long n) {
        this.n = n;
    }

    public Ticker(){}

}
