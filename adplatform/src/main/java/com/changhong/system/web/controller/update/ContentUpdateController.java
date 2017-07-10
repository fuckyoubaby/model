package com.changhong.system.web.controller.update;

import java.io.IOException;
import java.io.Writer;
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

import com.alibaba.fastjson.JSONObject;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.domain.upgrade.ContentUpgrade;
import com.changhong.system.domain.upgrade.ContentUpgradeArea;
import com.changhong.system.domain.upgrade.ContentUpgradeBox;
import com.changhong.system.service.ContentUpgradeAreaService;
import com.changhong.system.service.ContentUpgradeBoxService;
import com.changhong.system.service.ContentUpgradeCommunityService;
import com.changhong.system.service.ContentUpgradeService;
import com.changhong.system.service.PlayContentService;
import com.changhong.system.web.facade.dto.ContentUpgradeAreaDTO;
import com.changhong.system.web.facade.dto.ContentUpgradeBoxDTO;
import com.changhong.system.web.facade.dto.ContentUpgradeCommunityDTO;
import com.changhong.system.web.facade.dto.ContentUpgradeDTO;
import com.changhong.system.web.facade.dto.PlayContentDTO;
import com.changhong.system.web.paging.ContentUpgradeOverviewPaging;

@Controller
public class ContentUpdateController {
	
	private  final String MENU="AD_MANAGE";
	private  final String SUB_MENU="PLAY_UPGRADE_MANAGE";
	
	private String ajaxResponseType = "application/json; charset=utf-8";
	
	@Autowired
	private ContentUpgradeService contentUpgradeService;
	
	@Autowired
	private PlayContentService playContentService;
	
	@Autowired
	private ContentUpgradeBoxService contentUpgradeBoxService;
	
	@Autowired
	private ContentUpgradeCommunityService contentUpgradeCommunityService;
	
	@Autowired
	private ContentUpgradeAreaService contentUpgradeAreaService;
	
	
	@RequestMapping(value="/backend/contentupgradeform.html", method=RequestMethod.GET)
	public String getUpgradeForm(HttpServletRequest request, ModelMap model){
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		String contentUpgradeUuid = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		
		ContentUpgradeDTO dto = null;
		if(StringUtils.hasText(contentUpgradeUuid)){
			dto = contentUpgradeService.obtainDTOByUuid(contentUpgradeUuid);
		}else{
			dto = new ContentUpgradeDTO();
		}
		//TODO 初始化左侧菜单导航
		initMenu(model);
		model.put("contentUpgrade", dto);
		model.put("filterName",filterName);
		model.put("current", current);
		
		return "backend/play/update/playupgradeform";
	}
	
	@RequestMapping(value="/backend/contentupgradeform.html",method=RequestMethod.POST)
	public String saveForm(HttpServletRequest request, @ModelAttribute("contentUpgrade") ContentUpgradeDTO contentUpgradeDTO, 
			BindingResult errors, ModelMap model){
		
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName","");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		String contentUpgradeUuid = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		
		contentUpgradeDTO.setUuid(contentUpgradeUuid);
		validateForm(errors, contentUpgradeDTO);
		initMenu(model);
		if(errors.hasErrors()){
			model.put("filterName", filterName);
			model.put("current",current);
			model.put("contentUpgrade",contentUpgradeDTO);
			model.putAll(errors.getModel());
			
			return "backend/play/update/playupgradeform";
		}
		
		contentUpgradeService.saveDTO(contentUpgradeDTO);
		return "redirect:contentupgrademanagement.html?filterName="+filterName+"&current="+current;
	}
	
	@RequestMapping("/backend/contentupgrademanagement.html")
	public String contentUpgradeManage(HttpServletRequest request, ModelMap model){
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName","");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		
		ContentUpgradeOverviewPaging paging = constructPaging(filterName, current);
		
		List<ContentUpgradeDTO> lists = paging.getItems();
		model.put("filterName",filterName);
		model.put("current", current);
		model.put("contentUpgrades", lists);

		model.put("paging", paging);
		initMenu(model);
		
		return "backend/play/update/playupgradeindex";
	}
	
	@RequestMapping("/backend/contentupgradechangestatus.html")
	public String chageStauts(HttpServletRequest request){
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName","");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		
		if(StringUtils.hasText(contentUpgradeId)){
			contentUpgradeService.changeStatus(contentUpgradeId);
		}
		
		return "redirect:contentupgrademanagement.html?filterName="+filterName+"&current="+current;
	}
	@RequestMapping("/backend/contentupgradepublish.html")
	public String publishUpgrade(HttpServletRequest request){
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName","");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		
		if(StringUtils.hasText(contentUpgradeId)){
			contentUpgradeService.handlePublish(contentUpgradeId);
		}
		
		return "redirect:contentupgrademanagement.html?filterName="+filterName+"&current="+current;
	}
	
