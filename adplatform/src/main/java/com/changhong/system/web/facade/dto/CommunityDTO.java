package com.changhong.system.web.facade.dto;

import java.io.Serializable;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 17:45
 */
public class CommunityDTO implements Serializable {

    private String uuid;

    private String timestamp;

    private String name;

    private String abbreviation;

    private String comment;

    private String areaUuid;

    private String areaFullPath;

    private MetaDataDTO metaData;

    public CommunityDTO() {
    }

    public CommunityDTO(String areaUuid) {
        this.areaUuid = areaUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

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

    public String getAreaUuid() {
        return areaUuid;
    }

    public void setAreaUuid(String areaUuid) {
        this.areaUuid = areaUuid;
    }

    public String getAreaFullPath() {
        return areaFullPath;
    }

    public void setAreaFullPath(String areaFullPath) {
        this.areaFullPath = areaFullPath;
    }

    public MetaDataDTO getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaDataDTO metaData) {
        this.metaData = metaData;
    }
}
