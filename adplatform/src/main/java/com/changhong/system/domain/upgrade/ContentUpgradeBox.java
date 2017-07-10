package com.changhong.system.domain.upgrade;

import com.changhong.common.domain.EntityBase;
/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-26 
 * Time: 17:53:12
 *
 */
public class ContentUpgradeBox extends EntityBase{

	private String boxid;
	private ContentUpgrade contentUpgrade;
	
	public ContentUpgradeBox(){
		super();
	}
	
	public ContentUpgradeBox(String boxid, ContentUpgrade contentUpgrade) {
		super();
		this.boxid = boxid;
		this.contentUpgrade = contentUpgrade;
	}

	public String getBoxid() {
		return boxid;
	}
	public void setBoxid(String boxid) {
		this.boxid = boxid;
	}
	public ContentUpgrade getContentUpgrade() {
		return contentUpgrade;
	}
	public void setContentUpgrade(ContentUpgrade contentUpgrade) {
		this.contentUpgrade = contentUpgrade;
	}
	
}
