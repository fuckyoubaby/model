package com.changhong.system.service;

import java.util.List;

import com.changhong.common.domain.RecordeResourceInfo;
import com.changhong.system.domain.advertisment.AdverResource;
import com.changhong.system.web.facade.dto.AdverResourceDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2016-12-9
 * Time: 10:17:16
 */
public interface AdverResourceService {
	
	public void saveAdverResource(AdverResource ar);
	public void saveAdverResourceDTO(AdverResourceDTO dto);
	public void deleteByUUID(String uuid);
	public void updateAdverResourceDTO(AdverResourceDTO dto);
	public AdverResourceDTO obtainDTOByUuid(String uuid);
	
	void changeStatus(String uuid);
	
	boolean obtainAdverResourceExist(String uuid, String name);
	
	List<AdverResourceDTO> obtainAdverResources(String filterName,
			int startPosition, int pageSize);
	
	int obtainAdverResourceAmount(String filterName);
	
	List<AdverResourceDTO> obtainEnableAdverResources(String filterName,
			int startPosition, int pageSize);
	
	int obtainEnableAdverResourceAmount(String filterName);
	public List<RecordeResourceInfo> obtainRecordResourceInfo();
	public List<String> obtainFileNamesByPath(String keyword);
	public double obtainRecordPathSize(List<String> garbageRecordes);
	public int deleteByGarbageRecordes(List<String> garbageRecordes);
	
	AdverResource obtainAdverResourceByUuid(String uuid);
	
}
