package com.changhong.common.utils.excel.poi.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.changhong.common.utils.FileNameUtil;
import com.changhong.common.utils.excel.poi.helper.ExcelHelper;
import com.changhong.common.utils.excel.poi.helper.HssfExcelHelper;
import com.changhong.common.utils.excel.poi.helper.XssfExcelHelper;
import com.changhong.common.utils.excel.poi.model.BoxMacModel;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-20 
 * Time: 14:05:36
 */
public class BoxMacHandler {

	public static List<BoxMacModel> handlerWithFile(File file, String fileName) throws Exception{
		List<BoxMacModel> macs = new ArrayList<BoxMacModel>();
		String suffix = FileNameUtil.getFileSuffix(fileName).toLowerCase();
		ExcelHelper helper = null;
		if(suffix.equals("xlsx")){
			helper =  XssfExcelHelper.getInstance(file);
		}else helper = HssfExcelHelper.getInstance(file);
		
		macs = helper.readExcel(BoxMacModel.class, BoxMacModel.getFileNames(), true);
		return macs;
	}
	
	
	public static void writerTemplate(File file ,String fileName) throws Exception{
		String suffix = FileNameUtil.getFileSuffix(fileName).toLowerCase();
		ExcelHelper helper = null;
		if(suffix.equals("xlsx")){
			helper =  XssfExcelHelper.getInstance(file);
		}else helper = HssfExcelHelper.getInstance(file);
		List<BoxMacModel> macs = new ArrayList<BoxMacModel>();
		macs.add(new BoxMacModel("66:B6:66:FA:44:79"));
		macs.add(new BoxMacModel("66:B6:66:FA:44:80"));
		macs.add(new BoxMacModel("66:B6:66:FA:44:81"));
		
		helper.writeExcel(BoxMacModel.class, macs, BoxMacModel.getFileNames(), new String[]{"mac地址"});
	}
	
}
