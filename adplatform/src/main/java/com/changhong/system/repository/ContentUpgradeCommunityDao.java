package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.upgrade.ContentUpgradeCommunity;
import com.changhong.system.domain.upgrade.UpgradeEntity;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-2-6
 * Time: 11:34:29
 */
public interface ContentUpgradeCommunityDao extends EntityObjectDao{
	
	List<ContentUpgradeCommunity> loadUpgradeCommunities(String upgradeId);
	
	boolean checkExists(String communityId, String upgradeId);
	
	void batchSaveUpgradeCommunities(List<ContentUpgradeCommunity> communities);
	
	void deleteEntitiesByUpgradeId(String upgradeId);
	
	List<ContentUpgradeCommunity> loadUpgradeCommunitiesByCommunityId(String communityId);
	
	void deleteEntitiesByCommunityId(String communityId);
	
	UpgradeEntity loadLatestUpgradeByCommunityId(String communityId);
}
