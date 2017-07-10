package com.changhong.system.domain.uploaddata;

import java.math.BigDecimal;
import java.util.Date;

import com.changhong.common.domain.EntityBase;
import com.changhong.system.domain.advertisment.AdverResource;

/**
 * 广告资源播放情况对象
 * @author shijinxiang
 *
 */
public class UploadADData extends EntityBase{
	
	private AdverResource adverResource;
	private Date uploadDate;
	private int times;
	private BigDecimal duration;
	private BigDecimal totalTime;
	private String macNumber;
	
	public UploadADData()
	{
		
	}

	public UploadADData(AdverResource adverResource, Date uploadDate,
			Date endDate, int times, BigDecimal duration, BigDecimal totalTime,String macNumber) {
		super();
		this.adverResource = adverResource;
		this.uploadDate = uploadDate;
		this.times = times;
		this.duration = duration;
		this.totalTime = totalTime;
		this.macNumber = macNumber;
	}

	public AdverResource getAdverResource() {
		return adverResource;
	}

	public void setAdverResource(AdverResource adverResource) {
		this.adverResource = adverResource;
	}


	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getMacNumber() {
		return macNumber;
	}

	public void setMacNumber(String macNumber) {
		this.macNumber = macNumber;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public BigDecimal getDuration() {
		return duration;
	}

	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}

	public BigDecimal getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(BigDecimal totalTime) {
		this.totalTime = totalTime;
	}



	@Override
	public String toString() {
		return "UploadADData [adverResource=" + adverResource + ", uploadDate="
				+ uploadDate + ", times=" + times + ", duration=" + duration
				+ ", totalTime=" + totalTime + ", macNumber=" + macNumber
				+"]";
	}

	

}
