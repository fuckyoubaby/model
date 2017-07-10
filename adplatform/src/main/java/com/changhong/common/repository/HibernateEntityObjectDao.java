package com.changhong.common.repository;

import com.changhong.common.domain.EntityBase;
import com.changhong.common.repository.EntityObjectDao;
import com.changhong.common.utils.CHListUtils;
import com.changhong.common.utils.CHStringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:24
 */
public class HibernateEntityObjectDao extends HibernateDaoSupport implements EntityObjectDao {

    @Autowired
    public final void setEntityManagerFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public void saveOrUpdate(Object object) {
        getHibernateTemplate().saveOrUpdate(object);
    }

    public void persist(Object object) {
        getHibernateTemplate().persist(object);
        getHibernateTemplate().flush();
    }

    public void delete(Object object) {
        getHibernateTemplate().delete(object);
        getHibernateTemplate().flush();
    }

    public <T extends EntityBase> EntityBase findByUuid(String uuid, Class<T> clazz) {
        List<T> list = getHibernateTemplate().find("from " + clazz.getName() + " where uuid = ?", new Object[]{uuid});
        return CHListUtils.hasElement(list) ? list.get(0) : null;
    }

    public <T extends EntityBase> List<T> findByUuids(String[] uuids, Class<T> clazz) {
        if (uuids.length > 0) {
            return getHibernateTemplate().find("from " + clazz.getName() + " do where do.uuid in (" + CHStringUtils.convertListToSQLIn(uuids) + ")");
        }
        return new ArrayList<T>();
    }

    public void saveAll(List list) {
        getHibernateTemplate().saveOrUpdateAll(list);
    }

    public Transaction getCurrentTx() {
        return getHibernateTemplate().getSessionFactory().getCurrentSession().getTransaction();
    }
}
