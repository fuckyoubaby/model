package com.changhong.common.utils;

import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.system.domain.log.ActionModule;
import com.changhong.system.domain.log.ActionType;
import com.changhong.system.domain.user.User;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.web.event.ActionLogEvent;
/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-2-27 
 * Time: 13:34:42
 */
public class CHLogUtils {
	public static void doLog( Class clas,String logInfo, String message ){
		ApplicationLog.infoWithCurrentUser(clas, logInfo);
		User current = SecurityUtils.currentUser();
		ActionLogEvent event = new ActionLogEvent(current.getUuid(), current.getXingMing(), ActionType.A_LOG, ActionModule.A_AD, message);
		ApplicationThreadPool.executeThread(event);
	}
}
