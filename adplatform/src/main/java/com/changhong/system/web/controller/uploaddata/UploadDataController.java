package com.changhong.system.web.controller.uploaddata;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.common.utils.AesUtils;
import com.changhong.system.domain.advertisment.AdverResource;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.uploaddata.UploadADData;
import com.changhong.system.service.AdverResourceService;
import com.changhong.system.service.BoxInfoService;
import com.changhong.system.service.UploadADDataService;
import com.changhong.system.web.facade.dto.AdDataDTO;
import com.changhong.system.web.facade.dto.BoxDTO;

/**
 * 终端播放数据上传接口
 * @author shijinxiang
 *
 */
@Controller
public class UploadDataController {

	@Autowired
	private UploadADDataService uploadADDataService;
	@Autowired
	private AdverResourceService adverResourceService;
	@Autowired
	private BoxInfoService boxInfoService;
	
	@RequestMapping("/upload/uploadData.html")
	public void uploadData(HttpServletRequest request, HttpServletResponse response,String json) throws IOException
	{	
		
		response.setContentType("application/json;charset=utf-8");
		Writer writer = response.getWriter();
		JSONObject resultObj = new JSONObject();
		
		if (json==null||json.equals("")) {
			resultObj.put("message", "json为空");
			resultObj.put("status", 1003);
			writer.write(resultObj.toJSONString());
			writer.flush();
			writer.close();
			return;
		}
		
		//解析json
		JSONObject object = JSONObject.parseObject(json);
		String mac = "";
		String body = "";
		
		String [] necessaryKeys1 = {"mac","body"};
		checkNecessary(necessaryKeys1, object, resultObj);
		
		if(resultObj.containsKey("message")){
			writer.write(resultObj.toJSONString());
			writer.flush();
			writer.close();
			return;
		}
		
		mac = object.getString("mac");
		body = object.getString("body");
		
		Box box = boxInfoService.obtainBox(mac);
		if(box == null){
			resultObj.put("message", "无效的mac地址");
			resultObj.put("status", 1003);
			writer.write(resultObj.toJSONString());
			writer.flush();
			writer.close();
			return;
		}
		
		
		if(!StringUtils.hasText(box.getEncryptCode())){
			resultObj.put("message", "box 动态密码为空");
			resultObj.put("status", 1001);
			writer.write(resultObj.toJSONString());
			writer.flush();
			writer.close();
			return;
		}
		
		String boxResult = AesUtils.randomDecrypt(body, box.getEncryptCode());
		
		if(boxResult==null){
			resultObj.put("message", "解密失败");
			resultObj.put("status", 1001);
			writer.write(resultObj.toJSONString());
			writer.flush();
			writer.close();
			return;
		}
		
		//解析body
		JSONObject jsonObject = JSONObject.parseObject(boxResult);
		Date uploadDate = null;
		

		String [] necessaryKeys2 = {"date","playList"};
		checkNecessary(necessaryKeys2, jsonObject, resultObj);
		if(resultObj.containsKey("message")){
			writer.write(resultObj.toJSONString());
			writer.flush();
			writer.close();
			return;
		}
		
		uploadDate = jsonObject.getDate("date");
		
		String fileList = jsonObject.getString("playList");
		//解析play
		JSONArray jsonArray = JSONArray.parseArray(fileList);
			
		if(jsonArray.size()<1){
			resultObj.put("message", "播放列表参数为空");
			resultObj.put("status", 1001);
			writer.write(resultObj.toJSONString());
			writer.flush();
			writer.close();
			return;
		}
			
		List<UploadADData> datas = new ArrayList<UploadADData>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject file = jsonArray.getJSONObject(i);
			
			int times = 0;
			double duration= 0;
			UploadADData uploadADData = new UploadADData();
		
			uploadADData.setUploadDate(uploadDate);
			uploadADData.setMacNumber(mac);
			
			if (file.containsKey("uuid")) {
				String uuid= file.getString("uuid");
				AdverResource adverResource = adverResourceService.obtainAdverResourceByUuid(uuid);
				//无效资源，跳过
				if(adverResource==null) continue;
				uploadADData.setAdverResource(adverResource);
			}else continue;
			
			if (file.containsKey("times")) {
				times = file.getIntValue("times");
				uploadADData.setTimes(times);
			}else continue;
			if (file.containsKey("duration")) {
				duration = file.getDoubleValue("duration");
				uploadADData.setDuration(new BigDecimal(duration));
			}else continue;
			uploadADData.setTotalTime(new BigDecimal(times*duration));
			datas.add(uploadADData);
		}
		
