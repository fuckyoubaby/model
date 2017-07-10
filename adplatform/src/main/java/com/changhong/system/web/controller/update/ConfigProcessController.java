package com.changhong.system.web.controller.update;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
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
import com.changhong.system.domain.upgrade.ContentUpgrade;
import com.changhong.system.domain.upgrade.ContentUpgradeArea;
import com.changhong.system.domain.upgrade.ContentUpgradeBox;
import com.changhong.system.domain.upgrade.ContentUpgradeCommunity;
import com.changhong.system.service.AreaConfigService;
import com.changhong.system.service.BoxInfoService;
import com.changhong.system.service.BoxUpdateService;
import com.changhong.system.service.ContentUpgradeAreaService;
import com.changhong.system.service.ContentUpgradeBoxService;
import com.changhong.system.service.ContentUpgradeCommunityService;
import com.changhong.system.service.ContentUpgradeService;
import com.changhong.system.web.facade.dto.AreaDTO;
import com.changhong.system.web.facade.dto.BoxDTO;
import com.changhong.system.web.facade.dto.ContentUpgradeAreaDTO;
import com.changhong.system.web.facade.dto.ContentUpgradeBoxDTO;
import com.changhong.system.web.facade.dto.ContentUpgradeCommunityDTO;

@Controller
public class ConfigProcessController {

	@Autowired
	private BoxUpdateService boxUpdateService;
	
	@Autowired
	private AreaConfigService areaConfigService;
	
	@Autowired
	private ContentUpgradeService contentUpgradeService;
	
	@Autowired
	private ContentUpgradeBoxService contentUpgradeBoxService;
	
	@Autowired
	private ContentUpgradeCommunityService contentUpgradeCommunityService;
	
	@Autowired
	private ContentUpgradeAreaService contentUpgradeAreaService;
	
	protected String ajaxContentType="application/json; charset=utf-8";
	
