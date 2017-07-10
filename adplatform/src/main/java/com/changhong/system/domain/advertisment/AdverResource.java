package com.changhong.system.domain.advertisment;

import java.util.List;

import com.changhong.common.domain.EntityBase;
import com.changhong.system.domain.uploaddata.UploadADData;
/**
 * User: Guo xiaoyang
 * Date: 2016-12-8
 * Time: 下午16:13
 */
public class AdverResource extends EntityBase{
	
	private String name;
	private String type;
	private String path;
	private String advertiser;
	private String agents;
	private Double size = 0.0;
	private boolean status = true;
	
	private List<UploadADData> uploadAdDats;
	
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
	
	
	public AdverResource(){
		
	}
	
	
	public AdverResource(String name, String type, String path) {
		super();
		this.name = name;
		this.type = type;
		this.path = path;
		this.status = true;
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


	public Double getSize() {
		return size;
	}


	public void setSize(Double size) {
		this.size = size;
	}


	public List<UploadADData> getUploadAdDats() {
		return uploadAdDats;
	}


	public void setUploadAdDats(List<UploadADData> uploadAdDats) {
		this.uploadAdDats = uploadAdDats;
	}

}
