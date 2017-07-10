package com.changhong.system.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.box.Community;

import java.util.List;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 20:04
 */
public interface CommunityDao extends EntityObjectDao {

    List<Community> loadCommunities(String areaUuid, String name, int startPosition, int pageSize);

    int loadCommunitySize(String areaUuid, String name);
}
