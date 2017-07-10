package com.changhong.statistic.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.changhong.common.web.session.SessionKey;

/**
 * 统计广告播放时长和次数
 * @author shijinxiang
 * 2017年2月13日
 * 上午10:15:24
 */
@Controller
public class AdTimeStatisticController {

	@RequestMapping("/backend/adtimestatistic.html")
	public ModelAndView adTimeStatistic(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String,Object> model = new HashMap<String,Object>();
		model.put(SessionKey.MENU_KEY,"STA_MANAGE");
		model.put(SessionKey.SUB_MENU_KEY, "AD_TIME");
		
		return new ModelAndView("backend/statistic/adtimestatistic",model);
	}
}
