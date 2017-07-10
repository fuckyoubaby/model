package com.changhong.system.web.facade.assember;

import com.changhong.common.utils.CHDateUtils;
import com.changhong.system.domain.box.BoxCommand;
import com.changhong.system.web.facade.dto.BoxCommandDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:58
 */
public class BoxCommandWebAssember {

    public static BoxCommandDTO toBoxCommandDTO(BoxCommand command) {
        final String uuid = command.getUuid();
        final String timestamp = CHDateUtils.getFullDateFormat(command.getTimestamp());
        final String commandType = command.getCommandType().getDescription();
        final String mac = command.getMac();
        final String note = command.getComment();
        String planTime1 = command.getPlanTime();
        String planDate = "";
        String planTime = "";
        if (StringUtils.hasText(planTime1)) {
            planDate = planTime1.substring(0, 10);
            planTime = planTime1.substring(10, 6);
        }
        final boolean finished = command.isFinished();

        BoxCommandDTO dto =  new BoxCommandDTO(uuid, timestamp, commandType, mac, note, planDate, planTime, finished);
        return dto;
    }

    public static List<BoxCommandDTO> toBoxCommandDTOList(List<BoxCommand> commands) {
        List<BoxCommandDTO> dtos = new ArrayList<BoxCommandDTO>();
        if (commands != null) {
            for (BoxCommand loop : commands) {
                dtos.add(toBoxCommandDTO(loop));
            }
        }
        return dtos;
    }
}
