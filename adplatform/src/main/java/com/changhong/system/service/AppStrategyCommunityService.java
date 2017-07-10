package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.app.AppStrategyCommunity;
import com.changhong.system.web.facade.dto.AppStrategyCommunityDTO;

/**
 * @author yujiawei
 * @date 2017年2月10日
 * @time 上午9:23:18
 */
public interface AppStrategyCommunityService {

	/**
	 * 保存策略社区相关
	 * @param appStrategyCommunityDTO
	 */
	public void saveCommunityList(AppStrategyCommunityDTO appStrategyCommunityDTO);
	
	/**
	 * 通过strategyUuid获取社区
	 * @param strategyUuid
	 * @return
	 */
	public List<AppStrategyCommunity> obtainCommunityList(String strategyUuid);
	
	/**
	 * 根据strategyUuid删除社区
	 * @param strategyUuid
	 */
	public void deleteCommunity(String strategyUuid);
	
	List<AppStrategyCommunityDTO> obtainCommunityDTOsByCommunityId(String communityId);
}
