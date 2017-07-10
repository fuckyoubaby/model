package com.changhong.system.web.paging;

import java.util.List;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.service.BoxMacService;
import com.changhong.system.web.facade.dto.BoxMacDTO;

public class BoxMacOverviewPaging extends AbstractPaging<BoxMacDTO> {

	private BoxMacService boxMacService;
	private String filterName;

	
	
	public BoxMacOverviewPaging(BoxMacService boxMacService, String filterName) {
		this.boxMacService = boxMacService;
		this.filterName = filterName;
	}

	@Override
	public String getParameterValues() {
		return "&filterName="+filterName;
	}

	@Override
	public List<BoxMacDTO> getItems() {
		return boxMacService.obtainBoxMacs(filterName, getStartPosition(), getPageSize());
	}

	@Override
	public long getTotalItemSize() {
		if(totalItemSize>0){
			return totalItemSize;
		}
		totalItemSize = boxMacService.obtainBoxMacs(filterName);
		return totalItemSize;
	}

	public BoxMacService getBoxMacService() {
		return boxMacService;
	}

	public void setBoxMacService(BoxMacService boxMacService) {
		this.boxMacService = boxMacService;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

}
