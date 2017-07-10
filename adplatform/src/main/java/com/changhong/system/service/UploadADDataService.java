package com.changhong.system.service;

import java.util.Date;
import java.util.List;

import com.changhong.system.domain.uploaddata.UploadADData;
import com.changhong.system.web.facade.dto.AdDataDTO;
import com.changhong.system.web.facade.dto.BoxDTO;

/**
 * 
 * @author shijinxiang
 * 2017年1月9日
 * 下午2:13:12
 */
public interface UploadADDataService {

	void saveADData(UploadADData uploadADData);
	void deleteADData(UploadADData uploadADData);
	void updateADData(UploadADData uploadADData);
	
	void saveADDate(List<UploadADData> datas);
	
	/**
	 * 根据地区iD分页查询所有的盒子
	 * @param areaId
	 * @param offset
	 * @param length
	 * @return
	 */
	List<BoxDTO> obtainBoxDTOsByAreaId(String areaId,int offset,int length);
	
	int obtainCountByAreaId(String areaId);
	
	List<AdDataDTO> obtainAdDataDTOs(String mac,Date startDate,Date endDate);
}
