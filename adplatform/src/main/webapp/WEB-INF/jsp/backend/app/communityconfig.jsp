<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/jquery.jstree.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/_lib/jquery.cookie.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/_lib/jquery.hotkeys.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/htmlstyle.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/tz_page.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tz_page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.js"></script>    
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
            <p><strong>提示: </strong>对需要升级播放内容的小区进行选取配置</p>
        </div>
		
		<div class="widgets">
			<div class="oneThree">
		        <div class="widget">
		        
		            <div class="title">
		                <img src="${pageContext.request.contextPath}/images/icons/dark/users.png" alt="" class="titleIcon" /><h6>地区</h6>
		            </div>
		            <div id="tree" style="clear : both;height: 150px; overflow-y: auto;"></div>
		        </div>
	
		       
	   		</div>
	   	<div class="twoOne">
	   		<!-- 小区选取区域 -->
	   		 <div class="widget">
		            <div class="title">
		                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
		                <h6 id="areaName">小区</h6>
		            </div>
		            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
		                <thead>
		                <td width="20%">
	                        <input id="selectAll" type="checkbox" onclick="selectAllBox();"/>
	                    </td>
		                <td width="40%">创建时间</td>
		                <td width="40%">小区名称</td>
		                </thead>
		                <tbody id="communityBody">
		                
		                </tbody>
		            </table>
		                
		           
		   		 <div class="my-table-footer">
		            <div class="tPagination" style="text-align:center">
		            	<ul id="tablePaging" style="box-shadow: 0px 0px 0px;">
		                </ul>
		     		</div>
		     		<div class="table_process">
		     			<a class="button dblueB" title="" href="javascript:void(0);" onclick="addCommunities();"><span>添加所选</span></a>
		     		</div>
	            	<div class="clear"></div>
	            </div>
	        	</div>
	        
	        <div class="widget">
                 <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/refresh4.png" alt="" class="titleIcon"><h6>已选取小区展示</h6></div>
                 <div class="updates" id="choosedcommunity-list" style="max-height:800px;overflow-y:auto">
                 <%-- 更新时初始化 --%>
                 <c:forEach items="${listCAUtils}" var="listCAUtil">
                 	<div class="newUpdate" data-uuid='["${listCAUtil.uuid}"]'>
                         <div class="uDone">
                             <strong>${listCAUtil.name}</strong>
                             <span>${listCAUtil.areaFullPath}</span>
                         </div>
                         <div class="uDate">
                         	<a href="javascript:void(0);" class="tipS img-btn" original-title="移除" onclick="removeBoxDiv(this);">
                         		<img src="${pageContext.request.contextPath}/images/icons/remove_icon.png" width="16" height="16">
                         	</a></div>
                         <div class="clear"></div>
                     </div>	
                     </c:forEach>
                 </div>
                 <div class="my-table-footer">
		     		<div class="table_process">
		     			<a class="button dblueB" title="" href="javascript:void(0);" onclick="saveChoosedCommunity();"><span>保存配置</span></a>
		     			<a class="button dblueB" title="" href="javascript:void(0);" onclick="skipCommunity();"><span>跳过社区配置</span></a>
		     		</div>
	            	<div class="clear"></div>
	            </div>
             </div>	
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/contextPath.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/paging/numberPaging.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/update/community/areamagage.js"></script>
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
	function refreshCommunities(currentPage, rows){
	    jQuery.ajax({
	        type: "post",
	        url: 'getcommunities.html',
	        data: {
	            areaUuid: select_area_uuid ? select_area_uuid : '',
	            name: '',
	            currentPage: currentPage,
	            rows: rows
	        },
	        success:function(result){
	            if (result != null && typeof(result) != 'undefined') {
	                var list = result.rows;
	                var tableHtml = '';
	                if (list != null && typeof(list) != 'undefined') {
	                    for (var i = 0; i < list.length; i++) {
	                        var comment = list[i].comment ? list[i].comment : '-';
	                        var abbreviation = list[i].abbreviation ? list[i].abbreviation : '-';
	                        tableHtml += '<tr data-communityid=\'["'+list[i].uuid+'"]\'>';
	                        tableHtml+='<td align="center"><input type="checkbox" name="selectCommunityInfo" value="'+list[i].uuid+'"/></td>'
	                        tableHtml += '<td>' + list[i].timestamp + '</td>';
	                        tableHtml += '<td>' + list[i].name + '</td>';
	                        tableHtml += '</tr>';
	                    }
	                }
	                var totalPage = Math.ceil(parseInt(result.total) / rows);
	                var pagingHtml = generatePagingHtml(currentPage, totalPage, getTableRows());
	                jQuery("#communityBody").html(tableHtml);
	                jQuery("#tablePaging").html(pagingHtml);
	                current_page = currentPage;
	            }
	        }
	    });
	};
    
    	
