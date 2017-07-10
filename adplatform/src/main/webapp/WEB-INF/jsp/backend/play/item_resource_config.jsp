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
    #popup{background-color:#fff;border-radius:10px 10px 10px 10px;box-shadow:0 0 25px 5px #999;color:#111;display:none;padding:25px;min-width:160px;min-height:20px;}
    #popup:after {clear:both;content:".";display:block;font-size:0px;height:0;line-height:0px;visibility:hidden;}
    .mybutton.b-close{border-radius:7px 7px 7px 7px;box-shadow:none;font:bold 131% sans-serif;padding:0 6px 2px;position:absolute;right:-7px;top:-7px}
    .mybutton{background-color:#2b91af;border-radius:10px;box-shadow:0 2px 3px rgba(0,0,0,0.3);color:#fff;cursor:pointer;display:inline-block;padding:10px 20px;text-align:center;text-decoration:none}
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
        <p><strong>广告配置: </strong>为广告进行资源组织</p>
    </div>

    <div class="wrapper">
        <!-- Note -->
        <div class="widgets">

			<div class="twoOne">
				<div class="widget">
                    <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/stats.png" alt="" class="titleIcon"><h6>已配置资源</h6></div>
                    <table cellpadding="0" cellspacing="0" width="100%" class="sTable taskWidget">
                        <thead>
                            <tr>
                                <td width="40">序号</td>
                                <td width="80">名称</td>
                                <td>路径</td>
                                <td width="55">持续时间</td>
                                <td width="55">追加次数</td>
                                <td width="80">操作</td>
                            </tr>
                        </thead>
                        <tbody  id="content-tbody">
                        	<c:forEach items="${playItemContents}" var="playItemContent" >
                        	<tr data-itemcontentuuid='["${playItemContent.uuid}"]' data-type="${playItemContent.adverResourceDTO.type}">
                                <td align="center"><a href="#" title="" class="webStatsLink">${playItemContent.index}</a></td>
                                <td align="center">${playItemContent.adverResourceDTO.name}</td>
                                <td class="taskPr"><a href="#" class="tipS img-show" original-title="点击预览"  data-uuid='["${playItemContent.adverResourceDTO.uuid}"]'>${playItemContent.adverResourceDTO.path}</a></td>
                                <td align="center">${playItemContent.duration }</td>
                                <td align="center">${ playItemContent.repeat }</td>
                                <td class="actBtns">
                                	<a href="javascript:void(0);" class="tipS img-btn" original-title="上升" onclick="riseUp(this)"><img src="${pageContext.request.contextPath }/images/icons/up_arrow.png" width="16" height="16"/></a>
                                	<a href="javascript:void(0);" class="tipS img-btn update" original-title="配置" ><img src="${pageContext.request.contextPath }/images/icons/setup.png" width="16" height="16"/></a>
                                	<a href="javascript:void(0);" class="tipS img-btn" original-title="移除" onclick="contentDelete('${playItemContent.uuid}',this)"><img src="${pageContext.request.contextPath }/images/icons/remove_icon.png" width="16" height="16"/></a>
                                	<div class="clear"></div>
                                 </td>
                            </tr>
                        	</c:forEach>
                        </tbody>
                    </table>   
                </div>
                
                <div class="widget">
                    <div class="title">
                    <img src="${pageContext.request.contextPath}/images/icons/dark/timer.png" alt="" class="titleIcon" />
                    <h6 style="margin-right:20px">其他可用资源</h6>
                    <input type="text" id="keyword"  style="width: 200px; height: 25px;vertical-align:top;margin-top:5px;text-indent:0.5em" placeholder="名称"/>&nbsp;
	                    <a href="javascript:void(0);" class="button" name="find" onclick="searchKeyword();" style="margin-top:3px">
	                    	<img src="${pageContext.request.contextPath}/images/searchBtnSmall.png" width="30" height="30"/>
	                    </a>
                    <div class="num"><a href="${pageContext.request.contextPath}/backend/playresourceform.html?itemUuid=${playItem.uuid}&contentUuid=${playContentId}&contentUpgradeId=${contentUpgradeId}" class="greenNum">添加新资源</a></div>
                    </div>
                    <table cellpadding="0" cellspacing="0" width="100%" class="sTable taskWidget">
                        <thead>
                            <tr>
                                <td width="100">名称</td>
                                <td>路径</td>
                                <td width="120">广告/代理商</td>
                                <td width="50">类型</td>
                                <td width="100">操作</td>
                            </tr>
                        </thead>
                        <tbody id="resource-body">
                           <tr>
                               <td colspan="5" style="padding:40px;text-align:center;">
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

        	<div class="oneThree">
				<div class="widget">
                    <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/clipboard.png" alt="" class="titleIcon"><h6>广告基本信息</h6></div>
                    <div class="body" style="border-bottom:1px solid #E2E2E2">
                        <h4 class="pt10">名称</h4><p><span class="blueBack">${playItem.name}</span></p>
                        <h4 class="pt20">描述</h4><p>${playItem.description}</p>
                        <h4 class="pt20">编号</h4><p>${playItem.uuid} </p>
                    </div>
                    <div class="formSubmit" style="float:left">
                        <input type="button" value="返回" class="blueB" onclick="window.location.href='${pageContext.request.contextPath}/backend/playcontent.html?playContentId=${playContentId}&contentUpgradeId=${contentUpgradeId}'">
                        <input type="button" value="更新" class="greenB" onclick="window.location.href='${pageContext.request.contextPath}/backend/addpalyitem.html?playContentId=${playContentId}&playItemId=${playItem.uuid}&contentUpgradeId=${contentUpgradeId}'">
                    </div>
                    <div class="clear"></div>
                </div>
			</div>
			<div class="clear">
			</div>
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
<div id="resource-config-dialog-form" title="配置播放策略" class="myForm-dialog">
  <p class="validateTips" ></p>
  <form>
  <fieldset>
    <label for="repeat" >追加播放次数</label>
    <input type="text" name="repeat" id="repeat" value="" class="text ui-widget-content ui-corner-all" placeholder="不填或填0实际播放1次">
	<label for="duration" class="duration-label">持续时间(秒)<span class="star"></span></label>
    <input type="text" name="duration" id="duration" value="" class="text ui-widget-content ui-corner-all" >
  </fieldset>
  </form>
</div>

<div id="resource-config-dialog-update" title="配置播放策略" class="myForm-dialog">
  <p class="validateTips" ></p>
  <form>
  <fieldset>
    <label for="repeat_update" >追加播放次数</label>
    <input type="text" name="repeat_update" id="repeat_update"  class="text ui-widget-content ui-corner-all" placeholder="不填或填0实际播放1次">
	<label for="duration_update"  class="duration-update-label">持续时间(秒)<span class="star"></span></label>
    <input type="text" name="duration_update" id="duration_update"  class="text ui-widget-content ui-corner-all" >
  </fieldset>
  </form>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/contextPath.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/bPopup/bPopup.js"></script>
<script type="text/javascript">
var baseRequestUrl = '${pageContext.request.contextPath}';
var contentUuid = '${playContentId}';
var itemUuid = '${playItem.uuid}';
var contentUpgradeId = '${contentUpgradeId}';
var showImageRequestPath = baseRequestUrl+'/backend/getimages.html';
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

function contentDelete(uuid, obj) {
	var dialogId ='#content-delete-dialog-confirm';
	var $dialog = $(dialogId);
	var $tr = $(obj).parent().parent();
	var text = $tr.find('td:eq(0)').text();
	var $span = $dialog.find('span.content:eq(0)');
	$span.text('确定要删除序号为 [ '+text+' ] 的内容?');
	var confirmButton = {
		'确认': function() {
           	$.ajax({
           		type:'post',
           		data: {uuid:uuid, itemUuid:itemUuid, index: text },
           		url: baseRequestUrl+'/backend/ajaxDeleteItemContent.html',
           		success:function(data){
           			if((typeof data).toLowerCase() == "object"){
           				if(data.result){
           					notyf.confirm(data.msg?data.msg:'删除成功');
           					$tr.remove();
           	            	sortTR('content-tbody');
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

function resourceDelete(contentUuid, obj) {
	var dialogId ='#contentitem-delete-dialog-confirm';
	var $dialog = $(dialogId);
	var $tr = $(obj).parent().parent();
	var resourceUuid = $tr.data('resourceuuid')[0];
	var text = $tr.find('td:eq(0)').text();
	var $span = $dialog.find('span.content:eq(0)');
	$span.text('确定要删除名称为 [ '+text+' ] 的资源?');
	var confirmButton = {'确认': function() {
	        $.ajax({
	        	type:'post',
	        	url: baseRequestUrl+'/backend/ajaxResourceDelete.html',
	        	data: {resourceUuid: resourceUuid},
	        	success: function(data){
	        		if((typeof data).toLowerCase() == "object"){
        				if(data.result){
        					notyf.confirm(data.msg?data.msg:'删除成功');
        					$tr.remove();
        					initResource();
        				}else{
        					notyf.alert(data.msg?data.msg:'删除失败');
        				}
	        		}else{
	        			notyf.alert('删除失败,请稍后再试！');
	        		}
	        	}
	        } );
	    	$( this ).dialog( "close" );
	    }
	};
	dialogInitAndOpen($dialog, confirmButton);
};

function addNewTR(param){
	var $tbody=$('#content-tbody');
	if(param["duration"] && param["duration"].indexOf('.')==-1){
		param.duration = param.duration+'.0';
	}
	var html=' <td align="center"><a href="#" title="" class="webStatsLink">'+param.index+'</a></td>'
	+'   <td align="center">'+param.name+'</td>'
	+' <td class="taskPr"><a href="#" class="tipS img-show" original-title="点击预览"  data-uuid=\'["'+param.resourceUuid+'"]\'>'+param.path+'</a></td>'
	+'   <td align="center">'+param.duration+'</td>'
	+'   <td align="center">'+param.repeat+'</td>'
	+'<td class="actBtns">'
	+'<a href="javascript:void(0);" class="tipS img-btn" original-title="上升" onclick="riseUp(this)"><img src="${pageContext.request.contextPath }/images/icons/up_arrow.png" width="16" height="16"/></a>'
	+'<a href="javascript:void(0);" class="tipS img-btn update" original-title="配置" ><img src="${pageContext.request.contextPath }/images/icons/setup.png" width="16" height="16"/></a>'
	+'<a href="javascript:void(0);" class="tipS img-btn" original-title="移除" onclick="contentDelete(\''+param.uuid+'\',this)"><img src="${pageContext.request.contextPath }/images/icons/remove_icon.png" width="16" height="16"/></a>'
	+'<div class="clear"></div>'
	+'</td>';
	var $tr = $('<tr data-itemcontentuuid=\'["'+param.uuid+'"]\' data-type="'+param.type+'"></tr>');
	$tr.html(html).appendTo($tbody);
	initContentUpdateEvent();
	$('.tipS').tipsy({gravity: 's',fade: true});
};

function riseUp(obj){
	var $tr = $(obj).parent().parent();
	var $a = $tr.find('a.webStatsLink');
	var currentIndex = $a.text();
	var currentUuid = $tr.data('itemcontentuuid')[0];
	var $pretr= $tr.prev();
	var $prea = $pretr.find('a.webStatsLink');
	var preIndex = $prea.text();
	var preUuid= $pretr.data('itemcontentuuid')[0];
	if($pretr.length>0){
		--currentIndex;
		++preIndex;
		$.ajax({
			type:'post',
			url: baseRequestUrl+'/backend/ajaxReplaceItemContent.html',
			data:{currentIndex:currentIndex, currentUuid: currentUuid, preIndex:preIndex, preUuid: preUuid},
			success:function(data){
				if((typeof data).toLowerCase() == "object"){
    				if(data.result){
    					notyf.confirm(data.msg?data.msg:'调整成功');
    					$a.text(currentIndex);
    					$prea.text(preIndex);
    					$pretr.insertAfter($tr);
    				}else{
    					notyf.alert(data.msg?data.msg:'调整失败');
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

var resourceLoader =  {
		search:function(){
			resourceLoader.load(0,5,function(itemcount){
				resourceLoader.initPage(itemcount);
			 });
			},

		load:function(pno,psize,callback){
			var keyword = $("#keyword").val();
			var params = {indexNo:pno,pageSize:psize,keyword:keyword};
			$.ajax({
				type:"post",
				data:params,
				url:baseRequestUrl+'/backend/ajaxResourceFetch.html',
				success:function(data){
					$("#resource-body").html(data);
					initContentAddEvent();
					$('.tipS').tipsy({gravity: 's',fade: true});
					initShowImgeEvent('popup');
					if(callback){
						var itemcount = $("#resource-body").find("tr:eq(0)").data("itemcount");
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
					resourceLoader.load(pageNo *psize,psize);
				}
			});
		}
	};
	
	function searchKeyword(){
		resourceLoader.search();
	};
	function initResource(){
		resourceLoader.load(0,5,function(itemcount){
			resourceLoader.initPage(itemcount);
		});
	};
	initResource();
	
	function resourceUpdateLink(obj){
		var $tr = $(obj).parent().parent();
		var resourceUuid = $tr.data('resourceuuid')[0];
		window.location.href=baseRequestUrl+'/backend/playresourceform.html?resourceUuid='+resourceUuid
				+'&contentUuid='+contentUuid+'&itemUuid='+itemUuid+'&contentUpgradeId='+contentUpgradeId;	 
	};
	
	function checkResourceValues(values, isgif, add){
		var numReg = /^\d+$/g;
		var floatReg = /^(([1-9]\d*\.\d*)|(0\.\d*[1-9])|([1-9]\d*))\d*$/g;
		var repeatId = add ? '#repeat':'#repeatupdate';
		var durationId = add ? '#duration':'#duration';
		var duration = values.duration;
		var repeat = values.repeat;
		if(isgif){
			if(repeat){
				var b = numReg.test(repeat);
				if(!b){
					$(repeatId).focus();
					notyf.alert('追加播放次数['+repeat+']是无效数值！');
					return false;
				}
			}
			
			if(duration){
				var r = floatReg.test(duration);
				if(!r){
					$(durationId).focus();
					notyf.alert('持续时间['+duration+']是无效值！');
					return false;
				}
			}
			return true;
		}else{
			if(repeat){
				var b = numReg.test(repeat);
				if(!b){
					$(repeatId).focus();
					notyf.alert('追加播放次数['+repeat+']是无效数值！');
					return false;
				}
			}
			
			if(duration){
				var r = floatReg.test(duration);
				if(!r){
					$(durationId).focus();
					notyf.alert('持续时间['+duration+']是无效值！');
					return false;
				}
			}else{
				$(durationId).focus();
				notyf.alert('请填写持续时间！');
				return false;
			}
			return true;
		}
	}
	
	function initDialogForm(dialogId, type,isadd){
		var $dialog = $(dialogId);
		var durationDomId = isadd? "input[name='duration']" : "input[name='duration_update']";
		var durationLableId = isadd? "label.duration-label" : "label.duration-update-label";
		var $durationDom = $dialog.find(durationDomId);
		var $durationLabelDom = $dialog.find(durationLableId);
		$durationDom.show();
		$durationLabelDom.show();
		if(type.indexOf("image")!=-1){
			var suffix = type.substring(type.lastIndexOf("/")+1);
			if(suffix && suffix.toLowerCase() =='gif'){
				$durationDom.hide();
				$durationLabelDom.hide();
				return true;
			}
		};
		return false;
	}
	
	function initContentAddEvent(){
		$(".tipS.add").click(function(){
			var $dialog = $("#resource-config-dialog-form"),
			$tr = $(this).parent().parent(),
			$tips = $( ".validateTips" ),
			$duration = $( "#duration" ),
		    $repeat = $( "#repeat" );
			
			var resourceUuid = $tr.data('resourceuuid')[0],
			name = $tr.find("td:eq(0)").text(),
			path = $tr.find("td:eq(1)").text(),
			type = $tr.find("td:eq(3)").text();
			var isgif = initDialogForm("#resource-config-dialog-form", type,true);
			var params = {itemUuid:itemUuid, resourceUuid: resourceUuid};
			$tips.text('请配置 [ '+name+' ] 的播放参数：');
			
			var addButtonProcess = {
					"添加": function(){
					params.duration = $duration.val();
					params.repeat = $repeat.val();
					if(!checkResourceValues(params, isgif, true)){
						return;
					}
					var $tbody=$('#content-tbody');
					var index = $tbody.find('tr').length+1;
					params.index = index;
					params.isgif = isgif;
					$.ajax({
						type:'post',
						url: baseRequestUrl+'/backend/ajaxAddItemContent.html',
						data:params,
						success: function(data){
							if((typeof data).toLowerCase() == "object"){
		        				if(data.result){
		        					notyf.confirm(data.msg?data.msg:'配置成功');
		        					params.uuid = data.uuid;
		        					params.name = name;
		        					params.path =  path;
		        					params.type = type;
		        					addNewTR(params);
		        					initResource();
		        				}else{
		        					notyf.alert(data.msg?data.msg:'配置失败');
		        					if(data.uuid)console.log("data.uuid="+data.uuid);
		        				}
			        		}
						}
					});
					$( this ).dialog( "close" );
				}	
			};
			dialogInitAndOpen($dialog, addButtonProcess);
		});
	};
	
	function initContentUpdateEvent(){
		$('.img-btn.update').click(function(){
			var $dialog = $("#resource-config-dialog-update"),
			$tr = $(this).parent().parent(),
			index = $tr.find('td:eq(0)').text(),
			durationVal = $tr.find('td:eq(3)').text(),
			repeatVal = $tr.find('td:eq(4)').text(),
			type = $tr.data('type');
		    $durationUpdate = $('#duration_update');
		    $repeatUpdate = $('#repeat_update');
			
			$dialog.find('.validateTips').text('正在配置资源序号为 [ '+index+' ] 的播放参数');
			var isgif = initDialogForm("#resource-config-dialog-update", type,false);
			if(durationVal) $durationUpdate.val(durationVal);
			if(repeatVal) $repeatUpdate.val(repeatVal);
			
			var uuid = $tr.data('itemcontentuuid')[0];
			var updateButtonProcess={
				"保存": function(){
					var params ={uuid:uuid};
					params.duration = $durationUpdate.val().trim();
					params.repeat = $repeatUpdate.val().trim();
					params.isgif= isgif;
					if(!checkResourceValues(params, isgif, false)){
						return;
					}
					$.ajax({
						type:'post',
						url: baseRequestUrl+'/backend/ajaxUpdateItemContent.html',
						data:params,
						success: function(data){
							if((typeof data).toLowerCase() == "object"){
		        				if(data.result){
		        					notyf.confirm(data.msg?data.msg:'配置成功');
		        					if(params["duration"] && params["duration"].indexOf('.')==-1){
		        						params.duration = params.duration+'.0';
		        					}
		        					$tr.find('td:eq(3)').text(params.duration);
		        					$tr.find('td:eq(4)').text(params.repeat);
		        				}else{
		        					notyf.alert(data.msg?data.msg:'配置失败');
		        				}
			        		}
						}
					});
					$(this).dialog('close');
				}
			};
			
			dialogInitAndOpen($dialog, updateButtonProcess);
		});
	};
function reDrawImage(imagObj, midlleWidth, reduce){
	var $imgObj = (imagObj instanceof jQuery)? imagObj : $(imagObj);
	var width = $imgObj.width(),
	height = $imgObj.height();
	if(width>=midlleWidth){
		var newHeight =0;
		newHeight = height*midlleWidth/width;
		$imgObj.width(midlleWidth);
		$imgObj.height(newHeight);
		$imgObj.css("border","1px solid #ccc");
		var $popupDom = $imgObj.parent().parent();
	}else{
		if(reduce>10){
			reDrawImage($imgObj,midlleWidth-reduce, reduce/2);
		}else reDrawImage($imgObj,midlleWidth-10, 10);
	}
};
function redrawImage(width,height,middleWidth,reduce){
	if(width > middleWidth){
		var newHeight =0;
		newHeight = height*middleWidth/width;
		return {'width':middleWidth,'height':newHeight};
	}else if(width == middleWidth){
		return {'width':middleWidth,'height':height};
	}else{
		if(reduce > 25){
			return redrawImage(width,height, middleWidth-reduce, reduce/2);
		}else{
			return redrawImage(width,height, middleWidth-25,25);
		} 
	}
};	
function initShowImgeEvent(domId){
	$('a.tipS.img-show').unbind('click').bind('click',function(e){
		var html = ' <span class="mybutton b-close"><span>X</span></span>'+
		' <div class="content" style="line-height:0"></div>';
		var dom = $('<div id="'+domId+'" class="popup"></div>');
		dom.html(html);
		$('body').append(dom);
		
		var $container = $('div.content');
		var $a = $(this);
		var uuid = $a.data('uuid')[0];
		var image = new Image();
		image.onload = function(){
			//装载图片
			var r = redrawImage(image.width, image.height, 1050, 100);
			image.width = r.width;
			image.height = r.height;
			$container.empty().append(image);
			$container.find('img:eq(0)').css('border','1px solid #ccc');
			if(bPoup) bPoup.reposition(100);
		};
		image.src=showImageRequestPath+"?uuid="+uuid;
		
		e.preventDefault();
		var bPoup = $('#'+domId).bPopup({
    		speed: 650,
    		position:['auto','auto'],
    		onOpen:function(){
    			if(!image.complete){
    			$container.html('正在加载图片');
    			}
    		},
    		onClose:function(){
    			$('.popup').remove();
    		}
		});
	});
};
$(function(){
    var $duration = $( "#duration" ),
    $repeat = $( "#repeat" ),
    $durationUpdate = $('#duration_update');
    $repeatUpdate = $('#repeat_update');
    
    $allFields = $( [] ).add( $duration ).add( $repeat ).add( $durationUpdate ).add( $repeatUpdate ),
    $tips = $( ".validateTips" );
    
	$('.myForm-dialog').dialog({
		autoOpen:false,
		height:260,
		width: 350,
		model: true,
		close: function(){
			$allFields.val("").removeClass("ui-state-error");
		}
	});
	initContentAddEvent();
	initContentUpdateEvent();
	
	initShowImgeEvent('popup');
});

</script>
    
</body>
</html>