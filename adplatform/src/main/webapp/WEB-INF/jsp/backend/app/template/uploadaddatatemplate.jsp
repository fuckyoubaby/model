<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
	<c:when test="${uploadAdDatas!=null && fn:length(uploadAdDatas)>0}">
		<c:forEach items="${uploadAdDatas}" var="box" varStatus="counter">
	          <tr data-itemcount='${itemcount}' data-boxuuid='["${box.uuid}"]'>
	              <td>${box.mac}</td>
	             <%--  <td>${fn:split(box.communityPath,'/')}</td> --%>
	              
	              <td>${box.communityPath}</td>
	              <td>${box.note}</td>
	             <td><a href="${pageContext.request.contextPath}/backend/getuploadaddata.html?mac=${box.mac}">统计</a></td>
	             <!--  <td><a onclick="showDialog();">统计</a></td> -->
	          </tr>
	      </c:forEach>
	</c:when>
	<c:otherwise>
	<tr><td colspan="6" style="padding:60px;tex-align:center;">
  		<h4 class="empty">提示：暂无您所需要的数据....</h4>
  		</td>
  	</tr>
	</c:otherwise>
</c:choose>
	
<c:if test="${uploadAdDatas==null || fn:length(uploadAdDatas) ==0}">

</c:if>