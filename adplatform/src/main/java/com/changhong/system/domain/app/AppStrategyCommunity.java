package com.changhong.system.domain.app;

import com.changhong.common.domain.EntityBase;

public class AppStrategyCommunity extends EntityBase {
	
	private String communityUuid;
	private String strategyUuid;
	
	public AppStrategyCommunity(){
		
	}
	
	public AppStrategyCommunity(String communityUuid,String strategyUuid){
		this.communityUuid = communityUuid;
		this.strategyUuid = strategyUuid;
	}
	
	public String getCommunityUuid() {
		return communityUuid;
	}

	public void setCommunityUuid(String communityUuid) {
		this.communityUuid = communityUuid;
	}

	public String getStrategyUuid() {
		return strategyUuid;
	}
	
	public void setStrategyUuid(String strategyUuid) {
		this.strategyUuid = strategyUuid;
	}

}
