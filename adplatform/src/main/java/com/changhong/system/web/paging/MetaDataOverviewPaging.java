package com.changhong.system.web.paging;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.service.MetaDataService;
import com.changhong.system.web.facade.dto.MetaDataDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:34
 */
public class MetaDataOverviewPaging extends AbstractPaging<MetaDataDTO> {

    private MetaDataService metaDataService;

    public MetaDataOverviewPaging(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    public List<MetaDataDTO> getItems() {
        return metaDataService.obtainMetaDatas(getStartPosition(), getPageSize());
    }

    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = metaDataService.obtainMetaDataSize();
        return totalItemSize;
    }

    public String getParameterValues() {
        return "";
    }
}

