package com.changhong.system.web.facade.dto;

public class AdDataDTO {

	private String uuid;
	private String resourceName;
	private double length;
	private int times;
	public AdDataDTO() {
		// TODO Auto-generated constructor stub
	}
	public AdDataDTO(String uuid, String resourceName, double length, int times) {
		super();
		this.uuid = uuid;
		this.resourceName = resourceName;
		this.length = length;
		this.times = times;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	@Override
	public String toString() {
		return "AdDataDTO [uuid=" + uuid + ", resourceName=" + resourceName
				+ ", length=" + length + ", times=" + times + "]";
	}
	
	
}
