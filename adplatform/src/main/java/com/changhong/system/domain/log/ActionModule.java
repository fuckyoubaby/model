package com.changhong.system.domain.log;

/**
 * User: Jack Wang
 * Date: 16-11-29
 * Time: 上午10:23
 */
public enum ActionModule {

    A_TOUYING("投影仪模块"),
    A_SYSTEM("系统设置模块"),
    A_STA("统计模块"),
    A_AD("广告模块"),
    A_LOGIN("登录");

    private String description;

    ActionModule(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
