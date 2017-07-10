package com.changhong.system.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.advertisment.PlayItem;
/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-11 
 * Time: 18:18:10
 *
 */
@Repository("playItemDao")
public class PlayItemDaoImpl extends HibernateEntityObjectDao implements PlayItemDao{

	@Override
	public boolean obtainNameExists(String piUuid, String name) {
		
		String str = "select count(model.uuid) from PlayItem as model where model.name=?";
		if(StringUtils.hasText(piUuid)){
			str+=" and model.uuid <> ?";
			Object r = getHibernateTemplate().find(str, new Object[]{name, piUuid}).get(0);
			return (Long)r >0? true : false;
		}else{
			Object r = getHibernateTemplate().find(str, name).get(0);
			return (Long)r >0? true : false;
		}
	}

	@Override
	public List<PlayItem> loadEnableByKeyword(String keyword,
			int startPosition, int size) {
		StringBuilder sb = new StringBuilder();
		sb.append("from PlayItem as model where model.playContent is null ");
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(StringUtils.hasText(keyword)){
			sb.append(" and model.name like :name ");
			parameters.put("name", keyword+"%");
		}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		
		for(Entry<String,Object> entry: parameters.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		query.setFirstResult(startPosition);
		query.setMaxResults(size);
		
		return query.list();
	}

	@Override
	public int loadEnableBykeyword(String keyword) {
		
		String hql = "select count(model.uuid) from PlayItem as model where model.playContent is null ";
		if(StringUtils.hasText(keyword)){
			hql+=" and model.name like '"+keyword+"%'";
		}
		
		Object obj = getHibernateTemplate().find(hql).get(0);
		return ((Long)obj).intValue();
	}

	@Override
	public List<PlayItem> loadByContentId(String contentUuid) {
		
		return getHibernateTemplate().find("from PlayItem as model where model.playContent.uuid = ? order by model.index", contentUuid);
	}

	@Override
	public void resortItem(String contentUuid, int index) {
		StringBuilder sb = new StringBuilder();
		sb.append("update PlayItem as model set model.index = model.index-1 where model.playContent.uuid=:contentUuid and model.index > :index ");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		query.setParameter("contentUuid", contentUuid);
		query.setParameter("index", index);
		query.executeUpdate();
	}

}
