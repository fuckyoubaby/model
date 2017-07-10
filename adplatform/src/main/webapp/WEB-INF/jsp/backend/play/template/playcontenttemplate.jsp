<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<c:if test="${playContentDTOs!=null && fn:length(playContentDTOs)>0}">
	 <c:forEach items="${playContentDTOs}" var="playContent" varStatus="counter">
        <tr data-itemuuid='["${playContent.uuid}"]' data-itemcount='${itemcount}' >
	          <td><span class="lGrey f11">${playContent.name}</span></td>
	          <td align="center">${playContent.version}</td>
	          <td align="center"> ${playContent.amount} </td>
	          <td align="center"> <fmt:formatDate value="${playContent.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
	          <td class="actBtns">
	          <a href="#" class="tipS" original-title="选取内容" onclick="contentConfig(this)"><img src="${pageContext.request.contextPath}/images/icons/valider_yes.png" alt=""></a>
	          <a href="${pageContext.request.contextPath}/backend/playcontent.html?playContentId=${playContent.uuid}&contentUpgradeId=${contentUpgradeId}" class="tipS" original-title="详情"  ><img src="${pageContext.request.contextPath}/images/icons/infos_info.png" alt=""></a>
	          <a href="#" class="tipS" original-title="清除" onclick="contentDelete('${playContent.uuid}',this)"><img src="${pageContext.request.contextPath}/images/icons/croix_delete.png" alt=""></a></td>
	      </tr>
      </c:forEach>
</c:if>
<c:if test="${playContentDTOs==null || fn:length(playContentDTOs) ==0}">
<tr><td colspan="5" style="padding:60px;text-align:center;">
  	<h4 class="empty">提示：暂无您所需要的数据....</h4>
  </td></tr>
</c:if>