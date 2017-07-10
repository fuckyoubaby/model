package com.changhong.system.web.facade.dto;

import java.io.Serializable;

public class AppStrategyBoxDTO implements Serializable {

	private String uuid;
	private String strategyUuid;
    private String macNumber;
    
    public AppStrategyBoxDTO(){
    	
    }
    
    public AppStrategyBoxDTO(String uuid,String strategyUuid,String macNumber){
    	this.uuid = uuid;
    	this.strategyUuid = strategyUuid;
    	this.macNumber = macNumber;
    }
    
	public String getMacNumber() {
		return macNumber;
	}

	public void setMacNumber(String macNumber) {
		this.macNumber = macNumber;
	}

	public String getUuid() {
		return uuid;
	}
	
	public String getStrategyUuid() {
		return strategyUuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public void setStrategyUuid(String strategyUuid) {
		this.strategyUuid = strategyUuid;
	}
    
}
