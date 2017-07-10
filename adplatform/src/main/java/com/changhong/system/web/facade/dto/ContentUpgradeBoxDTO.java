package com.changhong.system.web.facade.dto;

import java.io.Serializable;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-2-6 
 * Time: 14:32:54
 */
public class ContentUpgradeBoxDTO implements Serializable{
	
	private String uuid;
	private BoxDTO box;
	private String upgradeUuid;
	
	public ContentUpgradeBoxDTO() {
		super();
	}
	
	public ContentUpgradeBoxDTO(String uuid, BoxDTO box, String upgradeUuid) {
		super();
		this.uuid = uuid;
		this.box = box;
		this.upgradeUuid = upgradeUuid;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public BoxDTO getBox() {
		return box;
	}
	public void setBox(BoxDTO box) {
		this.box = box;
	}
	public String getUpgradeUuid() {
		return upgradeUuid;
	}
	public void setUpgradeUuid(String upgradeUuid) {
		this.upgradeUuid = upgradeUuid;
	}
	

}
