package com.changhong.system.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.app.AppStrategy;

/**
 * @author yujiawei
 * @date 2017年2月10日
 */
@Repository("appStrategyDao")
public class AppStrategyDaoImpl extends HibernateEntityObjectDao implements AppStrategyDao{

	@Override
	public AppStrategy findAppStrategyByName(String name) {
		return null;
	}

	@Override
	public List<AppStrategy> loadAppStrategy(String strategyName,
			int startPosition, int pageSize) {
		StringBuilder builder = new StringBuilder();
		builder.append("from AppStrategy appStrategy");
		if(StringUtils.hasText(strategyName)){
			builder.append(" where appStrategy.name like '" + strategyName + "%'or appStrategy.name like '" + strategyName + "%'");
		}
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);
        List<AppStrategy> strategys = query.list(); 
		return strategys;
	}

	@Override
	public int loadAppSize(String strategyName) {
		StringBuilder builder = new StringBuilder();
		builder.append("select count(appStrategy.uuid) from AppStrategy appStrategy");
		if(StringUtils.hasText(strategyName)){
			builder.append(" where appStrategy.name like '" + strategyName + "%' or appStrategy.name like '" + strategyName + "%'");
		}
		List list =  getHibernateTemplate().find(builder.toString());
        return ((Long)list.get(0)).intValue();
	}

	@Override
	public boolean loadAppExist(String strategyUuid, String strategyName) {
		return false;
	}

	@Override
	public List<AppStrategy> loadAll() {
		String hql = "from AppStrategy where timestampDistribute!=null and strategyEnabled=1 and strategyDistributeEnabled=1 order by timestampDistribute desc";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return query.list();
	}

}
