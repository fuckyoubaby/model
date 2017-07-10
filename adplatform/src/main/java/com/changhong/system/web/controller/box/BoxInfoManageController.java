package com.changhong.system.web.controller.box;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.common.domain.Option;
import com.changhong.common.utils.CHListUtils;
import com.changhong.common.utils.CHStringUtils;
import com.changhong.system.domain.box.BoxCommand;
import com.changhong.system.domain.box.BoxCommandType;
import com.changhong.system.domain.box.BoxMac;
import com.changhong.system.domain.box.BoxMacStatus;
import com.changhong.system.domain.meta.MetaDataLevel;
import com.changhong.system.service.BoxInfoService;
import com.changhong.system.service.BoxMacService;
import com.changhong.system.service.CommunityService;
import com.changhong.system.web.facade.dto.BoxCommandDTO;
import com.changhong.system.web.facade.dto.BoxDTO;
import com.changhong.system.web.facade.dto.CommunityDTO;
import com.changhong.system.web.paging.BoxCommandOverviewPaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 2016/11/21
 * Time: 20:38
 */
@Controller
public class BoxInfoManageController {

    @Autowired
    private BoxInfoService boxInfoService;

    @Autowired
    private CommunityService communityService;
  
    @Autowired
    private BoxMacService boxMacService;

    /**************************************search box to box overview*************************************************/

    @RequestMapping("/backend/searchboxinfo.html")
    protected void searchBoxInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String boxInfo = ServletRequestUtils.getStringParameter(request, "boxInfo", "");

