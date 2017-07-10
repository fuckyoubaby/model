package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.app.AppStrategy;
import com.changhong.system.domain.app.AppUpgradeEntity;
import com.changhong.system.web.facade.dto.AppStrategyDTO;

/**
 * @author yujiawei
 * @date 2017年2月10日
 * @time 上午9:23:41
 */
public interface AppStrategyService {

	/**
	 * 编辑、更改信息
	 * @param Uuid
	 * @return
	 */
	AppStrategyDTO obtainStrategyByUuid(String Uuid);
	
	/**
	 * @param strateName
	 * @param startPosition
	 * @param pageSize
	 * @return
	 */
	List<AppStrategyDTO> obtainStrategys(String strategyName,int startPosition, int pageSize);
	
	int obtainStrategySize(String strategyName);
	
	/**
	 * 校验
	 * @param Uuid
	 * @param strategyName
	 * @return
	 */
	boolean obtainStrategyExist(String Uuid, String strategyName);
	
	/**
	 * 更改信息
	 * @param appStrategyDTO
	 */
	void changeAppStrategyDetails(AppStrategyDTO appStrategyDTO);
	
	/**
	 * 更新状态是否可用
	 * @param strategyUuid
	 */
	void changeStatusForAppStrategy(String strategyUuid);
	
	/**
	 * 处理是否发布
	 * @param strategyUuid
	 */
	void changeDistributeStatus(String strategyUuid);
	
	List<AppStrategy> obtainAllAppStrategties();
	
	AppUpgradeEntity obtainLatestAppVersionByInfos(String areaId, String communityId, String mac);
}
