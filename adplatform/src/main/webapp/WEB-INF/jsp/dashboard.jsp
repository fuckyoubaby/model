<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

    <%--主要内容*******************************************************--%>
    <%--<div class="titleArea">--%>
        <%--<div class="wrapper">--%>
            <%--<div class="pageTitle">--%>
                <%--<h5>首页</h5>--%>
            <%--</div>--%>
            <%--<div class="clear"></div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="wrapper">

        <!-- Note -->
        <div class="nNote nInformation hideit">
            <p><strong>提示: </strong>12小时未上线投影仪列表</p>
        </div>

        <!-- Chart -->
        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
            </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                    <td width="3%">
                        #
                    </td>
                    <td width="10%">>MAC</td>
                    <td width="40%">位置</td>
                    <td width="10%">最新状态更新</td>
                    <td width="5%">CPU</td>
                    <td width="5%">内存</td>
                    <td width="5%">磁盘</td>
                </thead>
                <tbody id="communityBody">
                    <c:forEach items="${boxes}" var="box" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${box.mac}</td>
                            <td>${box.communityPath}/${box.note}</td>
                            <td id="time_${box.uuid}">${box.lastReportTime}</td>
                            <td id="cpu_${box.uuid}">${box.cpuRate}%</td>
                            <td id="mem_${box.uuid}">${box.memRate}%</td>
                            <td id="disk_${box.uuid}">${box.diskRate}%</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="tPagination" style="text-align: center;">
                <ul style="box-shadow: 0px 0px 0px;">
                    <ch:numberpaging urlMapping="${pageContext.request.contextPath}/backend/dashboard.html" paging="${paging}"/>
                </ul>
            </div>
        </div>
    </div>

    <%--结尾部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
</body>
</html>
