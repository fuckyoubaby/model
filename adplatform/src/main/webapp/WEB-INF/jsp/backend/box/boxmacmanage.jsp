<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<%@ taglib prefix="fns" uri="http://www.chanhong.com/fns" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tz_page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.js"></script> 
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/htmlstyle.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/tz_page.css" rel="stylesheet" type="text/css" />
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
       	<p><strong>设备MAC管理: </strong>对MAC进行查询、添加、修改状态操作</p>
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
                 <a class="button greyishB" title="" href="#" style="position: absolute; top: 5px; right: 145px;"
                   onclick="window.location.href = '${pageContext.request.contextPath}/backend/boxmacadd.html'">
                    <img class="icon" alt="" src="${pageContext.request.contextPath}/images/icons/light/add.png">
                    <span>添加设备MAC</span>
                </a>
                 <a class="button greyishB" title="" href="#" style="position: absolute; top: 5px; right: 5px;"
                   onclick="window.location.href = '${pageContext.request.contextPath}/backend/boxmacbatchimport.html'">
                    <img class="icon" alt="" src="${pageContext.request.contextPath}/images/icons/light/add.png">
                    <span>批量添加MAC</span>
                </a>
            </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                    <tr>
                        <td width="5%">#</td>
                        <td width="25%">MAC地址</td>
                       	<td width="15%">当前状态</td>
                       	<td width="25%">创建时间</td>
                        <td >操作</td>
                    </tr>
                </thead>
                <tbody >
                	 <c:forEach items="${boxmacs}" var="boxmac" varStatus="counter">
		               <tr class="gradeX">
		                   <td style="text-align: center;">${counter.count}</td>
		                   <td style="text-align: center;">${boxmac.mac}</td>
		                   <td style="padding-left:30px;">${fns:displayMacStatus(boxmac.macStatus)}  
		                   		<c:if test="${boxmac.macStatus == 'B_INITE'}">
			                       <a class="button violetB" title="" href="#" onclick="return disabledConfirm('${boxmac.uuid}')">
			                            <span>禁用</span>
			                        </a>
			                      </c:if>
			                      <c:if test="${boxmac.macStatus == 'B_DISABLE'}">
				                       <a class="button greenB" title="" href="#" onclick="return activedConfirm('${boxmac.uuid}')">
				                            <span>激活</span>
				                        </a>
			                      </c:if>
			               </td>
		                   <td style="text-align: center;"><fmt:formatDate value="${boxmac.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                   <td style="text-align: center;">
		                   		<c:if test="${boxmac.macStatus != 'B_USED'}">
			                       <a class="button blueB" title="" href="#"
			                          onclick="window.location.href='${pageContext.request.contextPath}/backend/boxmacadd.html?boxmacUuid=${boxmac.uuid}'" >
			                           <span>编辑</span>
			                       </a>
			                      <a class="button redB" title="" href="#" onclick="return deleteConfirm('${boxmac.uuid}');" >
			                           <span>删除</span>
			                       </a>
		                       </c:if>
		                 </td>
		                 
		               </tr>
               	</c:forEach>
                </tbody>
            </table>
           <div class="tPagination" style="text-align: center;">
                <ul style="box-shadow: 0px 0px 0px;">
                    <ch:numberpaging urlMapping="${pageContext.request.contextPath}/backend/boxmacindex.html" paging="${paging}"/>
                </ul>
            </div>
        </div>
    </div>

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>

</div>
<div id="mac-disabled-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        	请确认你是否要禁用此条MAC?
    </p>
</div>
<div id="mac-actived-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        	请确认你是否要激活此条MAC?
    </p>
</div>
<div id="mac-delete-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
       	 请确认你是否要删除此条MAC?
    </p>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
 <script type="text/javascript">
    	var baseRequestUrl = '${pageContext.request.contextPath}';
    	var suffixUrl = '&filterName='+'${paging.filterName}'+'&current='+'${paging.currentPageNumber}';
    	console.log(suffixUrl);
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
                            	callback();
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
            var dialogId ="#mac-delete-dialog-confirm";
        	return	function(uuid) {
                	var url = '${pageContext.request.contextPath}/backend/boxmacdelete.html?boxmacUuid=' + uuid + suffixUrl;
                	dialogInit(dialogId, url);
                };	
        	}());
        var disabledConfirm = (function(){
        	var dialogId ="#mac-disabled-dialog-confirm";
        	return function(uuid){
        		var url = '${pageContext.request.contextPath}/backend/boxmacstatusdisabled.html?boxmacUuid='+uuid + suffixUrl;
        	   		dialogInit(dialogId, url);
        	   	};
        }());
        
        var activedConfirm = (function(){
        	var dialogId ="#mac-actived-dialog-confirm";
        	return function(uuid){
        		var url = '${pageContext.request.contextPath}/backend/boxmacstatusactived.html?boxmacUuid='+uuid + suffixUrl;
        	   		dialogInit(dialogId, url);
        	   	};
        }());
       
    </script>
</body>
</html>