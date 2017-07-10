package com.changhong.system.web.facade.assember;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.upgrade.ContentUpgrade;
import com.changhong.system.domain.upgrade.ContentUpgradeBox;
import com.changhong.system.web.facade.dto.ContentUpgradeBoxDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-2-6 
 * Time: 14:32:54
 */
public class ContentUpgradeBoxWebAssember {

	public static ContentUpgradeBox toDomain(ContentUpgradeBoxDTO dto){
		if(dto == null) return null;
		String uuid = dto.getUuid();
		
		String boxId = dto.getBox().getUuid();
		String upgradeId = dto.getUpgradeUuid();
		ContentUpgradeBox box = null;
		ContentUpgrade cu = (ContentUpgrade) EntityLoadHolder.getUserDao().findByUuid(upgradeId, ContentUpgrade.class);
		if(cu==null) return null;
		
		if(!StringUtils.hasText(uuid)){
			box = new ContentUpgradeBox(boxId, cu);
		}else{
			box = (ContentUpgradeBox) EntityLoadHolder.getUserDao().findByUuid(uuid, ContentUpgradeBox.class);
			box.setBoxid(boxId);
			box.setContentUpgrade(cu);
		}
		
		return box;
	}
	
	public static ContentUpgradeBoxDTO toDTO(ContentUpgradeBox cuBox){
		if(cuBox == null) return null;
		
		ContentUpgrade cu = cuBox.getContentUpgrade();
		if(cu==null) return null;
		
		String boxId = cuBox.getBoxid();
		Box box = null;
		if(StringUtils.hasText(boxId)){
			box = (Box) EntityLoadHolder.getUserDao().findByUuid(boxId, Box.class);
		}else return null;
		if(box == null) return null;
		
		String uuid = cuBox.getUuid();
		if(!StringUtils.hasText(uuid)) return null;
		
		ContentUpgradeBoxDTO dto = new ContentUpgradeBoxDTO(cuBox.getUuid(), BoxWebAssember.toBoxDTO(box, false), cu.getUuid());
		
		return dto;
	}
	
	public static List<ContentUpgradeBoxDTO> toDTOLists(List<ContentUpgradeBox> boxes){
		List<ContentUpgradeBoxDTO> dtos = new ArrayList<ContentUpgradeBoxDTO>();
		
		if(boxes!=null){
			for(ContentUpgradeBox box: boxes){
				ContentUpgradeBoxDTO temp = toDTO(box);
				// 判断盒子是否是无效的
				if(temp!=null){
					dtos.add(temp);
				}else{
					if(box!=null){
						if(StringUtils.hasText(box.getBoxid())){
							EntityLoadHolder.getContentUpgradeBoxDao().deleteUpgradeBoxesByBoxId(box.getBoxid());;
						}
					}
				}
			}
		}
		return dtos;
	}
}
