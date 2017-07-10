package com.changhong.system.web.facade.dto;

import java.io.Serializable;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 上午9:12
 */
public class PasswordDTO implements Serializable {
    private String userUuid;
    private String xingMing;
    private String username;
    private String oldPassword;
    private String newPassword;
    private String newPasswordAgain;

    public PasswordDTO() {
    }

    public PasswordDTO(String userUuid, String xingMing, String username) {
        this.userUuid = userUuid;
        this.xingMing = xingMing;
        this.username = username;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }

    public void setNewPasswordAgain(String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }
}

