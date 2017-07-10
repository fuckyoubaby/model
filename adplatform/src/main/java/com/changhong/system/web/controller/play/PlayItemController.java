package com.changhong.system.web.controller.play;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.servlet.mvc.AbstractController;

import com.alibaba.fastjson.JSONObject;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.PlayItemContentService;
import com.changhong.system.service.PlayItemService;
import com.changhong.system.web.facade.dto.PlayItemContentDTO;
import com.changhong.system.web.facade.dto.PlayItemDTO;

/**
 * Author: Guo xiaoyang
 * Date: 2017-1-10 
 * Time: 09:40:54
 */
@Controller
public class PlayItemController extends AbstractController{

	@Autowired
	private PlayItemService playItemService;
	
	@Autowired
	private PlayItemContentService playItemContentService;
	
	protected String backContentType = "application/json;charset=utf-8";
	
	private  final String MENU="AD_MANAGE";
	private  final String SUB_MENU="PLAY_LIST_MANAGE";
	private final String SUB_MENU2="PLAY_UPGRADE_MANAGE";
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@RequestMapping(value="/backend/play/playItemFetch.html", method=RequestMethod.POST)
	public String itemsFetch(HttpServletRequest request, ModelMap model){
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword","");
		Integer indexNo = ServletRequestUtils.getIntParameter(request, "indexNo", 0);
		Integer pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 5);
		
		List<PlayItemDTO> itemDTOs = playItemService.obtainEnableItems(keyword, indexNo, pageSize);
		int itemcount = playItemService.obtainEnableItemsAmount(keyword);
		
		model.put("playItemDTOs", itemDTOs);
		model.put("itemcount", itemcount);
		
