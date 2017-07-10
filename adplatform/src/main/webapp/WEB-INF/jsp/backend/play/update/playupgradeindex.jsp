<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/htmlstyle.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">

    	function initDialogEvent(dialogId, url){
    		jQuery(dialogId).css("visibility", "visible");
    		jQuery(dialogId).dialog({
                resizable: false,
                height:160,
                width:300,
                modal: true,
                buttons: {
                    "确认": function() {
                        jQuery(dialogId).css("visibility", "hidden");
                        jQuery(this).dialog("close");
                        window.location.href = url;
                    },
                    "取消": function() {
                        jQuery(dialogId).css("visibility", "hidden");
                        jQuery(this).dialog("close");
                    }
                }
            });
    	}
    
        function deleteConfirm(uuid, filterName, current) {
        	var dialogId ="#ad-delete-dialog-confirm";
            var url ='${pageContext.request.contextPath}/backend/contentupgradedelete.html?contentUpgradeId=' + uuid + '&filterName=' + filterName + '&current=' + current;
            initDialogEvent(dialogId,url);
        };
        
        function statusConfirm(uuid, filterName, current, type) {
        	var dialogId ="#cu-status-dialog-confirm";
             if(type==1){
            	$(dialogId).find("span.status-process").text("禁用");
            }else{
            	$(dialogId).find("span.status-process").text("激活");
            } 
            var url ='${pageContext.request.contextPath}/backend/contentupgradechangestatus.html?contentUpgradeId=' + uuid + '&filterName=' + filterName + '&current=' + current;
			initDialogEvent(dialogId, url);
        };
        
        function publishConfirm(uuid, filterName, current) {
        	var dialogId ="#cu-publish-dialog-confirm";
            var url ='${pageContext.request.contextPath}/backend/contentupgradepublish.html?contentUpgradeId=' + uuid + '&filterName=' + filterName + '&current=' + current;
			initDialogEvent(dialogId, url);
        };

    </script>
</head>

<body>

<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/left.jsp"/>

<%--内容部分***********************************************************--%>

<div id="rightSide">

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/head.jsp"/>

    <%--页面按钮部分*******************************************************--%>

    <div class="nNote nInformation hideit">
        <p><strong>播放升级管理: </strong>对播放的升级进行添加，编辑和删除操作</p>
    </div>

    <div class="wrapper">
        <!-- Note -->
        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
                <h6>名称:</h6>
                <form action="${pageContext.request.contextPath}/backend/playcontentmanagement.html" method="POST" style="padding-top:4px;">
                    <input type="text" name="filterName" value="${paging.filterName}" style="width: 200px; height: 25px"/>&nbsp;
                    <input type="submit" value=" " name="find"/>
                </form>
                <a class="button greyishB" title="" href="#" style="position: absolute; top: 5px; right: 5px;"
                   onclick="window.location.href = '${pageContext.request.contextPath}/backend/contentupgradeform.html'">
                    <img class="icon" alt="" src="${pageContext.request.contextPath}/images/icons/light/add.png">
                    <span>添加新的升级</span>
                </a>
            </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                    <tr>
                        <td width="5%">#</td>
                        <td width="20%">名称</td>
                       	<td width="10%">状态</td>
                        <td width="15%">创建时间</td>
                        <td width="20%">发布时间</td>
                        <td >操作</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${contentUpgrades}" var="contentUpgrade" varStatus="counter">
                    <tr class="gradeX">
                        <td>${counter.count}</td>
                        <td>${contentUpgrade.name}</td>
                        <td align="center">
                        <c:choose>
                        	<c:when test="${contentUpgrade.enable}">
                        	有效&nbsp;&nbsp;<a class="button violetB" title="" href="#"
                               onclick="statusConfirm('${contentUpgrade.uuid}','${filterName}','${current}', 1)" >
                                <span>禁用</span>
                         	</a>
                        	</c:when>
                        	<c:otherwise>
                        	无效&nbsp;&nbsp;<a class="button greenB" title="" href="#"
                               onclick="statusConfirm('${contentUpgrade.uuid}','${filterName}','${current}', 2)" >
                                <span>激活</span>
                                </a>
                        	</c:otherwise>
                        </c:choose>
                        </td>
                        <td style="text-align: center;"><fmt:formatDate value="${contentUpgrade.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td style="text-align: center;">
                        <c:choose>
                        <c:when test="${(empty contentUpgrade.publishTime) && contentUpgrade.enable}">
                         <a class="button blueB" title="" href="#"
                               onclick="publishConfirm('${contentUpgrade.uuid}','${filterName}','${current}')" >
                                <span>现在发布</span>
                         </a>
                        </c:when>
                        <c:otherwise>
                        <fmt:formatDate value="${contentUpgrade.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </c:otherwise>
                        </c:choose>
                        </td>
                        
                        <td style="text-align: center;">
                            <a class="button blueB" title="" href="#"
                               onclick="window.location.href='${pageContext.request.contextPath}/backend/contentupgradeform.html?contentUpgradeId=${contentUpgrade.uuid}&filterName=${filterName}&current=${current}'" >
                                <span>编辑</span>
                            </a>
                           <a class="button blueB" title="" href="#" onclick="window.location.href='${pageContext.request.contextPath}/backend/contentupgradeinfo.html?contentUpgradeId=${contentUpgrade.uuid}&filterName=${filterName}&current=${current}'">
                                <span>详情</span>
                            </a>
							<a class="button redB" title="" href="#"
                                onclick="return deleteConfirm('${contentUpgrade.uuid}','${filterName}','${current}');" >
                                <span>删除</span>
                            </a>
                      </td>
                      
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tPagination" style="text-align: center;">
                <ul style="box-shadow: 0px 0px 0px;">
                    <ch:numberpaging urlMapping="${pageContext.request.contextPath}/backend/contentupgrademanagement.html" paging="${paging}"/>
                </ul>
            </div>
        </div>
    </div>

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>

</div>

<div id="ad-delete-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要删除此条?
    </p>
</div>

<div id="cu-status-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要<span class="status-process"></span>此条?
    </p>
</div>
<div id="cu-publish-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        确认后无法重置发布时间，请确认你是否发布?
    </p>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
</body>
</html>