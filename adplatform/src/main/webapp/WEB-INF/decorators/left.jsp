<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>

<div id="leftSide">

    <div class="logo">
        <img alt="" src="${pageContext.request.contextPath}/images/logo.png" height="40" width="136">
        电梯广告资源管理平台
    </div>

    <div class="sidebarSep"></div>

    <!-- Left navigation -->
    <ul id="menu" class="nav">
        <li class="dash">
            <a href="${pageContext.request.contextPath}/backend/dashboard.html" title=""
               <c:if test="${MENU_KEY == 'DASHBOARD'}">class="active"</c:if>>
                <span>首页</span>
            </a>
        </li>

        <li class="files">
            <a href="#" title="" class="<c:if test="${MENU_KEY == 'ERROR_MANAGE'}">active</c:if> exp"
               <c:if test="${MENU_KEY == 'ERROR_MANAGE'}">id="current"</c:if>>
                <span>投影仪管理</span>
            </a>
            <ul class="sub">
                 <li <c:if test="${SUB_MENU_KEY == 'MAC_MANAGE'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/boxmacindex.html" title="">投影仪MAC库管理</a>
                </li>
                <li <c:if test="${SUB_MENU_KEY == 'AREA_MANAGE'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/areamanage.html" title="">区域、投影仪管理</a>
                </li>
                <li <c:if test="${SUB_MENU_KEY == 'REPORT_MANAGE'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/boxreportmanage.html" title="">投影仪异常管理</a>
                </li>
                <li <c:if test="${SUB_MENU_KEY == 'NOT_ONLINE_MANAGE'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/notonlinemanage.html" title="">未上线投影仪管理</a>
                </li>
            </ul>
        </li>

        <li class="typo">
            <a href="#" title="" class="<c:if test="${MENU_KEY == 'AD_MANAGE'}">active</c:if> exp"
               <c:if test="${MENU_KEY == 'AD_MANAGE'}">id="current"</c:if>>
                <span>广告管理</span>
            </a>
            <ul class="sub">
   				<li <c:if test="${SUB_MENU_KEY == 'PLAY_LIST_MANAGE'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/playcontentmanagement.html" title="">广告播放内容管理</a>
                </li>
                <li <c:if test="${SUB_MENU_KEY == 'PLAY_UPGRADE_MANAGE'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/contentupgrademanagement.html" title="">广告播放配置管理</a>
                </li>
            </ul>
        </li>

       <!--更改图标 -->
       <li class="product">
            <a href="#" title="" class="<c:if test="${MENU_KEY == 'RES_MANAGE'}">active</c:if> exp"
               <c:if test="${MENU_KEY == 'RES_MANAGE'}">id="current"</c:if>>
                <span>升级管理</span>
            </a>
            <ul class="sub">
                <li <c:if test="${SUB_MENU_KEY == 'APP_MANAGE'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/appmanagement.html" title="">终端应用管理</a>
                </li>
                <li <c:if test="${SUB_MENU_KEY == 'APP_PHONE_MANAGE'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/appphonemanagement.html" title="">手机应用管理</a>
                </li>
                <li <c:if test="${SUB_MENU_KEY == 'MANAGE_STRATEGY'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/managestrategy.html" title="">终端升级策略</a>
                </li>
                <li <c:if test="${SUB_MENU_KEY == 'MANAGE_HISTORY'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/areacommunitybox.html" title="">终端升级历史</a>
                </li>
            </ul>
        </li>

        <li class="statistic">
            <a href="#" title="" class="<c:if test="${MENU_KEY == 'STA_MANAGE'}">active</c:if> exp"
               <c:if test="${MENU_KEY == 'STA_MANAGE'}">id="current"</c:if>>
                <span>统计管理</span>
            </a>
            <ul class="sub">
                <li <c:if test="${SUB_MENU_KEY == 'STA_NUMBER'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/boxnumberstatistic.html" title="">投影仪数量、在线统计</a>
                </li>

                <li <c:if test="${SUB_MENU_KEY == 'AD_TIME'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/adtimestatistic.html" title="">广告资源播放统计</a>
                </li>
        		 <li <c:if test="${SUB_MENU_KEY == 'RESOURCE_SHOW'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/static/adresourcespacestatistic.html" title="">广告资源统计</a>
                </li>
                <li <c:if test="${SUB_MENU_KEY == 'VERSION_NUMBER'}">class="this"</c:if>>
                    <a href="${pageContext.request.contextPath}/backend/appversionsta.html" title="">应用版本统计</a>
                </li>
            </ul>
       	</li>
       	

       		
        <security:authorize ifAnyGranted="ROLE_ADMIN">
            <li class="setting">
                <a href="#" title="" class="<c:if test="${MENU_KEY == 'SETTING_MANAGE'}">active</c:if> exp"
                   <c:if test="${MENU_KEY == 'SETTING_MANAGE'}">id="current"</c:if>>      <!--id="current"也是为了改变样式  -->
                    <span>系统设置</span>
                </a>
                <ul class="sub">
                    <li <c:if test="${SUB_MENU_KEY == 'USER_MANAGE'}">class="this"</c:if>>
                        <a href="${pageContext.request.contextPath}/backend/usermanagement.html" title="">系统用户管理</a>
                    </li>
                    <li <c:if test="${SUB_MENU_KEY == 'META_MANAGE'}">class="this"</c:if>>
                        <a href="${pageContext.request.contextPath}/backend/metadatamanagement.html" title="">投影仪元数据管理</a>
                    </li>
                    <li <c:if test="${SUB_MENU_KEY == 'LOG_MANAGE'}">class="this"</c:if>>
                        <a href="${pageContext.request.contextPath}/backend/actionlogmanagement.html" title="">系统日志管理</a>
                    </li>
                </ul>
            </li>
        </security:authorize>
    </ul>
</div>
