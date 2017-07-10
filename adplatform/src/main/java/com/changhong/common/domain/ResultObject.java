package com.changhong.common.domain;

public class ResultObject {

	private boolean result;
	
	private String description;
	
	public ResultObject(boolean result, String description) {
		super();
		this.result = result;
		this.description = description;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
