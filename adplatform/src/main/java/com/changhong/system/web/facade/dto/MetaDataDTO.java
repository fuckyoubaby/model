package com.changhong.system.web.facade.dto;

/**
 * User: Jack Wang
 * Date: 16-11-21
 * Time: 上午9:33
 */
public class MetaDataDTO {

    private String uuid;
    private String timestamp;
    private String dataLevel;
    private String dataLevelDesc;
    private String startTime;
    private String endTime;
    private String heartInterval;
    private String note;

    public MetaDataDTO() {
    }

    public MetaDataDTO(String uuid, String timestamp, String dataLevel, String dataLevelDesc,
                       String startTime, String endTime, String heartInterval, String note) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.dataLevel = dataLevel;
        this.dataLevelDesc = dataLevelDesc;
        this.startTime = startTime;
        this.endTime = endTime;
        this.heartInterval = heartInterval;
        this.note = note;
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

    public String getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(String dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getDataLevelDesc() {
        return dataLevelDesc;
    }

    public void setDataLevelDesc(String dataLevelDesc) {
        this.dataLevelDesc = dataLevelDesc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHeartInterval() {
        return heartInterval;
    }

    public void setHeartInterval(String heartInterval) {
        this.heartInterval = heartInterval;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
