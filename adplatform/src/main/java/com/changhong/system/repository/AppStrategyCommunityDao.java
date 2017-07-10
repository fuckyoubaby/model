package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.app.AppStrategyCommunity;
import com.changhong.system.domain.app.AppUpgradeEntity;

/**
 * @author yujiawei
 */
public interface AppStrategyCommunityDao extends EntityObjectDao {

	/**
	 * 根据strategyUuid获取社区list
	 * @param strategyUuid
	 * @return
	 */
	public List<AppStrategyCommunity> getCommunityList(String strategyUuid);
	
	/**
	 * 通过strategyUuid删除社区
	 * @param strategyUuid
	 */
	public void deleteCommunity(String strategyUuid);
	
	List<AppStrategyCommunity> getCommunitiesByCommunityId(String communityId);
	
	
	AppUpgradeEntity loadLatestStratageByCommunityId(String communityId);
}
