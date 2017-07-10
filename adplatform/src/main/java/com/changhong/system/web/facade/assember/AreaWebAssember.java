package com.changhong.system.web.facade.assember;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.common.utils.CHListUtils;
import com.changhong.system.domain.box.Area;
import com.changhong.system.web.facade.dto.AreaDTO;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 14:08
 */
public class AreaWebAssember {

    public static Area toGongGaoDomain(AreaDTO areaDTO) {
        Area area = null;
        if(areaDTO == null) return null;
        String name = areaDTO.getName();
        if (!StringUtils.hasText(name)) {
            return null;
        }

        String uuid = areaDTO.getUuid();
        if (StringUtils.hasText(uuid)) {
            area = (Area) EntityLoadHolder.getUserDao().findByUuid(uuid, Area.class);
        } else {
            String parentUuid = areaDTO.getParentUuid();
            if (StringUtils.hasText(parentUuid)) {
                area = new Area();
                Area parent = new Area();
                parent.setUuid(parentUuid);
                area.setParentArea(parent);
            }
        }

        if (area != null) {
            area.setName(name);
        }

        return area;
    }

    public static String toJson(List<Area> areas, String areaUuid){
        JSONArray jsonArray = new JSONArray();

        if (CHListUtils.hasElement(areas)) {
            for (Area area : areas) {
                JSONObject areaJSON = new JSONObject();
                areaJSON.put("data", area.getName());
                {
                    JSONObject attrJSON = new JSONObject();
                    attrJSON.put("id", area.getUuid());
                    attrJSON.put("path", area.getFullAreaPath());
                    areaJSON.put("attr", attrJSON);
                }
                if(area.getChildAreas()!=null && area.getChildAreas().size()>0){
                    areaJSON.put("state", "closed");
                    areaJSON.put("children", "[]");
                }
                jsonArray.add(areaJSON);
            }
        }

        return jsonArray.toJSONString();
    }

    public static AreaDTO toAreaDTO(Area area) {
        String parentUuid = area.getParentArea() != null ? area.getParentArea().getUuid() : "";
        return new AreaDTO(area.getUuid(), area.getName(), parentUuid);
    }
    
    public static List<AreaDTO> toAreaDTOList(List<Area> areas){
    	List<AreaDTO> lists = new ArrayList<AreaDTO>();
    	if(areas!=null && areas.size()>0){
    		for(Area area : areas){
    			lists.add(toAreaDTO(area));
    		}
    	}
    	return lists;
    }
}
