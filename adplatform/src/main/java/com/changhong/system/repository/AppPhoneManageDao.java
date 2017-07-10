package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.app.AppManage;
import com.changhong.system.domain.app.AppPhoneManage;

public interface AppPhoneManageDao extends EntityObjectDao {

	AppPhoneManage findAppByName(String AppName);
	
	/**
	 * 进入appPhoneform
	 * @param AppName
	 * @param startPosition
	 * @param pageSize
	 * @return
	 */
	List<AppPhoneManage> loadApps(String AppName, int startPosition, int pageSize);
	
	/**
	 * 查询
	 * @param AppName
	 * @return
	 */
	int loadAppSize(String AppName);
	
	/**
	 * 检验app是否存在
	 * @param appUuid
	 * @param appName
	 * @return
	 */
	boolean loadAppExist(String appUuid, String appName);
	
	/**
	 * 
	 * @param appVersion
	 * @return
	 */
	AppPhoneManage loadAppPhoneManageByAppVersion(String appVersion);
	
	/**
	 * 检验是否存在
	 * @param appVersion
	 * @return
	 */
	boolean obtainAppVersionIsExist(String appVersion);
	
	/**
	 * 取最新的一条记录
	 * @return
	 */
	AppPhoneManage loadLastAppPhoneManage();
}
