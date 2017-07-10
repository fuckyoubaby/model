package com.changhong.statistic.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.box.Box;

import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 16-11-25
 * Time: 上午11:00
 */
public interface StatisticDao extends EntityObjectDao {

    int findTotalBoxByCommunities(List<String> communityUuids);

    int findOnlineBoxByCommunities(List<String> communityUuids);

    Map findTotalBoxByCommunitiesGroup(List<String> communityUuids);

    Map findOnlineBoxByCommunitiesGroup(List<String> communityUuids);
    
    List<Box> findBoxByCommunities(List<String> communityUuids);
}