		uploadADDataService.saveADDate(datas);
		
		resultObj.put("message", "处理成功");
		resultObj.put("status", 1000);
		writer.write(resultObj.toJSONString());
		writer.flush();
		writer.close();
		
	}
	@RequestMapping("/backend/ajaxRequestUploadADData.html")
	public String ajaxRequestUploadADData(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		   String areaId = ServletRequestUtils.getStringParameter(request, "areaId","");
		   int startPosition = ServletRequestUtils.getIntParameter(request, "startPosition", 0);
		   int size = ServletRequestUtils.getIntParameter(request, "size", 10);
		   int amount = 0;
		   //List<BoxDTO> boxes = null;
		   List<BoxDTO> list = uploadADDataService.obtainBoxDTOsByAreaId(areaId, startPosition, size);
		   amount = uploadADDataService.obtainCountByAreaId(areaId);
		   model.put("uploadAdDatas", list);
		   model.put("itemcount",amount);
		   return "backend/app/template/uploadaddatatemplate";
	}
	
	@RequestMapping("/backend/getuploadaddata.html")
	public String getAdData(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws ParseException, ServletRequestBindingException{
		String mac = ServletRequestUtils.getStringParameter(request, "mac");
		String startDateString = ServletRequestUtils.getStringParameter(request, "startDate");
		String endDateString = ServletRequestUtils.getStringParameter(request, "endDate");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		Date startDate;
		Date endDate;
		if (startDateString!=null&&!startDateString.equals("")) {
			startDate = sdf.parse(startDateString);
		}else {
			
			Calendar calendar = Calendar.getInstance();
			   calendar.add(Calendar.MONTH, -3);//得到前3个月
			   Date  formNow3Month = calendar.getTime();
			
			startDate = formNow3Month;
		}
		
		if (endDateString!=null&&!endDateString.equals("")) {
			endDate = sdf.parse(endDateString);
		}else {
			endDate = new Date();
		}
		List<AdDataDTO> list = uploadADDataService.obtainAdDataDTOs(mac, startDate, endDate);
		
		List<String> nameList = new ArrayList<>();
		List<Double> lengthList = new ArrayList<>();
		List<Integer> timesList = new ArrayList<>();
		
		for (int i = 0; i < list.size(); i++) {
			nameList.add(list.get(i).getResourceName());
			lengthList.add(list.get(i).getLength());
			timesList.add(list.get(i).getTimes());
		}
		
		List<String> nList = new ArrayList<>();
		for (int i = 0; i < nameList.size(); i++) {
			nList.add("'"+nameList.get(i)+"'");
		}
		model.put("adDatas", list);
		model.put("nameList",nList );
		model.put("mac", mac);
		//model.put("nameArray", jsonArray.toJSONString());
		model.put("lengthList", lengthList);
		model.put("timesList", timesList);
		return "backend/statistic/uploadaddatashow";
	}
	
	private void checkNecessary(String[] checkKeys, JSONObject json, JSONObject result){
		for(int i=0;i<checkKeys.length;i++){
			if(!json.containsKey(checkKeys[i])){
				result.put("message", checkKeys[i]+"为空");
				result.put("status", 1003);
				return;
			}
		}
		
	}
}
