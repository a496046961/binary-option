package org.exchange.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包实体类
 */
@TableName("t_wallet")
public class Wallet implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 币种名称
     */
    private String coinName;

    /**
     * 可用余额
     */
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * 冻结余额
     */
    private BigDecimal freezeBalance = BigDecimal.ZERO;

    /**
     * 锁仓余额
     */
    private BigDecimal lockBalance = BigDecimal.ZERO;

    /**
     * 版本号(用于乐观锁)
     */
    @Version
    private Integer version;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    @TableField(update = "now()")
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(BigDecimal freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public BigDecimal getLockBalance() {
        return lockBalance;
    }

    public void setLockBalance(BigDecimal lockBalance) {
        this.lockBalance = lockBalance;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
