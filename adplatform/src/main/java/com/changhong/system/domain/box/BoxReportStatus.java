package com.changhong.system.domain.box;
/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-22 
 * Time: 15:04:14
 *
 */
public enum BoxReportStatus {
	
	B_R_REPORT("未处理"),
	B_R_SOLVED("已解决");
	
	private String description;
	
	private BoxReportStatus(String description){
		this.setDescription(description);
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
