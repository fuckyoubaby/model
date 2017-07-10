package com.changhong.system.service;

import com.changhong.system.domain.box.Box;
import com.changhong.system.web.facade.dto.BoxCommandDTO;
import com.changhong.system.web.facade.dto.BoxDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-22
 * Time: 下午4:52
 */
public interface BoxInfoService {

    /*************************************机顶盒信息相关*******************************/

    BoxDTO obtainSearchBox(String boxInfo);

    List<BoxDTO> obtainBoxInfosByCommunity(String communityUuid);

    BoxDTO obtainBoxInfoByUuid(String boxUuid);

    void deleteBoxInfo(String boxInfoUuid);

    void changeBoxInfoDetails(BoxDTO dto);

    boolean obtainBoxMacDuplicate(String boxUuid, String mac);

    List<BoxDTO> obtainProblemBoxInfos(int startPosition, int pageSize);

    int obtainProblemBoxInfoSize();

    Box obtainBox(String mac);

    /*************************************机顶盒命令相关*******************************/
    
    boolean saveCommuCommand(String selectedCommus,String commandValue,String planTime,String comment);
    
    void saveBoxCommand(String selectedBoxes, String commandValue, String planTime, String comment);

    List<BoxCommandDTO> obtainCommands(String communityUuid, int startPosition, int pageSize);

    int obtainCommandSize(String communityUuid);
    
    void updateBoxCommandPlanTime();
}
