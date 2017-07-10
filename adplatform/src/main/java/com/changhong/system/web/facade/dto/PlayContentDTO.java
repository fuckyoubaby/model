package com.changhong.system.web.facade.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Author: Guo xiaoyang 
 * Date: 2017-1-12 
 * Time: 17:47:36
 */

public class PlayContentDTO implements Serializable{

	private String uuid;
	private String name;
	private String version;
	private Double defaultDuration;
	private Integer amount;
	private Date timestamp;
	
	
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Double getDefaultDuration() {
		return defaultDuration;
	}
	public void setDefaultDuration(Double defaultDuration) {
		this.defaultDuration = defaultDuration;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public PlayContentDTO(String uuid, String name, String version,
			Double defaultDuration) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.version = version;
		this.defaultDuration = defaultDuration;
	}
	
	public PlayContentDTO(){
		super();
	}
}
