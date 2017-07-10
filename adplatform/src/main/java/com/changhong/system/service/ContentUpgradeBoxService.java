package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.upgrade.ContentUpgradeBox;
import com.changhong.system.web.facade.dto.ContentUpgradeBoxDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-2-6 
 * Time: 14:29:49
 */
public interface ContentUpgradeBoxService {
	
	void saveDTO(ContentUpgradeBoxDTO dto);
	boolean obtainEntityExists(String boxId, String upgradeId);
	List<ContentUpgradeBoxDTO> obtainUpgradeBoxesDTO(String upgradeId);
	List<ContentUpgradeBox> obtainUpgradeBoxes(String upgradeId);
	
	/*      <tx:method name="obtain*" propagation="REQUIRED"/>
            <tx:method name="save*" propagation="REQUIRED"/>
            <tx:method name="delete*" propagation="REQUIRED"/>
            <tx:method name="create*" propagation="REQUIRED"/>
            <tx:method name="update*" propagation="REQUIRED"/>
            <tx:method name="change*" propagation="REQUIRED"/>
            <tx:method name="upload*" propagation="REQUIRED"/>
            <tx:method name="handle*" propagation="REQUIRED"/>
            <tx:method name="cancel*" propagation="REQUIRED"/>
	 */
	
	void saveUpgradeBoxes(List<ContentUpgradeBox> boxes);
	
	void deleteBoxByUpgradeId(String upgradeId);
	
	List<ContentUpgradeBoxDTO> obtainUpgradeBoxesByBoxId(String boxId);
	
}
