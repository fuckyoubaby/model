package com.changhong.system.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author yu
 * @date 2017年2月21日
 * @time 下午2:54:04
 */
public interface AppVersionStatisticService {
	
	/**
	 * 通过areaUuid获取版本信息并处理成JSONObject返回
	 * @param areaUuid
	 * @return
	 */
	public JSONObject obtainAppVersionNumberStaData(String areaUuid);

}
