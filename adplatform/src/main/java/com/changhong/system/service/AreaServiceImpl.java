package com.changhong.system.service;

import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.common.utils.CHListUtils;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.domain.box.Area;
import com.changhong.system.domain.log.ActionModule;
import com.changhong.system.domain.log.ActionType;
import com.changhong.system.domain.user.User;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.repository.AreaDao;
import com.changhong.system.web.event.ActionLogEvent;
import com.changhong.system.web.facade.assember.AreaWebAssember;
import com.changhong.system.web.facade.dto.AreaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 13:42
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public String obtainAreaByParentUuid(String parentUuid) {
        List<Area> areas = areaDao.loadAreaByParentId(parentUuid);
        return AreaWebAssember.toJson(areas, parentUuid);
    }

    @Override
    public AreaDTO obtainAreaByUuid(String areaUuid) {
        Area area  = (Area) areaDao.findByUuid(areaUuid, Area.class);
        if (area != null) {
            return AreaWebAssember.toAreaDTO(area);
        }
        return null;
    }

    @Override
    public void saveOrUpdateArea(AreaDTO areaDTO) {
        Area area = AreaWebAssember.toGongGaoDomain(areaDTO);
        if (area != null) {
            areaDao.saveOrUpdate(area);
        }

        //日志部分
        ActionLogEvent event = null;
        User current = SecurityUtils.currentUser();
        if (StringUtils.hasText(areaDTO.getUuid())) {
            ApplicationLog.infoWithCurrentUser(AreaServiceImpl.class, "change the area data info " + areaDTO.getUuid());
            event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "修改地区" + areaDTO.getName() + "信息");
        } else {
            ApplicationLog.infoWithCurrentUser(AreaServiceImpl.class, "add the area data info " + areaDTO.getUuid());
            event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_SYSTEM, "添加地区" + areaDTO.getName() + "信息");
        }
        ApplicationThreadPool.executeThread(event);
    }

    @Override
    public boolean deleteAreaCheck(String areaUuid) {
        Area area  = (Area) areaDao.findByUuid(areaUuid, Area.class);
        return deleteAreaCheck(area);
    }

    public boolean deleteAreaCheck(Area area) {
        if (area != null) {
            if (CHListUtils.hasElement(area.getChildAreas()) || CHListUtils.hasElement(area.getCommunities())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean deleteArea(String areaUuid) {
        Area area  = (Area) areaDao.findByUuid(areaUuid, Area.class);
        if (deleteAreaCheck(area)) {
            areaDao.delete(area);

            //日志部分
            ApplicationLog.infoWithCurrentUser(AreaServiceImpl.class, "delete the area " + areaUuid + " info");
            User current = SecurityUtils.currentUser();
            ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_AD, "删除地区" + area.getName());
            ApplicationThreadPool.executeThread(event);

            return true;
        }
        return false;
    }
}
