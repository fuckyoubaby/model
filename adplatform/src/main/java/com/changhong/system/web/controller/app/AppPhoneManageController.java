package com.changhong.system.web.controller.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.AppPhoneManageService;
import com.changhong.system.web.facade.dto.AppPhoneManageDTO;
import com.changhong.system.web.paging.AppPhoneOverViewPaging;

/**
 * @author yujiawei
 * @date 2017年3月22日
 * @time 下午4:39:03
 */
@Controller
public class AppPhoneManageController {

	@Autowired
	private AppPhoneManageService appPhoneManageService;
	
	@Value("${application.file.upload.path}")
	private String uploadPath;
	
	@RequestMapping("/backend/appphonemanagement.html")
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		Map<String,Object> model = new HashMap<String,Object>();
		model.put(SessionKey.MENU_KEY,"RES_MANAGE");
		model.put(SessionKey.SUB_MENU_KEY, "APP_PHONE_MANAGE");
		
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String filterName = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "filterName", ""));
        request.setAttribute("current", current);
        request.setAttribute("filterName", filterName);
        
        AppPhoneOverViewPaging paging = new AppPhoneOverViewPaging(appPhoneManageService);
        constructPaging(paging,current,filterName);
        List<AppPhoneManageDTO> Apps = paging.getItems();
        
        model.put("Apps", Apps);
        model.put("paging", paging);
        
		return new ModelAndView("backend/app/appphoneoverview",model);
	}
	
	private void constructPaging(AppPhoneOverViewPaging paging,int current,String filterName){
		paging.setCurrentPageNumber(current);
        paging.setFilterName(filterName);
	}
	
	@RequestMapping(value="/backend/appphoneform.html",method=RequestMethod.GET)
	public String setAppForm(HttpServletRequest request, ModelMap model) {
		model.put(SessionKey.MENU_KEY,"RES_MANAGE");
		model.put(SessionKey.SUB_MENU_KEY, "APP_PHONE_MANAGE");
		
		String appUuid = ServletRequestUtils.getStringParameter(request,"appUuid","");
		String current = ServletRequestUtils.getStringParameter(request, "current", "");
		String filterName =ServletRequestUtils.getStringParameter(request, "filterName","");
		
		request.setAttribute("current", current);
		request.setAttribute("filterName", filterName);
		
		AppPhoneManageDTO appPhoneManage = null;
		if(!StringUtils.hasText(appUuid)){
			appPhoneManage = new AppPhoneManageDTO();
		}else {
			appPhoneManage = appPhoneManageService.obtainAppByUuid(appUuid);
		}
		model.put("appPhoneManage",appPhoneManage);
		model.put("current", current);
		model.put("filterName", filterName);
		return "backend/app/appphoneform";
	 }
	
	//更改app状态
	@RequestMapping("/backend/appphonestatus.html")
	protected String changeAppStatus(HttpServletRequest request, HttpServletResponse response)throws Exception{
	    String appUuid = ServletRequestUtils.getStringParameter(request,"appUuid","");
	    String current = ServletRequestUtils.getStringParameter(request, "current","");
	    String filterName = ServletRequestUtils.getStringParameter(request, "filterName","");
	    
	    appPhoneManageService.changeStatusForApp(appUuid);
		return "redirect:appphonemanagement.html?filterName="+filterName+"&current="+current;
	}
	
	
	@RequestMapping(value="/backend/appphoneform.html",method=RequestMethod.POST)
	public String saveAppForm(HttpServletRequest request,@RequestParam("file")MultipartFile file,@ModelAttribute("appPhoneManage")AppPhoneManageDTO appPhoneManage,BindingResult errors,ModelMap model){
		String appUuid = ServletRequestUtils.getStringParameter(request, "appUuid","");
		String filterName=ServletRequestUtils.getStringParameter(request, "filterName","");
		String current = ServletRequestUtils.getStringParameter(request, "current","");
		
		request.setAttribute("current", current);
		request.setAttribute("filterName", filterName);
		appPhoneManage.setUuid(appUuid);
		validateAppForm(request, appPhoneManage, errors);
		if(appUuid.equals("")){              //
			if(!file.isEmpty()&&file!=null){
				String dir = uploadPath + "phonecontent/";
				File filePath = new File(dir);
				if (!filePath.exists()) {
					filePath.mkdir();      // 创建目录
				}
				String filename = appPhoneManage.getAppVersion()+"_"+file.getOriginalFilename();
				appPhoneManage.setAppFileUrl(filename);
				try {
					 OutputStream os=new FileOutputStream(dir+filename);
					 InputStream is=file.getInputStream();
					 int temp;
					 while((temp=is.read())!=-1){
						 os.write(temp);
					 }
					 os.flush();
			         os.close();
			         is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				model.put("fileEmpty","请选择文件");
				model.put("fileClass", "nNote nError hideit");
	        	return "backend/app/appphoneform";
			}
		}else{                 
			if(!file.isEmpty()){     
				String dir1 = uploadPath + "phonecontent/";
				File filePath1 = new File(dir1);
				if (!filePath1.exists()) {
					filePath1.mkdir();      // 创建目录
				}
				if(appPhoneManageService.obtainAppByUuid(appUuid).getAppFileUrl()!=null){
					File oldFile = new File(dir1+appPhoneManageService.obtainAppByUuid(appUuid).getAppFileUrl());
					if(!oldFile.isDirectory()&&oldFile.exists()){
						oldFile.delete();            //如果选择新文件，就删除旧文件。删除了第二次的文件
					}
				}
				String filename1 = appPhoneManageService.obtainAppByUuid(appUuid).getAppVersion()+"_"+file.getOriginalFilename();  //版本号+文件名
				appPhoneManage.setAppFileUrl(filename1);
				try {
					 OutputStream os1=new FileOutputStream(dir1+filename1);
					 InputStream is1=file.getInputStream();
					 int temp1;
					 while((temp1=is1.read())!=-1){
						 os1.write(temp1);
					 }
					 os1.flush();
			         os1.close();
			         is1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}else{
			appPhoneManage.setAppFileUrl(appPhoneManageService.obtainAppByUuid(appUuid).getAppFileUrl());
		}
	}
		if(errors.hasErrors()){
			model.putAll(errors.getModel());
			model.put("current", current);
			model.put("filterName", filterName);
			return "backend/app/appphoneform";
		}
	    appPhoneManageService.changeAppDetails(appPhoneManage);
	    return "redirect:appphonemanagement.html?filterName=" + filterName + "&current=" + current;
	}
	
	//表单验证
	private void validateAppForm(HttpServletRequest request,AppPhoneManageDTO appPhoneManage,BindingResult errors){
		String appUuid = appPhoneManage.getUuid();
		String appName = appPhoneManage.getAppName();
		String appType = appPhoneManage.getAppType();
		String appVersion = appPhoneManage.getAppVersion();
		
		if(!StringUtils.hasText(appName)){
		    errors.rejectValue("appName", "app.appname.empty");	
		}
		if(!StringUtils.hasText(appType)){
			errors.rejectValue("appType", "app.apptype.empty");
		}
		if(!StringUtils.hasText(appVersion)){          //需要验证不重复
			errors.rejectValue("appVersion", "app.appversion.empty");
		}else{
			if(!StringUtils.hasText(appUuid)){
				if(appPhoneManageService.obtainAppVersionIsExist(appVersion)){
					errors.rejectValue("appVersion", "app.appversion.exist");
				}
			}else{
				AppPhoneManageDTO appPhoneManageDTO = appPhoneManageService.obtainAppByUuid(appUuid);
				if(!appPhoneManageDTO.getAppVersion().equals(appVersion)){   //表单和原来的version不同就比较
					if(appPhoneManageService.obtainAppVersionIsExist(appVersion)){
						errors.rejectValue("appVersion", "app.appversion.exist");
					}
				}
			}
		}
	}
	
}
