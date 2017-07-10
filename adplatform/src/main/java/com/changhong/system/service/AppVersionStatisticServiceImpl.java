package com.changhong.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.common.utils.CHListUtils;
import com.changhong.statistic.repository.StatisticDao;
import com.changhong.system.domain.box.Area;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.Community;
import com.changhong.system.repository.BoxInfoDao;
import com.google.gson.JsonArray;

@Service("appVersionStaticServiceImpl")
public class AppVersionStatisticServiceImpl implements AppVersionStatisticService {
	
	@Autowired
	private StatisticDao statisticDao;
	
	@Autowired
	private BoxInfoDao boxInfoDao;
	
	@Override
	public JSONObject obtainAppVersionNumberStaData(String areaUuid) {
		
		//获取地区
        Area area = (Area)statisticDao.findByUuid(areaUuid, Area.class);
        List<Area> childAreas = area.getChildAreas();
        //查小区
        List<Community> communities = area.getCommunities();
        
        if (childAreas == null) {
            childAreas = new ArrayList<>();
        }
        if (communities == null) {
            communities = new ArrayList<>();
        }
        
        List<Box> boxList = new ArrayList<Box>();
        List<Box> boxAll = new ArrayList<Box>();
        for (Area loop : childAreas) {     //在childAreas不为空的情况下，下面有子区域
        	 List<String> communityUuids = new ArrayList<>();
             List<Area> subAreas = loop.getAllSitesBelowWithItself();
             
             for (Area sub : subAreas) {
                 List<String> uuids = CHListUtils.getAllUuids(sub.getCommunities());
                 communityUuids.addAll(uuids);
             }
             
             if (!communityUuids.isEmpty()) {
            	 boxAll = statisticDao.findBoxByCommunities(communityUuids);    //取到所有的box
            	 if(boxAll!=null){
            		 for(Box box:boxAll){
            			 boxList.add(box);
            		 }
            	 }
             }
        }
        
        //小区
        List<String> communityUuids = CHListUtils.getAllUuids(communities);
        if (!communityUuids.isEmpty()) {
            boxList = statisticDao.findBoxByCommunities(communityUuids);
        }
        
        List<String> listVersion = new ArrayList<String>();
        if(boxList!=null){
        	for(int j=0;j<boxList.size();j++){
            	listVersion.add(boxList.get(j).getFirmwareVersion());
            }
        }
        
        //统计
        Map<String,Integer> map = new HashMap<String,Integer>();
        
        for(String loop:listVersion){
        	if(map.containsKey(loop)){
        		map.put(loop, map.get(loop).intValue() + 1);
        	}else{
				map.put(loop, 1);
			}
        }
        
        Set<String> versions = new HashSet<String>(listVersion);
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        for(String versionName:versions){
        	JSONObject single = new JSONObject();
        	single.put("name", versionName);
        	single.put("number", map.get(versionName));
        	array.add(single);
        }
        jsonObject.put("items", array);
        
        return jsonObject;
	}

}
