package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.advertisment.AdverResource;
import com.changhong.system.domain.advertisment.PlayItem;
import com.changhong.system.domain.advertisment.PlayItemContent;
import com.changhong.system.web.facade.dto.PlayItemContentDTO;

/**
 * 
 * Author: Guo xiaoyang 
 * Date: 2017-1-17 
 * Time: 17:33:57
 */
public class PlayItemContentWebAssember {

	
	public static PlayItemContent toDomain(PlayItemContentDTO dto){
		if(dto == null) return null;
		String uuid = dto.getUuid();
		String playItemUuid = dto.getPlayItemUuid();
		String resourceUuid = dto.getResourceUuid();
		PlayItemContent pic = null;
		
		if(StringUtils.hasText(uuid)){
			//更新后保存转换,index 单独写service方法更新
			pic = (PlayItemContent) EntityLoadHolder.getUserDao().findByUuid(uuid, PlayItemContent.class);
			pic.setRepeat(dto.getRepeat());
			pic.setDuration(dto.getDuration());
			
		}else{
			//新增转换
			if(!StringUtils.hasText(resourceUuid) || !StringUtils.hasText(playItemUuid)){
				return null;
			}else{
				PlayItem pi = (PlayItem) EntityLoadHolder.getUserDao().findByUuid(playItemUuid, PlayItem.class);
				pic = new PlayItemContent(resourceUuid, dto.getIndex(), dto.getRepeat(), dto.getDuration(), pi);
			}
		}
		return pic;
	}
	
	public static PlayItemContentDTO toDTO(PlayItemContent pic){
		if(pic == null) return null;
		PlayItemContentDTO dto = null;
		String resourceUuid = pic.getResourceUuid();
		dto = new PlayItemContentDTO(pic.getUuid(), pic.getIndex(), pic.getRepeat(), pic.getDuration(), pic.getPlayItem().getUuid(), resourceUuid);
		AdverResource  ar = (AdverResource) EntityLoadHolder.getUserDao().findByUuid(resourceUuid, AdverResource.class);
		dto.setAdverResourceDTO(AdverResourceWebAssember.toDTO(ar));
		return dto;
	}
	
	public static List<PlayItemContentDTO> toDTOList(List<PlayItemContent> playItemContents){
		List<PlayItemContentDTO> lists = new ArrayList<PlayItemContentDTO>();
		if(playItemContents!=null){
			for(PlayItemContent pic: playItemContents){
				lists.add(toDTO(pic));
			}
		}
		return lists;
	}
}
