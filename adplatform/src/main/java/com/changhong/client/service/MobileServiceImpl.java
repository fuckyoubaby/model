package com.changhong.client.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.client.repository.MobileDao;
import com.changhong.client.web.controller.HandleResponse;
import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.common.utils.AesUtils;
import com.changhong.common.utils.CHPagingUtils;
import com.changhong.common.utils.CHStringUtils;
import com.changhong.common.utils.ValidationUtils;
import com.changhong.common.utils.excel.poi.tools.StringUtil;
import com.changhong.message.sms.content.SMSCodeSendThread;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.BoxCommand;
import com.changhong.system.domain.box.BoxCommandType;
import com.changhong.system.domain.box.BoxMac;
import com.changhong.system.domain.box.BoxMacStatus;
import com.changhong.system.domain.box.Community;
import com.changhong.system.domain.user.User;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.repository.BoxInfoDao;
import com.changhong.system.service.BoxInfoService;
import com.changhong.system.service.BoxMacService;
import com.changhong.system.web.facade.dto.BoxDTO;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 16-12-7
 * Time: 上午10:41
 */
@Service("mobileService")
public class MobileServiceImpl implements MobileService {

    @Autowired
    private MobileDao mobileDao;
    
    @Autowired
    private BoxInfoService boxInfoService;
    
    @Autowired
    private BoxMacService boxMacService;
    
    @Autowired
    private BoxInfoDao boxInfoDao;

