package com.changhong.statistic.service;

import com.alibaba.fastjson.JSONObject;

/**
 * User: Jack Wang
 * Date: 16-11-25
 * Time: 上午9:26
 */
public interface StatisticService {

    JSONObject obtainBoxNumberStaData(String areaUuid);
}
