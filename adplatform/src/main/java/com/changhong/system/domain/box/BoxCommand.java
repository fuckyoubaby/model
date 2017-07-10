package com.changhong.system.domain.box;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 16-11-22
 * Time: 上午10:14
 */
public class BoxCommand extends EntityBase {

    private String mac;
    private String comment;
    private BoxCommandType commandType;
    private String planTime;
    private boolean finished;

    public BoxCommand() {
    }

    public BoxCommand(String mac, BoxCommandType commandType, String comment) {
        this.mac = mac;
        this.commandType = commandType;
        this.comment = comment;
        this.finished = false;
    }

    /***************************************************GETTER*************************************************/

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public BoxCommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(BoxCommandType commandType) {
        this.commandType = commandType;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
