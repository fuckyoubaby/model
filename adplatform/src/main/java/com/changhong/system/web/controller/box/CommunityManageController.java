package com.changhong.system.web.controller.box;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.common.utils.CHListUtils;
import com.changhong.system.service.CommunityService;
import com.changhong.system.service.MetaDataService;
import com.changhong.system.web.facade.dto.BoxDTO;
import com.changhong.system.web.facade.dto.CommunityDTO;
import com.changhong.system.web.facade.dto.MetaDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 20:38
 */
@Controller
public class CommunityManageController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private MetaDataService metaDataService;

    /******************************************Community Overview**********************************************/

    @RequestMapping("/backend/getcommunities.html")
    protected void getCommunities(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String areaUuid = ServletRequestUtils.getStringParameter(request, "areaUuid", "");
        String name = ServletRequestUtils.getStringParameter(request, "name", "");
        int currentPage = ServletRequestUtils.getIntParameter(request, "current", 1);
        int rows = ServletRequestUtils.getIntParameter(request, "rows", 1);

        List<CommunityDTO> communityDTOs = communityService.obtainCommunities(areaUuid, name, currentPage, rows);
        int total = communityService.obtainCommunitySize(areaUuid, name);
        JSONObject rst = new JSONObject();
        if (CHListUtils.hasElement(communityDTOs)) {
            JSONArray jsonArray = new JSONArray();
            int startNum = (currentPage - 1) * rows + 1;
            for (int i = 0; i < communityDTOs.size(); i++) {
                CommunityDTO dto = communityDTOs.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("num", startNum + i);
                jsonObject.put("uuid", dto.getUuid());
                jsonObject.put("name", dto.getName());
                jsonObject.put("timestamp", dto.getTimestamp());
                jsonObject.put("abbreviation", dto.getAbbreviation());
                jsonObject.put("comment", dto.getComment());
                jsonArray.add(jsonObject);
            }
            rst.put("rows", jsonArray);
        }
        rst.put("total", total);

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(rst.toJSONString());
        writer.flush();
        writer.close();
    }

    @RequestMapping(value = "/backend/communityform.html", method = RequestMethod.GET)
    protected String getCommunity(HttpServletRequest request, ModelMap model) throws Exception {
        String method = ServletRequestUtils.getStringParameter(request, "method");

        CommunityDTO dto;
        if ("add".equals(method)) {
            String areaUuid = ServletRequestUtils.getStringParameter(request, "areaUuid", "");
            dto = new CommunityDTO(areaUuid);
        } else {
            String communityUuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
            dto = communityService.obtainCommunityByUuid(communityUuid, false);
        }
        model.put("community", dto);
        return "backend/box/communityform";
    }

    @RequestMapping(value = "/backend/communityform.html", method = RequestMethod.POST)
    public void setCommunity(@ModelAttribute("community") CommunityDTO community, HttpServletResponse response) throws Exception {
        boolean rst = communityService.saveOrUpdateCommunity(community);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", rst);
        //返回结果
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toJSONString());
        writer.flush();
        writer.close();
    }

    /******************************************Community Setting**********************************************/

    @RequestMapping(value = "/backend/communitymetadataform.html", method = RequestMethod.GET)
    protected String setCommunityMetaDataForm(HttpServletRequest request, ModelMap model) throws Exception {
        String communityUuid = ServletRequestUtils.getStringParameter(request, "communityUuid");
        CommunityDTO community = communityService.obtainCommunityByUuid(communityUuid, true);
        model.put("community", community);

        int sizes = metaDataService.obtainMetaDataSize();
        List<MetaDataDTO> metaDatas = metaDataService.obtainMetaDatas(0, sizes);
        model.put("metaDatas", metaDatas);
        return "backend/box/communitymetadataform";
    }

    @RequestMapping(value = "/backend/communitymetadataform.html", method = RequestMethod.POST)
    public void saveCommunityMetaDataForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String communityUuid = ServletRequestUtils.getStringParameter(request, "communityUuid");
        String metaDataUuid = ServletRequestUtils.getStringParameter(request, "metaDataUuid");

        communityService.saveCommunityMetaData(communityUuid, metaDataUuid);
    }
}