	   @RequestMapping("/backend/ajaxRequestBoxes.html")
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
		   return "backend/play/template/boxestemplate";
	    }
	   
	   //AJAX 保存散列的盒子UUID，下一步配置散列的小区
	   @RequestMapping(value="backend/ajaxPlayConifigSaveUuid.html", method=RequestMethod.POST)
	   public void ajaxPlayConifigSaveUuid(HttpServletRequest request, HttpServletResponse response) throws IOException{
		   
		   String[]  boxUuidList= ServletRequestUtils.getStringParameters(request, "boxList");
		   String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		   response.setContentType(ajaxContentType);
		   JSONObject json = new JSONObject();
		   
		   
		   if(StringUtils.hasText(contentUpgradeId)){
			   ContentUpgrade cu = contentUpgradeService.obtainEntityByUuid(contentUpgradeId);
			   if(cu!=null){
				   List<ContentUpgradeBox> tempList = new ArrayList<ContentUpgradeBox>();
				   //删去数据库中已保存的数据
				   contentUpgradeBoxService.deleteBoxByUpgradeId(contentUpgradeId);
				   //不用去重,直接保存
				   for(int i=0;i<boxUuidList.length;i++){
					   if(StringUtils.hasText(boxUuidList[i].trim())){
						   ContentUpgradeBox cub = new ContentUpgradeBox(boxUuidList[i].trim(), cu);
						   tempList.add(cub);
					   }
				   }
				   if(tempList.size()>0){
					   contentUpgradeBoxService.saveUpgradeBoxes(tempList);
				   }
				   json.put("result", true);
			   }else{
				   json.put("result", false); 
			   }
		   }else{
			   json.put("result", false); 
		   }
		   
		   Writer writer = response.getWriter();
		   writer.write(json.toString());
		   writer.flush();
		   writer.close();
	   };
	   
	   
	   @RequestMapping("/backend/playConfigCommunity.html")
	   public String playConfigCommunity(HttpServletRequest request, ModelMap model){
		   String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
			String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
			int current = ServletRequestUtils.getIntParameter(request, "current", 1);
			
			if(!StringUtils.hasText(contentUpgradeId)){
				return "redirec: contentupgrademanagement.html?filterName=" + filterName + "&current="+current;
			}
		   
			//1、加载已经配置的小区信息
			List<ContentUpgradeCommunityDTO> dtos = contentUpgradeCommunityService.obtainUpgradeCommunitiesDTO(contentUpgradeId);
			
			//2、返回
		   model.put("contentUpgradeId", contentUpgradeId);
		   model.put("upgradeCommunities", dtos);
		   model.put("filterName", filterName);
		   model.put("current", current);
		   
		   return "backend/play/update/playupdatecommunityconfig";
	   }
	   
	   @RequestMapping(value="/backend/ajaxPlayConifigSaveCommunity.html",method=RequestMethod.POST)
	   public void ajaxPlayConfigSaveCommunity(HttpServletRequest request, HttpServletResponse response) throws IOException{
		   response.setContentType(ajaxContentType);
		   String[] communityList = ServletRequestUtils.getStringParameters(request, "communityList");
		   String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		   
		   JSONObject result = new JSONObject();
		   
		   if(!StringUtils.hasText(contentUpgradeId)){
			   result.put("result", false);
		   }else{
			   //1、先删除表中已存在的，再批量加入新的
			   ContentUpgrade cu = contentUpgradeService.obtainEntityByUuid(contentUpgradeId);
			   if(cu!=null){
				   contentUpgradeCommunityService.deleteEntityByUpgradeId(contentUpgradeId);
				   List<ContentUpgradeCommunity> tempList = new ArrayList<ContentUpgradeCommunity>();
				   int len = communityList.length;
				   if(len>0){
					   for(int i=0; i<len;i++){
						   if(StringUtils.hasText(communityList[i].trim())){
							   ContentUpgradeCommunity cuc = new ContentUpgradeCommunity(communityList[i], cu);
							   tempList.add(cuc);
						   }
					   }
				   }
				   if(tempList.size()>0){
					   contentUpgradeCommunityService.saveUpgradeCommunities(tempList);
				   }
				   result.put("result", true);
			   }else{
				   result.put("result", false); 
			   }
		   }
		   
		   Writer writer = response.getWriter();
		   writer.write(result.toString());
		   writer.flush();
		   writer.close();
	   }
	   
	   @RequestMapping("/backend/playConfigArea.html")
	   public String playConfigArea(HttpServletRequest request, ModelMap model){
		   
		   String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		   String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
			int current = ServletRequestUtils.getIntParameter(request, "current", 1);
			
			if(!StringUtils.hasText(contentUpgradeId)){
				return "redirec: contentupgrademanagement.html?filterName=" + filterName + "&current="+current;
			}
		  
			//已保存地区的初始化放在了下方的ajax请求里面
		   
		   model.put("contentUpgradeId", contentUpgradeId);
		   model.put("filterName", filterName);
		   model.put("current", current);
		   
		   return "backend/play/update/playupdateareaconfig";
	   }
	   
	   @RequestMapping(value="/backend/ajaxGetAreas.html",method=RequestMethod.GET)
	   public void ajaxGetAreas(HttpServletRequest request, HttpServletResponse response) throws IOException{
		   response.setContentType(ajaxContentType);
		   
		   // 1、 加载全部Area，用parent那一套;
		   List<AreaDTO> areas= areaConfigService.obtainAllArea();
		   JSONObject result = new JSONObject();
		   JSONArray array = new JSONArray();
		   JSONArray choosedArray = new JSONArray();
		   if(CHListUtils.hasElement(areas)){
			   for(AreaDTO area: areas){
				   JSONObject areaJSON = new JSONObject();
				   areaJSON.put("text", area.getName());
				   areaJSON.put("id", area.getUuid());
				   if(StringUtils.hasText(area.getParentUuid())){
					   areaJSON.put("parent", area.getParentUuid());
				   }else {
					   areaJSON.put("parent", "#");
				   }
				   array.add(areaJSON);
			   }
		   }
		   
		   // 2、获取策略配置的area范围，初始化checked范围
		   // 两实体 in 操作，获取area
		   //Example
		   String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		   List<ContentUpgradeAreaDTO> choosedAreaDTOs = null;

		   if(StringUtils.hasText(contentUpgradeId)){
			   choosedAreaDTOs = contentUpgradeAreaService.obtainUpgradeAreaDTOs(contentUpgradeId);
			   for(ContentUpgradeAreaDTO temp: choosedAreaDTOs ){
				   AreaDTO areaDTO = temp.getArea();
				   JSONObject tempJSON = new JSONObject();
				   tempJSON.put("text", areaDTO.getName());
				   tempJSON.put("id", areaDTO.getUuid());
				   if(StringUtils.hasText(areaDTO.getParentUuid())){
					   tempJSON.put("parent", areaDTO.getParentUuid());
				   }else {
					  tempJSON.put("parent", "#");
				   }
				   choosedArray.add(tempJSON);
			   }
		   }
		   
		   result.put("areas",array);
		   result.put("choosedarray", choosedArray);
		   Writer writer = response.getWriter();
		   writer.write(result.toString());
		   writer.flush();
		   writer.close();
	   }
	   
	   @RequestMapping(value="/backend/ajaxPlayConifigSaveArea.html",method=RequestMethod.POST)
	   public void ajaxPlayConifigSaveArea(HttpServletRequest request, HttpServletResponse response) throws IOException{
		   String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		   String [] areaList = ServletRequestUtils.getStringParameters(request, "areaList");
		   
		   response.setContentType(ajaxContentType);
		   JSONObject result = new JSONObject();
		   
		   if(!StringUtils.hasText(contentUpgradeId)){
			   result.put("result", false);
		   }else{
			   ContentUpgrade cu = contentUpgradeService.obtainEntityByUuid(contentUpgradeId);
			   if(cu!=null){
				   //先删除已存储的，再存入要配置的
				   contentUpgradeAreaService.deleteEntityByUpgradeId(contentUpgradeId);
				   List<ContentUpgradeArea> upgradeAreas = new ArrayList<ContentUpgradeArea>();
				   int len = areaList.length;
				   for(int i=0;i<len;i++){
					   if(StringUtils.hasText(areaList[i].trim())){
						   ContentUpgradeArea temp = new ContentUpgradeArea(areaList[i].trim(),	cu);
						   upgradeAreas.add(temp);
					   }
				   }
				   
				   if(upgradeAreas.size()>0){
					   contentUpgradeAreaService.saveUpgradeAreas(upgradeAreas);
				   }
				   result.put("result", true);
			   }else{
				   result.put("result", false);
			   }
		   }
		   
		   Writer writer = response.getWriter();
		   writer.write(result.toString());
		   writer.flush();
		   writer.close();
		   
	   }
}
