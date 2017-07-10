package com.changhong.system.web.paging;

import com.changhong.common.web.paging.AbstractPaging;
import com.changhong.system.service.UserService;
import com.changhong.system.web.facade.dto.UserDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:34
 */
public class UserOverviewPaging extends AbstractPaging<UserDTO> {

    private UserService userService;

    private String filterName;

    public UserOverviewPaging(UserService userService) {
        this.userService = userService;
    }

    public List<UserDTO> getItems() {
        return userService.obtainUsers(filterName, getStartPosition(), getPageSize());
    }

    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = userService.obtainUserSize(filterName);
        return totalItemSize;
    }

    public String getParameterValues() {
        return "&filterName=" + getFilterName();
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
}

