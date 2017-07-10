package com.changhong.system.domain.app;

import com.changhong.common.domain.EntityBase;

public class AppStrategyArea extends EntityBase {

	private String strategyUuid;
	private String areaUuid;
	
	public AppStrategyArea(){
		
	}
	
	public AppStrategyArea(String strategyUuid,String areaUuid){
		this.strategyUuid = strategyUuid;
		this.areaUuid = areaUuid;
	}
	
	public String getStrategyUuid() {
		return strategyUuid;
	}
	
	public String getAreaUuid() {
		return areaUuid;
	}
	
	public void setStrategyUuid(String strategyUuid) {
		this.strategyUuid = strategyUuid;
	}
	
	public void setAreaUuid(String areaUuid) {
		this.areaUuid = areaUuid;
	}
	
}
