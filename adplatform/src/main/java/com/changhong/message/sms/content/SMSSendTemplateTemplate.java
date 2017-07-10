package com.changhong.message.sms.content;

/**
 * User: Jack Wang
 * Date: 16-8-22
 * Time: 上午9:02
 */
public class SMSSendTemplateTemplate {

    /************************************************验证码部分********************************************/

    private static final String LOGIN_ACTIVITE_CODE = "欢迎使用电梯广告平台，你本次登录验证码是XXXX，该验证码1小时内有效";

    public static String generateLoginMessageContent(String code) {
        return LOGIN_ACTIVITE_CODE.replace("XXXX", code);
    }
}
