package com.changhong.system.web.controller.play;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.servlet.support.RequestContext;



import com.alibaba.fastjson.JSONObject;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.common.utils.CHStringUtils;
import com.changhong.common.utils.FileNameUtil;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.domain.advertisment.AdverResource;
import com.changhong.system.service.AdverResourceService;
import com.changhong.system.web.facade.assember.AdverResourceWebAssember;
import com.changhong.system.web.facade.dto.AdverResourceDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-17 
 * Time: 10:58:26
 */
@Controller
public class PlayRescourceController{
	
	@Autowired
	private AdverResourceService  adverResourceService;
	
	@Value("${application.file.upload.path}")
	private String basePath;
	
	private  final String MENU="AD_MANAGE";
	private  final String SUB_MENU="PLAY_LIST_MANAGE";
	private final String SUB_MENU2="PLAY_UPGRADE_MANAGE";
	
	@RequestMapping(value="/backend/ajaxResourceFetch.html", method=RequestMethod.POST)
	public String requestResourceForAjax(HttpServletRequest request, ModelMap map){
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword","");
		Integer indexNo = ServletRequestUtils.getIntParameter(request, "indexNo", 0);
		Integer pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 5);
		
		List<AdverResourceDTO> adverResourceDTOs =  adverResourceService.obtainEnableAdverResources(keyword, indexNo, pageSize);
		int itemcount = adverResourceService.obtainEnableAdverResourceAmount(keyword); 
		
		map.put("adverResourceDTOs", adverResourceDTOs);
		map.put("itemcount", itemcount);
		
