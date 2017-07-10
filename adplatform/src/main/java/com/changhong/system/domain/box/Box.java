package com.changhong.system.domain.box;

import com.changhong.common.domain.EntityBase;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.common.utils.CHStringUtils;
import org.joda.time.LocalDate;

import java.util.Date;

/**
 * User: Jack Wang
 * Date: 16-11-21
 * Time: 下午1:11
 */
public class Box extends EntityBase {

    private final static int CODE_LENGTH = 4;
    private final static int OFF_LINE_MAX_TIME_INTERNAL = 10 * 60 * 1000; //10分钟
    private final static int ERROR_MAX_TIME_INTERNAL = 12 * 60 * 60 * 1000; //12小时

    private String mac;
    private String ssidName;
    private String ssidPassword;
    private String note;

    private String firmwareVersion;
    private Date lastReportTime;
    private int cpuRate;
    private int memRate;
    private int diskRate;

    private Date codeGenerateTime;
    private String encryptCode;

    private Community community;

    public Box() {
    }

    public Box(String mac, String note, String ssidName, String ssidPassword) {
        this.mac = mac.toUpperCase();
        this.ssidName = ssidName;
        this.ssidPassword = ssidPassword;
        this.note = note;
        this.firmwareVersion = "1.0";
        long currentStamp = new Date().getTime();
        this.lastReportTime = new Date(currentStamp - OFF_LINE_MAX_TIME_INTERNAL * 10);
    }

    public boolean isEncryptCodeOverdue() {
        if (codeGenerateTime == null) {
            return true;
        }
        String generateDateStr = CHDateUtils.getSimpleDateFormat(codeGenerateTime);
        LocalDate generateDate = new LocalDate(generateDateStr);
        if (generateDate.isBefore(new LocalDate())) {
            return true;
        }
        return false;
    }

    public String generateNewCode() {
        this.encryptCode = CHStringUtils.getRandomString(CODE_LENGTH);
        this.codeGenerateTime = new Date();

        return this.encryptCode;
    }

    public void updateStatus(int cpu, int mem, int disk, String version) {
        this.cpuRate = cpu;
        this.memRate = mem;
        this.diskRate = disk;
        this.firmwareVersion = version;
        this.lastReportTime = new Date();
    }

    public boolean isOnline() {
        long currentStamp = new Date().getTime();
        long lastUpdateStamp = lastReportTime.getTime();

        long internal = currentStamp - lastUpdateStamp;
        return internal <= OFF_LINE_MAX_TIME_INTERNAL;
    }

    public static Date shouldAfterIsOnline() {
        long currentStamp = new Date().getTime();
        Date after = new Date(currentStamp - OFF_LINE_MAX_TIME_INTERNAL);
        return after;
    }

    public static Date shouldBeforeIsProblem() {
        long currentStamp = new Date().getTime();
        Date before = new Date(currentStamp - ERROR_MAX_TIME_INTERNAL);
        return before;
    }

    /*************************************************GETTER**********************************************************/

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

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public Date getLastReportTime() {
        return lastReportTime;
    }

    public void setLastReportTime(Date lastReportTime) {
        this.lastReportTime = lastReportTime;
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

    public Date getCodeGenerateTime() {
        return codeGenerateTime;
    }

    public void setCodeGenerateTime(Date codeGenerateTime) {
        this.codeGenerateTime = codeGenerateTime;
    }

    public String getEncryptCode() {
        return encryptCode;
    }

    public void setEncryptCode(String encryptCode) {
        this.encryptCode = encryptCode;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }
}
