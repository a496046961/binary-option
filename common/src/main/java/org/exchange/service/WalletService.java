package org.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.exchange.model.Wallet;

import java.math.BigDecimal;

public interface WalletService extends IService<Wallet> {

    /**
     * 查询，带 for update
     *
     * @param userId
     * @param coinName
     * @return
     */
    Wallet getWalletForUpdate(Long userId, String coinName);

    /**
     * 查询
     * @param userId
     * @param coinName
     * @return
     */
    Wallet getWallet(Long userId, String coinName);

    /**
     * 初始化
     * @param userId
     * @param coinName
     */
    void initWallet(Long userId, String coinName);

    /**
     * 新增可用
     * @param wallet
     * @param amount
     * @param remark
     * @return
     */
    boolean addBalance(Wallet wallet, BigDecimal amount, String remark);

    /**
     * 减少可用
     * @param wallet
     * @param amount
     * @param remark
     * @return
     */
    boolean subtractBalance(Wallet wallet, BigDecimal amount, String remark);

    /**
     * 增加冻结
     * @param wallet
     * @param amount
     * @param remark
     * @return
     */
    boolean addFreezeBalance(Wallet wallet, BigDecimal amount, String remark);

    /**
     * 减少冻结
     * @param wallet
     * @param amount
     * @param remark
     * @return
     */
    boolean subtractFreezeBalance(Wallet wallet, BigDecimal amount, String remark);

    /**
     * 增加可用，减少冻结
     * @param wallet
     * @param amount
     * @param remark
     * @return
     */
    boolean addBalanceSubtractFreezeBalance(Wallet wallet, BigDecimal amount, String remark);

    /**
     * 减少可用，增加冻结
     * @param wallet
     * @param amount
     * @param remark
     * @return
     */
    boolean subtractBalanceAddFreezeBalance(Wallet wallet, BigDecimal amount, String remark);
}
