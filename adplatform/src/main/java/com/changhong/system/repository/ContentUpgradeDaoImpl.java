package com.changhong.system.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.changhong.common.domain.EntityBase;
import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.upgrade.ContentUpgrade;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-2-2 
 * Time: 10:48:02
 *
 */
@Repository("contentUpgradeDao")
public class ContentUpgradeDaoImpl extends HibernateEntityObjectDao implements ContentUpgradeDao {

	@Override
	public boolean obtainNameExists(String name) {
		Object obj = getHibernateTemplate().find(" select count(model.uuid) from ContentUpgrade as model where model.name =? ", name).iterator().next();
		
		return ((Long)obj)>0? true : false;
	}

	@Override
	public boolean obtainNameExists(String name, String uuid) {
		Object obj = getHibernateTemplate().find(" select count(model.uuid) from ContentUpgrade as model where model.name =? and model.uuid <> ?", 
				new Object[]{name,uuid}).iterator().next();
		return ((Long)obj)>0? true : false;
	}

	@Override
	public List<ContentUpgrade> loadContentUpgrades(String keyword,
			int startPosition, int size) {
		StringBuilder sb = new StringBuilder();
		List<Object> parameters  = new ArrayList<Object>();
		sb.append(" from ContentUpgrade as model ");
		if(StringUtils.hasText(keyword)){
			sb.append(" where model.name like ? ");
			parameters.add( keyword+"%");
			}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		for(int i=0;i<parameters.size();i++){
			query.setParameter(i, parameters.get(i));
		}
		query.setFirstResult(startPosition);
		query.setMaxResults(size);
		
		return query.list();
	}

	@Override
	public int loadAmount(String keyword) {
		StringBuilder sb = new StringBuilder();
		List<Object> parameters  = new ArrayList<Object>();
		sb.append(" select count(model.uuid) from ContentUpgrade as model ");
		if(StringUtils.hasText(keyword)){
			sb.append(" where model.name like ? ");
			parameters.add( keyword+"%");
			}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		for(int i=0;i<parameters.size();i++){
			query.setParameter(i, parameters.get(i));
		}
		
		return ((Long)query.uniqueResult()).intValue();
	}

	@Override
	public List<ContentUpgrade> loadAllContentUpgrades() {

		String hql = "from ContentUpgrade where enable=1 and publishTime!=null order by publishTime desc";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return query.list();
	}


}
