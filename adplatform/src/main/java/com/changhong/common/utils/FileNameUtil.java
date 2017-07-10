package com.changhong.common.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileNameUtil {
	
	
	public static Map<String, String > mediaType;
	
	static{
		mediaType = new HashMap<String, String>();
		//图片 image
		mediaType.put("jpg", "image/jpg");
		mediaType.put("jpeg", "image/jpeg");
		mediaType.put("gif", "image/gif");
		mediaType.put("png", "image/png");
		mediaType.put("ico", "image/ico");
		mediaType.put("bmp", "image/bmp");
		mediaType.put("pic", "image/pic");
		mediaType.put("tif", "image/tif");
		
		//视频 video
		mediaType.put("avi", "video/avi");
	}
	
	
	/**
	 * 获取文件后缀
	 * @param fileName
	 * 			文件名
	 * @return
	 */
	public static String getFileSuffix(String fileName){
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	/**
	 * 获取全路径中指定文件的后缀名
	 * @param filePath 
	 * 			全路径
	 * @return
	 */
	public static String getFullPathSuffix(String filePath){
		String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
		return getFileSuffix(fileName);
	}
	
	/**
	 * 获取指定文件名的文件名前缀（.之前的部分）
	 * @param fileName
	 * 			指定文件名
	 * @return
	 */
	public static String getFileNamePrefix(String fileName){
		int index = fileName.lastIndexOf(".");
		if(index==-1) return fileName;
		return fileName.substring(0, index);
	}
	
	/**
	 * 根据目标文件名在指定目录生成不重复的文件名
	 * @param directory
	 * 			指定目录
	 * @param fileName
	 * 			指定文件名
	 * @return
	 */
	public synchronized static String bulidNewFileName(File directory, String fileName){
		 
		String targetFilePrefix = getFileNamePrefix(fileName);
		String targetFileSuffix = getFileSuffix(fileName);
		File [] files = directory.listFiles();
		boolean hasDot = fileName.lastIndexOf(".")==-1?false:true;
		List<String> matchNames = new ArrayList<String>();
		int amount = 0;
		for(int i=0;i<files.length;i++){
			File file = files[i];
			String sourceFilePrefix = getFileNamePrefix(file.getName());
			String sourceFileSuffix = getFileSuffix(file.getName());
			if(sourceFilePrefix.indexOf(targetFilePrefix)!=-1 &
				sourceFileSuffix.equals(targetFileSuffix)){
				amount++;
				matchNames.add(sourceFilePrefix);
			}
		}
		if(amount==0){
			return fileName;
		}
		for(int k=2;k<=amount;k++){
			String cureentName = targetFilePrefix+"("+k+")";
			if(!isInclude(cureentName, matchNames)){
				if(!hasDot){
					return cureentName;
				}
				return cureentName+"."+targetFileSuffix;
			}
		}
		if(!hasDot){
			return targetFilePrefix+" ("+(amount+1)+")";
		}
		
		return targetFilePrefix+" ("+(amount+1)+")."+targetFileSuffix;
	}
	/**
	 * 检查指定目标字符串是否在原始字符串链表中
	 * @param target
	 * 			目标字符串
	 * @param sourceLists
	 * 			原始字符串链表
	 * @return
	 */
	public static boolean isInclude(String target, List<String> sourceLists){
		int size = sourceLists.size();
		for(int i=0;i<size;i++){
			if(sourceLists.get(i).equals(target)) return true;
		}
		return false;
	}
	
	public static String getContainerKB(long amount,int endLength){
		double d = amount/(1024*1.0);
		StringBuilder sb = new StringBuilder();
		sb.append("#.");
		for(int i=0;i<endLength;i++){
			sb.append("0");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		return df.format(d)+"KB";
	}
	
	public static String getContainerMB(long amount,int endLength){
		double d = amount/(1024*1024*1.0);
		StringBuilder sb = new StringBuilder();
		sb.append("#.");
		for(int i=0;i<endLength;i++){
			sb.append("0");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		return df.format(d)+"MB";
	}
	
	public static String getContainerGB(long amount,int endLength){
		double d = amount/(1024*1024*1024*1.0);
		StringBuilder sb = new StringBuilder();
		sb.append("#.");
		for(int i=0;i<endLength;i++){
			sb.append("0");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		return df.format(d)+"GB";
	}
	
	public static String getContainerSize(long amount,int endLength){
		if(amount>=(1024l*1024l*1024l*10l)){
			return getContainerGB(amount, endLength);
		}else if(amount>=1024*1024*10){
			return getContainerMB(amount, endLength);
			
		}else return getContainerKB(amount, endLength);
	}
	
	/**
	 * 根据文件名获取文件类型
	 * @param fileName 文件名，带后缀
	 * @return
	 */
	public static String buildMediaType(String fileName) {
		String suffix = getFileSuffix(fileName);
		
		suffix = suffix.toLowerCase();
		if(mediaType.containsKey(suffix)){
			return mediaType.get(suffix);
		}
		return null;
	}
	
	/**
	 * 根据文件路径获取文件类型
	 * @param path 文件路径，带后缀
	 * @return
	 */
	public static String buildMediaTypeByFullPath(String path) {
		String suffix = getFullPathSuffix(path);
		
		suffix = suffix.toLowerCase();
		if(mediaType.containsKey(suffix)){
			return mediaType.get(suffix);
		}
		return "*/"+suffix;
	}
	
}
