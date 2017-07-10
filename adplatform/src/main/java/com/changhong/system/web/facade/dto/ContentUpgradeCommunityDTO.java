package com.changhong.system.web.facade.dto;

import java.io.Serializable;

import com.changhong.system.domain.box.Community;

/**
 * 
 * Author: Guoxiaoyang
 * Date: 2017-2-7 
 * Time: 09:17:40
 */
public class ContentUpgradeCommunityDTO implements Serializable{
	private String uuid;
	private CommunityDTO community;
	private String upgradeUuid;
	
	
	public ContentUpgradeCommunityDTO() {
		super();
	}
	
	public ContentUpgradeCommunityDTO(String uuid, CommunityDTO community,
			String upgradeUuid) {
		super();
		this.uuid = uuid;
		this.community = community;
		this.upgradeUuid = upgradeUuid;
	}
	
	
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public CommunityDTO getCommunity() {
		return community;
	}
	public void setCommunity(CommunityDTO community) {
		this.community = community;
	}
	public String getUpgradeUuid() {
		return upgradeUuid;
	}
	public void setUpgradeUuid(String upgradeUuid) {
		this.upgradeUuid = upgradeUuid;
	}
	
	
}
