package org.exchange.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("t_trade_order")
public class TradeOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;                    // 交易订单唯一标识，主键，自增
    private Long userId;                // 用户ID，关联user表
    private Long walletId;              // 钱包ID，关联wallet表
    private String asset;               // 交易资产，例如“BTC/USD”
    private String optionType;          // 期权类型：CALL, PUT
    private BigDecimal amount;          // 交易金额，例如100.00
    private BigDecimal payout;          // 潜在回报金额，例如180.00
    private LocalDateTime expiryTime;   // 交易到期时间
    private String status;              // 交易状态：OPEN, CLOSED, EXPIRED
    private String result;              // 交易结果：WIN, LOSS, DRAW
    private LocalDateTime createdAt;    // 记录创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPayout() {
        return payout;
    }

    public void setPayout(BigDecimal payout) {
        this.payout = payout;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}