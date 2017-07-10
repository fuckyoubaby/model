package com.changhong.system.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.common.utils.CHListUtils;
import com.changhong.system.domain.app.AppManage;
import com.changhong.system.domain.app.AppPhoneManage;

@Repository("appPhoneManageDao")
public class AppPhoneManageDaoImpl extends HibernateEntityObjectDao implements
		AppPhoneManageDao {

	@Override
	public AppPhoneManage findAppByName(String AppName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppPhoneManage> loadApps(String AppName, int startPosition,
			int pageSize) {
		StringBuilder builder = new StringBuilder();
		builder.append("from AppPhoneManage app");
        if (StringUtils.hasText(AppName)) {
            builder.append(" where app.appName like '" + AppName + "%' or app.appName like '" + AppName + "%'");
        }
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);
        List<AppPhoneManage> apps = query.list();
        return apps;
	}

	@Override
	public int loadAppSize(String AppName) {
		StringBuilder builder = new StringBuilder();
        builder.append("select count(app.uuid) from AppPhoneManage app");
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
	public AppPhoneManage loadAppPhoneManageByAppVersion(String appVersion) {
		String hql = "from AppPhoneManage where appVersion=?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, appVersion);
		return (AppPhoneManage) (CHListUtils.hasElement(query.list()) ? query.list().get(0) : null);
	}

	@Override
	public boolean obtainAppVersionIsExist(String appVersion) {
		String hql = "from AppPhoneManage app where app.appVersion=?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter(0, appVersion);
        if(query.list().size()>0){
        	return true;
        }else {
        	return false;
		}
	}

	@Override
	public AppPhoneManage loadLastAppPhoneManage() {
		String hql="from AppPhoneManage";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        List<AppPhoneManage> list = query.list();
		return list.get(list.size()-1);
	}

}
