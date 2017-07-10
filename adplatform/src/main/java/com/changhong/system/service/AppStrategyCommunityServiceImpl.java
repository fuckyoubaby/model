package com.changhong.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.changhong.system.domain.app.AppStrategyBox;
import com.changhong.system.domain.app.AppStrategyCommunity;
import com.changhong.system.repository.AppStrategyCommunityDao;
import com.changhong.system.web.facade.assember.AppStrategyCommunityWebAssember;
import com.changhong.system.web.facade.dto.AppStrategyCommunityDTO;

/**
 * @author yujiawei
 * @date 2017年2月10日
 * @time 上午9:23:28
 */
@Service("appStrategyCommunityService")
public class AppStrategyCommunityServiceImpl implements AppStrategyCommunityService {

	@Autowired
	private AppStrategyCommunityDao appStrategyCommunityDao;
	
	@Override
	public void saveCommunityList(AppStrategyCommunityDTO appStrategyCommunityDTO) {
		AppStrategyCommunity appStrategyCommunity = AppStrategyCommunityWebAssember.toAppStrategyCommunityDomain(appStrategyCommunityDTO); 
        appStrategyCommunityDao.persist(appStrategyCommunity);
	}

	@Override
	public List<AppStrategyCommunity> obtainCommunityList(String strategyUuid) {
		List<AppStrategyCommunity> lists= new ArrayList<AppStrategyCommunity>();
		if(StringUtils.hasText(strategyUuid)){
			lists = appStrategyCommunityDao.getCommunityList(strategyUuid);
		}
		return lists;
	}

	@Override
	public void deleteCommunity(String strategyUuid) {
		appStrategyCommunityDao.deleteCommunity(strategyUuid);
	}


	@Override
	public List<AppStrategyCommunityDTO> obtainCommunityDTOsByCommunityId(
			String communityId) {
		List<AppStrategyCommunity> list = appStrategyCommunityDao.getCommunitiesByCommunityId(communityId);
		return AppStrategyCommunityWebAssember.toAppStrategyCommunityDTOList(list);
	}


}
