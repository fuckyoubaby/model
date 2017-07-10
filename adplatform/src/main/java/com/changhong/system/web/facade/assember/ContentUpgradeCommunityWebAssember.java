package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.box.Community;
import com.changhong.system.domain.upgrade.ContentUpgrade;
import com.changhong.system.domain.upgrade.ContentUpgradeCommunity;
import com.changhong.system.web.facade.dto.CommunityDTO;
import com.changhong.system.web.facade.dto.ContentUpgradeBoxDTO;
import com.changhong.system.web.facade.dto.ContentUpgradeCommunityDTO;

/**
 * 
 * Author: Guoxiaoyang
 * Date: 2017-2-7 
 * Time: 09:17:40
 */
public class ContentUpgradeCommunityWebAssember {
	
	public static ContentUpgradeCommunityDTO toDTO(ContentUpgradeCommunity cuc){
		if(cuc == null) return null;
		
		ContentUpgrade cu = cuc.getContentUpgrade();
		if(cu == null) return null;
		
		String communityid = cuc.getCommunityid();
		Community community = null;
		
		if(StringUtils.hasText(communityid)){
			community = (Community) EntityLoadHolder.getUserDao().findByUuid(communityid, Community.class);
		}else return null;
		if(community == null) return null;
		String uuid = cuc.getUuid();
		if(!StringUtils.hasText(uuid)) return null;
		
		CommunityDTO communityDTO = CommunityWebAssember.toCommunityDTO(community, false); 
		ContentUpgradeCommunityDTO dto = new ContentUpgradeCommunityDTO(uuid, communityDTO, cu.getUuid());
		return dto;
	}

	public static List<ContentUpgradeCommunityDTO> toDTOList(
			List<ContentUpgradeCommunity> lists) {
		List<ContentUpgradeCommunityDTO> communities = new ArrayList<ContentUpgradeCommunityDTO>();
		if(lists!=null){
			for(ContentUpgradeCommunity cuc:lists){
				ContentUpgradeCommunityDTO temp = toDTO(cuc);
				if(temp!=null){
					communities.add(toDTO(cuc));
				}else{
					if(cuc!=null){
						String communityId = cuc.getCommunityid();
						if(StringUtils.hasText(communityId)){
							EntityLoadHolder.getContentUpgradeCommunityDao().deleteEntitiesByCommunityId(communityId);
						}
					}
				}
			}
		}
		return communities;
	}

}
