package com.changhong.system.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.box.Area;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: pengjie
 * Date: 2016/11/21
 * Time: 13:44
 */
@Repository("areaDao")
public class AreaDaoImpl extends HibernateEntityObjectDao implements AreaDao {

    @Override
    public List<Area> loadAreaByParentId(String areaUuid) {
        StringBuilder builder = new StringBuilder();
        List<Object> parameter = new ArrayList<Object>();
        builder.append("from Area a");
        if (StringUtils.hasText(areaUuid)) {
            builder.append(" where a.parentArea.uuid = ?");
            parameter.add(areaUuid);
        } else {
            builder.append(" where a.parentArea is null");
        }

        List<Area> categories;
        if (StringUtils.hasText(areaUuid)) {
            categories = getHibernateTemplate().find(builder.toString(), parameter.toArray());
        } else {
            categories = getHibernateTemplate().find(builder.toString());
        }
        return categories;
    }

	@Override
	public List<Area> loadAllArea() {
		
		return getHibernateTemplate().find(" from Area ");
	}
}
