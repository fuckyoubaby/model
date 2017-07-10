package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.box.Area;
import com.changhong.system.domain.upgrade.ContentUpgrade;
import com.changhong.system.domain.upgrade.ContentUpgradeArea;
import com.changhong.system.web.facade.dto.AreaDTO;
import com.changhong.system.web.facade.dto.ContentUpgradeAreaDTO;

/**
 * 
 * Author: Guoxiaoyang
 * Date: 2017-2-7 
 * Time: 13:27:52
 *
 */
public class ContentUpgradeAreaWebAssember {

	public static ContentUpgradeAreaDTO toDTO(ContentUpgradeArea area){
		if(area == null) return null;
		
		String uuid = area.getUuid();
		if(!StringUtils.hasText(uuid)) return null;
		
		ContentUpgrade cu = area.getContentUpgrade();
		if(cu==null) return null;
		
		String areaid =  area.getAreaid();
		Area a = null;
		if(StringUtils.hasText(areaid)){
			a = (Area) EntityLoadHolder.getUserDao().findByUuid(areaid, Area.class);
		}else return null;
		if(a == null) return null;
		
		AreaDTO areaDTO = AreaWebAssember.toAreaDTO(a);
		return new ContentUpgradeAreaDTO(uuid, areaDTO, cu.getUuid());
	}
	
	public static List<ContentUpgradeAreaDTO> toDTOList(List<ContentUpgradeArea> upgradeAreas){
		List<ContentUpgradeAreaDTO> upgradeAreaDTOs = new ArrayList<ContentUpgradeAreaDTO>();
		if(upgradeAreas!=null){
			for(ContentUpgradeArea a: upgradeAreas){
				ContentUpgradeAreaDTO temp = toDTO(a);
				if(temp!=null){
					upgradeAreaDTOs.add(temp);
				}else{
					if(a!=null){
						String areaId = a.getAreaid();
						if(StringUtils.hasText(areaId)){
							EntityLoadHolder.getContentUpgradeAreaDao().deleteEntitiesByAreaId(areaId);
						}
					}
					
				}
			}
		}
		return upgradeAreaDTOs;
	}
}
