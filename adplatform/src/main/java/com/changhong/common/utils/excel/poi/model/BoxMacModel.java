package com.changhong.common.utils.excel.poi.model;
/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-20 
 * Time: 13:57:03
 *
 */
public class BoxMacModel {

	private String mac;

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	//无参构造函数是必须的
	public BoxMacModel(){
		
	}
	
	public BoxMacModel(String mac) {
		super();
		this.mac = mac;
	}

	public static String[] getFileNames(){
		return new String[]{"mac"};
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof BoxMacModel){
			return this.mac.equals(((BoxMacModel)obj).getMac());
		}else return false;
	}
	
	@Override
	//返回Hash值，去重时使用
	public int hashCode() {
		return this.mac.hashCode();
	}
	
}
