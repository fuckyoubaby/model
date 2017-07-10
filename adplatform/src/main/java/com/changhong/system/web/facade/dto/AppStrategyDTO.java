package com.changhong.system.web.facade.dto;

import java.io.Serializable;
import java.util.Date;

import com.changhong.system.domain.box.Area;
import com.changhong.system.domain.box.Community;

/**
 * 
 * @author yu
 *
 */
public class AppStrategyDTO implements Serializable{
	
	private String uuid;
	private String name;
	private Date timestampDistrbute;
    private String appVersion;
    private boolean strategyDistrbuteEnabled;
	private boolean strategyEnabled;
	
	public AppStrategyDTO(){
		
	}
	
	public AppStrategyDTO(String uuid,String name,Date timestampDistrbute,String appVersion,boolean strategyDistrbuteEnabled,boolean strategyEnabled){
		this.uuid = uuid;
		this.name = name;
		this.timestampDistrbute = timestampDistrbute;
		this.appVersion = appVersion;
		this.strategyDistrbuteEnabled = strategyDistrbuteEnabled;
		this.strategyEnabled = strategyEnabled;
	}

	public String getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public Date getTimestampDistrbute() {
		return timestampDistrbute;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public boolean isStrategyDistrbuteEnabled() {
		return strategyDistrbuteEnabled;
	}

	public boolean isStrategyEnabled() {
		return strategyEnabled;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTimestampDistrbute(Date timestampDistrbute) {
		this.timestampDistrbute = timestampDistrbute;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public void setStrategyDistrbuteEnabled(boolean strategyDistrbuteEnabled) {
		this.strategyDistrbuteEnabled = strategyDistrbuteEnabled;
	}

	public void setStrategyEnabled(boolean strategyEnabled) {
		this.strategyEnabled = strategyEnabled;
	}
	
}
