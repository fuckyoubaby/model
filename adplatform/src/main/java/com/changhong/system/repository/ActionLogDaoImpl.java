package com.changhong.system.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.system.domain.log.ActionLog;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-29
 * Time: 上午10:56
 */
@Repository("actionLogDao")
public class ActionLogDaoImpl extends HibernateEntityObjectDao implements ActionLogDao {

    @Override
    public List<ActionLog> findActionLogs(String startDate, String endDate, int startPosition, int pageSize) {
        Date from = CHDateUtils.parseSimpleDateFormat(startDate);
        Date to = CHDateUtils.parseSimpleDateFormat(endDate);

        List<Object> parameter = new ArrayList<Object>();
        StringBuilder builder = new StringBuilder();
        builder.append("from ActionLog a where a.timestamp >= ? and a.timestamp <=? order by a.timestamp desc");
        parameter.add(from);
        parameter.add(to);

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);
        for(int i = 0; i < parameter.size(); ++i) {
            query.setParameter(i, parameter.get(i));
        }

        List<ActionLog> logs = query.list();
        return logs;
    }

    @Override
    public int findActionLogSize(String startDate, String endDate) {
        Date from = CHDateUtils.parseSimpleDateFormat(startDate);
        Date to = CHDateUtils.parseSimpleDateFormat(endDate);

        List<Object> parameter = new ArrayList<Object>();
        StringBuilder builder = new StringBuilder();
        builder.append("select count(a.uuid) from ActionLog a where a.timestamp >= ? and a.timestamp <=? order by a.timestamp desc");
        parameter.add(from);
        parameter.add(to);

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        for(int i = 0; i < parameter.size(); ++i) {
            query.setParameter(i, parameter.get(i));
        }

        List list = query.list();
        return ((Long)list.get(0)).intValue();
    }
}
