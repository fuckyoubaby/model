package com.changhong.system.web.controller.download;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.changhong.common.utils.AesUtils;
import com.changhong.common.utils.DownloadFileUtil;
import com.changhong.common.utils.DownloadUtil;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.upgrade.ContentUpgradeArea;
import com.changhong.system.domain.upgrade.ContentUpgradeBox;
import com.changhong.system.domain.upgrade.ContentUpgradeCommunity;
import com.changhong.system.service.AdverResourceService;
import com.changhong.system.service.BoxInfoService;
import com.changhong.system.service.CommunityService;
import com.changhong.system.service.ContentUpgradeAreaService;
import com.changhong.system.service.ContentUpgradeBoxService;
import com.changhong.system.service.ContentUpgradeCommunityService;
import com.changhong.system.web.facade.dto.AdverResourceDTO;
import com.changhong.system.web.facade.dto.BoxDTO;
import com.changhong.system.web.facade.dto.CommunityDTO;

/**
 * 广告下载接口
 * @author shijinxiang
 *
 */
@Controller
public class PhoneADDownloadController {
	
	@Value("${application.file.upload.path}")
	private String path;

	
	@Autowired
	private AdverResourceService adverResourceService; 
	@Autowired 
	private BoxInfoService boxInfoService;
	
	@RequestMapping("/download/phoneaddownload.html")
	public void download(HttpServletRequest request, HttpServletResponse response,String json){
	
		
		String mac = "";
		String body = "";
		String uuid = "";
		JSONObject object = new JSONObject();
		if (json!=null&&!(json.equals(""))) {
			object = JSONObject.parseObject(json);
			if (object.containsKey("mac")) {
				mac = object.getString("mac");
			}
			//Box box = boxInfoService.obtainBox(mac);
			if (object.containsKey("body")) {
				body = object.getString("body");
			}
			String bodyEncyptResult = AesUtils.fixDecrypt(body);
			JSONObject bodyJson = JSONObject.parseObject(bodyEncyptResult);
			if (bodyJson.containsKey("uuid")) {
				uuid = bodyJson.getString("uuid");
			}
			
		}
		AdverResourceDTO adverResourceDTO = adverResourceService.obtainDTOByUuid(uuid);
		
		if (adverResourceDTO==null) {
			return ;
		}else {
			String filePath = path + adverResourceDTO.getPath();
			DownloadFileUtil.DownloadFileUtil(request, response, filePath);
		}
		
	}

}
