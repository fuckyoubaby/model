<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/jquery.jstree.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/_lib/jquery.cookie.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/_lib/jquery.hotkeys.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/htmlstyle.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/left.jsp"/>

<%--内容部分***********************************************************--%>

<div id="rightSide">
    <%--开头部分*******************************************************--%>

    <jsp:include page="/WEB-INF/decorators/head.jsp"/>

    <%--主要内容*******************************************************--%>

    <div class="wrapper">
        <!-- Note -->
        <div class="nNote nInformation hideit">
            <p><strong>提示: </strong>下面显示了所有的地区目录，点击地区目录，可以查看相应目录下的广告配置信息，并可进一步对广告信息进行配置</p>
        </div>

        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/users.png" alt="" class="titleIcon" /><h6>地区管理</h6>

                 <div class="num">
                    <input type="text" id="box_info_field" placeholder="请输入投影仪MAC或小区名字" style="width: 200px; height: 25px"/>&nbsp;
                    <a class="greyNum" title="" href="#" onclick="searchBoxInfo();"><span>  >  查询投影仪</span></a>
                </div>
            </div>
            <div id="tree" style="clear : both;height: 150px; overflow-y: auto;"></div>
        </div>

        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
                <h6 id="areaName">小区</h6>
                <div class="num">
                    <a class="greyNum" title="" href="#" onclick="addCommuCommand();"><span>  +  添加投影仪控制操作</span></a>
                    <a class="greyNum" title="" href="#" onclick="addCommunityDialog();"><span>  +  添加小区</span></a>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                <td width="3%">
                        <input id="selectAll" type="checkbox" onclick="selectAllCommunity();"/>
                </td>
                <td width="5%">序号</td>
                <td width="20%">创建时间</td>
                <td width="15%">小区名称</td>
                <td width="20%">小区简称</td>
                <td width="20%">备注</td>
                <td>操作</td>
                </thead>
                <tbody id="communityBody"></tbody>
            </table>
            <div class="tPagination" style="text-align: center;">
                <ul id="tablePaging" style="box-shadow: 0px 0px 0px;">
                </ul>
            </div>
        </div>
    </div>
    <%--结尾部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>
</div>
<div id="boxsearch-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        没有该投影仪相关信息，请确认后再查询。
    </p>
</div>

<div id="boxcommand-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请选择你需要操作的小区?
    </p>
</div>

<div id="savecommand-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认是否这些小区进行该操作?
    </p>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/contextPath.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/paging/numberPaging.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/box/areamagage.js"></script>
<script type="text/javascript">

    var communitySelectTemp = '';
    
    var settings = {
        align : 'center',									//Valid values, left, right, center
        top : 150, 											//Use an integer (in pixels)
        width : 600, 										//Use an integer (in pixels)
        padding : 10,										//Use an integer (in pixels)
        backgroundColor : 'white', 						    //Use any hex code
        source : '', 				    					//Refer to any page on your server, external pages are not valid e.g. http://www.google.co.uk
        borderColor : '#333333', 							//Use any hex code
        borderWeight : 4,									//Use an integer (in pixels)
        borderRadius : 5, 									//Use an integer (in pixels)
        fadeOutTime : 300, 									//Use any integer, 0 : no fade
        disableColor : '#666666', 							//Use any hex code
        disableOpacity : 40, 								//Valid range 0-100
        loadingImage : ''
    };
    
    function searchBoxInfo() {
        var boxInfo = $("#box_info_field").val();
        if(boxInfo != null && boxInfo != '') {
            $.ajax({
            type : "POST",
            url : getContextPath() + "/backend/searchboxinfo.html",
            data:{boxInfo:boxInfo},
            dataType : "json",
            success : function(result) {
                //获得数据
                var uuid = result.uuid;

                if(uuid == '-1') {
                    jQuery("#boxsearch-dialog-confirm").css("visibility", "visible");
                    jQuery("#boxsearch-dialog-confirm").dialog({
                            resizable: false,
                            height:160,
                            width:300,
                            modal: true,
                            buttons: {
                                "取消": function() {
                                    jQuery("#boxsearch-dialog-confirm").css("visibility", "hidden");
                                    jQuery(this).dialog("close");
                                }
                            }
                        });
                } else {
                    window.location.href = '${pageContext.request.contextPath}/backend/boxinfomanagement.html?communityUuid=' + uuid;
                }
            }
        });
        }
    }
    
    //    命令控制部分
    function selectAllCommunity() {
        var state = $("#selectAll").prop("checked");
        $("input[name='selectBoxInfo']").each(function() {
            if (!state) {
                $(this).closest('.checker > span').removeClass('checked');
            } else {
                $(this).closest('.checker > span').addClass('checked');
            }
            $(this).prop("checked", state);
        });
    }

    function addCommuCommand() {
        communitySelectTemp = "";
        $('input:checkbox[name=selectBoxInfo]:checked').each(function(i) {
            if (0 == i) {
                communitySelectTemp = $(this).val();
            } else {
                communitySelectTemp += ("," + $(this).val());
            }
        });

        if(communitySelectTemp == '') {
            jQuery("#boxcommand-dialog-confirm").css("visibility", "visible");
            jQuery("#boxcommand-dialog-confirm").dialog({
                    resizable: false,
                    height:160,
                    width:300,
                    modal: true,
                    buttons: {
                        "取消": function() {
                            jQuery("#boxcommand-dialog-confirm").css("visibility", "hidden");
                            jQuery(this).dialog("close");
                        }
                    }
                });
        } else {
            addCommuCommandDialog();
        }
    }

    function addCommuCommandDialog() {
        settings.top = 100;
        settings.source = '${pageContext.request.contextPath}/backend/Commucommandform.html';
        openModalPopup(settings);
    }
    
    function commandLineChange() {
        var commandValue = jQuery("#commandValue").val();
        if(commandValue == 'C_RESOURCE_PLAN') {
            jQuery("#planTimeDiv").css("visibility", "visible");
        } else {
            jQuery("#planTimeDiv").css("visibility", "hidden");
        }
    }
    
    function saveCommuCommand() {
        jQuery("#savecommand-dialog-confirm").css("visibility", "visible");
        jQuery("#savecommand-dialog-confirm").dialog({
                resizable: false,
                height:160,
                width:300,
                modal: true,
                buttons: {
                    "确认": function() {
                        jQuery("#savecommand-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");

                        var commandValue = jQuery("#commandValue").val();
                        var commandComment = jQuery("#commandComment").val();
                        var planTime1 = jQuery("#planTime1").val();
                        var planTime2 = jQuery("#planTime2").val();

                        $.ajax({
                            url : '${pageContext.request.contextPath}/backend/Commucommandform.html',
                            type : "POST",
                            data : {
                                selectedCommus : communitySelectTemp,
                                commandValue : commandValue ? commandValue : '',
                                commandComment : commandComment ? commandComment : '',
                                planTime1 : planTime1 ? planTime1 : '',
                                planTime2 : planTime2 ? planTime2 : ''
                            },
                            cache : false,
                            success : function(result) {
                                if (result == null || typeof(result.result) == 'undefined' || result.result == 'false') {
                    				alert("saveCommond 失败！");
                				} else {
                    				refreshCommunities(current_page, tableRows);
                    				jQuery(this).dialog("close");
                				}
                				closePopup(settings.fadeOutTime);
                            }
                            
                        });
                    },
                    "取消": function() {
                        jQuery("#savecommand-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                    }
                }
            });
    }
</script>
</body>
</html>
