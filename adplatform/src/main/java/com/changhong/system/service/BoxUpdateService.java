package com.changhong.system.service;

import java.util.List;

import com.changhong.system.web.facade.dto.BoxDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-20 
 * Time: 15:28:11
 */
public interface BoxUpdateService {

	
	List<BoxDTO> obtainBoxByCommunityAndPage(String community, int startPosition, int size);
	
	int obtainBoxAmountByCommunity(String community);
	
	
	List<BoxDTO> obtainBoxByMacPage(String mac, int startPosition, int size);
	int obtainBoxAmountByMacPage(String mac);
}
