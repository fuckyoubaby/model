<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <%-- <c:import url="/WEB-INF/decorators/jspart.jsp"/> --%>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.js" type="text/javascript"></script>
    <%-- <script src="${pageContext.request.contextPath}/js/jstree/_lib/jquery.cookie.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/_lib/jquery.hotkeys.js" type="text/javascript"></script>
    --%> 
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/htmlstyle.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/tz_page.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/jstree-3.3/themes/default/style.min.css" />
      <style>
    	.my-table-footer{border:1px solid #cdcdcd; border-left:0px;border-right:0px;height:57px}
    	.table_process{margin-top:16px;float:left;margin-left:18px;}
    </style>
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
            <p><strong>提示: </strong>对需要升级播放内容的区域进行选取配置</p>
        </div>
		<div class="widget">
            <div class="title">
                <img src="${pageContext.request.contextPath}/images/icons/dark/users.png" alt="" class="titleIcon" /><h6>地区</h6>
            </div>
            <div id="tree" style="clear : both;height: 150px; overflow-y: auto;"></div>
	        <div class="my-table-footer">
	     		<div class="table_process">
	     			<a class="button greenB" title="" href="javascript:void(0);" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/playConfigCommunity.html?contentUpgradeId=${contentUpgradeId}'"><span>上一步</span></a>
	     			<a class="button dblueB" title="" href="javascript:void(0);" onclick="saveChoosedArea();"><span>保存配置</span></a>
	     			<a class="button dblueB" title="" href="javascript:void(0);" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/contentupgradeinfo.html?contentUpgradeId=${contentUpgradeId}'"><span>下一步</span></a>
	     		</div>
            	<div class="clear"></div>
            </div>
		</div>
    </div>
    <%--结尾部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>
</div>

<div id="areaupload-dialog-confirm" title="系统对话框" class="myConfirm-dialog">
    <p style="margin:15px 60px;">
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
              已配置<span class="content"></span>, 是否要提交此配置信息？<br/>
    </p>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.4.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/ui/jquery.collapsible.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/contextPath.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tz_page.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.js"></script>    

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jstree-3.3/jstree.min.js"></script>
<script type="text/javascript">
var notyf = new Notyf({delay:3000});
var contentUpgradeId = '${contentUpgradeId}';

 $('.myConfirm-dialog').dialog({
	autoOpen:false,
    resizable: false,
    height:200,
    width:420,
    modal: true
});

function dialogInitAndOpen(dialogObj, confirmButton){
	var buttonProcess={
	        '取消': function() {
	            $(this).dialog('close');
	        }	
	};
	dialogObj.dialog({
		open:function(event,ui){
			$(this).dialog("option","buttons",$.extend(confirmButton, buttonProcess));
			$(this).parent().focus();
		} });
	dialogObj.dialog('open');
}	
$(function(){
   var $tree = $("#tree");
   $.getJSON('ajaxGetAreas.html?contentUpgradeId='+contentUpgradeId, function(result){
	   var choosearray = result.choosedarray;
	   $tree.jstree({
	        'plugins' : [ "checkbox" ], //出现选择框
               'checkbox': { cascade: "", three_state: true }, //不级联
               'core': {
                   'data': result.areas,
                   "themes": {
                       "responsive": false
                   }
               }
	   }).bind("ready.jstree",function(e,data){
	   	   data.instance.check_node(choosearray);
	   });
   });
});
    	

function  initChoose(obj, array){
	obj.check_node(array);
};
function showSelectedNode(){
	var outList = $('#tree').jstree("get_checked",false);
	return outList;
};

function initSelectedNode(){
	var $tree= $('#tree');
	$tree.jstree("uncheck_all");
	var liList = $tree.find('li');
	$.each(liList, function(index, element){
		var $element = $(element);
		if($element.attr("id") == "201701200939533987558"){
			$tree.jstree("check_node",$element);
		}
	});
}

var areaSelectedList = [];

function saveChoosedArea(){
	var dialogId ='#areaupload-dialog-confirm';
	var $dialog = $(dialogId);
	var $span = $dialog.find('span.content:eq(0)');
	areaSelectedList = showSelectedNode();
	$span.text(areaSelectedList.length+'个地区');
	var confirmButton = {
			'确认' : function(){
				$.ajax({
					type:'post',
					traditional: true,
					data:{areaList:areaSelectedList, contentUpgradeId:'${contentUpgradeId}'},
					url:"${pageContext.request.contextPath}/backend/ajaxPlayConifigSaveArea.html",
					success:function(data){
						if((typeof data).toLowerCase() == "object"){
							if(data.result){
								notyf.confirm(data.msg?data.msg:'配置成功');
							}else{
								notyf.alert(data.msg?data.msg:'配置失败');
							}
						}else{notyf.alert('请求出错, 请稍后重试! ');}
					}
				});
				$(this).dialog("close");
			}
	};
	dialogInitAndOpen($dialog, confirmButton);
};
</script>
</body>
</html>
