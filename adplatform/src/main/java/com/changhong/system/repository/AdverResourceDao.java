package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.advertisment.AdverResource;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2016-12-9
 * Time: 09:39:20
 */
public interface AdverResourceDao extends EntityObjectDao{

	boolean obtainNameExist(String arUuid, String name);
	
	List<AdverResource> loadAdverResources(final String name, final int startPosition,
			final int pageSize);

	int loadAdverResourceSize(String name);
	
	List<AdverResource> loadEnableAdverResources(final String name, final int startPosition,
			final int pageSize);

	int loadEnableAdverResourceSize(String name);

	List loadRecordResourceInfo();

	List<String> loadFileNamesByPath(String keyword);

	double loadRecordSize(List<String> garbageRecordes);

	int deleteByRecords(List<String> garbageRecordes);
	
}
