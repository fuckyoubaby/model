package com.changhong.system.web.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.common.utils.CHListUtils;
import com.changhong.system.domain.app.AppStrategyArea;
import com.changhong.system.domain.app.AppStrategyBox;
import com.changhong.system.domain.app.AppStrategyCommunity;
import com.changhong.system.service.AppStrategyAreaService;
import com.changhong.system.service.AppStrategyBoxService;
import com.changhong.system.service.AppStrategyCommunityService;
import com.changhong.system.service.AreaConfigService;
import com.changhong.system.service.AreaService;
import com.changhong.system.service.BoxUpdateService;
import com.changhong.system.service.CommunityService;
import com.changhong.system.web.facade.dto.AppStrategyAreaDTO;
import com.changhong.system.web.facade.dto.AppStrategyBoxDTO;
import com.changhong.system.web.facade.dto.AppStrategyCommunityDTO;
import com.changhong.system.web.facade.dto.AreaDTO;
import com.changhong.system.web.facade.dto.BoxDTO;
import com.changhong.system.web.facade.dto.CommunityDTO;

/**
 * @author yujiawei
 * @date 2017年2月10日
 * @time 上午9:25:17
 * 策略相关的盒子、社区、地区配置
 */
@Controller
public class AppStrategyConfigProcessController {

	@Autowired
	private BoxUpdateService boxUpdateService;
	
	@Autowired
	private AreaConfigService areaConfigService;
	
	@Autowired
	private AppStrategyBoxService appStrategyBoxService;
	
	@Autowired
	private AppStrategyCommunityService appStrategyCommunityService;
	
	@Autowired
	private AppStrategyAreaService appStrategyAreaService;
    
	@Autowired
	private CommunityService communityService;

	@Autowired
	private AreaService areaService;
	
	protected String ajaxContentType="application/json; charset=utf-8";
	
	   @RequestMapping("/backend/ajaxStrategyRequestBoxes.html")
	   public String ajaxRequestBoxes(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		   String communityUuid = ServletRequestUtils.getStringParameter(request, "communityUuid","");
		   String mac = ServletRequestUtils.getStringParameter(request, "mac","");
		   int startPosition = ServletRequestUtils.getIntParameter(request, "startPosition", 0);
		   int size = ServletRequestUtils.getIntParameter(request, "size", 10);
		   int amount = 0;
		   List<BoxDTO> boxes = null;
		   //获取设备
		   if(StringUtils.hasText(communityUuid)){
			   //小区id获取
			   boxes = boxUpdateService.obtainBoxByCommunityAndPage(communityUuid, startPosition, size);
			   amount = boxUpdateService.obtainBoxAmountByCommunity(communityUuid);
		   }else if(StringUtils.hasText(mac)){
			   boxes = boxUpdateService.obtainBoxByMacPage(mac, startPosition, size);
			   amount = boxUpdateService.obtainBoxAmountByMacPage(mac);
			   model.put("needPath",true);
		   }
		   model.put("boxes", boxes);
		   model.put("itemcount",amount);
		   return "backend/app/template/boxestemplate";
	    }
	   
	   //AJAX 保存和策略相关盒子MAC
	   @RequestMapping(value="backend/ajaxConifigSaveMac.html", method=RequestMethod.POST)
	   public void ajaxConifigSaveMac(HttpServletRequest request, HttpServletResponse response) throws IOException{
		   
		   String[]  macList = ServletRequestUtils.getStringParameters(request, "macList");
		   String strategyUuid = ServletRequestUtils.getStringParameter(request, "strategyUuid", "");
		   
		   List<AppStrategyBox> listStrategyBoxs = appStrategyBoxService.obtainMacList(strategyUuid);
		   if(macList.length!=0){
			   if(StringUtils.hasText(strategyUuid)){
				   for(int j=0;j<listStrategyBoxs.size();j++){      
						   appStrategyBoxService.deleteBoxMac(strategyUuid);    //删除重复mac的记录
				   }
				   for(int i =0;i<macList.length;i++){
					   AppStrategyBoxDTO appStrategyBoxDTO  = new AppStrategyBoxDTO();
					   appStrategyBoxDTO.setStrategyUuid(strategyUuid);
					   appStrategyBoxDTO.setMacNumber(macList[i]);
					   appStrategyBoxService.saveBoxList(appStrategyBoxDTO); 
				   }
			   }
		   }
		   response.setContentType(ajaxContentType);
		   JSONObject json = new JSONObject();
		   json.put("result", true);
		   Writer writer = response.getWriter();
		   writer.write(json.toString());
		   writer.flush();
		   writer.close();
	   };
	   
	   @RequestMapping("/backend/ConfigCommunity.html")
	   public String ConfigCommunity(HttpServletRequest request, ModelMap model){
		   String strategyUuid = ServletRequestUtils.getStringParameter(request, "strategyUuid", "");
		   List<AppStrategyCommunity> listCommunitys = null;
		   List<CommunityDTO> listCAUtils = new ArrayList<CommunityDTO>();
		   if(StringUtils.hasText("strategyUuid")){
		       listCommunitys = appStrategyCommunityService.obtainCommunityList(strategyUuid);
		   }
		       for(int i=0;i<listCommunitys.size();i++){
		    	   String communityUuid = listCommunitys.get(i).getCommunityUuid();
		    	   CommunityDTO communityDTO = communityService.obtainCommunityByUuid(communityUuid, true);     //查社区
		    	   listCAUtils.add(communityDTO);
		       }
		   model.put("listCAUtils", listCAUtils);
		   model.put("strategyUuid", strategyUuid);
		   return "backend/app/communityconfig";
	   }
	   
