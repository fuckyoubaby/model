package com.changhong.common.domain;

import com.changhong.common.utils.UUIDUtils;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:12
 */
public class EntityBase implements Serializable {

    private String uuid;

    private Date timestamp;

    protected EntityBase() {
        this.uuid = UUIDUtils.generateUUID();
        this.timestamp = new Date();
    }

    public void updateTimeStamp() {
        this.timestamp = new Date();
    }

    /**
     * *******************************************************GETTER/SETTER****************************************
     */

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
