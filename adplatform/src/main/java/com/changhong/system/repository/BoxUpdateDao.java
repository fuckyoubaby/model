package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.box.Box;
/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-20 
 * Time: 14:24:44
 */
public interface BoxUpdateDao extends EntityObjectDao{

	public List<Box> findBoxByCommunityAndPage(String community, int startPosition, int size);
	
	int findBoxAmountByCommunity(String community);
	
	
	public List<Box> findBoxByMacPage(String mac, int startPosition, int size);
	int loadBoxAmountByMac(String mac);
}
