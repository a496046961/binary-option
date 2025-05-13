package org.exchange.controller.request;

import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;


public class RegisterEmailRequest implements Serializable {

    private String email;
    private String password;
    private String code;
    private String invitationCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public RegisterEmailRequest() {
    }
}
