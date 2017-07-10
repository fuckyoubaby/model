package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.advertisment.AdverResource;
import com.changhong.system.web.facade.dto.AdverResourceDTO;

/**
 * 
 * Author: 郭潇阳
 * Date: 2016-12-8
 * Time: 16:48:21
 */
public class AdverResourceWebAssember {
	
	public static AdverResource toDomain(AdverResourceDTO adverResourceDTO){
		AdverResource adResource = null;
		if(adverResourceDTO == null) return null;
		String name = adverResourceDTO.getName();
		if(!StringUtils.hasText(name)){
			return null;
		}
		String uuid = adverResourceDTO.getUuid();
		String type = adverResourceDTO.getType();
		String path = adverResourceDTO.getPath();
		String advertiser = adverResourceDTO.getAdvertiser();
		String agents = adverResourceDTO.getAgents();
		double size = adverResourceDTO.getSize();
		
		if(StringUtils.hasText(uuid)){
			adResource = (AdverResource) EntityLoadHolder.getUserDao().findByUuid(uuid, AdverResource.class);
			adResource.setName(name);
			adResource.setPath(path);
			adResource.setType(type);
			if(StringUtils.hasText(advertiser)){
				adResource.setAdvertiser(advertiser);
			}
			if(StringUtils.hasText(agents)){
				adResource.setAgents(agents);;
			}
			if(size>0){
				adResource.setSize(size);
			}
		}else{
			adResource = new AdverResource(name,type,path);
			if(StringUtils.hasText(advertiser)){
				adResource.setAdvertiser(advertiser);
			}
			if(StringUtils.hasText(agents)){
				adResource.setAgents(agents);;
			}
			
			if(size>0){
				adResource.setSize(size);
			}
		}
		
		return adResource;
	}
	
	public static AdverResourceDTO toDTO(AdverResource adResource){
		if(adResource == null ) return null;
		String uuid = adResource.getUuid();
		AdverResourceDTO adResourceDTO = new AdverResourceDTO(uuid,adResource.getName(),adResource.getType(),adResource.getPath(),adResource.getSize(),adResource.isStatus());
		
		adResourceDTO.setAdvertiser(adResource.getAdvertiser());
		adResourceDTO.setAgents(adResource.getAgents());
		adResourceDTO.setTimestamp(adResource.getTimestamp());
		return adResourceDTO;
	}
	
	public static List<AdverResourceDTO> toDTOList(List<AdverResource> adverResourceList){
		List<AdverResourceDTO> lists= new ArrayList<AdverResourceDTO>();
		if(adverResourceList!=null){
			for(AdverResource adR : adverResourceList){
				lists.add(toDTO(adR));
			}
		}
		return lists;
	}
}
