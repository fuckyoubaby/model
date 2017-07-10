package com.changhong.system.domain.app;

import com.changhong.common.domain.EntityBase;

public class AppStrategyBox extends EntityBase{
	
	private String strategyUuid;
    private String macNumber;

    public AppStrategyBox(){
    	
    }
    
    public AppStrategyBox(String strategyUuid,String macNumber){
    	this.strategyUuid = strategyUuid;
    	this.macNumber = macNumber;
    }
    
	public String getMacNumber() {
		return macNumber;
	}

	public void setMacNumber(String macNumber) {
		this.macNumber = macNumber;
	}

	public String getStrategyUuid() {
		return strategyUuid;
	}
	
	public void setStrategyUuid(String strategyUuid) {
		this.strategyUuid = strategyUuid;
	}
    
}
