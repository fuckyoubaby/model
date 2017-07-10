package com.changhong.system.web.facade.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-16 
 * Time: 09:53:45
 */
public class PlayItemDTO implements Serializable{
	
	private String uuid;
	private String name;
	private String description;
	private int amount;
	
	private Integer index;

	private Date startDate;
	private Date endDate;
	private Integer repeat;

	private String playContentUuid;
	
	
	public PlayItemDTO(){
		super();
	}
	
	public PlayItemDTO(String uuid, String name, String description) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.description = description;
	}


	public PlayItemDTO(String uuid, String name, String description, int index,
			Date startDate, Date endDate, Integer repeat) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.description = description;
		this.index = index;
		this.startDate = startDate;
		this.endDate = endDate;
		this.repeat = repeat;
	}

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getRepeat() {
		return repeat;
	}
	public void setRepeat(Integer repeat) {
		this.repeat = repeat;
	}
	public String getPlayContentUuid() {
		return playContentUuid;
	}
	public void setPlayContentUuid(String playContentUuid) {
		this.playContentUuid = playContentUuid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
