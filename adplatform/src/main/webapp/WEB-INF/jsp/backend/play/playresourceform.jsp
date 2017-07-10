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
	 <style>
    	div.uploader { width: 400px; position: relative; overflow: hidden; box-shadow: 0 0 0 2px #f4f4f4; -webkit-box-shadow: 0 0 0 2px #f4f4f4; -moz-box-shadow: 0 0 0 2px #f4f4f4; border: 1px solid #DDD; background: white; padding: 2px 2px 2px 8px; }
		div.uploader span.filename { color: #777; max-width: 350px; font-size: 11px; line-height: 22px; float: left; display: block; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; cursor: default; }
		div.uploader input { width: 400px; opacity: 0; filter: alpha(opacity:0); position: absolute; top: 0; right: 0; bottom: 0; float: right; height: 26px; border: none; cursor: pointer; }
		.uploader { display: -moz-inline-box; display: inline-block; vertical-align: middle; zoom: 1; *display: inline; }
    </style>
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
                <h5>广告资源管理</h5>
            </div>
            <div class="clear"></div>
        </div>
    </div>

    <%--主要内容*******************************************************--%>
    <div class="wrapper">
        <spring-form:form commandName="adverResource" method="POST" cssClass="form" enctype="multipart/form-data" >
            <fieldset>
                <div class="widget">
                    <div class="title">
                        <img src="${pageContext.request.contextPath}/images/icons/dark/alert.png" alt="" class="titleIcon" />
                        <h6>
                            <c:choose>
                                <c:when test="${empty adverResource.uuid}">
                                    资源新增
                                </c:when>
                                <c:otherwise>
                                    资源编辑
                                </c:otherwise>
                            </c:choose>
                        </h6>
                    </div>
                    <input type="hidden" name="resourceUuid" value="${adverResource.uuid}"/>
                    <input type="hidden" name="itemUuid" value="${itemUuid}"/>
                    <input type="hidden" name="contentUuid" value="${contentUuid}"/>
                    <input type="hidden" name="contentUpgradeId" value="${contentUpgradeId}" />
                    <input type="hidden" name="filePath" value="${adverResource.path}"/>
                    <div class="formRow">
                        <label>资源名称<span class="req">*</span></label>
                        <div class="formRight">
                            <spring-form:input path="name" maxlength="30"/>
                            <spring-form:errors path="name" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                        </div>
                        <div class="clear"></div>
                    </div>
					 <div class="formRow">
                        <label>资源路径<span class="req">*</span></label>
                        <div class="formRight">
                            <input id="file" name="file" type="file" /> 
                            <%--  <spring-form:errors path="path" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/> --%>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="formRow">
                        <label>广告商<span class="req">*</span></label>
                        <div class="formRight">
                            <spring-form:input id="advertiser" path="advertiser"/>
                            <spring-form:errors path="advertiser" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                     <div class="formRow">
                        <label>代理商<span class="req">*</span></label>
                        <div class="formRight">
                            <spring-form:input id="agents" path="agents"/>
                            <spring-form:errors path="agents" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="formSubmit">
                        <input type="button" value="返回" class="greenB" onclick="window.location.href='${pageContext.request.contextPath}/backend/configplayitem.html?itemUuid=${itemUuid}&contentUuid=${contentUuid}&contentUpgradeId=${contentUpgradeId}'"/>
                        <input type="submit" value="保存" class="blueB"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </fieldset>
        </spring-form:form>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript">
$(function(){
	var $file = $("#file");
	var $filename = $file.siblings(".filename");
	var filePath = '${adverResource.path}'
	if(filePath){
		$filename.text(filePath);
	}
	var fileError = '${fileError}';
	if(fileError){
		//不要改，确实是这个id
		addErrorSpan('uniform-file', fileError);
	}
});

function addErrorSpan(id,message){
	var $dom = $("#"+id);
	var spanHtml='<span id="'+id+
	'.errors"class="nNote nError hideit" style="margin: 5px 0px 0px 6px;">'+message+'</span>';
	$(spanHtml).insertAfter($dom);
}
</script>
</body>
</html>