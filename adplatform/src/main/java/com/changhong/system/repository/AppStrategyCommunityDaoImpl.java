package com.changhong.system.repository;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.app.AppStrategyBox;
import com.changhong.system.domain.app.AppStrategyCommunity;
import com.changhong.system.domain.app.AppUpgradeEntity;

/**
 * @author yujiawei
 * @date 2017年2月10日
 */
@Repository("appStrategyCommunityDao")
public class AppStrategyCommunityDaoImpl extends HibernateEntityObjectDao
		implements AppStrategyCommunityDao {

	@Override
	public List<AppStrategyCommunity> getCommunityList(String strategyUuid) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("from AppStrategyCommunity appStrategyCommunity");
		if(StringUtils.hasText(strategyUuid)){
			builder.append(" where appStrategyCommunity.strategyUuid = ?");
		}
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(builder.toString());
        query.setParameter(0, strategyUuid);
		return query.list();
	}

	@Override
	public void deleteCommunity(String strategyUuid) {
		String hql = "from AppStrategyCommunity appStrategyCommunity where appStrategyCommunity.strategyUuid = ?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();	
		Query query = session.createQuery(hql);
		if(StringUtils.hasText(strategyUuid)){
			query.setParameter(0, strategyUuid);
		}
		List<AppStrategyCommunity> lists = query.list();
		for(int i=0;i<lists.size();i++){
			session.delete(lists.get(i));
		}
	}

	@Override
	public List<AppStrategyCommunity> getCommunitiesByCommunityId(
			String communityId) {
		String hql = "from AppStrategyCommunity where communityUuid=?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, communityId);
		return query.list();
	}

	@Override
	public AppUpgradeEntity loadLatestStratageByCommunityId(String communityId) {
		StringBuilder sql = new StringBuilder();
		sql.append("  select cu.uuid as uuid, cu.timestamp_distribute as publishtime , cu.app_version as app_version from app_strategy as cu, app_strategy_community as appsc where appsc.strategy_uuid = cu.uuid and cu.timestamp_distribute is not NULL and cu.app_strategy_enabled=1 and appsc.community_uuid=:communityId  order by publishtime desc limit 1 ");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql.toString()).addScalar("uuid", Hibernate.STRING).addScalar("publishtime", Hibernate.DATE).addScalar("app_version", Hibernate.STRING);
		query.setParameter("communityId", communityId);
		List<Object[]> lists= query.list();
		if(lists!=null && lists.size()>0){
			Object[] objs = lists.get(0);
			if(objs[0] != null){
				AppUpgradeEntity aue = new AppUpgradeEntity();
				aue.setUuid(objs[0].toString());
				aue.setPublishTime((Date)objs[1]);
				aue.setApkVersion(objs[2].toString());
				return aue;
			} 
		}
		return null;
	}

}
