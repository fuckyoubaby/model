package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.upgrade.ContentUpgradeCommunity;
import com.changhong.system.web.facade.dto.ContentUpgradeCommunityDTO;

/**
 * 
 * Author: Guoxiaoyang
 * Date: 2017-2-7 
 * Time: 09:28:16
 *
 */
public interface ContentUpgradeCommunityService {
	/*      
	 <tx:method name="obtain*" propagation="REQUIRED"/>
    <tx:method name="save*" propagation="REQUIRED"/>
    <tx:method name="delete*" propagation="REQUIRED"/>
    <tx:method name="create*" propagation="REQUIRED"/>
    <tx:method name="update*" propagation="REQUIRED"/>
    <tx:method name="change*" propagation="REQUIRED"/>
    <tx:method name="upload*" propagation="REQUIRED"/>
    <tx:method name="handle*" propagation="REQUIRED"/>
    <tx:method name="cancel*" propagation="REQUIRED"/>
*/
	void saveEntity(ContentUpgradeCommunity cuc);
	boolean obtainEntityExisits(String communityid, String upgradeid);
	List<ContentUpgradeCommunityDTO> obtainUpgradeCommunitiesDTO(String upgradeId);
	List<ContentUpgradeCommunity> obtainUpgradeCommunities(String upgradeId);
	
	void saveUpgradeCommunities(List<ContentUpgradeCommunity> communities);
	
	void deleteEntityByUpgradeId(String upgradeId);
	
	List<ContentUpgradeCommunityDTO> obtainUpgradeCommunitiesByCommunityId(String communityId);
}
