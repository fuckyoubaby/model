package com.changhong.system.domain.advertisment;

import java.util.List;

import com.changhong.common.domain.EntityBase;
import com.changhong.system.domain.upgrade.ContentUpgrade;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-11 
 * Time: 16:46:05
 *
 */
public class PlayContent extends EntityBase{
	
	private String name;
	private String version;
	private Double defaultDuration;
	private Integer amount =0;
	
	private ContentUpgrade contentUpgrade;
	
	private List<PlayItem> playItems;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public double getDefaultDuration() {
		return defaultDuration;
	}
	public void setDefaultDuration(double defaultDuration) {
		this.defaultDuration = defaultDuration;
	}
	
	public PlayContent(){
		
	}
	public PlayContent(String name, String version, double defaultDuration) {
		super();
		this.name = name;
		this.version = version;
		this.defaultDuration = defaultDuration;
	}
	public List<PlayItem> getPlayItems() {
		return playItems;
	}
	public void setPlayItems(List<PlayItem> playItems) {
		this.playItems = playItems;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public ContentUpgrade getContentUpgrade() {
		return contentUpgrade;
	}
	public void setContentUpgrade(ContentUpgrade contentUpgrade) {
		this.contentUpgrade = contentUpgrade;
	}
	
}
