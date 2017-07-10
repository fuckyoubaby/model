<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/htmlstyle.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">

        function deleteConfirm(uuid, filterName, current) {
        	var dialogId ="#ad-delete-dialog-confirm";
            jQuery(dialogId).css("visibility", "visible");
            jQuery(dialogId).dialog({
                    resizable: false,
                    height:160,
                    width:300,
                    modal: true,
                    buttons: {
                        "确认": function() {
                            jQuery(dialogId).css("visibility", "hidden");
                            jQuery(this).dialog("close");
                            window.location.href = '${pageContext.request.contextPath}/backend/deletePlayContent.html?playContentId=' + uuid + '&filterName=' + filterName + '&current=' + current;
                        },
                        "取消": function() {
                            jQuery(dialogId).css("visibility", "hidden");
                            jQuery(this).dialog("close");
                        }
                    }
                });
        }

    </script>
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
        <p><strong>播放内容管理: </strong>对播放内容进行添加，编辑，删除以及广告配置操作</p>
    </div>

    <div class="wrapper">
        <!-- Note -->


        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
                <h6>名称:</h6>
                <form action="${pageContext.request.contextPath}/backend/boxmacindex.html" method="POST" style="padding-top:4px;">
                    <input type="text" name="filterName" value="${paging.filterName}" style="width: 200px; height: 25px"/>&nbsp;
                    <input type="submit" value=" " name="find"/>
                </form>
                <a class="button greyishB" title="" href="#" style="position: absolute; top: 5px; right: 5px;"
                   onclick="window.location.href = '${pageContext.request.contextPath}/backend/playcontentform.html'">
                    <img class="icon" alt="" src="${pageContext.request.contextPath}/images/icons/light/add.png">
                    <span>添加新内容</span>
                </a>
            </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                    <tr>
                        <td width="5%">#</td>
                        <td width="20%">名称</td>
                        <td width="10%">版本</td>
                        <td width="10%">广告数量</td>
                       	<td width="10%">默认播放时间</td>
                       	<td width="20%">创建时间</td>
                        <td >操作</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${playContents}" var="playContent" varStatus="counter">
                    <tr class="gradeX">
                        <td>${counter.count}</td>
                        <td>${playContent.name}</td>
                        <td style="text-align: center;">${playContent.version}</td>
                        <td style="text-align: center;">${playContent.amount}</td>
                        <td style="text-align: center;">${playContent.defaultDuration}</td>
                        <td style="text-align: center;"><fmt:formatDate value="${playContent.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td style="text-align: center;">
                            <a class="button blueB" title="" href="#"
                               onclick="window.location.href='${pageContext.request.contextPath}/backend/playcontentform.html?contentUuid=${playContent.uuid}&filterName=${filterName}&current=${current}'" >
                                <span>编辑</span>
                            </a>
                           <a class="button blueB" title="" href="#" onclick="window.location.href='${pageContext.request.contextPath}/backend/playcontent.html?playContentId=${playContent.uuid}&filterName=${filterName}&current=${current}'">
                                <span>配置</span>
                            </a>
							<a class="button redB" title="" href="#"
                                onclick="return deleteConfirm('${playContent.uuid}','${filterName}','${current}');" >
                                <span>删除</span>
                            </a>
                      </td>
                      
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tPagination" style="text-align: center;">
                <ul style="box-shadow: 0px 0px 0px;">
                    <ch:numberpaging urlMapping="${pageContext.request.contextPath}/backend/playcontentmanagement.html" paging="${paging}"/>
                </ul>
            </div>
        </div>
    </div>

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>

</div>

<div id="ad-delete-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要删除此条?
    </p>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
</body>
</html>