package com.changhong.system.service;

import java.util.List;

import com.changhong.common.domain.ResultObject;
import com.changhong.system.domain.box.BoxMac;
import com.changhong.system.domain.box.BoxMacStatus;
import com.changhong.system.web.facade.dto.BoxMacDTO;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-15 
 * Time: 15:55:57
 * 
 */
public interface BoxMacService {
	
	BoxMacDTO obtainBoxMacDTO(String uuid);
	
	void saveBoxMacDTO(BoxMacDTO dto);
	
	void saveBoxMac(BoxMac boxMac);
	
	void saveBoxMacs(List<BoxMac> boxmacs);
	
	/**
	 * 返回的是批量添加后与数据库重复的MAC 记录数
	 * @return
	 */
	int deleteBatchImport();
	
	boolean obtainMacExist(String mac);
	
	boolean obtainMacExist(String mac,String uuid);
	
	List<BoxMacDTO> obtainBoxMacs(String filterName, int startPosition, int pageSize);
	
	int obtainBoxMacs(String filterName);

	void changeStatus(String boxmacUuid, BoxMacStatus bDisable);

	ResultObject obtainMacCanDeleted(String boxmacUuid);

	void deleteByUuid(String boxmacUuid);
	
	List<BoxMac> obtainEnableMac(String keyword,int startPosition, int pageSize);
	
	BoxMac obtainByMac(String mac);
}
