package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.util.StringUtils;

import IceUtilInternal.StringUtil;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.app.AppManage;
import com.changhong.system.domain.user.User;
import com.changhong.system.web.facade.dto.AppManageDTO;
/**
 * 
 * @author yu
 *
 */
public class AppManageWebAssember {

	public static AppManage toAppManageDomain(AppManageDTO dto){
		AppManage appManage = null;
		if(dto==null) return null;
		if(StringUtils.hasText(dto.getUuid())){
			appManage = (AppManage) EntityLoadHolder.getUserDao().findByUuid(dto.getUuid(), AppManage.class);
			appManage.setAppName(dto.getAppName());
			appManage.setAppType(dto.getAppType());
			appManage.setAppVersion(dto.getAppVersion());
			appManage.setAppVersionInfo(dto.getAppVersionInfo());
			appManage.setAppFileUrl(dto.getAppFileUrl());
		}else {
			appManage = new AppManage(dto.getAppName(),dto.getAppType(),dto.getAppVersion(),dto.getAppVersionInfo(),dto.getAppFileUrl());
		}
		return appManage;
	}
	
	public static AppManageDTO toAppManageDTO(AppManage appManage){

			String uuid = appManage.getUuid();
			String appName = appManage.getAppName();
			String appType = appManage.getAppType();
			String appVersion = appManage.getAppVersion();
			String appVersionInfo = appManage.getAppVersionInfo();
			String appFileUrl = appManage.getAppFileUrl();
			boolean enabled = appManage.isAppEnabled();
			AppManageDTO appManageDTO = new AppManageDTO(uuid, appName, appType, appVersion, appVersionInfo, appFileUrl, enabled);
		    return appManageDTO;
	}
	
	public static List<AppManageDTO> toAppManageDTOList(List<AppManage> appManages){
		List<AppManageDTO> dtos = new ArrayList<AppManageDTO>(); 
		if(appManages!=null){
			for(AppManage loop :appManages){
				dtos.add(toAppManageDTO(loop));
			}
		}
		return dtos;
	}
}
