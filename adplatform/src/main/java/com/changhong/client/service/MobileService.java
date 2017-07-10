package com.changhong.client.service;

/**
 * User: Jack Wang
 * Date: 16-12-7
 * Time: 上午10:40
 */
public interface MobileService {

    String handleMobileSMSSend(String requestBody);

    String handleMobileLogin(String requestBody);

    String handleMobileCommunitySearch(String requestBody);

    String handleMobileCommunityBox(String requestBody);
    
    String handleMobileBoxAdd(String requestBody);
    
    /**
     * 处理手机端投影仪控制命令添加
     * @param requestBody 请求正文(body键的值)，需解密
     * @return
     */
    String hanldeMobileBoxCommandAdd(String requestBody);
}
