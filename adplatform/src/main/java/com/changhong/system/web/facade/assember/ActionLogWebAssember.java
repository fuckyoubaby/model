package com.changhong.system.web.facade.assember;

import com.changhong.common.utils.CHDateUtils;
import com.changhong.system.domain.log.ActionLog;
import com.changhong.system.web.facade.dto.ActionLogDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:58
 */
public class ActionLogWebAssember {

    public static ActionLogDTO toActionLogDTO(ActionLog log) {
        final String uuid = log.getUuid();
        final String timestamp = CHDateUtils.getFullDateFormat(log.getTimestamp());
        final String operator = log.getUserXingMing();
        final String type = log.getActionType().getDescription();
        final String module = log.getActionModule().getDescription();
        final String desc = log.getActionDesc();

        ActionLogDTO dto =  new ActionLogDTO(uuid, timestamp, operator, type, module, desc);
        return dto;
    }

    public static List<ActionLogDTO> toActionLogDTOList(List<ActionLog> logs) {
        List<ActionLogDTO> dtos = new ArrayList<ActionLogDTO>();
        if (logs != null) {
            for (ActionLog loop : logs) {
                dtos.add(toActionLogDTO(loop));
            }
        }
        return dtos;
    }
}
