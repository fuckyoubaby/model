package com.changhong.system.web.paging;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.service.ActionLogService;
import com.changhong.system.web.facade.dto.ActionLogDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-29
 * Time: 下午1:08
 */
public class ActionLogOverviewPaging extends AbstractPaging<ActionLogDTO> {

    private ActionLogService actionLogService;

    private String startDate;
    private String endDate;

    public ActionLogOverviewPaging(ActionLogService actionLogService) {
        this.actionLogService = actionLogService;
    }

    public List<ActionLogDTO> getItems() {
        return actionLogService.obtainActionLogs(startDate, endDate, getStartPosition(), getPageSize());
    }

    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = actionLogService.obtainActionLogSize(startDate, endDate);
        return totalItemSize;
    }

    public String getParameterValues() {
        return "&startDate=" + startDate + "&endDate=" + endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
