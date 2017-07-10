<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>

<div class="topNav">
    <div class="wrapper">
        <div class="userNav">
            <ul>
                <li><a href="javascript:void(0);" title=""><img src="${pageContext.request.contextPath}/images/icons/topnav/profile.png" alt="" /><span><ch:user/></span></a></li>
                <li><a href="${pageContext.request.contextPath}/backend/passwordform.html" title=""><img src="${pageContext.request.contextPath}/images/icons/topnav/settings.png" alt="" /><span>修改密码</span></a></li>
                <li><a href="${pageContext.request.contextPath}/j_spring_security_logout" title=""><img src="${pageContext.request.contextPath}/images/icons/topnav/logout.png" alt="" /><span>退出</span></a></li>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
</div>

<div class="resp">
    <div class="respHead">
        <a href="${pageContext.request.contextPath}/backend/dashboard.html" title=""><img src="${pageContext.request.contextPath}/images/loginLogo.png" alt="" /></a>
    </div>
</div>

<div class="titleArea">
    <div class="wrapper">
        <div class="clear"></div>
    </div>
</div>