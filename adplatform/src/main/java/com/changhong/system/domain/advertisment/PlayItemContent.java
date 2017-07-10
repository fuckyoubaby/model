package com.changhong.system.domain.advertisment;

import com.changhong.common.domain.EntityBase;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-11 
 * TIme: 16:42:15
 */
public class PlayItemContent extends EntityBase{
	
	private String resourceUuid;
	private Integer index;
	private Integer repeat;
	private Double duration;
	private PlayItem playItem;
	
	public String getResourceUuid() {
		return resourceUuid;
	}
	public void setResourceUuid(String resourceUuid) {
		this.resourceUuid = resourceUuid;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Integer getRepeat() {
		return repeat;
	}
	public void setRepeat(Integer repeat) {
		this.repeat = repeat;
	}
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	public PlayItem getPlayItem() {
		return playItem;
	}
	public void setPlayItem(PlayItem playItem) {
		this.playItem = playItem;
	}
	
	public PlayItemContent(){
		
	}
	
	public PlayItemContent(String resourceUuid, Integer index, Integer repeat,
			Double duration, PlayItem playItem) {
		super();
		this.resourceUuid = resourceUuid;
		this.index = index;
		this.repeat = repeat;
		this.duration = duration;
		this.playItem = playItem;
	}
	
	
}