    @Override
    public String handleMobileSMSSend(String json) {
        JSONObject result = new JSONObject();
        try {
            //先要判断BOX存在与否
            JSONObject body = new JSONObject();
            JSONObject parameters = JSONObject.parseObject(AesUtils.fixDecrypt(json));
            String mobile = parameters.getString("mobile");
            User user = mobileDao.findUserByUsername(mobile);

            if (user == null) {
                //用户内容信息不全
                result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(body.toJSONString()));
                HandleResponse.generateMoblileNotFoundResponse(result);
            } else {
                 //发送验证码
                String code = CHStringUtils.getRandomNumber(4);
                SMSCodeSendThread thread = new SMSCodeSendThread(mobile, code);
                ApplicationThreadPool.executeThread(thread);

                //设置验证吗
                user.updateActiviteInfo(code);

                result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(body.toJSONString()));
                HandleResponse.generateOKResponse(result);
            }
        } catch (Exception e) {
            ApplicationLog.error(MobileServiceImpl.class, "error", e);
            throw new RuntimeException("exception for method handleClientStatusReportAndCommandGet");
        }
        return result.toJSONString();
    }

    @Override
    public String handleMobileLogin(String json) {
        JSONObject result = new JSONObject();
        try {
            //先要判断BOX存在与否
            JSONObject body = new JSONObject();
            JSONObject parameters = JSONObject.parseObject(AesUtils.fixDecrypt(json));
            String mobile = parameters.getString("mobile");
            String code = parameters.getString("code");
            User user = mobileDao.findUserByUsername(mobile);

            result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(body.toJSONString()));
            if (user == null) {
                //用户内容信息不全
                HandleResponse.generateMoblileNotFoundResponse(result);
            } else {

                //设置验证吗
                boolean activiteInfoRight = user.isActiviteInfoRight(code);
                if (activiteInfoRight) {
                    HandleResponse.generateOKResponse(result);
                } else {
                    //用户内容信息不全
                    HandleResponse.generateMobileCodeOverdueResponse(result);
                }
            }
        } catch (Exception e) {
            ApplicationLog.error(MobileServiceImpl.class, "error", e);
            throw new RuntimeException("exception for method handleClientStatusReportAndCommandGet");
        }
        return result.toJSONString();
    }

    @Override
    public String handleMobileCommunitySearch(String json) {
        JSONObject result = new JSONObject();
        try {
            //先要判断BOX存在与否
            JSONObject body = new JSONObject();
            JSONObject parameters = JSONObject.parseObject(AesUtils.fixDecrypt(json));
            String words = parameters.getString("words");
            int number = parameters.getIntValue("number");

            if (number == 0) {
                //内容信息不全
                HandleResponse.generateDataNotFoundResponse(result);
                result.put(HandleResponse.RESPONSE_BODY, body.toJSONString());
            } else {
                //写入数据
                List<Community> communities = mobileDao.findCommunity(words, number);
                JSONArray array = new JSONArray();
                for (Community loop : communities) {
                    JSONObject community = new JSONObject();
                    community.put("id", loop.getUuid());
                    community.put("name", loop.getName());
                    community.put("location", loop.getArea().getFullAreaPath());
                    array.add(community);

                }
                body.put("communities", array);

                body.put("number", communities.size());

                result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(body.toJSONString()));
                HandleResponse.generateOKResponse(result);
            }
        } catch (Exception e) {
            ApplicationLog.error(MobileServiceImpl.class, "error", e);
            throw new RuntimeException("exception for method handleMobileCommunitySearch");
        }
        return result.toJSONString();
    }

    @Override
    public String handleMobileCommunityBox(String json) {
        JSONObject result = new JSONObject();
        try {
            //先要判断BOX存在与否
            JSONObject body = new JSONObject();
            JSONObject parameters = JSONObject.parseObject(AesUtils.fixDecrypt(json));
            String communityUuid = parameters.getString("communityUuid");
            int currentPage = parameters.getIntValue("currentPage");
            if (currentPage == 0) {
                currentPage = 1;
            }
            int pageSize = parameters.getIntValue("pageSize");
            if (pageSize == 0) {
                pageSize = 100;
            }

            if (!StringUtils.hasText(communityUuid)) {
                //内容信息不全
                HandleResponse.generateDataNotFoundResponse(result);
                result.put(HandleResponse.RESPONSE_BODY, body.toJSONString());
            } else {
                //写入数据
                JSONArray array = new JSONArray();
                int total = mobileDao.findBoxSizeByCommunityUuid(communityUuid);
                CHPagingUtils paging = new CHPagingUtils(total, currentPage, pageSize);

                /**添加小区盒子列表**/
                List<Box> boxes = mobileDao.findBoxByCommunityUuid(communityUuid, paging.getStartPosition(), paging.getMaxItems());  //根据小区ID获取盒子
                if (boxes != null) {
                    for (Box loop1 : boxes) {
                        JSONObject box = new JSONObject();
                        box.put("uuid", loop1.getUuid());
                        box.put("mac", loop1.getMac());
                        box.put("ssidName", loop1.getSsidName());
                        box.put("ssidPassword", loop1.getSsidPassword());
                        array.add(box);
                    }
                }

                body.put("boxes", array);
                body.put("total", total);
                body.put("currentPage", currentPage);

                result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(body.toJSONString()));
                HandleResponse.generateOKResponse(result);
            }
        } catch (Exception e) {
            ApplicationLog.error(MobileServiceImpl.class, "error", e);
            throw new RuntimeException("exception for method handleMobileCommunitySearch");
        }
        return result.toJSONString();
    }

	@Override
	public String handleMobileBoxAdd(String requestBody) {
		/**
		 * "body":{
		 *		"communityUuid":"123456789",
		 *		"boxUuid":"",
		 *		"mac":"9A.82.14.4C.D2.33",
		 *		"note":"1栋3单元",
		 *		"ssidName":"CH123",
		 *		"ssidPassword":"CH123"
		 *		}
		 */
		
		JSONObject result = new JSONObject();
		try{
			
			JSONArray responseBody = new JSONArray();
            JSONObject parameters = JSONObject.parseObject(AesUtils.fixDecrypt(requestBody));
            String[] necessaryFiled={"communityUuid","mac"};
            
            //必须参数检测
            necessaryCheck(necessaryFiled, parameters, responseBody);
           
            if(responseBody.size()>0){
            	//未通过必填项检测
            	result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(responseBody.toJSONString()));
            	HandleResponse.generateFailedResponse(result);
            }else{
            	String communityUuid = parameters.getString("communityUuid");
            	String mac = parameters.getString("mac").toUpperCase();
            	String note = parameters.getString("note");
            	String boxUuid = parameters.getString("boxUuid");
            	
            	
            	boolean isMacRight = ValidationUtils.isRightMacByReg(mac);
            	if(!isMacRight){
            		JSONObject temp= new JSONObject();
            		temp.put("fieldName", "mac");
            		temp.put("msg", "mac编号格式错误！");
					responseBody.add(temp);
					result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(responseBody.toJSONString()));
	            	HandleResponse.generateFailedResponse(result);
					return result.toJSONString();
            	}
            	
            	
            	if(StringUtils.hasText(boxUuid)){
            		//更新
            		 BoxDTO dto = boxInfoService.obtainBoxInfoByUuid(boxUuid);
            		if(dto!=null ){	
            			if(dto.getMac().equals(mac)){
            				JSONObject macObj = new JSONObject();
            				BoxMac boxmac = boxMacService.obtainByMac(mac);
            				if(boxmac==null){
            					macObj.put("fieldName", "mac");
            					macObj.put("msg", "mac编号不在库中, 请先添加MAC");
            					responseBody.add(macObj);
            				}else{
            					if(boxmac.getMacStatus()!=BoxMacStatus.B_USED){
                         			boxMacService.changeStatus(boxmac.getUuid(), BoxMacStatus.B_USED);
                         		}
            	        		dto.setNote(note);
            	        		
            	        		boxInfoService.changeBoxInfoDetails(dto);
            	        		result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(responseBody.toJSONString()));
            	        		HandleResponse.generateOKResponse(result);
            	        		return result.toJSONString();
            				}
            			}else{
            				
            				BoxMac boxmac = boxMacService.obtainByMac(mac);
            				
            				if(boxmac==null){
            					JSONObject macObj = new JSONObject();
            					macObj.put("fieldName", "mac");
            					macObj.put("msg", "mac编号不在库中, 请先添加MAC");
            					responseBody.add(macObj);
            				}else{
            					BoxMacStatus status = boxmac.getMacStatus();
                	        	if(status == BoxMacStatus.B_INITE){
                	        		dto.setMac(mac);
                	        		dto.setNote(note);
                	        		boxInfoService.changeBoxInfoDetails(dto);
                	        		
                	        		result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(responseBody.toJSONString()));
                	        		HandleResponse.generateOKResponse(result);
                	        		return result.toJSONString();
                	        	}else{
                	        		checkMacStatus(status, responseBody);
                	        	}
            				} // end if(boxmac ==null)
            			}
            		}else{
            			JSONObject boxUUidObj = new JSONObject();
            			boxUUidObj.put("fieldName", "boxUuid");
            			boxUUidObj.put("error", "无效的盒子UUID");
            			responseBody.add(boxUUidObj);
            		}
            		
            	}else{
            		//保存时处理逻辑
            		BoxDTO dto = new BoxDTO();
            		BoxMac boxmac = boxMacService.obtainByMac(mac);
    				
    				if(boxmac==null){
    					JSONObject macObj = new JSONObject();
    					macObj.put("fieldName", "mac");
    					macObj.put("msg", "mac编号不在库中, 请先添加MAC");
    					responseBody.add(macObj);
    				}else{
    					BoxMacStatus status = boxmac.getMacStatus();
        	        	if(status == BoxMacStatus.B_INITE){
        	        		dto.setMac(mac);
        	        		dto.setNote(note);
        	        		dto.setCommunityUuid(communityUuid);
        	        		boxInfoService.changeBoxInfoDetails(dto);
        	        		
        	        		result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(responseBody.toJSONString()));
        	        		HandleResponse.generateOKResponse(result);
        	        		return result.toJSONString();
        	        	}else{
        	        		checkMacStatus(status, responseBody);
        	        	}
    				}
            	}

            	result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(responseBody.toJSONString()));
            	HandleResponse.generateFailedResponse(result);
            }
		}catch(Exception e){
			ApplicationLog.error(MobileServiceImpl.class, "error", e);
			throw new RuntimeException("exception for method handleMobileBoxAdd");
		}
		
		return result.toJSONString();
	}
	
	private void necessaryCheck(String[] fields, JSONObject obj, JSONArray result){
		for(int i=0;i<fields.length;i++){
			String temp = obj.getString(fields[i]);
			if(!StringUtils.hasText(temp)){
				JSONObject tempObj = new JSONObject();
				tempObj.put("fieldName", fields[i]);
				tempObj.put("error", fields[i]+"不能为空");
				result.add(tempObj);
			}
		}
	}
	
	protected void checkMacStatus(BoxMacStatus status, JSONArray array){
		JSONObject macObj = new JSONObject();
		if(status == BoxMacStatus.B_USED){
			macObj.put("fieldName", "mac");
			macObj.put("msg", "mac编号已使用");
			array.add(macObj);
    	}else if(status == BoxMacStatus.B_DISABLE){
    		macObj.put("result", true);
    		macObj.put("msg", "mac编号状态不可用");
    		array.add(macObj);
    	}
	}

	@Override
	public String hanldeMobileBoxCommandAdd(String requestBody) {
		JSONObject result = new JSONObject();
		try{
			JSONArray responseBody = new JSONArray(); 
			String[] necessaryFiled={"type","mac"};
			
			
			String text = AesUtils.fixDecrypt(requestBody);
			JSONObject parameters = JSONObject.parseObject(text);
			
			necessaryCheck(necessaryFiled, parameters, responseBody);
			if(responseBody.size()>0){
				result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(responseBody.toJSONString()));
				HandleResponse.generateFailedResponse(result);
				return result.toJSONString();
			}
			
			JSONArray macs = parameters.getJSONArray("mac");
			String type = parameters.getString("type");
			String comment = parameters.getString("comment");
			String planTime = parameters.getString("planTime");
			if( macs.size()<1){
				JSONObject fieldErrors = new JSONObject();
				fieldErrors.put("fieldName", "mac");
				fieldErrors.put("error", "请选择设备");
				responseBody.add(fieldErrors);
			}else{
				if(StringUtils.hasText(type)){
					List<BoxCommand> commands = new ArrayList<BoxCommand>();
					BoxCommandType commandType;
					try{
						commandType = Enum.valueOf(BoxCommandType.class,type);
						String planFullTime;
						if(StringUtils.hasText(planTime)){
							planFullTime = new LocalDate().toString() + " " + planTime;
						}else planFullTime = planTime;
								
						for(Object s: macs){
							String mac = s.toString().trim().toUpperCase();
							if(StringUtils.hasText(mac)){
								BoxCommand command = new BoxCommand(mac, commandType, comment);
								 if (command.getCommandType().equals(BoxCommandType.C_RESOURCE_PLAN)) {
									 	if(StringUtils.hasText(planFullTime)){
									 		command.setPlanTime(planFullTime);
									 	}else{
									 		JSONObject temp = new JSONObject();
									 		temp.put("fieldName", "planTime");
									 		temp.put("error", "无效的计划时间");
											responseBody.add(temp);
									 		result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(responseBody.toJSONString()));
											HandleResponse.generateFailedResponse(result);
											return result.toJSONString();
									 	}
				                    } else {
				                        command.setPlanTime(null);
				                    }
								 commands.add(command);
							}
						}
						
						boxInfoDao.saveAll(commands);
						result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(responseBody.toJSONString()));
						HandleResponse.generateOKResponse(result);
						return result.toJSONString(); 
					}catch(Exception e){
						JSONObject fieldErrors = new JSONObject();
						fieldErrors.put("fieldName", "type");
						fieldErrors.put("error", "命令类型错误");
						responseBody.add(fieldErrors);
					}
				}else{
					JSONObject fieldErrors = new JSONObject();
					fieldErrors.put("fieldName", "type");
					fieldErrors.put("error", "请选择命令类型");
					responseBody.add(fieldErrors);
				}
			}
			
			result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(responseBody.toJSONString()));
			HandleResponse.generateFailedResponse(result);
		}catch(Exception e){
			ApplicationLog.error(MobileServiceImpl.class, "error", e);
			throw new RuntimeException("exception for method hanldeMobileBoxCommandAdd");
		}
		return result.toJSONString();
	}
}
