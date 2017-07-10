package com.changhong.system.web.facade.dto;

/**
 * User: Jack Wang
 * Date: 16-11-24
 * Time: 下午4:10
 */
public class BoxCommandDTO {

    private String uuid;
    private String timestamp;
    private String commandType;
    private String mac;
    private String note;
    private String planDate;
    private String planTime;
    private boolean finished;

    public BoxCommandDTO(String uuid, String timestamp, String commandType, String mac, String note, String planDate, String planTime, boolean finished) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.commandType = commandType;
        this.mac = mac;
        this.note = note;
        this.planDate = planDate;
        this.planTime = planTime;
        this.finished = finished;
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

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}

