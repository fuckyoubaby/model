package com.changhong.system.domain.log;

/**
 * User: Jack Wang
 * Date: 16-11-29
 * Time: 上午10:23
 */
public enum ActionType {

    A_LOG("日志"),
    A_ERROR("错误");

    private String description;

    ActionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
