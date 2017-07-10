package com.changhong.system.web.controller.download;


import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.changhong.common.utils.AesUtils;
import com.changhong.common.utils.DownloadFileUtil;
import com.changhong.system.domain.app.AppManage;
import com.changhong.system.domain.app.AppUpgradeEntity;
import com.changhong.system.domain.box.Box;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.service.AppManageService;
import com.changhong.system.service.AppStrategyAreaService;
import com.changhong.system.service.AppStrategyBoxService;
import com.changhong.system.service.AppStrategyCommunityService;
import com.changhong.system.service.AppStrategyService;
import com.changhong.system.service.BoxInfoService;
import com.changhong.system.service.CommunityService;
import com.changhong.system.web.facade.dto.BoxDTO;
import com.changhong.system.web.facade.dto.CommunityDTO;
/**
 * apk下载接口
 * @author shijinxiang
 *
 */
@Controller
public class ApkDowmloadController {

	@Value("${application.file.upload.path}")
	private String path;
	
	@Autowired
	private BoxInfoService boxInfoService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private AppStrategyAreaService appStrategyAreaService;
	@Autowired
	private AppStrategyCommunityService appStrategyCommunityService;
	@Autowired
	private AppStrategyBoxService appStrategyBoxService;
	@Autowired
	private AppStrategyService appStrategyService;
	@Autowired
	private AppManageService appManageService;
	
	
	@RequestMapping("/download/apkversionacquire.html")
	public void versionAcquired(HttpServletRequest request, HttpServletResponse response,String json) throws IOException{
		
		String mac = "";
		int status = 1000;
		String message ="";
		JSONObject resultObj = new JSONObject();
		
		response.setContentType("application/json;charset=utf-8");
		Writer writer = response.getWriter();
		
		if (json!=null&&!(json.equals(""))) {
			JSONObject object = JSONObject.parseObject(json);
			if (object.containsKey("mac")) {
				mac = object.getString("mac");
			}else{
				status = 1003;
				message = "json参数异常";
				buidlResult(resultObj, message, status, writer);
				return;
			}
		}else {
			status = 1003;
			message = "没有获取mac地址"; 
			buidlResult(resultObj, message, status,writer);
			return;
		}
		
		//设备的boxID，所在社区的id和所在地区的id
		
		
		if(!StringUtils.hasText(mac)){
			status = 1001;
			message = "无效的mac地址"; 
			buidlResult(resultObj, message, status,writer);
		}
		
		
		String community_uuid = "";
		String area_uuid = "";
		mac= mac.trim();
		
		BoxDTO boxDTO = boxInfoService.obtainSearchBox(mac);
		Box box = boxInfoService.obtainBox(mac);
				
		if (boxDTO==null) {
			status = 1001;
			message = "无效的mac地址"; 
			buidlResult(resultObj, message, status,writer);
		}
		
		community_uuid = boxDTO.getCommunityUuid();
		
		CommunityDTO communityDTO = communityService.obtainCommunityByUuid(community_uuid, false);
		
		area_uuid = communityDTO.getAreaUuid();
		
		String apk_version="";

//		List<AppStrategyAreaDTO> appStrategyAreaDTOs = appStrategyAreaService.obtainAppStrategyAreaDTOsByAreaId(area_uuid);
//		List<AppStrategyCommunityDTO> appStrategyCommunityDTOs = appStrategyCommunityService.obtainCommunityDTOsByCommunityId(community_uuid);
//		List<AppStrategyBoxDTO> appStrategyBoxDTOs = appStrategyBoxService.obtainAppStrategyBoxDTOsByMac(mac);
//		
//		List<AppStrategy> appStrategies = appStrategyService.obtainAllAppStrategties();
		
//		String strategyUuid="";
//		String tempUUID="";
//		for (int i = 0; i < appStrategies.size(); i++) {
//			for (int j = 0; j < appStrategyAreaDTOs.size(); j++) {
//				if (appStrategyAreaDTOs.get(j).getStrategyUuid().equals(appStrategies.get(i).getUuid())) {
//					strategyUuid = appStrategies.get(i).getUuid();
//					apk_version = appStrategies.get(i).getAppVersion();
//					tempUUID = appStrategyAreaDTOs.get(j).getUuid();
//					break;
//				}
//			}
//			
//			for (int j = 0; j < appStrategyCommunityDTOs.size(); j++) {
//				if (appStrategyCommunityDTOs.get(j).getStrategyUuid().equals(appStrategies.get(i).getUuid())) {
//					String temp = appStrategyCommunityDTOs.get(j).getUuid();
//					if (temp.compareTo(tempUUID)>0) {
//						strategyUuid = appStrategies.get(i).getUuid();
//						apk_version = appStrategies.get(i).getAppVersion();
//						tempUUID = temp;
//					}
//					break;
//				}
//			}
//			
//			for (int j = 0; j < appStrategyBoxDTOs.size(); j++) {
//				if (appStrategyBoxDTOs.get(j).getStrategyUuid().equals(appStrategies.get(i).getUuid())) {
//					String temp = appStrategyBoxDTOs.get(j).getUuid();
//					if (temp.compareTo(tempUUID)>0) {
//						strategyUuid = appStrategies.get(i).getUuid();
//						apk_version = appStrategies.get(i).getAppVersion();
//						tempUUID = temp;
//					}
//					break;
//				}
//			}
//			if (!apk_version.equals("")) {//如果找到了一个配置信息，跳出循环
//				break;
//			}
//		}
		
		
		AppUpgradeEntity ape = appStrategyService.obtainLatestAppVersionByInfos(area_uuid, community_uuid, mac);
		
		apk_version =  ape==null ? "" : ape.getApkVersion();
		ApplicationLog.info(ApkDowmloadController.class, "latest apk_version is "+apk_version);
		JSONObject bodyObject = new JSONObject();
		bodyObject.put("apk_version", apk_version);
		resultObj.put("body", AesUtils.randomEncrypt(bodyObject.toJSONString(), box.getEncryptCode()));
		
		resultObj.put("message", "处理成功");
		resultObj.put("status", 1000);
		writer.write(resultObj.toJSONString());
		writer.flush();
		writer.close();
	}

