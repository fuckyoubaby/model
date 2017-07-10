package com.changhong.system.domain.upgrade;

import com.changhong.common.domain.EntityBase;
/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-26 
 * Time: 17:52:142
 *
 */
public class ContentUpgradeCommunity extends EntityBase {

	private String communityid;
	private ContentUpgrade contentUpgrade;
	
	public ContentUpgradeCommunity(){
		super();
	}
	
	public ContentUpgradeCommunity(String communityid,
			ContentUpgrade contentUpgrade) {
		super();
		this.communityid = communityid;
		this.contentUpgrade = contentUpgrade;
	}


	public String getCommunityid() {
		return communityid;
	}
	public void setCommunityid(String communityid) {
		this.communityid = communityid;
	}
	public ContentUpgrade getContentUpgrade() {
		return contentUpgrade;
	}
	public void setContentUpgrade(ContentUpgrade contentUpgrade) {
		this.contentUpgrade = contentUpgrade;
	}
	
}
