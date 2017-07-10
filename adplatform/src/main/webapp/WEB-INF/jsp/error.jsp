<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
</head>

<body class="nobg errorPage">

<%--开头菜单部分***********************************************************--%>

<div class="topNav">
    <div class="wrapper">
        <div class="userNav" style="margin-right: 0px;">
            <ul>
                <li><a href="javascript:void(0);" title=""><img src="${pageContext.request.contextPath}/images/icons/topnav/profile.png" alt="" /><span><ch:user/></span></a></li>
                <li><a href="${pageContext.request.contextPath}/backend/userchangepassword.html" title=""><img src="${pageContext.request.contextPath}/images/icons/topnav/settings.png" alt="" /><span>修改密码</span></a></li>
                <li><a href="${pageContext.request.contextPath}/j_spring_security_logout" title=""><img src="${pageContext.request.contextPath}/images/icons/topnav/logout.png" alt="" /><span>退出</span></a></li>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
</div>

<%--内容部分***********************************************************--%>

<div class="errorWrapper">
    <span class="sadEmo"></span>
    <span class="errorTitle">系统错误 :(</span>
    <span class="errorNum">404</span>
    <span class="errorDesc">对不起, 系统错误，请联系管理员!</span>
    <a href="${pageContext.request.contextPath}/backend/dashboard.html" title="" class="button blueB"><span>返回主页</span></a>
</div>

</body>
</html>