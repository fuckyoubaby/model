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
    <script type="text/javascript">
    
    </script>
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
                <h5>终端升级策略</h5>
            </div>
            <div class="clear"></div>
        </div>
        </div>
	
		<div class="wrapper">
        <spring-form:form method="POST" commandName="appStrategyDTO" class="form">
				<fieldset>
					<div class="widget">
						<div class="title">
                        <img src="${pageContext.request.contextPath}/images/icons/dark/alert.png" alt="" class="titleIcon" />
                        <h6>
                                  编辑策略信息
                        </h6>
                    </div>
                    <input type="hidden" name="strategyUuid" value="${appStrategyDTO.uuid}"/>
						<div class="formRow">
							<label>策略名称<span class="req">*</span></label>
								<div class="formRight">
									<spring-form:input path="name" maxlength="50"/>
									<spring-form:errors path="name" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
								</div>
							<div class="clear"></div>
						</div>
						
						<div class="formRow">
							<label>软件版本<span class="req">*</span></label>
								<div class="formRight">
									<spring-form:input  path="appVersion" placeholder="输入已有的应用版本" maxlength="30"/>
									<spring-form:errors path="appVersion" cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
								</div>
							<div class="clear"></div>
						</div>
						
						<div class="">
						<div class="formSubmit">
	                        <input type="button" value="返回" class="greenB" onclick="window.location.href='${pageContext.request.contextPath}/backend/managestrategy.html?filterName=${filterName}&current=${current}'"/>
	                        <input type="submit" value="保存" class="blueB"/>
                    	</div>
                    	<div class="clear"></div>
					</div>
				 </div>
				</fieldset>
			</spring-form:form>
    	</div>
	</div>
  </body>
</html>
