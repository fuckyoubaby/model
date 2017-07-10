package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.advertisment.PlayContent;
import com.changhong.system.web.facade.dto.PlayContentDTO;
import com.changhong.system.web.facade.dto.PlayItemDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-13 
 * Time: 10:13:32
 *
 */
public interface PlayContentService {
	
	void savePlayContent(PlayContent pc);
	
	void savePlayContentDTO(PlayContentDTO dto);
	
	void deleteByUUID(String uuid);
	void updateDTO(PlayContentDTO dto);
	
	PlayContentDTO obtainDTOByUUID(String uuid);
	
	boolean obtainNameExists(String uuid, String name);
	List<PlayContentDTO> obtainPlayContentDTOs(String filterName, int startPosition, int size);
	int obtainAmount(String filterName);
	
	
	List<PlayItemDTO> obtainPlayItemByUuid(String uuid);

	PlayContentDTO obtainDTOByUpgradeId(String uuid);

	List<PlayContentDTO> obtainEnablePlayContentDTOs(String keyword,
			Integer indexNo, Integer pageSize);
	
	int obtainEnablePlayContentDTOsAmount(String keyword);
	
	List<PlayContentDTO> obtainPlayContentDTOsByContentResourceId(String contentResourceId);
	
}
