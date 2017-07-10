 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.custom.js" type="text/javascript"></script>
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

    <%--元数据管理******************************************************--%>

    <div class="wrapper">
        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
                <h6 id="configure">${community.areaFullPath} </h6>
                <div class="num">
                    <a class="greyNum" title="" href="#" onclick="addCommunityMetaDataDialog();"><span>  +  修改设置</span></a>
                </div>
            </div>

            <c:set var="metaData" value="${community.metaData}"/>
            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                    <td width="50%">开关机时间</td>
                    <td width="50%">状态上报时间间隔</td>
                </thead>
                <tbody>
                    <tr>
                        <td style="text-align: center"><span class="red f11">${metaData.startTime} | ${metaData.endTime}</span></td>
                        <td style="text-align: center"><span class="red f11">${metaData.heartInterval}秒</span></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <%--主要内容*******************************************************--%>

    <div class="wrapper">
        <div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
                <h6 id="areaName">${community.areaFullPath}</h6>
                <div class="num">
                    <a class="greyNum" title="" href="${pageContext.request.contextPath}/backend/boxcommandmanagement.html?communityUuid=${community.uuid}"><span>  < 查看所有投影仪历史</span></a>
                    <a class="greyNum" title="" href="#" onclick="addBoxCommand();"><span>  +  添加投影仪控制操作</span></a>
                    <a class="greyNum" title="" href="#" onclick="addBoxDialog('add', '');"><span>  +  添加投影仪</span></a>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                <thead>
                    <td width="3%">
                        <input id="selectAll" type="checkbox" onclick="selectAllBox();"/>
                    </td>
                    <td width="3%">
                        #
                    </td>
                    <td width="12%">创建时间</td>
                    <td width="10%">MAC</td>
                    <td width="5%">版本</td>
                    <td width="20%">位置</td>
                    <td width="12%">最新状态更新</td>
                    <td width="5%">CPU</td>
                    <td width="5%">内存</td>
                    <td width="5%">磁盘</td>
                    <td width="5%">在线</td>
                    <td>操作</td>
                </thead>
                <tbody id="communityBody">
                    <c:forEach items="${boxes}" var="box" varStatus="counter">
                        <tr>
                            <td><input type="checkbox" name="selectBoxInfo" value="${box.mac}"/></td>
                            <td>${counter.count}</td>
                            <td>${box.timestamp}</td>
                            <td>${box.mac}</td>
                            <td>${box.version}</td>
                            <td>${box.note}</td>
                            <td id="time_${box.uuid}"></td>
                            <td id="cpu_${box.uuid}"></td>
                            <td id="mem_${box.uuid}"></td>
                            <td id="disk_${box.uuid}"></td>
                            <td id="status_${box.uuid}"></td>
                            <td>
                                <a class="button redB" href="javascript:void(0);" onclick="deleteBoxInfo('${box.uuid}')"><span>删除</span></a>&nbsp;
                                <a class="button blueB" href="javascript:void(0);" onclick="addBoxDialog('edit', '${box.uuid}');"><span>编辑</span></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
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

<div id="boxdelete-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要删除该投影仪信息?
    </p>
</div>

<div id="boxcommand-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请选择你需要操作的设备?
    </p>
</div>

<div id="savecommand-dialog-confirm" title="系统对话框" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认是否这些投影仪进行该操作?
    </p>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript">

    var communityUuid = '${community.uuid}';
    var boxSelectTemp = '';

//    系统参数设置部分

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

    function openModalPopup(obj) {
        modalPopup(obj.align, obj.top, obj.width, obj.padding, obj.disableColor, obj.disableOpacity, obj.backgroundColor, obj.borderColor, obj.borderWeight, obj.borderRadius, obj.fadeOutTime, obj.source, obj.loadingImage, obj.callback);
    }

//    盒子数据动态更新部分

    $(document).ready(function() {
        refreshBoxInfo();
        setInterval("refreshBoxInfo()", 10000);
    });

    function refreshBoxInfo(){
        jQuery.ajax({
            type: "post",
            url: "getboxinfo.html",
            data: {
                communityUuid: communityUuid
            },
            success:function(result){
                if (result != null && typeof(result) != 'undefined') {
                    var list = result.rows;
                    if (list != null && typeof(list) != 'undefined') {
                        for (var i = 0; i < list.length; i++) {
                            var uuid = list[i].uuid;
                            var status = "<span class=\"red f11\">离线</span>";
                            if(list[i].isOnline) {
                                status = "<span class=\"green f11\">在线</span>";
                            }

                            jQuery("#time_" + uuid).html(list[i].lastUpdate);
                            jQuery("#cpu_" + uuid).html(list[i].cpu + "%");
                            jQuery("#mem_" + uuid).html(list[i].mem + "%");
                            jQuery("#disk_" + uuid).html(list[i].disk + "%");
                            jQuery("#status_" + uuid).html(status);
                        }
                    }
                }
            }
        });
    }