//

function selectAllBox() {
    var state = $("#selectAll").prop("checked");
    $("input[name='selectCommunityInfo']").each(function() {
        if (!state) {
            $(this).closest('.checker > span').removeClass('checked');
        } else {
            $(this).closest('.checker > span').addClass('checked');
        }
        $(this).prop("checked", state);
    });
};
function addCommunities(){
	var communityName = $('#communityName').text();
	var areaName = $('#areaName').text();
	var path = '';
	if(areaName){
		path = areaName.substr(6);
	};
	var errorArray= [];
	 $("input[name='selectCommunityInfo']:checked").each(function() {
       	var communityUuid = $(this).val();
       	var $tr = $(this).parent().parent();
       	var name = $tr.find('td:eq(2)').text();
       	if(!checkIsExist(communityUuid)){
       		commSelectedList.push(communityUuid);   		
	       	createNewDiv( 'choosedcommunity-list',communityUuid, path,name);	        	
       	}else{
       		errorArray.push(name);
       	}
	  });
	 var len =errorArray.length;
	 if(len>0){
		 var msg = '';
		 for(var l=0;l<len;l++){
			 msg+=errorArray[l]+', '
		 }
		 msg = msg.slice(0, msg.length-2);
		 notyf.alert('<'+msg+'>已存在');
	 }
       		
};
function createNewDiv(ulId, uuid, path,name ){
	var divHtml='<div class="newUpdate" data-uuid=\'["'+uuid+'"]\'>'+
        '<div class="uDone">'+
   		'<strong>'+name+'</strong>'+
   		'<span>'+path+'</span>'+
		'</div>'+
		'<div class="uDate">'+
		'<a href="javascript:void(0);" class="tipS img-btn" original-title="移除" onclick="removeBoxDiv(this)">'+
		'<img src="${pageContext.request.contextPath}/images/icons/remove_icon.png" width="16" height="16">'+
		'</a></div>'+
		'<div class="clear"></div>'+
		'</div>';
	$('#'+ulId).append($(divHtml));
};

function removeBoxDiv(obj){
	var $boxDiv = $(obj).parent().parent();
	var uuid = $boxDiv.data('uuid')[0];
	$boxDiv.remove();
	var index = commSelectedList.indexOf(uuid);
	//如果需要配置的话，AJAX删除已存的
	commSelectedList = commSelectedList.del(index);
	console.log("length: "+commSelectedList.length);
};

// 查找的时候用的
function getChooseBoxDiv(){
	var boxId = 'choosedcommunity-list';
	var newDivList = $('#'+boxId).find('div.newUpdate');
	var result=[];
	newDivList.each(function(index, element){
		var temp = {};
		temp.mac = $(element).find('div.uDone > strong').text();
		temp.dom = element;
		console.log(temp.mac+"--"+temp.dom);
		result.push(temp);
	});
	
};

function getChooseComUuid(){
	var boxId = 'choosedcommunity-list';
	var newDivList = $('#'+boxId).find('div.newUpdate');
	var result=[];
	newDivList.each(function(index, element){
		var mac = $(element).data('uuid')[0];
		result.push(mac);
	});
	return result;
};
var commSelectedList = [];

function checkIsExist(obj){
	if(!obj) return true;
	if(commSelectedList.length == 0){
		commSelectedList = getChooseComUuid();   //初始化
	}
	for(var i=0;i<commSelectedList.length;i++){
		if(commSelectedList[i] == obj) return true;
	}
	return false;
};

function saveChoosedCommunity(){
	console.log(commSelectedList.length);
	if(commSelectedList.length<1){
		return;
	}
	
	$.ajax({
		type:'post',
		traditional:true,
		data:{communityList:commSelectedList, strategyUuid:'${strategyUuid}'},
		url:"${pageContext.request.contextPath}/backend/ajaxConifigSaveCommunity.html",
		success:function(data){
			window.location.href="${pageContext.request.contextPath}/backend/ConifigArea.html?strategyUuid=${strategyUuid}";
		}
	});
}

function skipCommunity(){
	 window.location.href="${pageContext.request.contextPath}/backend/ConifigArea.html?strategyUuid=${strategyUuid}";
}
</script>
</body>
</html>
