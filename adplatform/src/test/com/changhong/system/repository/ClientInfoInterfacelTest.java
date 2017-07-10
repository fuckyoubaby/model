package com.changhong.system.repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changhong.client.web.controller.ClientBusiness;
import com.changhong.client.web.controller.HandleResponse;
import com.changhong.common.utils.AesUtils;
import com.changhong.common.utils.WebUtils;
import junit.framework.TestCase;
import org.apache.commons.httpclient.methods.PostMethod;
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
public class ClientInfoInterfacelTest extends TestCase {

    private static final String host = "http://localhost:8080/adplatform/client/resource.html";
//    private static final String host = "http://211.149.208.93:8080/adplatform/client/resource.html";

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testClientUpdateEncryptCode() {
        JSONObject request = new JSONObject();
        request.put(HandleResponse.APP_TYPE, "ANDROID");
        request.put(HandleResponse.APP_VERSION, "1.0");
        request.put(HandleResponse.BUSINESS_TYPE, ClientBusiness.BOX_INITIAL);
        request.put(HandleResponse.REQUEST_MAC, "66:B6:66:FA:44:79");

        JSONObject body = new JSONObject();
        body.put("version", "1.0");
        request.put(HandleResponse.REQUEST_BODY, AesUtils.fixEncrypt(body.toJSONString()));

        PostMethod postMethod = new PostMethod(host);
        System.out.println(request.toJSONString());
        postMethod.addParameter("json", request.toJSONString());

        String result = WebUtils.httpPostRequest(postMethod);
        JSONObject o = JSONObject.parseObject(result);
        System.out.println(result);

        JSONObject responseBody = JSONObject.parseObject(AesUtils.fixDecrypt(o.getString("body")));
        String code = responseBody.getJSONObject("encrypt").getString("code");
        System.out.println("code get is " + code);
    }

    @Test
    public void testClientReportStatus() {
        JSONObject request = new JSONObject();
        request.put(HandleResponse.APP_TYPE, "ANDROID");
        request.put(HandleResponse.APP_VERSION, "1.0");
        request.put(HandleResponse.BUSINESS_TYPE, ClientBusiness.STATUS_SEND_COMMAND_GET);
        request.put(HandleResponse.REQUEST_MAC, "66:B6:66:FA:44:79");

        JSONObject body = new JSONObject();
        body.put("version", "1.8");
        body.put("cpu", "90");
        body.put("mem", "90");
        body.put("disk", "90");
        request.put(HandleResponse.REQUEST_BODY, AesUtils.randomEncrypt(body.toJSONString(), "kyyo"));

        PostMethod postMethod = new PostMethod(host);
        System.out.println(request.toJSONString());
        postMethod.addParameter("json", request.toJSONString());

        String result = WebUtils.httpPostRequest(postMethod);
        System.out.println(result);
        JSONObject o = JSONObject.parseObject(result);
        String requestBody = AesUtils.randomDecrypt(o.getString("body"), "kyyo");
        System.out.println(requestBody);
    }
    
    

    @Test
    public void testClientCommandFinish() {
        JSONObject request = new JSONObject();
        request.put(HandleResponse.APP_TYPE, "ANDROID");
        request.put(HandleResponse.APP_VERSION, "1.0");
        request.put(HandleResponse.BUSINESS_TYPE, ClientBusiness.COMMAND_FINISH);
        request.put(HandleResponse.REQUEST_MAC, "11:11:11:11:11:12");

        JSONObject body = new JSONObject();
        body.put("id", "201702201806340912685");
        request.put(HandleResponse.REQUEST_BODY, AesUtils.randomEncrypt(body.toJSONString(), "1111"));

        PostMethod postMethod = new PostMethod(host);
        System.out.println(request.toJSONString());
        postMethod.addParameter("json", request.toJSONString());

        String result = WebUtils.httpPostRequest(postMethod);
        System.out.println(result);
    }

