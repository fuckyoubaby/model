package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.app.AppStrategyBox;
import com.changhong.system.web.facade.dto.AppStrategyBoxDTO;

/**
 * @author yujiawei
 * @date 2017年2月10日
 * @time 上午9:22:49
 */
public interface AppStrategyBoxService {
	
	/**
	 * 保存boxuuid和strategyUuid
	 * @param appStrategyBoxDTO
	 */
	public void saveBoxList(AppStrategyBoxDTO appStrategyBoxDTO);
	
	/**
	 * 显示已经配置的mac地址
	 * @param strategyUuid
	 * @return
	 */
	public List<AppStrategyBox> obtainMacList(String strategyUuid);

	/**
	 * 删除同一个策略相同uuid情况下相同的mac
	 * @param strategyUuid
	 */
	public void deleteBoxMac(String strategyUuid);
	
	List<AppStrategyBoxDTO> obtainAppStrategyBoxDTOsByMac(String mac);
}
