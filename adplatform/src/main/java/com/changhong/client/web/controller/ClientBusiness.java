package com.changhong.client.web.controller;

/**
 * User: Jack Wang
 * Date: 16-4-1
 * Time: 上午8:55
 */
public class ClientBusiness {

    /************************************获取机顶盒秘钥，双方采用AES*************************************/

    /**
     * 该业务主要含了下面的业务场景
     * <p>
     * 1 - 机顶盒获取秘钥，一天更新一次
     * 2 - 前端向终端返回系统配置元数据，这个元数据可能是系统默认的也可能是单个配置的
     * 3 - 检查是否有更新
     */
    public final static String BOX_INITIAL = "box_initial";

    /**************************************状态上报业务*****************************************/

    /**
     * 该业务主要含了下面的业务场景
     * <p>
     * 1 - 终端从前端发送机顶盒状态
     * 2 - 前端向中端返回系统控制命名，包括，重启，升级
     */
    public final static String STATUS_SEND_COMMAND_GET = "status_send_command_get";

    /**
     * 当前端下发了命令后，需要终端反馈控制是否成功
     */
    public final static String COMMAND_FINISH = "command_finished";

    /**************************************播放列表下发***************************************/

    /**
     * 该业务主要含了下面的业务场景
     * <p>
     * 1 - 终端向前端获取播放列表，终端有默认显示图片，在没有获取到播放列表显示，如果有的则按照播放列表
     *     如果一个终端匹配到两个播放，则获取配置时间较新的那个
     */
    public final static String AD_PLAY_LIST_GET = "ad_play_list_get";

    /**************************************播放数据上报***************************************/

    /**
     * 该业务主要含了下面的业务场景
     * <p>
     * 1 - 终端从前端发送广告播放的数据，业务可以10分钟一次，主要是播放广告的ID，播放的次数，本地先缓存，统一上报
     *     注意返回状态码
     */
    public final static String AD_PLAY_DETAILS_SEND = "ad_play_details_send";

    /**************************************终端日志上报***************************************/

    /**
     * 错误日志上报，1小时一次，固定周期上报
     */
    public final static String CLIENT_REPORT = "client_report";

}
