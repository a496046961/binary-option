package org.exchange.controller;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.exchange.controller.request.RegisterEmailRequest;
import org.exchange.controller.request.RegisterUserNameRequest;
import org.exchange.model.MessageResult;
import org.exchange.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * 账号注册
     */
    @PostMapping("account")
    public MessageResult register(@RequestBody RegisterUserNameRequest registerUserNameRequest,
                                  HttpServletRequest request) {
        return userService.register(registerUserNameRequest.getUsername(), registerUserNameRequest.getPassword(),
                registerUserNameRequest.getCode(), registerUserNameRequest.getInvitationCode(), request);
    }

    @PostMapping("email")
    public MessageResult registerEmail(@RequestBody RegisterEmailRequest registerEmailRequest, HttpServletRequest request) {
        return userService.registerEmail(registerEmailRequest.getEmail(), registerEmailRequest.getPassword(),
                registerEmailRequest.getCode(), registerEmailRequest.getInvitationCode(), request);
    }
}
