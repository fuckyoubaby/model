package com.changhong.system.web.facade.dto;

import java.io.Serializable;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-17 
 * Time: 17:25:49
 */
public class PlayItemContentDTO implements Serializable{

	private String uuid;
	private Integer index;
	private Integer repeat;
	private Double duration;
	private String playItemUuid;
	
	private String resourceUuid;
	
	private AdverResourceDTO adverResourceDTO;

	public PlayItemContentDTO(){
		super();
	}
	
	
	public PlayItemContentDTO(String uuid, Integer index, Integer repeat,
			Double duration, String playItemUuid, String resourceUuid) {
		super();
		this.uuid = uuid;
		this.index = index;
		this.repeat = repeat;
		this.duration = duration;
		this.playItemUuid = playItemUuid;
		this.resourceUuid = resourceUuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getPlayItemUuid() {
		return playItemUuid;
	}

	public void setPlayItemUuid(String playItemUuid) {
		this.playItemUuid = playItemUuid;
	}

	public AdverResourceDTO getAdverResourceDTO() {
		return adverResourceDTO;
	}

	public void setAdverResourceDTO(AdverResourceDTO adverResourceDTO) {
		this.adverResourceDTO = adverResourceDTO;
	}


	public String getResourceUuid() {
		return resourceUuid;
	}


	public void setResourceUuid(String resourceUuid) {
		this.resourceUuid = resourceUuid;
	}
	
	
}
