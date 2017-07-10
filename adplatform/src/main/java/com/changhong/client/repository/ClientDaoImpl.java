package com.changhong.client.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.BoxCommand;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:25
 */
@Repository("clientDao")
public class ClientDaoImpl extends HibernateEntityObjectDao implements ClientDao {

    @Override
    public Box findBoxByMac(String mac) {
        List<Box> macs = getHibernateTemplate().find("from Box b where b.mac = ?", new Object[]{mac});
        if (macs.isEmpty()) {
            return null;
        }
        return macs.get(0);
    }

    @Override
    public BoxCommand findBoxCommand(String mac) {
        List<BoxCommand> commands = getHibernateTemplate().find("from BoxCommand b where b.mac = ? and b.finished = false and (b.planTime is null or b.planTime = '') order by b.timestamp desc ", new Object[]{mac});
        if (commands.isEmpty()) {
            return null;
        }
        return commands.get(0);
    }

	@Override
	public void changeBoxCommandStatus(String mac, String uuid) {
		String hql = "update BoxCommand b set b.finished =true where b.mac = :mac and b.finished = false and b.uuid <> :uuid" ;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("mac", mac);
		query.setParameter("uuid", uuid);
		query.executeUpdate();
	}
}
