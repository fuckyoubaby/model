package com.changhong.system.domain.app;

import com.changhong.common.domain.EntityBase;

/**
 * @author yu
 */
public class AppManage extends EntityBase{
	
	private static final long serialVersionUID = 1L;

	private String appName;
	
	private String appType;
	private String appVersion;
	private String appVersionInfo;
	private String appFileUrl;
	private boolean appEnabled = true;
	
	public AppManage(){
		
	}

	public AppManage(String appName,String appType,String appVersion,String appVersionInfo,String appFileUrl){
		this.appName = appName;
		this.appType = appType;
		this.appVersion = appVersion;
		this.appVersionInfo = appVersionInfo;
		this.appFileUrl = appFileUrl;
		this.appEnabled = true;
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
