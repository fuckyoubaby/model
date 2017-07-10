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

    <div class="nNote nInformation hideit">
        <p><strong>系统日志数据管理: </strong>查看系统所有操作日志</p>
    </div>

    <div class="wrapper">
        <!-- Note -->


        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
                <h6>开始/结束时间:</h6>
                <form action="${pageContext.request.contextPath}/backend/actionlogmanagement.html" method="POST" style="padding-top:4px;">
                    <input type="text" name="fromDate" value="${paging.startDate}" class="datepicker" style="width: 200px; height: 25px"/>&nbsp;/
                    <input type="text" name="endDate" value="${paging.endDate}" class="datepicker" style="width: 200px; height: 25px"/>&nbsp;
                    <input type="submit" value=" " name="查找"/>
                </form>
            </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                    <tr>
                        <td width="5%">#</td>
                        <td width="12%">时间</td>
                        <td width="8%">操作人</td>
                        <td width="8%">类型</td>
                        <td>描述</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${logs}" var="log" varStatus="counter">
                    <tr class="gradeX">
                        <td>${counter.count}</td>
                        <td>${log.timestamp}</td>
                        <td>${log.operator}</td>
                        <td>${log.type}</td>
                        <td>${log.desc}</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tPagination" style="text-align: center;">
                <ul style="box-shadow: 0px 0px 0px;">
                    <ch:numberpaging urlMapping="${pageContext.request.contextPath}/backend/actionlogmanagement.html" paging="${paging}"/>
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