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
        <p><strong>设备MAC管理: </strong> 
           <c:choose>
             <c:when test="${empty boxmac.uuid}">
                     新增设备MAC信息
             </c:when>
             <c:otherwise>
                      编辑设备MAC信息
             </c:otherwise>
            </c:choose>
        </p>
    </div>

    <div class="wrapper">
        <!-- Note -->
      <spring-form:form commandName="boxmac"  cssClass="form"  method="post">
       	<fieldset>
      	<input type="hidden" name="boxmacUuid" value="${boxmac.uuid}"/>
        <div class="widget">
             <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/list.png" alt="" class="titleIcon"><h6>MAC信息</h6></div>
             <div class="formRow">
                 <label>MAC地址:</label>
                 <div class="formRight">
                 <spring-form:input path="mac" maxlength="17" />
                 <spring-form:errors path="mac"  cssClass="nNote nError hideit" style="margin: 5px 0px 0px 0px;"/>
                 </div>
                 <div class="clear"></div>
             </div>
             <div class="formRow">
                 <label>状态</label>
                 <div class="formRight">
                 	<select id="macStatus" name="macStatus" style="width:100%">
                 		<c:choose>
                 			<c:when test="${empty boxmac.macStatus}">
                 			<c:forEach items="${status}" var="loop" varStatus="counter">
	                 			<option value="${loop.value}" >${counter.count}. ${loop.label}</option>
	                 		</c:forEach>
                 			</c:when>
                 			<c:otherwise>
                 			<c:forEach items="${status}" var="loop" varStatus="counter">
	                 			<option value="${loop.value}" ${boxmac.macStatus == loop.value ?'selected':'' }>${counter.count}. ${loop.label}</option>
	                 		</c:forEach>
                 			</c:otherwise>
                 		</c:choose>
                 		
                 	</select>
                 	<spring-form:errors path="macStatus"  cssClass="nNote nError hideit" style="margin: 0px 0px 0px 10px;float:right"/>
                 </div>
                 <div class="clear"></div>
             </div>
              <div class="formSubmit">
                  <input type="button" value="返回" class="greenB" onclick="window.location.href='${pageContext.request.contextPath}/backend/boxmacindex.html'"/>
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