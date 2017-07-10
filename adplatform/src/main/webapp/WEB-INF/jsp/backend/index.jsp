<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html class="html">
<head>
	<title>电梯广告资源管理平台</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />

    <!-- CSS -->
   <link rel="stylesheet" href="${pageContext.request.contextPath}/login/css/reset.css">
   <link rel="stylesheet" href="${pageContext.request.contextPath}/login/css/supersized.css">
   <link rel="stylesheet" href="${pageContext.request.contextPath}/login/css/style.css">

    <!-- Javascript -->
    <script src="${pageContext.request.contextPath}/login/js/jquery-1.8.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/login/js/supersized.3.2.7.min.js"></script>
    <script src="${pageContext.request.contextPath}/login/js/supersized-init.js"></script>
    <script src="${pageContext.request.contextPath}/login/js/scripts.js"></script>
</head>

<body>

    <div class="page-container">
        <h1>电梯广告资源管理平台 V${projectVersion}</h1>
        <form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
            <input type="text" name="j_username" class="username" placeholder="用户名">
            <input type="password" name="j_password" class="password" placeholder="密  码">
            <button type="submit">登录</button>
            <div class="error"><span>+</span></div>
        </form>
        <div class="connect">
            <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                <p>用户名或者密码错误!</p>
            </c:if>
        </div>
    </div>

</body>

</html>
