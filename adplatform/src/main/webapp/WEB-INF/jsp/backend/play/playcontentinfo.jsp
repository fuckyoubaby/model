<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/>
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/htmlstyle.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/tz_page.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tz_page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.js"></script>    
	<style>
	.ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
   .ui-dialog  label, .ui-dialog input { display:block; }
    .ui-dialog input.text { margin-bottom:12px; width:95%; padding: .4em; }
    .ui-dialog fieldset { padding:0; border:0; margin-top:15px; }
   .ui-dialog  span.star{color:#ff0000;padding-left:3px}
    .img-btn{float:left;line-height:0;}
	</style>
</head>

<body>

<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/left.jsp"/>

<%--内容部分***********************************************************--%>

<div id="rightSide">

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/head.jsp"/>

    <%--页面按钮部分*******************************************************--%>

    <div class="nNote nInformation hideit">
        <p><strong>播放内容管理: </strong>为播放内容进行广告组织和配置</p>
    </div>

    <div class="wrapper">
        <!-- Note -->
        <div class="widgets">
        	<div class="oneThree">
				<div class="widget">
                    <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/clipboard.png" alt="" class="titleIcon"><h6>播放内容基本信息</h6></div>
                    <div class="body" style="border-bottom:1px solid #E2E2E2">
                        <h4 class="pt5">编号</h4><p><span class="greenBack content-uuid-dom">${playContent.uuid}</span> </p>
                        <h4 class="pt10">名称</h4><p> <span class="greyBack">${playContent.name}</span> </p>
                        <h4 class="pt10">版本</h4><p><span class="blueBack">${playContent.version }</span></p>
                        <h4 class="pt10">默认播放时间</h4><p>${playContent.defaultDuration} <span class="greyBack">秒</span> </p>
                    </div>
                    <div class="formSubmit" style="float:left">
                    <c:choose>
                    	<c:when test="${ empty contentUpgradeId}">
                    	<input type="button" value="返回" class="blueB" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/playcontentmanagement.html?filterName=${filterName}&current=${current}'">
                    	</c:when>
                    	<c:otherwise>
                    	<input type="button" value="返回" class="blueB" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/contentupgradeconfigplay.html?contentUpgradeId=${contentUpgradeId}'">
                    	<input type="button" value="更新" class="greenB" onclick="window.location.href='${pageContext.request.contextPath}/backend/playcontentform.html?contentUuid=${playContent.uuid}&&contentUpgradeId=${contentUpgradeId}'">
                    	</c:otherwise>
                    </c:choose>
                        
                    </div>
                    <div class="clear"></div>
                </div>
			</div>
			<div class="twoOne">
				<div class="widget">
                    <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/stats.png" alt="" class="titleIcon"><h6>已组织的广告</h6></div>
                    <table cellpadding="0" cellspacing="0" width="100%" class="sTable">
                        <thead>
                            <tr>
                                <td width="80">序号</td>
                                <td width="80">广告名字</td>
                                <td>描述</td>
                                <td width="50">资源数量</td>
                                <td width="80">起始日期</td>
                                <td width="80">结束日期</td>
                                <td width="100">操作</td>
                            </tr>
                        </thead>
                        <tbody  id="content-tbody">
                        <c:forEach items="${items}" var="item" >
                       	   <tr data-repeat="${item.repeat}" data-itemuuid='["${item.uuid}"]'>
                               <td align="center"><a href="#" title="" class="webStatsLink">${item.index}</a></td>
                               <td align="center">${item.name}</td>
                               <td>${item.description}</td>
                               <td align="center">${item.amount}</td>
                               <td align="center"><fmt:formatDate value="${item.startDate}" pattern="yyyy.MM.dd"/></td>
                               <td align="center"><fmt:formatDate value="${item.endDate}" pattern="yyyy.MM.dd"/></td>
                               <td class="actBtns">
                               	<a href="javascript:void(0);" class="tipS img-btn" original-title="上升" onclick="riseUp(this)"><img src="${pageContext.request.contextPath }/images/icons/up_arrow.png" width="16" height="16"/></a>
                               	<a href="javascript:void(0);" class="tipS img-btn " onclick="configItemResource(this)" original-title="详情" ><img src="${pageContext.request.contextPath }/images/icons/info_icon.png" width="16" height="16"/></a>
                               	<a href="javascript:void(0);" class="tipS img-btn update" original-title="播放参数配置" ><img src="${pageContext.request.contextPath }/images/icons/setup.png" width="16" height="16"/></a>
                               	<a href="javascript:void(0);" class="tipS img-btn" original-title="移除" onclick="contentDelete('123',this)"><img src="${pageContext.request.contextPath }/images/icons/remove_icon.png" width="16" height="16"/></a>
                               	<div class="clear"></div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>   
                </div>
                
                <div class="widget">
                    <div class="title">
                    	<img src="${pageContext.request.contextPath}/images/icons/dark/timer.png" alt="" class="titleIcon">
                    	<h6 style="margin-right:20px">其他可用广告</h6>
                    	<input type="text" id="keyword" style="width: 200px; height: 25px;vertical-align:top;margin-top:5px;text-indent:0.5em" placeholder="名称"/>&nbsp;
	                    <a href="javascript:void(0);" class="button" name="find" onclick="searchKeyword();" style="margin-top:3px">
	                    	<img src="${pageContext.request.contextPath}/images/searchBtnSmall.png" width="30" height="30"/>
	                    </a>
                    	<div class="num"><a href="${pageContext.request.contextPath}/backend/addpalyitem.html?playContentId=${playContent.uuid}&contentUpgradeId=${contentUpgradeId}" class="greenNum">添加</a></div></div>
                    <table cellpadding="0" cellspacing="0" width="100%" class="sTable taskWidget" >
                        <thead>
                            <tr>
                                <td width="100">名字</td>
                                <td>描述</td>
                                <td width="50">资源数量</td>
                                <td width="100">操作</td>
                            </tr>
                        </thead>
                        <tbody id="item-tbody">
                            <tr>
                               <td colspan="4" style="padding:40px;text-align:center;">
							  	<h4 class="empty"><img alt="loading" src="${pageContext.request.contextPath}/images/loaders/loader2.gif" />正在加载数据</h4>
							  </td>
                            </tr>
                        </tbody>
                    </table> 
                  	<div class="tPagination">
						<div class="page"></div>
						<div class="clear"></div>
					</div>
                </div>
			</div>
			<div class="clear"></div>
        </div>
    </div>

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>

</div>

<%--弹出对话框部分*******************************************************--%>

<div id="content-delete-dialog-confirm" title="系统对话框" class="myConfirm-dialog">
    <p >
        <span class="ui-icon ui-icon-alert" style="float:left; margin:2px 4px 20px 0;"></span>
        <span class="content"></span>
    </p>
</div>
<div id="contentitem-delete-dialog-confirm" title="系统对话框" class="myConfirm-dialog">
    <p >
        <span class="ui-icon ui-icon-alert" style="float:left; margin:2px 4px 20px 0;"></span>
        <span class="content"></span>
    </p>
</div>
<div id="play-content-dialog-form" title="配置播放参数" class="myForm-dialog">
  <p class="validateTips"  >所有的表单字段都是必填的。</p>
  <form>
  <fieldset>
    <label for="repeat" >追加播放次数</label>
    <input type="text" name="repeat" id="repeat" value="" class="text ui-widget-content ui-corner-all" autofocus placeholder="不填或填0则广告播放1次">
	<label for="startdate" >开始日期<span class="star">*</span></label>
	<input type="text" name="startdate" id="startdate" readonly value="" class=" text ui-widget-content ui-corner-all" placeholder="点击选取日期">
   	<%-- <img src="${pageContext.request.contextPath}/images/icons/control/16/calendar.png" class="inputImg" /> --%>
    <label for="enddate">结束日期<span class="star">*</span></label>
    <input type="text" name="enddate" id="enddate" readonly="readonly" value="" class="text ui-widget-content ui-corner-all" placeholder="点击选取日期">
  </fieldset>
  </form>
</div>

<div id="play-content-dialog-update" title="配置播放参数" class="myForm-dialog">
  <p class="validateTips"  >所有的表单字段都是必填的。</p>
  <form>
  <fieldset>
    <label for="repeatupdate" >追加播放次数</label>
    <input type="text" name="repeatupdate" id="repeatupdate" value="" class="text ui-widget-content ui-corner-all" placeholder="不填或填0则广告播放1次">
	<label for="startdateupdate" >开始日期<span class="star">*</span></label>
	<input type="text" name="startdateupdate" id="startdateupdate" readonly="readonly" value="" class=" text ui-widget-content ui-corner-all" placeholder="点击选取日期">
   	<%-- <img src="${pageContext.request.contextPath}/images/icons/control/16/calendar.png" class="inputImg" /> --%>
    <label for="enddateupdate">结束日期<span class="star">*</span></label>
    <input type="text" name="enddateupdate" id="enddateupdate" readonly="readonly" value="" class="text ui-widget-content ui-corner-all" placeholder="点击选取日期">
  </fieldset>
  </form>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript">
var notyf = new Notyf({delay:3000});
$('.myConfirm-dialog').dialog({
	autoOpen:false,
    resizable: false,
    height:160,
    width:300,
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

function contentDelete(contentUuid, obj) {
	var dialogId ='#content-delete-dialog-confirm';
	var $dialog = $(dialogId);
	var $tr = $(obj).parent().parent();
	var uuid = $tr.data('itemuuid')[0];
	
	var text = $tr.find('td:eq(0)').text();
	var $span = $dialog.find('span.content:eq(0)');
	$span.text('确定要删除序号为 [ '+text+' ] 的内容?');
	var confirmButton = {
			'确认': function() {
                $.ajax({
                	type:'post',
                	url:'${pageContext.request.contextPath}/backend/ajaxRemoveItem.html',
                	data:{uuid:uuid},
                	success:function(data){
                		if((typeof data).toLowerCase() == "object"){
                			if(data.result){
            					notyf.confirm(data.msg?data.msg:'移除成功');
            					$tr.remove();
            	            	sortTR('content-tbody');
            	            	initResource();
            				}else{
            					notyf.alert(data.msg?data.msg:'移除失败');
            				}
                		}
                	}
                });
            	$( this ).dialog( "close" );
            }
	};
	dialogInitAndOpen($dialog, confirmButton);
};

function contentItemDelete(contentUuid, obj) {
	var dialogId ='#contentitem-delete-dialog-confirm';
	var $dialog = $(dialogId);
	var $tr = $(obj).parent().parent();
	var uuid = $tr.data('itemuuid')[0];
	var text = $tr.find('td:eq(0)').text();
	var $span = $dialog.find('span.content:eq(0)');
	$span.text('确定要删除名称为 [ '+text+' ] 的内容?');
	var confirmButton = {'确认': function() {
	        $.ajax({
                	type:'post',
                	url:'${pageContext.request.contextPath}/backend/ajaxDeleteItem.html',
                	data:{uuid:uuid},
                	success:function(data){
                		if((typeof data).toLowerCase() == "object"){
                			if(data.result){
            					notyf.confirm(data.msg?data.msg:'删除成功');
            					$tr.remove();
            	            	initResource();
            				}else{
            					notyf.alert(data.msg?data.msg:'删除失败');
            				}
                		}
                	}
                });
	    	$( this ).dialog( "close" );
	    }
	};
	dialogInitAndOpen($dialog, confirmButton);
};

function addNewTR(param){
	var $tbody=$('#content-tbody');
	var index = $tbody.find('tr').length+1;
	var html=' <td align="center"><a href="#" title="" class="webStatsLink">'+index+'</a></td>'
	+'<td align="center">'+param.name+'</td>'
	+'<td>'+param.desc+'</td>'
	+'    <td align="center">'+param.amount+'</td>'
	+'   <td align="center">'+param.startdate+'</td>'
	+'   <td align="center">'+param.enddate+'</td>'
	+'<td class="actBtns">'
	+'<a href="javascript:void(0);" class="tipS img-btn" original-title="上升" onclick="riseUp(this)"><img src="${pageContext.request.contextPath }/images/icons/up_arrow.png" width="16" height="16"/></a>'
	+'<a href="javascript:void(0);" class="tipS img-btn " onclick="configItemResource(this)" original-title="详情" ><img src="${pageContext.request.contextPath }/images/icons/info_icon.png" width="16" height="16"/></a>'
	+'<a href="javascript:void(0);" class="tipS img-btn update" original-title="配置" ><img src="${pageContext.request.contextPath }/images/icons/setup.png" width="16" height="16"/></a>'
	+'<a href="javascript:void(0);" class="tipS img-btn" original-title="移除" onclick="contentDelete(\''+param.uuid+'\',this)"><img src="${pageContext.request.contextPath }/images/icons/remove_icon.png" width="16" height="16"/></a>'
	+'<div class="clear"></div>'
	+'</td>';
	var $tr = $('<tr data-repeat="'+param.repeat+'" data-itemuuid=\'["'+param.uuid+'"]\'></tr>');
	$tr.html(html).appendTo($tbody);
	itemUpdateEventInit();
	$('.tipS').tipsy({gravity: 's',fade: true});
};


function riseUp(obj){
	var $tr = $(obj).parent().parent();
	var $a = $tr.find('a.webStatsLink');
	var currentIndex = $a.text();
	var currentUuid = $tr.data('itemuuid')[0];
	var $pretr= $tr.prev();
	var $prea = $pretr.find('a.webStatsLink');
	var preUuid = $pretr.data('itemuuid')[0];
	var preIndex = $prea.text();
	if($pretr.length>0){
		var params = {
			preIndex:++preIndex,
			preUuid:preUuid,
			currentIndex: --currentIndex,
			currentUuid: currentUuid
		};
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/backend/ajaxChangeIndex.html',
			data:params,
			success:function(data){
				if((typeof data).toLowerCase() == "object"){
    				if(data.result){
    					notyf.confirm(data.msg?data.msg:'修改成功');
    					$a.text(currentIndex);
    					$prea.text(preIndex);
    					$pretr.insertAfter($tr);
    				}else{
    					notyf.alert(data.msg?data.msg:'修改失败');
    				}
    			}
			}
		});
		
	}
};

function sortTR(tbodyId){
	var $tbody=$('#'+tbodyId);
	var $trs = $tbody.find('tr');
	$trs.each(function(index, element){
		var $a = $(element).find('a.webStatsLink');
		$a.text(index+1);
	});
};

var playItemLoader = {
		search:function(){
			playItemLoader.load(0,5,function(itemcount){
				playItemLoader.initPage(itemcount);
			 });
			},
		load:function(pno,psize,callback){
			var keyword = $("#keyword").val();
			var params = {indexNo:pno,pageSize:psize,keyword:keyword};
			$.ajax({
				type:"post",
				data:params,
				url:'${pageContext.request.contextPath}/backend/play/playItemFetch.html',
				success:function(data){
					$("#item-tbody").html(data);
					itemConfigEventInit();
					$('.tipS').tipsy({gravity: 's',fade: true});
					if(callback){
						var itemcount = $("#item-tbody").find("tr:eq(0)").data("itemcount");
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
					playItemLoader.load(pageNo *psize,psize);
				}
			});
		}
	};
	
	function searchKeyword(){
		playItemLoader.search();
	};
	function initResource(){
		playItemLoader.load(0,5,function(itemcount){
			playItemLoader.initPage(itemcount);
		});
	};
	initResource();

function checkAddForm(values, add){
	var numReg=/^\d+$/g;
	var repeatId = add ? '#repeat':'#repeatupdate';
	var startdateId = add ? '#startdate' : '#startdateupdate';
	var enddateId = add ? '#enddate' : '#enddateupdate';
	if(values.repeat){
		var repeat = values.repeat;
		var b = numReg.test(repeat);
		if(!b){
			$(repeatId).focus();
			notyf.alert('追加播放次数['+repeat+']是无效数值！');
			return false;
		}
	}
	
	if(!values.startdate){
		$(startdateId).focus();
		notyf.alert('请填写起始日期！');
		return false;
	}
	
	if(!values.enddate){
		$(enddateId).focus();
		notyf.alert('请填写截止日期！');
		return false;
	}
	return true;
}
	
	
function itemConfigEventInit(){
	$('.tipS.add').click(function(){
		var $dialog = $("#play-content-dialog-form");
		var $tr = $(this).parent().parent();
		var desc = $tr.find("td:eq(1)").text();
		var name = $tr.find("td:eq(0)").text();
		var amount = $tr.find("td:eq(2)").text();
		var contentUuid = $('.content-uuid-dom').text();
		var uuid = $tr.data('itemuuid')[0];
		
		var $tbody=$('#content-tbody');
		var index = $tbody.find('tr').length+1;
		$dialog.find('.validateTips').text('配置广告: [ '+name+' ] 的播放参数');
		
		var params = {index:index};
		var addButtonProcess = {
				"添加": function(){
				params.startdate = $( '#startdate' ).val();
				params.enddate = $( '#enddate' ).val();
				params.repeat = $( '#repeat' ).val();
				if(!checkAddForm(params, true)){
					return;
				}
				
				params.uuid = uuid;
				params.contentUuid = contentUuid;
				
				$.ajax({
				type:"post",
				data:params,
				url:'${pageContext.request.contextPath}/backend/ajaxConfigContent.html',
				success:function(data){
					if((typeof data).toLowerCase() == "object"){
        				if(data.result){
        					notyf.confirm(data.msg?data.msg:'添加成功');
        					params.desc = desc;
        					params.name = name;
        					params.amount = amount;
        					addNewTR(params);
        					initResource();
        				}else{
        					notyf.alert(data.msg?data.msg:'添加失败');
        				}
        			}
				}
				});
				$( this ).dialog( 'close' );
			}	
		};
		dialogInitAndOpen($dialog, addButtonProcess);
	});
};

function itemUpdateEventInit(){
	$('.img-btn.update').click(function(){
		var $dialog = $("#play-content-dialog-update");
		var $tr = $(this).parent().parent();
		var startDate = $tr.find('td:eq(4)').text();
		var endDate = $tr.find('td:eq(5)').text();
		var name = $tr.find('td:eq(1)').text();
		var repeat =  $tr.data('repeat');
		var uuid = $tr.data('itemuuid')[0];
	    var $startdateUpdate = $('#startdateupdate'),
	    $enddateUpdate = $( '#enddateupdate' ),
	    $repeatUpdate = $( '#repeatupdate' );
	    
	    $startdateUpdate.val(startDate);
	    $enddateUpdate.val(endDate);
	    $repeatUpdate.val(repeat);
		$dialog.find('.validateTips').text('正在配置['+name+']的播放参数');
	    
		var addButtonProcess = {
				"保存": function(){
				var params = {};
				params.startdate = $startdateUpdate.val();
				params.enddate = $enddateUpdate.val();
				params.repeat = $repeatUpdate.val();
				params.uuid = uuid;
				if(!checkAddForm(params, false)){
					return;
				}
				$.ajax({
				type:"post",
				data:params,
				url:'${pageContext.request.contextPath}/backend/ajaxUpdateItemConfig.html',
				success:function(data){
					if((typeof data).toLowerCase() == "object"){
        				if(data.result){
        					notyf.confirm(data.msg?data.msg:'保存成功');
        					$tr.find('td:eq(4)').text(params.startdate);
        					$tr.find('td:eq(5)').text(params.enddate);
        					$tr.data('repeat', params.repeat);
        				}else{
        					notyf.alert(data.msg?data.msg:'保存失败');
        				}
        			}
				}
				});
				$( this ).dialog( 'close' );
			}	
		};
		dialogInitAndOpen($dialog, addButtonProcess);
	});
};

function configItemResource(obj){
	var $tr = $(obj).parent().parent();
	var itemUuid = $tr.data('itemuuid')[0];
	var contentUuid = $('.content-uuid-dom').text();
	var contentUpgradeId = '${contentUpgradeId}';
	window.location.href='${pageContext.request.contextPath}/backend/configplayitem.html?itemUuid='+itemUuid+'&contentUuid='+contentUuid+'&contentUpgradeId='+contentUpgradeId;
};

$(function(){
    var $startdate = $( "#startdate" ),
    $enddate = $( "#enddate" ),
    $repeat = $( "#repeat" ),
    $allFields = $( [] ).add( $startdate ).add( $enddate ).add( $repeat ),
    $tips = $( ".validateTips" );
    
    var $startdateUpdate = $('#startdateupdate'),
    $enddateUpdate = $( '#enddateupdate' ),
    $repeatUpdate = $( '#repeatupdate' ),
    $allUpdateFields = $( [] ).add( $startdateUpdate ).add( $enddateUpdate ).add( $repeatUpdate );
    
    var datepickerOptions = {dateFormat: 'yy.mm.dd'}
    $startdate.datepicker(datepickerOptions);
    $enddate.datepicker(datepickerOptions);
    $startdateUpdate.datepicker(datepickerOptions);
    $enddateUpdate.datepicker(datepickerOptions);
    
	$("#play-content-dialog-form").dialog({
		autoOpen:false,
		height:320,
		width: 350,
		model: true,
		close: function(){
			$allFields.val("").removeClass("ui-state-error");
		}
	});
	
	$("#play-content-dialog-update").dialog({
		autoOpen:false,
		height:320,
		width: 350,
		model: true,
		close: function(){
			$allUpdateFields.val("").removeClass("ui-state-error");
		}
	});
	
	itemUpdateEventInit();
});

</script>
    
</body>
</html>