package com.changhong.system.web.controller.download;


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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.common.utils.AesUtils;

import com.changhong.common.utils.FileNameUtil;

import com.changhong.system.domain.box.Box;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.service.BoxInfoService;
import com.changhong.system.service.CommunityService;
import com.changhong.system.service.ContentUpgradeAreaService;
import com.changhong.system.service.ContentUpgradeBoxService;
import com.changhong.system.service.ContentUpgradeCommunityService;
import com.changhong.system.service.ContentUpgradeService;
import com.changhong.system.service.PlayContentService;
import com.changhong.system.service.PlayItemContentService;
import com.changhong.system.web.facade.dto.AdverResourceDTO;
import com.changhong.system.web.facade.dto.BoxDTO;
import com.changhong.system.web.facade.dto.CommunityDTO;
import com.changhong.system.web.facade.dto.PlayContentDTO;
import com.changhong.system.web.facade.dto.PlayItemContentDTO;
import com.changhong.system.web.facade.dto.PlayItemDTO;

/**
 * 广告下载接口
 * @author shijinxiang
 *
 */
@Controller
public class ADConfigDownloadController {
	
	@Value("${application.file.upload.path}")
	private String path;

	
	@Autowired
	private BoxInfoService boxInfoService;
	@Autowired
	private ContentUpgradeAreaService contentUpgradeAreaService;
	@Autowired
	private ContentUpgradeCommunityService contentUpgradeCommunityService;
	@Autowired
	private ContentUpgradeBoxService contentUpgradeBoxService;
	@Autowired
	private ContentUpgradeService contentUpgradeService;
	@Autowired
	private PlayContentService playContentService;
	@Autowired 
	private PlayItemContentService playItemContentService;
	
	@Autowired
	private CommunityService communityService;
	
	@RequestMapping("/download/configdownload.html")
	public void download(HttpServletRequest request, HttpServletResponse response,String json) throws IOException{
		String mac = "";
		
		response.setContentType("application/json;charset=utf-8");
		Writer writer = response.getWriter();
		if (json!=null&&!(json.equals(""))) {
			JSONObject object = JSONObject.parseObject(json);
			if (object.containsKey("mac")) {
				mac = object.getString("mac");
			}
		}else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("message", "没有获取到mac地址");
			jsonObject.put("body", "");
			jsonObject.put("status", 1002);
			
			writer.write(jsonObject.toString());
			
			writer.flush();
			writer.close();
			return;
		}
		
		
		//设备的boxID，所在社区的id和所在地区的id
		String box_uuid="";
		String community_uuid = "";
		String area_uuid = "";
		
		BoxDTO boxDTO = boxInfoService.obtainSearchBox(mac);
		Box box = boxInfoService.obtainBox(mac);
		
		if (boxDTO == null) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("message", "mac地址无效");
			jsonObject.put("body", "");
			jsonObject.put("status", 1001);
			
			writer.write(jsonObject.toString());
			
			writer.flush();
			writer.close();
			return;
		}
		
		box_uuid = boxDTO.getUuid();
		community_uuid = boxDTO.getCommunityUuid();
		CommunityDTO communityDTO = communityService.obtainCommunityByUuid(community_uuid, false);
		area_uuid = communityDTO.getAreaUuid();
		String upgradeId="";

		
