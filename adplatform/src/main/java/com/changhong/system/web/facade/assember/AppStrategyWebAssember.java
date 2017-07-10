package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.util.StringUtils;
import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.app.AppStrategy;
import com.changhong.system.web.facade.dto.AppStrategyDTO;

/**
 * 
 * @author yu
 *
 */
public class AppStrategyWebAssember {
	
	public static AppStrategy toAppStrategyDomain(AppStrategyDTO dto){
		AppStrategy appStrategy = null;
		if(dto==null) return null;
		if(StringUtils.hasText(dto.getUuid())){
			appStrategy = (AppStrategy)EntityLoadHolder.getUserDao().findByUuid(dto.getUuid(), AppStrategy.class);
			appStrategy.setName(dto.getName());
			appStrategy.setAppVersion(dto.getAppVersion());
		}else{
			appStrategy = new AppStrategy(dto.getName(),dto.getAppVersion());
		}
		return appStrategy;
	}

	public static AppStrategyDTO toAppStrategyDTO(AppStrategy appStrategy){
		String uuid = appStrategy.getUuid();
		String name = appStrategy.getName();
		Date timestampDistrbute = appStrategy.getTimestampDistribute();
		String appVersion = appStrategy.getAppVersion();
		boolean strategyDistrbuteEnabled = appStrategy.isStrategyDistributeEnabled();
	    boolean strategyEnabled = appStrategy.isStrategyEnabled();
	    AppStrategyDTO appStrategyDTO = new AppStrategyDTO(uuid, name, timestampDistrbute, appVersion, strategyDistrbuteEnabled, strategyEnabled);
	    return appStrategyDTO;
	}
	
	public static List<AppStrategyDTO> toAppStrategyDTOList(List<AppStrategy> appStrategys){
		List<AppStrategyDTO> dtos = new ArrayList<AppStrategyDTO>();
		if(appStrategys!=null){
			for(AppStrategy loop:appStrategys){
				dtos.add(toAppStrategyDTO(loop));
			}
		}
		return dtos;
	}
}
