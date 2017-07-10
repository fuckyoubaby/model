package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.app.AppManage;
import com.changhong.system.web.facade.dto.AppManageDTO;
import com.changhong.system.web.facade.dto.UserDTO;

public interface AppManageService {

	/**
	 * 编辑的时候使用，更改信息
	 * @param Uuid
	 * @return
	 */
	AppManageDTO obtainAppByUuid(String Uuid);
	
	/**
	 * appOverviewform.jsp
	 * @param AppName
	 * @param startPosition
	 * @param pageSize
	 * @return
	 */
	List<AppManageDTO> obtainApps(String AppName,int startPosition, int pageSize);
	
	
	int obtainAppSize(String AppName);
	/**
	 * 更改信息
	 * @param appManageDTO
	 */
    void changeAppDetails(AppManageDTO appManageDTO);

    /**
	 * 更改状态
	 * @param appManageDTO
	 */
    void changeStatusForApp(String appUuid);
    
    /**
     * 检查版本是否已经存在
     * @param appUuid
     * @param appVersion
     * @return 相同版本返回true，不同返回false
     */
    boolean obtainAppVersionIsExist(String appVersion);
    
    AppManage obtainAppManageByAppVersion(String appVersion);
	
}
