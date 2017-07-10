package com.changhong.client.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.BoxCommand;
import com.changhong.system.domain.box.Community;
import com.changhong.system.domain.user.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:25
 */
@Repository("mobileDao")
public class MobileDaoImpl extends HibernateEntityObjectDao implements MobileDao {

    @Override
    public User findUserByUsername(String mobile) {
        List<User> list = getHibernateTemplate().find("from User u where u.username = ? and u.enabled = true", new Object[]{mobile});
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Community> findCommunity(String words, int number) {
        StringBuilder builder = new StringBuilder();
        builder.append("from Community c");
        if (StringUtils.hasText(words)) {
            builder.append(" where c.name like '%" + words + "%'");
        }
        builder.append(" order by c.timestamp desc");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(number);
        query.setFirstResult(0);

        List<Community> communities = query.list();
        return communities;
    }

    @Override
    public int findBoxSizeByCommunityUuid(String communityUuid) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(b.uuid) from Box b where b.community.uuid = '" + communityUuid + "'");
        List list =  getHibernateTemplate().find(builder.toString());
        return ((Long)list.get(0)).intValue();
    }

    @Override
    public List<Box> findBoxByCommunityUuid(String communityUuid, int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("from Box b where b.community.uuid = '" + communityUuid + "'");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<Box> boxes = query.list();
        return boxes;
    }
}
