<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0,user-scalable=0" />
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
                <h5>手机应用管理</h5>
            </div>
            <div class="clear"></div>
        </div>
    </div>

    <%--主要内容*******************************************************--%>
    <div class="wrapper">
        <spring-form:form commandName="appPhoneManage" method="POST" enctype="multipart/form-data" cssClass="form">
            <fieldset>
                <div class="widget">
                    <div class="title">
                        <img src="${pageContext.request.contextPath}/images/icons/dark/alert.png" alt="" class="titleIcon" />
                        <h6>
                            <c:choose>
                                <c:when test="${appPhoneManage.uuid != null && appPhoneManage.uuid !=''}">
                                   手机应用编辑
                                </c:when>
                                <c:otherwise>
                                   手机应用添加
                                </c:otherwise>
                            </c:choose>
                        </h6>
                    </div>
                    <input type="hidden" name="appUuid" value="${appPhoneManage.uuid}"/>
                    <div class="formRow">
                        <label>应用名<span class="req">*</span></label>
                        <div class="formRight">
                            <spring-form:input path="appName" maxlength="30"/>
                            <spring-form:errors path="appName" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formRow">
                        <label>类型<span class="req">*</span></label>
                        <div class="formRight">
                            <spring-form:input path="appType" maxlength="30"/>
                            <spring-form:errors path="appType" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formRow">
                        <label>版本<span class="req">*</span></label>
                        <div class="formRight">
                            <spring-form:input id="appVersion" path="appVersion"/>
                            <spring-form:errors path="appVersion" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    
                    <div class="formRow">
                        <label>版本信息</label>
                        <div class="formRight">
                            <spring-form:input type="text" maxlength="100" id="appVersionInfo" path="appVersionInfo"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formRow">
                        <c:choose>
                         <c:when test="${appPhoneManage.uuid != null && appPhoneManage.uuid !=''}">
                         		<label>文件</label>
                         </c:when>
                         <c:otherwise>
                         		<label>文件<span class="req">*</span></label> 
                         </c:otherwise>
                        </c:choose>
                        <div class="formRight">
	                        <input type="file" id="file" name="file"/>
	                        <div>
	                        	<span class="${fileClass}" style="margin:5px 0px 0px 0px;">${fileEmpty}</span>
	                        </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    
                    <div class="formSubmit">
                        <input type="button" value="返回" class="greenB" onclick="window.location.href='${pageContext.request.contextPath}/backend/appphonemanagement.html?filterName=${filterName}&current=${current}'"/>
                        <input type="submit" value="保存" class="blueB"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </fieldset>
        </spring-form:form>
    </div>
    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
</body>
</html>