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
        <p><strong>投影仪元数据管理: </strong>设置投影仪开关时间、状态上报时间间隔</p>
    </div>

    <div class="wrapper">
        <!-- Note -->


        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
                <%--<h6>姓名:</h6>--%>
                <%--<form action="${pageContext.request.contextPath}/backend/metadatamanagement.html" method="POST" style="padding-top:4px;">--%>
                    <%--<input type="text" name="filterName" value="${paging.filterName}" style="width: 200px;"/>&nbsp;--%>
                    <%--<input type="submit" value=" " name="find"/>--%>
                <%--</form>--%>
                <a class="button greyishB" title="" href="#" style="position: absolute; top: 5px; right: 5px;"
                   onclick="window.location.href = '${pageContext.request.contextPath}/backend/metadataform.html?current=${current}'">
                    <img class="icon" alt="" src="${pageContext.request.contextPath}/images/icons/light/add.png">
                    <span>添加投影仪配置</span>
                </a>
            </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                    <tr>
                        <td width="5%">#</td>
                        <td width="15%">级别</td>
                        <td width="15%">开关机时间</td>
                        <td width="10%">状态上报间隔</td>
                        <td width="40%">描述</td>
                        <td >操作</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${metaDatas}" var="data" varStatus="counter">
                    <tr class="gradeX">
                        <td>${counter.count}</td>
                        <td>${data.dataLevelDesc}</td>
                        <td>${data.startTime} | ${data.endTime}</td>
                        <td>${data.heartInterval}秒</td>
                        <td>${data.note}</td>
                        <td style="text-align: center;">
                            <c:if test="${data.dataLevel == 'USER'}">
                                <a class="button redB" title="" href="#"
                                   onclick="window.location.href='${pageContext.request.contextPath}/backend/metadataform.html?metaDataUuid=${data.uuid}&current=${current}'">
                                    <span>删除</span>
                                </a>
                            </c:if>
                            <a class="button blueB" title="" href="#"
                               onclick="window.location.href='${pageContext.request.contextPath}/backend/metadataform.html?metaDataUuid=${data.uuid}&current=${current}'">
                                <span>编辑</span>
                            </a>
                      </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tPagination" style="text-align: center;">
                <ul style="box-shadow: 0px 0px 0px;">
                    <ch:numberpaging urlMapping="${pageContext.request.contextPath}/backend/metadatamanagement.html" paging="${paging}"/>
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