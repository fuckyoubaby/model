package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.app.AppStrategyArea;
import com.changhong.system.web.facade.dto.AppStrategyAreaDTO;

/**
 * @author yujiawei
 * @date 2017年2月10日
 * @time 上午9:22:16
 */
public interface AppStrategyAreaService {

	/**
	 * 保存策略地区信息
	 * @param appStrategyAreaDTO
	 */
	public void saveAreaList(AppStrategyAreaDTO appStrategyAreaDTO);
	
	/**
	 * 获取Arealist
	 * @param strategyUuid
	 * @return
	 */
	public List<AppStrategyArea> obtainAreaList(String strategyUuid);
	
	/**
	 * 删除area
	 * @param strategyUuid
	 */
	public void deleteArea(String strategyUuid);
	
	List<AppStrategyAreaDTO> obtainAppStrategyAreaDTOsByAreaId(String areaid);
}
