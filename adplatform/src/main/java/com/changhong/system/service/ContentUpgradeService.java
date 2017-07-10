package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.upgrade.ContentUpgrade;
import com.changhong.system.web.facade.dto.ContentUpgradeDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-2-2 
 * Time: 12:07:42
 *
 */
public interface ContentUpgradeService {

	ContentUpgradeDTO obtainDTOByUuid(String uuid);

	boolean obtainNameExists(String uuid, String name);

	void saveDTO(ContentUpgradeDTO contentUpgradeDTO);

	List<ContentUpgradeDTO> obtainContentUpgradeDTOs(String filterName,
			int startPosition, int pageSize);


	int obtainAmount(String filterName);

	void changeStatus(String contentUpgradeId);

	void handlePublish(String contentUpgradeId);

	void deleteByUuid(String contentUpgradeId);

	void updateNewContent(String upgradeUuid, String newContentId,
			String oldContentId);

	ContentUpgrade obtainEntityByUuid(String contentUpgradeId);
	
	List<ContentUpgrade> obtainContentUpgrades();
	
	String obtainLatestUpgradeUUIDByBox(String areaId,String communityId, String boxId);
}
