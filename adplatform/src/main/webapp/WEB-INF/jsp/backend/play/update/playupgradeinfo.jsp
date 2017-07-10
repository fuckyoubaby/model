<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/htmlstyle.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/tz_page.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tz_page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.js"></script>    
	<style>
	.ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
   .ui-dialog  label, .ui-dialog input { display:block; }
    .ui-dialog input.text { margin-bottom:12px; width:95%; padding: .4em; }
    .ui-dialog fieldset { padding:0; border:0; margin-top:15px; }
   .ui-dialog  span.star{color:#ff0000;padding-left:3px}
    .img-btn{float:left;line-height:0;}
	</style>
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
        <p><strong>内容升级管理: </strong>对升级进行内容组织和范围配置</p>
    </div>

    <div class="wrapper">
        <!-- Note -->
        <div class="widgets">
        	<div class="oneThree">
				<div class="widget">
                    <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/clipboard.png" alt="" class="titleIcon"><h6>内容升级基本信息</h6></div>
                    <div class="body" style="border-bottom:1px solid #E2E2E2">
                        <h4 class="pt5">编号</h4><p><span class="greenBack content-uuid-dom">${contentUpgrade.uuid}</span> </p>
                        <h4 class="pt10">名称</h4><p> <span class="greyBack">${contentUpgrade.name}</span> </p>
                        <c:if test="${! empty contentUpgrade.description}">
	                        <h4 class="pt10">描述</h4><p><span class="blueBack">${contentUpgrade.description }</span></p>
                        </c:if>
                        <h4 class="pt10">创建时间</h4><p><fmt:formatDate value="${contentUpgrade.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                    </div>
                    <div class="formSubmit" style="float:left">
                        <input type="button" value="返回" class="blueB" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/contentupgrademanagement.html?filterName=${filterName}&current=${current}'">
                    </div>
                    <div class="clear"></div>
                </div>
			</div>
			<div class="twoOne">
				<div class="widget">
                    <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/stats.png" alt="" class="titleIcon"><h6>已配置的播放内容信息</h6></div>
                    <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                        <thead>
                            <tr>
                                <td width="80">名称</td>
                                <td width="20">广告数量</td>
                                <td width="80">版本</td>
                                <td width="80">创建时间</td>
                                <td width="80">操作</td>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:choose>
                        		<c:when test="${empty playContent}">
                        		<tr>
                        			<td colspan="4" align="center">还未配置内容，请点击按钮进行配置！</td>
                        			 <td class="actBtns">
	                           			<a class="button blueB" title="" href="#" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/contentupgradeconfigplay.html?contentUpgradeId=${contentUpgrade.uuid}&filterName=${filterName}&current=${current}'">
	                                		<span>配置</span>
	                            		</a>
	                                	<div class="clear"></div>
	                                 </td>
                        		</tr>
                        		</c:when>
                        		<c:otherwise>
	                        		<tr  data-contentuuid='["${playContent.uuid}"]'>
		                                <td align="center"><a href="#" title="" class="webStatsLink">${playContent.name}</a></td>
		                                <td align="center">${playContent.amount}</td>
		                                <td align="center">${playContent.version}</td>
		                                <td align="center"><fmt:formatDate value="${playContent.timestamp}" pattern="yyyy.MM.dd"/></td>
		                                <td class="actBtns">
		                           			<a class="button blueB" title="" href="#" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/contentupgradeconfigplay.html?contentUpgradeId=${contentUpgrade.uuid}&filterName=${filterName}&current=${current}'">
		                                		<span>详情</span>
		                            		</a>
		                                	<div class="clear"></div>
		                                 </td>
	                            	</tr>
                        		</c:otherwise>
                        	</c:choose>
                        </tbody>
                    </table> 
<%--                    <div class="body" style="border-bottom:1px solid #E2E2E2">
                   		<c:choose>
                   			<c:when test="${empty playContent}">
                   			<p>还未配置内容，请点击按钮进行配置！</p>
                   			</c:when>
                   			<c:otherwise>
                   				<h4 class="pt5">编号</h4><p><span class="greenBack content-uuid-dom">${playContent.uuid}</span> </p>
		                        <h4 class="pt10">名称</h4><p> <span class="greyBack">${playContent.name}</span> </p>
			                    <h4 class="pt10">版本</h4><p><span class="blueBack">${playContent.version }</span></p>
			                    <h4 class="pt10">数量</h4><p><span class="blueBack">${playContent.amount }</span></p>
		                        <h4 class="pt10">创建时间</h4><p><fmt:formatDate value="${playContent.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                   			</c:otherwise>
                   		</c:choose>
                   </div> --%>
 <%--                    <div class="formSubmit" style="float:left">
                    	<c:if test="${! empty playContent}">
                    		<%
                    		//TODO 升级发布后，内容只预览不修改
                    		%>
                    		<input type="button" value="详情" class="blueB" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/contentupgrademanagement.html?filterName=${filterName}&current=${current}'">
                    	</c:if>
                        <input type="button" value="配置" class="blueB" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/contentupgradeconfigplay.html?contentUpgradeId=${contentUpgrade.uuid}&filterName=${filterName}&current=${current}'">
                    </div>
                    <div class="clear"></div> --%>
                </div>
                
                <div class="widget">
                    <div class="title">
                    	<img src="${pageContext.request.contextPath}/images/icons/dark/timer.png" alt="" class="titleIcon">
                    	<h6 style="margin-right:20px">配置的升级范围</h6>
                    </div>
 					<div class="body" style="border-bottom:1px solid #E2E2E2">
 						已配置${fn:length(areasDTO)}个地区，配置${fn:length(communitiesDTO)}个小区，配置${fn:length(boxsDTO)}台终端。
                   		<%-- <c:choose>
                   			<c:when test="${empty playContent}">
                   			<p>还未配置内容，请点击按钮进行配置！</p>
                   			</c:when>
                   			<c:otherwise>
                   				<h4 class="pt5">编号</h4><p><span class="greenBack content-uuid-dom">${playContent.uuid}</span> </p>
		                        <h4 class="pt10">名称</h4><p> <span class="greyBack">${playContent.name}</span> </p>
			                    <h4 class="pt10">版本</h4><p><span class="blueBack">${playContent.version }</span></p>
			                    <h4 class="pt10">数量</h4><p><span class="blueBack">${playContent.amount }</span></p>
		                        <h4 class="pt10">创建时间</h4><p><fmt:formatDate value="${playContent.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                   			</c:otherwise>
                   		</c:choose> --%>
                   </div>
                    <div class="formSubmit" style="float:left">
                        <input type="button" value="详情" class="blueB" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/contentUpdateConfig.html?contentUpgradeId=${contentUpgrade.uuid}&filterName=${filterName}&current=${current}'">
                    </div>
                    <div class="clear"></div> 
                </div>
			</div>
			<div class="clear"></div>
        </div>
    </div>

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>

</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>

    
</body>
</html>