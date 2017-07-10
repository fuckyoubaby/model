package com.changhong.system.domain.upgrade;

import java.util.Date;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-4-7 
 * Time: 10:03:20
 *
 */
public class UpgradeEntity {

	private String uuid;
	private Date publishTime;
	
	public UpgradeEntity(){
		
	}
	
	public UpgradeEntity(String uuid, Date publishTime){
		this.uuid = uuid;
		this.publishTime = publishTime;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
}
