<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/jquery.jstree.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/_lib/jquery.cookie.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/_lib/jquery.hotkeys.js" type="text/javascript"></script>
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

    <div class="wrapper">
        <!-- Note -->
        <div class="nNote nInformation hideit">
            <p><strong>提示: </strong>下面显示了所有的地区目录，点击地区目录，可以查看相应目录下的投影仪数据的统计</p>
        </div>

        <div class="widget">
            <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/users.png" alt="" class="titleIcon" /><h6>地区选择</h6></div>
            <div id="tree" style="clear : both;height: 150px; overflow-y: auto;"></div>
        </div>

        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
                <h6 id="areaName">地区&nbsp;&nbsp;:&nbsp;&nbsp;</h6>
            </div>
        </div>

        <div style="width:100%;min-height: 240px" id="barStack"></div>
    </div>
    <%--结尾部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/BlockUI/BlockUI.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/contextPath.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/sta/boxnumbersta.min.js"></script>

</body>
</html>