	@RequestMapping("/backend/contentupgradeinfo.html")
	public String upgradeInfo(HttpServletRequest request, ModelMap model){
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName","");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		
		ContentUpgradeDTO dto = contentUpgradeService.obtainDTOByUuid(contentUpgradeId);
		if(dto==null){
			return "redirect:contentupgrademanagement.html?filterName="+filterName+"&current="+current; 
		}
		//TODO 加载配置的区域信息【地区--小区-- 终端】、播放列表【】
		PlayContentDTO contentDTO = playContentService.obtainDTOByUpgradeId(contentUpgradeId);
		
		
		List<ContentUpgradeAreaDTO> areasDTO = contentUpgradeAreaService.obtainUpgradeAreaDTOs(contentUpgradeId);
		List<ContentUpgradeCommunityDTO> communitiesDTO = contentUpgradeCommunityService.obtainUpgradeCommunitiesDTO(contentUpgradeId);
		List<ContentUpgradeBoxDTO> boxsDTO = contentUpgradeBoxService.obtainUpgradeBoxesDTO(contentUpgradeId);
		
		model.put("filterName",filterName);
		model.put("current", current);
		model.put("contentUpgrade", dto);
		
		//获取播放内容DTO
		model.put("playContent", contentDTO);
		
		//放入策略配置的范围
		model.put("areasDTO", areasDTO);
		model.put("communitiesDTO", communitiesDTO);
		model.put("boxsDTO", boxsDTO);
		initMenu(model);
		return "backend/play/update/playupgradeinfo";
	}
	
	//升级配置播放内容
	@RequestMapping("/backend/contentupgradeconfigplay.html")
	public String configUgradeContent(HttpServletRequest request, ModelMap model){
		String uuid = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		
		ContentUpgradeDTO cuDTO = contentUpgradeService.obtainDTOByUuid(uuid);
		
		//查找已存在的playContentDTO;
		PlayContentDTO dto = playContentService.obtainDTOByUpgradeId(uuid);
		
		
		model.put("contentUpgrade", cuDTO);
		model.put("playContent", dto);
		initMenu(model);
		return "backend/play/update/upgradeconfigcontent";
	}
	
	//更新【移除旧的，添加新的】升级配置的内容
	@RequestMapping(value="/backend/contentupgradeupdateplay.html",method=RequestMethod.POST)
	public void updateUgradeContent(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String upgradeUuid = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		String newContentId = ServletRequestUtils.getStringParameter(request, "newContentId", "");
		String oldContentId = ServletRequestUtils.getStringParameter(request, "oldContentId", "");
		JSONObject result = new JSONObject(); 
		try{
			contentUpgradeService.updateNewContent(upgradeUuid, newContentId, oldContentId);
			result.put("result", true);
			result.put("msg", "配置成功");
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", "配置失败");
		}
		
		response.setContentType(ajaxResponseType);
		Writer writer = response.getWriter();
		writer.write(result.toString());
		writer.flush();
		writer.close();
	}
	
	@RequestMapping("/backend/contentupgradedelete.html")
	public String deleteUpgrade(HttpServletRequest request){
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName","");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		
		//TODO 多表关联之后需要进一步确认
		
		if(StringUtils.hasText(contentUpgradeId)){
			contentUpgradeService.deleteByUuid(contentUpgradeId);
		}
		
		return "redirect:contentupgrademanagement.html?filterName="+filterName+"&current="+current;
	}
	
	private ContentUpgradeOverviewPaging constructPaging(String filterName,
			int current) {
		ContentUpgradeOverviewPaging paging = new ContentUpgradeOverviewPaging(contentUpgradeService);
		paging.setFilterName(filterName);
		paging.setCurrentPageNumber(current);
		return paging;
	}

	private void validateForm(BindingResult errors,
			ContentUpgradeDTO contentUpgradeDTO) {
		String uuid = contentUpgradeDTO.getUuid();
		String name = contentUpgradeDTO.getName();
		String description = contentUpgradeDTO.getDescription();
		if(!StringUtils.hasText(name)){
			errors.rejectValue("name", "contentupgrade.name.empty", "名称不能为空");
		}else{
			if(name.length()>85){
				errors.rejectValue("name", "contentupgrade.name.length", "长度不超过85");
			}else{
				boolean exist = contentUpgradeService.obtainNameExists(uuid, name);
				if(exist){
					errors.rejectValue("name", "contentupgrade.name.exist", "名称已存在");
				}
			}
		}
		
		if(StringUtils.hasText(description)){
			if(description.length()>200){
				errors.rejectValue("description", "contentupgrade.description.maxlen", "长度不超过200");
			}
		}
	}
	
	//开始配置小区
	@RequestMapping("/backend/contentUpdateConfig.html")
	public String configInfo(HttpServletRequest request, ModelMap model){
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		
		if(!StringUtils.hasText(contentUpgradeId)){
			return "redirec: contentupgrademanagement.html?filterName=" + filterName + "&current="+current;
		}
		
		List<ContentUpgradeBoxDTO> boxes = contentUpgradeBoxService.obtainUpgradeBoxesDTO(contentUpgradeId);
		
		model.put("contentUpgradeId", contentUpgradeId);
		model.put("boxList", boxes);
		model.put("filterName",filterName);
		model.put("current", current);
		
		return "backend/play/update/playupdateboxconfig";
	}

	
	private void initMenu(ModelMap model){
		model.put(SessionKey.MENU_KEY, MENU);
		model.put(SessionKey.SUB_MENU_KEY, SUB_MENU);
	}
}
