package com.changhong.system.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.advertisment.PlayItemContent;
/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-11 
 * Time: 18:16:10
 *
 */
@Repository("playItemContentDao")
public class PlayItemContentDaoImpl extends HibernateEntityObjectDao implements
		PlayItemContentDao {

	@Override
	public List<PlayItemContent> loadByItemid(String itemUuid) {
		return getHibernateTemplate().find("from PlayItemContent as model where model.playItem.uuid = ? order by model.index ", itemUuid);
	}

	@Override
	public void changeIndexByItemUuid(String itemUuid, int index) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql = "update PlayItemContent as model set model.index = model.index-1 where model.playItem.uuid =:uuid and model.index > :index  ";
		Query query = session.createQuery(hql);
		
		query.setParameter("uuid", itemUuid);
		query.setParameter("index", index);
		
		query.executeUpdate();
	}

	@Override
	public void changeIndexByUuid(String uuid, int index) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql = "update PlayItemContent as model set model.index = :index where model.uuid =:uuid ";
		Query query = session.createQuery(hql);
		
		query.setParameter("uuid", uuid);
		query.setParameter("index", index);
		
		query.executeUpdate();
		
	}

	@Override
	public List<PlayItemContent> loadPlayItemByContentResourceId(
			String contentResourceId) {
		String hql = "from PlayItemContent where resourceUuid=?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, contentResourceId);
		return query.list();
	}
	
}
