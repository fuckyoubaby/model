package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.upgrade.ContentUpgradeBox;
import com.changhong.system.domain.upgrade.UpgradeEntity;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-2-6
 * Time: 11:34:29
 */
public interface ContentUpgradeBoxDao extends EntityObjectDao{

	List<ContentUpgradeBox> loadUpgradeBoxes(String upgradeId);
	
	boolean checkExists(String boxId, String upgradeId);
	
	void batchSaveUpgradeBoxes(List<ContentUpgradeBox> boxes);
	
	void deleteBoxByUpgradeId(String upgradeId);
	
	void deleteEntities(List<ContentUpgradeBox> boxes);
	
	List<ContentUpgradeBox> loadUpgradeBoxesByBoxId(String boxid);
	
	void deleteUpgradeBoxesByBoxId(String boxid);
	
	UpgradeEntity loadLatestUpgradeByBoxId(String boxId);
	
}
