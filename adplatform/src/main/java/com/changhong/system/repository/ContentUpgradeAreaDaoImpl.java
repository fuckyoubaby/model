package com.changhong.system.repository;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.upgrade.ContentUpgradeArea;
import com.changhong.system.domain.upgrade.UpgradeEntity;

/**
 * Author: Guo xiaoyang
 * Date: 2017-2-6 
 * Time: 11:36:36
 */
@Repository("contentUpgradeAreaDao")
public class ContentUpgradeAreaDaoImpl extends HibernateEntityObjectDao implements ContentUpgradeAreaDao{

	@Override
	public List<ContentUpgradeArea> loadUpgradeAreas(String upgradeId) {
		return getHibernateTemplate().find("from ContentUpgradeArea as model where model.contentUpgrade.uuid=? ", upgradeId);
	}

	@Override
	public boolean checkExists(String areaid, String upgradeId) {
		Object obj = getHibernateTemplate().find("selecte count(model.uuid) from ContentUpgradeArea as model where model.areaid=? and model.contentUpgrade.uuid =?  ", 
				new Object[]{areaid, upgradeId}).get(0);
		return ((Long)obj).intValue()>0 ? true : false;
	}

	@Override
	public void batchSaveUpgradeAreas(List<ContentUpgradeArea> areas) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		for(int i=1;i<areas.size();i++){
			session.merge(areas.get(i-1));
			if(i%20==0){
				session.flush();
				session.clear();
			}
		}
		session.flush();
		session.clear();
	}

	@Override
	public void deleteEntitiesByUpgradeId(String upgradeId) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from  ContentUpgradeArea as model where model.contentUpgrade.uuid='"+upgradeId+"' ");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		query.executeUpdate();
	}

	@Override
	public List<ContentUpgradeArea> loadUpgradeAreasByAreaId(String areaId) {
		return getHibernateTemplate().find("from ContentUpgradeArea as model where model.areaid=? order by uuid", areaId);
	}

	@Override
	public void deleteEntitiesByAreaId(String areaId) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from  ContentUpgradeArea as model where model.areaid='"+areaId+"' ");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		query.executeUpdate();
		
	}

	@Override
	public UpgradeEntity loadLatestUpgradeEntityByAreaId(String areaId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select cu.uuid as uuid, cu.publishtime as publishtime from content_upgrade as cu, content_upgrade_area as cua where cua.upgrade_id = cu.uuid and cu.publishtime is not NULL and cu.`enable`=1 and cua.area_id=:areaId order by publishtime desc limit 1 ");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql.toString()).addScalar("uuid", Hibernate.STRING).addScalar("publishtime", Hibernate.DATE);
		query.setParameter("areaId", areaId);
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
