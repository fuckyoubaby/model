package com.changhong.common.domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * Author: Guoxiaoyang
 * Date: 2017-2-10 
 * Time: 09:19:28
 *
 */
public class DirectoryResolver implements Serializable{

	private List<String> paths = new ArrayList<String>();
	/*
	 * 以月为文件目录，同是以此月为关键词，查询数据库数据，实现伪分页查询，减少查询的次数
	 * */
	private Map<String,List<String>> fileNameFilter = new HashMap<String,List<String>>();
	
	private long localSize = 0;
	private long localAmount = 0;
	
	private String uploadPath;
	
	
	public DirectoryResolver(String uploadPath) {
		super();
		this.uploadPath = uploadPath;
	}



	public void initPathInfo(File targetDir){
		initPathInfo(targetDir, null);
	}
	
	
	private void initPathInfo(File file, List<String> fileNames){
		if(file.isDirectory()){
			List<String> tempList = new ArrayList<String>();
			File[] tempFiles = file.listFiles();
			for(File temp : tempFiles){
				initPathInfo(temp, tempList);
			}
			fileNameFilter.put(file.getName(), tempList);
		}else{
			if(fileNames!=null){
				String tempStr = reducePath(file.getAbsolutePath());
				fileNames.add(tempStr);
				//fileNames.add(file.getName());
				localSize = localSize + file.length();
				localAmount++;
				paths.add(tempStr);
			}
		}
	}
	
	public String reducePath(String path){
		return path.replace(uploadPath , "").replace("\\", "/");
	}
	
	
	public List<String> getPaths() {
		return paths;
	}
	public void setPaths(List<String> paths) {
		this.paths = paths;
	}
	public Map<String, List<String>> getFileNameFilter() {
		return fileNameFilter;
	}
	public void setFileNameFilter(Map<String, List<String>> fileNameFilter) {
		this.fileNameFilter = fileNameFilter;
	}
	public long getLocalSize() {
		return localSize;
	}
	public void setLocalSize(long localSize) {
		this.localSize = localSize;
	}
	public long getLocalAmount() {
		return localAmount;
	}
	public void setLocalAmount(long localAmount) {
		this.localAmount = localAmount;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uplodPath) {
		this.uploadPath = uplodPath;
	}
	
//	public static void main(String[] args) {
//		String path = "D:\\CH_WORK\\Projects\\LiHongJian\\dianti_ad\\application\\server\\out\\artifacts\\Main_war_exploded\\upload\\";
//		
//		DirectoryResolver dr = new DirectoryResolver(path);
//		File targetDir = new File(dr.uploadPath+"adresource");
//		if(!targetDir.exists()) { targetDir.mkdirs(); }
//		dr.initPathInfo(targetDir);
//		System.out.println("localSize: "+dr.getLocalSize()+" ~~ "+dr.getLocalSize()/(1024*1.0)+" KB");
//		System.out.println("localAmount: "+dr.getLocalAmount());
//		System.out.println("locaFiles: "+dr.getPaths());
//		System.out.println("\n~~~~~~~~~~~\n");
//		System.out.println("localDirectory: ");
//		for(Entry<String, List<String>> entry : dr.getFileNameFilter().entrySet()){
//			System.out.println("directory: "+entry.getKey()+", files name: "+ entry.getValue());
//		}
//	}


	public long getGarbageAmount(List<String> garbagePath) {
		long size = 0;
		for(int i=0;i<garbagePath.size();i++){
			File temp = new File(uploadPath+"/"+garbagePath.get(i));
			if(temp.exists()){
				size+= temp.length();
			}
		}
		return size;
	}
	
}
