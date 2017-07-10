package com.changhong.system.service;

import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.BoxCommand;
import com.changhong.system.domain.box.BoxCommandType;
import com.changhong.system.domain.log.ActionModule;
import com.changhong.system.domain.log.ActionType;
import com.changhong.system.domain.user.User;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.repository.BoxInfoDao;
import com.changhong.system.web.event.ActionLogEvent;
import com.changhong.system.web.facade.assember.BoxCommandWebAssember;
import com.changhong.system.web.facade.assember.BoxWebAssember;
import com.changhong.system.web.facade.dto.BoxCommandDTO;
import com.changhong.system.web.facade.dto.BoxDTO;
import com.sun.star.sdb.CommandType;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-11-22
 * Time: 下午4:52
 */
@Service("boxInfoService")
public class BoxInfoServiceImpl implements BoxInfoService {

    @Autowired
    private BoxInfoDao boxInfoDao;

    /*************************************机顶盒信息相关*******************************/

    @Override
    public BoxDTO obtainSearchBox(String boxInfo) {
        Box box = boxInfoDao.findSearchBox(boxInfo);
        return BoxWebAssember.toBoxDTO(box, false);
    }

    @Override
    public List<BoxDTO> obtainBoxInfosByCommunity(String communityUuid) {
        List<Box> boxes = boxInfoDao.findBoxInfosByCommunity(communityUuid);
        return BoxWebAssember.toBoxDTOList(boxes, false);
    }

    @Override
    public BoxDTO obtainBoxInfoByUuid(String boxUuid) {
        Box box = (Box) boxInfoDao.findByUuid(boxUuid, Box.class);
        return BoxWebAssember.toBoxDTO(box, false);
    }

    @Override
    public void deleteBoxInfo(String boxInfoUuid) {
        Box box = (Box)boxInfoDao.findByUuid(boxInfoUuid, Box.class);
        boxInfoDao.delete(box);

        //日志部分
        ApplicationLog.infoWithCurrentUser(BoxInfoServiceImpl.class, "delete box " + box.getUuid() + " info");
        User current = SecurityUtils.currentUser();
        ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_AD, "删除投影仪" + box.getNote());
        ApplicationThreadPool.executeThread(event);
    }

    @Override
    public void changeBoxInfoDetails(BoxDTO dto) {
        Box box = BoxWebAssember.toBoxDomain(dto);
        boxInfoDao.saveOrUpdate(box);

        ApplicationLog.infoWithCurrentUser(BoxInfoServiceImpl.class, "save box " + box.getUuid() + " info");
    }

    @Override
    public boolean obtainBoxMacDuplicate(String boxUuid, String mac) {
        return boxInfoDao.findBoxMacDuplicate(boxUuid, mac);
    }

    @Override
    public List<BoxDTO> obtainProblemBoxInfos(int startPosition, int pageSize) {
        List<Box> boxes = boxInfoDao.findProblemBoxInfos(startPosition, pageSize);
        return BoxWebAssember.toBoxDTOList(boxes, true);
    }

    @Override
    public int obtainProblemBoxInfoSize() {
        return boxInfoDao.findProblemBoxInfoSize();
    }

    @Override
	public Box obtainBox(String mac) {
		return boxInfoDao.findBoxByMac(mac);
	}

    /*************************************机顶盒命令相关*******************************/

    @Override
    public void saveBoxCommand(String selectedBoxes, String commandValue, String planTime, String comment) {
        String[] macs = StringUtils.delimitedListToStringArray(selectedBoxes, ",");
        if (macs.length > 0 && StringUtils.hasText(commandValue)) {
            List<BoxCommand> commands = new ArrayList<>();

            for (String mac : macs) {
                mac = mac.trim();
                if (StringUtils.hasText(mac)) {
                    BoxCommand command = new BoxCommand(mac, BoxCommandType.valueOf(commandValue), comment);
                    if (command.getCommandType().equals(BoxCommandType.C_RESOURCE_PLAN)) {
                        command.setPlanTime(new LocalDate().toString() + " " + planTime);
                    } else {
                        command.setPlanTime(null);
                    }
                    commands.add(command);
                }
            }

            boxInfoDao.saveAll(commands);

            //日志部分
            ApplicationLog.infoWithCurrentUser(BoxInfoServiceImpl.class, "save command " + commandValue + " for box " + selectedBoxes);
            User current = SecurityUtils.currentUser();
            ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_AD, "给投影仪设置命令" +( comment == null ? "" : comment));
            ApplicationThreadPool.executeThread(event);
        }
    }

	@Override
	public boolean saveCommuCommand(String selectedCommus, String commandValue,
			String planTime, String comment) {
		String []commuUuids = StringUtils.delimitedListToStringArray(selectedCommus, ",");
		List<Box> listBoxs = new ArrayList<Box>();
		List<String> lists = new ArrayList<String>();  //用来存放取到的mac地址
		List<BoxCommand> commands = new ArrayList<>();
		if(commuUuids.length>0&& StringUtils.hasText(commandValue)){
			for(int i=0;i<commuUuids.length;i++){
			   //通过commuUuid找到所有mac
				listBoxs = boxInfoDao.findBoxInfosByCommunity(commuUuids[i]);
				for(int j=0;j<listBoxs.size();j++){
					String mac = listBoxs.get(j).getMac();
					lists.add(mac);
				}
			}
            for (String mac : lists) {
                mac = mac.trim();
                if (StringUtils.hasText(mac)) {
                    BoxCommand command = new BoxCommand(mac, BoxCommandType.valueOf(commandValue), comment);
                    if (command.getCommandType().equals(BoxCommandType.C_RESOURCE_PLAN)) {
                        command.setPlanTime(new LocalDate().toString() + " " + planTime);
                    } else {
                        command.setPlanTime(null);
                    }
                    commands.add(command);
                }
            }
            boxInfoDao.saveAll(commands);
          //日志部分
            ApplicationLog.infoWithCurrentUser(BoxInfoServiceImpl.class, "save command " + commandValue + " for box " + commuUuids);
            User current = SecurityUtils.currentUser();
            ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_AD, "给小区投影仪设置命令" +( comment == null ? "" : comment));
            ApplicationThreadPool.executeThread(event);
            return true;
		}
		return false;
	}

    @Override
    public int obtainCommandSize(String communityUuid) {
        return boxInfoDao.findCommandSize(communityUuid);
    }

    @Override
    public List<BoxCommandDTO> obtainCommands(String communityUuid, int startPosition, int pageSize) {
        List<BoxCommand> commands = boxInfoDao.findCommands(communityUuid, startPosition, pageSize);
        return BoxCommandWebAssember.toBoxCommandDTOList(commands);
    }

    @Override
    public void updateBoxCommandPlanTime() {
        ApplicationLog.debug(BoxInfoServiceImpl.class, "begin to check box plan command");
        List<BoxCommand> commands = boxInfoDao.findPlanBoxCommand();
        if (commands != null) {
            for (BoxCommand command : commands) {
                //如果PLAN的时间为现在计划的时间，就把这个COMMAND设置为可用
                String planTime = command.getPlanTime();
                String currentTime = CHDateUtils.getFullDateFormat(new Date()) + ":00";
                if (currentTime.equals(planTime)) {
                    command.setPlanTime(null);
                    boxInfoDao.saveOrUpdate(command);
                }
                ApplicationLog.info(BoxInfoServiceImpl.class, "check box plan command uuid is " + command.getUuid() + " and plan time is " + planTime);
            }
        }
        ApplicationLog.debug(BoxInfoServiceImpl.class, "end to check box plan command");
    }

}
