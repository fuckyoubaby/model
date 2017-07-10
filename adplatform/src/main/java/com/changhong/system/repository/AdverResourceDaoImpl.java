package com.changhong.system.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;





import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.advertisment.AdverResource;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2016-12-9
 * Time: 10:16:12
 *
 */
@Repository("adverResourceDao")
public class AdverResourceDaoImpl extends HibernateEntityObjectDao  implements AdverResourceDao{

	@Override
	public boolean obtainNameExist(String arUuid, String name) {
		boolean result;
		if(!StringUtils.hasText(arUuid)){
			Iterator<?> its= getHibernateTemplate().find("select count(model.uuid) from AdverResource as model where model.name = '"+name+"'").iterator();
			Object obj = its.next();
			result = ((Long)obj) > 0 ? true : false;
		}else{
			Object obj = getHibernateTemplate()
					.find("select count(model.uuid) from AdverResource as model where model.name = ? and model.uuid <> ?", 
							new Object[]{name, arUuid}).get(0);
			result = ((Long)obj) > 0 ? true : false;
		}
		return result;
	}

	@Override
	public List<AdverResource> loadAdverResources(String name,
			int startPosition, int pageSize) {
		
		StringBuilder sb = new StringBuilder();
		Map<String,Object> parameters = new HashMap<String, Object>();
		sb.append("from AdverResource as model where 1=1 ");
		if(StringUtils.hasText(name)){
			sb.append(" and model.name like :name ");
			parameters.put("name", name+"%");
		}
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Query query = session.createQuery(sb.toString());
		
		for(Entry<String,Object> entry: parameters.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		
		return query.list();
	}

	@Override
	public int loadAdverResourceSize(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(model.uuid) from AdverResource as model where 1=1 ");
		if(StringUtils.hasText(name)){
			sb.append(" and model.name like '"+name+"%'");
		}
		Object result = getHibernateTemplate().find(sb.toString()).get(0);
		return ((Long)result).intValue();
	}

	@Override
	public List<AdverResource> loadEnableAdverResources(String name,
			int startPosition, int pageSize) {
		StringBuilder sb = new StringBuilder();
		Map<String,Object> parameters = new HashMap<String, Object>();
		sb.append("from AdverResource as model where model.status= :status");
		parameters.put("status", true);
		if(StringUtils.hasText(name)){
			sb.append(" and model.name like :name ");
			parameters.put("name", name+"%");
		}
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Query query = session.createQuery(sb.toString());
		
		for(Entry<String,Object> entry: parameters.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		
		return query.list();
	}

	@Override
	public int loadEnableAdverResourceSize(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(model.uuid) from AdverResource as model where model.status=? ");
		if(StringUtils.hasText(name)){
			sb.append(" and model.name like '"+name+"%'");
		}
		Object result = getHibernateTemplate().find(sb.toString(), true).get(0);
		return ((Long)result).intValue();
	}

	@Override
	public List loadRecordResourceInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1) as amount, ifnull(round(sum(size),2),0.00) as size from ad_resource union all ");
		sb.append("select count(1) as amount , ifnull(round(sum(size),2),0.00) as size from ad_resource where status=0 union all ");
		sb.append("select count(1) as amount , ifnull(round(sum(size),2),0.00)  as size from ad_resource where status=1 ");
		final String sqlQurey = sb.toString();
		return getHibernateTemplate().executeFind(new HibernateCallback<List>() {
			@Override
			public List doInHibernate(Session arg0) throws HibernateException,
					SQLException {
					return arg0.createSQLQuery(sqlQurey)
							.addScalar("amount",Hibernate.LONG	) 
							.addScalar("size",Hibernate.DOUBLE)
							.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP) 
							.list();
			}
			
		});
	}

	@Override
	public List<String> loadFileNamesByPath(String keyword) {
		String queryStr = "select path from AdverResource where path like '"+keyword+"%'";
		return getHibernateTemplate().find(queryStr);
	}

	@Override
	public double loadRecordSize(List<String> garbageRecordes) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select sum(model.size) as size from AdverResource as model where model.path in (:pathList)");

		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Query query = session.createQuery(sb.toString());
		query.setParameterList("pathList", garbageRecordes);
		return (double) query.uniqueResult();
	}

	@Override
	public int deleteByRecords(List<String> garbageRecordes) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from AdverResource as model where model.status = true and model.path in (:garbageRecordes)");

		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Query query = session.createQuery(sb.toString());
		query.setParameterList("pathList", garbageRecordes);
		return query.executeUpdate();
		
	}


}
