package com.changhong.system.web.paging;

import java.util.List;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.service.ContentUpgradeService;
import com.changhong.system.web.facade.dto.ContentUpgradeDTO;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-2-2 
 * Time: 13:01:23
 *
 */
public class ContentUpgradeOverviewPaging extends
		AbstractPaging<ContentUpgradeDTO> {

	private ContentUpgradeService contentUpgradeService;
	private String filterName;
	
	public ContentUpgradeOverviewPaging( ContentUpgradeService contentUpgradeService) {
		this.contentUpgradeService = contentUpgradeService;
	}
	
	
	@Override
	public String getParameterValues() {
		return "&filterName="+filterName;
	}

	@Override
	public List<ContentUpgradeDTO> getItems() {
		return contentUpgradeService.obtainContentUpgradeDTOs(filterName, getStartPosition() , getPageSize());
	}

	@Override
	public long getTotalItemSize() {
		if(totalItemSize>0){
			return totalItemSize;
		}
		totalItemSize = contentUpgradeService.obtainAmount(filterName);
		return totalItemSize;
	}

	public ContentUpgradeService getContentUpgradeService() {
		return contentUpgradeService;
	}


	public void setContentUpgradeService(ContentUpgradeService contentUpgradeService) {
		this.contentUpgradeService = contentUpgradeService;
	}


	public String getFilterName() {
		return filterName;
	}


	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
}
