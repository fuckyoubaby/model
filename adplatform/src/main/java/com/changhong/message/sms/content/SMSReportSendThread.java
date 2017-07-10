package com.changhong.message.sms.content;

import java.util.List;

import com.changhong.common.web.application.ApplicationEventPublisher;
import com.changhong.message.sms.sender.HttpSMSTaobao;
import com.changhong.system.log.ApplicationLog;

/**
 * User: Jack Wang
 * Date: 16-7-16
 * Time: 下午3:29
 */
public class SMSReportSendThread implements Runnable {
	//电话号码
    private String mobile;
    //短信内容
    private String content;

    /**
     * 多个ID用|隔开
     */
    public SMSReportSendThread(String mobile, String content) {
        this.mobile = mobile;
        this.content = content;
    }

    @Override
    public void run() {
        HttpSMSTaobao sender = (HttpSMSTaobao) ApplicationEventPublisher.getBean("smsSender");
        boolean liveEnvironment = ApplicationEventPublisher.isLiveEnvironment();

        if (!liveEnvironment) {
            //调试环境时，不发送短信
        	ApplicationLog.info(SMSReportSendThread.class, "(not live environment) send sms message for user " + mobile + " successful.\n"+content);
        }else{
        	try {
                sender.sendMessage(mobile, content);
                ApplicationLog.info(SMSReportSendThread.class, "send sms message for user " + mobile + " successful");
            } catch (Exception e) {
                ApplicationLog.error(SMSReportSendThread.class, "send sms message for user " + mobile + " failed", e);
            }
        }
        
    }
}