//    盒子信息部分

    function deleteBoxInfo(boxInfoUuid) {
        jQuery("#boxdelete-dialog-confirm").css("visibility", "visible");
        jQuery("#boxdelete-dialog-confirm").dialog({
                resizable: false,
                height:160,
                width:300,
                modal: true,
                buttons: {
                    "确认": function() {
                        jQuery("#boxdelete-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                        window.location.href = '${pageContext.request.contextPath}/backend/boxinfodelete.html?boxInfoUuid=' + boxInfoUuid + '&communityUuid=' + communityUuid;
                    },
                    "取消": function() {
                        jQuery("#boxdelete-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                    }
                }
            });
    }

    function addBoxDialog(method, boxUuid) {
        settings.top = 100;
        settings.source = '${pageContext.request.contextPath}/backend/boxinfoform.html?method=' + method + '&boxUuid=' + boxUuid + '&communityUuid=' + communityUuid;
        settings.callback = function(){
        	var url = '${pageContext.request.contextPath}/backned/ajaxBoxMacsLoad.html'
        	return function(){
        		var $mac = $("#mac");
        		var dataType='json';
         	   	$mac.autocomplete({
         		      source: function( request, response ) {
         		        $.ajax({
         		          url: url,
         		          dataType: dataType,
         		          data: {
         		            keyword: request.term
         		          },
         		          success: function( data ) {
         		            response( $.map( data, function( item ) {
         		              return {
         		                label: item.value,
         		                value: item.value
         		              }
         		            }));
         		          }
         		        });
         		      },
         		      minLength: 2, 
         	   });
        	};
        }();
        openModalPopup(settings);
    }
	var checkMacRight = function(mac){
		var reg_name=/^[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}$/; 
		return reg_name.test(mac);
	};
	
	function displayAllhelper(){
		var arry = ["mac_empty_help","mac_error_help","mac_duplicate_help"];
		for(var i=0;i<arry.length;i++){
			$('#'+arry[i]).css("display","none");
		}
	}
	
    function saveBoxInfo() {
        var communityUuid = jQuery("#communityUuid").val();
        var boxUuid = jQuery("#boxUuid").val();
        var mac = jQuery("#mac").val();
        var note = jQuery("#note").val();
		var oldDulpicuteText = jQuery("#mac_duplicate_help").text();
		displayAllhelper();
        if (mac == null || mac == '') {
            jQuery("#mac_empty_help").css("display", "block");
        } else if(!checkMacRight(mac)){
        	jQuery("#mac_error_help").css("display", "block");
        }else{
            $.ajax({
                url : '${pageContext.request.contextPath}/backend/checkboxmacduplicate.html',
                type : "POST",
                data : {
                    boxUuid : boxUuid ? boxUuid : '',
                    mac : mac ? mac : ''
                },
                dataType : "json",
                cache : false,
                success : function(data) {
                    jQuery("#box_mac_help").css("display", "none");
                    if(data.result) {
                    	var $help = jQuery("#mac_duplicate_help");
                    	var text = data.msg? data.msg: oldDulpicuteText;
                    	$help.text(text).css("display", "block");
                    } else {
                        jQuery("#mac_duplicate_help").css("display", "none");
                        $.ajax({
                            url : '${pageContext.request.contextPath}/backend/boxinfoform.html',
                            type : "POST",
                            data : {
                                communityUuid : communityUuid,
                                uuid : boxUuid ? boxUuid : '',
                                mac : mac ? mac : '',
                                note : note ? note : ''
                            },
                            cache : false,
                            success : function(result) {
                                window.location.href = '${pageContext.request.contextPath}/backend/boxinfomanagement.html?communityUuid=' + communityUuid;
                            }
                        });
                    }
                }
            });

        }
    }

//    社区配置部分
    function addCommunityMetaDataDialog() {
        settings.top = 100;
        settings.source = '${pageContext.request.contextPath}/backend/communitymetadataform.html?communityUuid=' + communityUuid;
        openModalPopup(settings);
    }

    function saveCommunityMetaData() {
        var communityUuid = jQuery("#communityUuid").val();
        var metaDataUuid = jQuery("#metaDataUuid").val();
        $.ajax({
            url : '${pageContext.request.contextPath}/backend/communitymetadataform.html',
            type : "POST",
            data : {
                communityUuid : communityUuid,
                metaDataUuid : metaDataUuid ? metaDataUuid : ''
            },
            cache : false,
            success : function(result) {
                window.location.href = '${pageContext.request.contextPath}/backend/boxinfomanagement.html?communityUuid=' + communityUuid;
            }
        });
    }
    
     function selectAllBox() {
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

    function addBoxCommand() {
        boxSelectTemp = "";
        $('input:checkbox[name=selectBoxInfo]:checked').each(function(i) {
            if (0 == i) {
                boxSelectTemp = $(this).val();
            } else {
                boxSelectTemp += ("," + $(this).val());
            }
        });

        if(boxSelectTemp == '') {
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
            addBoxCommandDialog();
        }
    }

    function addBoxCommandDialog() {
        settings.top = 100;
        settings.source = '${pageContext.request.contextPath}/backend/boxcommandform.html';
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

    function saveBoxCommand() {
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
                            url : '${pageContext.request.contextPath}/backend/boxcommandform.html',
                            type : "POST",
                            data : {
                                selectedBoxes : boxSelectTemp,
                                commandValue : commandValue ? commandValue : '',
                                commandComment : commandComment ? commandComment : '',
                                planTime1 : planTime1 ? planTime1 : '',
                                planTime2 : planTime2 ? planTime2 : ''
                            },
                            cache : false,
                            success : function(result) {
                                window.location.href = '${pageContext.request.contextPath}/backend/boxinfomanagement.html?communityUuid=' + communityUuid;
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
