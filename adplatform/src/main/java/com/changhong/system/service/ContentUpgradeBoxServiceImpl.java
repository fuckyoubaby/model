package com.changhong.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhong.common.utils.CHLogUtils;
import com.changhong.system.domain.upgrade.ContentUpgradeBox;
import com.changhong.system.repository.ContentUpgradeBoxDao;
import com.changhong.system.web.facade.assember.ContentUpgradeBoxWebAssember;
import com.changhong.system.web.facade.dto.ContentUpgradeBoxDTO;
/**
 * 
 * Author: Guoxiaoyang
 * Date: 2017-2-6 
 * Time: 15:08:50
 */
@Service("contentUpgradeBoxService")
public class ContentUpgradeBoxServiceImpl implements ContentUpgradeBoxService {

	@Autowired
	private ContentUpgradeBoxDao contentUpgradeBoxDao;
	
	@Override
	public void saveDTO(ContentUpgradeBoxDTO dto) {
		ContentUpgradeBox box = ContentUpgradeBoxWebAssember.toDomain(dto);
		if(box!=null){
			contentUpgradeBoxDao.saveOrUpdate(box);
			if(box.getContentUpgrade()!=null){
				CHLogUtils.doLog(ContentUpgradeBoxServiceImpl.class, "save new content upgrade box "+box.getUuid(), "配置终端到播放升级["+box.getContentUpgrade().getName()+"]");
			}else{
				CHLogUtils.doLog(ContentUpgradeBoxServiceImpl.class, "save new content upgrade box "+box.getUuid(), "配置终端到播放升级["+dto.getUpgradeUuid()+"]");
			}
		}
	}

	@Override
	public boolean obtainEntityExists(String boxId, String upgradeId) {
		return contentUpgradeBoxDao.checkExists(boxId, upgradeId);
	}

	@Override
	public List<ContentUpgradeBoxDTO> obtainUpgradeBoxesDTO(String upgradeId) {
		List<ContentUpgradeBox> lists = contentUpgradeBoxDao.loadUpgradeBoxes(upgradeId);
		return ContentUpgradeBoxWebAssember.toDTOLists(lists);
	}

	@Override
	public void saveUpgradeBoxes(List<ContentUpgradeBox> boxes) {
		if(boxes!=null && boxes.size()>0){
			contentUpgradeBoxDao.saveAll(boxes);
			if(boxes.get(0)!=null && boxes.get(0).getContentUpgrade()!=null){
				CHLogUtils.doLog(ContentUpgradeBoxServiceImpl.class, "config boxes to content upgrade "+boxes.get(0).getContentUpgrade().getUuid(), "配置终端到播放升级["+boxes.get(0).getContentUpgrade().getName()+"]");
			}
		}
		
	}

	@Override
	public void deleteBoxByUpgradeId(String upgradeId) {
		contentUpgradeBoxDao.deleteBoxByUpgradeId(upgradeId);
		CHLogUtils.doLog(ContentUpgradeBoxServiceImpl.class, "remove boxes from content upgrade "+upgradeId, "移除播放升级["+upgradeId+"]的所有终端配置");
	}

	@Override
	public List<ContentUpgradeBox> obtainUpgradeBoxes(String upgradeId) {
		return contentUpgradeBoxDao.loadUpgradeBoxes(upgradeId);
	}

	@Override
	public List<ContentUpgradeBoxDTO> obtainUpgradeBoxesByBoxId(String boxId) {
		List<ContentUpgradeBox> lists = contentUpgradeBoxDao.loadUpgradeBoxesByBoxId(boxId);
		return ContentUpgradeBoxWebAssember.toDTOLists(lists);
	}

}
