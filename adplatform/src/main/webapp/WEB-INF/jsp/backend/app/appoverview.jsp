<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html, charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/htmlstyle.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        function appDeleteConfirm(appUuid, filterName, current) {
            jQuery("#userenable-dialog-confirm").css("visibility", "visible");
            jQuery("#userenable-dialog-confirm").dialog({
                    resizable: false,
                    height:160,
                    width:300,
                    modal: true,
                    buttons: {
                        "确认": function() {
                            jQuery("#userenable-dialog-confirm").css("visibility", "hidden");
                            jQuery(this).dialog("close");
                            window.location.href = '${pageContext.request.contextPath}/backend/appstatus.html?appUuid=' + appUuid + '&filterName=' + filterName + '&current=' + current;
                        },
                        "取消": function() {
                            jQuery("#userenable-dialog-confirm").css("visibility", "hidden");
                            jQuery(this).dialog("close");
                        }
                    }
                });
        }

        function appEnableConfirm(appUuid, filterName, current) {
            jQuery("#userdisable-dialog-confirm").css("visibility", "visible");
            jQuery("#userdisable-dialog-confirm").dialog({
                    resizable: false,
                    height:160,
                    width:300,
                    modal: true,
                    buttons: {
                        "确认": function() {
                            jQuery("#userdisable-dialog-confirm").css("visibility", "hidden");
                            jQuery(this).dialog("close");
                            window.location.href = '${pageContext.request.contextPath}/backend/appstatus.html?appUuid=' + appUuid + '&filterName=' + filterName + '&current=' + current;
                        },
                        "取消": function() {
                            jQuery("#userdisable-dialog-confirm").css("visibility", "hidden");
                            jQuery(this).dialog("close");
                        }
                    }
                });
        }
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
        <p><strong>终端应用管理: </strong>对终端应用进行添加，编辑和删除操作</p>
    </div>

    <div class="wrapper">
        <!-- Note -->


        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
                <h6>应用名:</h6>
                <form action="${pageContext.request.contextPath}/backend/appmanagement.html" method="POST" style="padding-top:4px;">
                    <input type="text" name="filterName" value="${paging.filterName}" style="width: 200px; height: 25px"/>&nbsp;
                    <input type="submit" value=" " name="find"/>
                </form>
                <a class="button greyishB" title="" href="#" style="position: absolute; top: 5px; right: 5px;"
                   onclick="window.location.href = '${pageContext.request.contextPath}/backend/appform.html'">
                    <img class="icon" alt="" src="${pageContext.request.contextPath}/images/icons/light/add.png">
                    <span>新增应用</span> 
                </a>
            </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                    <tr>
                        <td width="5%">#</td>
                        <td width="15%">应用名称</td>
                        <td width="15%">类型</td>
                        <td width="15%">应用版本</td>
                        <td width="25%">版本信息</td>
                        <td width="10%">状态</td>
                        <td >操作</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${Apps}" var="appManage" varStatus="counter">
                    <tr class="gradeX">
                        <td style="text-align: center;">${counter.count}</td>
                        <td style="text-align: center;">${appManage.appName}</td>
                        <td style="text-align: center;">${appManage.appType}</td>
                        <td style="text-align: center;">${appManage.appVersion}</td>
                        <td style="text-align: center;">${appManage.appVersionInfo}</td>
                        <%-- <td> 
                             <c:forEach items="${Apps}" var="appManage" varStatus="counter">
                             ${appManage.appName};
                        	 </c:forEach>
                        </td> --%>
                        <td style="text-align: center;">
                            <c:if test="${appManage.appEnabled}">
                                在用
                            </c:if>
                            <c:if test="${!appManage.appEnabled}">
                                停用
                            </c:if>
                        </td>
                        <td style="text-align: center;">
                            <a class="button blueB" title="" href="#"
                               onclick="window.location.href='${pageContext.request.contextPath}/backend/appform.html?appUuid=${appManage.uuid}&filterName=${filterName}&current=${current}'">
                                <span>编辑</span>
                            </a>
                            <c:if test="${appManage.appEnabled}">
                                <a class="button redB" title="" href="#" onclick="return appDeleteConfirm('${appManage.uuid}', '${filterName}', '${current}');">
                                    <span>停用</span>
                                </a>
                            </c:if>
                            <c:if test="${!appManage.appEnabled}">
                                <a class="button greenB" title="" href="#" onclick="return appEnableConfirm('${appManage.uuid}', '${filterName}', '${current}');">
                                    <span>激活</span>
                                </a>
                            </c:if>
                            <%-- <a class="button greenB" title="" href="#"
                               onclick="window.location.href='${pageContext.request.contextPath}/backend/appform.html?appUuid=${appManage.uuid}&filterName=${filterName}&current=${current}'">
                                <span>下载</span>
                            </a> --%>
                      </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tPagination" style="text-align: center;">
                <ul style="box-shadow: 0px 0px 0px;">
                    <ch:numberpaging urlMapping="${pageContext.request.contextPath}/backend/appmanagement.html" paging="${paging}"/>
                </ul>
            </div>
        </div>
    </div>

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>

</div>

<div id="userenable-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要停用该应用？
    </p>
</div>

<div id="userdisable-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要激活应用?
    </p>
</div>
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
</body>
</html>