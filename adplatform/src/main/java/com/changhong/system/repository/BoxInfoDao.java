package com.changhong.system.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.BoxCommand;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-22
 * Time: 下午5:05
 */
public interface BoxInfoDao extends EntityObjectDao {

    /*************************************机顶盒信息相关*******************************/

    Box findSearchBox(String boxInfo);

    List<Box> findBoxInfosByCommunity(String communityUuid);

    boolean findBoxMacDuplicate(String boxUuid, String mac);

    List<Box> findProblemBoxInfos(int startPosition, int pageSize);

    int findProblemBoxInfoSize();

    Box findBoxByMac(String mac);

    /*************************************机顶盒命令相关*******************************/

    List<BoxCommand> findCommands(String communityUuid, int startPosition, int pageSize);

    int findCommandSize(String communityUuid);

    List<BoxCommand>  findPlanBoxCommand();

}
