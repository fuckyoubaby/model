package com.changhong.system.web.facade.dto;

import java.util.Date;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-16 
 * Time: 09:19:56
 *
 */
public class BoxMacDTO {

	private String uuid;
	private Date timestamp;
	private String macStatus;
	private String mac;
	
	public BoxMacDTO(){
		super();
	}
	
	public BoxMacDTO(String uuid, Date timestamp, String macStatus, String mac) {
		super();
		this.uuid = uuid;
		this.timestamp = timestamp;
		this.macStatus = macStatus;
		this.mac = mac;
	}
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMacStatus() {
		return macStatus;
	}
	public void setMacStatus(String macStatus) {
		this.macStatus = macStatus;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	
	
}
