package com.hylanda.model.common.domain;

import java.util.Date;

public class StandardMediaName {

	private Long standardId;
	private String mediaName;
	private String siteType;
	private Date tmCreate;
	private String area;
	private int isRank;
	private String url;
	private String siteLevel;
	private String areaMediaName;
	public Long getStandardId() {
		return standardId;
	}
	public void setStandardId(Long standardId) {
		this.standardId = standardId;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getSiteType() {
		return siteType;
	}
	public void setSityType(String siteType) {
		this.siteType = siteType;
	}
	public Date getTmCreate() {
		return tmCreate;
	}
	public void setTmCreate(Date tmCreate) {
		this.tmCreate = tmCreate;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getIsRank() {
		return isRank;
	}
	public void setIsRank(int isRank) {
		this.isRank = isRank;
	}
	public String getSiteLevel() {
		return siteLevel;
	}
	public void setSiteLevel(String siteLevel) {
		this.siteLevel = siteLevel;
	}
	public String getAreaMediaName() {
		return areaMediaName;
	}
	public void setAreaMediaName(String areaMediaName) {
		this.areaMediaName = areaMediaName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
