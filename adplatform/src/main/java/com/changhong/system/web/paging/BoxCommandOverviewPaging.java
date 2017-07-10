package com.changhong.system.web.paging;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.service.BoxInfoService;
import com.changhong.system.service.MetaDataService;
import com.changhong.system.web.facade.dto.BoxCommandDTO;
import com.changhong.system.web.facade.dto.MetaDataDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:34
 */
public class BoxCommandOverviewPaging extends AbstractPaging<BoxCommandDTO> {

    private BoxInfoService boxInfoService;

    private String communityUuid;

    public BoxCommandOverviewPaging(BoxInfoService boxInfoService) {
        this.boxInfoService = boxInfoService;
    }

    public List<BoxCommandDTO> getItems() {
        return boxInfoService.obtainCommands(communityUuid, getStartPosition(), getPageSize());
    }

    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = boxInfoService.obtainCommandSize(communityUuid);
        return totalItemSize;
    }

    public String getParameterValues() {
        return "&communityUuid=" + communityUuid;
    }

    public String getCommunityUuid() {
        return communityUuid;
    }

    public void setCommunityUuid(String communityUuid) {
        this.communityUuid = communityUuid;
    }
}

