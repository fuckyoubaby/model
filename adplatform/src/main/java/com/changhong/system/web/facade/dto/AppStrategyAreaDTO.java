package com.changhong.system.web.facade.dto;

import java.io.Serializable;

public class AppStrategyAreaDTO implements Serializable {
	
	private String uuid;
	private String strategyUuid;
	private String areaUuid;
	
	public AppStrategyAreaDTO(){
		
	}
	
	public AppStrategyAreaDTO(String uuid, String strategyUuid,String areaUuid){
		this.uuid = uuid;
		this.strategyUuid = strategyUuid;
		this.areaUuid = areaUuid;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public String getStrategyUuid() {
		return strategyUuid;
	}
	
	public String getAreaUuid() {
		return areaUuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public void setStrategyUuid(String strategyUuid) {
		this.strategyUuid = strategyUuid;
	}
	
	public void setAreaUuid(String areaUuid) {
		this.areaUuid = areaUuid;
	}
	
}
