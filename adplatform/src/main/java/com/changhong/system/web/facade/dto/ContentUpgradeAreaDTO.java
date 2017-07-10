package com.changhong.system.web.facade.dto;

import java.io.Serializable;

/**
 * 
 * Author: Guoxiaoyang
 * Date: 2017-2-7 
 * Time: 13:27:52
 *
 */
public class ContentUpgradeAreaDTO implements Serializable{
	
	private String uuid;
	private AreaDTO area;
	private String upgradeUuid;
	
	public ContentUpgradeAreaDTO(){
		super();
	}
	
	public ContentUpgradeAreaDTO(String uuid, AreaDTO area, String upgradeUuid){
		super();
		this.uuid = uuid;
		this.area =area;
		this.upgradeUuid = upgradeUuid;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public AreaDTO getArea() {
		return area;
	}
	public void setArea(AreaDTO area) {
		this.area = area;
	}
	public String getUpgradeUuid() {
		return upgradeUuid;
	}
	public void setUpgradeUuid(String upgradeUuid) {
		this.upgradeUuid = upgradeUuid;
	}
	
	
}
