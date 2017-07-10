package com.changhong.system.web.facade.dto;

import java.io.Serializable;

public class AppStrategyCommunityDTO implements Serializable {
	
	private String uuid;
	private String communityUuid;
	private String strategyUuid;
	
	public AppStrategyCommunityDTO(){
		
	}
	
	public AppStrategyCommunityDTO(String uuid,String communityUuid,String strategyUuid){
		this.uuid = uuid;
		this.communityUuid = communityUuid;
		this.strategyUuid = strategyUuid;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public String getCommunityUuid() {
		return communityUuid;
	}
	
	public String getStrategyUuid() {
		return strategyUuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public void setCommunityUuid(String communityUuid) {
		this.communityUuid = communityUuid;
	}
	
	public void setStrategyUuid(String strategyUuid) {
		this.strategyUuid = strategyUuid;
	}
	
}
