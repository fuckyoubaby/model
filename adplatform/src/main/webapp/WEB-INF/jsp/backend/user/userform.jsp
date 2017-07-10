<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <link href="${pageContext.request.contextPath}/css/htmlstyle.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/left.jsp"/>

<%--内容部分***********************************************************--%>

<div id="rightSide">

    <%--开头部分*******************************************************--%>

    <jsp:include page="/WEB-INF/decorators/head.jsp"/>

    <div class="titleArea">
        <div class="wrapper">
            <div class="pageTitle">
                <h5>系统用户管理</h5>
            </div>
            <div class="clear"></div>
        </div>
    </div>

    <%--主要内容*******************************************************--%>
    <div class="wrapper">
        <spring-form:form commandName="user" method="POST" cssClass="form">
            <fieldset>
                <div class="widget">
                    <div class="title">
                        <img src="${pageContext.request.contextPath}/images/icons/dark/alert.png" alt="" class="titleIcon" />
                        <h6>
                            <c:choose>
                                <c:when test="${user.uuid != null && user.uuid !=''}">
                                    系统用户编辑
                                </c:when>
                                <c:otherwise>
                                    系统用户添加
                                </c:otherwise>
                            </c:choose>
                        </h6>
                    </div>
                    <input type="hidden" name="userUuid" value="${user.uuid}"/>
                    <div class="formRow">
                        <label>姓名<span class="req">*</span></label>
                        <div class="formRight">
                            <spring-form:input path="xingMing" maxlength="30"/>
                            <spring-form:errors path="xingMing" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formRow">
                        <label>电话号码 (用户名)<span class="req">*</span></label>
                        <div class="formRight">
                            <c:set var="justRead" value="false"/>
                            <c:if test="${user.username == 'chadadmin'}">
                                <c:set var="justRead" value="true"/>
                            </c:if>
                            <spring-form:input path="username" maxlength="30" readonly="${justRead}"/>
                            <spring-form:errors path="username" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formRow">
                        <label>邮箱</label>
                        <div class="formRight">
                            <spring-form:input id="contactWay" path="contactWay"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formSubmit">
                        <input type="button" value="返回" class="greenB" onclick="window.location.href='${pageContext.request.contextPath}/backend/usermanagement.html?filterName=${filterName}&current=${current}'"/>
                        <input type="submit" value="保存" class="blueB"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </fieldset>
        </spring-form:form>
    </div>
</div>

<script type="text/javascript">
</script>
</body>
</html>