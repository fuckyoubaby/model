package com.changhong.system.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.box.Community;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 20:06
 */
@Repository("communityDao")
public class CommunityDaoImpl extends HibernateEntityObjectDao implements CommunityDao {

    @Override
    public List<Community> loadCommunities(String areaUuid, String name, int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        List<Object> parameter = new ArrayList<Object>();

        builder.append("from Community c where c.area.uuid = ?");
        parameter.add(areaUuid);
        if (StringUtils.hasText(name)) {
            builder.append(" and (c.name like ? or c.abbreviation like ?");
            parameter.add("%" + name + "%");
            parameter.add("%" + name + "%");
        }
        builder.append(" order by c.timestamp asc");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        for(int i = 0; i < parameter.size(); ++i) {
            query.setParameter(i, parameter.get(i));
        }
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<Community> communities = query.list();
        return communities;
    }

    @Override
    public int loadCommunitySize(String areaUuid, String name) {
        StringBuilder builder = new StringBuilder();
        List<Object> parameter = new ArrayList<Object>();
        builder.append("select count(*) from Community c where c.area.uuid = ?");
        parameter.add(areaUuid);
        if (StringUtils.hasText(name)) {
            builder.append(" and (c.name like ? or c.abbreviation like ?");
            parameter.add("%" + name + "%");
            parameter.add("%" + name + "%");
        }
        List list = getHibernateTemplate().find(builder.toString(),parameter.toArray());
        return ((Long)list.get(0)).intValue();
    }
}
