package com.changhong.common.utils;

import com.changhong.common.domain.EntityBase;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Administrator
 * Date: 15-10-25
 * Time: 下午3:45
 */
public class CHListUtils {

    public static List<String> getAllUuids(List<? extends EntityBase> domains) {
        List<String> list = new ArrayList<String>();
        if (domains != null) {
            for(EntityBase element: domains) {
                list.add(element.getUuid());
            }
        }
        return list;
    }

    public static <T> List<T> getSameElement(List<T> list1,List<T> list2) {
        List<T> list = new ArrayList<T>();
        for(T element:list2) {
            if(list1.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

    public static boolean hasElement(List list) {
        if(list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public static <T> List<T> removeSameElement(List<T> list1,List<T> list2) {
        for(T element:list2) {
            if(list1.contains(element)) {
                list1.remove(element);
            }
        }
        return list1;
    }
}
