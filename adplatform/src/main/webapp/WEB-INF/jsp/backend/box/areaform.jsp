<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<div class="wrapper">
    <form class="form" action="${pageContext.request.contextPath}/backend/areaform.html" method="POST">
        <fieldset>
            <div class="widget">
                <div class="title">
                    <img src="${pageContext.request.contextPath}/images/icons/dark/alert.png" alt="" class="titleIcon" />
                    <h6>
                        <c:if test="${area.uuid != null}">
                            修改地区
                        </c:if>
                        <c:if test="${area.uuid == null}">
                            新增地区
                        </c:if>
                    </h6>
                </div>
                <input type="hidden" name="uuid" value="${area.uuid}"/>
                <input type="hidden" name="parentUuid" value="${area.parentUuid}"/>
                <div class="formRow">
                    <label for="name">地区名 [必填]</label>
                    <div class="formRight">
                        <input type="text" class="required" id="name" style="width: 100%;" name="name" value="${area.name}"/>
                        <span id="areaName_help" class="nNote nError hideit" style="display: none;line-height: 17px;margin: 5px 0px 0px 0px;width: 24%;">地区名不能为空</span>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="formSubmit">
                    <input type="button" value="保存" class="redB" onclick="saveArea(this.form);"/>
                </div>
                <div class="clear"></div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
