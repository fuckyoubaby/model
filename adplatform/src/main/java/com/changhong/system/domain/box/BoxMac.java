package com.changhong.system.domain.box;

import com.changhong.common.domain.EntityBase;
/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-15 
 * Time: 13:56:35
 *
 */
public class BoxMac extends EntityBase {

	private String mac;
	private BoxMacStatus macStatus;
	
	public BoxMac(){
		
	}
	
	public BoxMac(String mac, BoxMacStatus macStatus) {
		this.mac = mac;
		this.macStatus = macStatus;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public BoxMacStatus getMacStatus() {
		return macStatus;
	}

	public void setMacStatus(BoxMacStatus macStatus) {
		this.macStatus = macStatus;
	}


	
}
