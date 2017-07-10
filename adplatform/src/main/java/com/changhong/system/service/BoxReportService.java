package com.changhong.system.service;

import java.util.List;

import com.changhong.system.domain.box.BoxReport;
import com.changhong.system.domain.box.BoxReportStatus;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-22 
 * Time: 15:47:27 
 *
 */
public interface BoxReportService {
	
	void saveBoxReport(BoxReport report);
	
	void saveList(List<BoxReport> reports);
	
	void changeStatus(String uuid, BoxReportStatus status);
	
	List<BoxReport> obtainReports(String keyword, int startPosition, int pageSize);
	int obtainReportAmount(String keyword);
	
	List<BoxReport> obtainReports(String keyword, BoxReportStatus status, int startPosition, int pageSize);
	int obtainReportAmount(String keyword, BoxReportStatus status);
	
	List<BoxReport> obtainReports(String startDate, String endDate , int startPosition, int pageSize);
	int obtainReportAmount(String startDate, String endDate );
	
	void deleteBoxReport(String uuid);
	
}
