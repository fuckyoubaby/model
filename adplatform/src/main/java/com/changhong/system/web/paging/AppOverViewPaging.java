package com.changhong.system.web.paging;

import java.util.List;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.service.AppManageService;
import com.changhong.system.web.facade.dto.AppManageDTO;

public class AppOverViewPaging extends AbstractPaging<AppManageDTO>{

	private AppManageService appManageService;

    private String filterName;
    
    public AppOverViewPaging(AppManageService appManageService){
    	this.appManageService = appManageService;
    }
    
	@Override
	public String getParameterValues() {
		return "&filterName=" + getFilterName();
	}

	@Override
	public List<AppManageDTO> getItems() {
		return appManageService.obtainApps(filterName, getStartPosition(), getPageSize());
	}

	@Override
	public long getTotalItemSize() {
		if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = appManageService.obtainAppSize(filterName);
        return totalItemSize;
	}
	
	public String getFilterName() {
		return filterName;
	}
	
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
}
