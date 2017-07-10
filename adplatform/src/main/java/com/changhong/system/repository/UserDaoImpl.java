package com.changhong.system.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.user.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:26
 */
@Repository("userDao")
public class UserDaoImpl extends HibernateEntityObjectDao implements UserDao {

    public UserDetails findUserByName(String username) {
        List<User> users = getHibernateTemplate().find("from User u where u.username = ? and u.enabled = true", username);
        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> loadUsers(String xingMing, int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("from User u");
        if (StringUtils.hasText(xingMing)) {
            builder.append(" where u.xingMing like '" + xingMing + "%' or u.username like '" + xingMing + "%'");
        }

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<User> users = query.list();
        return users;
    }

    public int loadUserSize(String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(u.uuid) from User u");
        if (StringUtils.hasText(name)) {
            builder.append(" where u.xingMing like '" + name + "%' or u.username like '" + name + "%'");
        }
        List list =  getHibernateTemplate().find(builder.toString());
        return ((Long)list.get(0)).intValue();
    }

    public boolean loadUserExist(String userUuid, String username) {
        if (!StringUtils.hasText(userUuid)) {
            List list = getHibernateTemplate().find("select count(u.uuid) from User u where u.username = ?", username);
            return ((Long)list.get(0)).intValue() > 0 ? true : false;
        } else {
            List list = getHibernateTemplate().find("select count(u.uuid) from User u where u.username = ? and u.uuid <> ?",
                    new Object[]{username, userUuid});
            return ((Long)list.get(0)).intValue() > 0 ? true : false;
        }
    }
}
