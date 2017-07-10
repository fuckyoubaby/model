package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.app.AppStrategyArea;
import com.changhong.system.domain.app.AppStrategyCommunity;
import com.changhong.system.web.facade.dto.AppStrategyCommunityDTO;

public class AppStrategyCommunityWebAssember {

	public static AppStrategyCommunityDTO toAppStrategyCommunityDTO(AppStrategyCommunity appStrategyCommunity){
		if(appStrategyCommunity==null) return null;
		String uuid = appStrategyCommunity.getUuid();
		String strategyUuid = appStrategyCommunity.getStrategyUuid();
		String communityUuid = appStrategyCommunity.getCommunityUuid();
		AppStrategyCommunityDTO appStrategyCommunityDTO = new AppStrategyCommunityDTO(uuid, communityUuid, strategyUuid);
		return appStrategyCommunityDTO;
	}
	
	public static AppStrategyCommunity toAppStrategyCommunityDomain(AppStrategyCommunityDTO dto){
		AppStrategyCommunity appStrategyCommunity = null;
		if(dto==null) return null;
		if(StringUtils.hasText(dto.getUuid())){
			appStrategyCommunity = (AppStrategyCommunity)EntityLoadHolder.getUserDao().findByUuid(dto.getUuid(),AppStrategyCommunity.class);
	        appStrategyCommunity.setCommunityUuid(dto.getUuid());
	        appStrategyCommunity.setStrategyUuid(dto.getStrategyUuid());
		}else{
			appStrategyCommunity = new AppStrategyCommunity(dto.getCommunityUuid(),dto.getStrategyUuid());
		}
		return appStrategyCommunity;
	}
	
	public static List<AppStrategyCommunityDTO> toAppStrategyCommunityDTOList(List<AppStrategyCommunity> appStrategyCommunitys){
		
		List<AppStrategyCommunityDTO> appStrategyCommunityDTOs = new ArrayList<>();
		if(appStrategyCommunitys!=null){
			for(AppStrategyCommunity loop:appStrategyCommunitys){
				appStrategyCommunityDTOs.add(toAppStrategyCommunityDTO(loop));
			}
		}
		return appStrategyCommunityDTOs;
	}
}
