package com.changhong.system.web.facade.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * User: Guo xiaoyang
 * Date: 16-12-8
 * Time: 下午16:33
 */
public class AdverResourceDTO implements Serializable{
	
	private String uuid;
	private String name;
	private String type;
	private String path;
	private String advertiser;
	private String agents;

	private boolean status;
	private Date timestamp;
	private double size;
	
	public AdverResourceDTO(){
		
	}
	
	public AdverResourceDTO(String uuid, String name, String type, String path, double size,
			boolean status) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.type = type;
		this.path = path;
		this.size = size;
		this.status = status;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(String advertiser) {
		this.advertiser = advertiser;
	}

	public String getAgents() {
		return agents;
	}

	public void setAgents(String agents) {
		this.agents = agents;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}
}
