package com.changhong.common.utils;

import java.util.Date;

/**
 * User: pengjie
 * Date: 2016/5/20
 * Time: 11:48
 */
public class UUIDUtils {

    public static String generateUUID() {
        return CHDateUtils.getFullDateFormatUUID(new Date()) + CHStringUtils.getRandomNumber(7);
    }

    public static void main(String[] args) {
        System.out.println(generateUUID());
    }
}
