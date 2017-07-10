package com.changhong.system.domain.box;

import com.changhong.common.domain.EntityBase;
import com.changhong.system.domain.meta.MetaData;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 17:11
 */
public class Community extends EntityBase {

    private String name;

    private String abbreviation;

    private String comment;

    private Area area;

    private MetaData metaData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}
