package com.changhong.system.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.common.utils.CHListUtils;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.BoxCommand;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-22
 * Time: 下午5:06
 */
@Repository("boxInfoDao")
public class BoxInfoDaoImpl extends HibernateEntityObjectDao implements BoxInfoDao {

    /*************************************机顶盒信息相关*******************************/

    @Override
    public Box findSearchBox(String boxInfo) {
        List list = getHibernateTemplate().find("from Box b where b.mac like '%" + boxInfo + "%' or b.community.name like '%" + boxInfo + "%'");
        if (list.isEmpty()) {
            return null;
        }
        return (Box) list.get(0);
    }

    @Override
    public List<Box> findBoxInfosByCommunity(String communityUuid) {
        List<Object> parameter = new ArrayList<Object>();
        StringBuilder builder = new StringBuilder();
        builder.append("from Box b where b.community.uuid = ?");
        parameter.add(communityUuid);

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        for(int i = 0; i < parameter.size(); ++i) {
            query.setParameter(i, parameter.get(i));
        }
        //TODO:currently no one community box number than 1000, if max than this number, use paging
        query.setFirstResult(0);
        query.setMaxResults(1000);

        List<Box> boxes = query.list();
        return boxes;
    }

    @Override
    public boolean findBoxMacDuplicate(String boxUuid, String mac) {
        if (!StringUtils.hasText(boxUuid)) {
            List list = getHibernateTemplate().find("select count(b.uuid) from Box b where b.mac = ?", mac);
            return ((Long)list.get(0)).intValue() > 0 ? true : false;
        } else {
            List list = getHibernateTemplate().find("select count(b.uuid) from Box b where b.mac = ? and b.uuid <> ?",
                    new Object[]{mac, boxUuid});
            return ((Long)list.get(0)).intValue() > 0 ? true : false;
        }
    }

    @Override
    public List<Box> findProblemBoxInfos(int startPosition, int pageSize) {
        Date before = Box.shouldBeforeIsProblem();

        List<Object> parameter = new ArrayList<Object>();
        StringBuilder builder = new StringBuilder();
        builder.append("from Box b where b.lastReportTime < ? order by b.community.uuid asc");
        parameter.add(before);

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);
        for(int i = 0; i < parameter.size(); ++i) {
            query.setParameter(i, parameter.get(i));
        }

        List<Box> boxes = query.list();
        return boxes;
    }

    @Override
    public int findProblemBoxInfoSize() {
        Date before = Box.shouldBeforeIsProblem();
        List list =  getHibernateTemplate().find("select count(b.uuid) from Box b where b.lastReportTime < ? order by b.community.uuid asc", new Object[]{before});
        return ((Long)list.get(0)).intValue();
    }

    @Override
	public Box findBoxByMac(String mac) {
		String hql = "from Box where mac=?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, mac);
		return (Box) (CHListUtils.hasElement(query.list()) ? query.list().get(0) : null);
	}

    /*************************************机顶盒命令相关*******************************/

    @Override
    public List<BoxCommand> findCommands(String communityUuid, int startPosition, int pageSize) {
        List<Object> parameter = new ArrayList<Object>();
        StringBuilder builder = new StringBuilder();
        builder.append("select bc from BoxCommand bc, Box b where bc.mac = b.mac and b.community.uuid = ? order by bc.timestamp desc");
        parameter.add(communityUuid);

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);
        for(int i = 0; i < parameter.size(); ++i) {
            query.setParameter(i, parameter.get(i));
        }

        List<BoxCommand> commands = query.list();
        return commands;
    }

    @Override
    public int findCommandSize(String communityUuid) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(bc.uuid) from BoxCommand bc, Box b where bc.mac = b.mac and b.community.uuid = ?");
        List list =  getHibernateTemplate().find(builder.toString(), new Object[]{communityUuid});
        return ((Long)list.get(0)).intValue();
    }

    @Override
    public List<BoxCommand> findPlanBoxCommand() {
        return getHibernateTemplate().find("from BoxCommand bc where bc.finished = false and (bc.planTime is not null or bc.planTime != '')");
    }
}
