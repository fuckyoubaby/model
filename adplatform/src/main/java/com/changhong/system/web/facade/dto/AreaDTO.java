package com.changhong.system.web.facade.dto;

import java.io.Serializable;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 13:27
 */
public class AreaDTO implements Serializable {

    private String uuid;

    private String timestamp;

    private String name;

    private String parentUuid;

    public AreaDTO() {

    }

    public AreaDTO(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public AreaDTO(String uuid, String name, String parentUuid) {
        this.uuid = uuid;
        this.name = name;
        this.parentUuid = parentUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }
}
