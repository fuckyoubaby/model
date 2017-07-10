package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.upgrade.ContentUpgrade;
import com.changhong.system.web.facade.dto.ContentUpgradeDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-2-2 
 * Time: 11:52:16
 *
 */
public class ContentUpgradeWebAssember {

	public static ContentUpgrade toDomainAsSave(ContentUpgradeDTO contentUpgradeDTO){
		if(contentUpgradeDTO == null) return null;
		ContentUpgrade cu = null;
		String name = contentUpgradeDTO.getName();
		if(!StringUtils.hasText(name)) return null;
		
		String description = contentUpgradeDTO.getDescription();
		String uuid = contentUpgradeDTO.getUuid();
		if(StringUtils.hasText(uuid)){
			cu = (ContentUpgrade) EntityLoadHolder.getUserDao().findByUuid(uuid, ContentUpgrade.class);
			cu.setName(name);
			cu.setDescription(description);
		}else{
			cu = new ContentUpgrade(name, description);
		}
		
		return cu;
	}
	
	public static ContentUpgradeDTO toDTO(ContentUpgrade cu){
		if(cu == null) return null;
		ContentUpgradeDTO contentUpgradeDTO = new ContentUpgradeDTO(cu.getName(),cu.getDescription());
		contentUpgradeDTO.setUuid(cu.getUuid());
		contentUpgradeDTO.setPublishTime(cu.getPublishTime());
		contentUpgradeDTO.setTimestamp(cu.getTimestamp());
		contentUpgradeDTO.setEnable(cu.isEnable());
		return contentUpgradeDTO;
	}
	
	public static List<ContentUpgradeDTO> toDTOList(List<ContentUpgrade> cuList){
		List<ContentUpgradeDTO> list = new ArrayList<ContentUpgradeDTO>();
		if(cuList!=null){
			for(ContentUpgrade cu: cuList){
				list.add(toDTO(cu));
			}
		}
		return list;
	}
	
}
