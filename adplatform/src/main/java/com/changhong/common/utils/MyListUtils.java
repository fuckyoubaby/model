package com.changhong.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-20 
 * Time: 15:26:42
 *
 */
public class MyListUtils {
	/**
	 * Hastset 根据 hashcode 判断是否重复，数据不会重复。
	 * 使用HashSet去重
	 * @param list
	 * @return
	 */
	 public static List removeDuplicateByHashSet(List list)  {
	    HashSet h  =   new  HashSet(list);
	    list.clear();
	    list.addAll(h);
	    return list;
	}
	 
	 /**
	  * Hastset 根据 hashcode 判断是否重复，数据不会重复。
	  * ArrayList 保持顺序
	  * @param list
	  * @return
	  */
	 public static List removeDuplicateWithOrderByHashSet(List list)  {
	      Set set  =   new  HashSet();
	      List newList  =   new  ArrayList();
	   for  (Iterator iter  =  list.iterator(); iter.hasNext();)  {
	         Object element  =  iter.next();
	         if  (set.add(element))
	            newList.add(element);
	     } 
	     return newList;
	 }
	 
	 /**
	  * 冒泡排序
	  * @param list
	  * @return
	  */
	 public static List removeDuplicate(List list)  {
	   for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
	    for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
	      if  (list.get(j).equals(list.get(i)))  {
	        list.remove(j);
	      } 
	    } 
	  } 
	  return list;
	}
	 
}
