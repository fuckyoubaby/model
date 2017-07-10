package com.changhong.system.web.facade.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-2-2 
 * Time: 11:38:41
 *
 */
public class ContentUpgradeDTO implements Serializable{
	
	private String uuid;
	private String name;
	private String description;
	private Date publishTime;
	private Date timestamp;
	private boolean enable;
	
	public ContentUpgradeDTO(){
		super();
	}
	
	public ContentUpgradeDTO(String name, String description){
		super();
		this.name = name;
		this.description = description;
	}
	
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	
}
