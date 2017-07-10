package com.changhong.system.web.event;

import java.util.ArrayList;
import java.util.List;

import com.changhong.common.utils.CHListUtils;
import com.changhong.common.web.application.ApplicationEventPublisher;
import com.changhong.system.domain.box.BoxReport;
import com.changhong.system.domain.box.BoxReportStatus;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.service.BoxReportService;

/**
 * 
 * Author: Xiaoyang Guo
 * Date: 2017-3-23 
 * Time: 14:09:54
 *
 */
public class BoxReportEvent  implements Runnable{
	
	public BoxReportEvent( List<String> codes, String mac) {
		this.codes = codes;
		this.mac = mac;
	}


	//private String desc 
	//TODO 是否保存异常描述
	private String mac;
	private List<String> codes;  

	
	@Override
	public void run() {

		try{
			if(CHListUtils.hasElement(codes)){
				BoxReportService boxReportService =  (BoxReportService) ApplicationEventPublisher.getBean("boxReportService");
				List<BoxReport> reports = new ArrayList<BoxReport>();
				for(String code : codes){
					BoxReport report = new BoxReport(mac, BoxReportStatus.B_R_REPORT, code, null);
					reports.add(report);
				}
				boxReportService.saveList(reports);
			}
		}catch(Exception e){
			ApplicationLog.error(BoxReportEvent.class, "save box report failed",e);
		}
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public String getMac() {
		return mac;
	}


	public void setMac(String mac) {
		this.mac = mac;
	}

}
