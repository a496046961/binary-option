package org.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import org.exchange.model.MessageResult;
import org.exchange.model.User;

public interface UserService extends IService<User> {

    /**
     * 账号注册
     */
    MessageResult register(String username, String password, String code, String invitationCode, HttpServletRequest request);

    /**
     * 邮箱注册
     */
    MessageResult registerEmail(String email, String password, String code, String invitationCode, HttpServletRequest request);

    /**
     * 根据邀请码查询
     */
    User getUserByInvitationCode(String invitationCode);

    /**
     * 登陆
     */
    MessageResult login(String username, String password, String code,HttpServletRequest request);

}
