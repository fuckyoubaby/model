package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.app.AppStrategyArea;
import com.changhong.system.domain.app.AppUpgradeEntity;

/**
 * @author yujiawei
 * @date 2017年2月10日
 */
public interface AppStrategyAreaDao extends EntityObjectDao {

	/**
	 * 通过strategyUuid获得AreaID
	 * @param strategyUuid
	 * @return
	 */
	public List<AppStrategyArea> obtainAreaList(String strategyUuid);
	
	/**
	 * 通过StrategyUuid删除地区
	 * @param StrategyUuid
	 */
	public void deleteArea(String strategyUuid);
	
	/**
	 * 通过地区id获取策略
	 * @param areaId
	 * @return
	 */
	List<AppStrategyArea> obtainAreaListByAreaId(String areaId);
	
	/**
	 * 通过地区id获取最新的可用策略
	 * @param areaId
	 * @return
	 */
	AppUpgradeEntity loadLatestStrategyByAreaId(String areaId);
	
}
