package com.changhong.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.changhong.system.domain.app.AppStrategyBox;
import com.changhong.system.repository.AppStrategyBoxDao;
import com.changhong.system.web.facade.assember.AppStrategyBoxWebAssember;
import com.changhong.system.web.facade.dto.AppStrategyBoxDTO;

/**
 * @author yujiawei
 * @date 2017年2月10日
 * @time 上午9:23:01
 */
@Service("appStrategyBoxService")
public class AppStrategyBoxServiceImpl implements AppStrategyBoxService {

	@Autowired
	private AppStrategyBoxDao appStrategyBoxDao;
	
	@Override
	public void saveBoxList(AppStrategyBoxDTO appStrategyBoxDTO) {
      AppStrategyBox appStrategyBox = AppStrategyBoxWebAssember.toAppStrategyBoxDomain(appStrategyBoxDTO); 
	  appStrategyBoxDao.persist(appStrategyBox);
	}

	@Override
	public List<AppStrategyBox> obtainMacList(String strategyUuid) {
		List<AppStrategyBox> listMac = new ArrayList<AppStrategyBox>();
		if(StringUtils.hasText(strategyUuid)){
			listMac = appStrategyBoxDao.getMacList(strategyUuid);
		}
		return listMac;
	}

	@Override
	public void deleteBoxMac(String strategyUuid) {
		appStrategyBoxDao.deleteBoxMac(strategyUuid);
	}

	@Override
	public List<AppStrategyBoxDTO> obtainAppStrategyBoxDTOsByMac(String mac) {
		List<AppStrategyBox> list = appStrategyBoxDao.getStrategyBoxsByMac(mac);
		return AppStrategyBoxWebAssember.toAppStrategyBoxDTOList(list);
	}

}
