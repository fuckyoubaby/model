package com.changhong.system.web.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.changhong.common.web.session.SessionKey;
import com.changhong.system.domain.app.AppStrategyBox;
import com.changhong.system.service.AppManageService;
import com.changhong.system.service.AppStrategyBoxService;
import com.changhong.system.service.AppStrategyService;
import com.changhong.system.web.facade.dto.AppStrategyDTO;
import com.changhong.system.web.paging.AppStrategyOverViewPaging;

/**
 * @author yujiawei
 * @date 2017年2月10日
 * @time 上午9:25:17
 * 应用升级管理
 */
@Controller
public class AppStrategyController {
	
	@Autowired
	private AppStrategyService appStrategyService;
	
	@Autowired
	private AppManageService appManageService;
	
	@Autowired
	private AppStrategyBoxService appStrategyBoxService;
	
	//浏览策略
	@RequestMapping(value="/backend/managestrategy.html")
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		model.put(SessionKey.MENU_KEY,"RES_MANAGE");
		model.put(SessionKey.SUB_MENU_KEY, "MANAGE_STRATEGY");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String filterName = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "filterName", ""));
        request.setAttribute("current", current);
        request.setAttribute("filterName", filterName);
        
        AppStrategyOverViewPaging paging = new AppStrategyOverViewPaging(appStrategyService);
        constructPaging(paging, current, filterName);
        List<AppStrategyDTO> appStrategys = paging.getItems();
        
        model.put("appStrategys", appStrategys);
        model.put("paging", paging);
		return new ModelAndView("backend/app/strategyoverview",model);
	}
	
	private void constructPaging(AppStrategyOverViewPaging paging, int current, String filterName) {
        paging.setCurrentPageNumber(current);
        paging.setFilterName(filterName);
    }
	
	//策略表单部分
	@RequestMapping(value="/backend/strategyform.html",method=RequestMethod.GET)
	public String setAppStrategyForm(HttpServletRequest request,ModelMap model) {
		model.put(SessionKey.SUB_MENU_KEY, "MANAGE_STRATEGY");
		
		String strategyUuid = ServletRequestUtils.getStringParameter(request,"strategyUuid","");
		String current = ServletRequestUtils.getStringParameter(request, "current", "");
		String filterName =ServletRequestUtils.getStringParameter(request, "filterName","");
		
		request.setAttribute("current", current);
		request.setAttribute("filterName", filterName);
		
		AppStrategyDTO appStrategyDTO = null;
		if(!StringUtils.hasText(strategyUuid)){
			appStrategyDTO = new AppStrategyDTO();
		}else{
			appStrategyDTO = appStrategyService.obtainStrategyByUuid(strategyUuid);
		}
		model.put("appStrategyDTO", appStrategyDTO);
		model.put("current", current);
		model.put("filterName", filterName);
		return "backend/app/strategyform";
	}
	
	//更改策略状态
	@RequestMapping("/backend/appstrategyStatus.html")
	protected String changeStrategyStatus(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String strategyUuid = ServletRequestUtils.getStringParameter(request,"strategyUuid","");
		String current = ServletRequestUtils.getStringParameter(request, "current","");
	    String filterName = ServletRequestUtils.getStringParameter(request, "filterName","");
		
	    appStrategyService.changeStatusForAppStrategy(strategyUuid);
	    return "redirect:managestrategy.html?filterName="+filterName+"&current="+current;
	}
	
	//变更发布与否
	@RequestMapping("/backend/appstrategyDistributeStatus.html")
	public String changeDistributeStatus(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String strategyUuid = ServletRequestUtils.getStringParameter(request, "strategyUuid", "");
		String current = ServletRequestUtils.getStringParameter(request, "current","");
	    String filterName = ServletRequestUtils.getStringParameter(request, "filterName","");
		
	    appStrategyService.changeDistributeStatus(strategyUuid);
		return "redirect:managestrategy.html?filterName="+filterName+"&current="+current;
	}
	
	//新增或者编辑策略
	@RequestMapping(value="/backend/strategyform.html", method= RequestMethod.POST)
	public String saveStrategyForm(HttpServletRequest request, @ModelAttribute("appStrategyDTO")AppStrategyDTO appStrategyDTO,BindingResult errors, ModelMap model){
		String strategyUuid =  ServletRequestUtils.getStringParameter(request, "strategyUuid", "");
		String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
        
        request.setAttribute("current", current);
        request.setAttribute("filterName", filterName);
        
        appStrategyDTO.setUuid(strategyUuid);
        validateAppForm(request, appStrategyDTO, errors);
        if (errors.hasErrors()) {
            model.putAll(errors.getModel());
            model.put("current", current);
            model.put("filterName", filterName);
            return "backend/app/strategyform";
        }
        appStrategyService.changeAppStrategyDetails(appStrategyDTO);
        return "redirect:managestrategy.html?filterName=" + filterName + "&current=" + current;
	}

	//策略表单验证
	private void validateAppForm(HttpServletRequest request,AppStrategyDTO appStrategyDTO,BindingResult errors){
	    String name = appStrategyDTO.getName();
		String appVersion = appStrategyDTO.getAppVersion();
		
		if(!StringUtils.hasText(name)){
		    errors.rejectValue("name", "appStrategy.name.empty");	
		}
		if(!StringUtils.hasText(appVersion)){
			errors.rejectValue("appVersion", "app.appversion.empty");
		}else{
			if(!appManageService.obtainAppVersionIsExist(appVersion)){  //验证appVersion是否存在
				errors.rejectValue("appVersion", "app.appversion.notExist");
			}
		}
			
	}
	
	//配置盒子信息
	@RequestMapping(value="/backend/strategybox.html",method=RequestMethod.GET)
	public String configBoxInfo(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		model.put(SessionKey.SUB_MENU_KEY, "MANAGE_STRATEGY");
		String strategyUuid = ServletRequestUtils.getStringParameter(request, "strategyUuid", "");
		String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
        request.setAttribute("current", current);
        request.setAttribute("filterName", filterName);
        List <AppStrategyBox> appStrategyBoxs = null;
        if(StringUtils.hasText(strategyUuid)){
        	appStrategyBoxs = appStrategyBoxService.obtainMacList(strategyUuid);
        }
        model.put("appStrategyBoxs", appStrategyBoxs);
		model.put("strategyUuid", strategyUuid);
		return "backend/app/boxconfig";
	}
	
}