	@RequestMapping("/download/apkdownload.html")
	public void download(HttpServletRequest request, HttpServletResponse response,String json){
		
		String mac = "";
		String body ="";
		String apk_version ="";
		ApplicationLog.info(getClass(), "json 解密前："+json);
		if (!StringUtils.hasText(json)) {
			ApplicationLog.error(ApkDowmloadController.class, "处理失败：json为空");
			response.addHeader("errorMsg", "json is empty");
			return;
		}else{
			
			JSONObject object = JSONObject.parseObject(json);
			
			if (!object.containsKey("mac")) {
				ApplicationLog.error(ApkDowmloadController.class, "处理失败：无mac地址");
				response.addHeader("errorMsg", "mac is empty");
				return;
			}
			
			mac = object.getString("mac");
			Box box = boxInfoService.obtainBox(mac);
			
			if(box==null){
				ApplicationLog.error(ApkDowmloadController.class, "处理失败：无效mac地址<"+mac+">");
				response.addHeader("errorMsg", "mac invalid");
				return;
			}
			
			if (object.containsKey("body")) {
				body = object.getString("body");
			}
			
			if(!StringUtils.hasText(body)){
				ApplicationLog.error(ApkDowmloadController.class, "处理失败：body为空");
				response.addHeader("errorMsg", "body is empty");
				return;
			}else{
				if(!StringUtils.hasText(box.getEncryptCode())){
					ApplicationLog.error(ApkDowmloadController.class, "处理失败：设备(uuid="+box.getUuid()+", mac="+box.getMac()+")的动态密钥为空");
					response.addHeader("errorMsg", "box code num is empty");
					return;
				}
				
				String bodyEncyptResult = AesUtils.randomDecrypt(body, box.getEncryptCode());
				JSONObject bodyJson = JSONObject.parseObject(bodyEncyptResult);
				ApplicationLog.info(getClass(), "apkversion 解密前："+body+"解密后"+bodyEncyptResult);
				if (bodyJson.containsKey("apk_version")) {
					apk_version = bodyJson.getString("apk_version");					
				}
				
				AppManage appManage = appManageService.obtainAppManageByAppVersion(apk_version);
				
				String downLoadPath = "";
				if (appManage==null) {
					ApplicationLog.error(ApkDowmloadController.class, "处理失败：无效的apk_version<"+apk_version+">");
					response.addHeader("errorMsg", "apk_version invalid");
					return ;
				}else {
					downLoadPath = path+"appcontent/"+appManage.getAppFileUrl();
					DownloadFileUtil.DownloadFileUtil(request, response, downLoadPath);
				}
				
			}
		}
		
	}
	
	
	private void buidlResult(JSONObject result, String message, int status, Writer writer) throws IOException{
		result.put("message", message);
		result.put("status", status);
		result.put("body", "");
		
		writer.write(result.toJSONString());
		writer.flush();
		writer.close();
	}
}
