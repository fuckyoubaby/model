package com.changhong.system.domain.box;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 16-12-5
 * Time: 上午11:23
 */
public class BoxLog extends EntityBase {

    private String mac;
    private String desc;

    public BoxLog() {
    }

    public BoxLog(String mac, String desc) {
        this.mac = mac;
        this.desc = desc;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
