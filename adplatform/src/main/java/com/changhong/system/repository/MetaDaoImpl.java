package com.changhong.system.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.meta.MetaData;
import com.changhong.system.domain.meta.MetaDataLevel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-21
 * Time: 上午9:41
 */
@Repository("metaDao")
public class MetaDaoImpl extends HibernateEntityObjectDao implements MetaDao {


    @Override
    public MetaData findSystmeDefaultMetaData() {
        StringBuilder builder = new StringBuilder();
        builder.append("from MetaData m where m.dataLevel = ?");
        List<MetaData> list =  getHibernateTemplate().find(builder.toString(), MetaDataLevel.SYSTEM);
        return list.get(0);
    }

    @Override
    public int findMetaDataSize() {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(m.uuid) from MetaData m");
        List list =  getHibernateTemplate().find(builder.toString());
        return ((Long)list.get(0)).intValue();
    }

    @Override
    public List<MetaData> findMetaDatas(int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("from MetaData m");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<MetaData> datas = query.list();
        return datas;
    }
}
