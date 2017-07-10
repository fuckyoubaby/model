package com.hylanda.base.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {
	//根据ID加载实体
		public T get(Class<T> entityClass,Serializable id);
		//保存实体
		public void save(T entity);
		
		/**
		 * 批量存储
		 * @param lists
		 * @return
		 */
		public int save(List<T> lists);
		
		//更新实体
		void update(T entity);
		
		//删除实体
		void delete(T entity);
		
		//根据ID删除实体
		void delete(Class<T> entityClass,Serializable id);
		
		//获取所有实体
		List<T> findAll(Class<T> entityClass);
		
		//分页获取所有实体
		List<T> findByPage(Class<T> entityClass,final int offset,final int length);
		
		//获取所有实体的数目
		int getAllCount(Class<T> entityClass);

}
