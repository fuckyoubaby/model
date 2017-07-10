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
        <p><strong>广告管理: </strong>
        	<c:choose>
             <c:when test="${empty playItem.uuid}">
                     增加新的广告
             </c:when>
             <c:otherwise>
                      编辑广告
             </c:otherwise>
            </c:choose>
        	</p>
    </div>

    <div class="wrapper">
      <!-- Note -->
      <spring-form:form commandName="playItem"  cssClass="form"  method="post" action="${pageContext.request.contextPath}/backend/saveplayitem.html">
       	<fieldset>
      	<input type="hidden" name="playItemId" value="${playItem.uuid}"/>
      	<input type="hidden" name="playContentId" value="${playContentId}"/>
      	<input type="hidden" name="contentUpgradeId" value="${contentUpgradeId}"/>
        <div class="widget">
             <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/list.png" alt="" class="titleIcon"><h6>广告信息</h6></div>
             <div class="formRow">
                 <label>名称*</label>
                 <div class="formRight">
                 	<spring-form:input path="name" maxlength="85" />
                 	<spring-form:errors path="name"  cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                 </div>
                 <div class="clear"></div>
             </div>
             <div class="formRow">
                 <label for="labelFor">描述</label>
                 <div class="formRight">
                 	<spring-form:input path="description" maxlength="200"/>
                 	<spring-form:errors path="description"  cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                 </div>
                 <div class="clear"></div>
             </div>
              <div class="formSubmit">
              	  <c:choose>
              	  	<c:when test="${empty playItem.uuid}">
              	  		<input type="button" value="返回" class="greenB" onclick="window.location.href='${pageContext.request.contextPath}/backend/playcontent.html?playContentId=${playContentId}&contentUpgradeId=${contentUpgradeId}'"/>
              	  	</c:when>
              	  	<c:otherwise>
              	  		<!-- 更新 -->
              	  		<c:choose>
              	  		<c:when test="${empty contentUpgradeId}">
              	  			<input type="button" value="返回" class="greenB" onclick="window.location.href='${pageContext.request.contextPath}/backend/configplayitem.html?contentUuid=${playContentId}&itemUuid=${playItem.uuid}'"/>
              	  		</c:when>
              	  		<c:otherwise>
              	  			<input type="button" value="返回" class="greenB" onclick="window.location.href='${pageContext.request.contextPath}/backend/configplayitem.html?contentUuid=${playContentId}&itemUuid=${playItem.uuid}'"/>
              	  		</c:otherwise>
              	  		</c:choose>
              	  	</c:otherwise>
              	  </c:choose>
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