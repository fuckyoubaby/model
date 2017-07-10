package com.changhong.system.web.controller.box;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.common.domain.ResultObject;
import com.changhong.common.utils.FileNameUtil;
import com.changhong.common.utils.MyListUtils;
import com.changhong.common.utils.ValidationUtils;
import com.changhong.common.utils.excel.poi.handler.BoxMacHandler;
import com.changhong.common.utils.excel.poi.helper.ExcelHelper;
import com.changhong.common.utils.excel.poi.helper.XssfExcelHelper;
import com.changhong.common.utils.excel.poi.model.BoxMacModel;
import com.changhong.common.utils.excel.poi.model.MacInfo;
import com.changhong.common.utils.excel.poi.tools.DateUtils;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.domain.box.BoxMac;
import com.changhong.system.domain.box.BoxMacStatus;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.service.BoxMacService;
import com.changhong.system.web.facade.dto.BoxMacDTO;
import com.changhong.system.web.paging.BoxMacOverviewPaging;
import com.changhong.system.web.paging.PlayContentOverviewPaging;
/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-15 
 * Time: 16:15:57
 *
 */
@Controller
public class BoxMacController {
	
	@Autowired
	private BoxMacService boxMacService;
	
	private final String responseType= "application/json;charset=utf-8";
	
	private final String MENU_KEY="ERROR_MANAGE";
	private final String SUB_MENU_KEY = "MAC_MANAGE";
	
	@Value("${application.mac.template.path}")
	private String excelPath;
	
	@Value("${application.mac.template.file}")
	private String excelFileName;
	
	@RequestMapping(value="/backend/boxmacindex.html")
	public String boxMacIndex(HttpServletRequest request, ModelMap model){
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		
		BoxMacOverviewPaging paging = constructPaging(filterName, current);
		List<BoxMacDTO> boxmacs = paging.getItems();
		
		model.put("filterName", filterName);
		model.put("current", current);
		model.put("boxmacs", boxmacs);
		model.put("paging",paging);
		initMenu(model);
		
		return "backend/box/boxmacmanage";
	}
	
	
	private BoxMacOverviewPaging constructPaging(String filterName,
			int current) {
		BoxMacOverviewPaging paging = new BoxMacOverviewPaging(boxMacService, filterName);
		paging.setCurrentPageNumber(current);
		return paging;
	}


	@RequestMapping(value="/backend/boxmacadd.html", method=RequestMethod.GET)
	public String singleAddRequest(HttpServletRequest request, ModelMap model){
		
		String boxmacUuid = ServletRequestUtils.getStringParameter(request, "boxmacUuid","");
		
		BoxMacDTO boxmacdto = null;
		if(StringUtils.hasText(boxmacUuid)){
			boxmacdto = boxMacService.obtainBoxMacDTO(boxmacUuid);
		}else{
			boxmacdto = new BoxMacDTO();
		}
		
		model.put("boxmac",boxmacdto);
		model.put("status", BoxMacStatus.getSavedOptions());
		
		initMenu(model);
		
		return "backend/box/boxmacaddform";
	}
	
	
	@RequestMapping(value="/backend/boxmacadd.html", method=RequestMethod.POST)
	public String saveOrUpdate(HttpServletRequest request, @ModelAttribute("boxmac") BoxMacDTO boxmacDTO,
			BindingResult errors, ModelMap model){
		
		String uuid = ServletRequestUtils.getStringParameter(request, "boxmacUuid", "");
		String macStatus = ServletRequestUtils.getStringParameter(request, "macStatus", "");
		
		boxmacDTO.setUuid(uuid);
		boxmacDTO.setMacStatus(macStatus);
		validatorFormData(request,errors,boxmacDTO);
		
		if(errors.hasErrors()){
			model.putAll(errors.getModel());
			model.put("boxmac", boxmacDTO);
			model.put("status", BoxMacStatus.getSavedOptions());
			initMenu(model);
			
			return "backend/box/boxmacaddform";
		}
		
		boxMacService.saveBoxMacDTO(boxmacDTO);
		
		return "redirect:boxmacindex.html";
	}
	
	@RequestMapping("/backend/boxmacstatusdisabled.html")
	public String disableBoxMac(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String boxmacUuid = ServletRequestUtils.getStringParameter(request, "boxmacUuid", "");
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		
		if(StringUtils.hasText(boxmacUuid)){
			boxMacService.changeStatus(boxmacUuid, BoxMacStatus.B_DISABLE);
		}
		
		return "redirect:boxmacindex.html?filterName="+filterName+"&current="+current;
	}
	
	@RequestMapping("/backend/boxmacstatusactived.html")
	public String activeBoxMac(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String boxmacUuid = ServletRequestUtils.getStringParameter(request, "boxmacUuid", "");
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		
		if(StringUtils.hasText(boxmacUuid)){
			boxMacService.changeStatus(boxmacUuid, BoxMacStatus.B_INITE);
		}
		
		return "redirect:boxmacindex.html?filterName="+filterName+"&current="+current;
	}
	
	@RequestMapping("/backend/boxmacdelete.html")
	public String deleteBoxMac(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String boxmacUuid = ServletRequestUtils.getStringParameter(request, "boxmacUuid", "");
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName", "");
		int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		
		ResultObject result = boxMacService.obtainMacCanDeleted(boxmacUuid);
		if(result.isResult()){
			boxMacService.deleteByUuid(boxmacUuid);
		}
		
		return "redirect:boxmacindex.html?filterName="+filterName+"&current="+current;
	}
	
