package com.changhong.system.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.log.ActionLog;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-29
 * Time: 上午10:56
 */
public interface ActionLogDao extends EntityObjectDao {

    List<ActionLog> findActionLogs(String startDate, String endDate, int startPosition, int pageSize);

    int findActionLogSize(String startDate, String endDate);
}
