package com.changhong.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.changhong.system.domain.app.AppStrategyArea;
import com.changhong.system.repository.AppStrategyAreaDao;
import com.changhong.system.web.facade.assember.AppStrategyAreaWebAssember;
import com.changhong.system.web.facade.dto.AppStrategyAreaDTO;

/**
 * @author yujiawei
 * @date 2017年2月10日
 * @time 上午9:22:34
 */
@Service("appStrategyAreaService")
public class AppStrategyAreaServiceImpl implements AppStrategyAreaService {

	@Autowired
	private AppStrategyAreaDao appStrategyAreaDao;
	
	@Override
	public void saveAreaList(AppStrategyAreaDTO appStrategyAreaDTO) {
         AppStrategyArea appStrategyArea = AppStrategyAreaWebAssember.toAppStrategyAreaDomain(appStrategyAreaDTO);
         appStrategyAreaDao.persist(appStrategyArea);
	}

	@Override
	public List<AppStrategyArea> obtainAreaList(String strategyUuid) {
		List<AppStrategyArea> listArea = new ArrayList<AppStrategyArea>();
		if(StringUtils.hasText(strategyUuid)){
			listArea = appStrategyAreaDao.obtainAreaList(strategyUuid);
		}
		return listArea;
	}

	@Override
	public void deleteArea(String strategyUuid) {
		appStrategyAreaDao.deleteArea(strategyUuid);
	}

	@Override
	public List<AppStrategyAreaDTO> obtainAppStrategyAreaDTOsByAreaId(String areaid) {
		List<AppStrategyArea> list = appStrategyAreaDao.obtainAreaListByAreaId(areaid);
		return AppStrategyAreaWebAssember.toAppStrategyAreaList(list);
	}

}
