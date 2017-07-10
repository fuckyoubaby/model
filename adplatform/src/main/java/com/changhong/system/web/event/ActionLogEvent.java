package com.changhong.system.web.event;

import com.changhong.common.web.application.ApplicationEventPublisher;
import com.changhong.system.domain.log.ActionLog;
import com.changhong.system.domain.log.ActionModule;
import com.changhong.system.domain.log.ActionType;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.service.ActionLogService;

/**
 * User: Jack Wang
 * Date: 16-11-29
 * Time: 上午10:37
 */
public class ActionLogEvent extends AbstractEvent implements Runnable {

    private ActionType type;
    private ActionModule module;
    private String desc;

    public ActionLogEvent(String userUuid, String userXingMing, ActionType type, ActionModule module, String desc) {
        super(userUuid, userXingMing);
        this.type = type;
        this.module = module;
        this.desc = desc;
    }

    @Override
    public void run() {
        ActionLogService actionLogService = (ActionLogService) ApplicationEventPublisher.getBean("actionLogService");

        try {
            actionLogService.saveActionLog(new ActionLog(this));
        } catch (Exception e) {
            ApplicationLog.error(ActionLogEvent.class, "record error log failed", e);
        }
    }

    public ActionType getType() {
        return type;
    }

    public ActionModule getModule() {
        return module;
    }

    public String getDesc() {
        return desc;
    }
}
