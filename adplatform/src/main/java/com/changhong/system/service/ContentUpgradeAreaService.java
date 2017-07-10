package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.upgrade.ContentUpgradeArea;
import com.changhong.system.web.facade.dto.ContentUpgradeAreaDTO;
/**
 * 
 * Author: Guoxiaoyang
 * Date: 2017-2-7 
 * Time: 13:49:00
 */
public interface ContentUpgradeAreaService {
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
	
	boolean obtainEntityExists(String areaid, String upgradeId);
	List<ContentUpgradeAreaDTO> obtainUpgradeAreaDTOs(String upgradeId);
	List<ContentUpgradeArea> obtainUpgradeAreas(String upgradeId);
	
	void saveUpgradeAreas(List<ContentUpgradeArea> areas);
	
	void deleteEntityByUpgradeId(String upgradeId);
	
	List<ContentUpgradeAreaDTO> obtainUpgradeAreasByAreaId(String areaId);
	
}
