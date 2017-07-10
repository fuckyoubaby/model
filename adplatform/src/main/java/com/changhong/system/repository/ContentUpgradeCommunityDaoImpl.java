package com.changhong.system.repository;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.upgrade.ContentUpgradeCommunity;
import com.changhong.system.domain.upgrade.UpgradeEntity;

/**
 * Author: Guo xiaoyang
 * Date: 2017-2-6 
 * Time: 11:36:36
 */
@Repository("contentUpgradeCommunityDao")
public class ContentUpgradeCommunityDaoImpl extends HibernateEntityObjectDao implements ContentUpgradeCommunityDao{

	@Override
	public List<ContentUpgradeCommunity> loadUpgradeCommunities(String upgradeId) {
		return getHibernateTemplate().find("from ContentUpgradeCommunity as model where model.contentUpgrade.uuid=? ", upgradeId);
	}

	@Override
	public boolean checkExists(String communityId, String upgradeId) {
		Object obj = getHibernateTemplate().find("selecte count(model.uuid) from ContentUpgradeCommunity as model where model.communityid=? and model.contentUpgrade.uuid =?  ", 
				new Object[]{communityId, upgradeId}).get(0);
		return ((Long)obj).intValue()>0 ? true : false;
	}

	@Override
	public void batchSaveUpgradeCommunities(
			List<ContentUpgradeCommunity> communities) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		for(int i=1;i<communities.size();i++){
			session.merge(communities.get(i-1));
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
		sb.append("delete from  ContentUpgradeCommunity as model where model.contentUpgrade.uuid='"+upgradeId+"' ");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		query.executeUpdate();
	}

	@Override
	public List<ContentUpgradeCommunity> loadUpgradeCommunitiesByCommunityId(
			String communityId) {
		return getHibernateTemplate().find("from ContentUpgradeCommunity as model where model.communityid=? order by uuid", communityId);
	}

	@Override
	public void deleteEntitiesByCommunityId(String communityId) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from  ContentUpgradeCommunity as model where model.communityid='"+communityId+"' ");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		query.executeUpdate();
		
	}

	@Override
	public UpgradeEntity loadLatestUpgradeByCommunityId(String communityId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select cu1.uuid as uuid, cu1.publishtime as publishtime from content_upgrade as cu1, content_upgrade_community as cuc where cuc.upgrade_id = cu1.uuid and cu1.publishtime is not NULL and cu1.`enable`=1 and cuc.community_id=:communityId order by publishtime desc limit 1 ");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql.toString()).addScalar("uuid", Hibernate.STRING).addScalar("publishtime", Hibernate.DATE);
		query.setParameter("communityId", communityId);
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
