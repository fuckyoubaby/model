package com.changhong.system.web.event;

/**
 * User: Jack Wang
 * Date: 16-11-29
 * Time: 上午10:42
 */
public class AbstractEvent {

    private String userUuid;
    private String userXingMing;

    public AbstractEvent(String userUuid, String userXingMing) {
        this.userUuid = userUuid;
        this.userXingMing = userXingMing;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getUserXingMing() {
        return userXingMing;
    }
}
