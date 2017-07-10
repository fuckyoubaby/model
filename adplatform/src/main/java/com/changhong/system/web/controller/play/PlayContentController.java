package com.changhong.system.web.controller.play;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.servlet.mvc.AbstractController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.common.utils.FileNameUtil;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.PlayContentService;
import com.changhong.system.service.PlayItemContentService;
import com.changhong.system.service.PlayItemService;
import com.changhong.system.web.facade.dto.AdverResourceDTO;
import com.changhong.system.web.facade.dto.PlayContentDTO;
import com.changhong.system.web.facade.dto.PlayItemContentDTO;
import com.changhong.system.web.facade.dto.PlayItemDTO;
import com.changhong.system.web.paging.PlayContentOverviewPaging;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-6
 * Time: 14:14:20
 *
 */

@Controller
public class PlayContentController extends AbstractController{
	
	@Autowired
	private PlayContentService playContentService;
	
	@Autowired
	private PlayItemService playItemService;
	
	@Autowired
	private PlayItemContentService playItemContentService;
	
	
	private  final String MENU="AD_MANAGE";
	private  final String SUB_MENU="PLAY_LIST_MANAGE";
	private final String SUB_MENU2 = "PLAY_UPGRADE_MANAGE";
	
	private  static String jsonResponse = "application/json;charset=utf-8";  
	
	@RequestMapping("/backend/playcontentmanagement.html")
	public String playContentManagement(HttpServletRequest request, ModelMap model){
		
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
		PlayContentOverviewPaging paging = constructPaging(filterName, current);
		List<PlayContentDTO> lists = paging.getItems();
		
		model.put("filterName", filterName);
		model.put("current", current);
		model.put("playContents", lists);
		model.put("paging", paging);
		initMenu(model,null);
		
		return "backend/play/playcontentindex";
	}
	
	

	@Override
	@RequestMapping("/backend/playcontent.html")
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String playContentId= ServletRequestUtils.getStringParameter(arg0, "playContentId","");
		String filterName = ServletRequestUtils.getStringParameter(arg0, "filterName", "");
		int current = ServletRequestUtils.getIntParameter(arg0, "current", 1);
		String contentUpgradeId = ServletRequestUtils.getStringParameter(arg0, "contentUpgradeId", "");
		
		if(!StringUtils.hasText(playContentId)){
			mav.setViewName("redirect: playcontentmanagement.html");
			return mav;
		}
		
		PlayContentDTO dto = playContentService.obtainDTOByUUID(playContentId);
		List<PlayItemDTO> itemDTOs = playContentService.obtainPlayItemByUuid(playContentId);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("playContentId", playContentId);
		model.put("filterName", filterName);
		model.put("current", current);
		model.put("playContent",dto);
		model.put("items", itemDTOs);
		model.put(SessionKey.MENU_KEY, MENU);
		if(StringUtils.hasText(contentUpgradeId)){
			model.put(SessionKey.SUB_MENU_KEY, SUB_MENU2);
		}else{
			model.put(SessionKey.SUB_MENU_KEY, SUB_MENU);
		}
		model.put("contentUpgradeId", contentUpgradeId);
		
