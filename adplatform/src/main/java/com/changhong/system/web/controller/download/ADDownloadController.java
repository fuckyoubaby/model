package com.changhong.system.web.controller.download;


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
import com.changhong.system.domain.box.Box;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.service.AdverResourceService;
import com.changhong.system.service.BoxInfoService;
import com.changhong.system.web.facade.dto.AdverResourceDTO;

/**
 * 广告下载接口
 * @author shijinxiang
 *
 */
@Controller
public class ADDownloadController {
	
	@Value("${application.file.upload.path}")
	private String path;

	
	@Autowired
	private AdverResourceService adverResourceService; 
	@Autowired 
	private BoxInfoService boxInfoService;
	
	@RequestMapping("/download/addownload.html")
	public void download(HttpServletRequest request, HttpServletResponse response,String json){
	
		
		String mac = "";
		String body = "";
		String uuid = "";
		JSONObject object = new JSONObject();
		if (json!=null&&!(json.equals(""))) {
			object = JSONObject.parseObject(json);
			if (object.containsKey("mac")) {
				mac = object.getString("mac");
			}else{
				ApplicationLog.error(ADDownloadController.class, "处理失败：mac为空");
				response.addHeader("errorMsg", "mac is empty");
				return;
			}
			Box box = boxInfoService.obtainBox(mac);
			if (object.containsKey("body")) {
				body = object.getString("body");
			}else{
				ApplicationLog.error(ADDownloadController.class, "处理失败：body为空");
				response.addHeader("errorMsg", "body is empty");
				return;
			}
			
			if(box!=null){
				if(StringUtils.hasText(box.getEncryptCode())){
					String bodyEncyptResult = AesUtils.randomDecrypt(body, box.getEncryptCode());
					JSONObject bodyJson = JSONObject.parseObject(bodyEncyptResult);
					if (bodyJson.containsKey("uuid")) {
						uuid = bodyJson.getString("uuid");
					}
				}else{
					ApplicationLog.error(ADDownloadController.class, "处理失败：box(mac="+mac+")的动态密钥为空");
					response.addHeader("errorMsg", "box(mac="+mac+") code num is empty");
					return;
				}
			}else{
				ApplicationLog.error(ADDownloadController.class, "处理失败：mac("+mac+")无效");
				response.addHeader("errorMsg", "mac("+mac+") invalid");
				return;
			}
			
		}else{
			ApplicationLog.error(ADDownloadController.class, "处理失败：json 为空");
			response.addHeader("errorMsg", "json is empty");
			return;
		}
		AdverResourceDTO adverResourceDTO = adverResourceService.obtainDTOByUuid(uuid);
		
		if (adverResourceDTO==null) {
			ApplicationLog.error(ADDownloadController.class, "处理失败：uuid("+uuid+")无效");
			response.addHeader("errorMsg", "uuid("+uuid+") invalid");
			return ;
		}else {
			String filePath = path + adverResourceDTO.getPath();
			DownloadFileUtil.DownloadFileUtil(request, response, filePath);
		}
		
	}

}
