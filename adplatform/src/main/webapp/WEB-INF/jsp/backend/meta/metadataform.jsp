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
                <h5>系统元数据管理</h5>
            </div>
            <div class="clear"></div>
        </div>
    </div>

    <%--主要内容*******************************************************--%>
    <div class="wrapper">
        <spring-form:form commandName="metaData" method="POST" cssClass="form">
            <fieldset>
                <div class="widget">
                    <div class="title">
                        <img src="${pageContext.request.contextPath}/images/icons/dark/alert.png" alt="" class="titleIcon" />
                        <h6>
                            <c:choose>
                                <c:when test="${metaData.uuid != null && metaData.uuid !=''}">
                                    投影仪配置编辑
                                </c:when>
                                <c:otherwise>
                                    投影仪配置添加
                                </c:otherwise>
                            </c:choose>
                        </h6>
                    </div>
                    <input type="hidden" name="metaDataUuid" value="${metaData.uuid}"/>

                    <div class="formRow">
                        <label>开机时间<span class="req">*</span></label>
                        <div class="formRight">
                            <spring-form:input path="startTime" class="maskHour" maxlength="5"/>
                            <span class="formNote">请按照改格式进行填写：如06:00</span>
                            <spring-form:errors path="startTime" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formRow">
                        <label>关机时间<span class="req">*</span></label>
                        <div class="formRight">
                            <spring-form:input path="endTime" class="maskHour" maxlength="5"/>
                            <span class="formNote">请按照改格式进行填写：如23:00</span>
                            <spring-form:errors path="endTime" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formRow">
                        <label>上报时间间隔（秒）<span class="req">*</span></label>
                        <div class="formRight">
                            <spring-form:input path="heartInterval" maxlength="100"/>
                            <spring-form:errors path="heartInterval" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formRow">
                        <label>描述</label>
                        <div class="formRight">
                            <spring-form:input path="note" maxlength="100"/>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="formSubmit">
                        <input type="button" value="返回" class="greenB" onclick="window.location.href='${pageContext.request.contextPath}/backend/metadatamanagement.html?current=${current}'"/>
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