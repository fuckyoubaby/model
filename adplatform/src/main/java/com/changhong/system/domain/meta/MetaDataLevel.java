package com.changhong.system.domain.meta;

import com.changhong.common.domain.Option;
import com.changhong.common.domain.SelectOption;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-21
 * Time: 上午10:58
 */
public enum MetaDataLevel {
    SYSTEM("系统默认"),
    USER("用户操作");

    private String description;

    MetaDataLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
