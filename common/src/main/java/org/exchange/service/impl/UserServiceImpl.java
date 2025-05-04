package org.exchange.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.exchange.constant.AccountEnum;
import org.exchange.constant.RedisKeyConstant;
import org.exchange.mapper.UserMapper;
import org.exchange.model.MessageResult;
import org.exchange.model.User;
import org.exchange.service.InvitationStatisticsService;
import org.exchange.service.UserService;
import org.exchange.service.WalletService;
import org.exchange.utils.IdUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    WalletService walletService;
    @Resource
    InvitationStatisticsService invitationStatisticsService;
    @Resource
    RedissonClient redissonClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageResult register(String username, String password, String code, String invitationCode, HttpServletRequest request) {
        try {
            if (this.lambdaQuery().eq(User::getUsername, username).exists()) {
                return MessageResult.error(" This account already exists ");
            }

            RBucket<Object> bucket = redissonClient.getBucket(RedisKeyConstant.CAPTCHA.concat(request.getSession().getId()));
            if (!bucket.get().toString().equalsIgnoreCase(code)) { // 验证码不对
                return MessageResult.error(" Verification code input error ");
            } else {
                bucket.delete();
            }

            User userByInvitationCode = this.getUserByInvitationCode(invitationCode);

            if (userByInvitationCode == null) {
                return MessageResult.error(" Invitation Code not exist ");
            }

            User user = new User();
            user.setId(IdUtils.nextId());
            user.setUsername(username);
            user.setPassword(password);
            user.setStatus(AccountEnum.ACTIVE.name());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setInviterId(userByInvitationCode.getInviterId());

            user.setInvitationCode(this.createInvitationCode());

            this.save(user);

            walletService.initWallet(user.getId(), "USDT"); // 初始化千百遍
            invitationStatisticsService.init(user.getId()); // 初始化邀请统计
            invitationStatisticsService.updateCount(userByInvitationCode.getId()); // 修改上一级的邀请数量

            StpUtil.login(user.getId());
            String tokenValue = StpUtil.getTokenValue();
            return MessageResult.success(tokenValue);
        } catch (Exception e) {
            log.error("注册异常：", e);
        }
        return MessageResult.error("system error");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageResult registerEmail(String email, String password, String code, String invitationCode, HttpServletRequest request) {
        try {
            if (this.lambdaQuery().eq(User::getEmail, email).exists()) {
                return MessageResult.error(" This account already exists ");
            }
            RBucket<Object> bucket = redissonClient.getBucket(RedisKeyConstant.CAPTCHA.concat(request.getSession().getId()));
            if (!bucket.get().toString().equalsIgnoreCase(code)) { // 验证码不对
                return MessageResult.error(" Verification code input error ");
            } else {
                bucket.delete();
            }

            User userByInvitationCode = this.getUserByInvitationCode(invitationCode);

            if (userByInvitationCode == null) {
                return MessageResult.error(" Invitation Code not exist ");
            }

            User user = new User();
            user.setId(IdUtils.nextId());
            user.setEmail(email);
            user.setPassword(password);
            user.setStatus(AccountEnum.ACTIVE.name());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setInviterId(userByInvitationCode.getInviterId());

            user.setInvitationCode(this.createInvitationCode());

            this.save(user);

            walletService.initWallet(user.getId(), "USDT"); // 初始化钱包
            invitationStatisticsService.init(user.getId()); // 初始化邀请统计
            invitationStatisticsService.updateCount(userByInvitationCode.getId()); // 修改上一级的邀请数量

            StpUtil.login(user.getId());
            String tokenValue = StpUtil.getTokenValue();
            return MessageResult.success(tokenValue);
        } catch (Exception e) {
            log.error("邮箱注册异常", e);
        }
        return MessageResult.error("system error");
    }

    @Override
    public User getUserByInvitationCode(String invitationCode) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getInvitationCode, invitationCode);

        return this.getOne(queryWrapper);
    }

    @Override
    public MessageResult login(String username, String password, String code, HttpServletRequest request) {
        try {
            RBucket<Object> bucket = redissonClient.getBucket(RedisKeyConstant.CAPTCHA.concat(request.getSession().getId()));
            if (!bucket.get().toString().equalsIgnoreCase(code)) { // 验证码不对
                return MessageResult.error(" Verification code input error ");
            } else {
                bucket.delete();
            }

            User user = this.lambdaQuery().eq(User::getUsername, username).or().eq(User::getEmail, username).one();
            if (user == null) {
                return MessageResult.error(" User not exist ");
            }
            StpUtil.login(user.getId());
            String tokenValue = StpUtil.getTokenValue();

            return MessageResult.success(tokenValue);
        } catch (Exception e) {
            log.error("登陆异常： ", e);
        }
        return MessageResult.error("system error");
    }

    public String createInvitationCode() {
        for (; ; ) {
            String s = RandomUtil.randomStringUpper(6);
            User userByInvitationCode = this.getUserByInvitationCode(s);
            if (userByInvitationCode == null) {
                return s;
            }
        }
    }

}
