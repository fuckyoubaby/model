package com.changhong.system.repository;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.box.BoxMac;
import com.changhong.system.domain.box.BoxMacStatus;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-15 
 * Time: 14:48:04
 *
 */
@Repository("boxMacDao")
public class BoxMacDaoImpl extends HibernateEntityObjectDao implements BoxMacDao{

	@Override
	public boolean findBoxMacDuplicate(String mac) {
		String hql ="select count(model.uuid) from BoxMac as model where model.mac =? ";
		Object result = getHibernateTemplate().find(hql,mac).iterator().next();
		return ((Long)result)>0?true : false;
	}
	
	@Override
	public boolean findBoxMacDuplicate(String mac, String uuid) {
		
		if(!StringUtils.hasText(uuid)){
			String hql ="select count(model.uuid) from BoxMac as model where model.mac =? ";
			Object result = getHibernateTemplate().find(hql,mac).iterator().next();
			return ((Long)result)>0?true : false;
		}else{
			String hql ="select count(model.uuid) from BoxMac as model where model.mac =? and model.uuid <> ?";
			Object result = getHibernateTemplate().find(hql, new Object[]{mac,uuid}).iterator().next();
			return ((Long)result)>0?true : false;
		}
		
	}

	@Override
	public List<BoxMac> findMacs(String keyword, int startPosition, int pageSize) {
		return findMacs(keyword, null, startPosition, pageSize);
	}

	@Override
	public int findMacsAmount(String keyword) {
		return findMacsAmount(keyword, null);
	}

	@Override
	public List<BoxMac> findMacs(String keyword, String status,
			int startPosition, int pageSize) {
		StringBuilder sb = new StringBuilder();
		Map<String,Object> values = new HashMap<String, Object>();
		
		sb.append("from BoxMac as model where 1=1 ");
		if(StringUtils.hasText(keyword)){
			sb.append(" and model.mac like :keyword ");
			values.put("keyword", keyword+"%");
		}
		
		if(StringUtils.hasText(status)){
			try{
				BoxMacStatus b = Enum.valueOf(BoxMacStatus.class, status);
				sb.append(" and model.macStatus = :status ");
				values.put("status", b);
			}catch(Exception e){
				
			}
		}
		
		sb.append(" order by model.timestamp desc "); 
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		for(Entry<String,Object> entry: values.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		return query.list();
		
	}

	@Override
	public int findMacsAmount(String keyword, String status) {
		StringBuilder sb = new StringBuilder();
		Map<String,Object> values = new HashMap<String, Object>();
		
		sb.append("select count(model.uuid) from BoxMac as model where 1=1 ");
		if(StringUtils.hasText(keyword)){
			sb.append(" and model.mac like :keyword ");
			values.put("keyword", keyword+"%");
		}
		
		if(StringUtils.hasText(status)){
			BoxMacStatus b;
			try{
				b = Enum.valueOf(BoxMacStatus.class, status);
				sb.append(" and model.macStatus = :status ");
				values.put("status", b);
			}catch(Exception e){
				
			}
		}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		for(Entry<String,Object> entry: values.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		Object result = query.uniqueResult();
		return ((Long)result).intValue();
	}

	@Override
	public BoxMac findByMac(String mac) {
		List result =	getHibernateTemplate().find("from BoxMac as model where model.mac = '"+mac+"'");
		if(result!=null && result.size()>0){
			return (BoxMac) result.get(0);
		}
		return null;
	}

	@Override
	public int deleteDuplicateMacs() {
		String sql = "delete a.* from box_mac a, (select min(uuid) as uuid ,mac from box_mac group by mac) b where a.mac = b.mac and a.uuid!=b.uuid";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		return query.executeUpdate();
	}

	@Override
	public List<BoxMac> findMacs(String keyword, String status,
			int startPosition, int pageSize, String propertyName) {
		StringBuilder sb = new StringBuilder();
		Map<String,Object> values = new HashMap<String, Object>();
		
		sb.append("from BoxMac as model where 1=1 ");
		if(StringUtils.hasText(keyword)){
			sb.append(" and model.mac like :keyword ");
			values.put("keyword", keyword+"%");
		}
		
		if(StringUtils.hasText(status)){
			try{
				BoxMacStatus b = Enum.valueOf(BoxMacStatus.class, status);
				sb.append(" and model.macStatus = :status ");
				values.put("status", b);
			}catch(Exception e){
				
			}
		}
		
		if(StringUtils.hasText(propertyName) ){
			try{
				Field filed = BoxMac.class.getDeclaredField(propertyName);
				sb.append(" order by model."+filed.getName());
			}catch(Exception e){
				
			}
		}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		for(Entry<String,Object> entry: values.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		return query.list();
	}

}
