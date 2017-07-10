package com.changhong.system.domain.upgrade;

import com.changhong.common.domain.EntityBase;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-26 
 * Time: 17:52:12
 *
 */
public class ContentUpgradeArea extends EntityBase {
	
	private String areaid;
	private ContentUpgrade contentUpgrade;
	
	public ContentUpgradeArea() {
		super();
	}
	
	public ContentUpgradeArea(String areaid, ContentUpgrade contentUpgrade) {
		super();
		this.areaid = areaid;
		this.contentUpgrade = contentUpgrade;
	}

	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public ContentUpgrade getContentUpgrade() {
		return contentUpgrade;
	}
	public void setContentUpgrade(ContentUpgrade contentUpgrade) {
		this.contentUpgrade = contentUpgrade;
	}
	
	
}
