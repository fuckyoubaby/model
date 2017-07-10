package com.changhong.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhong.common.utils.CHLogUtils;
import com.changhong.system.domain.upgrade.ContentUpgradeArea;
import com.changhong.system.repository.ContentUpgradeAreaDao;
import com.changhong.system.web.facade.assember.ContentUpgradeAreaWebAssember;
import com.changhong.system.web.facade.dto.ContentUpgradeAreaDTO;
/**
 * 
 * Author: Guoxiaoyang
 * Date: 2017-2-7 
 * Time: 13:49:00
 */

@Service("contentUpgradeAreaService")
public class ContentUpgradeAreaServiceImpl implements ContentUpgradeAreaService {

	@Autowired
	private ContentUpgradeAreaDao contentUpgradeAreaDao;
	

	@Override
	public boolean obtainEntityExists(String areaid, String upgradeId) {
		return contentUpgradeAreaDao.checkExists(areaid, upgradeId);
	}

	@Override
	public List<ContentUpgradeAreaDTO> obtainUpgradeAreaDTOs(String upgradeId) {
		List<ContentUpgradeArea> cuAreas = contentUpgradeAreaDao.loadUpgradeAreas(upgradeId);
		return ContentUpgradeAreaWebAssember.toDTOList(cuAreas);
	}

	@Override
	public List<ContentUpgradeArea> obtainUpgradeAreas(String upgradeId) {
		return contentUpgradeAreaDao.loadUpgradeAreas(upgradeId);
	}

	@Override
	public void saveUpgradeAreas(List<ContentUpgradeArea> areas) {
		if(areas!=null && areas.size()>0){
			contentUpgradeAreaDao.saveAll(areas);
			if(areas.get(0)!=null && areas.get(0).getContentUpgrade()!=null){
				CHLogUtils.doLog(ContentUpgradeAreaServiceImpl.class, "config all areas to content upgrade "+areas.get(0).getContentUpgrade().getUuid(), "为播放升级["+areas.get(0).getContentUpgrade().getName()+"]配置地区");
			}
		}
		
	}

	@Override
	public void deleteEntityByUpgradeId(String upgradeId) {
		contentUpgradeAreaDao.deleteEntitiesByUpgradeId(upgradeId);
		CHLogUtils.doLog(ContentUpgradeAreaServiceImpl.class, "remove all areas from content upgrade "+upgradeId, "清空播放升级["+upgradeId+"]配置的所有地区");
	}

	@Override
	public List<ContentUpgradeAreaDTO> obtainUpgradeAreasByAreaId(String areaId) {
		List<ContentUpgradeArea> cuAreas = contentUpgradeAreaDao.loadUpgradeAreasByAreaId(areaId);
		return ContentUpgradeAreaWebAssember.toDTOList(cuAreas);
	}

	

}
