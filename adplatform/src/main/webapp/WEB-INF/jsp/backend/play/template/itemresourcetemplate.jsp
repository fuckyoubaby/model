<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${playItemDTOs!=null && fn:length(playItemDTOs)>0}">
	 <c:forEach items="${playItemDTOs}" var="playItem" varStatus="counter">
        <tr data-itemuuid='["${playItem.uuid}"]' data-itemcount='${itemcount}' >
	          <td><span class="lGrey f11">${playItem.name}</span></td>
	          <td > ${playItem.description} </td>
	          <td align="center"> ${playItem.amount} </td>
	          <td class="actBtns">
	          <a href="#" class="tipS add" original-title="添加到内容列表"><img src="${pageContext.request.contextPath}/images/icons/valider_yes.png" alt=""></a>
	          <a href="#" class="tipS" original-title="详情" onclick="configItemResource(this)" ><img src="${pageContext.request.contextPath}/images/icons/infos_info.png" alt=""></a>
	          <a href="#" class="tipS" original-title="清除" onclick="contentItemDelete('${playItem.uuid}',this)"><img src="${pageContext.request.contextPath}/images/icons/croix_delete.png" alt=""></a></td>
	      </tr>
      </c:forEach>
</c:if>
<c:if test="${playItemDTOs==null || fn:length(playItemDTOs) ==0}">
<tr><td colspan="6" style="padding:60px;text-align:center;">
  	<h4 class="empty">提示：暂无您所需要的数据....</h4>
  </td></tr>
</c:if>