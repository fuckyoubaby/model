package com.changhong.system.web.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.changhong.common.web.session.SessionKey;
import com.changhong.system.domain.app.AppStrategyArea;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.Community;
import com.changhong.system.service.AppStrategyAreaService;
import com.changhong.system.service.AppStrategyBoxService;
import com.changhong.system.service.AppStrategyCommunityService;
import com.changhong.system.service.AppStrategyService;
import com.changhong.system.service.AreaService;
import com.changhong.system.service.BoxInfoService;
import com.changhong.system.service.BoxInfoServiceImpl;
import com.changhong.system.service.BoxUpdateService;
import com.changhong.system.service.CommunityService;
import com.changhong.system.web.facade.dto.AppStrategyAreaDTO;
import com.changhong.system.web.facade.dto.AppStrategyBoxDTO;
import com.changhong.system.web.facade.dto.AppStrategyCommunityDTO;
import com.changhong.system.web.facade.dto.AppStrategyDTO;
import com.changhong.system.web.facade.dto.BoxDTO;
import com.changhong.system.web.facade.dto.CommunityDTO;
import com.changhong.system.web.paging.AppOverViewPaging;

/**
 * @author yujiawei
 * @date 2017年2月13日
 * @time 上午9:48:33
 */
@Controller
public class AppUpdateHistoryController {
	
	@Autowired
	private BoxUpdateService boxUpdateService;
	
	@Autowired
	private AppStrategyBoxService appStrategyBoxService;
	
	@Autowired
	private BoxInfoService boxInfoService;
	
	@Autowired
	private AppStrategyCommunityService appStrategyCommunityService;
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private AppStrategyAreaService appStrategyAreaService;
	
	@Autowired
	private AppStrategyService appStrategyService;
	
	/**
	 * 小区盒子
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/backend/ajaxFindAppUpdateHisRequestBoxes.html")
	public String ajaxFindAppUpdateHisRequestBoxes(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		String communityUuid = ServletRequestUtils.getStringParameter(request,
				"communityUuid", "");
		String mac = ServletRequestUtils.getStringParameter(request, "mac", "");
		int startPosition = ServletRequestUtils.getIntParameter(request,
				"startPosition", 0);
		int size = ServletRequestUtils.getIntParameter(request, "size", 10);
		int amount = 0;
		List<BoxDTO> boxes = null;
		// 获取设备
		if (StringUtils.hasText(communityUuid)) {
			// 小区id获取
			boxes = boxUpdateService.obtainBoxByCommunityAndPage(communityUuid,
					startPosition, size);
			amount = boxUpdateService.obtainBoxAmountByCommunity(communityUuid);
		} else if (StringUtils.hasText(mac)) {
			boxes = boxUpdateService.obtainBoxByMacPage(mac, startPosition,
					size);
			amount = boxUpdateService.obtainBoxAmountByMacPage(mac);
			model.put("needPath", true);
		}
		model.put("boxes", boxes);
		model.put("itemcount", amount);
		return "backend/app/template/boxesforfindupdatehistory";
	}

	/**
	 * 进入查看升级历史页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/backend/areacommunitybox.html")
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put(SessionKey.MENU_KEY, "RES_MANAGE");
		model.put(SessionKey.SUB_MENU_KEY, "MANAGE_history");
		return new ModelAndView("backend/app/areacommunitybox", model);
	}
	
	/**
	 * 查看历史
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/backend/appUpdateHistoryTable.html",method=RequestMethod.GET)
	public String appUpdateHistoryFind(HttpServletRequest request, ModelMap model){
		
		String macNumber = request.getParameter("macNumber");
		Set<String> list = new HashSet<String>();
		//取出第一部分的StrategyUuid
		List<AppStrategyBoxDTO> appStrategyBoxs = appStrategyBoxService.obtainAppStrategyBoxDTOsByMac(macNumber);
		for(int i=0;i<appStrategyBoxs.size();i++){
			list.add(appStrategyBoxs.get(i).getStrategyUuid()); 
		}
		BoxDTO boxDTO = boxInfoService.obtainSearchBox(macNumber);
		CommunityDTO communityDTO = communityService.obtainCommunityByUuid(boxDTO.getCommunityUuid(), false);
		
		//取第二部分StrategyUuid
		String communityUuid = communityDTO.getUuid();
		List<AppStrategyCommunityDTO> appStrategyCommunitys = appStrategyCommunityService.obtainCommunityDTOsByCommunityId(communityUuid);
		for(int j=0;j<appStrategyCommunitys.size();j++){
			list.add(appStrategyCommunitys.get(j).getStrategyUuid());  //第二部分StrategyUuid
		}
		
		//取第三部分StrategyUuid
		String areaUuid = communityDTO.getAreaUuid();
		List<AppStrategyAreaDTO> appStrategyAreas = appStrategyAreaService.obtainAppStrategyAreaDTOsByAreaId(areaUuid);
		for(int k=0;k<appStrategyAreas.size();k++){
			list.add(appStrategyAreas.get(k).getStrategyUuid());
		}
		
		List<AppStrategyDTO> listAppStrategys = new ArrayList<AppStrategyDTO>();
	    Iterator<String> it = list.iterator();
		while(it.hasNext()){
			AppStrategyDTO appStrategyDTO = appStrategyService.obtainStrategyByUuid(it.next());
			listAppStrategys.add(appStrategyDTO);
		}
		
		model.put("listAppStrategys", listAppStrategys);
		return "backend/app/appupdatehistory";
	}
}
