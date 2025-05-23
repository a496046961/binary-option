package org.exchange.controller.request;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    private String username;
    private String password;
    private String code;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LoginRequest() {

    }

}
