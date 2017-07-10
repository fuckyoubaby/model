<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		            <div id="tree" style="clear : both;height: 250px; overflow-y: auto;"></div>
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
		                <tr>
			                <td width="10%"><input id="selectAll" type="checkbox" onclick="selectAllBox();"/></td>
			                <td width="40%">小区名称</td>
			                <td width="40%">创建时间</td>
		                </tr>
		                </thead>
		                <tbody id="communityBody">
		                <tr>
	                	<td colspan="3" style="padding:20px 0 30px 0;" align="center">
  							<p class="empty">请选择 [ 地区 ] ，列出该区域的小区</p>
  						</td>
  					</tr>
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
	        
	        <%--    小区选取结果展示区域           --%>
	        <div class="widget">
                 <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/refresh4.png" alt="" class="titleIcon"><h6>已选取小区展示</h6></div>
                 <div class="updates" id="choosedcommunity-list" style="max-height:800px;overflow-y:auto">
                 <c:choose>
                 <c:when test="${upgradeCommunities!=null && fn:length(upgradeCommunities)>0}">
                 <c:forEach items="${upgradeCommunities}" var="updateCommunity">
                 	<div class="newUpdate" data-uuid='["${updateCommunity.community.uuid}"]'>
                         <div class="uDone">
                             <strong>${updateCommunity.community.name}</strong>
                             <span>${fn:substringBefore(updateCommunity.community.areaFullPath, updateCommunity.community.name)}</span>
                         </div>
                         <div class="uDate">
                         	<a href="javascript:void(0);" class="tipS img-btn" original-title="移除" onclick="removeBoxDiv(this);">
                         		<img src="${pageContext.request.contextPath}/images/icons/remove_icon.png" width="16" height="16">
                         	</a></div>
                         <div class="clear"></div>
                     </div>
                 </c:forEach>
                 </c:when>
                 <c:otherwise>
                 </c:otherwise>
                 </c:choose>
                 </div>
                 <div class="my-table-footer">
		     		<div class="table_process">
		     			<a class="button greenB" title="" href="javascript:void(0);" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/contentUpdateConfig.html?contentUpgradeId=${contentUpgradeId}'"><span>返回</span></a>
		     			<a class="button dblueB" title="" href="javascript:void(0);" onclick="saveChoosedCommunity();"><span>保存配置</span></a>
		     			<a class="button dblueB" title="" href="javascript:void(0);" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/playConfigArea.html?contentUpgradeId=${contentUpgradeId}';"><span>下一步</span></a>
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
<div id="communityupload-dialog-confirm" title="系统对话框" class="myConfirm-dialog">
    <p style="margin:15px 60px;">
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
              已配置<span class="content"></span>, 是否要提交此配置信息？<br/>
    </p>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/contextPath.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/paging/numberPaging.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/update/community/areamagage.js"></script>
<script type="text/javascript">
var notyf = new Notyf({delay:3000});
var contentUpgradeId = '${contentUpgradeId}';
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
	                        tableHtml += '<td align="center">' + list[i].timestamp + '</td>';
	                        tableHtml += '<td align="center">' + list[i].name + '</td>';
	                        tableHtml += '</tr>';
	                    }
	                }
	                var totalPage = Math.ceil(parseInt(result.total) / rows);
	                var pagingHtml = generatePagingHtml(currentPage, totalPage, getTableRows());
	                jQuery("#communityBody").html(tableHtml);
	                jQuery("#tablePaging").html(pagingHtml);
	                current_page = currentPage;
	                $("#selectAll").prop("checked",false).closest('.checker > span').removeClass('checked');
	            }
	        }
	    });
	};

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
	$('.tipS').tipsy({gravity: 's',fade: true});
};

function removeBoxDiv(obj){
	var $boxDiv = $(obj).parent().parent();
	var uuid = $boxDiv.data('uuid')[0];
	$boxDiv.remove();
	var index = commSelectedList.indexOf(uuid);
	commSelectedList = commSelectedList.del(index);
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
		commSelectedList = getChooseComUuid();
	}
	for(var i=0;i<commSelectedList.length;i++){
		if(commSelectedList[i] == obj) return true;
	}
	return false;
};

function saveChoosedCommunity(){
	var dialogId ='#communityupload-dialog-confirm';
	var $dialog = $(dialogId);
	var $span = $dialog.find('span.content:eq(0)');
	$span.text(commSelectedList.length+'个小区');
	var confirmButton = {
			'确认': function() {
				$.ajax({
					type:'post',
					traditional: true,
					data:{communityList:commSelectedList, contentUpgradeId:contentUpgradeId},
					url:"${pageContext.request.contextPath}/backend/ajaxPlayConifigSaveCommunity.html",
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
				$( this ).dialog( "close" );
			}
		};
	dialogInitAndOpen($dialog, confirmButton);
};
</script>
</body>
</html>
