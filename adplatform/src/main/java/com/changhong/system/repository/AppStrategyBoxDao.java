package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.app.AppStrategyBox;
import com.changhong.system.domain.app.AppUpgradeEntity;

/**
 * @author yujiawei
 */
public interface AppStrategyBoxDao extends EntityObjectDao {
	
	/**
	 * 通过strategyUuid获取AppStrategyBox
	 * @param strategyUuid
	 * @return
	 */
	public List<AppStrategyBox> getMacList(String strategyUuid);
	
	/**
	 * 根据strategyUuid删除盒子
	 * @param strategyUuid
	 */
	public void deleteBoxMac(String strategyUuid);
	
	List<AppStrategyBox> getStrategyBoxsByMac(String mac);
	
	AppUpgradeEntity loadLatestStrategyByBoxMac(String mac);
	
}
