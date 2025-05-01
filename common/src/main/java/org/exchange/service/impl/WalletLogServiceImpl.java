package org.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.exchange.mapper.WalletLogMapper;
import org.exchange.model.Wallet;
import org.exchange.model.WalletLog;
import org.exchange.service.WalletLogService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
public class WalletLogServiceImpl extends ServiceImpl<WalletLogMapper, WalletLog> implements WalletLogService {


    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordWalletChange(Wallet oldWallet, Wallet newWallet, String changeType, String businessId, String operator, String ipAddress, String remark) {
        Runnable r = () -> {
            // 1. 验证参数
            if (oldWallet == null || newWallet == null) {
                throw new IllegalArgumentException("钱包对象不能为null");
            }
            if (!oldWallet.getCoinName().equals(newWallet.getCoinName())) {
                throw new IllegalArgumentException("币种名称不一致");
            }

            // 2. 计算变动金额
            BigDecimal balanceChange = newWallet.getBalance().subtract(oldWallet.getBalance());
            BigDecimal freezeChange = newWallet.getFreezeBalance().subtract(oldWallet.getFreezeBalance());
            BigDecimal lockChange = newWallet.getLockBalance().subtract(oldWallet.getLockBalance());

            // 3. 只有实际发生变动时才记录日志
            if (balanceChange.compareTo(BigDecimal.ZERO) != 0 ||
                    freezeChange.compareTo(BigDecimal.ZERO) != 0 ||
                    lockChange.compareTo(BigDecimal.ZERO) != 0) {

                WalletLog walletLog = new WalletLog();
                walletLog.setWalletId(oldWallet.getId());
                walletLog.setCoinName(oldWallet.getCoinName());
                walletLog.setChangeType(changeType);

                // 设置变动金额(以余额变动为主)
                walletLog.setChangeAmount(balanceChange);

                // 设置前后值
                walletLog.setBeforeBalance(oldWallet.getBalance());
                walletLog.setAfterBalance(newWallet.getBalance());
                walletLog.setBeforeFreeze(oldWallet.getFreezeBalance());
                walletLog.setAfterFreeze(newWallet.getFreezeBalance());
                walletLog.setBeforeLock(oldWallet.getLockBalance());
                walletLog.setAfterLock(newWallet.getLockBalance());

                // 设置业务信息
                walletLog.setBusinessId(businessId);
                walletLog.setRemark(remark);
                walletLog.setOperator(operator);
                walletLog.setIpAddress(ipAddress);

                // 保存日志
                this.save(walletLog);
            }
        };
        threadPoolTaskExecutor.execute(r);
    }
}
