package com.changhong.system.repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.advertisment.PlayContent;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-11 
 * Time: 18:15:10
 *
 */
@Repository("playContentDao")
public class PlayContentDaoImpl extends HibernateEntityObjectDao implements
		PlayContentDao {

	@Override
	public boolean obtainNameExists(String name) {
		Object obj = getHibernateTemplate().find(" select count(model.uuid) from PlayContent as model where model.name=? ", name).iterator().next();
		return ((Long)obj)>0?true : false;
	}

	@Override
	public boolean obtainNameExists(String pcUuid, String name) {
		Object obj = getHibernateTemplate()
				.find("select count(model.uuid) from PlayContent as model where model.name = ? and model.uuid <> ?", 
						new Object[]{name, pcUuid}).get(0);
		return ((Long)obj) > 0 ? true : false;
	}

	@Override
	public List<PlayContent> loadPlayContents(String keyword,
			int startPosition, int size) {
		
		StringBuilder sb = new StringBuilder();
		Map<String, Object> parameters = new HashMap<String, Object>();
		sb.append("from PlayContent as model where 1=1 ");
		if(StringUtils.hasText(keyword)){
			sb.append(" and model.name like :name");
			parameters.put("name", keyword+"%");
		}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		
		for(Entry<String, Object> entry : parameters.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		query.setFirstResult(startPosition);
		query.setMaxResults(size);
		return query.list();
	}

	@Override
	public int loadAmount(String keyword) {
		String queryStr="select count(model.uuid) from PlayContent as model ";
		if(StringUtils.hasText(keyword)){
			queryStr += " where model.name like '"+keyword+"%'";
		}
		Iterator<?> it = getHibernateTemplate().find(queryStr).iterator();
		Object obj = it.next();
		
		return ((Long)obj).intValue();
	}

	@Override
	public PlayContent loadByUpgradeId(String uuid) {
		List obj = getHibernateTemplate()
				.find("from PlayContent as model where model.contentUpgrade.uuid = ? ", 
						new Object[]{uuid});
		if(obj.size()>0)
			return (PlayContent) obj.get(0);
		return null;
	}

	@Override
	public List<PlayContent> loadEnablePlayContents(String keyword,
			int startPosition, int size) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> parameters = new HashMap<String, Object>();
		sb.append("from PlayContent as model where model.contentUpgrade is null ");
		if(StringUtils.hasText(keyword)){
			sb.append(" and model.name like :name ");
			parameters.put("name", keyword+"%");
		}
		sb.append(" order by model.timestamp desc");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		
		for(Entry<String, Object> entry : parameters.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		query.setFirstResult(startPosition);
		query.setMaxResults(size);
		return query.list();
	}

	@Override
	public int loadEnableAmount(String keyword) {
		String queryStr="select count(model.uuid) from PlayContent as model where model.contentUpgrade is null ";
		if(StringUtils.hasText(keyword)){
			queryStr += " and model.name like '"+keyword+"%'";
		}
		Iterator<?> it = getHibernateTemplate().find(queryStr).iterator();
		Object obj = it.next();
		
		return ((Long)obj).intValue();
	}

	@Override
	public void updateUpgrade(String playContentUuid) {
		StringBuilder sb = new StringBuilder();
		sb.append("update PlayContent as model set model.contentUpgrade = null where model.uuid = '"+playContentUuid+"' ");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		
		query.executeUpdate();
	}

	@Override
	public List<PlayContent> loadPlayContentsByContentResourceId(
			String contentResourceId) {
		String hql = "from PlayContent where contentUpgrade.uuid=?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, contentResourceId);
		return query.list();
	}

}
