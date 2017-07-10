package com.changhong.system.domain.advertisment;

import java.util.Date;
import java.util.List;

import com.changhong.common.domain.EntityBase;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-11 
 * Time: 16:46:05
 *
 */
public class PlayItem extends EntityBase{
	
	private String name;
	private String description;
	private Integer index;
	private Date startDate;
	private Date endDate;
	
	private Integer repeat;
	private Integer amount = 0;
	
	private PlayContent playContent;

	private List<PlayItemContent> playItemContents;
	
	
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public PlayContent getPlayContent() {
		return playContent;
	}

	public void setPlayContent(PlayContent playContent) {
		this.playContent = playContent;
	}
	
	public List<PlayItemContent> getPlayItemContents() {
		return playItemContents;
	}

	public void setPlayItemContents(List<PlayItemContent> playItemContents) {
		this.playItemContents = playItemContents;
	}

	public PlayItem(){
		
	}
	
	

	public PlayItem(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	
	
	public PlayItem(String name, String description, int index, Date startDate,
			Date endDate, int repeat) {
		super();
		this.name = name;
		this.description = description;
		this.index = index;
		this.startDate = startDate;
		this.endDate = endDate;
		this.repeat = repeat;
	}

	public PlayItem(String name, String description, int index, Date startDate,
			Date endDate, int repeat, int amount, PlayContent playContent) {
		super();
		this.name = name;
		this.description = description;
		this.index = index;
		this.startDate = startDate;
		this.endDate = endDate;
		this.repeat = repeat;
		this.amount = amount;
		this.playContent = playContent;
	}
	
}
