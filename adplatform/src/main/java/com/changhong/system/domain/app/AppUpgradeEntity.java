package com.changhong.system.domain.app;

import java.util.Date;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-4-7 
 * Time: 11:14:12
 *
 */
public class AppUpgradeEntity {

	
	private String uuid;
	private String apkVersion;
	private Date publishTime;
	
	public AppUpgradeEntity(){
		
	}
	
	public AppUpgradeEntity(String uuid, String apkVersion, Date publishTime) {
		super();
		this.uuid = uuid;
		this.apkVersion = apkVersion;
		this.publishTime = publishTime;
	}

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getApkVersion() {
		return apkVersion;
	}
	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	
}
