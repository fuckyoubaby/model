package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.app.AppManage;
import com.changhong.system.domain.app.AppPhoneManage;
import com.changhong.system.web.facade.dto.AppPhoneManageDTO;

public interface AppPhoneManageService {

	/**
	 * 编辑
	 * @param Uuid
	 * @return
	 */
	AppPhoneManageDTO obtainAppByUuid(String Uuid);
	
	List<AppPhoneManageDTO> obtainApps(String AppName,int startPosition, int pageSize);
	
	int obtainAppSize(String AppName);
	
    /**
     * 更改信息	
     * @param appPhoneManageDTO
     */
	void changeAppDetails(AppPhoneManageDTO appPhoneManageDTO);
	
	/**
	 * 更改状态
	 * @param appUuid
	 */
	void changeStatusForApp(String appUuid);
	
	/**
	 * 检查版本是否已经存在
	 * @param appVersion
	 * @return
	 */
    boolean obtainAppVersionIsExist(String appVersion);
    
    AppPhoneManage obtainAppPhoneManageByAppVersion(String appVersion);
    
    /**
     * 取最新的一条数据
     * @return
     */
    AppPhoneManage obtainLastAppPhoneManage();
}
