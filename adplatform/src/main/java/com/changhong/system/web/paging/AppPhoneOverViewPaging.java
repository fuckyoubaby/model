package com.changhong.system.web.paging;

import java.util.List;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.service.AppPhoneManageService;
import com.changhong.system.web.facade.dto.AppPhoneManageDTO;

public class AppPhoneOverViewPaging extends AbstractPaging<AppPhoneManageDTO>{

	private AppPhoneManageService appPhoneManageService;
	private String filterName;
	
	public AppPhoneOverViewPaging(AppPhoneManageService appPhoneManageService){
		this.appPhoneManageService = appPhoneManageService;
	}
	
	@Override
	public String getParameterValues() {
		return "&filterName=" + getFilterName();
	}

	@Override
	public List<AppPhoneManageDTO> getItems() {
		return appPhoneManageService.obtainApps(filterName, getStartPosition(), getPageSize());
	}

	@Override
	public long getTotalItemSize() {
		if(totalItemSize>0){
			return totalItemSize;
		}
		totalItemSize = appPhoneManageService.obtainAppSize(filterName);
		return totalItemSize;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

}
