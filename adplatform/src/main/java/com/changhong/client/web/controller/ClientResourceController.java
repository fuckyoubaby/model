package com.changhong.client.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.changhong.client.service.ClientService;
import com.changhong.system.log.ApplicationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * User: Jack Wang
 * Date: 14-4-11
 * Time: 下午3:21
 */
@Controller
public class ClientResourceController extends AbstractResourceController {

    @Autowired
    private ClientService clientService;

    /********************************************广告部分***********************************************/

    @RequestMapping("/client/resource.html")
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String json = ServletRequestUtils.getStringParameter(request, "json", "");

        String responseJSON = "";
        try {
            if (!StringUtils.hasText(json)) {
                responseJSON = HandleResponse.generateDataNotFoundResponse().toJSONString();
            } else {
                JSONObject jsonObject = JSONObject.parseObject(json);
                String businessType = jsonObject.getString(HandleResponse.BUSINESS_TYPE);
                String requestBody = jsonObject.getString(HandleResponse.REQUEST_BODY);
                String requestMac = jsonObject.getString(HandleResponse.REQUEST_MAC).toUpperCase();

                /************************************判断是否输出日志**********************************************/

                if (!ClientBusiness.CLIENT_REPORT.equals(businessType)) {
                    ApplicationLog.info(ClientResourceController.class, json);
                }

                /************************************系统逻辑部分**************************************************/

                switch(businessType) {
                    case ClientBusiness.BOX_INITIAL:
                        responseJSON = clientService.handleClientEncryptCodeUpdate(requestBody, requestMac);
                        break;
                    case ClientBusiness.STATUS_SEND_COMMAND_GET:
                        responseJSON = clientService.handleClientStatusReportAndCommandGet(requestBody, requestMac);
                        break;
                    case ClientBusiness.COMMAND_FINISH:
                        responseJSON = clientService.handleClientCommandFinished(requestBody, requestMac);
                        break;
                     case ClientBusiness.CLIENT_REPORT:
                    	//下面的是: body json数组 每个项中未加入code字段后的解析
                       // responseJSON = clientService.handleClientLogReport(requestBody, requestMac);
                        //下面的是: body json数组 每个项中加入code字段后的解析
                        responseJSON = clientService.handleClientLogSaveReport(requestBody, requestMac);
                        break;
                    default:
                        responseJSON = HandleResponse.generateDataNotFoundResponse().toJSONString();
                        break;
                }
            }
        } catch (Exception e) {
            responseJSON = HandleResponse.generateFailedResponse().toJSONString();
        }

        //返回结果
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(responseJSON);
        writer.flush();
        writer.close();

        return null;
    }
}
