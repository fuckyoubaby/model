package com.changhong.common.repository;

import com.changhong.common.domain.EntityBase;
import org.hibernate.Transaction;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:23
 */
public interface EntityObjectDao {

    void saveOrUpdate(Object object);

    void persist(Object object);

    void delete(Object object);

    <T extends EntityBase> EntityBase findByUuid(String uuid, Class<T> clazz);

    <T extends EntityBase> List<T> findByUuids(String[] uuids, Class<T> clazz);

    void saveAll(List list);

    Transaction getCurrentTx();
}
