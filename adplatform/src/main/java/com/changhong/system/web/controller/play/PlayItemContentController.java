package com.changhong.system.web.controller.play;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.PlayItemContentService;
import com.changhong.system.web.facade.dto.PlayItemContentDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-17 
 * Time: 17:22:22
 */

@Controller
public class PlayItemContentController {

	@Autowired
	private PlayItemContentService playItemContentService;
	
	protected String backContentType = "application/json; charset=utf-8";
	
	
	@RequestMapping(value="/backend/ajaxAddItemContent.html",method = RequestMethod.POST)
	public void ajaxAddItemContent(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType(backContentType);
		
		String resourceUuid = ServletRequestUtils.getStringParameter(request, "resourceUuid", "");
		String itemUuid = ServletRequestUtils.getStringParameter(request, "itemUuid", "");
		double duration = ServletRequestUtils.getDoubleParameter(request, "duration", -1.0);
		int repeat = ServletRequestUtils.getIntParameter(request, "repeat", -1);
		int index = ServletRequestUtils.getIntParameter(request, "index", -1);
		boolean isgif = ServletRequestUtils.getBooleanParameter(request, "isgif", false);
		
		JSONObject resultObj = new JSONObject();
		if(!StringUtils.hasText(itemUuid) || !StringUtils.hasText(resourceUuid)){
			resultObj.put("result", false);
		}else{
			if(index==-1){
				resultObj.put("result", false);
			}else{
				if(!isgif && duration==-1.0 ){
					resultObj.put("result", false);
				}else{
					PlayItemContentDTO dto = new PlayItemContentDTO();
					dto.setResourceUuid(resourceUuid);
					dto.setPlayItemUuid(itemUuid);
					if( duration !=-1.0){
						dto.setDuration(duration);
					}	
					if(index !=-1){
						dto.setIndex(index);
					}
					if(repeat !=-1){
						dto.setRepeat(repeat);
					}
					
					String uuid = playItemContentService.saveDTO(dto);
					if(StringUtils.hasText(uuid)){
						resultObj.put("result", true);
						resultObj.put("msg", "配置成功");
						resultObj.put("uuid", uuid);
					}else{
						resultObj.put("result", false);
						resultObj.put("msg", "配置失败");
						resultObj.put("uuid", uuid);
					}
					
				}
			}
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(resultObj.toString());
		writer.flush();
		writer.close();
	}
	
	@RequestMapping(value="/backend/ajaxDeleteItemContent.html", method=RequestMethod.POST)
	public void ajaxDeleteItemContent(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType(backContentType);
		String uuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
		String itemUuid = ServletRequestUtils.getStringParameter(request, "itemUuid", "");
		int index = ServletRequestUtils.getIntParameter(request, "index", -1);
		JSONObject resultObj = new JSONObject();
		
		if(!StringUtils.hasText(uuid) || !StringUtils.hasText(itemUuid) || index==-1){
			resultObj.put("result", false);
		}else{
				//1、删除，2、重新排序
				playItemContentService.deleteByUuid(uuid);
				playItemContentService.changeIndexByItemUuid(itemUuid, index);
				resultObj.put("result", true);
				resultObj.put("msg", "删除成功");
			
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(resultObj.toString());
		writer.flush();
		writer.close();
	}
	
	@RequestMapping(value="/backend/ajaxReplaceItemContent.html", method=RequestMethod.POST)
	public void ajaxReplaceItemContent(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType(backContentType);
		String currentUuid = ServletRequestUtils.getStringParameter(request, "currentUuid", "");
		String preUuid = ServletRequestUtils.getStringParameter(request, "preUuid", "");
		int currentIndex = ServletRequestUtils.getIntParameter(request, "currentIndex",-1);
		int preIndex = ServletRequestUtils.getIntParameter(request, "preIndex",-1);
		
		JSONObject resultObj = new JSONObject();
		
		if(!StringUtils.hasText(currentUuid) || !StringUtils.hasText(preUuid) || currentIndex<1 || preIndex<1){
			resultObj.put("result", false);
		}else{
				//1、删除，2、重新排序
				playItemContentService.changeIndexByUuid(preUuid, preIndex);
				playItemContentService.changeIndexByUuid(currentUuid, currentIndex);
				resultObj.put("result", true);
				resultObj.put("msg", "调整成功");
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(resultObj.toString());
		writer.flush();
		writer.close();
	}
	
	
	@RequestMapping(value="/backend/ajaxUpdateItemContent.html", method=RequestMethod.POST)
	public void ajaxUpdateItemContent(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType(backContentType);
		String uuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
		int repeat = ServletRequestUtils.getIntParameter(request, "repeat",-1);
		double duration = ServletRequestUtils.getDoubleParameter(request, "duration",-1.0);
		boolean isgif = ServletRequestUtils.getBooleanParameter(request, "isgif", false);
		
		JSONObject resultObj = new JSONObject();
		
		if(!StringUtils.hasText(uuid) ){
			resultObj.put("result", false);
		}else{
			if(!isgif && duration==-1.0){
				resultObj.put("result", false);
			}else{
				playItemContentService.updatePlayConfigInfo(uuid, repeat, duration);
				resultObj.put("result", true);
				resultObj.put("msg", "配置成功");
			}
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(resultObj.toString());
		writer.flush();
		writer.close();
	}
	
}
