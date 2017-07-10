package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.upgrade.ContentUpgrade;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-2-2
 * Time: 10:41:59
 */
public interface ContentUpgradeDao extends EntityObjectDao{
	/**
	 * 新建时检测是否名字重复
	 * @param name
	 * @return
	 */
	boolean obtainNameExists(String name);
	
	/**
	 * 更新时检测是否有重复
	 * @param name
	 * @param uuid
	 * @return
	 */
	boolean obtainNameExists(String name, String uuid);

	/**
	 * 获取指定关键词和分页信息的内容升级列表
	 * @param keyword 查找的关键词
	 * @param startPosition 起始位置
	 * @param size 获取大小
	 * @return
	 */
	List<ContentUpgrade> loadContentUpgrades(String keyword, int startPosition, int size);
	
	/**
	 * 获取指定关键词的数量
	 * @param keyword 指定关键词
	 * @return
	 */
	int loadAmount(String keyword);
	
	
	List<ContentUpgrade> loadAllContentUpgrades();
}