	   //保存和策略相关社区
	   @RequestMapping(value="/backend/ajaxConifigSaveCommunity.html",method=RequestMethod.POST)
	   public void ajaxConfigSaveCommunity(HttpServletRequest request, HttpServletResponse response) throws IOException{
		   response.setContentType(ajaxContentType);
		   String[] communityList = ServletRequestUtils.getStringParameters(request, "communityList");
		   String strategyUuid = ServletRequestUtils.getStringParameter(request, "strategyUuid", "");
		   List<AppStrategyCommunity> listStrategyCommunities = appStrategyCommunityService.obtainCommunityList(strategyUuid);
		   if(communityList.length!=0){
			   if(StringUtils.hasText(strategyUuid)){
				   for(int j=0;j<listStrategyCommunities.size();j++){
						   appStrategyCommunityService.deleteCommunity(strategyUuid);
				   }
				   for(int i=0;i<communityList.length;i++){
					   AppStrategyCommunityDTO appStrategyCommunityDTO = new AppStrategyCommunityDTO();
					   appStrategyCommunityDTO.setCommunityUuid(communityList[i]);
					   appStrategyCommunityDTO.setStrategyUuid(strategyUuid);
					   appStrategyCommunityService.saveCommunityList(appStrategyCommunityDTO);
				   }
			   }
		   }
		   JSONObject result = new JSONObject();
		   result.put("result", true);
		   Writer writer = response.getWriter();
		   writer.write(result.toString());
		   writer.flush();
		   writer.close();
	   }
	   
	   @RequestMapping("/backend/ConifigArea.html")
	   public String ConfigArea(HttpServletRequest request, ModelMap model){
		   String strategyUuid = ServletRequestUtils.getStringParameter(request, "strategyUuid", "");
		   model.put("strategyUuid", strategyUuid);
		   return "backend/app/areaconfig";
	   }
	   
	   @RequestMapping(value="/backend/ajaxStrategyGetAreas.html",method=RequestMethod.GET)
	   public void ajaxGetAreas(HttpServletRequest request, HttpServletResponse response) throws IOException{
		   response.setContentType(ajaxContentType);
		   String strategyUuid = ServletRequestUtils.getStringParameter(request, "strategyUuid", "");
		   
		   // 1、加载全部Area，用parent那一套;
		   List<AreaDTO> areas= areaConfigService.obtainAllArea();
		   JSONObject result = new JSONObject();
		   JSONArray array = new JSONArray();
		   JSONArray choosedArray = new JSONArray();
		   if(CHListUtils.hasElement(areas)){
			   for(AreaDTO area: areas){
				   JSONObject areaJSON = new JSONObject();
				   areaJSON.put("text", area.getName());
				   areaJSON.put("id", area.getUuid());
				   //TODO 判断parent是否在这边
				   if(StringUtils.hasText(area.getParentUuid())){
					   areaJSON.put("parent", area.getParentUuid());
				   }else {
					   areaJSON.put("parent", "#");
				   }
				   array.add(areaJSON);
			   }
		   }
		   
		   List<AppStrategyArea> listArea = new ArrayList<AppStrategyArea>();
		   if(StringUtils.hasText(strategyUuid)){
			   listArea = appStrategyAreaService.obtainAreaList(strategyUuid);
		   }
		   for(int i=0;i<listArea.size();i++){
			   JSONObject temp = new JSONObject();
			   AreaDTO areaDTO = areaService.obtainAreaByUuid(listArea.get(i).getAreaUuid());
			   temp.put("id", listArea.get(i).getAreaUuid());
			   temp.put("text", areaDTO.getName());
			   temp.put("parent", areaDTO.getParentUuid());
			   choosedArray.add(temp);
		   }
		   result.put("areas",array);
		   result.put("choosedarray", choosedArray);
		   Writer writer = response.getWriter();
		   writer.write(result.toString());
		   writer.flush();
		   writer.close();
	   }
	   
	   //保存和策略相关地区
	   @RequestMapping(value="/backend/ajaxStrategyConifigSaveArea.html",method=RequestMethod.POST)
	   public void ajaxConfigSaveArea(HttpServletRequest request, HttpServletResponse response) throws IOException{
	   
		   String[]  areaList = ServletRequestUtils.getStringParameters(request, "areaList");
		   String strategyUuid = ServletRequestUtils.getStringParameter(request, "strategyUuid", "");
		   List<AppStrategyArea> lists = appStrategyAreaService.obtainAreaList(strategyUuid);
		   
		   if(areaList.length!=0){
			   if(StringUtils.hasText(strategyUuid)){
				   for(int j=0;j<lists.size();j++){
					   appStrategyAreaService.deleteArea(strategyUuid);
				   }
				   for(int i=0;i<areaList.length;i++){
					   AppStrategyAreaDTO appStrategyAreaDTO = new AppStrategyAreaDTO();
					   appStrategyAreaDTO.setStrategyUuid(strategyUuid);
					   appStrategyAreaDTO.setAreaUuid(areaList[i]);
					   appStrategyAreaService.saveAreaList(appStrategyAreaDTO);
				   }
			   }
		   }
		   response.setContentType(ajaxContentType);
		   JSONObject json = new JSONObject();
		   json.put("result", true);
		   Writer writer = response.getWriter();
		   writer.write(json.toString());
		   writer.flush();
		   writer.close();
	   }
	   
}
