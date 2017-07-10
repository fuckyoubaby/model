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
                        <c:if test="${community.uuid != null}">
                            修改小区
                        </c:if>
                        <c:if test="${community.uuid == null}">
                            新增小区
                        </c:if>
                    </h6>
                </div>
                <input type="hidden" id="communityUuid" name="uuid" value="${community.uuid}"/>
                <input type="hidden" id="areaUuid" name="areaUuid" value="${community.areaUuid}"/>
                <div class="formRow">
                    <label for="communityName">小区名 [必填]</label>
                    <div class="formRight">
                        <input type="text" class="required" id="communityName" style="width: 100%;" name="name" value="${community.name}"/>
                        <span id="communityName_help" class="nNote nError hideit" style="display: none;line-height: 17px;margin: 5px 0px 0px 0px;width: 24%;">小区名不能为空</span>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="formRow">
                    <label for="abbreviation">小区简称</label>
                    <div class="formRight">
                        <input type="text" class="required" id="abbreviation" style="width: 100%;" name="abbreviation" value="${community.abbreviation}"/>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="formRow">
                    <label for="comment">备注</label>
                    <div class="formRight">
                        <textarea class="required" id="comment" name="comment" rows="6" cols="63">${community.comment}</textarea>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="formSubmit">
                    <input type="button" value="保存" class="redB" onclick="saveCommunity();"/>
                </div>
                <div class="clear"></div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
