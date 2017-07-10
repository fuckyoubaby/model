package com.changhong.common.utils;

import java.util.Scanner;

/**
 * User: pengjie
 * Date: 2016/7/7
 * Time: 16:46
 */
public class CHIpUtils {
    public static long ipToLong(String ip) {
        long ipLong = 0;
        if (ValidationUtils.isRightIpAddress(ip)) {
            Scanner sc = new Scanner(ip).useDelimiter("\\.");
            ipLong = (sc.nextLong() << 24) + (sc.nextLong() << 16) + (sc.nextLong() << 8) + (sc.nextLong());
        }
        return ipLong;
    }

    public static void main(String[] args) {
        System.out.println(CHIpUtils.ipToLong("255.255.255.255"));
    }
}
