package com.changhong.message.sms.content;

import com.changhong.common.web.application.ApplicationEventPublisher;
import com.changhong.message.sms.sender.HttpSMSTaobao;
import com.changhong.system.log.ApplicationLog;

/**
 * User: Jack Wang
 * Date: 16-7-16
 * Time: 下午3:29
 */
public class SMSCodeSendThread implements Runnable {

    private String mobile;
    private String code;

    /**
     * 多个ID用|隔开
     */
    public SMSCodeSendThread(String mobile, String code) {
        this.mobile = mobile;
        this.code = code;
    }

    @Override
    public void run() {
        HttpSMSTaobao sender = (HttpSMSTaobao) ApplicationEventPublisher.getBean("smsSender");
        boolean liveEnvironment = ApplicationEventPublisher.isLiveEnvironment();

        if (!liveEnvironment) {
            mobile = ApplicationEventPublisher.getSmsDefaultTarget();
        }
        String content = SMSSendTemplateTemplate.generateLoginMessageContent(code);

        try {
            sender.sendMessage(mobile, content);
            ApplicationLog.info(SMSCodeSendThread.class, "send sms message for user " + mobile + " successful");
        } catch (Exception e) {
            ApplicationLog.error(SMSCodeSendThread.class, "send sms message for user " + mobile + " failed", e);
        }
    }
}
