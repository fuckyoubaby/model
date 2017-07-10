package com.changhong.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changhong.common.utils.CHLogUtils;
import com.changhong.system.domain.upgrade.ContentUpgradeCommunity;
import com.changhong.system.repository.ContentUpgradeCommunityDao;
import com.changhong.system.web.facade.assember.ContentUpgradeCommunityWebAssember;
import com.changhong.system.web.facade.dto.ContentUpgradeCommunityDTO;

/**
 * 
 * Author: Guoxiaoyang
 * Date: 2017-2-7 
 * Time: 09:28:16
 *
 */
@Service("contentUpgradeCommunity")
public class ContentUpgradeCommunityServiceImpl implements
		ContentUpgradeCommunityService {

	@Autowired
	private ContentUpgradeCommunityDao contentUpgradeCommunityDao;
	
	@Override
	public void saveEntity(ContentUpgradeCommunity cuc) {
		if(cuc!=null){
			contentUpgradeCommunityDao.saveOrUpdate(cuc);
			if(cuc.getContentUpgrade()!=null){
				CHLogUtils.doLog(ContentUpgradeCommunityServiceImpl.class, "save new content upgrade community "+cuc.getUuid(), "配置新的小区到播放升级["+cuc.getContentUpgrade().getName()+"]");
			}else CHLogUtils.doLog(ContentUpgradeCommunityServiceImpl.class, "save new content upgrade community "+cuc.getUuid(), "配置新的小区到播放升级");
		}
	}

	@Override
	public boolean obtainEntityExisits(String communityid, String upgradeid) {
		return contentUpgradeCommunityDao.checkExists(communityid, upgradeid);
	}

	@Override
	public List<ContentUpgradeCommunityDTO> obtainUpgradeCommunitiesDTO(
			String upgradeId) {
		List<ContentUpgradeCommunity> lists = contentUpgradeCommunityDao.loadUpgradeCommunities(upgradeId);
		
		return ContentUpgradeCommunityWebAssember.toDTOList(lists);
	}

	@Override
	public List<ContentUpgradeCommunity> obtainUpgradeCommunities(
			String upgradeId) {
		return contentUpgradeCommunityDao.loadUpgradeCommunities(upgradeId);
	}

	@Override
	public void saveUpgradeCommunities(List<ContentUpgradeCommunity> communities) {
		if(communities!=null && communities.size()>0){
			contentUpgradeCommunityDao.saveAll(communities);
			if(communities.get(0)!=null && communities.get(0).getContentUpgrade()!=null)
			CHLogUtils.doLog(ContentUpgradeCommunityServiceImpl.class, "config communities to content upgrade "+communities.get(0).getContentUpgrade().getUuid(), "配置小区到播放升级["+communities.get(0).getContentUpgrade().getName()+"]");
		}
	}

	@Override
	public void deleteEntityByUpgradeId(String upgradeId) {
		contentUpgradeCommunityDao.deleteEntitiesByUpgradeId(upgradeId);
		CHLogUtils.doLog(ContentUpgradeCommunityServiceImpl.class, "remove all communities in content upgrade "+upgradeId, "清空播放升级"+upgradeId+"已配置的小区");
	}

	@Override
	public List<ContentUpgradeCommunityDTO> obtainUpgradeCommunitiesByCommunityId(
			String communityId) {
		List<ContentUpgradeCommunity> lists = contentUpgradeCommunityDao.loadUpgradeCommunitiesByCommunityId(communityId);
		return ContentUpgradeCommunityWebAssember.toDTOList(lists);
	}

}
