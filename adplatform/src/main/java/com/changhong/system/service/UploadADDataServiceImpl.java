package com.changhong.system.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.uploaddata.UploadADData;
import com.changhong.system.repository.BoxInfoDao;
import com.changhong.system.repository.CommunityDao;
import com.changhong.system.repository.UploadADDataDao;
import com.changhong.system.web.facade.assember.AdDataWebAssember;
import com.changhong.system.web.facade.assember.BoxWebAssember;
import com.changhong.system.web.facade.dto.AdDataDTO;
import com.changhong.system.web.facade.dto.BoxDTO;

/**
 * 
 * @author shijinxiang
 * 2017年1月9日
 * 下午2:25:11
 */
@Service("uploadADDataService")
public class UploadADDataServiceImpl implements UploadADDataService{

	@Autowired
	private UploadADDataDao uploadADDataDao;
	
	@Override
	public void saveADData(UploadADData uploadADData) {
		uploadADDataDao.saveOrUpdate(uploadADData);
	}

	@Override
	public void deleteADData(UploadADData uploadADData) {
		uploadADDataDao.delete(uploadADData);
	}

	@Override
	public void updateADData(UploadADData uploadADData) {
		uploadADDataDao.saveOrUpdate(uploadADData);
	}

	@Override
	public List<BoxDTO> obtainBoxDTOsByAreaId(String areaId, int offset,
			int length) {
		List<Box> list = uploadADDataDao.loadBoxsByAreaId(areaId, offset, length);
		return BoxWebAssember.toBoxDTOList(list, true);
	}

	@Override
	public int obtainCountByAreaId(String areaId) {
		// TODO Auto-generated method stub
		return uploadADDataDao.getCountByAreaId(areaId);
	}

	@Override
	public List<AdDataDTO> obtainAdDataDTOs(String mac, Date startDate,
			Date endDate) {
		List<UploadADData> list = uploadADDataDao.loadAdDatas(mac, startDate, endDate);
		return AdDataWebAssember.toAdDataDTOs(list);
	}

	@Override
	public void saveADDate(List<UploadADData> datas) {
		if(datas!=null && datas.size()>0){
			uploadADDataDao.saveAll(datas);
		}
	}

}
