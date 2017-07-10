package com.changhong.system.domain.box;

import java.util.ArrayList;
import java.util.List;

import com.changhong.common.domain.Option;
import com.changhong.common.domain.SelectOption;

/**
 * 
 * Author: Shellyang Guo
 * Date: 2017-3-15 
 * Time: 11:33:24
 */
public enum BoxMacStatus {
	
	B_INITE("初始状态"),
	B_USED("使用状态"),
	B_DISABLE("禁用状态");
	
	private String description;
	
	private BoxMacStatus(String description) {
		this.setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static List<Option> getOptions(){
		List<Option> options = new ArrayList<>();
		BoxMacStatus[] values = BoxMacStatus.values();
		for(BoxMacStatus value : values){
			options.add(new SelectOption(value.getDescription(), value.name()));
		}
		return options;
	}
	
	public static List<Option> getSavedOptions(){
		List<Option> options = new ArrayList<>();
		BoxMacStatus[] values = BoxMacStatus.values();
		for(BoxMacStatus value : values){
			if(value.equals(BoxMacStatus.B_USED)){continue;}
			options.add(new SelectOption(value.getDescription(), value.name()));
		}
		
		return options;
	}

}
