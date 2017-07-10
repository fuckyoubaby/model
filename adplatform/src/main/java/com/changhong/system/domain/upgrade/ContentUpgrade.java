package com.changhong.system.domain.upgrade;

import java.util.Date;
import java.util.List;

import com.changhong.common.domain.EntityBase;
import com.changhong.system.domain.advertisment.PlayContent;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-26 
 * Time: 17:52:12
 *
 */
public class ContentUpgrade extends EntityBase{

	private String name;
	private String description;
	private boolean enable = true;
	private Date publishTime;
	private PlayContent playContent;
	
	private List<ContentUpgradeBox> boxes;
	
	private List<ContentUpgradeCommunity> communities;
	
	private List<ContentUpgradeArea> areas;
	
	public ContentUpgrade(){
		super();
	}
	
	public ContentUpgrade(String name, String description) {
		super();
		this.name = name;
		this.description = description;
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
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public PlayContent getPlayContent() {
		return playContent;
	}

	public void setPlayContent(PlayContent playContent) {
		this.playContent = playContent;
	}

	public List<ContentUpgradeBox> getBoxes() {
		return boxes;
	}

	public void setBoxes(List<ContentUpgradeBox> boxes) {
		this.boxes = boxes;
	}

	public List<ContentUpgradeCommunity> getCommunities() {
		return communities;
	}

	public void setCommunities(List<ContentUpgradeCommunity> communities) {
		this.communities = communities;
	}

	public List<ContentUpgradeArea> getAreas() {
		return areas;
	}

	public void setAreas(List<ContentUpgradeArea> areas) {
		this.areas = areas;
	}
	
}
