package com.changhong.system.service;

import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.domain.log.ActionModule;
import com.changhong.system.domain.log.ActionType;
import com.changhong.system.domain.meta.MetaData;
import com.changhong.system.domain.user.User;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.repository.MetaDao;
import com.changhong.system.web.event.ActionLogEvent;
import com.changhong.system.web.facade.assember.MetaDataWebAssember;
import com.changhong.system.web.facade.dto.MetaDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-21
 * Time: 上午9:17
 */
@Service("metaDataService")
public class MetaDataServiceImpl implements MetaDataService {

    @Autowired
    private MetaDao metaDataDao;

    @Override
    public List<MetaDataDTO> obtainMetaDatas(int startPosition, int pageSize) {
        List<MetaData> metaDatas = metaDataDao.findMetaDatas(startPosition, pageSize);
        return MetaDataWebAssember.toMetaDataDTOList(metaDatas);
    }

    @Override
    public int obtainMetaDataSize() {
        return metaDataDao.findMetaDataSize();
    }

    @Override
    public MetaDataDTO obtainMetaUuidByUuid(String metaDataUuid) {
        MetaData metaData = (MetaData) metaDataDao.findByUuid(metaDataUuid, MetaData.class);
        return MetaDataWebAssember.toMetaDataDTO(metaData);
    }

    @Override
    public void changeMetaDataDetails(MetaDataDTO dto) {
        MetaData metaData = MetaDataWebAssember.toMetaDataDomain(dto);
        metaDataDao.saveOrUpdate(metaData);

        //日志部分
        ActionLogEvent event = null;
        User current = SecurityUtils.currentUser();
        String note = metaData.getNote() == null ? "" : metaData.getNote();
        if (StringUtils.hasText(dto.getUuid())) {
            ApplicationLog.infoWithCurrentUser(MetaDataServiceImpl.class, "change the meta data info " + metaData.getUuid());
            event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "修改元数据" + note + "配置");
        } else {
            ApplicationLog.infoWithCurrentUser(MetaDataServiceImpl.class, "add the meta data info " + metaData.getUuid());
            event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "添加元数据" + note + "配置");
        }
        ApplicationThreadPool.executeThread(event);
    }
}
