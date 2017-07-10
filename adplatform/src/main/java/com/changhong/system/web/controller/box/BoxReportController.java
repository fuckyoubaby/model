package com.changhong.system.web.controller.box;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.changhong.common.utils.JodaUtils;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.domain.box.BoxReport;
import com.changhong.system.domain.box.BoxReportStatus;
import com.changhong.system.service.BoxReportService;
import com.changhong.system.web.paging.BoxReportOverviewPaging;


@Controller
public class BoxReportController {
	
	@Autowired
	private BoxReportService boxReportService;
	
	
	@RequestMapping("/backend/boxreportmanage.html")
	public String toBoxReportIndex(HttpServletRequest request, ModelMap model){
		  String startDate = ServletRequestUtils.getStringParameter(request, "startDate", JodaUtils.getFirstDateOfMonth().toString());
	      String endDate = ServletRequestUtils.getStringParameter(request, "endDate", JodaUtils.getEndDateOfMonth().toString());
	      int current = ServletRequestUtils.getIntParameter(request, "current", 1);
	      request.setAttribute("current", current);
		
	      BoxReportOverviewPaging paging = new BoxReportOverviewPaging(boxReportService);
	      initPaging(paging, current, startDate, endDate);
	      
	      List<BoxReport> reports = paging.getItems();
	      
	      model.put("reports", reports);
	      model.put("paging",paging);
	      initMenu(model);
	      
		return "backend/box/boxreportoverview";
	}

	@RequestMapping("/backend/boxreportdelete.html")
	public String deleteReport(HttpServletRequest request, ModelMap model){
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate", JodaUtils.getFirstDateOfMonth().toString());
	    String endDate = ServletRequestUtils.getStringParameter(request, "endDate", JodaUtils.getEndDateOfMonth().toString());
	    int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		String uuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
		
		if(StringUtils.hasText(uuid)){
			boxReportService.deleteBoxReport(uuid);
		}
		
		return "redirect:boxreportmanage.html?startDate="+startDate+"&endDate="+endDate+"&current="+current;
	}
	
	@RequestMapping("/backend/boxreportdsolved.html")
	public String solvedReport(HttpServletRequest request, ModelMap model){
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate", JodaUtils.getFirstDateOfMonth().toString());
	    String endDate = ServletRequestUtils.getStringParameter(request, "endDate", JodaUtils.getEndDateOfMonth().toString());
	    int current = ServletRequestUtils.getIntParameter(request, "current", 1);
		String uuid = ServletRequestUtils.getStringParameter(request, "uuid", "");
		
		if(StringUtils.hasText(uuid)){
			boxReportService.changeStatus(uuid, BoxReportStatus.B_R_SOLVED);
		}
		
		return "redirect:boxreportmanage.html?startDate="+startDate+"&endDate="+endDate+"&current="+current;
	}
	
	
	private void initPaging(BoxReportOverviewPaging paging, int current,
			String startDate, String endDate) {
		paging.setCurrentPageNumber(current);
		paging.setStartDate(startDate);
		paging.setEndDate(endDate);
		
	}

	
	private void initMenu(ModelMap model){
		model.put(SessionKey.MENU_KEY,"ERROR_MANAGE");
		model.put(SessionKey.SUB_MENU_KEY,"REPORT_MANAGE");
	}
}
