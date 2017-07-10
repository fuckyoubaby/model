package com.changhong.statistic.web.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.AppVersionStatisticService;
import com.changhong.system.service.AppVersionStatisticServiceImpl;

/**
 * @author yujiawei
 * @date 2017年2月21日
 * @time 下午2:16:38
 */
@Controller
public class AppVersionStatisticController {

	@Autowired
	private AppVersionStatisticService appVersionStatisticService;
	
	/**
	 * 进入统计界面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/backend/appversionsta.html")
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		model.put(SessionKey.MENU_KEY, "STA_MANAGE");
		model.put(SessionKey.SUB_MENU_KEY,"APP_VERSION");
		
		return new ModelAndView("backend/statistic/appversionnumberstatistic", model);
	}
	
	/**
	 * ajax请求使用
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/backend/getAppVersionstatisticdata.html")
	public void getAppVersionNumber(HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
		String areaUuid = ServletRequestUtils.getStringParameter(request, "areaUuid", "");
		JSONObject item = appVersionStatisticService.obtainAppVersionNumberStaData(areaUuid);
		String responseJSON = item.toString();
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(responseJSON);
        writer.flush();
        writer.close();
		
	}
	
}
