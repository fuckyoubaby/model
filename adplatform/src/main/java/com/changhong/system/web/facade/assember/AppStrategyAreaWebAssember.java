package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.app.AppStrategyArea;
import com.changhong.system.web.facade.dto.AppStrategyAreaDTO;

public class AppStrategyAreaWebAssember {

	public static AppStrategyAreaDTO toAppStrategyAreaDTO(AppStrategyArea appStrategyArea){
		if(appStrategyArea==null) return null;
		String uuid = appStrategyArea.getUuid();
		String areaUuid = appStrategyArea.getAreaUuid();
		String strategyUuid = appStrategyArea.getStrategyUuid();
		AppStrategyAreaDTO appStrategyAreaDTO = new AppStrategyAreaDTO(uuid, strategyUuid, areaUuid);
		
		return appStrategyAreaDTO;
	}
	
	public static AppStrategyArea toAppStrategyAreaDomain(AppStrategyAreaDTO dto){
		AppStrategyArea appStrategyArea = null;
		if(dto==null) return null;
		if(StringUtils.hasText(dto.getUuid())){
			appStrategyArea = (AppStrategyArea)EntityLoadHolder.getUserDao().findByUuid(dto.getUuid(),AppStrategyArea.class);
		    appStrategyArea.setStrategyUuid(dto.getStrategyUuid());
		    appStrategyArea.setAreaUuid(dto.getAreaUuid());
		}else{
			appStrategyArea = new AppStrategyArea(dto.getStrategyUuid(),dto.getAreaUuid());
		}
		return appStrategyArea;
	}
	
	public static List<AppStrategyAreaDTO> toAppStrategyAreaList(List<AppStrategyArea> appStrategyAreas){
		List<AppStrategyAreaDTO> dtos = new ArrayList<AppStrategyAreaDTO>();
		if(appStrategyAreas!=null){
			for(AppStrategyArea loop:appStrategyAreas){
				dtos.add(toAppStrategyAreaDTO(loop));
			}
		}
		return dtos;
	}
}
