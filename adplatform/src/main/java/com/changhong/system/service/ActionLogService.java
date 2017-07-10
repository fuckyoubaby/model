package com.changhong.system.service;

import com.changhong.system.domain.log.ActionLog;
import com.changhong.system.web.event.ActionLogEvent;
import com.changhong.system.web.facade.dto.ActionLogDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-29
 * Time: 上午10:55
 */
public interface ActionLogService {

    void saveActionLog(ActionLog log);

    List<ActionLogDTO> obtainActionLogs(String startDate, String endDate, int startPosition, int pageSize);

    int obtainActionLogSize(String startDate, String endDate);
}
