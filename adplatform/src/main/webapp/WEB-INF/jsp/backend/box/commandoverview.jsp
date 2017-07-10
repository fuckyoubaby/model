<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/htmlstyle.css" rel="stylesheet" type="text/css" />
</head>

<body>

<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/left.jsp"/>

<%--内容部分***********************************************************--%>

<div id="rightSide">

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/head.jsp"/>

    <%--页面按钮部分*******************************************************--%>

    <div class="wrapper">
        <!-- Note -->
        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
                <h6 id="configure">${community.areaFullPath}操作历史 </h6>
                <div class="num">
                    <a class="greyNum" title="" href="${pageContext.request.contextPath}/backend/boxinfomanagement.html?communityUuid=${community.uuid}"><span>  < 返回</span></a>
                </div>
            </div>

            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                    <tr>
                        <td width="5%">#</td>
                        <td width="15%">操作时间</td>
                        <td width="15%">MAC</td>
                        <td width="55%">备注</td>
                        <td>完成</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${commands}" var="command" varStatus="counter">
                    <tr class="gradeX">
                        <td>${counter.count}</td>
                        <td>${command.timestamp}</td>
                        <td>${command.mac}</td>
                        <td>${command.note}</td>
                        <td>
                            <c:if test="${command.finished}">
                                <span class="green f11">已完成</span>
                            </c:if>
                            <c:if test="${!command.finished}">
                                <span class="red f11">未完成</span>
                            </c:if>
                        </td>
                      </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tPagination" style="text-align: center;">
                <ul style="box-shadow: 0px 0px 0px;">
                    <ch:numberpaging urlMapping="${pageContext.request.contextPath}/backend/boxcommandmanagement.html" paging="${paging}"/>
                </ul>
            </div>
        </div>
    </div>

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>

</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
</body>
</html>