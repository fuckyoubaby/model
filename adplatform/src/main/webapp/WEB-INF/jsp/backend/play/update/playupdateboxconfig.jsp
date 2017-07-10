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
            <p><strong>提示: </strong>下面显示了所有的地区目录，点击地区目录，可以查看相应目录下的广告配置信息，并可进一步对广告信息进行配置</p>
        </div>
		
		<div class="widgets">
			<div class="oneThree">
		        <div class="widget">
		        
		            <div class="title">
		                <img src="${pageContext.request.contextPath}/images/icons/dark/users.png" alt="" class="titleIcon" /><h6>地区</h6>
		            </div>
		            <div id="tree" style="clear : both;height: 150px; overflow-y: auto;"></div>
		        </div>
	
		        <div class="widget">
		            <div class="title">
		                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
		                <h6 id="areaName">小区</h6>
		            </div>
		            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
		                <thead>
		                <tr>
		                <td width="10%">序号</td>
		                <td width="40%">创建时间</td>
		                <td width="30%">小区名称</td>
		                <td>操作</td>
		                </tr>
		                </thead>
		                <tbody id="communityBody"></tbody>
		            </table>
		            <div class="tPagination" style="text-align: center;">
		                <ul id="tablePaging" style="box-shadow: 0px 0px 0px;">
		                </ul>
		            </div>
	        	</div>
	   		</div>
	   	<div class="twoOne">
	   		<%--    盒子选取列表区域           --%>	
	     	<div class="widget">
	            <div class="title">
	                <img src="${pageContext.request.contextPath}/images/icons/dark/frames.png" alt="" class="titleIcon" />
	                <h6 id="communityName">${community.areaFullPath}</h6>
	                <div class="num">
		                    <input type="text" id="box_info_field" placeholder="请输入投影仪MAC" style="width: 200px; height: 25px"/>&nbsp;
		                    <a class="greyNum" title="" href="#" onclick="searchBoxInfo();"><span>查询</span></a>
		            </div>
	            </div>
	            <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
	                <thead>
	                <tr>
	                    <td width="3%">
	                        <input id="selectAll" type="checkbox" onclick="selectAllBox();"/>
	                    </td>
	                    <td width="3%">
	                        #
	                    </td>
	                    <td width="25%">创建时间</td>
	                    <td width="20%">MAC</td>
	                    <td align="center">位置</td>
	                 </tr>
	                </thead>
	                <tbody id="boxes-tbody">
	                <tr>
	                	<td colspan="6" style="padding:20px 0 30px 0;" align="center">
  							<p class="empty">请选取 [ 地区 ] ，并点击小区列表中的 [ 显示设备 ] 来选择设备</p>
  						</td>
  					</tr>
	                </tbody>
	            </table>
	            <div class="my-table-footer">
		            <div class="tPagination" style="text-align:center">
		            	<div class="page"></div>
		     			<div class="clear"></div>
		     		</div>
		     		<div class="table_process">
		     			<a class="button dblueB" title="" href="javascript:void(0);" onclick="addBoxes();"><span>添加所选</span></a>
		     		</div>
	            	<div class="clear"></div>
	            </div>
				
	        </div>
	        
	        <%--    盒子选取结果展示区域           --%>
	        <div class="widget">
                 <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/refresh4.png" alt="" class="titleIcon"><h6>已选取盒子展示</h6></div>
                 <div class="updates" id="choosedbox-list" style="max-height:800px;overflow-y:auto">
                 <c:choose>
                	 <c:when test="${boxList!=null && fn:length(boxList)>0}">
                	 <c:forEach items="${boxList}" var="upgradeBox" >
                	 	<div class="newUpdate" data-boxuuid='["${upgradeBox.box.uuid}"]'>
	                         <div class="uDone">
	                             <strong>${upgradeBox.box.mac}</strong>
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
		     			<a class="button greenB" title="" href="javascript:void(0);" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/contentupgradeinfo.html?contentUpgradeId=${contentUpgradeId}'"><span>返回</span></a>
		     			<a class="button dblueB" title="" href="javascript:void(0);" onclick="saveChoosedBox();"><span>保存配置</span></a>
		     			<a class="button dblueB" title="" href="javascript:void(0);" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/playConfigCommunity.html?contentUpgradeId=${contentUpgradeId}';"><span>下一步</span></a>
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

