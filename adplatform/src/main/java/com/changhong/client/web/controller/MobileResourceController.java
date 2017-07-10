package com.changhong.client.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.changhong.client.service.ClientService;
import com.changhong.client.service.MobileService;
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
public class MobileResourceController extends AbstractResourceController {

    @Autowired
    private MobileService mobileService;

    /********************************************手机端部分***********************************************/

    @RequestMapping("/mobile/resource.html")
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

                 /************************************判断是否输出日志**********************************************/

                ApplicationLog.info(MobileResourceController.class, json);

                /************************************系统逻辑部分**************************************************/
                
                switch(businessType) {
                    case MobileBusiness.MOBILE_SMS_SEND:
                        responseJSON = mobileService.handleMobileSMSSend(requestBody);
                        break;
                    case MobileBusiness.MOBILE_LOGIN:
                        responseJSON = mobileService.handleMobileLogin(requestBody);
                        break;
                    case MobileBusiness.MOBILE_COMMUNITY_SEARCH:
                        responseJSON = mobileService.handleMobileCommunitySearch(requestBody);
                        break;
                    case MobileBusiness.MOBILE_COMMUNITY_BOX:
                        responseJSON = mobileService.handleMobileCommunityBox(requestBody);
                        break;
                    case MobileBusiness.MOBILE_BOX_ADD:
                    	responseJSON = mobileService.handleMobileBoxAdd(requestBody);
                    	break;
                    case MobileBusiness.MOBILE_BOX_COMMAND:
                    	responseJSON = mobileService.hanldeMobileBoxCommandAdd(requestBody);
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
