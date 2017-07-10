package com.changhong.system.repository;

import java.util.Date;
import java.util.List;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.box.BoxReport;
import com.changhong.system.domain.box.BoxReportStatus;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-22 
 * Time: 15:10:53
 *
 */
public interface BoxReportDao extends EntityObjectDao{
	
	List<BoxReport> findReports(String keyword, int startPostion, int pageSize);
	int findReportsAmount(String keyword);
	
	List<BoxReport> findReports(String keyword, BoxReportStatus status,int startPosition, int pageSize);
	int findReportsAmount(String keyword, BoxReportStatus status);
	
	List<BoxReport> findReports(String keyword, BoxReportStatus status,String orderByFiledName,int startPosition, int pageSize);
	
	
	List<BoxReport> findReports(Date startDate, Date endDate,int startPosition, int pageSize);
	int findReportsAmount(Date startDate, Date endDate);
	
}
