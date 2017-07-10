package com.changhong.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * Author: Guoxiaoyang
 * Date: 2017-2-10 
 * Time: 10:25:05
 *
 */
public class RecordeResourceInfo implements Serializable{

	private long amount;
	private BigDecimal size;
	
	public RecordeResourceInfo(long amount, BigDecimal size) {
		super();
		this.amount = amount;
		this.size = size;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public BigDecimal getSize() {
		return size;
	}
	public void setSize(BigDecimal size) {
		this.size = size;
	}
}
