<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
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
        <spring-form:form id="usualValidate" class="form" method="post" commandName="password">
            <input type="hidden" name="userUuid" value="${password.userUuid}"/>
        	<fieldset>
                <div class="widget">
                    <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/alert.png" alt="" class="titleIcon" /><h6>用户修改密码</h6></div>

                    <div class="formRow">
                        <label>姓名</label>
                        <div class="formRight">
                            <input type="text" class="required" name="xingMing" value="${password.xingMing}" readonly="true"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formRow">
                        <label>旧密码:<span class="req">*</span></label>
                        <div class="formRight">
                            <input type="password" class="required" id="oldPassword" name="oldPassword" maxlength="30"/>
                            <spring-form:errors path="oldPassword" cssClass="nNote nError hideit"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formRow">
                        <label>新密码:<span class="req">*</span></label>
                        <div class="formRight">
                            <input type="password" class="required" id="newPassword" name="newPassword" maxlength="30"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formRow">
                        <label>重复新密码:<span class="req">*</span></label>
                        <div class="formRight">
                            <input type="password" class="required" id="newPasswordAgain" name="newPasswordAgain" maxlength="30"/>
                            <spring-form:errors path="newPasswordAgain" cssClass="nNote nError hideit"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formSubmit"><input type="submit" value="保存" class="blueB" /></div>
                    <div class="clear"></div>
                </div>

            </fieldset>
        </spring-form:form>

    </div>

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>

</div>

</body>
</html>