package com.changhong.system.web.controller.download;

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
import com.changhong.system.domain.app.AppPhoneManage;
import com.changhong.system.service.AppPhoneManageService;

@Controller
public class PhoneApkDownloadController {

	@Value("${application.file.upload.path}")
	private String path;
	
	@Autowired
	private AppPhoneManageService appPhoneManageService;
	
	//取得新版版本信息
	@RequestMapping("/download/phoneapkversion.html")
	public void getPhoneApkVersion(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String result = null;
		//取出最新一条数据
		AppPhoneManage appPhoneManage = appPhoneManageService.obtainLastAppPhoneManage();
		Writer writer = response.getWriter();
		JSONObject obj = new JSONObject();
		if(StringUtils.hasText(appPhoneManage.getAppVersion())){
			result = AesUtils.fixEncrypt(appPhoneManage.getAppVersion());
			obj.put("apkversion", result);
			obj.put("message", "处理成功");
			obj.put("status", 1000);
		}else {
			obj.put("message", "处理失败");
			obj.put("status", 1001);
		}
		writer.write(obj.toString());
		writer.flush();
		writer.close();
	}
	
	//下载
	@RequestMapping("/download/phoneappdownload.html")
	public void download(HttpServletRequest request, HttpServletResponse response)throws Exception{
		AppPhoneManage appPhoneManage = appPhoneManageService.obtainLastAppPhoneManage();
		if(StringUtils.hasText(appPhoneManage.getAppFileUrl())){
			String filePath = path+"phonecontent/"+appPhoneManage.getAppFileUrl();
			DownloadFileUtil.DownloadFileUtil(request, response, filePath);
		}else {
			return;
		}
	}
}
