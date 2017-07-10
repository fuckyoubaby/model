package com.changhong.system.service;

import com.changhong.system.web.facade.dto.BoxDTO;
import com.changhong.system.web.facade.dto.CommunityDTO;

import java.util.List;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 19:52
 */
public interface CommunityService {

    List<CommunityDTO> obtainCommunities(String areaUuid, String name, int startPosition, int pageSize);

    int obtainCommunitySize(String areaUuid, String name);

    CommunityDTO obtainCommunityByUuid(String communityUuid, boolean loadMetaData);

    boolean saveOrUpdateCommunity(CommunityDTO communityDTO);

    void saveCommunityMetaData(String communityUuid, String metaDataUuid);
}
