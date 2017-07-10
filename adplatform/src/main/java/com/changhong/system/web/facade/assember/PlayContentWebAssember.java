package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.advertisment.PlayContent;
import com.changhong.system.web.facade.dto.PlayContentDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-12 
 * Time: 18:01:34
 */
public class PlayContentWebAssember {

	
	public static PlayContent toDomain(PlayContentDTO playContentDTO){
		PlayContent pc = null;
		if(playContentDTO == null) return null;
		String name = playContentDTO.getName();
		if(!StringUtils.hasText(name)) return null;
		String uuid = playContentDTO.getUuid();
		String version = playContentDTO.getVersion();
		Double defaultDuration = playContentDTO.getDefaultDuration();

		if(StringUtils.hasText(uuid)){
			pc = (PlayContent) EntityLoadHolder.getUserDao().findByUuid(uuid, PlayContent.class);
			pc.setName(name);
			pc.setVersion(version);
			pc.setDefaultDuration(defaultDuration);
		}else{
			pc = new PlayContent(name, version, defaultDuration);
		}
		
		return pc;
	}
	
	public static PlayContentDTO toDTO(PlayContent pc){
		if(pc==null) return null;
		PlayContentDTO pcDTO = new PlayContentDTO(pc.getUuid(), pc.getName(), pc.getVersion(), pc.getDefaultDuration());
		pcDTO.setAmount(pc.getAmount());
		pcDTO.setTimestamp(pc.getTimestamp());
		return pcDTO;
	}
	
	public static List<PlayContentDTO> toDTOList(List<PlayContent> pcList){
		List<PlayContentDTO> lists = new ArrayList<PlayContentDTO>();
		if(pcList!=null){
			for(PlayContent pc: pcList){
				lists.add(toDTO(pc));
			}
		}
		return lists;
	}
}
