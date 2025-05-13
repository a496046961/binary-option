package org.exchange.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("t_user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;                    // 用户唯一标识，主键，自增
    private String username;            // 用户名，用于显示，例如“john_doe”
    private String email;               // 用户邮箱，唯一，用于登录

    private String password;            // 用户密码（哈希存储，例如使用BCrypt）
    private String status;              // 账户状态：ACTIVE, SUSPENDED, PENDING
    private LocalDateTime createdAt;    // 记录创建时间
    private LocalDateTime updatedAt;    // 记录最后更新时间

    /**
     * 上级邀请人的id
     */
    private long inviterId;

    /**
     * 邀请码
     */
    private String invitationCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getInviterId() {
        return inviterId;
    }

    public void setInviterId(long inviterId) {
        this.inviterId = inviterId;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public User() {
    }

}