//		List<ContentUpgradeAreaDTO> contentUpgradeAreaDTOs = contentUpgradeAreaService.obtainUpgradeAreasByAreaId(area_uuid);
//		List<ContentUpgradeCommunityDTO> contentUpgradeCommunitieDTOs = contentUpgradeCommunityService.obtainUpgradeCommunitiesByCommunityId(community_uuid);
//		List<ContentUpgradeBoxDTO> contentUpgradeBoxDTOs = contentUpgradeBoxService.obtainUpgradeBoxesByBoxId(box_uuid);
//		
//		List<ContentUpgrade> contentUpgrades = contentUpgradeService.obtainContentUpgrades();  //所有可用配置文件
//		
//		
//		String tempUUid = "";//记录查mac和资源对应关系表的uuid，用于判断是不是最新的
//		for (int i = 0; i < contentUpgrades.size(); i++) {
//			for (int j = 0; j < contentUpgradeAreaDTOs.size(); j++) {
//				if (contentUpgradeAreaDTOs.get(j).getUpgradeUuid().equals(contentUpgrades.get(i).getUuid())) {
//					tempUUid = contentUpgradeAreaDTOs.get(j).getUuid();
//					upgradeId = contentUpgrades.get(i).getUuid();
//					break;
//				}
//			}
//			for (int j = 0; j < contentUpgradeCommunitieDTOs.size(); j++) {
//				if (contentUpgradeCommunitieDTOs.get(j).getUpgradeUuid().equals(contentUpgrades.get(i).getUuid())) {
//					String temp = contentUpgradeCommunitieDTOs.get(j).getUuid();
//					if (temp.compareTo(tempUUid)>0) {
//						tempUUid = temp;
//						upgradeId = contentUpgrades.get(i).getUuid();
//					}
//					break;
//				}
//			}
//			for (int j = 0; j < contentUpgradeBoxDTOs.size(); j++) {
//				if (contentUpgradeBoxDTOs.get(j).getUpgradeUuid().equals(contentUpgrades.get(i).getUuid())) {
//					String temp = contentUpgradeBoxDTOs.get(j).getUuid();
//					if (temp.compareTo(tempUUid)>0) {
//						tempUUid = temp;
//						upgradeId = contentUpgrades.get(i).getUuid();
//					}
//					break;
//				}
//			}
//			if (!upgradeId.equals("")) {//如果找到了一个配置信息，跳出循环
//				break;
//			}
//		}
		
		upgradeId = contentUpgradeService.obtainLatestUpgradeUUIDByBox(area_uuid, community_uuid, box_uuid);
		ApplicationLog.info(ADConfigDownloadController.class, "latest upgraId is "+upgradeId);
		
		if(!StringUtils.hasText(upgradeId)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("message", "处理失败");
			jsonObject.put("body", "");
			jsonObject.put("status", 1001);
			
			writer.write(jsonObject.toString());
		}else{
			List<PlayContentDTO> pContentDTOs = playContentService.obtainPlayContentDTOsByContentResourceId(upgradeId);
			
			
			PlayContentDTO pcDTO = null;
			if (pContentDTOs.size()>0) {
				pcDTO = pContentDTOs.get(0);
				
				
				List<PlayItemDTO> playItemDTOLists = playContentService.obtainPlayItemByUuid(pcDTO.getUuid());
				
				
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
							obj.put("uuid", temp.getResourceUuid());
							obj.put("index", temp.getIndex().toString());
							obj.put("duration", buildJSONDouble(temp.getDuration()));
							obj.put("repeat", buildJSONInteger(temp.getRepeat()));
							obj.put("advertiser", adverDTO.getAdvertiser());
							obj.put("agency", adverDTO.getAgents());
							obj.put("mineType",  FileNameUtil.buildMediaTypeByFullPath(adverDTO.getPath()));
							obj.put("url", adverDTO.getPath());
							filesArray.add(obj);
						}
					}
					arrayItem.put("files", filesArray);
					
					playListArray.add(arrayItem);
				}
				
				playContentJSON.put("playList", playListArray);
				
				String result = AesUtils.randomEncrypt(playContentJSON.toString(), box.getEncryptCode());
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("body", result);
				jsonObject.put("message", "处理成功");
				jsonObject.put("status", 1000);
				
				writer.write(jsonObject.toString());
			}
		}
		
		writer.flush();
		writer.close();
		
	}

	
	private String parseDateBySimplePattern(Date date, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if(date == null) return "";
		return sdf.format(date);
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
	
	
}
