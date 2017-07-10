package com.changhong.system.domain.log;

import com.changhong.common.domain.EntityBase;
import com.changhong.system.web.event.ActionLogEvent;

/**
 * User: Jack Wang
 * Date: 16-11-29
 * Time: 上午10:21
 */
public class ActionLog extends EntityBase {

    private String userUuid;
    private String userXingMing;

    private ActionType actionType;
    private ActionModule actionModule;
    private String actionDesc;

    public ActionLog() {
    }

    public ActionLog(ActionLogEvent event) {
        this.userUuid = event.getUserUuid();
        this.userXingMing = event.getUserXingMing();
        this.actionType = event.getType();
        this.actionModule = event.getModule();
        this.actionDesc = event.getDesc();
    }

    /***************************************************GETTER*************************************************/

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserXingMing() {
        return userXingMing;
    }

    public void setUserXingMing(String userXingMing) {
        this.userXingMing = userXingMing;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public ActionModule getActionModule() {
        return actionModule;
    }

    public void setActionModule(ActionModule actionModule) {
        this.actionModule = actionModule;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }
}
