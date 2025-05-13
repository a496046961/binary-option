package org.exchange.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.exchange.controller.request.LoginRequest;
import org.exchange.model.MessageResult;
import org.exchange.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Resource
    private UserService userService;


    @PostMapping("")
    public MessageResult login(@RequestBody  LoginRequest loginRequest, HttpServletRequest request) {
        return userService.login(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getCode(), request);
    }

}
