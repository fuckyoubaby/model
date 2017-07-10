package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.app.AppManage;

/**
 * @author yu
 * @date 2017年12月16日
 * @time 下午3:04:47
 */
public interface AppManageDao extends EntityObjectDao{

	/**
	 * 待定
	 * @param AppName
	 * @return
	 */
	AppManage findAppByName(String AppName);
	
	/**
	 * 进入appform
	 * @param appName
	 * @param startPosition
	 * @param pageSize
	 * @return
	 */
	List<AppManage> loadApps(String AppName, int startPosition, int pageSize);
	
	/**
	 * appoverviewpagging中使用
	 * @param appName
	 * @return
	 */
	int loadAppSize(String AppName);
	
	/**
	 * 校验app是否存在
	 * @param appUuid
	 * @param appName
	 * @return
	 */
	boolean loadAppExist(String appUuid, String appName);
	

	AppManage loadAppManageByAppVersion(String appVersion);
	

	/**
	 * 检验版本是否存在
	 * @param appVersion
	 * @return
	 */
	boolean obtainAppVersionIsExist(String appVersion);

}
