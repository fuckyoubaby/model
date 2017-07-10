package com.changhong.system.repository;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.upgrade.ContentUpgradeBox;
import com.changhong.system.domain.upgrade.UpgradeEntity;

/**
 * Author: Guo xiaoyang
 * Date: 2017-2-6 
 * Time: 11:36:36
 */
@Repository("contentUpgradeBoxDao")
public class ContentUpgradeBoxDaoImpl extends HibernateEntityObjectDao implements ContentUpgradeBoxDao{

	@Override
	public List<ContentUpgradeBox> loadUpgradeBoxes(String upgradeId) {
		return getHibernateTemplate().find("from ContentUpgradeBox as model where model.contentUpgrade.uuid=? ", upgradeId);
	}

	@Override
	public boolean checkExists(String boxId, String upgradeId) {
		
		Object obj = getHibernateTemplate().find("selecte count(model.uuid) from ContentUpgradeBox as model where model.boxid=? and model.contentUpgrade.uuid =?  ", 
				new Object[]{boxId, upgradeId}).get(0);
		return ((Long)obj).intValue()>0 ? true : false;
	}

	@Override
	public void batchSaveUpgradeBoxes(List<ContentUpgradeBox> boxes) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		for(int i=1;i<boxes.size();i++){
			session.merge(boxes.get(i-1));
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
		session.flush();
		session.clear();
	}

	@Override
	public void deleteBoxByUpgradeId(String upgradeId) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from  ContentUpgradeBox as model where model.contentUpgrade.uuid='"+upgradeId+"' ");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		query.executeUpdate();
	}

	@Override
	public void deleteEntities(List<ContentUpgradeBox> boxes) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		for(int i=0;i<boxes.size();i++){
			if(i%20==0){
				session.clear();
			}
			session.delete(boxes.get(i));
		}
	}

	@Override
	public List<ContentUpgradeBox> loadUpgradeBoxesByBoxId(String boxid) {
		return getHibernateTemplate().find("from ContentUpgradeBox as model where model.boxid=? order by uuid", boxid);
	}

	@Override
	public void deleteUpgradeBoxesByBoxId(String boxid) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from  ContentUpgradeBox as model where model.boxid='"+boxid+"' ");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		query.executeUpdate();
		
	}

	@Override
	public UpgradeEntity loadLatestUpgradeByBoxId(String boxId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select cu2.uuid as uuid, cu2.publishtime as publishtime from content_upgrade as cu2, content_upgrade_box as cub where cub.upgrade_id = cu2.uuid and cu2.publishtime is not NULL and cu2.`enable`=1 and cub.box_id=:boxId order by publishtime desc limit 1 ");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql.toString()).addScalar("uuid", Hibernate.STRING).addScalar("publishtime", Hibernate.DATE);
		query.setParameter("boxId", boxId);
		List<Object[]> lists= query.list();
		if(lists!=null && lists.size()>0){
			Object[] objs = lists.get(0);
			if(objs[0] != null){
				UpgradeEntity ue = new UpgradeEntity();
				ue.setUuid(objs[0].toString());
				ue.setPublishTime((Date)objs[1]);
				return ue;
			} 
		}
		return null;
	}

	
}
