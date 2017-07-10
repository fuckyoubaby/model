package com.changhong.system.repository;

import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.advertisment.PlayContent;

/**
 * 
 * Author: Guo xiaoyang
 * Date: 2017-1-11 
 * Time: 18:12:10
 *
 */
public interface PlayContentDao extends EntityObjectDao{
	/**
	 * 查看名称是否重复，用于保存时。存在返回 true，不存在返回 false。
	 * @param name
	 * @return
	 */
	boolean obtainNameExists(String name);
	/**
	 * 查看名称是否重复，用于更新时。存在返回 true，不存在返回 false。
	 * @param pcUuid
	 * @param name
	 * @return
	 */
	boolean obtainNameExists(String pcUuid, String name);
	
	/**
	 * 获取指定关键词和分页的数据
	 * @param keyword 查找的关键词
	 * @param startPositino 起始位置
	 * @param size 获取大小
	 * @return
	 */
	List<PlayContent> loadPlayContents(String keyword, int startPosition, int size);
	
	/**
	 * 获取符合指定关键词的数量
	 * @param keyword 指定的关键词
	 * @return
	 */
	int loadAmount(String keyword);
	
	PlayContent loadByUpgradeId(String uuid);
	
	List<PlayContent> loadEnablePlayContents(String keyword, int startPosition, int size);
	
	int loadEnableAmount(String keyword);
	void updateUpgrade(String playContentUuid);
	
	List<PlayContent> loadPlayContentsByContentResourceId(String contentResourceId);
}
