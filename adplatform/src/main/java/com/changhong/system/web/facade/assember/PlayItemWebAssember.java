package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.advertisment.PlayContent;
import com.changhong.system.domain.advertisment.PlayItem;
import com.changhong.system.web.facade.dto.PlayItemDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-16 
 * Time: 09:53:56
 */
public class PlayItemWebAssember {

	public static PlayItem toDomain(PlayItemDTO playItemDTO){
		if(playItemDTO == null) return null;
		PlayItem item = null;
		String uuid = playItemDTO.getUuid();
		String name = playItemDTO.getName();
		String description = playItemDTO.getDescription();
		
		String playContentId = playItemDTO.getPlayContentUuid();
		
		if(StringUtils.hasText(uuid)){
			item = (PlayItem) EntityLoadHolder.getUserDao().findByUuid(uuid, PlayItem.class);
			item.setUuid(uuid);
			item.setName(name);
			item.setDescription(description);
			
			if(StringUtils.hasText(playContentId)){
				PlayContent playContent = (PlayContent) EntityLoadHolder.getUserDao().findByUuid(playContentId, PlayContent.class);
				item.setPlayContent(playContent);
				item.setEndDate(playItemDTO.getEndDate());
				item.setStartDate(playItemDTO.getStartDate());
				item.setIndex(playItemDTO.getIndex());
				item.setRepeat(playItemDTO.getRepeat());
			}
		}else{
			item = new PlayItem(name, description);
		}
		return item;
	}
	
	public static PlayItemDTO toDTO(PlayItem playItem ){
		if(playItem == null) return null;
		PlayContent pc = playItem.getPlayContent();
		PlayItemDTO dto = null;
		String uuid = playItem.getUuid();
		String name = playItem.getName();
		String description = playItem.getDescription();
		int amount = playItem.getAmount();

		dto = new PlayItemDTO(uuid, name, description);
		dto.setAmount(amount);
		if(pc != null  ){
			if(StringUtils.hasText(pc.getUuid())){
				dto.setIndex(playItem.getIndex());
				dto.setStartDate(playItem.getStartDate());
				dto.setEndDate(playItem.getEndDate());
				dto.setRepeat(playItem.getRepeat());
				dto.setPlayContentUuid(pc.getUuid());
			}
		}
		
		return dto;
	}
	
	public static List<PlayItemDTO> toDTOList(List<PlayItem> piList){
		List<PlayItemDTO> lists = new ArrayList<PlayItemDTO>();
		if(piList!=null){
			for(PlayItem pi: piList){
				lists.add(toDTO(pi));
			}
		}
		
		return lists;
	}
}
