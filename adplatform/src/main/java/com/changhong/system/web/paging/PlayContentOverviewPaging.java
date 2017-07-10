package com.changhong.system.web.paging;

import java.util.List;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.service.PlayContentService;
import com.changhong.system.web.facade.dto.PlayContentDTO;

/**
 * 
 * Author: Guo xiaoyang 
 * Date: 2017-1-12 
 * Time: 18:30:36
 */
public class PlayContentOverviewPaging extends AbstractPaging<PlayContentDTO>{
	
	private PlayContentService playContentService;
	private String filterName;
	
	
	
	public PlayContentOverviewPaging(PlayContentService playContentService) {
		this.playContentService = playContentService;
	}

	@Override
	public String getParameterValues() {
		return "&filterName="+filterName;
	}

	@Override
	public List<PlayContentDTO> getItems() {
		return playContentService.obtainEnablePlayContentDTOs(filterName, getStartPosition(), getPageSize());
	}

	@Override
	public long getTotalItemSize() {
		if(totalItemSize>0){
			return totalItemSize;
		}
		totalItemSize = playContentService.obtainEnablePlayContentDTOsAmount(filterName);
		return totalItemSize;
	}

	public PlayContentService getPlayContentService() {
		return playContentService;
	}

	public void setPlayContentService(PlayContentService playContentService) {
		this.playContentService = playContentService;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

}
