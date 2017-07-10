package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.app.AppStrategy;

/** 
 * @author yujiawei
 * @date 2017年2月10日
 */
public interface AppStrategyDao extends EntityObjectDao {
	
	/** 
	 * @param name
	 * @return
	 */
	AppStrategy findAppStrategyByName(String name);
	
	/**
	 * 进入strategyform中使用
	 * @param strategyName
	 * @param startPosition
	 * @param pageSize
	 * @return
	 */
	List<AppStrategy> loadAppStrategy(String strategyName, int startPosition, int pageSize);
	
	/**
	 * 
	 * @param strategyName
	 * @return
	 */
	int loadAppSize(String strategyName);

	/**
	 * 检验策略是否存在
	 * @param strategyUuid
	 * @param strategyName
	 * @return
	 */
	boolean loadAppExist(String strategyUuid, String strategyName);
	
	List<AppStrategy> loadAll();
}
