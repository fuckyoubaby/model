package com.changhong.common.web.tag;

import com.changhong.system.domain.box.BoxMacStatus;
/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-16 
 * Time: 16:03:39
 *
 */
public class FunctionProcess {
	/**
	 * 显示盒子Mac状态的描述信息
	 * @param status 状态字母值
	 * @return
	 */
	public static String displayMacStatus(String status){
		try{
			BoxMacStatus bms = BoxMacStatus.valueOf(status);
			return bms.getDescription();
		}catch(Exception e){
			return status;
		}
	} 
	
	public static void main(String[] args) {
		System.out.println(FunctionProcess.displayMacStatus("B_INITE"));
	}
}
