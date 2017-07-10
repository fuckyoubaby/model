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

    <div class="titleArea">
        <div class="wrapper">
            <div class="pageTitle">
                <h5>终端升级历史</h5>
            </div>
            <div class="clear"></div>
        </div>
    </div>

    <div class="wrapper">
        <!-- Note -->


        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
                <h6>升级历史:</h6>
                <%-- <form action="${pageContext.request.contextPath}/backend/appmanagement.html" method="POST" style="padding-top:4px;">
                    <input type="text" name="filterName" value="${paging.filterName}" style="width: 200px; height: 25px"/>&nbsp;
                    <input type="submit" value=" " name="find"/>
                </form> --%>
            </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                    <tr>
                        <td width="5%">#</td>
                        <td width="30%">升级时间</td>
                        <td width="30%">升级版本</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listAppStrategys}" var="appStrategyDTO" varStatus="counter">
                    <tr class="gradeX">
                        <td style="text-align: center;">${counter.count}</td>
                        <td style="text-align: center;">${appStrategyDTO.timestampDistrbute }</td>
                        <td style="text-align: center;">${appStrategyDTO.appVersion }</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tPagination" style="text-align: center;">
                <%-- <ul style="box-shadow: 0px 0px 0px;">
                    <ch:numberpaging urlMapping="${pageContext.request.contextPath}/backend/appmanagement.html" paging="${paging}"/>
                </ul> --%>
            </div>
        </div>
    </div>

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>

</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
</body>
</html>