package com.changhong.message.sms.sender;

import com.changhong.system.log.ApplicationLog;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Assert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("smsSender")
public class HttpSMSTaobao implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.assertNotNull("url can't be null", baseUrl);
        Assert.assertNotNull("username can't be null", username);
        Assert.assertNotNull("password can't be null", password);
    }

    @Value("${sms.url}")
    private String baseUrl;
    @Value("${sms.username}")
    private String username;
    @Value("${sms.password}")
    private String password;

    public HttpSMSTaobao() {
    }

    public HttpSMSTaobao(String url, String username, String password) {
        this.baseUrl = url;
        this.username = username;
        this.password = password;
    }

    public boolean sendMessage(String phoneNUM, String smsContent) {
        HttpClient client = new HttpClient();
        client.setConnectionTimeout(10000);
        client.getParams().setContentCharset("gbk");

        PostMethod postMethod = new PostMethod(baseUrl);
        postMethod.addParameter("method", "sendSMS");
        postMethod.addParameter("username", username);
        String pwdEncode = Base64.encodeToString(password.trim().getBytes(), Base64.NO_WRAP);
        postMethod.addParameter("password", pwdEncode);
        postMethod.addParameter("smstype", "1");
        postMethod.addParameter("mobile", phoneNUM);
        postMethod.addParameter("content", smsContent);
        postMethod.addParameter("isLongSms", "0");
        postMethod.addParameter("extenno", "");

        int status = 0;
        String response = "";
        try {
            status = client.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
            } else {
                response = "failed";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            postMethod.releaseConnection();
        }

        ApplicationLog.info(HttpSMSTaobao.class, "send sms to " + phoneNUM + " with status " + response);
        return response.startsWith("success");
    }

    public static void main(String[] args) throws Exception {
        HttpSMSTaobao sms = new HttpSMSTaobao("http://61.147.98.117:9001/servlet/UserServiceAPI", "13881153014", "800116");
        sms.sendMessage("13880933821", "你好");
    }
}
