package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.box.BoxMac;
/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-15 
 * Time: 13:31:52
 *
 */
public interface BoxMacDao extends EntityObjectDao{
	
	boolean findBoxMacDuplicate(String mac);
	boolean findBoxMacDuplicate(String mac, String uuid);
	
	BoxMac findByMac(String mac);
	
	List<BoxMac> findMacs(String keyword, int startPosition, int pageSize);
	int findMacsAmount(String keyword);
	
	List<BoxMac> findMacs(String keyword, String status,int startPosition, int pageSize);
	int findMacsAmount(String keyword, String status);
	
	List<BoxMac> findMacs(String keyword, String status,int startPosition, int pageSize, String propertyName);

	int deleteDuplicateMacs();
}
