<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page import="com.changhong.system.domain.box.BoxReportStatus" %>

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

    <div class="nNote nInformation hideit" style="margin:32px 3% 10px 3%">
	        <p><strong>设备异常报告管理: </strong>查看所有异常报告</p>
	</div>

    <div class="wrapper">
        <!-- Note -->
		
		<c:set var="notSolved" value="<%=BoxReportStatus.B_R_REPORT %>" />
        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
                <h6>开始/结束时间:</h6>
                <form action="${pageContext.request.contextPath}/backend/boxreportmanage.html" method="POST" style="padding-top:4px;">
                    <input type="text" name="startDate" value="${paging.startDate}" class="datepicker" style="width: 200px; height: 25px"/>&nbsp;/
                    <input type="text" name="endDate" value="${paging.endDate}" class="datepicker" style="width: 200px; height: 25px"/>&nbsp;
                    <input type="submit" value=" " name="查找"/>
                </form>
            </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                    <tr>
                        <td width="5%">#</td>
                        <td width="20%">时间</td>
                        <td width="15%">MAC地址</td>
                        <td width="15%">异常代码</td>
                        <td width="15%">异常状态</td>
                         <td width="20%">处理时间</td>
                        <td>处理</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${reports}" var="report" varStatus="counter">
                    <tr class="gradeX">
                        <td style="text-align:center">${counter.count}</td>
                        <td style="text-align:center"><fmt:formatDate value="${report.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td style="text-align:center">${report.mac}</td>
                        <td style="text-align:center">${report.code}</td>
                        <td style="text-align:center">${report.status.description}</td>
                         <td style="text-align:center">
                         	<c:if test="${not empty  report.solveTime}">
                         		<fmt:formatDate value="${report.solveTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                         	</c:if>
                         </td>
                        <td style="text-align:center">
                        	
		                      <c:if test="${report.status == notSolved}">
		                        <a class="button blueB" title="" href="#"
                        		  onclick="return disabledConfirm('${report.uuid}');" >
                           			<span>已处理</span>
                       		 	</a>
		                      </c:if>
                      
								<a class="button blueB" title="" href="#" onclick="return deleteConfirm('${report.uuid}');" >
		                           <span>删除</span>
		                       </a>
                        
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tPagination" style="text-align: center;">
                <ul style="box-shadow: 0px 0px 0px;">
                    <ch:numberpaging urlMapping="${pageContext.request.contextPath}/backend/boxreportmanage.html" paging="${paging}"/>
                </ul>
            </div>
        </div>
    </div>

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>

</div>
<div id="report-solved-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认修改此异常状态为【已处理】?
    </p>
</div>
<div id="report-delete-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要删除此条异常?
    </p>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript">
var startDate = '${paging.startDate}',endDate='${paging.endDate}',current = '${paging.currentPageNumber}',baseRequestUrl = '${pageContext.request.contextPath}';
function dialogInit(dialogId, url, callback){
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
                    if(callback){
                    	callback(url);
                    }else window.location.href = url;
                },
                "取消": function() {
                    jQuery(dialogId).css("visibility", "hidden");
                    jQuery(this).dialog("close");
                }
            }
        });
	};
	
    var deleteConfirm=(function(){
        var dialogId ="#report-delete-dialog-confirm";
    	return	function(uuid) {
            	var url = '${pageContext.request.contextPath}/backend/boxreportdelete.html?uuid='+ uuid+'&startDate='+startDate+'&endDate='+endDate+'&current='+current;
            	dialogInit(dialogId, url);
            };	
    	}());
    
    var disabledConfirm = (function(){
    	var dialogId ="#report-solved-dialog-confirm";
    	return function(uuid){
    		var url = '${pageContext.request.contextPath}/backend/boxreportdsolved.html?uuid='+ uuid+'&startDate='+startDate+'&endDate='+endDate+'&current='+current;
    	   		dialogInit(dialogId, url);
    	   	};
    }());
</script>
</body>
</html>