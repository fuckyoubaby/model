package com.changhong.system.web.controller;

import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.BoxInfoService;
import com.changhong.system.web.facade.dto.BoxDTO;

import com.changhong.system.web.facade.dto.MetaDataDTO;
import com.changhong.system.web.paging.MetaDataOverviewPaging;
import com.changhong.system.web.paging.ProblemBoxOverviewPaging;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.changhong.common.utils.*;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:07
 */
@Controller
public class DashboardController {

    @Autowired
    public BoxInfoService boxInfoService;

    @RequestMapping("/backend/dashboard.html")
    protected String handleRequestInternal(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        model.put("MENU_KEY", "DASHBOARD");

        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        request.setAttribute("current", current);

        ProblemBoxOverviewPaging paging = new ProblemBoxOverviewPaging(boxInfoService);
        constructPaging(paging, current);
        List<BoxDTO> boxInfos = paging.getItems();
        model.put("boxes", boxInfos);
        model.put("paging", paging);

        return "dashboard";
    }

    private void constructPaging(ProblemBoxOverviewPaging paging, int current) {
        paging.setCurrentPageNumber(current);
    }

    @RequestMapping("/backend/notonlinemanage.html")
    public String notOnlineBoxesDisplay(HttpServletRequest request, ModelMap model) {
        model.put(SessionKey.MENU_KEY, "ERROR_MANAGE");
        model.put(SessionKey.SUB_MENU_KEY, "NOT_ONLINE_MANAGE");

        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        request.setAttribute("current", current);

        ProblemBoxOverviewPaging paging = new ProblemBoxOverviewPaging(boxInfoService);
        constructPaging(paging, current);
        List<BoxDTO> boxInfos = paging.getItems();
        model.put("boxes", boxInfos);
        model.put("paging", paging);

        return "dashboard";
    }
}
