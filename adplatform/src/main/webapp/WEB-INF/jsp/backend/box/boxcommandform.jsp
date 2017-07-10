<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<div class="wrapper" id="commandDiv">
    <form action="#" class="form">
        <fieldset>
            <div class="widget">
                <div class="title">
                    <img src="${pageContext.request.contextPath}/images/icons/dark/alert.png" alt="" class="titleIcon" />
                    <h6>
                        添加机顶盒命令控制
                    </h6>
                </div>
                <div class="formRow">
                    <label for="commandValue">操作命令 [必填]</label>
                    <div class="formRight">
                        <select id="commandValue" name="commandValue" style="width: 100%" onchange="commandLineChange();">
                            <c:forEach items="${commands}" var="loop" varStatus="counter">
                                <option value="${loop.value}">${counter.count}, ${loop.label}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="formRow" id="planTimeDiv" style="visibility: hidden;">
                    <label for="commandValue">计划时间 [必填]</label>
                    <div class="formRight">
                        <select id="planTime1" name="planTime1" style="width: 20%" >
                            <c:forEach begin="9" end="22" step="1" var="counter">
                                <option value="${counter}">${counter}时</option>
                             </c:forEach>
                        </select>:
                        <select id="planTime2" name="planTime2" style="width: 20%" >
                            <option value="00">00分</option>
                            <option value="15">15分</option>
                            <option value="30">30分</option>
                            <option value="45">45分</option>
                        </select>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="formRow">
                    <label for="commandComment">备注</label>
                    <div class="formRight">
                        <input type="text" class="required" id="commandComment" style="width: 100%;" name="commandComment"/>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="formSubmit">
                    <input type="button" value="保存" class="redB" onclick="saveBoxCommand();"/>
                </div>
                <div class="clear"></div>
            </div>
        </fieldset>
    </form>
</div>

</body>
</html>
