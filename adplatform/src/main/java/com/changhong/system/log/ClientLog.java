package com.changhong.system.log;

import org.apache.log4j.Logger;

/**
 * User: Jack Wang
 * Date: 16-12-6
 * Time: 上午9:13
 */
public class ClientLog {

    private static Logger logger = Logger.getLogger("com.changhong.client");

    public static void error(String mac, String message) {
        logger.error(mac + "\n" + message);
    }
}
