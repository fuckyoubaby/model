package com.changhong.system.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.box.Area;
import com.changhong.system.domain.box.Community;
import com.changhong.system.domain.meta.MetaData;
import com.changhong.system.web.facade.dto.CommunityDTO;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 17:50
 */
public class CommunityWebAssember {

    public static Community toCommunityDomain(CommunityDTO communityDTO) {
        Community community = null;
        if(communityDTO == null) return null;
        String name = communityDTO.getName();
        if (!StringUtils.hasText(name)) {
            return null;
        }

        String uuid = communityDTO.getUuid();
        if (StringUtils.hasText(uuid)) {
            community = (Community) EntityLoadHolder.getUserDao().findByUuid(uuid, Community.class);
        } else {
            String areaUuid = communityDTO.getAreaUuid();
            if (StringUtils.hasText(areaUuid)) {
                community = new Community();
                Area area = new Area();
                area.setUuid(communityDTO.getAreaUuid());
                community.setArea(area);

                MetaData metaData = (MetaData) EntityLoadHolder.getMetaDao().findSystmeDefaultMetaData();
                community.setMetaData(metaData);
            }
        }

        if (community != null) {
            community.setName(name);
            community.setAbbreviation(communityDTO.getAbbreviation());
            community.setComment(communityDTO.getComment());
        }

        return community;
    }

    public static CommunityDTO toCommunityDTO(Community community, boolean loadMetaData) {
        if (community != null) {
            CommunityDTO dto = new CommunityDTO();
            dto.setUuid(community.getUuid());
            String timestamp = community.getTimestamp().toString();
            if (timestamp != null) {
                int endIndex = timestamp.indexOf(".");
                if (endIndex > 0) {
                    timestamp = timestamp.substring(0, endIndex);
                }
            }
            dto.setTimestamp(timestamp);
            dto.setName(community.getName());
            dto.setAbbreviation(community.getAbbreviation());
            dto.setComment(community.getComment());

            Area area = community.getArea();
            if (area != null) {
                dto.setAreaUuid(area.getUuid());
                dto.setAreaFullPath(area.getFullAreaPath() + "/" + community.getName());
            }

            if (loadMetaData) {
                MetaData metaData = community.getMetaData();
                if (metaData == null) {
                    metaData = EntityLoadHolder.getMetaDao().findSystmeDefaultMetaData();
                }
                dto.setMetaData(MetaDataWebAssember.toMetaDataDTO(metaData));
            }
            return dto;
        }
        return null;
    }

    public static List<CommunityDTO> toCommunityDTOList(List<Community> communities) {
        List<CommunityDTO> dtos = new ArrayList<CommunityDTO>();
        if (communities != null) {
            for (Community community : communities) {
                dtos.add(toCommunityDTO(community, false));
            }
        }
        return dtos;
    }
}
