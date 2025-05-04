package org.exchange.controller;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.exchange.model.MessageResult;
import org.exchange.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    @Resource
    private UserService userService;

    /**
     * @param username 账号
     * @param password 密码
     * @param code     验证码
     * @return MessageResult
     */
    @PostMapping("account")
    public MessageResult register(String username, String password, String code, String invitationCode, HttpServletRequest request) {

       return userService.register(username, password, code, invitationCode, request);
    }

    @PostMapping("email")
    public MessageResult registerEmail(String email, String password, String code, String invitationCode, HttpServletRequest request){
        return userService.registerEmail(email, password, code, invitationCode, request);
    }
}
