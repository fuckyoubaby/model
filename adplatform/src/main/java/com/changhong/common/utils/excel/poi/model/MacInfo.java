package com.changhong.common.utils.excel.poi.model;

import java.util.Date;

import com.changhong.common.utils.excel.poi.tools.DateUtils;


public class MacInfo {
	
	private String mac;
	
	private Date date;

	
	public MacInfo(){
		
	}
	
	
	public MacInfo(String mac, Date date) {
		super();
		this.mac = mac;
		this.date = date;
	}

	public MacInfo(String mac) {
		this.mac = mac;
		this.date = new Date();
	}


	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "MacInfo[mac: "+this.mac+", date: "+DateUtils.format(date)+"]";
	}
	

}
