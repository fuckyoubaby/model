package com.changhong.system.repository;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.uploaddata.UploadADData;

/**
 * 
 * @author shijinxiang
 *
 */
@Repository("uploadADDataDao")
public class UploadADDataDaoImpl extends HibernateEntityObjectDao implements UploadADDataDao{

	@Override
	public List<UploadADData> loadAdData(String areaId, int startPosition,
			int pageSize) {
		String hql = "from UploadADData where areaId=?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, areaId);
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		return query.list();
	}

	@Override
	public List<Box> loadBoxsByAreaId(String areaId, int offset, int length) {
		String hql = "from Box box where box.community.area.uuid=?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, areaId);
		query.setFirstResult(offset);
		query.setMaxResults(length);
		return query.list();
	}

	@Override
	public int getCountByAreaId(String areaId) {
		String hql = "from Box box where box.community.area.uuid=?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, areaId);
		return query.list().size();
	}

	@Override
	public List<UploadADData> loadAdDatas(String mac, Date startDate,
			Date endDate) {
		String hql = "from UploadADData where macNumber=? and uploadDate>=? and uploadDate<=? order by adverResource.uuid";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, mac);
		query.setParameter(1, startDate);
		query.setParameter(2, endDate);
		return query.list();
	}

	
}
