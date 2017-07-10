package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.advertisment.PlayItem;
/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-11 
 * Time: 18:12:10
 *
 */
public interface PlayItemDao extends EntityObjectDao{
	
	boolean obtainNameExists(String piUuid,String name);
	
	List<PlayItem> loadEnableByKeyword(String keyword, int startPosition, int size);
	
	int loadEnableBykeyword(String keyword);
	
	List<PlayItem> loadByContentId(String contentUuid);

	void resortItem(String contentUuid, int index);
	
}
