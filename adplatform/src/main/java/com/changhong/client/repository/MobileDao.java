package com.changhong.client.repository;


import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.BoxCommand;
import com.changhong.system.domain.box.Community;
import com.changhong.system.domain.user.User;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:25
 */
public interface MobileDao extends EntityObjectDao {

    User findUserByUsername(String mobile);

    List<Community> findCommunity(String words, int number);

    int findBoxSizeByCommunityUuid(String communityUuid);

    List<Box> findBoxByCommunityUuid(String communityUuid, int startPosition, int pageSize);
}