		return "/backend/play/template/itemresourcetemplate"; 
	} 
	
	
	@RequestMapping(value="/backend/addpalyitem.html", method=RequestMethod.GET)
	public String addNewItem(HttpServletRequest request, ModelMap model){
		
		String playContentId= ServletRequestUtils.getStringParameter(request, "playContentId","");
		String playItemId = ServletRequestUtils.getStringParameter(request, "playItemId", "");
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		
		PlayItemDTO dto = null;
		
		if(StringUtils.hasText(playItemId)){
			dto = playItemService.otainByUuid(playItemId);
		}else{
			dto = new PlayItemDTO();
		}
		
		model.put("playContentId", playContentId);
		model.put("playItem", dto);
		model.put("contentUpgradeId", contentUpgradeId);
		
		initMenu( model, contentUpgradeId);
		return "backend/play/playitemadd";
	}

	@RequestMapping(value="/backend/saveplayitem.html", method=RequestMethod.POST)
	public String saveNewItem(HttpServletRequest request, @ModelAttribute("playItem") PlayItemDTO playItemDTO,
			BindingResult errors, ModelMap model){
		String playContentId = ServletRequestUtils.getStringParameter(request, "playContentId","");
		String playItemId = ServletRequestUtils.getStringParameter(request, "playItemId", "");
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		
		//不设置playContentId, toDomian() 时，只做基本信息保存或更新
		playItemDTO.setUuid(playItemId);
		validateForm(errors, playItemDTO);
		initMenu(model, contentUpgradeId);
		if(errors.hasErrors()){
			model.put("playContentId", playContentId);
			model.put("playItem", playItemDTO);
			model.putAll(errors.getModel());
			initParamters(request, "contentUpgradeId", model);
			return "/backend/play/playitemadd";
		}
		
		playItemService.saveDTO(playItemDTO);
		
		if(StringUtils.hasText(playContentId)){
			if(StringUtils.hasText(playItemId)){
				//update old, 进入 资源配置页面
				return "redirect:configplayitem.html?itemUuid="+playItemId+"&contentUuid="+playContentId+"&contentUpgradeId="+contentUpgradeId;
			}else{
				//save new, 进入播放内容配置页面
				return "redirect:playcontent.html?contentUpgradeId="+contentUpgradeId+"&playContentId="+playContentId;
			}
		}else{
			if(StringUtils.hasText(contentUpgradeId)){
				return "redirect:contentupgradeinfo.html?contentUpgradeId="+contentUpgradeId;
			}
			return "redirect:playcontentmanagement.html";
		}
		
	}
	
	@RequestMapping("/backend/configplayitem.html")
	public String configItemResource(HttpServletRequest request, ModelMap model){
		String itemUuid = ServletRequestUtils.getStringParameter(request, "itemUuid", "");
		String contentUuid = ServletRequestUtils.getStringParameter(request, "contentUuid","");
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId","");
		
		if(StringUtils.hasText(itemUuid)){
			PlayItemDTO dto = playItemService.otainByUuid(itemUuid);
			List<PlayItemContentDTO> playItemContentDTOs = playItemContentService.obtainContentByItemUuid(itemUuid);
			model.put("playItem", dto);
			model.put("playContentId", contentUuid);
			model.put("playItemContents", playItemContentDTOs);
			model.put("contentUpgradeId", contentUpgradeId);
			initMenu(model, contentUpgradeId);
			return "backend/play/item_resource_config";
		}else{
			if(StringUtils.hasText(contentUuid)){
				return "redirect: playcontent.html?playContentId="+contentUuid+"&contentUpgradeId="+contentUpgradeId;
			}else{
				if(StringUtils.hasText(contentUpgradeId)){
					return "refirect: contentupgradeconfigplay.html?contentUpgradeId="+contentUpgradeId;
				}
				return "redirect: playcontentmanagement.html";
			}
		}
	}
	
	@RequestMapping(value="/backend/ajaxConfigContent.html", method=RequestMethod.POST)
	public void configItemPlay(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
		String contentUuid = ServletRequestUtils.getStringParameter(request, "contentUuid","");
		JSONObject resultObj = new JSONObject();
		response.setContentType(backContentType);
		
		if(!StringUtils.hasText(uuid) || !StringUtils.hasText(contentUuid)){
			resultObj.put("result", false);
			resultObj.put("msg", "添加失败");
		}else{
			String startDate = ServletRequestUtils.getStringParameter(request, "startdate","");
			String endDate = ServletRequestUtils.getStringParameter(request, "enddate","");
			int repeat = ServletRequestUtils.getIntParameter(request, "repeat", -1);
			int index = ServletRequestUtils.getIntParameter(request, "index", 1);
			
			PlayItemDTO pi = playItemService.otainByUuid(uuid);
			pi.setPlayContentUuid(contentUuid);
			pi.setEndDate(parseSimpleDateFormat(endDate));
			pi.setStartDate(parseSimpleDateFormat(startDate));
			pi.setIndex(index);
			if(repeat!=-1){
				pi.setRepeat(repeat);
			}
			playItemService.updateDTO(pi);
			resultObj.put("result", true);
			resultObj.put("msg", "添加成功");
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(resultObj.toString());
		writer.flush();
		writer.close();
	}
	
	@RequestMapping(value="/backend/ajaxChangeIndex.html", method=RequestMethod.POST)
	public void configItemIndex(HttpServletRequest request, HttpServletResponse response) throws IOException{

		String preUuid = ServletRequestUtils.getStringParameter(request, "preUuid", "");
		String currentUuid = ServletRequestUtils.getStringParameter(request, "currentUuid","");
		int preIndex = ServletRequestUtils.getIntParameter(request, "preIndex", 2);
		int currentIndex = ServletRequestUtils.getIntParameter(request, "currentIndex", 1);
		
		JSONObject resultObj = new JSONObject();
		response.setContentType(backContentType);
		
		if(!StringUtils.hasText(preUuid) || !StringUtils.hasText(currentUuid)){
			resultObj.put("result", false);
			resultObj.put("msg", "修改失败");
		}else{
			playItemService.changeIndex(preUuid, preIndex);
			playItemService.changeIndex(currentUuid, currentIndex);	
			resultObj.put("result", true);
			resultObj.put("msg", "修改成功");
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(resultObj.toString());
		writer.flush();
		writer.close();
	}
	
	@RequestMapping(value="/backend/ajaxRemoveItem.html", method=RequestMethod.POST)
	public void removeItem(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
		JSONObject resultObj = new JSONObject();
		response.setContentType(backContentType);
		
		if(!StringUtils.hasText(uuid) ){
			resultObj.put("result", false);
			resultObj.put("msg", "移除失败");
		}else{
			playItemService.cancelNull(uuid);	
			
			resultObj.put("result", true);
			resultObj.put("msg", "移除成功");
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(resultObj.toString());
		writer.flush();
		writer.close();
	}
	
	@RequestMapping(value="/backend/ajaxUpdateItemConfig.html", method=RequestMethod.POST)
	public void updateItemConfig(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
		JSONObject resultObj = new JSONObject();
		response.setContentType(backContentType);
		
		if(!StringUtils.hasText(uuid)){
			resultObj.put("result", false);
			resultObj.put("msg", "保存失败");
		}else{
			String startDate = ServletRequestUtils.getStringParameter(request, "startdate","");
			String endDate = ServletRequestUtils.getStringParameter(request, "enddate","");
			int repeat = ServletRequestUtils.getIntParameter(request, "repeat", -1);
			
			PlayItemDTO pi = playItemService.otainByUuid(uuid);
			if(pi == null){
				resultObj.put("result", false);
				resultObj.put("msg", "保存失败");
			}else{
				pi.setEndDate(parseSimpleDateFormat(endDate));
				pi.setStartDate(parseSimpleDateFormat(startDate));
				if(repeat!=-1){
					pi.setRepeat(repeat);
				}else{
					pi.setRepeat(null);
				}
				playItemService.updateDTO(pi);
				resultObj.put("result", true);
				resultObj.put("msg", "保存成功");
			}
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(resultObj.toString());
		writer.flush();
		writer.close();
	}
	
	@RequestMapping(value="/backend/ajaxDeleteItem.html", method=RequestMethod.POST)
	public void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
		JSONObject resultObj = new JSONObject();
		response.setContentType(backContentType);
		
		if(!StringUtils.hasText(uuid) ){
			resultObj.put("result", false);
			resultObj.put("msg", "移除失败");
		}else{
			playItemService.deleteItem(uuid);	
			resultObj.put("result", true);
			resultObj.put("msg", "移除成功");
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(resultObj.toString());
		writer.flush();
		writer.close();
	}
	
	/**
	 * HTML页面POST提交时进行的表单验证
	 * @param errors
	 * @param dto
	 */
	private void validateForm(BindingResult errors, PlayItemDTO dto){
		String uuid = dto.getUuid();
		String name = dto.getName();
		String description = dto.getDescription();
		if(!StringUtils.hasText(name)){
			errors.rejectValue("name", "playitem.name.empty", "名称不能为空");
		}else{
			if(name.length()>85){
				errors.rejectValue("name", "playitem.name.length", "长度不超过85");
			}else{
				boolean exist = playItemService.obtainNameExists(uuid, name);
				if(exist){
					errors.rejectValue("name", "playitem.name.exist", "名称已存在");
				}
			}
		}
		if(StringUtils.hasText(description)){
			if(description.length()>200){
				errors.rejectValue("description", "playitem.description.exist", "字数不超过200");
			}
		}
	}
	
	
	 private  Date parseSimpleDateFormat(String dateStr) {
	        try {
	            SimpleDateFormat time = new SimpleDateFormat("yyyy.MM.dd");
	            Date date = time.parse(dateStr);
	            return date;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
		private void initMenu(ModelMap model, String judgeStr){
			model.put(SessionKey.MENU_KEY, MENU);
			if(StringUtils.hasText(judgeStr)){
				model.put(SessionKey.SUB_MENU_KEY, SUB_MENU2);
			}else{
				model.put(SessionKey.SUB_MENU_KEY, SUB_MENU);
			}
		}
		
		private void initParamters(HttpServletRequest request, String keyName, ModelMap map){
			Object value = request.getParameter(keyName);
			map.put(keyName, value);
		}
}
