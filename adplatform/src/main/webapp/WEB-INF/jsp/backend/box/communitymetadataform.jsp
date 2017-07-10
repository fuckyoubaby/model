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
                        修改${community.areaFullPath}投影仪配置
                    </h6>
                </div>
                <input type="hidden" id="communityUuid" name="communityUuid" value="${community.uuid}"/>
                <c:set var="metaData" value="${community.metaData}"/>
                <div class="formRow">
                    <label for="metaDataUuid">选择配置 [必填]</label>
                    <div class="formRight">
                        <select id="metaDataUuid" name="metaDataUuid" style="width: 100%">
                            <c:forEach items="${metaDatas}" var="loop" varStatus="counter">
                                <option value="${loop.uuid}" <c:if test="${loop.uuid == metaData.uuid}">selected="true"</c:if>>${counter.count}, ${loop.note}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="formSubmit">
                    <input type="button" value="保存" class="redB" onclick="saveCommunityMetaData();"/>
                </div>
                <div class="clear"></div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