	@RequestMapping("/backend/boxmacbatchimport.html")
	public String toBoxMacBatchImport(ModelMap model){
		initMenu(model);
		return "backend/box/boxmacbatchimport";
	}
	
	
	@RequestMapping(value="/backend/ajaxboxmacfileupload.html", method=RequestMethod.POST)
	public void ajaxBoxMacUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file ,HttpServletResponse response) throws IOException {
			/**
			 * 1、 上传文件，接收             √
			 * 2、改造文件上传过程，使用AJAX上传   √
			 * 3、后台接收，打印返回    √
			 * 4、前台进度条组件，AJAX上传 onprogress 监听    √
			 * 5、前台处理过程显示    √
			 * 6、后台file转换，EXCEL解析  
			 * 7、批量添加
			 * 8、JSON 返回
			 */
			 response.setContentType(responseType);
			
			//接受了文件，下一步是解析文件并处理
			String fileName = file.getOriginalFilename();
	        CommonsMultipartFile cf= (CommonsMultipartFile)file; 
	        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
	        File f = fi.getStoreLocation();
	        
	        int originalSize,duplicateRemovedSize, deleteSize;

	        JSONObject json = new JSONObject();
			try {
				//excelTest(f);
				
				List<BoxMacModel> macs = BoxMacHandler.handlerWithFile(f, fileName);
				//原始记录数
				originalSize = macs.size();
				
				//EXCEL自我去重数
				List<BoxMacModel> temp1 = MyListUtils.removeDuplicateByHashSet(macs);

				// 校验，检测符合格式的有效数据
				List<BoxMac> boxmacs = new ArrayList<BoxMac>();
				for(BoxMacModel boxmac1: temp1){
					if(macValidate(boxmac1.getMac())){
						boxmacs.add(new BoxMac(boxmac1.getMac(), BoxMacStatus.B_INITE));
					}
				}
				duplicateRemovedSize =  boxmacs.size();
				//批量保存后，重复的数量
				boxMacService.saveBoxMacs(boxmacs);
				//注意保存和删除不能放在一个Service方法中，因为session没刷新，数据没提交，删重操作永远是0
				deleteSize = boxMacService.deleteBatchImport();
				json.put("result", true);
				json.put("msg","此次插入了"+(duplicateRemovedSize-deleteSize)+"个MAC。其中识别了"+originalSize+"个, 有效"+duplicateRemovedSize+"个, 与库中重复"+deleteSize+"个。");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("result", false);
				json.put("msg", " EXCEL 文档解析错误，请刷新后上传规范文档! ");
			}
			
			PrintWriter writer = response.getWriter();
			
			writer.print(json.toString());
			writer.flush();
			writer.close();
	}
	@RequestMapping(value="/backend/boxmactemplatedownload.html")
	public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException{
		ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String basePath = context.getRealPath("/");
		String templatePath = basePath+excelPath;
		File templateDir = new File(basePath+excelPath);
		if(!templateDir.exists()){
			templateDir.mkdirs();
		}
		String fileName = StringUtils.hasText(excelFileName)?excelFileName:"template.xlsx";
		
		File templateFile = new File(templatePath+File.separator+fileName);
		if(!templateFile.exists()){
			try{
				BoxMacHandler.writerTemplate(templateFile, fileName);
				ApplicationLog.info(getClass(), "create new excel template file: "+fileName);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		String dfileName = new String( ("MAC地址批量导入模板."+FileNameUtil.getFileSuffix(fileName)).getBytes("UTF-8"),"iso8859-1");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", dfileName);
		
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(templateFile), headers, HttpStatus.CREATED);
	}
	
	
	
	@RequestMapping(value="/backned/ajaxBoxMacsLoad.html")
	public void ajaxBoxMacLoad(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType(responseType);
		
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		
		List<BoxMac> macs = boxMacService.obtainEnableMac(keyword, 0, 15);
		
		JSONArray array = new JSONArray();
		JSONObject obj = null;
		for(BoxMac mac:macs){
			obj = new JSONObject();
			obj.put("value", mac.getMac());
			array.add(obj);
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(array.toString());
		writer.flush();
		writer.close();
	}
	
	
	
	private boolean macValidate(String mac) {
		return ValidationUtils.isRightMacByReg(mac);
	}


	private void validatorFormData(HttpServletRequest request, BindingResult error, BoxMacDTO boxmac){
		String uuid = ServletRequestUtils.getStringParameter(request, "boxmacUuid", "");
		String macStatus = ServletRequestUtils.getStringParameter(request, "macStatus", "");
		String mac = boxmac.getMac();
		
		if(StringUtils.hasText(mac)){
			if(!ValidationUtils.isRightMacByReg(mac)){
				error.rejectValue("mac", "boxmac.mac.length", "请填写规范的17位MAC地址！");
			}else{
				boolean exist = boxMacService.obtainMacExist(mac, uuid);
				if(exist){
					error.rejectValue("mac", "boxmac.mac.exist", "此MAC地址已存在！");
				}
			}
			
		}else{
			error.rejectValue("mac", "boxmac.mac.empty", "请填写MAC地址！");
		}
		
		if(StringUtils.hasText(macStatus)){
			try{
				Enum.valueOf(BoxMacStatus.class, macStatus);
			}catch(Exception e){
			error.rejectValue("macStatus", "boxmac.macStatus.error", "MAC状态选择错误！");
			}
		}else{
			error.rejectValue("macStatus", "boxmac.macStatus.empty", "请选择MAC状态！");
		}
		
	}
	
	private void initMenu(ModelMap model){
		model.put(SessionKey.MENU_KEY,MENU_KEY);
		model.put(SessionKey.SUB_MENU_KEY,SUB_MENU_KEY);
	}
}
