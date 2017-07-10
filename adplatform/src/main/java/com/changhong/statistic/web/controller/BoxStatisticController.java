package com.changhong.statistic.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.changhong.statistic.service.StatisticService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-25
 * Time: 上午9:13
 */
@Controller
public class BoxStatisticController {

    @Autowired
    private StatisticService statisticService;

    /********************************************投影仪数量统计*********************************************/

    /**
     * 显示统计的页面
     */
    @RequestMapping("/backend/boxnumberstatistic.html")
    public String boxNumberStatistic(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String areaUuid = ServletRequestUtils.getStringParameter(request, "areaUuid", "");
        model.put("areaUuid", areaUuid);
        return "backend/statistic/boxnumberstatistic";
    }

    /**
     * 获取统计的JSON数据
     */
    @RequestMapping("/backend/getvisitstatisticdata.html")
    public void getCustomerVisitStatisticData(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String areaUuid = ServletRequestUtils.getStringParameter(request, "areaUuid", "");
        JSONObject item = statisticService.obtainBoxNumberStaData(areaUuid);
        String responseJSON = item.toString();

        //返回结果
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(responseJSON);
        writer.flush();
        writer.close();
    }
}
