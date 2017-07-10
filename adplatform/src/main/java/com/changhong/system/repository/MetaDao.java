package com.changhong.system.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.meta.MetaData;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-21
 * Time: 上午9:41
 */
public interface MetaDao extends EntityObjectDao {

    MetaData findSystmeDefaultMetaData();

    List<MetaData> findMetaDatas(int startPosition, int pageSize);

    int findMetaDataSize();
}
