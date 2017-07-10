package com.changhong.system.service;

import java.util.List;

import com.changhong.system.web.facade.dto.PlayItemDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-16 
 * Time: 09:48:08
 *
 */
public interface PlayItemService {
	
	PlayItemDTO otainByUuid(String uuid);
	
	List<PlayItemDTO> obtainEnableItems(String keyword, int startPosition, int size);
	int obtainEnableItemsAmount(String keyword);

	boolean obtainNameExists(String uuid, String name);

	void saveDTO(PlayItemDTO playItemDTO);

	void updateDTO(PlayItemDTO pi);

	void changeIndex(String preUuid, int preIndex);

	void cancelNull(String uuid);

	void deleteItem(String uuid);
	
}
