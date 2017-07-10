package com.changhong.system.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.box.Area;

import java.util.List;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 13:43
 */
public interface AreaDao extends EntityObjectDao {

    List<Area> loadAreaByParentId(String areaUuid);
    
    List<Area> loadAllArea();
}
