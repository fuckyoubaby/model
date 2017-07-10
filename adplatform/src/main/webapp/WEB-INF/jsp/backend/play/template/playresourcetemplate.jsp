<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${adverResourceDTOs!=null && fn:length(adverResourceDTOs)>0}">
	 <c:forEach items="${adverResourceDTOs}" var="adverResource" varStatus="counter">
        <tr data-resourceuuid='["${adverResource.uuid}"]' data-itemcount='${itemcount}'>
             <td><span class="green f11">${adverResource.name}</span></td>
             <td class="taskPr"><a href="#" class="tipS img-show" original-title="点击预览" data-uuid='["${adverResource.uuid}"]'>${adverResource.path}</a></td>
             <td align="center">${adverResource.advertiser}/${adverResource.agents}</td>
             <td align="center"> ${ adverResource.type }</td>
             <td class="actBtns">
   			<a href="javascript:void(0);" class="tipS add" original-title="添加到资源列表"  ><img src="${pageContext.request.contextPath}/images/icons/valider_yes.png" alt=""></a>
            <a href="javascript:void(0);" onclick="resourceUpdateLink(this);"  class="tipS" original-title="详情" ><img src="${pageContext.request.contextPath}/images/icons/infos_info.png" alt=""></a>
            <a href="javascript:void(0);" class="tipS" original-title="清除" onclick="resourceDelete('${adverResource.uuid}',this)"><img src="${pageContext.request.contextPath}/images/icons/croix_delete.png" alt=""></a>
            </td>
        </tr>
      </c:forEach>
</c:if>
<c:if test="${adverResourceDTOs==null || fn:length(adverResourceDTOs) ==0}">
<tr><td colspan="5" style="padding:60px;text-align:center;">
  	<h4 class="empty">提示：暂无您所需要的数据....</h4>
  </td></tr>
</c:if>