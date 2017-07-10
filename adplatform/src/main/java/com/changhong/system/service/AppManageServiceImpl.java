package com.changhong.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.domain.app.AppManage;
import com.changhong.system.domain.log.ActionModule;
import com.changhong.system.domain.log.ActionType;
import com.changhong.system.domain.user.User;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.repository.AppManageDao;
import com.changhong.system.web.event.ActionLogEvent;
import com.changhong.system.web.facade.assember.AppManageWebAssember;
import com.changhong.system.web.facade.dto.AppManageDTO;

@Service("appManageService")
public class AppManageServiceImpl implements AppManageService{
	
	@Autowired
	private AppManageDao appManageDao;

	@Override
	public AppManageDTO obtainAppByUuid(String Uuid) {
		AppManage appManage = (AppManage)appManageDao.findByUuid(Uuid, AppManage.class);
		return AppManageWebAssember.toAppManageDTO(appManage);
	}

	@Override
	public List<AppManageDTO> obtainApps(String AppName, int startPosition,
			int pageSize) {
		List<AppManage> list = appManageDao.loadApps(AppName, startPosition, pageSize);
		return AppManageWebAssember.toAppManageDTOList(list);
	}

	@Override
	public int obtainAppSize(String AppName) {
		return appManageDao.loadAppSize(AppName);
	}

	@Override
	public void changeAppDetails(AppManageDTO appManageDTO) {
        AppManage appManage = AppManageWebAssember.toAppManageDomain(appManageDTO);
        appManageDao.persist(appManage);
        
        //日志部分
        ActionLogEvent event = null;
        User current = SecurityUtils.currentUser();
        if(StringUtils.hasText(appManageDTO.getUuid())){
        	ApplicationLog.infoWithCurrentUser(AppManageServiceImpl.class, "change the app"+appManage.getUuid()+"info");
        	event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "更新app信息"+appManage.getAppName());
        }else{
        	ApplicationLog.infoWithCurrentUser(AppManageServiceImpl.class, "add the app"+appManage.getUuid()+"info");
        	event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "新增app信息"+appManage.getAppName());
        }
        ApplicationThreadPool.executeThread(event);
	}

	@Override
	public void changeStatusForApp(String appUuid) {
		AppManage appManage = (AppManage)appManageDao.findByUuid(appUuid, AppManage.class);
		if(appManage.isAppEnabled()){
			appManage.setAppEnabled(false);
		}else{
			appManage.setAppEnabled(true);
		}
		//日志部分
	  ApplicationLog.infoWithCurrentUser(AppManageServiceImpl.class, "change the app " + appUuid + " status");
	  User current = SecurityUtils.currentUser();
	  ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "更新app状态"+appManage.getAppName());
	  ApplicationThreadPool.executeThread(event);
	}

	@Override
	public boolean obtainAppVersionIsExist(String appVersion) {
		if(appManageDao.obtainAppVersionIsExist(appVersion)){
			return true;
		}else{
			return false;
		}
	}   

	@Override
	public AppManage obtainAppManageByAppVersion(String appVersion) {
		// TODO Auto-generated method stub
		return appManageDao.loadAppManageByAppVersion(appVersion);
	}

}
