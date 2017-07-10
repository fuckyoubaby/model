package com.changhong.statistic.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.common.domain.EntityBase;
import com.changhong.common.utils.CHListUtils;
import com.changhong.statistic.repository.StatisticDao;
import com.changhong.system.domain.box.Area;
import com.changhong.system.domain.box.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * User: Jack Wang
 * Date: 16-11-25
 * Time: 上午9:26
 */
@Service("statisticService")
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticDao statisticDao;

    @Override
    public JSONObject obtainBoxNumberStaData(String areaUuid) {

        /************************************************************************/
        //获取地区
        Area area = (Area)statisticDao.findByUuid(areaUuid, Area.class);
        List<Area> childAreas = area.getChildAreas();
        List<Community> communities = area.getCommunities();
        if (childAreas == null) {
            childAreas = new ArrayList<>();
        }
        if (communities == null) {
            communities = new ArrayList<>();
        }

        /************************************************************************/
        //设置名字MAP和数量的MAP
        Map<String, String> nameMap = new HashMap<>();
        Map<String, String> numberMap = new HashMap<>();
        //1 - 查找区域下的数量
        for (Area loop : childAreas) {
            String uuid = loop.getUuid();

            //名字
            nameMap.put(uuid, loop.getName());
            //数量
            List<String> communityUuids = new ArrayList<>();
            List<Area> subAreas = loop.getAllSitesBelowWithItself();
            for (Area sub : subAreas) {
                List<String> uuids = CHListUtils.getAllUuids(sub.getCommunities());
                communityUuids.addAll(uuids);
            }
            int total = 0, online = 0;
            if (!communityUuids.isEmpty()) {
                total = statisticDao.findTotalBoxByCommunities(communityUuids);
                online = statisticDao.findOnlineBoxByCommunities(communityUuids);
            }
            numberMap.put(uuid, total + "|" + online);

        }

        //2 - 查找区域下小区的数量
        List<String> communityUuids = CHListUtils.getAllUuids(communities);
        Map<String, Long> totalModel = new HashMap<>();
        Map<String, Long> onlineModel = new HashMap<>();
        if (!communityUuids.isEmpty()) {
            totalModel = statisticDao.findTotalBoxByCommunitiesGroup(communityUuids);
            onlineModel = statisticDao.findOnlineBoxByCommunitiesGroup(communityUuids);
        }
        for (Community loop : communities) {
            String uuid = loop.getUuid();

            //名字
            nameMap.put(uuid, loop.getName());
            //数量
            int total = totalModel.get(uuid) != null ? totalModel.get(uuid).intValue() : 0;
            int online = onlineModel.get(uuid) != null ? onlineModel.get(uuid).intValue() : 0;
            numberMap.put(uuid, total + "|" + online);
        }

        /************************************************************************/
        //组装返回数据
        JSONObject o = new JSONObject();
        JSONArray array = new JSONArray();
        Set<String> nameKeys = nameMap.keySet();
        for (String nameKey : nameKeys) {
            JSONObject single = new JSONObject();
            single.put("name", nameMap.get(nameKey));

            String temp = numberMap.get(nameKey);
            String[] tokens = StringUtils.delimitedListToStringArray(temp, "|");
            single.put("online", tokens[1]);
            single.put("offline", Integer.valueOf(tokens[0]) - Integer.valueOf(tokens[1]));
            single.put("total", tokens[0]);

            array.add(single);
        }
        o.put("items", array);
        return o;
    }

}
