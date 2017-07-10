<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
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
	     			<a class="button dblueB" title="" href="javascript:void(0);" onclick="saveChoosedArea();"><span>保存配置</span></a>
	     			<a class="button dblueB" title="" href="javascript:void(0);" onclick="skipArea();"><span>跳过地区配置</span></a>
	     		</div>
            	<div class="clear"></div>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/ui/jquery.collapsible.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/contextPath.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tz_page.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.js"></script>    

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jstree-3.3/jstree.min.js"></script>
<script type="text/javascript">
var notyf = new Notyf({delay:3000});
var playUpgradeId = '${playUpgrade}';
Array.prototype.del=function(n) {
	//n表示第几项，从0开始算起。
	//prototype为对象原型，注意这里为对象增加自定义方法的方法。
	if(n<0)//如果n<0，则不进行任何操作。
		return this;
	else
	return this.slice(0,n).concat(this.slice(n+1,this.length));
	/*
	　　　concat方法：返回一个新数组，这个新数组是由两个或更多数组组合而成的。
	　　　　　　　　　这里就是返回this.slice(0,n)/this.slice(n+1,this.length)
	　　 　　　　　　组成的新数组，这中间，刚好少了第n项。
	　　　slice方法： 返回一个数组的一段，两个参数，分别指定开始和结束的位置。
	　　*/
	};
	
   $(function(){
	   var $tree = $("#tree");
	   $.getJSON('ajaxStrategyGetAreas.html?strategyUuid=${strategyUuid}', function(result){
		   var choosearray = result.choosedarray;
		   $tree.jstree({
/* 			   "json_data":{
		            "data": data
		        },
		        "themes" : {
		            "theme" : "classic",
		            "dots" : true,
		            "icons" : true
		        },
		        "checkbox" : {"three_state": false},
		        "plugins" : ["themes","json_data","ui","core","crrm", "cookies", "checkbox"] */
		        'plugins' : [ "checkbox" ], //出现选择框
                'checkbox': { cascade: "", three_state: true }, //不级联
                'core': {
                    'data': result.areas,
                    "themes": {
                        "responsive": false
                    }
                }
		   }).bind("ready.jstree",function(e,data){
		   	   //initChoose(data.instance, choosearray);
		   	   data.instance.check_node(choosearray);
		   });
	   });
   });
    	
function  initChoose(obj, array){
	obj.check_node(array);
};

function showSelectedNode(){
	var outList = $('#tree').jstree("get_checked",false);
	console.log(outList);
    return outList;
};

function initSelectedNode(){
	var $tree= $('#tree');
	var liList = $tree.find('li');
	$.each(liList, function(index, element){
		var $element = $(element);
		console.log($element);
		if($element.attr("id") == "201701200939533987558"){
			$tree.jstree("check_node",$element);
		}
	});
}

// 查找的时候用的
function getChooseAreaUuid(){
	var boxId = 'choosedcommunity-list';
	var newDivList = $('#'+boxId).find('div.newUpdate');
	var result=[];
	newDivList.each(function(index, element){
		var mac = $(element).data('uuid')[0];
		result.push(mac);
	});
	return result;
};
var areaSelectedList = [];

function checkIsExist(obj){
	if(!obj) return true;
	if(areaSelectedList.length == 0){
		areaSelectedList = getChooseComUuid();
	}
	for(var i=0;i<areaSelectedList.length;i++){
		if(areaSelectedList[i] == obj) return true;
	}
	return false;
};

function saveChoosedArea(){
	areaSelectedList = showSelectedNode();
	console.log(areaSelectedList.length);
	
	if(areaSelectedList.length<1){
		return;
	}
	$.ajax({
		type:'post',
		traditional:true,
		data:{areaList:areaSelectedList, strategyUuid:'${strategyUuid}'},
		url:"${pageContext.request.contextPath}/backend/ajaxStrategyConifigSaveArea.html",
		success:function(data){
			window.location.href="${pageContext.request.contextPath}/backend/managestrategy.html";
		}
	});
}

function skipArea(){
	window.location.href="${pageContext.request.contextPath}/backend/managestrategy.html";
}
</script>
</body>
</html>
