<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<div class="wrapper">
    <form action="#" class="form">
        <fieldset>
            <div class="widget">
                <div class="title">
                    <img src="${pageContext.request.contextPath}/images/icons/dark/alert.png" alt="" class="titleIcon" />
                    <h6>
                        <c:if test="${box.uuid != null}">
                            修改投影仪
                        </c:if>
                        <c:if test="${box.uuid == null}">
                            新增投影仪
                        </c:if>
                    </h6>
                </div>
                <input type="hidden" id="boxUuid" name="boxUuid" value="${box.uuid}"/>
                <input type="hidden" id="communityUuid" name="communityUuid" value="${box.communityUuid}"/>
                <div class="formRow">
                    <label for="mac">MAC[必填]</label>
                    <div class="formRight">
                        <input type="text" class="required" id="mac" style="width: 100%;" name="mac" value="${box.mac}"/>
                        <span id="mac_empty_help" class="nNote nError hideit" style="display: none;line-height: 17px;margin: 5px 0px 0px 0px;width: 50%;">请输入必填字段信息</span>
                        <span id="mac_error_help" class="nNote nError hideit" style="display: none;line-height: 17px;margin: 5px 0px 0px 0px;width: 50%;">MAC格式错误</span>
                        <span id="mac_duplicate_help" class="nNote nError hideit" style="display: none;line-height: 17px;margin: 5px 0px 0px 0px;width: 50%;">MAC编号重复</span>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="formRow">
                    <label for="note">位置信息</label>
                    <div class="formRight">
                        <input type="text" class="required" id="note" style="width: 100%;" name="note" value="${box.note}" placeholder="如：1栋1单元1号"/>
                    </div>
                    <div class="clear"></div>
                </div>
                <c:choose>
                	<c:when test="${not empty box.uuid}">
                		 <div class="formRow">
		                    <label >SSID名称</label>
		                    <div class="formRight" style="line-height: 28px;text-indent: 2em;">
		                        <!--  <input type="text" class="required" id="ssidName" style="width: 100%;" name="ssidName" value="${box.ssidName}"/>
		                    	-->
		                    	${box.ssidName}
		                    </div>
		                    <div class="clear"></div>
		                </div>
		                <div class="formRow">
		                    <label for="mac">SSID密码</label>
		                    <div class="formRight" style="line-height: 28px;text-indent: 2em;">
		                       <%--  <input type="text" class="required" id="ssidPassword" style="width: 100%;" name="ssidPassword" value="${box.ssidPassword}"/> --%>
		                    	${box.ssidPassword}
		                    </div>
		                    <div class="clear"></div>
		                </div>	
                	</c:when>
                	<c:otherwise>
                	</c:otherwise>
                </c:choose>
               
                <div class="formSubmit">
                    <input type="button" value="保存" class="redB" onclick="saveBoxInfo();"/>
                </div>
                <div class="clear"></div>
            </div>
        </fieldset>
    </form>
</div>

</body>
</html>
