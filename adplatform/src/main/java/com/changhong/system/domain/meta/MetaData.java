package com.changhong.system.domain.meta;

import com.changhong.common.domain.EntityBase;
import org.omg.CORBA.StringHolder;

/**
 * User: Jack Wang
 * Date: 16-11-21
 * Time: 上午8:53
 */
public class MetaData extends EntityBase {

    private MetaDataLevel dataLevel;
    private String startTime;
    private String endTime;
    private String heartInterval;
    private String note;

    public MetaData() {
        this.dataLevel = MetaDataLevel.USER;
    }

    /*************************************************GETTER**********************************************************/

    public MetaDataLevel getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(MetaDataLevel dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHeartInterval() {
        return heartInterval;
    }

    public void setHeartInterval(String heartInterval) {
        this.heartInterval = heartInterval;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