    @Test
    public void testClientLogReport() {
        String content = "java.lang.ArithmeticException: / by zero\n" +
                "\tat com.changhong.system.web.controller.DashboardController.handleRequestInternal(DashboardController.java:28)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)\n" +
                "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "\tat java.lang.reflect.Method.invoke(Method.java:601)\n" +
                "\tat org.springframework.web.bind.annotation.support.HandlerMethodInvoker.invokeHandlerMethod(HandlerMethodInvoker.java:176)\n" +
                "\tat org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter.invokeHandlerMethod(AnnotationMethodHandlerAdapter.java:440)\n" +
                "\tat org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter.handle(AnnotationMethodHandlerAdapter.java:428)\n" +
                "\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)\n" +
                "\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)\n" +
                "\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)\n" +
                "\tat org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:827)\n" +
                "\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:617)\n" +
                "\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)\n" +
                "\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:723)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:290)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)\n" +
                "\tat com.opensymphony.module.sitemesh.filter.PageFilter.parsePage(PageFilter.java:119)\n" +
                "\tat com.opensymphony.module.sitemesh.filter.PageFilter.doFilter(PageFilter.java:55)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:235)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)\n" +
                "\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:343)\n" +
                "\tat org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:109)\n" +
                "\tat org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:83)\n" +
                "\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:355)\n" +
                "\tat org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:97)\n" +
                "\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:355)\n" +
                "\tat org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:100)\n" +
                "\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:355)\n" +
                "\tat org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:78)\n" +
                "\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:355)\n" +
                "\tat org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:54)\n" +
                "\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:355)\n" +
                "\tat org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:35)\n" +
                "\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:355)\n" +
                "\tat org.springframework.security.web.authentication.www.BasicAuthenticationFilter.doFilter(BasicAuthenticationFilter.java:177)\n" +
                "\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:355)\n" +
                "\tat org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:187)\n" +
                "\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:355)\n" +
                "\tat org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:105)\n" +
                "\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:355)\n" +
                "\tat org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:79)\n" +
                "\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:355)\n" +
                "\tat org.springframework.security.web.session.ConcurrentSessionFilter.doFilter(ConcurrentSessionFilter.java:109)\n" +
                "\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:355)\n" +
                "\tat org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:149)\n" +
                "\tat org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:346)\n" +
                "\tat org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:259)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:235)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)\n" +
                "\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\n" +
                "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:235)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)\n" +
                "\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:233)\n" +
                "\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:191)\n" +
                "\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:127)\n" +
                "\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:103)\n" +
                "\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:109)\n" +
                "\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:293)\n" +
                "\tat org.apache.coyote.http11.Http11Processor.process(Http11Processor.java:861)\n" +
                "\tat org.apache.coyote.http11.Http11Protocol$Http11ConnectionHandler.process(Http11Protocol.java:606)\n" +
                "\tat org.apache.tomcat.util.net.JIoEndpoint$Worker.run(JIoEndpoint.java:489)\n" +
                "\tat java.lang.Thread.run(Thread.java:722)";

        long start = System.currentTimeMillis();

        JSONObject request = new JSONObject();
        request.put(HandleResponse.APP_TYPE, "ANDROID");
        request.put(HandleResponse.APP_VERSION, "1.0");
        request.put(HandleResponse.BUSINESS_TYPE, ClientBusiness.CLIENT_REPORT);
        request.put(HandleResponse.REQUEST_MAC, "11:11:11:11:11:12");

        JSONArray body = new JSONArray();
        JSONObject o = new JSONObject();
        o.put("desc", content);
        o.put("code","0x001001");
        body.add(o);
        request.put(HandleResponse.REQUEST_BODY, body.toJSONString());

        PostMethod postMethod = new PostMethod(host);
        System.out.println(request.toJSONString());
        postMethod.addParameter("json", request.toJSONString());

        String result = WebUtils.httpPostRequest(postMethod);
        System.out.println(result);

        long end = System.currentTimeMillis();
        long during = end - start;
        System.out.println("total spent " + during);
    }

}

