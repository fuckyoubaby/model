package com.changhong.system.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.box.Box;
/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-20 
 * Time: 14:24:44
 */
@Repository("boxUpdateDao")
public class BoxUpdateDaoImpl extends HibernateEntityObjectDao implements BoxUpdateDao{

	@Override
	public List<Box> findBoxByCommunityAndPage(String communityUuid,
			int startPosition, int size) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder builder = new StringBuilder();
		builder.append("from Box as model ");
		if(StringUtils.hasText(communityUuid)){
			builder.append(" where model.community.uuid=:communityUuid ");
			parameters.put("communityUuid", communityUuid);
		}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(builder.toString());
		for(Entry<String, Object> entry: parameters.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}

		query.setFirstResult(startPosition);
		query.setMaxResults(size);
		return query.list();
	}

	@Override
	public List<Box> findBoxByMacPage(String mac, int startPosition, int size) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder builder = new StringBuilder();
		builder.append("from Box as model ");
		if(StringUtils.hasText(mac)){
			builder.append(" where model.mac like :mac ");
			parameters.put("mac", mac+"%");
		}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(builder.toString());
		for(Entry<String, Object> entry: parameters.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}

		query.setFirstResult(startPosition);
		query.setMaxResults(size);
		return query.list();
	}

	@Override
	public int findBoxAmountByCommunity(String community) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder builder = new StringBuilder();
		builder.append("select count(model.uuid) from Box as model ");
		if(StringUtils.hasText(community)){
			builder.append(" where model.community.uuid=:communityUuid ");
			parameters.put("communityUuid", community);
		}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(builder.toString());
		for(Entry<String, Object> entry: parameters.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return ((Long)query.uniqueResult()).intValue();
	}

	@Override
	public int loadBoxAmountByMac(String mac) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder builder = new StringBuilder();
		builder.append("select count(model.uuid) from Box as model ");
		if(StringUtils.hasText(mac)){
			builder.append(" where model.mac like :mac ");
			parameters.put("mac", mac+"%");
		}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(builder.toString());
		for(Entry<String, Object> entry: parameters.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return ((Long)query.uniqueResult()).intValue();
	}
	
	
	

}
