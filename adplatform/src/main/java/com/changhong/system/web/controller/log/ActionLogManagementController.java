package com.changhong.system.web.controller.log;

import com.changhong.common.utils.JodaUtils;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.ActionLogService;
import com.changhong.system.web.facade.dto.ActionLogDTO;
import com.changhong.system.web.paging.ActionLogOverviewPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 16-11-29
 * Time: 下午1:02
 */
@Controller
public class ActionLogManagementController {

    @Autowired
    private ActionLogService actionLogService;

    /***********************************************元数据浏览部分******************************************************/

    @RequestMapping("/backend/actionlogmanagement.html")
    protected String handleActionLogOverview(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        model.put(SessionKey.MENU_KEY, "SETTING_MANAGE");
        model.put(SessionKey.SUB_MENU_KEY, "LOG_MANAGE");

        String startDate = ServletRequestUtils.getStringParameter(request, "startDate", JodaUtils.getFirstDateOfMonth().toString());
        String endDate = ServletRequestUtils.getStringParameter(request, "endDate", JodaUtils.getEndDateOfMonth().toString());
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        request.setAttribute("current", current);

        ActionLogOverviewPaging paging = new ActionLogOverviewPaging(actionLogService);
        constructPaging(paging, current, startDate, endDate);
        List<ActionLogDTO> logs = paging.getItems();
        model.put("logs", logs);
        model.put("paging", paging);

        return "backend/log/actionlogoverview";
    }

    private void constructPaging(ActionLogOverviewPaging paging, int current, String startDate, String endDate) {
        paging.setCurrentPageNumber(current);
        paging.setStartDate(startDate);
        paging.setEndDate(endDate);
    }
}