		return "/backend/play/template/playresourcetemplate";
	} 
	
	@RequestMapping(value="/backend/playresourceform.html", method=RequestMethod.GET)
	public String toResourceForm(HttpServletRequest request, ModelMap model){
		String resourceUuid =  ServletRequestUtils.getStringParameter(request, "resourceUuid","");
		String playContentUuid = ServletRequestUtils.getStringParameter(request, "contentUuid","");
		String itemUuid = ServletRequestUtils.getStringParameter(request, "itemUuid","");
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		
		AdverResourceDTO resourceDTO = null;
		if(!StringUtils.hasText(resourceUuid)){
			resourceDTO =  new AdverResourceDTO();
		} else {
			resourceDTO = adverResourceService.obtainDTOByUuid(resourceUuid);
		}
		model.put("adverResource", resourceDTO);
		model.put("contentUuid", playContentUuid);
		model.put("itemUuid",itemUuid);
		model.put("contentUpgradeId", contentUpgradeId);
		initMenu(model, contentUpgradeId);
		
		return "backend/play/playresourceform";
	}
	
	@RequestMapping(value="/backend/playresourceform.html", method=RequestMethod.POST)
	public String saveForm(HttpServletRequest request,@RequestParam("file") MultipartFile file , @ModelAttribute("adverResource") AdverResourceDTO resourceDTO,
			BindingResult errors, ModelMap model) throws IllegalStateException, IOException{
		
		String resourceUuid =  ServletRequestUtils.getStringParameter(request, "resourceUuid","");
		String filePath = ServletRequestUtils.getStringParameter(request,"filePath","");
		String contentUuid = ServletRequestUtils.getStringParameter(request, "contentUuid","");
		String itemUuid = ServletRequestUtils.getStringParameter(request, "itemUuid","");
		String contentUpgradeId = ServletRequestUtils.getStringParameter(request, "contentUpgradeId", "");
		
		boolean isUpdate = StringUtils.hasText(resourceUuid);
		RequestContext requestContext = new RequestContext(request);
		String fileError = requestContext.getMessage("adverResource.path.empty", "文件不能为空！");
	
		resourceDTO.setUuid(resourceUuid);
		resourceDTO.setPath(filePath);
		resourceDTO.setType(FileNameUtil.buildMediaTypeByFullPath(filePath));
		validateForm(errors, resourceDTO);
		
		model.put("itemUuid", itemUuid);
		model.put("contentUuid", contentUuid);
		model.put("contentUpgradeId", contentUpgradeId);
		initMenu(model, contentUpgradeId);
		
		if(errors.hasErrors()){
			model.put("adverResource", resourceDTO);
			model.putAll(errors.getModel());
			if(!isUpdate && file.isEmpty()){
				model.put("fileError", fileError);
			}
			return "/backend/play/playresourceform";
		}
		String currentName = CHDateUtils.getFullDateFormatUUID(new Date()) + CHStringUtils.getRandomNumber(7);
			if(isUpdate){
				//更新时，无上传文件就维持原文件更新其他信息
				if(file.isEmpty()){
					adverResourceService.updateAdverResourceDTO(resourceDTO);
				}else{
					//否则删除原文件
					String newFilePath = saveNewFile(basePath, file, currentName);
					resourceDTO.setPath(newFilePath);
					//换算成字节
					double size = file.getSize()/(1024*1.0);
					resourceDTO.setSize(size);
					resourceDTO.setType( FileNameUtil.buildMediaTypeByFullPath(newFilePath));
					adverResourceService.updateAdverResourceDTO(resourceDTO);
					deleteOldFile(basePath, filePath, newFilePath);
				}
			}else{
				//新建
				if(file.isEmpty()){
					model.put("adverResource", resourceDTO);
					model.put("fileError", fileError);
					return "/backend/play/playresourceform";
				}
				AdverResource ar = AdverResourceWebAssember.toDomain(resourceDTO);
				String newFilePath = saveNewFile(basePath, file, currentName);
				ar.setPath(newFilePath);
				//换算成KB
				double size = file.getSize()/(1024*1.0);
				ar.setSize(size);
				ar.setType(FileNameUtil.buildMediaTypeByFullPath(newFilePath));
				adverResourceService.saveAdverResource(ar);
			}
		return "redirect:configplayitem.html?itemUuid="+itemUuid+"&contentUuid="+contentUuid+"&contentUpgradeId="+contentUpgradeId;
	} 
	
	
	@RequestMapping("/backend/playresourcedelete.html")
	public String deleteEntity(HttpServletRequest request){
		String filterName = ServletRequestUtils.getStringParameter(request, "filterName","");
		String current = ServletRequestUtils.getStringParameter(request, "current", "");
		
		String resourceUuid = ServletRequestUtils.getStringParameter(request, "resourceUuid", "");
		if(StringUtils.hasText(resourceUuid)){
			AdverResourceDTO arDTO = adverResourceService.obtainDTOByUuid(resourceUuid);
			adverResourceService.deleteByUUID(resourceUuid);
			if(arDTO!=null){
				String filePath = arDTO.getPath();
				deleteOldFile(basePath, filePath, "删除");
			}
		}
		return "redirect:adresourcemanagement.html?filterName="+filterName+"&current="+current;
	}
	
	@RequestMapping(value="/backend/ajaxResourceDelete.html", method=RequestMethod.POST)
	public void ajaxResourceDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		response.setContentType("application/json;charset=utf-8");
		JSONObject resultObj = new JSONObject();
		String resourceUuid = ServletRequestUtils.getStringParameter(request, "resourceUuid", "");
		
		if(!StringUtils.hasText(resourceUuid)){
			resultObj.put("result", false);
			resultObj.put("msg", "删除失败");
		}else{
			AdverResourceDTO arDTO = adverResourceService.obtainDTOByUuid(resourceUuid);
			adverResourceService.deleteByUUID(resourceUuid);
			if(arDTO!=null){
				String filePath = arDTO.getPath();
				deleteOldFile(basePath, filePath, "删除");
			}
			resultObj.put("result", true);
			resultObj.put("msg", "删除成功");
		}
		
		PrintWriter writer = response.getWriter();
		writer.write(resultObj.toString());
		writer.flush();
		writer.close();
	}
	
	
	@RequestMapping("/backend/getimages.html")
	public void displayImages(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
		AdverResourceDTO dto = null;
		if(StringUtils.hasText(uuid)){
			dto = adverResourceService.obtainDTOByUuid(uuid);
		}
		if(dto!=null && StringUtils.hasText(dto.getPath())){
			String filePath = dto.getPath();
			String type = dto.getType();
			
			File imageFile= new File( basePath+filePath);
			response.setContentType(type+";charset=utf-8");
			if(!imageFile.exists()){
				imageFile = new File(request.getSession().getServletContext().getRealPath("/")+"images/sadEmo.png");
			}
			InputStream in = null;
			OutputStream out = null;
				
			byte[] bytes = new byte[4096];
			int len =0;
			  try {
		            // 写入浏览器的输出流
				  in = new BufferedInputStream( new FileInputStream( imageFile));
		          out = new BufferedOutputStream(response.getOutputStream());
		            while ((len = in.read(bytes)) > 0) {
		                out.write(bytes, 0, len);
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            throw e;
		        } finally {
		            if (in != null) {
		                in.close();
		            }
		            if (out != null){
		            	out.flush();
		            	out.close();
		            }
		        }
			}
	}
	
	private void validateForm(BindingResult errors,
			AdverResourceDTO resourceDTO) {
		String uuid = resourceDTO.getUuid();
		String name = resourceDTO.getName();
		String advertiser = resourceDTO.getAdvertiser();
		String agents = resourceDTO.getAgents();
		
		if(!StringUtils.hasText(name)){
			errors.rejectValue("name", "adverResource.name.empty", "资源名称不能为空");
		}else{
			if(name.length()>50){
				errors.rejectValue("name", "adverResource.name.maxlength", "资源名称长度不能超过50");
			}else{
				boolean exist = adverResourceService.obtainAdverResourceExist(uuid, name);
				if(exist){
					errors.rejectValue("name", "adverResource.name.exist", "此名称已存在");
				}
			}
		}
		
		if(!StringUtils.hasText(advertiser)){
			errors.rejectValue("advertiser", "adverResource.advertiser.empty", "广告商不能为空");
		}else{
			if(advertiser.length()>80){
				errors.rejectValue("advertiser", "adverResource.advertiser.maxlength", "广告商名称字数不能超过80");
			}
		}
		if(!StringUtils.hasText(agents)){
			errors.rejectValue("agents", "adverResource.agents.empty", "代理商不能为空");
		}else{
			if(agents.length()>80){
				errors.rejectValue("agents", "adverResource.agents.maxlength", "代理商名称字数不能超过80");
			}
		}
		
	}
	
	/**
	 * 保存新文件
	 * @param basePath 基础目录
	 * @param file 文件对象
	 * @param fileName 要保存的文件名[不包含后缀]
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private String saveNewFile(String basePath, MultipartFile file, String fileName) throws IllegalStateException, IOException{
		String prefixPath="adresource/"+getNowSimpleStr("yyyy-MM")+"/";
		File parentDirectory = new File(basePath+prefixPath);
		if(!parentDirectory.exists()){
			parentDirectory.mkdirs();
		}
		String suffix = "."+FileNameUtil.getFileSuffix(file.getOriginalFilename());
		file.transferTo(new File(basePath+prefixPath+fileName+suffix));
		return prefixPath+fileName+suffix;
	}
	
	/**
	 * 新旧文件名不同时，移除旧文件；删除文件时也可调用
	 * @param basePath 基础目录
	 * @param oldFileName 旧文件所在路径
	 * @param newFileName 新文件所在路径
	 */
	private void deleteOldFile(String basePath, String oldFilePath, String newFilePath){
		if(!oldFilePath.equals(newFilePath)){
			File oldFile = new File(basePath+oldFilePath);
			if(oldFile.exists()){
				oldFile.delete();
			}
		}
	}
	
	private String getNowSimpleStr(String pattern){
		SimpleDateFormat sdf = null;
		if(StringUtils.hasText(pattern)){
			sdf = new SimpleDateFormat(pattern);
		}else{
			sdf = new SimpleDateFormat("yyyy-MM");
		}
		return sdf.format(new Date());
	}
	
	
	private void initMenu(ModelMap model, String judgeStr){
		model.put(SessionKey.MENU_KEY, MENU);
		if(StringUtils.hasText(judgeStr)){
			model.put(SessionKey.SUB_MENU_KEY, SUB_MENU2);
		}else{
			model.put(SessionKey.SUB_MENU_KEY, SUB_MENU);
		}
	}
}
