package com.changhong.system.web.facade.dto;

import java.io.Serializable;

public class AppManageDTO implements Serializable{
	
	private String uuid;
    private String appName;
	
    private String appType;
	private String appVersion;
	private String appVersionInfo;
	private String appFileUrl;
	private boolean appEnabled;
	
	public AppManageDTO(){
		
	}
	
	public AppManageDTO(String uuid,String appName,String appType,String appVersion,String appVersionInfo,String appFileUrl,boolean appEnabled){
		this.uuid = uuid;
		this.appName = appName;
		this.appType = appType;
		this.appVersion = appVersion;
		this.appVersionInfo = appVersionInfo;
		this.appFileUrl = appFileUrl;
	    this.appEnabled = appEnabled;
	}

	public String getUuid() {
		return uuid;
	}

	public String getAppName() {
		return appName;
	}

	public String getAppType() {
		return appType;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public String getAppVersionInfo() {
		return appVersionInfo;
	}

	public String getAppFileUrl() {
		return appFileUrl;
	}

	public boolean isAppEnabled() {
		return appEnabled;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public void setAppVersionInfo(String appVersionInfo) {
		this.appVersionInfo = appVersionInfo;
	}

	public void setAppFileUrl(String appFileUrl) {
		this.appFileUrl = appFileUrl;
	}

	public void setAppEnabled(boolean appEnabled) {
		this.appEnabled = appEnabled;
	}	

}
