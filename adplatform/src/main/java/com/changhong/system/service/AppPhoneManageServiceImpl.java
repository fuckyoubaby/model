package com.changhong.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.domain.app.AppManage;
import com.changhong.system.domain.app.AppPhoneManage;
import com.changhong.system.domain.log.ActionModule;
import com.changhong.system.domain.log.ActionType;
import com.changhong.system.domain.user.User;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.repository.AppPhoneManageDao;
import com.changhong.system.web.event.ActionLogEvent;
import com.changhong.system.web.facade.assember.AppManageWebAssember;
import com.changhong.system.web.facade.assember.AppPhoneManageWebAssember;
import com.changhong.system.web.facade.dto.AppPhoneManageDTO;

@Service("appPhoneManageService")
public class AppPhoneManageServiceImpl implements AppPhoneManageService {

	@Autowired
	private AppPhoneManageDao appPhoneManageDao;
	
	@Override
	public AppPhoneManageDTO obtainAppByUuid(String Uuid) {
		AppPhoneManage appPhoneManage = (AppPhoneManage)appPhoneManageDao.findByUuid(Uuid, AppPhoneManage.class);
		return AppPhoneManageWebAssember.toAppPhoneManageDTO(appPhoneManage);
	}

	@Override
	public List<AppPhoneManageDTO> obtainApps(String AppName,
			int startPosition, int pageSize) {
		List<AppPhoneManage> list = appPhoneManageDao.loadApps(AppName, startPosition, pageSize);
		return AppPhoneManageWebAssember.toAppPhoneManageDTOList(list);
	}

	@Override
	public int obtainAppSize(String AppName) {
		return appPhoneManageDao.loadAppSize(AppName);
	}

	@Override
	public void changeAppDetails(AppPhoneManageDTO appPhoneManageDTO) {
		AppPhoneManage appPhoneManage = AppPhoneManageWebAssember.toAppPhoneManageDomain(appPhoneManageDTO);
        appPhoneManageDao.persist(appPhoneManage);
        
      //日志部分
        ActionLogEvent event = null;
        User current = SecurityUtils.currentUser();
        if(StringUtils.hasText(appPhoneManageDTO.getUuid())){
        	ApplicationLog.infoWithCurrentUser(AppPhoneManageServiceImpl.class, "change the app"+appPhoneManage.getUuid()+"info");
        	event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "更新app信息"+appPhoneManage.getAppName());
        }else{
        	ApplicationLog.infoWithCurrentUser(AppPhoneManageServiceImpl.class, "add the app"+appPhoneManage.getUuid()+"info");
        	event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "新增app信息"+appPhoneManage.getAppName());
        }
        ApplicationThreadPool.executeThread(event);
	}

	@Override
	public void changeStatusForApp(String appUuid) {
		AppPhoneManage appPhoneManage = (AppPhoneManage)appPhoneManageDao.findByUuid(appUuid, AppPhoneManage.class);
		if(appPhoneManage.isAppEnabled()){
			appPhoneManage.setAppEnabled(false);
		}else{
			appPhoneManage.setAppEnabled(true);
		}
		//日志部分
		ApplicationLog.infoWithCurrentUser(AppPhoneManageServiceImpl.class, "change the app " + appUuid + " status");
		User current = SecurityUtils.currentUser();
		ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "更新app状态"+appPhoneManage.getAppName());
		ApplicationThreadPool.executeThread(event);
	}

	@Override
	public boolean obtainAppVersionIsExist(String appVersion) {
		if(appPhoneManageDao.obtainAppVersionIsExist(appVersion)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public AppPhoneManage obtainAppPhoneManageByAppVersion(String appVersion) {
		return appPhoneManageDao.loadAppPhoneManageByAppVersion(appVersion);
	}

	@Override
	public AppPhoneManage obtainLastAppPhoneManage() {
		return appPhoneManageDao.loadLastAppPhoneManage();
	}

}
