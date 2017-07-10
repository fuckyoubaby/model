package com.changhong.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhong.system.domain.box.Area;
import com.changhong.system.repository.AreaDao;
import com.changhong.system.web.facade.assember.AreaWebAssember;
import com.changhong.system.web.facade.dto.AreaDTO;

@Service("areaConfigService")
public class AreaConfigServiceImpl implements AreaConfigService {
	@Autowired
	private AreaDao areaDao;
	
	@Override
	public List<AreaDTO> obtainAllArea() {
		List<Area> aList = areaDao.loadAllArea();
		return AreaWebAssember.toAreaDTOList(aList);
	}

}
