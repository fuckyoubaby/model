package com.changhong.system.web.facade.dto;

/**
 * User: Jack Wang
 * Date: 16-11-22
 * Time: 下午4:56
 */
public class BoxDTO {
    private String uuid;
    private String timestamp;

    private String mac;
    private String ssidName;
    private String ssidPassword;
    private String note;
    private String version;
    private String lastReportTime;
    private boolean isOnline;

    private int cpuRate;
    private int memRate;
    private int diskRate;

    private String communityPath;
    private String communityUuid;

    public BoxDTO() {
    }

    public BoxDTO(String communityUuid) {
        this.communityUuid = communityUuid;
    }

    public BoxDTO(String uuid, String timestamp, String mac,String ssidName, String ssidPassword,
                  String note, String version, String lastReportTime, boolean online, int cpuRate, int memRate, int diskRate, String communityUuid) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.mac = mac.toUpperCase();
        this.ssidName = ssidName;
        this.ssidPassword = ssidPassword;
        this.note = note;
        this.version = version;
        this.lastReportTime = lastReportTime;
        isOnline = online;
        this.cpuRate = cpuRate;
        this.memRate = memRate;
        this.diskRate = diskRate;
        this.communityUuid = communityUuid;
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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSsidName() {
        return ssidName;
    }

    public void setSsidName(String ssidName) {
        this.ssidName = ssidName;
    }

    public String getSsidPassword() {
        return ssidPassword;
    }

    public void setSsidPassword(String ssidPassword) {
        this.ssidPassword = ssidPassword;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLastReportTime() {
        return lastReportTime;
    }

    public void setLastReportTime(String lastReportTime) {
        this.lastReportTime = lastReportTime;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public int getCpuRate() {
        return cpuRate;
    }

    public void setCpuRate(int cpuRate) {
        this.cpuRate = cpuRate;
    }

    public int getMemRate() {
        return memRate;
    }

    public void setMemRate(int memRate) {
        this.memRate = memRate;
    }

    public int getDiskRate() {
        return diskRate;
    }

    public void setDiskRate(int diskRate) {
        this.diskRate = diskRate;
    }

    public String getCommunityUuid() {
        return communityUuid;
    }

    public void setCommunityUuid(String communityUuid) {
        this.communityUuid = communityUuid;
    }

    public String getCommunityPath() {
        return communityPath;
    }

    public void setCommunityPath(String communityPath) {
        this.communityPath = communityPath;
    }
}
