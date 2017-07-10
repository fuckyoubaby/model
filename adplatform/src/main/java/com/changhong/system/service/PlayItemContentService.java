package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.advertisment.PlayItemContent;
import com.changhong.system.web.facade.dto.PlayItemContentDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-16 
 * Time: 09:49:01
 */
public interface PlayItemContentService {

	List<PlayItemContentDTO> obtainContentByItemUuid(String itemUuid);

	String saveDTO(PlayItemContentDTO dto);

	void deleteByUuid(String uuid);

	PlayItemContentDTO obtainItemContentDTOByUuid(String uuid);
	PlayItemContent obtainItemContentByUuid(String uuid);

	void changeIndexByItemUuid(String itemUuid, int index);
	void changeIndexByUuid(String uuid, int index);

	void updatePlayConfigInfo(String uuid, int repeat, double duration);
	
}
