package org.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.exchange.mapper.WalletMapper;
import org.exchange.model.Wallet;
import org.exchange.service.WalletService;

@Slf4j
@Mapper
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements WalletService {
}
