package com.hylanda.base.dao.impl;
import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hylanda.base.dao.BaseDao;



@Transactional
public class BaseDaoImpl<T> implements BaseDao<T>{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public Session getSession()
	{
		return sessionFactory.getCurrentSession();
	}
	public T get(Class<T> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return (T) getSession().get(entityClass, id);
	}

	public void save(T entity) {
		//getSession().save(entity);
		getSession().merge(entity);
	}

	public void update(T entity) {
		getSession().merge(entity);
	}

	public void delete(T entity) {
		Session session = getSession();
		session.clear();
		session.delete(entity);
		
		
	}

	public void delete(Class<T> entityClass, Serializable id) {
		delete(get(entityClass, id));
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> entityClass) {
		// TODO Auto-generated method stub
		//return find("select * from " +entityClass.getSimpleName() );
	/*	System.out.println(" getSimpleName = "+entityClass.getSimpleName());
		System.out.println(" getName = "+entityClass.getName());*/
		return getSession().createQuery("from "+entityClass.getSimpleName()).list();
	}
	@SuppressWarnings("unchecked")
	protected List<T> find(String hql){
		return getSession().createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> find(String hql, Object... params){
		Query query = getSession().createQuery(hql);
		
		for(int i=0, len = params.length ; i<len; i++){
			query.setParameter(i, params[i]);
		}
		
		return query.list();
	}
	@SuppressWarnings("unchecked")
	public List<T> findByPage(Class<T> entityClass, int offset, int length) {
		Query query = getSession().createQuery("from "+entityClass.getSimpleName());
		query.setFirstResult(offset);
		query.setMaxResults(length);
		return query.list();
	}
	public int getAllCount(Class<T> entityClass) {
		String hql = "Select Count(*) from " + entityClass.getSimpleName() + " as count";
		Query query = getSession().createQuery(hql);
		
		return ((Long)query.uniqueResult()).intValue();
	}
	public int save(List<T> lists) {
		int size = lists.size();
		Session session = getSession();
		for(int i=0;i<size;i++){
			//session.save(lists.get(i));
			session.merge(lists.get(i));
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
		session.flush();
		session.clear();
		return size;
	}

}
