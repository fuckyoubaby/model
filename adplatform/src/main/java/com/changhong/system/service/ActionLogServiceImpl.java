package com.changhong.system.service;

import com.changhong.system.domain.log.ActionLog;
import com.changhong.system.repository.ActionLogDao;
import com.changhong.system.web.facade.assember.ActionLogWebAssember;
import com.changhong.system.web.facade.dto.ActionLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-29
 * Time: 上午10:55
 */
@Service("actionLogService")
public class ActionLogServiceImpl implements ActionLogService {

    @Autowired
    private ActionLogDao actionLogDao;

    @Override
    public void saveActionLog(ActionLog log) {
        actionLogDao.saveOrUpdate(log);
    }

    @Override
    public List<ActionLogDTO> obtainActionLogs(String startDate, String endDate, int startPosition, int pageSize) {
        List<ActionLog> logs = actionLogDao.findActionLogs(startDate, endDate, startPosition, pageSize);
        return ActionLogWebAssember.toActionLogDTOList(logs);
    }

    @Override
    public int obtainActionLogSize(String startDate, String endDate) {
        return actionLogDao.findActionLogSize(startDate, endDate);
    }
}
