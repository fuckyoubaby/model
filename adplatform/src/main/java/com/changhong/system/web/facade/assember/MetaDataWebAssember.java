package com.changhong.system.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.system.domain.meta.MetaData;
import com.changhong.system.web.facade.dto.MetaDataDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:58
 */
public class MetaDataWebAssember {

    public static MetaData toMetaDataDomain(MetaDataDTO dto) {
        MetaData data = null;
        if(dto == null) return null;

        if (StringUtils.hasText(dto.getUuid())) {
            data = (MetaData) EntityLoadHolder.getUserDao().findByUuid(dto.getUuid(), MetaData.class);
            data.updateTimeStamp();
        } else {
            data = new MetaData();
        }

        data.setStartTime(dto.getStartTime());
        data.setEndTime(dto.getEndTime());
        data.setHeartInterval(dto.getHeartInterval());
        data.setNote(dto.getNote());
        return data;
    }

    public static MetaDataDTO toMetaDataDTO(MetaData data) {
        final String uuid = data.getUuid();
        final String timestamp = CHDateUtils.getFullDateFormat(data.getTimestamp());
        final String dataLevel = data.getDataLevel().name();
        final String dataLevelDesc = data.getDataLevel().getDescription();
        final String startTime = data.getStartTime();
        final String endTime = data.getEndTime();
        final String heartInterval = data.getHeartInterval();
        final String note = data.getNote();

        MetaDataDTO dto =  new MetaDataDTO(uuid, timestamp, dataLevel, dataLevelDesc, startTime, endTime, heartInterval, note);
        return dto;
    }

    public static List<MetaDataDTO> toMetaDataDTOList(List<MetaData> datas) {
        List<MetaDataDTO> dtos = new ArrayList<MetaDataDTO>();
        if (datas != null) {
            for (MetaData loop : datas) {
                dtos.add(toMetaDataDTO(loop));
            }
        }
        return dtos;
    }
}
