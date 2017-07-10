package com.changhong.client.service;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:24
 */
public interface ClientService {

    /************************************获取机顶盒秘钥，双方采用AES*************************************/

    String handleClientEncryptCodeUpdate(String json, String mac);

    /****************************************状态上报业务******************************************/

    String handleClientStatusReportAndCommandGet(String json, String mac);

    String handleClientCommandFinished(String json, String mac);

    /****************************************终端日志上报*****************************************/

    String handleClientLogReport(String json, String mac);
    
    /************************************ 终端日志上报, 保存上报状态码 **************************************/
    String handleClientLogSaveReport(String json, String mac);
}
