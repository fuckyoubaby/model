package com.changhong.system.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import Ice.Application;

import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.domain.app.AppStrategy;
import com.changhong.system.domain.app.AppUpgradeEntity;
import com.changhong.system.domain.log.ActionModule;
import com.changhong.system.domain.log.ActionType;
import com.changhong.system.domain.user.User;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.repository.AppStrategyAreaDao;
import com.changhong.system.repository.AppStrategyBoxDao;
import com.changhong.system.repository.AppStrategyCommunityDao;
import com.changhong.system.repository.AppStrategyDao;
import com.changhong.system.web.event.ActionLogEvent;
import com.changhong.system.web.facade.assember.AppStrategyWebAssember;
import com.changhong.system.web.facade.dto.AppStrategyDTO;

/**
 * @author yujiawei
 * @date 2017年2月10日
 * @time 上午9:23:50
 */
@Service("appStrategyService")
public class AppStrategyServiceImpl implements AppStrategyService {
	
	@Autowired
	private AppStrategyDao appStrategyDao;
	@Autowired
	private AppStrategyAreaDao appStrategyAreaDao;
	@Autowired
	private AppStrategyCommunityDao appStrategyCommunityDao;
	
	@Autowired
	private AppStrategyBoxDao appStrategyBoxDao;

	@Override
	public AppStrategyDTO obtainStrategyByUuid(String Uuid) {
		AppStrategy appStrategy = (AppStrategy)appStrategyDao.findByUuid(Uuid, AppStrategy.class);
		return AppStrategyWebAssember.toAppStrategyDTO(appStrategy);
	}

	@Override
	public List<AppStrategyDTO> obtainStrategys(String strategyName,
			int startPosition, int pageSize) {
		List<AppStrategy> list = appStrategyDao.loadAppStrategy(strategyName, startPosition, pageSize);
		return AppStrategyWebAssember.toAppStrategyDTOList(list);
	}

	@Override
	public int obtainStrategySize(String strategyName) {
		return appStrategyDao.loadAppSize(strategyName);
	}

	@Override
	public boolean obtainStrategyExist(String Uuid, String strategyName) {
		return false;
	}
	
	@Override
	public void changeAppStrategyDetails(AppStrategyDTO appStrategyDTO) {
		AppStrategy appStrategy = AppStrategyWebAssember.toAppStrategyDomain(appStrategyDTO);
		appStrategyDao.persist(appStrategy);
		//日志部分
		ActionLogEvent event = null;
		User current = SecurityUtils.currentUser();
		if(StringUtils.hasText(appStrategyDTO.getUuid())){
			ApplicationLog.infoWithCurrentUser(AppStrategyServiceImpl.class, "change the strategy"+appStrategy.getUuid()+"info");
		    event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "更新app策略信息"+appStrategy.getName());
		}else{
			ApplicationLog.infoWithCurrentUser(AppStrategyServiceImpl.class, "add the strategy"+appStrategy.getUuid()+"info");
			event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "新增app策略信息"+appStrategy.getName());
		}
		ApplicationThreadPool.executeThread(event);
	}

	@Override
	public void changeStatusForAppStrategy(String strategyUuid) {
        AppStrategy appStrategy = (AppStrategy)appStrategyDao.findByUuid(strategyUuid, AppStrategy.class);
        if(appStrategy.isStrategyEnabled()){
        	appStrategy.setStrategyEnabled(false);
        }else {
			appStrategy.setStrategyEnabled(true);
		}
        //日志部分
        ApplicationLog.infoWithCurrentUser(AppStrategyServiceImpl.class, "change the strategy"+strategyUuid+"status");
        User current = SecurityUtils.currentUser();
        ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "更新策略状态"+appStrategy.getName());
        ApplicationThreadPool.executeThread(event);
	}

	@Override
	public void changeDistributeStatus(String strategyUuid) {
	    AppStrategy appStrategy = (AppStrategy)appStrategyDao.findByUuid(strategyUuid,AppStrategy.class);
		if(!appStrategy.isStrategyDistributeEnabled()){
			appStrategy.setStrategyDistributeEnabled(true);
			Date date = new Date();
			appStrategy.setTimestampDistribute(date);
		}
	}

	@Override
	public List<AppStrategy> obtainAllAppStrategties() {
		List<AppStrategy> list = appStrategyDao.loadAll();
		//return AppStrategyWebAssember.toAppStrategyDTOList(list);
		return list;
	}

	@Override
	public AppUpgradeEntity obtainLatestAppVersionByInfos(String areaId,
			String communityId, String mac) {
		AppUpgradeEntity ape1 = null;
		AppUpgradeEntity ape2 = null;
		AppUpgradeEntity ape3 = null;
		
		if(StringUtils.hasText(areaId)){
			ape1 = appStrategyAreaDao.loadLatestStrategyByAreaId(areaId);
		}
		
		if(StringUtils.hasText(communityId)){
			ape2 = appStrategyCommunityDao.loadLatestStratageByCommunityId(communityId);
		}
		
		if(StringUtils.hasText(mac)){
			ape3 = appStrategyBoxDao.loadLatestStrategyByBoxMac(mac);
		}
		
		List<AppUpgradeEntity> lists = new ArrayList<AppUpgradeEntity>();
		if(ape1!=null) lists.add(ape1);
		if(ape2!=null) lists.add(ape2);
		if(ape3!=null) lists.add(ape3);
		
		int size = lists.size();
		if(size==0) return null;
		if(size==1) return lists.get(0);
		Collections.sort(lists, new Comparator<AppUpgradeEntity>(){

			@Override
			public int compare(AppUpgradeEntity o1, AppUpgradeEntity o2) {
				Date d1 = o1.getPublishTime();
				Date d2 = o2.getPublishTime();
				
				String s1 = o1.getUuid();
				String s2 = o2.getUuid();
				if(d1==null && d2!=null){
					return -1;
				}else if(d2==null && d1 != null){
					return 1;
				}else if(d1==null && d2==null){
					s1.compareTo(s2);
					//比较uuid
				}else{
					//比较publishTime
					int b = d1.compareTo(d2);
					if(b==0){
						return s1.compareTo(s2);
					}else return b;
				}
				return 0;
			}
			
		});
		
		return lists.get(size-1);
	}
	
}
