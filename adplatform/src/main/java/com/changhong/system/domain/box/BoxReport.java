package com.changhong.system.domain.box;

import java.util.Date;

import com.changhong.common.domain.EntityBase;
/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-22 
 * Time: 11:29:19
 *
 */
public class BoxReport extends EntityBase{
	
	private String mac;
	private BoxReportStatus status;
	private String code;
	private Date solveTime;
	
	public BoxReport(){
		
	}
	
	public BoxReport(String mac, BoxReportStatus status, String code, Date solveTime) {
		super();
		this.mac = mac;
		this.status = status;
		this.code = code;
		this.solveTime = solveTime;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public BoxReportStatus getStatus() {
		return status;
	}

	public void setStatus(BoxReportStatus status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getSolveTime() {
		return solveTime;
	}

	public void setSolveTime(Date solveTime) {
		this.solveTime = solveTime;
	}
	
}
