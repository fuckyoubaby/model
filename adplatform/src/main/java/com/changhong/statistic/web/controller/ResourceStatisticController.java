package com.changhong.statistic.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changhong.common.domain.DirectoryResolver;
import com.changhong.common.domain.RecordeResourceInfo;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.AdverResourceService;

/**
 * Author: Guoxioyang
 * Date: 2017-2-8 
 * Time: 16:57:50
 */

@Controller
public class ResourceStatisticController {
	
	@Autowired
	private AdverResourceService adverResourceService;

	@Value("${application.file.upload.path}")
	private String uploadPath;
	
	private final String resourcePath = "adresource";
	
	private final String MENU_KEY="STA_MANAGE";
	private final String SUB_MENU_KEY="RESOURCE_SHOW";
	
	/*  广告资源容量统计     */
	@RequestMapping(value="/backend/static/adresourcespacestatistic.html")
	public String resourceStatistic(HttpServletRequest request, ModelMap model){
		
		//TODO 注意如果使用远程文件服务器配置的话，需要重新调整该处代码
		String baseDirPath = uploadPath+resourcePath;
		
		File baseDir = new File(baseDirPath);
		if(!baseDir.exists()){  baseDir.mkdirs();}
		
		//初始化本地存储的文件信息
		DirectoryResolver dr = new DirectoryResolver(uploadPath);
		dr.initPathInfo(baseDir);
	
		//文件筛选Map<文件夹名, 文件列表>
		Map<String, List<String>> fileCounter = dr.getFileNameFilter();
		
		//垃圾文件保存
		List<String> garbagePath = new ArrayList<String>();
		
		//无效记录文件保存
		List<String> garbageRecordes = new ArrayList<String>();
		
		/**
		 * 1、数据库记录的(recorded)的资源数量和资源容量
		 * 2、数据库记录的(recorded)的未用资源数量和可用资源容量
		 * 3、数据库记录的(recorded)的已用资源数量和可用资源容量
		 */
		List<RecordeResourceInfo> recordeResourecInfos = adverResourceService.obtainRecordResourceInfo();

		if(recordeResourecInfos.size()==3){
			model.put("recordeAllAmount", recordeResourecInfos.get(0).getAmount());
			model.put("recordeAllSize", recordeResourecInfos.get(0).getSize().doubleValue());
			model.put("recordeUsedAmount", recordeResourecInfos.get(1).getAmount());
			model.put("recordeUsedSize", recordeResourecInfos.get(1).getSize().doubleValue());
			model.put("recordeUnusedAmount", recordeResourecInfos.get(2).getAmount());
			model.put("recordeUnusedSize", recordeResourecInfos.get(2).getSize().doubleValue());
		}
		
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
		
		/**
		 * 1、实际存储的(saved)的资源数量和资源容量
		 * 2、实际存储的(saved)的垃圾文件数量和垃圾文件容量
		 */
		//实际保存的资源数量及资源容量
		double savedSize = dr.getLocalSize()/(1024*1.0);
		long savedAmount = dr.getLocalAmount();
		//初始化
		fileCounter = dr.getFileNameFilter();
		fileCounter.remove(resourcePath);
		
		//获取垃圾文件数量和容量
		for(Entry<String,List<String>> entry: fileCounter.entrySet()){
			String keyword = resourcePath+"/"+entry.getKey()+"/";
			List<String> tempList = entry.getValue();
			List<String> savedFileNames = adverResourceService.obtainFileNamesByPath(keyword);
			// ListUtils.substract(a1,a2)，将a1元素中和a2重复的去掉，返回新的List
			garbagePath.addAll(ListUtils.subtract(tempList, savedFileNames));
			garbageRecordes.addAll(ListUtils.subtract(savedFileNames, tempList));
			
		}
		
		long savedGarbageAmount = garbagePath.size();
		long recordGarbageAmount = garbageRecordes.size();
		long savedGarbageSize = 0;
		double recordGarbageSize = 0;
		
		if(savedGarbageAmount>0){
			savedGarbageSize = dr.getGarbageAmount(garbagePath);
		}
		
		recordGarbageSize = adverResourceService.obtainRecordPathSize(garbageRecordes);
		
		model.put("savedResourceSize", savedSize);
		model.put("savedResourceAmount", savedAmount);
		
		model.put("savedGarbageSize", savedGarbageSize/(1024*1.0));
		model.put("savedGarbageAmount", savedGarbageAmount);
		
		model.put("recordGarbageAmount", recordGarbageAmount);
		model.put("recordGarbageSize", recordGarbageSize);
		
		model.put(SessionKey.MENU_KEY,MENU_KEY);
		model.put(SessionKey.SUB_MENU_KEY, SUB_MENU_KEY);
		
		return "backend/statistic/adresourcespacestatistic";
	}
	
	@RequestMapping(value="/backend/static/adgarbageclean.html")
	public String ajaxCleanGarbage(HttpServletRequest request, HttpServletResponse response) {
		
		
		//TODO 注意如果使用远程文件服务器配置的话，需要重新调整该处代码
		String baseDirPath = uploadPath+resourcePath;
		
		File baseDir = new File(baseDirPath);
		if(!baseDir.exists()){  baseDir.mkdirs();}
		
		//初始化本地存储的文件信息
		DirectoryResolver dr = new DirectoryResolver(uploadPath);
		dr.initPathInfo(baseDir);
	
		//文件筛选Map
		Map<String, List<String>> fileCounter = dr.getFileNameFilter();
		
		//垃圾文件保存
		List<String> garbagePath = new ArrayList<String>();
		
		//无效记录文件保存
		List<String> garbageRecordes = new ArrayList<String>();
		
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
		
		/**
		 * 1、实际保存的(saved)的资源数量和资源容量
		 * 2、实际保存的(saved)的垃圾文件数量和垃圾文件容量
		 */
		//初始化
		fileCounter = dr.getFileNameFilter();
		fileCounter.remove(resourcePath);
		
		//获取垃圾文件数量和容量
		for(Entry<String,List<String>> entry: fileCounter.entrySet()){
			String keyword = resourcePath+"/"+entry.getKey()+"/";
			List<String> tempList = entry.getValue();
			List<String> savedFileNames = adverResourceService.obtainFileNamesByPath(keyword);
			garbagePath.addAll(ListUtils.subtract(tempList, savedFileNames));
			garbageRecordes.addAll(ListUtils.subtract(savedFileNames, tempList));
			
		}
		//*********垃圾处理
		
		//1、删除存储路径上的垃圾文件
		if(garbagePath.size()>0){
			for(String garbagePathItem: garbagePath){
				File tempFile = new File(uploadPath+garbagePathItem);
				if(tempFile.exists()&& tempFile.isFile()){
					tempFile.delete();
				}
			}
		}
		//2、删除status为1的无效数据
		if(garbageRecordes.size()>0){
			adverResourceService.deleteByGarbageRecordes(garbageRecordes);
		}
		
		return "redirect: adresourcespacestatistic.html";
	}

}