        BoxDTO dto = boxInfoService.obtainSearchBox(boxInfo);
        JSONObject rst = new JSONObject();
        if (dto == null) {
            rst.put("uuid", "-1");
        } else {
            rst.put("uuid", dto.getCommunityUuid());
        }

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(rst.toJSONString());
        writer.flush();
        writer.close();
    }

    /**************************************Box Overview*************************************************/

    @RequestMapping("/backend/boxinfomanagement.html")
    protected String getCommunities(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String communityUuid = ServletRequestUtils.getStringParameter(request, "communityUuid", "");

        CommunityDTO community = communityService.obtainCommunityByUuid(communityUuid, true);
        model.put("community", community);

        List<BoxDTO> boxes = boxInfoService.obtainBoxInfosByCommunity(communityUuid);
        model.put("boxes", boxes);

        return "backend/box/boxinfomanage";
    }

    @RequestMapping("/backend/getboxinfo.html")
    protected void getCommunities(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String communityUuid = ServletRequestUtils.getStringParameter(request, "communityUuid", "");

        List<BoxDTO> boxes = boxInfoService.obtainBoxInfosByCommunity(communityUuid);
        JSONObject rst = new JSONObject();
        if (CHListUtils.hasElement(boxes)) {
            JSONArray jsonArray = new JSONArray();

            int i=1;
            for (BoxDTO box : boxes) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("num", i++);
                jsonObject.put("uuid", box.getUuid());
                jsonObject.put("timestamp", box.getTimestamp());
                jsonObject.put("mac", box.getMac());
                jsonObject.put("note", box.getNote());
                jsonObject.put("version", box.getVersion());
                jsonObject.put("cpu", box.getCpuRate());
                jsonObject.put("mem", box.getMemRate());
                jsonObject.put("disk", box.getDiskRate());
                jsonObject.put("lastUpdate", box.getLastReportTime());
                jsonObject.put("isOnline", box.isOnline());
                jsonArray.add(jsonObject);
            }
            rst.put("rows", jsonArray);
        }

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(rst.toJSONString());
        writer.flush();
        writer.close();
    }

    /**************************************Box Info*************************************************/

    @RequestMapping(value = "/backend/boxinfoform.html", method = RequestMethod.GET)
    protected String setBoxInfoForm(HttpServletRequest request, ModelMap model) throws Exception {
        String communityUuid = ServletRequestUtils.getStringParameter(request, "communityUuid");
        String method = ServletRequestUtils.getStringParameter(request, "method");

        BoxDTO dto = null;
        if ("add".equals(method)) {
            dto = new BoxDTO(communityUuid);
        } else {
            String boxUuid = ServletRequestUtils.getStringParameter(request, "boxUuid", "");
            dto = boxInfoService.obtainBoxInfoByUuid(boxUuid);
        }
        model.put("box", dto);
        return "backend/box/boxform";
    }

    @RequestMapping(value = "/backend/boxinfoform.html", method = RequestMethod.POST)
    public void saveBoxInfoForm(@ModelAttribute("box") BoxDTO box, HttpServletResponse response) throws Exception {
        boxInfoService.changeBoxInfoDetails(box);
    }

    @RequestMapping(value = "/backend/checkboxmacduplicate.html", method = RequestMethod.POST)
    public void checkBoxMacDuplicate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String boxUuid = ServletRequestUtils.getStringParameter(request, "boxUuid", "");
        
        String mac = ServletRequestUtils.getStringParameter(request, "mac", "");
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.hasText(boxUuid)){
        	//更新时
        	 BoxDTO dto = boxInfoService.obtainBoxInfoByUuid(boxUuid);
             if(dto!=null && StringUtils.hasText(mac)){
             	if(dto.getMac().equals(mac)){
             		BoxMac boxmac = boxMacService.obtainByMac(mac);
             		if(boxmac.getMacStatus()!=BoxMacStatus.B_USED){
             			boxMacService.changeStatus(boxmac.getUuid(), BoxMacStatus.B_USED);
             		}
             		//新旧mac地址相同时，跳过检测
             		jsonObject.put("result", false);
             	}else{
             		checkMacUseful(mac, jsonObject);
             	}
             }else{
            	 jsonObject.put("result", true);
            	 jsonObject.put("msg","无效的MAC编号");
             }
        }else{
        	//新增时
        	checkMacUseful(mac, jsonObject);
        }
        
        //返回结果
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toJSONString());
        writer.flush();
        writer.close();
    }

    protected void checkMacUseful(String mac, JSONObject jsonObject){
    	BoxMac boxmac = boxMacService.obtainByMac(mac);
        if(boxmac==null){
        	  jsonObject.put("result", true);
        	  jsonObject.put("msg", "mac不在库中，请先添加");
        }else{
        	if(boxmac.getMacStatus() == BoxMacStatus.B_INITE){
        		jsonObject.put("result", false);
          	  	//jsonObject.put("msg", "mac可用");
        	}else if(boxmac.getMacStatus() == BoxMacStatus.B_USED){
        		 jsonObject.put("result", true);
           	  	 jsonObject.put("msg", "mac编号已使用");
        	}else{
        		 jsonObject.put("result", true);
           	  	 jsonObject.put("msg", "mac编号状态不可用");
        	}
        }
    }
    
    
    /**************************************Box Delete*************************************************/

    @RequestMapping(value = "/backend/boxinfodelete.html")
    protected String deleteBoxInfo(HttpServletRequest request, ModelMap model) throws Exception {
        String boxInfoUuid = ServletRequestUtils.getStringParameter(request, "boxInfoUuid");
        String communityUuid = ServletRequestUtils.getStringParameter(request, "communityUuid");

        boxInfoService.deleteBoxInfo(boxInfoUuid);

        return "redirect:boxinfomanagement.html?communityUuid=" + communityUuid;
    }

    /**************************************Box Command Save*************************************************/

    @RequestMapping(value = "/backend/boxcommandform.html", method = RequestMethod.GET)
    protected String setBoxCommandForm(HttpServletRequest request, ModelMap model) throws Exception {

         List<Option> options = BoxCommandType.getOptions();
         model.put("commands", options);
         return "backend/box/boxcommandform";
    }

    @RequestMapping(value = "/backend/boxcommandform.html", method = RequestMethod.POST)
    public void saveBoxCommandForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String selectedBoxes = ServletRequestUtils.getStringParameter(request, "selectedBoxes", "");
        String commandValue = ServletRequestUtils.getStringParameter(request, "commandValue", "");
        String commandComment = ServletRequestUtils.getStringParameter(request, "commandComment", "");
        String planTime1 = CHStringUtils.toFixNumberString(ServletRequestUtils.getStringParameter(request, "planTime1", ""), 2);
        String planTime2 = ServletRequestUtils.getStringParameter(request, "planTime2", "");
        boxInfoService.saveBoxCommand(selectedBoxes, commandValue, planTime1 + ":" + planTime2 + ":00", commandComment);
    }
    
    //新
    @RequestMapping(value = "/backend/Commucommandform.html", method = RequestMethod.GET)
    protected String setCommuCommandForm(HttpServletRequest request, ModelMap model) throws Exception {
        List<Option> options = BoxCommandType.getOptions();
        model.put("commands", options);
        return "backend/box/commucommandform";
   }
    
    @RequestMapping(value = "/backend/Commucommandform.html", method = RequestMethod.POST)
    public void saveCommuCommandForm(HttpServletRequest request, HttpServletResponse response)throws Exception{
    	//取得所有的community uuid
    	String selectedCommus = ServletRequestUtils.getStringParameter(request, "selectedCommus", "");
    	String commandValue = ServletRequestUtils.getStringParameter(request, "commandValue", "");
        String commandComment = ServletRequestUtils.getStringParameter(request, "commandComment", "");
        String planTime1 = CHStringUtils.toFixNumberString(ServletRequestUtils.getStringParameter(request, "planTime1", ""), 2);
        String planTime2 = ServletRequestUtils.getStringParameter(request, "planTime2", "");
        boolean rst = boxInfoService.saveCommuCommand(selectedCommus,commandValue,planTime1 + ":" + planTime2 + ":00",commandComment);
        JSONObject obj = new JSONObject();
        obj.put("result", rst);
        
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(obj.toJSONString());
        writer.flush();
        writer.close();
    }

    /**************************************Box Command Overview*************************************************/

    @RequestMapping(value = "/backend/boxcommandmanagement.html", method = RequestMethod.GET)
    public String boxCommandOverview(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String communityUuid = ServletRequestUtils.getStringParameter(request, "communityUuid");
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        request.setAttribute("current", current);

        BoxCommandOverviewPaging paging = new BoxCommandOverviewPaging(boxInfoService);
        constructPaging(paging, current, communityUuid);
        List<BoxCommandDTO> commands = paging.getItems();
        model.put("commands", commands);
        model.put("paging", paging);

        CommunityDTO community = communityService.obtainCommunityByUuid(communityUuid, true);
        model.put("community", community);

        return "backend/box/commandoverview";
    }

    private void constructPaging(BoxCommandOverviewPaging paging, int current, String communityUuid) {
        paging.setCurrentPageNumber(current);
        paging.setCommunityUuid(communityUuid);
    }
}
