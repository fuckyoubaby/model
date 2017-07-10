package com.changhong.system.web.facade.dto;

/**
 * User: Jack Wang
 * Date: 16-11-29
 * Time: 下午1:57
 */
public class ActionLogDTO {

    private String uuid;
    private String timestamp;
    private String operator;
    private String type;
    private String module;
    private String desc;

    public ActionLogDTO(String uuid, String timestamp, String operator, String type, String module, String desc) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.operator = operator;
        this.type = type;
        this.module = module;
        this.desc = desc;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
