package com.changhong.common.utils.excel.poi.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.changhong.common.utils.excel.poi.helper.ExcelHelper;
import com.changhong.common.utils.excel.poi.helper.XssfExcelHelper;
import com.changhong.common.utils.excel.poi.model.MacInfo;


public class MacInfoTest {
	
	public static void main(String[] args) throws Exception {
		
		List<MacInfo> macs = new ArrayList<MacInfo>();
		
		macs.add(new MacInfo("66:B6:66:FA:44:79"));
		macs.add(new MacInfo("A6:B6:66:FA:44:79"));
		
		File file = new File("E:\\macInfoTest.xlsx");
		ExcelHelper eh2 = XssfExcelHelper.getInstance(file);
		//eh2.writeExcel(MacInfo.class, macs);
		List<MacInfo> lists2 = eh2.readExcel(MacInfo.class, new String[]{"mac" ,"date"}, true);
		System.out.println("~~~~~~~~~~~~~~~~E:\\macInfoTest.xlsx~~~~~~~~~~~~~~~~~");
		for(MacInfo info : lists2){
			System.out.println(info);
		}
		
	}

}