<div id="boxupload-dialog-confirm" title="系统对话框" class="myConfirm-dialog">
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
var contentUpgradeId = '${contentUpgradeId}';
var notyf = new Notyf({delay:3000});
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
Array.prototype.del=function(n) {
	if(n<0)
		return this;
	else
	return this.slice(0,n).concat(this.slice(n+1,this.length));
	};

    var boxTbodyId = "boxes-tbody";
    var boxLoader = {
    		load:function(pno,psize,callback){
    			var params = {startPosition:pno,size:psize};
    			if(!boxLoader.isSearch){
    				var communityId = $('#'+boxTbodyId).data("communityid")[0];
        			params.communityUuid = communityId;
        			$("#box_info_field").val('');
    			}else{
    				var macInfo = $("#box_info_field").val();
    				params.mac = macInfo;
    			}
    			$.ajax({
    				type:"post",
    				data:params,
    				url:'${pageContext.request.contextPath}/backend/ajaxRequestBoxes.html',
    				success:function(data){
    					$("#boxes-tbody").html(data);
    					$('.tipS').tipsy({gravity: 's',fade: true});
    					$("#selectAll").prop("checked",false).closest('.checker > span').removeClass('checked');
    					if(callback){
    						var itemcount = $("#boxes-tbody").find("tr:eq(0)").data("itemcount");
    						callback(itemcount);
    					}
    				}
    			});
    		},
    		initPage:function(itemcount){
    			$("div.page").tzPage(itemcount, {
    				num_edge_entries : 1, 
    				num_display_entries :4, 
    				num_edge_entries:4,
    				current_page:0,
    				items_per_page : 5,
    				prev_text : '上一页',
    				next_text : '下一页',
    				callback : function(pageNo,psize){
    					boxLoader.load(pageNo *psize,psize);
    				}
    			});
    		}
    	};
    	function initResource(currentPage, pageSize ,isSearch){
    		currentPage = arguments[0]?arguments[0]:0;
    		pageSize = arguments[1]?arguments[1]:5;
    		boxLoader.isSearch=isSearch;
    		boxLoader.load(currentPage,pageSize,function(itemcount){
    			boxLoader.initPage(itemcount);
    		});
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
                        var checkDetails = "<a class=\"button blueB\" href=\"javascript:void(0);\" onclick=\"showCommunityBoxes(this, 0, 5);\" ><span>显示设备</span></a>";
                        tableHtml += '<tr data-communityid=\'["'+list[i].uuid+'"]\'>';
                        tableHtml += '<td>' + list[i].num + '</td>';
                        tableHtml += '<td>' + list[i].timestamp + '</td>';
                        tableHtml += '<td>' + list[i].name + '</td>';
                        tableHtml += '<td>' + checkDetails + '</td>';
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
    	
    	
function showCommunityBoxes(obj, currentPage, rows){
    var $tr = $(obj).parent().parent();
    var communityUuid = $tr.data("communityid")[0];
    var communityName = $tr.find("td:eq(2)").text();

	var $tbody = $('#'+boxTbodyId);
	$tbody.data("communityid",[""+communityUuid]);
	$('#communityName').text("小区 ："+communityName);
	var currPage = arguments[1] ? arguments[1]:0;
	var tableRow = arguments[2] ? arguments[2]:5;
	initResource(currPage, tableRow, false);
};

function searchBoxInfo() {
    var currPage = arguments[1] ? arguments[1]:0;
    var tableRow = arguments[2] ? arguments[2]:5;
    $('#communityName').text("");
    initResource(currPage, tableRow, true);
};

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
};
function addBoxes(){
	var communityName = $('#communityName').text();
	var areaName = $('#areaName').text();
	var path = '';
	if(communityName){
		path = areaName.substr(6)+'/'+communityName.substr(4);
	};
	 $("input[name='selectBoxInfo']:checked").each(function() {
       	var mac = $(this).val();
       	var $tr = $(this).parent().parent();
       	var uuid = $tr.data('boxuuid')[0];
       	if(!checkIsExist(mac)){
       		macSelectedList.push(mac);
       		boxSelectedList.push(uuid);
	       	createNewDiv( 'choosedbox-list',mac, uuid);
       	}else{
			//TODO 加入 notfy弹窗提示       		
       		console.log('<'+mac+'>已存在');
       	}
	  });
};
function createNewDiv(ulId, value, uuid){
	var divHtml='<div class="newUpdate" data-boxuuid=\'["'+uuid+'"]\'>'+
        '<div class="uDone">'+
   		'<strong>'+value+'</strong>'+
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
	var uuid = $boxDiv.data('boxuuid')[0];
	var mac = $boxDiv.find('div.uDone > strong').text().trim();
	$boxDiv.remove();
	var index = macSelectedList.indexOf(mac);
	if(index !=-1){
		macSelectedList = macSelectedList.del(index);
		boxSelectedList = boxSelectedList.del(index);	
	}
};

function getChooseBoxMac(){
	var boxId = 'choosedbox-list';
	var newDivList = $('#'+boxId).find('div.newUpdate');
	var len = newDivList.length;
	var result=[];
	newDivList.each(function(index, element){
		var mac = $(element).find('div.uDone > strong').text();
		result.push(mac);
	});
	return result;
};

function getChoosedBoxId(){
	var boxId = 'choosedbox-list';
	var newDivList = $('#'+boxId).find('div.newUpdate');
	var len = newDivList.length;
	var result=[];
	newDivList.each(function(index, element){
		var uuid = $(element).data('boxuuid')[0];
		result.push(uuid);
	});
	return result;
}

var macSelectedList = [];
var boxSelectedList = [];

function checkIsExist(obj){
	if(!obj) return true;
	if(macSelectedList.length == 0){
		macSelectedList = getChooseBoxMac();
	}
	if(boxSelectedList.length == 0){
		boxSelectedList = getChoosedBoxId();
	}
	
	for(var i=0;i<macSelectedList.length;i++){
		if(macSelectedList[i] == obj) return true;
	}
	return false;
};

function saveChoosedBox(){
	var dialogId ='#boxupload-dialog-confirm';
	var $dialog = $(dialogId);
	var $span = $dialog.find('span.content:eq(0)');
	$span.text(boxSelectedList.length+'个盒子');
	var confirmButton = {
			'确认': function() {
				$.ajax({
					type:'post',
					traditional: true,
					data:{boxList:boxSelectedList, contentUpgradeId: contentUpgradeId},
					url:"${pageContext.request.contextPath}/backend/ajaxPlayConifigSaveUuid.html",
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
