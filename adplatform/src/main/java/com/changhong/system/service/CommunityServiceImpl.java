package com.changhong.system.service;

import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.common.utils.CHListUtils;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.domain.box.Community;
import com.changhong.system.domain.log.ActionModule;
import com.changhong.system.domain.log.ActionType;
import com.changhong.system.domain.meta.MetaData;
import com.changhong.system.domain.user.User;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.repository.CommunityDao;
import com.changhong.system.web.event.ActionLogEvent;
import com.changhong.system.web.facade.assember.CommunityWebAssember;
import com.changhong.system.web.facade.dto.BoxDTO;
import com.changhong.system.web.facade.dto.CommunityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 20:00
 */
@Service("communityService")
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityDao communityDao;

    @Override
    public List<CommunityDTO> obtainCommunities(String areaUuid, String name, int startPosition, int pageSize) {
        List<Community> communities = communityDao.loadCommunities(areaUuid, name, startPosition - 1, pageSize);
        if (CHListUtils.hasElement(communities)) {
            return CommunityWebAssember.toCommunityDTOList(communities);
        }
        return null;
    }

    @Override
    public int obtainCommunitySize(String areaUuid, String name) {
        return communityDao.loadCommunitySize(areaUuid, name);
    }

    @Override
    public CommunityDTO obtainCommunityByUuid(String communityUuid, boolean loadMetaData) {
        Community community  = (Community) communityDao.findByUuid(communityUuid, Community.class);
        if (community != null) {
            return CommunityWebAssember.toCommunityDTO(community, loadMetaData);
        }
        return null;
    }

    @Override
    public boolean saveOrUpdateCommunity(CommunityDTO communityDTO) {
        Community community = CommunityWebAssember.toCommunityDomain(communityDTO);
        if (community != null) {
            communityDao.saveOrUpdate(community);

            //日志部分
            ActionLogEvent event = null;
            User current = SecurityUtils.currentUser();
            if (StringUtils.hasText(communityDTO.getUuid())) {
                ApplicationLog.infoWithCurrentUser(CommunityServiceImpl.class, "change community " + communityDTO.getUuid());
                event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_AD, "更新小区" + communityDTO.getName() + "信息");
            } else {
                ApplicationLog.infoWithCurrentUser(CommunityServiceImpl.class, "add community " + community.getUuid());
                event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_AD, "修改小区" + communityDTO.getName() + "信息");
            }
            ApplicationThreadPool.executeThread(event);

            return true;
        }
        return false;
    }

    @Override
    public void saveCommunityMetaData(String communityUuid, String metaDataUuid) {
        Community community  = (Community) communityDao.findByUuid(communityUuid, Community.class);
        MetaData metaData  = (MetaData) communityDao.findByUuid(metaDataUuid, MetaData.class);

        community.setMetaData(metaData);
        communityDao.saveOrUpdate(metaData);

        //日志部分
        ApplicationLog.infoWithCurrentUser(CommunityServiceImpl.class, "change community meta data  " + metaDataUuid);
        User current = SecurityUtils.currentUser();
        ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_AD, "修改小区" + community.getName() + "元数据配置" + metaData.getNote());
        ApplicationThreadPool.executeThread(event);
    }
}