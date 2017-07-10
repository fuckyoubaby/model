package com.changhong.client.repository;


import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.BoxCommand;
import com.changhong.system.domain.meta.MetaData;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:25
 */
public interface ClientDao extends EntityObjectDao {

    Box findBoxByMac(String mac);

    BoxCommand findBoxCommand(String mac);

	void changeBoxCommandStatus(String mac, String uuid);
}
