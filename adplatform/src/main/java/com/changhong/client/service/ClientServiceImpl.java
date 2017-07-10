package com.changhong.client.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.client.repository.ClientDao;
import com.changhong.client.web.controller.HandleResponse;
import com.changhong.common.thread.ApplicationThreadPool;
import com.changhong.common.utils.CHDateUtils;
import com.changhong.common.utils.AesUtils;
import com.changhong.common.web.application.ApplicationEventPublisher;
import com.changhong.message.sms.content.SMSReportSendThread;
import com.changhong.system.domain.box.Box;
import com.changhong.system.domain.box.BoxCommand;
import com.changhong.system.domain.box.BoxLog;
import com.changhong.system.domain.meta.MetaData;
import com.changhong.system.log.ApplicationLog;
import com.changhong.system.log.ClientLog;
import com.changhong.system.web.event.BoxReportEvent;
import com.changhong.system.web.facade.assember.BoxWebAssember;
import com.changhong.system.web.facade.dto.BoxDTO;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:24
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {

    @Value("${application.host}")
    private String applicationHost;

    @Value("${application.file.host}")
    private String applicationFileHost;

    @Autowired
    private ClientDao clientDao;

    /************************************获取机顶盒秘钥，双方采用AES*************************************/

    @Override
    public String handleClientEncryptCodeUpdate(String json, String mac) {
        JSONObject result = new JSONObject();
        try {
            //先要判断BOX存在与否
            Box box = clientDao.findBoxByMac(mac.toUpperCase());
            JSONObject body = new JSONObject();

            if (box == null) {
                //盒子内容信息不全
                result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(body.toJSONString()));
                HandleResponse.generateDataNotFoundResponse(result);
            } else {
                String text = AesUtils.fixDecrypt(json);
                JSONObject parameters = JSONObject.parseObject(text);
                String version = parameters.getString("version");

                //参数内容信息不全
                if (!StringUtils.hasText(version)) {
                    result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(body.toJSONString()));
                    HandleResponse.generateDataNotFoundResponse(result);
                } else {

                    //返回CODE
                    JSONObject codeJSON = new JSONObject();
                    boolean overdue = box.isEncryptCodeOverdue();
                    if (overdue) {
                        box.generateNewCode();
                    }
                    codeJSON.put("overdue", overdue);
                    codeJSON.put("code", box.getEncryptCode());
                    codeJSON.put("time", box.getCodeGenerateTime());
                    codeJSON.put("name", box.getNote());
                    codeJSON.put("ssidName", box.getSsidName());
                    codeJSON.put("ssidPassword", box.getSsidPassword());
                    body.put("encrypt", codeJSON);

                    //返回配置
                    JSONObject configureJSON = new JSONObject();
                    MetaData metaData = box.getCommunity().getMetaData();
                    configureJSON.put("start", metaData.getStartTime());
                    configureJSON.put("end", metaData.getEndTime());
                    configureJSON.put("heart", metaData.getHeartInterval());
                    body.put("configure", configureJSON);

                    result.put(HandleResponse.RESPONSE_BODY, AesUtils.fixEncrypt(body.toJSONString()));
                    HandleResponse.generateOKResponse(result);
                }
            }
        } catch (Exception e) {
            ApplicationLog.error(ClientServiceImpl.class, "error", e);
            throw new RuntimeException("exception for method handleClientStatusReportAndCommandGet");
        }
        return result.toJSONString();
    }

    /**************************************状态上报业务***************************************/

    @Override
    public String handleClientStatusReportAndCommandGet(String json, String mac) {
         JSONObject result = new JSONObject();
        try {
             //先要判断BOX存在与否
            Box box = clientDao.findBoxByMac(mac.toUpperCase());
            JSONObject body = new JSONObject();

            if (box == null) {
                //盒子内容信息不全
                result.put(HandleResponse.RESPONSE_BODY, body);
                HandleResponse.generateDataNotFoundResponse(result);
            }  else {

                String text = AesUtils.randomDecrypt(json,box.getEncryptCode());
                JSONObject parameters = JSONObject.parseObject(text);
                int cup = parameters.getIntValue("cpu");
                int mem = parameters.getIntValue("mem");
                int disk = parameters.getIntValue("disk");
                String version = parameters.getString("version");

                if (cup < 0 || mem <0 || disk <0 || !StringUtils.hasText(version)) {
                    //盒子内容信息不全
                    result.put(HandleResponse.RESPONSE_BODY, body);
                    HandleResponse.generateDataNotFoundResponse(result);
                } else {
                    //更新状态
                    box.updateStatus(cup, mem, disk, version);

                    //反馈控制命令
                    JSONObject commandJSON = new JSONObject();
                    BoxCommand command = clientDao.findBoxCommand(mac);
                    if (command != null) {
                    	clientDao.changeBoxCommandStatus(mac, command.getUuid());
                        commandJSON.put("id", command.getUuid());
                        commandJSON.put("code", command.getCommandType().name());
                        commandJSON.put("desc", command.getCommandType().getDescription());
                        commandJSON.put("time", CHDateUtils.getFullDateFormat(command.getTimestamp()));
                        body.put("command", commandJSON);
                    }

                    result.put(HandleResponse.RESPONSE_BODY, AesUtils.randomEncrypt(body.toJSONString(),box.getEncryptCode()));
                    HandleResponse.generateOKResponse(result);
                }
            }
        } catch (Exception e) {
            ApplicationLog.error(ClientServiceImpl.class, "error", e);
            throw new RuntimeException("exception for method handleClientStatusReportAndCommandGet");
        }
        return result.toJSONString();
    }

    @Override
    public String handleClientCommandFinished(String json, String mac) {
        JSONObject result = new JSONObject();
        try {
             //先要判断BOX存在与否
            Box box = clientDao.findBoxByMac(mac.toUpperCase());
            JSONObject body = new JSONObject();

            if (box == null) {
                //盒子内容信息不全
                result.put(HandleResponse.RESPONSE_BODY, body);
                HandleResponse.generateDataNotFoundResponse(result);
            }  else {
                String text = AesUtils.randomDecrypt(json,box.getEncryptCode());
                JSONObject parameters = JSONObject.parseObject(text);
                String id = parameters.getString("id");

                BoxCommand command = (BoxCommand) clientDao.findByUuid(id, BoxCommand.class);
                if (command == null) {
                    result.put(HandleResponse.RESPONSE_BODY, AesUtils.randomEncrypt(body.toJSONString(),box.getEncryptCode()));
                    HandleResponse.generateDataNotFoundResponse(result);
                } else {
                    command.setFinished(true);
                    result.put(HandleResponse.RESPONSE_BODY, AesUtils.randomEncrypt(body.toJSONString(),box.getEncryptCode()));
                    HandleResponse.generateOKResponse(result);
                }
            }
        } catch (Exception e) {
            ApplicationLog.error(ClientServiceImpl.class, "error", e);
            throw new RuntimeException("exception for method handleClientCommandFinished");
        }
        return result.toJSONString();
    }

    /****************************************终端日志上报*****************************************/

    @Override
    public String handleClientLogReport(String json, String mac) {
        JSONObject result = new JSONObject();
        try {
             //先要判断BOX存在与否
            Box box = clientDao.findBoxByMac(mac.toUpperCase());
            JSONObject body = new JSONObject();

            if (box == null) {
                //盒子内容信息不全
                result.put(HandleResponse.RESPONSE_BODY, AesUtils.randomEncrypt(body.toJSONString(),box.getEncryptCode()));
                HandleResponse.generateDataNotFoundResponse(result);
            }  else {
                ClientLog.error(mac, json);
                result.put(HandleResponse.RESPONSE_BODY, AesUtils.randomEncrypt(body.toJSONString(),box.getEncryptCode()));
                HandleResponse.generateOKResponse(result);
            }
        } catch (Exception e) {
            ApplicationLog.error(ClientServiceImpl.class, "error", e);
            throw new RuntimeException("exception for method handleClientLogReport");
        }
        return result.toJSONString();
    }

	@Override
	public String handleClientLogSaveReport(String json, String mac) {
		JSONObject result = new JSONObject();
        try {
             //先要判断BOX存在与否
            Box box = clientDao.findBoxByMac(mac.toUpperCase());
            
            ClientLog.error(mac, json);
            if (box == null) {
                //盒子内容信息不全
                result.put(HandleResponse.RESPONSE_BODY, "");
                HandleResponse.generateDataNotFoundResponse(result);
            }  else {
                //TODO save error report
                /**
                 * 1、save error report 
                 * 2、send mobile short message to maintenance worker
                 */
               // String text = AesUtils.randomDecrypt(json,box.getEncryptCode());
                JSONArray arrays = JSONArray.parseArray(json);
                List<String> codes = new ArrayList<String>();
                for(int i=0;i<arrays.size();i++){
                	JSONObject temp = (JSONObject) arrays.get(i);
                	codes.add(temp.getString("code"));
                }
                
                //异常日志批量保存的线程
                BoxReportEvent reportEvent = new BoxReportEvent(codes, mac);
                ApplicationThreadPool.executeThread(reportEvent);
                
                //异常日志短信发送的线程
                //1、收短信人的手机号
                	//TODO 配置在sms.properties 属性文件中， 修改sms.report.receiver值为短信接收者手机号
                //2、短信内容
                if(codes.size()>0){
                	SMSReportSendThread  reportSendEvent =  new SMSReportSendThread(ApplicationEventPublisher.getSmsReveiver(), buildSMContent(box, codes)); 
                	ApplicationThreadPool.executeThread(reportSendEvent);
                }
                
                result.put(HandleResponse.RESPONSE_BODY,"");
                HandleResponse.generateOKResponse(result);
            }
        } catch (Exception e) {
            ApplicationLog.error(ClientServiceImpl.class, "error", e);
            throw new RuntimeException("exception for method handleClientLogReport");
        }
        return result.toJSONString();
	}
    
	
	private String buildSMContent(Box box, List<String> code){
		BoxDTO dto = BoxWebAssember.toBoxDTO(box, true);
		String content = "位于"+dto.getCommunityPath()+dto.getNote()+"的设备["+box.getMac()+"]发生了(exceptions)异常";
		int len = code.size();
		boolean b = len>3?true:false;
		int max = len>3?3:len;
		String exceptions = code.get(0);
		for(int i=1;i<max;i++){
			exceptions = ", "+code.get(i);
		}
		if(b)exceptions+="...";
		return content.replace("exceptions", exceptions);
	}
    
}
