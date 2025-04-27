package org.exchange.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包资产变动日志实体类
 */

@TableName("t_wallet_log")
public class WalletLog implements Serializable {
    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联的钱包ID(对应t_wallet.id)
     */
    private Long walletId;

    /**
     * 币种名称
     */
    private String coinName;

    /**
     * 变动类型(充值/提现/转账/交易/系统调整等)
     */
    private String changeType;

    /**
     * 变动金额(正数表示增加，负数表示减少)
     */
    private BigDecimal changeAmount;

    /**
     * 变动前可用余额
     */
    private BigDecimal beforeBalance;

    /**
     * 变动后可用余额
     */
    private BigDecimal afterBalance;

    /**
     * 变动前冻结余额
     */
    private BigDecimal beforeFreeze;

    /**
     * 变动后冻结余额
     */
    private BigDecimal afterFreeze;

    /**
     * 变动前锁仓余额
     */
    private BigDecimal beforeLock;

    /**
     * 变动后锁仓余额
     */
    private BigDecimal afterLock;

    /**
     * 业务ID(订单号/交易号等)
     */
    private String businessId;

    /**
     * 备注说明
     */
    private String remark;

    /**
     * 操作人(用户ID或系统)
     */
    private String operator;

    /**
     * 操作IP地址
     */
    private String ipAddress;

    /**
     * 创建时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public BigDecimal getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(BigDecimal beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public BigDecimal getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(BigDecimal afterBalance) {
        this.afterBalance = afterBalance;
    }

    public BigDecimal getBeforeFreeze() {
        return beforeFreeze;
    }

    public void setBeforeFreeze(BigDecimal beforeFreeze) {
        this.beforeFreeze = beforeFreeze;
    }

    public BigDecimal getAfterFreeze() {
        return afterFreeze;
    }

    public void setAfterFreeze(BigDecimal afterFreeze) {
        this.afterFreeze = afterFreeze;
    }

    public BigDecimal getBeforeLock() {
        return beforeLock;
    }

    public void setBeforeLock(BigDecimal beforeLock) {
        this.beforeLock = beforeLock;
    }

    public BigDecimal getAfterLock() {
        return afterLock;
    }

    public void setAfterLock(BigDecimal afterLock) {
        this.afterLock = afterLock;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
