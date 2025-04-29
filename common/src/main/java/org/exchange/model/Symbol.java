package org.exchange.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;


@TableName("t_symbol")
public class Symbol implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 全名称
     */
    private String fullName;

    /**
     * 交易对
     */
    private String symbol;

    /**
     * 基础币名称
     */
    private String baseCoin;

    /**
     * 计价货币
     */
    private String quoteCoin;

    /**
     * 精度
     */
    private Integer scale;

    private String followName;

    /**
     * 跟随的数据
     */
    private String followInput;

    /**
     * 数据源
     */
    private String dataSource;

    /**
     * 类型，1:数字货币，2:期货，3:指数
     */
    private Integer type;

    /**
     * 回报率
     */
    private String returnRateConf;

    /**
     * 手续费
     */
    private BigDecimal fee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBaseCoin() {
        return baseCoin;
    }

    public void setBaseCoin(String baseCoin) {
        this.baseCoin = baseCoin;
    }

    public String getQuoteCoin() {
        return quoteCoin;
    }

    public void setQuoteCoin(String quoteCoin) {
        this.quoteCoin = quoteCoin;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getFollowInput() {
        return followInput;
    }

    public void setFollowInput(String followInput) {
        this.followInput = followInput;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReturnRateConf() {
        return returnRateConf;
    }

    public void setReturnRateConf(String returnRateConf) {
        this.returnRateConf = returnRateConf;
    }


    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Symbol() {
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", symbol='" + symbol + '\'' +
                ", baseCoin='" + baseCoin + '\'' +
                ", quoteCoin='" + quoteCoin + '\'' +
                ", scale=" + scale +
                ", followName='" + followName + '\'' +
                ", followInput='" + followInput + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", type=" + type +
                ", returnRateConf='" + returnRateConf + '\'' +
                '}';
    }
}
