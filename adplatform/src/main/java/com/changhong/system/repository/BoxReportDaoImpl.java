package com.changhong.system.repository;

import java.lang.reflect.Field;
import java.util.Date;
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
import com.changhong.system.domain.box.BoxReport;
import com.changhong.system.domain.box.BoxReportStatus;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-22 
 * Time: 15:11:41
 *
 */
@Repository("boxReportDao")
public class BoxReportDaoImpl extends HibernateEntityObjectDao implements BoxReportDao{

	@Override
	public List<BoxReport> findReports(String keyword, int startPosition,
			int pageSize) {
		return findReports(keyword, null, startPosition, pageSize);
	}

	@Override
	public int findReportsAmount(String keyword) {
		return findReportsAmount(keyword,null);
	}

	@Override
	public List<BoxReport> findReports(String keyword, BoxReportStatus status,
			int startPosition, int pageSize) {
		return findReports(keyword, status, "uuid", startPosition, pageSize);
	}

	@Override
	public int findReportsAmount(String keyword, BoxReportStatus status) {
		StringBuilder sb = new StringBuilder();
		Map<String,Object> values = new HashMap<String, Object>();
		
		sb.append("select count(model.uuid) from BoxReport as model where 1=1 ");
		if(StringUtils.hasText(keyword)){
			sb.append(" and model.mac like :keyword ");
			values.put("keyword", keyword+"%");
		}
		
		if(status!=null){
			sb.append(" and model.status = :status ");
			values.put("status", status);
		}
		
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		for(Entry<String,Object> entry: values.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		return ((Long)query.uniqueResult()).intValue();
	}

	@Override
	public List<BoxReport> findReports(String keyword, BoxReportStatus status,
			String orderByFiledName, int startPosition, int pageSize) {
		StringBuilder sb = new StringBuilder();
		Map<String,Object> values = new HashMap<String, Object>();
		
		sb.append("from BoxReport as model where 1=1 ");
		if(StringUtils.hasText(keyword)){
			sb.append(" and model.mac like :keyword ");
			values.put("keyword", keyword+"%");
		}
		
		if(status!=null){
			sb.append(" and model.status = :status ");
			values.put("status", status);
		}
		
		if(StringUtils.hasText(orderByFiledName) ){
			try{
				Field filed = BoxMac.class.getDeclaredField(orderByFiledName);
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

	@Override
	public List<BoxReport> findReports(Date startDate, Date endDate,
			int startPosition, int pageSize) {
		
		StringBuilder sb = new StringBuilder();
		Map<String,Object> values = new HashMap<String, Object>();
		
		sb.append("from BoxReport as model where 1=1 ");
		
		if(startDate != null){
			sb.append(" and model.timestamp >= :startDate ");
			values.put("startDate", startDate);
		}
		
		if(endDate !=null){
			sb.append(" and model.timestamp <= :endDate ");
			values.put("endDate", endDate);
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
	public int findReportsAmount(Date startDate, Date endDate) {
		StringBuilder sb = new StringBuilder();
		Map<String,Object> values = new HashMap<String, Object>();
		
		sb.append("select count(model.uuid) from BoxReport as model where 1=1 ");
		
		if(startDate != null){
			sb.append(" and model.timestamp >= :startDate ");
			values.put("startDate", startDate);
		}
		
		if(endDate !=null){
			sb.append(" and model.timestamp <= :endDate ");
			values.put("endDate", endDate);
		}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		for(Entry<String,Object> entry: values.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		return ((Long)query.uniqueResult()).intValue();
	}

}
