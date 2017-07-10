package com.changhong.system.repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.client.web.controller.ClientBusiness;
import com.changhong.client.web.controller.HandleResponse;
import com.changhong.client.web.controller.MobileBusiness;
import com.changhong.common.utils.AesUtils;
import com.changhong.common.utils.WebUtils;
import com.changhong.system.domain.box.BoxCommandType;

import junit.framework.TestCase;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: Jack Wang
 * Date: 14-11-20
 * Time: 上午8:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
public class MobileInfoInterfacelTest extends TestCase {

    private static final String host = "http://localhost:8080/adplatform/mobile/resource.html";
//    private static final String host = "http://211.149.208.93:8080/adplatform/client/resource.html";

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

   //@Test
    public void testMobileSendSms() {
        JSONObject request = new JSONObject();
        request.put(HandleResponse.APP_TYPE, "ANDROID");
        request.put(HandleResponse.APP_VERSION, "1.0");
        request.put(HandleResponse.BUSINESS_TYPE, MobileBusiness.MOBILE_SMS_SEND);

        JSONObject body = new JSONObject();
        body.put("mobile", "13880933821");
        request.put(HandleResponse.REQUEST_BODY, AesUtils.fixEncrypt(body.toJSONString()));

        PostMethod postMethod = new PostMethod(host);
        System.out.println(request.toJSONString());
        postMethod.addParameter("json", request.toJSONString());

        String result = WebUtils.httpPostRequest(postMethod);
        JSONObject o = JSONObject.parseObject(result);
        System.out.println(result);

        JSONObject responseBody = JSONObject.parseObject(AesUtils.fixDecrypt(o.getString("body")));
        System.out.println("code get is " + responseBody);
    }

   // @Test
    public void testMobileLogin() {
        JSONObject request = new JSONObject();
        request.put(HandleResponse.APP_TYPE, "ANDROID");
        request.put(HandleResponse.APP_VERSION, "1.0");
        request.put(HandleResponse.BUSINESS_TYPE, MobileBusiness.MOBILE_LOGIN);

        JSONObject body = new JSONObject();
        body.put("mobile", "13880933821");
        body.put("code", "4002");
        request.put(HandleResponse.REQUEST_BODY, AesUtils.fixEncrypt(body.toJSONString()));

        PostMethod postMethod = new PostMethod(host);
        System.out.println(request.toJSONString());
        postMethod.addParameter("json", request.toJSONString());

        String result = WebUtils.httpPostRequest(postMethod);
        JSONObject o = JSONObject.parseObject(result);
        System.out.println(result);

        JSONObject responseBody = JSONObject.parseObject(AesUtils.fixDecrypt(o.getString("body")));
        System.out.println("code get is " + responseBody);
    }

     //@Test
    public void testMobileSearchCommunity() throws Exception {
        JSONObject request = new JSONObject();
        request.put(HandleResponse.APP_TYPE, "ANDROID");
        request.put(HandleResponse.APP_VERSION, "1.0");
        request.put(HandleResponse.BUSINESS_TYPE, MobileBusiness.MOBILE_COMMUNITY_SEARCH);

        JSONObject body = new JSONObject();
        body.put("words", "长虹");
        body.put("number", "10");
        request.put(HandleResponse.REQUEST_BODY, AesUtils.fixEncrypt(body.toJSONString()));

        PostMethod postMethod = new PostMethod(host);
        System.out.println(request.toJSONString());
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_URI_CHARSET, "UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_ELEMENT_CHARSET, "UTF-8");
        postMethod.addParameter("json", request.toJSONString());

        String result = WebUtils.httpPostRequest(postMethod);
        JSONObject o = JSONObject.parseObject(result);
        System.out.println(result);

        JSONObject responseBody = JSONObject.parseObject(AesUtils.fixDecrypt(o.getString("body")));
        System.out.println("code get is " + responseBody);
    }

   //  @Test
    public void testMobileSearchCommunityBox() throws Exception {
        JSONObject request = new JSONObject();
        request.put(HandleResponse.APP_TYPE, "ANDROID");
        request.put(HandleResponse.APP_VERSION, "1.0");
        request.put(HandleResponse.BUSINESS_TYPE, MobileBusiness.MOBILE_COMMUNITY_BOX);

        JSONObject body = new JSONObject();
        body.put("communityUuid", "201703291355503180290");
        body.put("currentPage", "1");
        body.put("pageSize", "100");
        request.put(HandleResponse.REQUEST_BODY, AesUtils.fixEncrypt(body.toJSONString()));

        PostMethod postMethod = new PostMethod(host);
        System.out.println(request.toJSONString());
        postMethod.addParameter("json", request.toJSONString());

        String result = WebUtils.httpPostRequest(postMethod);
        JSONObject o = JSONObject.parseObject(result);
        System.out.println(result);

        JSONObject responseBody = JSONObject.parseObject(AesUtils.fixDecrypt(o.getString("body")));
        System.out.println("content get is " + responseBody);
    }
     
     @Test
     public void testMobileBoxAdd(){
    	 JSONObject request = new JSONObject();
         request.put(HandleResponse.APP_TYPE, "ANDROID");
         request.put(HandleResponse.APP_VERSION, "1.0");
         request.put(HandleResponse.BUSINESS_TYPE, MobileBusiness.MOBILE_BOX_ADD);

         JSONObject body = new JSONObject();
         body.put("communityUuid", "201702221651198341315");
         body.put("boxUuid", "");
         body.put("note", "1栋1单元1号");
         body.put("mac", "66:B6:66:FA:44:83");
         request.put(HandleResponse.REQUEST_BODY, AesUtils.fixEncrypt(body.toJSONString()));

         PostMethod postMethod = new PostMethod(host);
         System.out.println(request.toJSONString());
         postMethod.addParameter("json", request.toJSONString());

         String result = WebUtils.httpPostRequest(postMethod);
         JSONObject o = JSONObject.parseObject(result);
         System.out.println(result);

         JSONArray responseBody = JSONArray.parseArray(AesUtils.fixDecrypt(o.getString("body")));
         System.out.println("code get is " + responseBody);
         
         
    	 
     }
     
    // @Test
     public void testMobileBoxCommandAdd(){
    	 JSONObject request = new JSONObject();
         request.put(HandleResponse.APP_TYPE, "ANDROID");
         request.put(HandleResponse.APP_VERSION, "1.0");
         request.put(HandleResponse.BUSINESS_TYPE, MobileBusiness.MOBILE_BOX_COMMAND);
         
         JSONArray macs = new JSONArray();
         macs.add("66:B6:66:FA:44:M3");
         
         JSONObject body = new JSONObject();
         //body.put("mac", macs);
         body.put("type", BoxCommandType.C_RESOURCE_PLAN);
         body.put("comment", "");
         //planTime可为空字符串，只有为资源计划时，字值段才会用到
         body.put("planTime", "09:15:00");
         System.out.println("body is: "+body.toJSONString());
         request.put(HandleResponse.REQUEST_BODY, AesUtils.fixEncrypt(body.toJSONString()));

         PostMethod postMethod = new PostMethod(host);
         System.out.println(request.toJSONString());
         postMethod.addParameter("json", request.toJSONString());

         String result = WebUtils.httpPostRequest(postMethod);
         JSONObject o = JSONObject.parseObject(result);
         System.out.println(result);

         JSONArray responseBody = JSONArray.parseArray(AesUtils.fixDecrypt(o.getString("body")));
         System.out.println("code get is " + responseBody);
     }
}

