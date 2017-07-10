package com.changhong.system.domain.app;

import java.util.Date;

import com.changhong.common.domain.EntityBase;

/**
 * @author yu
 * @date 2017年1月13日
 * @time 下午4:35:23
 */
public class AppStrategy extends EntityBase {

	private String name;
	
	private Date timestampDistribute;
	
    private String appVersion;
    
    private boolean strategyEnabled = true;     //初始化的是true
    
    private boolean strategyDistributeEnabled = false;   //是否发布
    
    public AppStrategy(){
		
	}
    
	public AppStrategy(String name,String appVersion){
		this.name = name;
		this.appVersion = appVersion;
		strategyEnabled = true;
		strategyDistributeEnabled = false;
	}

	public Date getTimestampDistribute() {
		return timestampDistribute;
	}

	public void setTimestampDistribute(Date timestampDistribute) {
		this.timestampDistribute = timestampDistribute;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
	public boolean isStrategyEnabled() {
		return strategyEnabled;
	}

	public void setStrategyEnabled(boolean strategyEnabled) {
		this.strategyEnabled = strategyEnabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStrategyDistributeEnabled() {
		return strategyDistributeEnabled;
	}

	public void setStrategyDistributeEnabled(boolean strategyDistributeEnabled) {
		this.strategyDistributeEnabled = strategyDistributeEnabled;
	}
	
}
