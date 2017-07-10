package com.changhong.system.service;


import com.changhong.system.web.facade.dto.AreaDTO;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 13:41
 */
public interface AreaService {

    String obtainAreaByParentUuid(String parentUuid);

    AreaDTO obtainAreaByUuid(String areaUuid);

    void saveOrUpdateArea(AreaDTO areaDTO);

    boolean deleteAreaCheck(String areaUuid);

    boolean deleteArea(String areaUuid);
}
