package com.changhong.common.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Jack Wang
 * Date: 16-5-9
 * Time: 下午4:24
 */
public class ValidationUtils {

    private static final String IPADDRESS_REGULAR_EXPRESSION =
               "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";
    
    private static final String MAC_REGULAR_EXPRESSION=
    		"^[A-F\\d]{2}:[A-F\\d]{2}:[A-F\\d]{2}:[A-F\\d]{2}:[A-F\\d]{2}:[A-F\\d]{2}$";

    public static boolean isRightIpAddress(String ip) {
        if (StringUtils.hasText(ip)) {
            Pattern p = Pattern.compile(IPADDRESS_REGULAR_EXPRESSION);
            Matcher m = p.matcher(ip);
            return m.matches();
        }
        return false;
    }

    public static boolean isRightMac(String mac) {
        if (StringUtils.hasText(mac) && mac.length() == 17) {
            String[] tokens = StringUtils.delimitedListToStringArray(mac, ":");
            if (tokens.length == 6) {
                return true;
            }
            return false;
        }
        return false;
    }
    
    public static boolean isRightMacByReg(String mac){
    	 if (StringUtils.hasText(mac)) {
             Pattern p = Pattern.compile(MAC_REGULAR_EXPRESSION);
             Matcher m = p.matcher(mac);
             return m.matches();
         }
         return false;
    }
    
}
