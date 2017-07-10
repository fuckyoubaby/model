package com.changhong.system.domain.box;

import com.changhong.common.domain.Option;
import com.changhong.common.domain.SelectOption;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-22
 * Time: 上午10:16
 */
public enum BoxCommandType {

    C_UPDATE("升级控制"),
    C_RESTART("重启控制"),
    C_STOP("关机控制"),
	C_APK_CHECK("apk更新检测"),
	C_RESOURCE_CHECK("资源更新检测"),
	C_RESOURCE_PLAN("资源更新计划");

    private String description;

    BoxCommandType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<Option> getOptions() {
        List<Option> options = new ArrayList<>();
        BoxCommandType[] values = BoxCommandType.values();
        for (BoxCommandType value : values) {
            options.add(new SelectOption(value.getDescription(), value.name()));
        }
        return options;
    }
}
