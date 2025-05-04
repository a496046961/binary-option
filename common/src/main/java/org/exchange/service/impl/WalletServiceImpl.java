package org.exchange.service.impl;

import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.exchange.constant.ChangeTypeEnum;
import org.exchange.mapper.WalletMapper;
import org.exchange.model.Wallet;
import org.exchange.service.WalletLogService;
import org.exchange.service.WalletService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements WalletService {


    @Resource
    private WalletLogService walletLogService;

    @Override
    public Wallet getWalletForUpdate(Long userId, String coinName) {
        LambdaQueryWrapper<Wallet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Wallet::getUserId, userId);
        queryWrapper.eq(Wallet::getCoinName, coinName);
        queryWrapper.last(" for update ");
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Wallet getWallet(Long userId, String coinName) {
        LambdaQueryWrapper<Wallet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Wallet::getUserId, userId);
        queryWrapper.eq(Wallet::getCoinName, coinName);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void initWallet(Long userId, String coinName) {
        Date now = new Date();
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setCoinName(coinName);
        wallet.setCreateDate(now);
        wallet.setUpdateDate(now);
        baseMapper.insert(wallet);
    }

    @Override
    @Lock4j(keys = {"#wallet.id"}, expire = 5000, acquireTimeout = 5000)
    public boolean addBalance(Wallet wallet, BigDecimal amount, String remark) {
        boolean b = this.lambdaUpdate().set(Wallet::getBalance, wallet.getBalance().add(amount))
                .set(Wallet::getVersion, wallet.getVersion() + 1)
                .eq(Wallet::getUserId, wallet.getUserId()).eq(Wallet::getId, wallet.getId())
                .eq(Wallet::getVersion, wallet.getVersion())
                .update();

        Wallet newWallet = new Wallet();
        BeanUtils.copyProperties(wallet, newWallet);
        newWallet.setBalance(wallet.getBalance().add(amount));

        walletLogService.recordWalletChange(wallet, newWallet, ChangeTypeEnum.ADD_BALANCE.name(), "",
                null, "", remark);

        return b;
    }

    @Override
    @Lock4j(keys = {"#wallet.id"}, expire = 5000, acquireTimeout = 5000)
    public boolean subtractBalance(Wallet wallet, BigDecimal amount, String remark) {
        boolean b = this.lambdaUpdate().set(Wallet::getBalance, wallet.getBalance().subtract(amount))
                .set(Wallet::getVersion, wallet.getVersion() + 1)
                .eq(Wallet::getUserId, wallet.getUserId())
                .eq(Wallet::getId, wallet.getId())
                .eq(Wallet::getVersion, wallet.getVersion())
                .last("balance - " + amount.stripTrailingZeros().toPlainString() +
                        " >=0")
                .update();

        Wallet newWallet = new Wallet();
        BeanUtils.copyProperties(wallet, newWallet);
        newWallet.setBalance(wallet.getBalance().subtract(amount));

        walletLogService.recordWalletChange(wallet, newWallet, ChangeTypeEnum.SUBTRACT_BALANCE.name(), "",
                null, "", remark);

        return b;
    }

    @Lock4j(keys = {"#wallet.id"}, expire = 5000, acquireTimeout = 5000)
    public boolean addFreezeBalance(Wallet wallet, BigDecimal amount, String remark) {
        boolean b = this.lambdaUpdate().set(Wallet::getFreezeBalance, wallet.getFreezeBalance().add(amount))
                .set(Wallet::getVersion, wallet.getVersion() + 1)
                .eq(Wallet::getUserId, wallet.getUserId()).eq(Wallet::getId, wallet.getId())
                .eq(Wallet::getVersion, wallet.getVersion())
                .update();

        Wallet newWallet = new Wallet();
        BeanUtils.copyProperties(wallet, newWallet);
        newWallet.setFreezeBalance(wallet.getFreezeBalance().add(amount));

        walletLogService.recordWalletChange(wallet, newWallet, ChangeTypeEnum.ADD_FREEZE_BALANCE.name(), "",
                null, "", remark);

        return b;
    }

    @Override
    @Lock4j(keys = {"#wallet.id"}, expire = 5000, acquireTimeout = 5000)
    public boolean subtractFreezeBalance(Wallet wallet, BigDecimal amount, String remark) {
        boolean b = this.lambdaUpdate().set(Wallet::getFreezeBalance, wallet.getFreezeBalance().subtract(amount))
                .set(Wallet::getVersion, wallet.getVersion() + 1)
                .eq(Wallet::getUserId, wallet.getUserId()).eq(Wallet::getId, wallet.getId())
                .eq(Wallet::getVersion, wallet.getVersion())
                .update();

        Wallet newWallet = new Wallet();
        BeanUtils.copyProperties(wallet, newWallet);
        newWallet.setFreezeBalance(wallet.getFreezeBalance().subtract(amount));

        walletLogService.recordWalletChange(wallet, newWallet, ChangeTypeEnum.SUBTRACT_FREEZE_BALANCE.name(), "",
                null, "", remark);

        return b;
    }

    @Override
    @Lock4j(keys = {"#wallet.id"}, expire = 5000, acquireTimeout = 5000)
    public boolean addBalanceSubtractFreezeBalance(Wallet wallet, BigDecimal amount, String remark) {
        boolean b = this.lambdaUpdate().set(Wallet::getBalance, wallet.getBalance().add(amount))
                .set(Wallet::getFreezeBalance, wallet.getFreezeBalance().subtract(amount))
                .set(Wallet::getVersion, wallet.getVersion() + 1)
                .eq(Wallet::getUserId, wallet.getUserId()).eq(Wallet::getId, wallet.getId())
                .eq(Wallet::getVersion, wallet.getVersion())
                .update();

        Wallet newWallet = new Wallet();
        BeanUtils.copyProperties(wallet, newWallet);
        newWallet.setFreezeBalance(wallet.getFreezeBalance().subtract(amount));
        newWallet.setBalance(wallet.getBalance().add(amount));

        walletLogService.recordWalletChange(wallet, newWallet, ChangeTypeEnum.ADD_BALANCE_SUBTRACT_FREEZE_BALANCE.name(), "",
                null, "", remark);

        return b;
    }

    @Override
    @Lock4j(keys = {"#wallet.id"}, expire = 5000, acquireTimeout = 5000)
    public boolean subtractBalanceAddFreezeBalance(Wallet wallet, BigDecimal amount, String remark) {
        boolean b = this.lambdaUpdate().set(Wallet::getBalance, wallet.getBalance().subtract(amount))
                .set(Wallet::getFreezeBalance, wallet.getFreezeBalance().add(amount))
                .set(Wallet::getVersion, wallet.getVersion() + 1)
                .eq(Wallet::getUserId, wallet.getUserId()).eq(Wallet::getId, wallet.getId())
                .eq(Wallet::getVersion, wallet.getVersion())
                .update();

        Wallet newWallet = new Wallet();
        BeanUtils.copyProperties(wallet, newWallet);
        newWallet.setFreezeBalance(wallet.getFreezeBalance().add(amount));
        newWallet.setBalance(wallet.getBalance().subtract(amount));

        walletLogService.recordWalletChange(wallet, newWallet, ChangeTypeEnum.ADD_BALANCE_SUBTRACT_FREEZE_BALANCE.name(), "",
                null, "", remark);

        return b;
    }
}
