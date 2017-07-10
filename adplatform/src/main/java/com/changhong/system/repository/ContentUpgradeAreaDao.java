package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.upgrade.ContentUpgradeArea;
import com.changhong.system.domain.upgrade.UpgradeEntity;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-2-6
 * Time: 11:34:29
 */
public interface ContentUpgradeAreaDao extends EntityObjectDao{
	List<ContentUpgradeArea> loadUpgradeAreas(String upgradeId);
	
	boolean checkExists(String areaid, String upgradeId);
	
	void batchSaveUpgradeAreas(List<ContentUpgradeArea> areas);
	
	void deleteEntitiesByUpgradeId(String upgradeId);
	
	List<ContentUpgradeArea> loadUpgradeAreasByAreaId(String areaId);
	
	void deleteEntitiesByAreaId(String areaId);
	
	UpgradeEntity loadLatestUpgradeEntityByAreaId(String areaId);
}
