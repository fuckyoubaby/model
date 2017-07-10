package com.changhong.system.web.controller.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.AppManageService;
import com.changhong.system.web.facade.dto.AppManageDTO;
import com.changhong.system.web.facade.dto.UserDTO;
import com.changhong.system.web.paging.AppOverViewPaging;

/**
 * @author yujiawei
 */
@Controller
public class AppManagementController extends AbstractController {

	@Autowired
	private AppManageService appManageService;
	
	@Value("${application.file.upload.path}")
	private String uploadPath;
	
	//应用浏览部分
	@RequestMapping("/backend/appmanagement.html")
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		model.put(SessionKey.MENU_KEY,"RES_MANAGE");
		model.put(SessionKey.SUB_MENU_KEY, "APP_MANAGE");
		
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String filterName = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "filterName", ""));
        request.setAttribute("current", current);
        request.setAttribute("filterName", filterName);
        
        AppOverViewPaging paging = new AppOverViewPaging(appManageService);
        constructPaging(paging, current, filterName);
        List<AppManageDTO> Apps = paging.getItems();
        //System.out.println("apps"+Apps.get(0).getAppName());
        model.put("Apps", Apps);
        model.put("paging", paging);
        
		return new ModelAndView("backend/app/appoverview",model);
	}
	
	private void constructPaging(AppOverViewPaging paging, int current, String filterName) {
        paging.setCurrentPageNumber(current);
        paging.setFilterName(filterName);
    }
	
	/*********************************************应用表单***************************************************/
	@RequestMapping(value="/backend/appform.html",method=RequestMethod.GET)
	public String setAppForm(HttpServletRequest request, ModelMap model) {
		model.put(SessionKey.MENU_KEY,"RES_MANAGE");
		model.put(SessionKey.SUB_MENU_KEY, "APP_MANAGE");
		
		String appUuid = ServletRequestUtils.getStringParameter(request,"appUuid","");
		String current = ServletRequestUtils.getStringParameter(request, "current", "");
		String filterName =ServletRequestUtils.getStringParameter(request, "filterName","");
		
		request.setAttribute("current", current);
		request.setAttribute("filterName", filterName);
		
		AppManageDTO appManage = null;
		if(!StringUtils.hasText(appUuid)){
			appManage = new AppManageDTO();
		}else {
			appManage = appManageService.obtainAppByUuid(appUuid);
		}
		
		model.put("appManage",appManage);
		model.put("current", current);
		model.put("filterName", filterName);
		
		return "backend/app/appform";
	 }
	
	/**********************************************更改app状态************************************************/
	@RequestMapping("/backend/appstatus.html")
	protected String changeAppStatus(HttpServletRequest request, HttpServletResponse response)throws Exception{
	    String appUuid = ServletRequestUtils.getStringParameter(request,"appUuid","");
	    String current = ServletRequestUtils.getStringParameter(request, "current","");
	    String filterName = ServletRequestUtils.getStringParameter(request, "filterName","");
	    
	    appManageService.changeStatusForApp(appUuid);
		return "redirect:appmanagement.html?filterName="+filterName+"&current="+current;
	}
	
	@RequestMapping(value="/backend/appform.html",method=RequestMethod.POST)
	public String saveAppForm(HttpServletRequest request,@RequestParam("file")MultipartFile file,@ModelAttribute("appManage")AppManageDTO appManage,BindingResult errors,ModelMap model){
		String appUuid = ServletRequestUtils.getStringParameter(request, "appUuid","");
		String filterName=ServletRequestUtils.getStringParameter(request, "filterName","");
		String current = ServletRequestUtils.getStringParameter(request, "current","");
		
		request.setAttribute("current", current);
		request.setAttribute("filterName", filterName);
		appManage.setUuid(appUuid);
		validateAppForm(request, appManage, errors);
		if(appUuid.equals("")){              //新增
			if(!file.isEmpty()&&file!=null){
				String dir = uploadPath + "appcontent/";
				File filePath = new File(dir);
				if (!filePath.exists()) {
					filePath.mkdir();      // 创建目录
				}
				String filename = appManage.getAppVersion()+"_"+file.getOriginalFilename();
				appManage.setAppFileUrl(filename);
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
	        	return "backend/app/appform";
			}
		}else{                 //编辑的时候
			if(!file.isEmpty()){     
				String dir1 = uploadPath + "appcontent/";
				File filePath1 = new File(dir1);
				if (!filePath1.exists()) {
					filePath1.mkdir();      // 创建目录
				}
				if(appManageService.obtainAppByUuid(appUuid).getAppFileUrl()!=null){
					File oldFile = new File(dir1+appManageService.obtainAppByUuid(appUuid).getAppFileUrl());
					if(!oldFile.isDirectory()&&oldFile.exists()){
						oldFile.delete();            //如果选择新文件，就删除旧文件。删除了第二次的文件
					}
				}
				String filename1 = appManageService.obtainAppByUuid(appUuid).getAppVersion()+"_"+file.getOriginalFilename();  //版本号+文件名
				appManage.setAppFileUrl(filename1);
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
		}else{   //为空的时候
			appManage.setAppFileUrl(appManageService.obtainAppByUuid(appUuid).getAppFileUrl());
		}
	}
		if(errors.hasErrors()){
			model.putAll(errors.getModel());
			model.put("current", current);
			model.put("filterName", filterName);
			return "backend/app/appform";
		}
	    appManageService.changeAppDetails(appManage);
	    return "redirect:appmanagement.html?filterName=" + filterName + "&current=" + current;
	}
	
	/**
	 * 表单验证部分
	 * @param request
	 * @param appManage
	 * @param errors
	 */
	private void validateAppForm(HttpServletRequest request,AppManageDTO appManage,BindingResult errors){
		String appUuid = appManage.getUuid();
		String appName = appManage.getAppName();
		String appType = appManage.getAppType();
		String appVersion = appManage.getAppVersion();
		
		if(!StringUtils.hasText(appName)){
		    errors.rejectValue("appName", "app.appname.empty");	
		}
		if(!StringUtils.hasText(appType)){
			errors.rejectValue("appType", "app.apptype.empty");
		}
		if(!StringUtils.hasText(appVersion)){          //需要验证不重复
			errors.rejectValue("appVersion", "app.appversion.empty");
		}else{
			if(!StringUtils.hasText(appUuid)){      //新增
				if(appManageService.obtainAppVersionIsExist(appVersion)){
					errors.rejectValue("appVersion", "app.appversion.exist");
				}
			}else{               //编辑
				AppManageDTO appManageDTO = appManageService.obtainAppByUuid(appUuid);
				if(!appManageDTO.getAppVersion().equals(appVersion)){       //表单和原来的version不同就比较
					if(appManageService.obtainAppVersionIsExist(appVersion)){
						errors.rejectValue("appVersion", "app.appversion.exist");
					}
				}
			}
		}
	}
	
}
