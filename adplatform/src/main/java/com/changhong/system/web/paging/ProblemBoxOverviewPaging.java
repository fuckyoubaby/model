package com.changhong.system.web.paging;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.service.BoxInfoService;
import com.changhong.system.web.facade.dto.BoxDTO;
import java.util.List;

public class ProblemBoxOverviewPaging extends AbstractPaging<BoxDTO> {

    private BoxInfoService boxInfoService;

    public ProblemBoxOverviewPaging(BoxInfoService boxInfoService) {
        this.boxInfoService = boxInfoService;
    }

	@Override
	public List<BoxDTO> getItems() {
		return boxInfoService.obtainProblemBoxInfos(getStartPosition(), getPageSize());
	}

	@Override
	public long getTotalItemSize() {
		if(totalItemSize>0){
			return totalItemSize;
		}
		totalItemSize = boxInfoService.obtainProblemBoxInfoSize();
		return totalItemSize;
	}

    @Override
	public String getParameterValues() {
		return "";
	}
}
