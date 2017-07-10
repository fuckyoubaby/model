package com.changhong.system.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.changhong.common.utils.CHLogUtils;
import com.changhong.system.domain.advertisment.PlayContent;
import com.changhong.system.domain.upgrade.ContentUpgrade;
import com.changhong.system.domain.upgrade.UpgradeEntity;
import com.changhong.system.repository.ContentUpgradeAreaDao;
import com.changhong.system.repository.ContentUpgradeBoxDao;
import com.changhong.system.repository.ContentUpgradeCommunityDao;
import com.changhong.system.repository.ContentUpgradeDao;
import com.changhong.system.repository.PlayContentDao;
import com.changhong.system.web.facade.assember.ContentUpgradeWebAssember;
import com.changhong.system.web.facade.dto.ContentUpgradeDTO;


/**
 * Author: Guo xiaoyang
 * Date: 2017-2-2 
 * Time: 12:07:42
 *
 */
@Service("contentUpgradeService")
public class ContentUpgradeServiceImpl implements ContentUpgradeService{

	@Autowired
	private ContentUpgradeDao contentUpgradeDao;
	@Autowired
	private PlayContentDao playContentDao;
	
	@Autowired
	private ContentUpgradeAreaDao contentUpgradeAreaDao;
	
	@Autowired
	private ContentUpgradeCommunityDao contentUpgradeCommunityDao;
	
	@Autowired
	private ContentUpgradeBoxDao contentUpgradeBoxDao;
	
	
	@Override
	public ContentUpgradeDTO obtainDTOByUuid(String uuid) {
		
		ContentUpgrade cu = (ContentUpgrade) contentUpgradeDao.findByUuid(uuid, ContentUpgrade.class);
		return ContentUpgradeWebAssember.toDTO(cu);
	}

	@Override
	public boolean obtainNameExists(String uuid, String name) {
		
		if(StringUtils.hasText(uuid)){
			return contentUpgradeDao.obtainNameExists(name, uuid); 
		}else{
			return contentUpgradeDao.obtainNameExists(name);
		}
		
	}

	@Override
	public void saveDTO(ContentUpgradeDTO contentUpgradeDTO) {
		ContentUpgrade cu = ContentUpgradeWebAssember.toDomainAsSave(contentUpgradeDTO);
		if(cu!=null){
			contentUpgradeDao.saveOrUpdate(cu);	
			CHLogUtils.doLog(ContentUpgradeServiceImpl.class, "save content upgrade "+cu.getUuid(), "保存播放升级["+contentUpgradeDTO.getName()+"]");
		}
	}

	@Override
	public List<ContentUpgradeDTO> obtainContentUpgradeDTOs(String filterName,
			int startPosition, int pageSize) {
		List<ContentUpgrade> lists = contentUpgradeDao.loadContentUpgrades(filterName, startPosition, pageSize);
		return ContentUpgradeWebAssember.toDTOList(lists);
	}

	@Override
	public int obtainAmount(String filterName) {
		return contentUpgradeDao.loadAmount(filterName);
	}

	@Override
	public void changeStatus(String contentUpgradeId) {
		ContentUpgrade cu = (ContentUpgrade) contentUpgradeDao.findByUuid(contentUpgradeId, ContentUpgrade.class);
		if(cu!=null){
			cu.setEnable(!cu.isEnable());
			CHLogUtils.doLog(ContentUpgradeServiceImpl.class, "update content upgrade "+cu.getUuid()+" status ", "修改播放升级["+cu.getName()+"]的状态");
		}
	}

	@Override
	public void handlePublish(String contentUpgradeId) {
		ContentUpgrade cu = (ContentUpgrade) contentUpgradeDao.findByUuid(contentUpgradeId, ContentUpgrade.class);
		if(cu!=null && cu.getPublishTime()==null){
			cu.setPublishTime(new Date());
			CHLogUtils.doLog(ContentUpgradeServiceImpl.class, "publish content upgrade "+cu.getUuid(), "发布播放升级["+cu.getName()+"]");
		}
		
	}