		mav.setViewName("backend/play/playcontentinfo");
		mav.addAllObjects(model);
		return mav;
	}
	
	
	
	
	@RequestMapping(value="/backend/playcontentform.html", method=RequestMethod.GET)
	public String addNewContent(HttpServletRequest request, ModelMap model){
		
		String filterNmae = ServletRequestUtils.getStringParameter(request, "filterName", "");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		String contentUuid = ServletRequestUtils.getStringParameter(request, "contentUuid", "");
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		
		PlayContentDTO playContentDTO = null;
		if(!StringUtils.hasText(contentUuid)){
			playContentDTO = new PlayContentDTO();
		}else{
			playContentDTO =  playContentService.obtainDTOByUUID(contentUuid);
		}
		
		model.put("playContent", playContentDTO);
		model.put("filterName", filterNmae);
		model.put("current",current);
		model.put("contentUpgradeId", contentUpgradeId);
		
		initMenu(model, contentUpgradeId);
		
		return "backend/play/playcontentform";
	}

	
	@RequestMapping(value="/backend/playcontentform.html", method=RequestMethod.POST)
	public String saveNewContent(HttpServletRequest request, @ModelAttribute("playContent") PlayContentDTO playContentDTO, 
			BindingResult errors, ModelMap model){
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		String contentUuid = ServletRequestUtils.getStringParameter(request, "contentUuid","");
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		int version = ServletRequestUtils.getIntParameter(request, "version", 1);
		
		playContentDTO.setUuid(contentUuid);
		validateForm(errors, playContentDTO, request);
		playContentDTO.setVersion(version+"");
		initMenu(model, contentUpgradeId);
		model.put("contentUuid", contentUuid);
		model.put("contentUpgradeId", contentUpgradeId);
		
		if(errors.hasErrors()){
			model.put("current", current);
			model.put("filterName", filterName);
			model.put("playContent",playContentDTO);
			model.putAll(errors.getModel());
			
			return "backend/play/playcontentform";
		}else{
			playContentService.savePlayContentDTO(playContentDTO);
			if(StringUtils.hasText(contentUpgradeId)){
				return "redirect:contentupgradeconfigplay.html?contentUpgradeId="+contentUpgradeId;
			}else{
				return "redirect:playcontentmanagement.html?filterName="+filterName+"&current="+current;
			}
			
		}
	}
	
	@RequestMapping("/backend/deletePlayContent.html")
	public String deletePlayContent(HttpServletRequest request){
		
		String playContentId = ServletRequestUtils.getStringParameter(request, "playContentId", "");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
		
		if(StringUtils.hasText(playContentId)){
			playContentService.deleteByUUID(playContentId);
		}
		
		return "redirect: playcontentmanagement.html?filterName="+filterName+"&current="+current;
	}

	@RequestMapping(value="/backend/ajaxDeletePlayContent.html", method=RequestMethod.POST)
	public void ajaxDeletePlayContent(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String playContentId = ServletRequestUtils.getStringParameter(request, "playContentId", "");
		response.setContentType(jsonResponse);
		
		JSONObject obj = new JSONObject();
		
		if(StringUtils.hasText(playContentId)){
			playContentService.deleteByUUID(playContentId);
			obj.put("result", true);
		}else{
			obj.put("result", false);
		}
		
		Writer writer = response.getWriter();
		writer.write(obj.toString());
		writer.flush();
		writer.close();
	}
	
	
	@RequestMapping("/backend/bulidPlayListJSON.html")
	public void bulidPlayListJSON(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String playContentId = ServletRequestUtils.getStringParameter(request, "playContentId", "");
		response.setContentType(jsonResponse);
		Writer writer = response.getWriter();

		
		if(!StringUtils.hasText(playContentId)){
			writer.write("参数为空");
		}else{
			PlayContentDTO pcDTO = playContentService.obtainDTOByUUID(playContentId);
			List<PlayItemDTO> playItemDTOLists = playContentService.obtainPlayItemByUuid(playContentId);
			Map<String,List<PlayItemContentDTO>> itemContentMap = new HashMap<String, List<PlayItemContentDTO>>();
			JSONObject playContentJSON = new JSONObject();
			
			for(PlayItemDTO piDTO : playItemDTOLists){
				if(piDTO.getAmount()>0){
					String playItemUuid = piDTO.getUuid();
					List<PlayItemContentDTO> itemContentDTOLists = playItemContentService.obtainContentByItemUuid(playItemUuid);
					itemContentMap.put(playItemUuid, itemContentDTOLists);
				}
			}
			
			playContentJSON.put("ID", pcDTO.getUuid());
			playContentJSON.put("defaulDuration", buildJSONDouble(pcDTO.getDefaultDuration()));
			playContentJSON.put("version", pcDTO.getVersion());
			
			JSONArray playListArray = new JSONArray();
			for(PlayItemDTO itemDTO : playItemDTOLists){
				JSONObject arrayItem = new JSONObject();
				arrayItem.put("index", itemDTO.getIndex()+"");
				arrayItem.put("name", itemDTO.getName());
				arrayItem.put("startDate", buildJSONDate(itemDTO.getStartDate()));
				arrayItem.put("endDate", buildJSONDate(itemDTO.getEndDate()));
				arrayItem.put("repeat", buildJSONInteger(itemDTO.getRepeat()));
				JSONArray filesArray = new JSONArray();
				List<PlayItemContentDTO> itemContentLists = itemContentMap.get(itemDTO.getUuid());
				if(itemContentLists!=null && itemContentLists.size()>0){
					for(int i=0; i< itemContentLists.size();i++){
						PlayItemContentDTO  temp = itemContentLists.get(i);
						AdverResourceDTO adverDTO = temp.getAdverResourceDTO();
						JSONObject obj = new JSONObject();
						obj.put("index", temp.getIndex().toString());
						obj.put("duration", buildJSONDouble(temp.getDuration()));
						obj.put("repeat", buildJSONInteger(temp.getRepeat()));
						obj.put("advertiser", adverDTO.getAdvertiser());
						obj.put("agency", adverDTO.getAgents());
						obj.put("mineType",  FileNameUtil.buildMediaTypeByFullPath(adverDTO.getPath()));
						obj.put("url", buildFileRequstUrl(adverDTO.getPath()));
						filesArray.add(obj);
					}
				}
				arrayItem.put("files", filesArray);
				
				playListArray.add(arrayItem);
			}
			
			playContentJSON.put("playList", playListArray);
			writer.write(playContentJSON.toString());
		}
		
		writer.flush();
		writer.close();
	}
	
	@RequestMapping(value="/backend/ajaxEnablePlayContent.html", method=RequestMethod.POST)
	public String requestForEnableContent(HttpServletRequest request, HttpServletResponse response){
		
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		Integer indexNo = ServletRequestUtils.getIntParameter(request, "indexNo", 0);
		Integer pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 5);
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request,"contentUpgradeId","");
		
		List<PlayContentDTO> lists = playContentService.obtainEnablePlayContentDTOs(keyword, indexNo,pageSize); 
		int itemcount = playContentService.obtainEnablePlayContentDTOsAmount(keyword);
		
		request.setAttribute("playContentDTOs", lists);
		request.setAttribute("itemcount",itemcount);
		request.setAttribute("contentUpgradeId", contentUpgradeId);
		return "backend/play/template/playcontenttemplate";
	}
	
	
	
	
	
	private PlayContentOverviewPaging constructPaging(String filterName, int pageNo){
		PlayContentOverviewPaging paging = new PlayContentOverviewPaging(playContentService);
		paging.setFilterName(filterName);
		paging.setCurrentPageNumber(pageNo);
		return paging;
	}
	
	private void validateForm(BindingResult errors, PlayContentDTO dto, HttpServletRequest request){
		String uuid = dto.getUuid();
		String name = dto.getName();
		
		if(!StringUtils.hasText(name)){
			errors.rejectValue("name", "playcontent.name.empty", "名称不能为空");
		}else{
			if(name.length()>85){
				errors.rejectValue("name", "playcontent.name.length", "长度不超过85");
			}else{
				boolean exist = playContentService.obtainNameExists(uuid, name);
				if(exist){
					errors.rejectValue("name", "playcontent.name.exist", "名称已存在");
				}
			}
		}
		
//		if(StringUtils.hasText(de))
		
	}
	
	private String parseDateBySimplePattern(Date date, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if(date == null) return "";
		return sdf.format(date);
	}
	
	private String buildFileRequstUrl(String path) {
		// TODO 需要完善请求路径
		return path;
	}
	
	private String buildJSONDouble(Double d){
		return d == null ? "": d.toString();
	}
	
	private String buildJSONInteger(Integer i){
		return i == null ? "": i.toString();
	}
	
	private String buildJSONDate(Date date){
		return parseDateBySimplePattern(date, "yyyy.MM.dd");
	}
	
	private void initMenu(ModelMap model,String judgeStr){
		model.put(SessionKey.MENU_KEY, MENU);
		if(StringUtils.hasText(judgeStr)){
			model.put(SessionKey.SUB_MENU_KEY, SUB_MENU2);
		}else{
			model.put(SessionKey.SUB_MENU_KEY, SUB_MENU);
		}
	}
	
	
}
