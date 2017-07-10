package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.advertisment.PlayItemContent;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-11 
 * Time: 18:12:10
 *
 */
public interface PlayItemContentDao extends EntityObjectDao {
	
	List<PlayItemContent> loadByItemid(String itemUuid);

	void changeIndexByItemUuid(String itemUuid, int index);

	void changeIndexByUuid(String uuid, int index);
	
	List<PlayItemContent> loadPlayItemByContentResourceId(String contentResourceId);

}
