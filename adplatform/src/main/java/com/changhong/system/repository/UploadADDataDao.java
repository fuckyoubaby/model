package com.changhong.system.repository;

import java.util.Date;
import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.uploaddata.UploadADData;

/**
 * 
 * @author shijinxiang
 * 2017年1月9日
 * 下午2:13:23
 */
public interface UploadADDataDao extends EntityObjectDao{

	List<UploadADData> loadAdData(String areaId,int startPosition, int pageSize);
	
	/**
	 * 获取某个地区的所有的盒子
	 * @param areaId
	 * @param offset
	 * @param length
	 * @return
	 */
	List<Box> loadBoxsByAreaId(String areaId,int offset,int length);
	
	/**
	 * 获取摸个地区盒子的数目
	 * @param areaId
	 * @return
	 */
	int getCountByAreaId(String areaId);
	
	/**
	 * 获取某段时期内某个盒子播放的资源列表
	 * @param mac
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<UploadADData> loadAdDatas(String mac,Date startDate,Date endDate);
}
