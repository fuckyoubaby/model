package com.changhong.system.web.controller.box;

import com.alibaba.fastjson.JSONObject;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.AreaService;
import com.changhong.system.web.facade.dto.AreaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 11:12
 */
@Controller
public class AreaManageController {

    @Autowired
    private AreaService areaService;

    @RequestMapping("/backend/areamanage.html")
    protected String getAreaWebHtml(HttpServletRequest request, ModelMap model) throws Exception {
    	model.put(SessionKey.MENU_KEY,"ERROR_MANAGE");
        model.put(SessionKey.SUB_MENU_KEY, "AREA_MANAGE");
        return "backend/box/areamanage";
    }

    @RequestMapping("/backend/areatree.html")
    protected void getAreaTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String areaUuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
        String json = areaService.obtainAreaByParentUuid(areaUuid);

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
        writer.close();
    }

    @RequestMapping(value = "/backend/areaform.html", method = RequestMethod.GET)
    protected String getArea(HttpServletRequest request, ModelMap model) throws Exception {
        String method = ServletRequestUtils.getStringParameter(request, "method");
        String areaUuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
        AreaDTO dto;
        if ("add".equals(method)) {
            dto = new AreaDTO(areaUuid);
        } else {
            dto = areaService.obtainAreaByUuid(areaUuid);
        }
        model.put("area", dto);
        return "backend/box/areaform";
    }

    @RequestMapping(value = "/backend/areaform.html", method = RequestMethod.POST)
    protected String saveArea(@ModelAttribute("area") AreaDTO area) throws Exception {
        areaService.saveOrUpdateArea(area);
        return "redirect:areamanage.html";
    }

    @RequestMapping("/backend/areadeletecheck.html")
    protected void handleAreaDeleteCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String areaUuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
        boolean canDelete = areaService.deleteAreaCheck(areaUuid);

        response.setContentType("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("message", canDelete ? "true" : "false");
        PrintWriter writer = response.getWriter();
        writer.write(json.toString());
        writer.flush();
        writer.close();
    }

    @RequestMapping("/backend/areadelete.html")
    protected void handleAreaDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String areaUuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
        boolean rst = areaService.deleteArea(areaUuid);

        response.setContentType("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("message", rst ? "true" : "false");
        PrintWriter writer = response.getWriter();
        writer.write(json.toString());
        writer.flush();
        writer.close();
    }
}
