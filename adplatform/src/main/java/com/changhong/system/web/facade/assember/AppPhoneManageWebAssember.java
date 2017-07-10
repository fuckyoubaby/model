package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.app.AppPhoneManage;
import com.changhong.system.web.facade.dto.AppPhoneManageDTO;

public class AppPhoneManageWebAssember {
      
	public static AppPhoneManage toAppPhoneManageDomain(AppPhoneManageDTO dto){
		AppPhoneManage appPhoneManage = null;
		if(dto==null) return null;
		if(StringUtils.hasText(dto.getUuid())){
			appPhoneManage = (AppPhoneManage)EntityLoadHolder.getUserDao().findByUuid(dto.getUuid(), AppPhoneManage.class);
			appPhoneManage.setAppName(dto.getAppName());
			appPhoneManage.setAppType(dto.getAppType());
			appPhoneManage.setAppVersion(dto.getAppVersion());
			appPhoneManage.setAppVersionInfo(dto.getAppVersionInfo());
			appPhoneManage.setAppFileUrl(dto.getAppFileUrl());
		}else{
			appPhoneManage =  new AppPhoneManage(dto.getAppName(),dto.getAppType(),dto.getAppVersion(),dto.getAppVersionInfo(),dto.getAppFileUrl());
		}
		return appPhoneManage;
	}
	
	public static AppPhoneManageDTO toAppPhoneManageDTO(AppPhoneManage appPhoneManage){
		String uuid = appPhoneManage.getUuid();
		String appName = appPhoneManage.getAppName();
		String appType = appPhoneManage.getAppType();
		String appVersion = appPhoneManage.getAppVersion();
		String appVersionInfo = appPhoneManage.getAppVersionInfo();
		String appFileUrl = appPhoneManage.getAppFileUrl();
		boolean enabled = appPhoneManage.isAppEnabled();
		AppPhoneManageDTO appPhoneManageDTO = new AppPhoneManageDTO(uuid, appName, appType, appVersion, appVersionInfo, appFileUrl, enabled);
		
		return appPhoneManageDTO;
	}
	
	public static List<AppPhoneManageDTO> toAppPhoneManageDTOList(List<AppPhoneManage> appPhoneManages){
		List<AppPhoneManageDTO> dtos = new ArrayList<AppPhoneManageDTO>();
		if(appPhoneManages!=null){
			for(AppPhoneManage loop:appPhoneManages){
				dtos.add(toAppPhoneManageDTO(loop));
			}
		}
		return dtos;
	}
}
