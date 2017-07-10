package com.changhong.system.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.changhong.common.utils.CHDateUtils;
import com.changhong.common.utils.CHListUtils;
import com.changhong.common.utils.CHLogUtils;
import com.changhong.system.domain.box.BoxReport;
import com.changhong.system.domain.box.BoxReportStatus;
import com.changhong.system.repository.BoxReportDao;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-22 
 * Time: 16:11:12
 *
 */

@Service("boxReportService")
public class BoxReportServiceImpl implements BoxReportService{

	
	@Autowired
	private BoxReportDao boxReportDao;
	
	@Override
	public void saveBoxReport(BoxReport report) {
		if(report!=null){
			boxReportDao.saveOrUpdate(report);
		}
	}

	@Override
	public void changeStatus(String uuid, BoxReportStatus status) {
		if(status == null || !StringUtils.hasText(uuid)) return;
		BoxReport report = (BoxReport) boxReportDao.findByUuid(uuid, BoxReport.class);
		
		if(report!=null){
			report.setStatus(status);
			report.setSolveTime(new Date());
			CHLogUtils.doLog(BoxReportServiceImpl.class, "update box report "+report.getUuid()+" status", "修改设备异常报告["+report.getUuid()+"]为"+status.getDescription());
		}
	}

	@Override
	public List<BoxReport> obtainReports(String keyword,
			int startPosition, int pageSize) {
		return boxReportDao.findReports(keyword, startPosition, pageSize);
	}

	@Override
	public int obtainReportAmount(String keyword) {
		return boxReportDao.findReportsAmount(keyword);
	}

	@Override
	public List<BoxReport> obtainReports(String keyword,
			BoxReportStatus status, int startPosition, int pageSize) {
		return boxReportDao.findReports(keyword, status, startPosition, pageSize);
	}

	@Override
	public int obtainReportAmount(String keyword, BoxReportStatus status) {
		return boxReportDao.findReportsAmount(keyword, status);
	}

	@Override
	public List<BoxReport> obtainReports(String startDate, String endDate,
			int startPosition, int pageSize) {
		Date startTime = CHDateUtils.parseSimpleDateFormat(startDate);
		Date endTime = CHDateUtils.parseSimpleDateFormat(endDate);
		
		if(startTime == null || endTime == null){
			return null;
		}else{
			return boxReportDao.findReports(startTime, endTime, startPosition, pageSize);
		}
	}

	@Override
	public int obtainReportAmount(String startDate, String endDate) {
		Date startTime = CHDateUtils.parseSimpleDateFormat(startDate);
		Date endTime = CHDateUtils.parseSimpleDateFormat(endDate);
		
		if(startTime == null || endTime == null){
			return 0;
		}
		return boxReportDao.findReportsAmount(startTime, endTime);
	}

	@Override
	public void deleteBoxReport(String uuid) {
		BoxReport report = (BoxReport) boxReportDao.findByUuid(uuid, BoxReport.class);
		if(report!=null){
			boxReportDao.delete(report);
			CHLogUtils.doLog(BoxReportServiceImpl.class, "delete box report "+report.getUuid()+" status", "删除设备异常报告["+report.getUuid()+"]");
		}
	}

	@Override
	public void saveList(List<BoxReport> reports) {
		if(CHListUtils.hasElement(reports)){
			boxReportDao.saveAll(reports);
		}
	}

}
