package com.changhong.system.web.paging;

import java.util.List;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.service.AppStrategyService;
import com.changhong.system.web.facade.dto.AppStrategyDTO;

public class AppStrategyOverViewPaging extends AbstractPaging<AppStrategyDTO>{
	
	private AppStrategyService appStrategyService;
    private String filterName;
    
    public AppStrategyOverViewPaging(AppStrategyService appStrategyService){
    	this.appStrategyService = appStrategyService;
    }
	
	@Override
	public String getParameterValues() {
		return "&filterName=" + getFilterName();
	}

	@Override
	public List<AppStrategyDTO> getItems() {
		return appStrategyService.obtainStrategys(filterName, getStartPosition(), getPageSize());
	}

	@Override
	public long getTotalItemSize() {
		if (totalItemSize >= 0) {
            return totalItemSize;
        }
		totalItemSize = appStrategyService.obtainStrategySize(filterName);
		return totalItemSize;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
}
