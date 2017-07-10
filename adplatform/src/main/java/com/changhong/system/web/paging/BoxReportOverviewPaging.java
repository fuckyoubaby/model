package com.changhong.system.web.paging;

import java.util.List;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.domain.box.BoxReport;
import com.changhong.system.service.BoxReportService;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-22 
 * Time: 17:24:52
 */
public class BoxReportOverviewPaging extends AbstractPaging<BoxReport>{

	private BoxReportService boxReportService;
	
	private String startDate;
	private String endDate;
	
	
	public BoxReportOverviewPaging(BoxReportService boxReportService) {
		super();
		this.boxReportService = boxReportService;
	}
	

	@Override
	public String getParameterValues() {
		return "&startDate="+startDate+"&endDate="+endDate;
	}

	@Override
	public List<BoxReport> getItems() {
		return boxReportService.obtainReports(startDate, endDate, getStartPosition(), getPageSize());
	}

	@Override
	public long getTotalItemSize() {
		if(totalItemSize>0){
			return totalItemSize;
		}
		
		totalItemSize = boxReportService.obtainReportAmount(startDate, endDate);
		
		return totalItemSize;
	}

	public BoxReportService getBoxReportService() {
		return boxReportService;
	}

	public void setBoxReportService(BoxReportService boxReportService) {
		this.boxReportService = boxReportService;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
