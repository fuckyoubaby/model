package com.changhong.system.web.paging;

import java.util.List;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.service.AdverResourceService;
import com.changhong.system.web.facade.dto.AdverResourceDTO;

public class AdverResourceOverviewPaging extends AbstractPaging<AdverResourceDTO>{

	private AdverResourceService adverResourceService;
	
	private String filterName;
	
	public AdverResourceOverviewPaging(AdverResourceService adverResourceService) {
		this.adverResourceService = adverResourceService;
	}

	@Override
	public String getParameterValues() {
		return "&filterName="+filterName;
	}

	@Override
	public List<AdverResourceDTO> getItems() {
		return adverResourceService.obtainAdverResources(filterName, getStartPosition(), getPageSize());
	}

	@Override
	public long getTotalItemSize() {
		if(totalItemSize>0){
			return totalItemSize;
		}
		totalItemSize = adverResourceService.obtainAdverResourceAmount(filterName);
		return totalItemSize;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}



}
