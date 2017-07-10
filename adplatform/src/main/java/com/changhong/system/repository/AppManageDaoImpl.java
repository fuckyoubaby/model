package com.changhong.system.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.data.redis.core.query.QueryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.common.utils.CHListUtils;
import com.changhong.system.domain.app.AppManage;
import com.changhong.system.domain.box.Box;

/**
 * @author yujiawei
 * @date 2017年2月10日
 * @time 上午11:25:45
 */
@Repository("appManageDao")
public class AppManageDaoImpl extends HibernateEntityObjectDao implements AppManageDao{

	@Override
	public AppManage findAppByName(String AppName) {         //？？
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppManage> loadApps(String AppName, int startPosition,int pageSize) {
		StringBuilder builder = new StringBuilder();
		builder.append("from AppManage app");
        if (StringUtils.hasText(AppName)) {
            builder.append(" where app.appName like '" + AppName + "%' or app.appName like '" + AppName + "%'");
        }
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);
        List<AppManage> apps = query.list();
        return apps;
	}

	@Override
	public int loadAppSize(String AppName) {
		StringBuilder builder = new StringBuilder();
        builder.append("select count(app.uuid) from AppManage app");
        if (StringUtils.hasText(AppName)) {
            builder.append(" where app.appName like '" + AppName + "%' or app.appName like '" + AppName + "%'");
        }
        List list =  getHibernateTemplate().find(builder.toString());
        return ((Long)list.get(0)).intValue();
	}

	@Override
	public boolean loadAppExist(String appUuid, String appName) {
		return false;
	}


	@Override
	public AppManage loadAppManageByAppVersion(String appVersion) {
		String hql = "from AppManage where appVersion=?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, appVersion);
		return (AppManage) (CHListUtils.hasElement(query.list()) ? query.list().get(0) : null);
	}


	@Override
	public boolean obtainAppVersionIsExist(String appVersion) {
		String hql = "from AppManage app where app.appVersion=?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter(0, appVersion);
        if(query.list().size()>0){
        	return true;
        }else {
        	return false;
		}
	}

}
