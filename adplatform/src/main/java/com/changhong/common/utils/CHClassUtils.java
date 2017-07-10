package com.changhong.common.utils;

/**
 * User: pengjie
 * Date: 2016/7/14
 * Time: 17:42
 */
public class CHClassUtils {
    public static <T> String getClassName(T t) {
        if (t != null) {
            return t.getClass().getName();
        }
        return null;
    }

    public static <T> String getSimpleClassName(T t) {
        if (t != null) {
            return t.getClass().getSimpleName();
        }
        return null;
    }
}
