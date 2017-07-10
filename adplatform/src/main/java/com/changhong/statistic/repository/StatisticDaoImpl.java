package com.changhong.statistic.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.common.utils.CHStringUtils;
import com.changhong.system.domain.box.Box;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 16-11-25
 * Time: 上午11:00
 */
@Repository("statisticDao")
public class StatisticDaoImpl extends HibernateEntityObjectDao implements StatisticDao {

    @Override
    public int findTotalBoxByCommunities(List<String> communityUuids) {
        String sqlIn = CHStringUtils.convertListToSQLIn(communityUuids);
        List list = getHibernateTemplate().find("select count(b.uuid) from Box b where b.community.uuid in (" + sqlIn + ")");
        return ((Long)list.get(0)).intValue();
    }

    @Override
    public int findOnlineBoxByCommunities(List<String> communityUuids) {
        String sqlIn = CHStringUtils.convertListToSQLIn(communityUuids);
        Date after = Box.shouldAfterIsOnline();
        List list = getHibernateTemplate().find("select count(b.uuid) from Box b where b.community.uuid in (" + sqlIn + ") and b.lastReportTime >= ?", new Object[]{after});
        return ((Long) list.get(0)).intValue();
    }

    @Override
    public Map findTotalBoxByCommunitiesGroup(List<String> communityUuids) {
        String sqlIn = CHStringUtils.convertListToSQLIn(communityUuids);
        List list = getHibernateTemplate().find("select count(b.uuid), b.community.uuid from Box b where b.community.uuid in (" + sqlIn + ") group by b.community");

        Map<String, Long> model = new HashMap<>();
        for (Object o : list) {
            Object[] values = (Object[]) o;
            model.put((String)values[1], ((Long)values[0]));
        }
        return model;
    }

    @Override
    public Map findOnlineBoxByCommunitiesGroup(List<String> communityUuids) {
        String sqlIn = CHStringUtils.convertListToSQLIn(communityUuids);
        Date after = Box.shouldAfterIsOnline();
        List list = getHibernateTemplate().find("select count(b.uuid), b.community.uuid from Box b where b.community.uuid in (" + sqlIn + ") and b.lastReportTime >= ? group by b.community", new Object[]{after});

        Map<String, Long> model = new HashMap<>();
        for (Object o : list) {
            Object[] values = (Object[]) o;
            model.put((String)values[1], ((Long)values[0]));
        }
        return model;
    }

	@Override
	public List<Box> findBoxByCommunities(List<String> communityUuids) {
		 String sqlIn = CHStringUtils.convertListToSQLIn(communityUuids);
	     List list = getHibernateTemplate().find("from Box b where b.community.uuid in (" + sqlIn + ")");
		 return list;
	}
    
}
