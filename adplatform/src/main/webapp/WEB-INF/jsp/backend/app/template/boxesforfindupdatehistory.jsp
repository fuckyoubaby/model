<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
	<c:when test="${boxes!=null && fn:length(boxes)>0}">
		<c:forEach items="${boxes}" var="box" varStatus="counter">
	          <tr data-itemcount='${itemcount}' data-boxuuid='["${box.uuid}"]'>
	              <td>${counter.count}</td>
	              <td>${box.timestamp}</td>
	              <td>${box.mac}</td>
	              <c:choose>
	              	<c:when test="${empty needPath}">
	              		 <td>${box.note}</td>
	              	</c:when>
	              	<c:otherwise>
	              		<td>${box.communityPath}-${box.note}</td>
	              	</c:otherwise>
	              </c:choose>
	              <td>
	              <a class="button blueB" href="javascript:void(0);" onclick="window.location.href = '${pageContext.request.contextPath}/backend/appUpdateHistoryTable.html?macNumber=${box.mac }'"><span>查看升级历史</span></a>
	              </td>
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
	
<c:if test="${boxes==null || fn:length(boxes) ==0}">

</c:if>