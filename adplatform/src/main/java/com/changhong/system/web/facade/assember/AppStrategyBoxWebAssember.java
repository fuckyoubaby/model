package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.StaticApplicationContext;
import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.app.AppStrategy;
import com.changhong.system.domain.app.AppStrategyBox;
import com.changhong.system.web.facade.dto.AppStrategyBoxDTO;

public class AppStrategyBoxWebAssember {
	
	public static AppStrategyBox toAppStrategyBoxDomain(AppStrategyBoxDTO dto){
		AppStrategyBox appStrategyBox = null;
		if(dto==null) return null;
		if(StringUtils.hasText(dto.getUuid())){
			appStrategyBox = (AppStrategyBox)EntityLoadHolder.getUserDao().findByUuid(dto.getUuid(),AppStrategyBox.class);
			appStrategyBox.setMacNumber(dto.getMacNumber());
			appStrategyBox.setStrategyUuid(dto.getStrategyUuid());
		}else
			appStrategyBox = new AppStrategyBox(dto.getStrategyUuid(), dto.getMacNumber());
		return appStrategyBox;
	}

	public static AppStrategyBoxDTO toAppStrategyBoxDTO(AppStrategyBox appStrategyBox){
		String uuid = appStrategyBox.getUuid();
		String strategyUuid = appStrategyBox.getStrategyUuid();
		String macNumber = appStrategyBox.getMacNumber();
		AppStrategyBoxDTO  appStrategyBoxDTO = new AppStrategyBoxDTO(uuid, strategyUuid, macNumber);
		return appStrategyBoxDTO;
	}
	
	public static List<AppStrategyBoxDTO> toAppStrategyBoxDTOList(List<AppStrategyBox> appStrategyBoxs){
		List<AppStrategyBoxDTO> dtos = new ArrayList<AppStrategyBoxDTO>();
		if(appStrategyBoxs!=null){
			for(AppStrategyBox loop:appStrategyBoxs){
				dtos.add(toAppStrategyBoxDTO(loop));
			}
		}
		return dtos;
	}
	
}
