package com.changhong.system.web.facade.dto;

import com.changhong.common.utils.CHStringUtils;

import java.io.Serializable;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:37
 */
public class UserDTO implements Serializable {
    private String uuid;
    private String xingMing;
    private String contactWay;

    private String username;
    private String password;
    private boolean enabled;

    public UserDTO() {
        this.password = CHStringUtils.getRandomString(10);
    }

    public UserDTO(String uuid, String xingMing, String contactWay, String username, String password, boolean enabled) {
        this.uuid = uuid;
        this.xingMing = xingMing;
        this.contactWay = contactWay;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