	@Override
	public void deleteByUuid(String contentUpgradeId) {
		ContentUpgrade cu = (ContentUpgrade) contentUpgradeDao.findByUuid(contentUpgradeId, ContentUpgrade.class);
		if(cu!=null){
			contentUpgradeDao.delete(cu);
			CHLogUtils.doLog(ContentUpgradeServiceImpl.class, "delete content upgrade "+cu.getUuid(), "删除播放升级["+cu.getName()+"]");
		}
	}

	@Override
	public void updateNewContent(String upgradeUuid, String newContentId,
			String oldContentId) {
		ContentUpgrade cu = (ContentUpgrade) contentUpgradeDao.findByUuid(upgradeUuid, ContentUpgrade.class);
		PlayContent newPlayContent = (PlayContent) playContentDao.findByUuid(newContentId, PlayContent.class);
		if(cu==null) return;
		PlayContent temp = cu.getPlayContent();
		if(temp !=null){
			if(temp.getUuid().equals(oldContentId)){
				playContentDao.updateUpgrade(oldContentId);
			}
		}
	
		if(newPlayContent != null && newPlayContent.getContentUpgrade() == null){
			newPlayContent.setContentUpgrade(cu);
			
			CHLogUtils.doLog(ContentUpgradeServiceImpl.class, "set play content "+newContentId+" to content upgrade "+cu.getUuid(), "修改播放升级["+cu.getName()+"]的播放内容为["+newPlayContent.getName()+"]");
		}
		
	}

	@Override
	public ContentUpgrade obtainEntityByUuid(String contentUpgradeId) {
		
		return (ContentUpgrade) contentUpgradeDao.findByUuid(contentUpgradeId, ContentUpgrade.class);
	}

	@Override
	public List<ContentUpgrade> obtainContentUpgrades() {
		return contentUpgradeDao.loadAllContentUpgrades();
	}

	@Override
	public String obtainLatestUpgradeUUIDByBox(String areaId,
			String communityId, String boxId) {
		UpgradeEntity ue1 = null;
		UpgradeEntity ue2 = null;
		UpgradeEntity ue3 = null;
		if(StringUtils.hasText(areaId)){
			ue1 = contentUpgradeAreaDao.loadLatestUpgradeEntityByAreaId(areaId);
		}
		if(StringUtils.hasText(communityId)){
			ue2 = contentUpgradeCommunityDao.loadLatestUpgradeByCommunityId(communityId);
		}
		if(StringUtils.hasText(boxId)){
			ue3 = contentUpgradeBoxDao.loadLatestUpgradeByBoxId(boxId);
		}
		
		List<UpgradeEntity> lists = new ArrayList<UpgradeEntity>();
		if(ue1!=null) lists.add(ue1);
		if(ue2!=null) lists.add(ue2);
		if(ue3!=null) lists.add(ue3);
		if(lists.size()==0) return null;
		if(lists.size()==1) return lists.get(0).getUuid();
		
		Collections.sort(lists, new Comparator<UpgradeEntity>(){

			@Override
			public int compare(UpgradeEntity o1, UpgradeEntity o2) {
				Date d1 = o1.getPublishTime();
				Date d2 = o2.getPublishTime();
				String s1 = o1.getUuid();
				String s2 = o2.getUuid();
				if(d1==null && d2!=null){
					return -1;
				}else if(d2==null && d1 != null){
					return 1;
				}else if(d1==null && d2==null){
					return s1.compareTo(s2);
					//比较uuid
				}else{
					//比较publishTime
					int b = d1.compareTo(d2);
					if(b==0){
						return s1.compareTo(s2);
					}else return b;
				}
			}
		});
		
		
		return lists.get(lists.size()-1).getUuid();
	}

}
