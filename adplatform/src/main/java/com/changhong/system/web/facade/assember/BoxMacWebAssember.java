package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.common.utils.CHListUtils;
import com.changhong.system.domain.box.BoxMac;
import com.changhong.system.domain.box.BoxMacStatus;
import com.changhong.system.web.facade.dto.BoxMacDTO;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-16 
 * Time: 09:24:48
 *
 */
public class BoxMacWebAssember {

	
	public static BoxMacDTO toDTO(BoxMac boxmac){
		if(boxmac == null) return null;
		String uuid = boxmac.getUuid();
		if(!StringUtils.hasText(uuid)) return null;
		String status = boxmac.getMacStatus().name();
		
		BoxMacDTO dto = new BoxMacDTO(uuid, boxmac.getTimestamp(), status, boxmac.getMac());
		return dto;
	}
	
	public static BoxMac toDomain(BoxMacDTO dto){
		if(dto==null) return null;
		String uuid = dto.getUuid();
		BoxMac boxmac = null;
		if(StringUtils.hasText(uuid)){
			//更新
			boxmac = (BoxMac) EntityLoadHolder.getUserDao().findByUuid(uuid, BoxMac.class);
			//设置表单页面能修改的属性
			boxmac.setMac(dto.getMac());
			try{
				boxmac.setMacStatus(Enum.valueOf(BoxMacStatus.class, dto.getMacStatus()));
			}catch(Exception e){
				
			}
			
		}else{
			//新增
			boxmac = new BoxMac();
			boxmac.setMac(dto.getMac());
			try{
				boxmac.setMacStatus(Enum.valueOf(BoxMacStatus.class, dto.getMacStatus()));
			}catch(Exception e){
				boxmac.setMacStatus(BoxMacStatus.B_INITE);
			}
		}
		
		return boxmac;
	}
	
	public static List<BoxMacDTO> toDTOList(List<BoxMac> boxMacs){
		List<BoxMacDTO> dtos = new ArrayList<BoxMacDTO>();
		if(CHListUtils.hasElement(boxMacs)){
			for(BoxMac boxmac: boxMacs){
				dtos.add(toDTO(boxmac));
			}
		}
		return dtos;
	}
}
