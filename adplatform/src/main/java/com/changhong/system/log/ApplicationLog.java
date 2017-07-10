package com.changhong.system.log;

import com.changhong.common.utils.SecurityUtils;
import org.apache.log4j.Logger;

/**
 * User: Jack Wang
 * Date: 16-9-11
 * Time: 上午8:38
 */
public class ApplicationLog {

    private static Logger logger = Logger.getLogger("com.changhong.server");

    public static void debug(Class zClass, String message) {
        logger.debug(zClass.getName() + " " + message);
    }

    public static void info(Class zClass, String message) {
        logger.info(zClass.getName() + " " + message);
    }

    public static void infoWithCurrentUser(Class zClass, String message) {
        String uuid = SecurityUtils.currectUserUuid();
        logger.info(zClass.getName() + " " + message + " by user " + uuid);
    }

    public static void error(Class zClass, Throwable e) {
        logger.error(e);
    }

    public static void error(Class zClass, String message) {
        logger.error(zClass.getName() + " " + message);
    }

    public static void error(Class zClass, String message, Throwable e) {
        logger.info(zClass.getName() + " " + message, e);
    }

}
