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
        <p><strong>内容升级管理: </strong>对升级进行内容组织和范围配置</p>
    </div>

    <div class="wrapper">
        <!-- Note -->
        <div class="widgets">
        	
			<div class="twoOne">
                <div class="widget">
                    <div class="title">
                    	<img src="${pageContext.request.contextPath}/images/icons/dark/timer.png" alt="" class="titleIcon">
                    	<h6 style="margin-right:20px">可选播放内容</h6>
                    	<input type="text" id="keyword" style="width: 200px; height: 25px;vertical-align:top;margin-top:5px;text-indent:0.5em" placeholder="名称"/>&nbsp;
	                    <a href="javascript:void(0);" class="button" name="find" onclick="searchKeyword();" style="margin-top:3px">
	                    	<img src="${pageContext.request.contextPath}/images/searchBtnSmall.png" width="30" height="30"/>
	                    </a>
                    	<div class="num"><a href="${pageContext.request.contextPath}/backend/playcontentform.html?contentUpgradeId=${contentUpgrade.uuid}" class="greenNum">新增</a></div></div>
                    <table cellpadding="0" cellspacing="0" width="100%" class="sTable taskWidget" >
                        <thead>
                            <tr>
                                <td width="100">名称</td>
                                <td width="60">版本</td>
                                <td width="60">广告数量</td>
                                <td >创建时间</td>
                                <td width="100">操作</td>
                            </tr>
                        </thead>
                        <tbody id="item-tbody">
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
			
				<div class="widget">
                    <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/stats.png" alt="" class="titleIcon"><h6>组织的播放内容信息</h6></div>
                   <div class="body upgrade-content" style="border-bottom:1px solid #E2E2E2" >
                   		<c:choose>
                   			<c:when test="${empty playContent}">
                   			<p>还未配置内容，请配置！</p>
                   			</c:when>
                   			<c:otherwise>
		                        <h4 class="pt5">名称</h4><p class="currentUuid" data-currentuuid='["${playContent.uuid}"]'><span class="greyBack">${playContent.name}</span> </p>
			                    <h4 class="pt5">版本</h4><p><span class="blueBack">${playContent.version }</span></p>
			                    <h4 class="pt5">数量</h4><p><span class="blueBack">${playContent.amount }</span></p>
		                        <h4 class="pt5">创建时间</h4><p class=""><fmt:formatDate value="${playContent.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                   			</c:otherwise>
                   		</c:choose>
                   </div>
                    <div class="formSubmit upgrade-process" style="float:left">
                    	<c:if test="${! empty playContent}">
                    		<input type="button" value="移除" class="blueB" onclick="resetPlayContent()" />
                    		<input type="button" value="详情" class="blueB" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/playcontent.html?playContentId=${playContent.uuid}&contentUpgradeId=${contentUpgrade.uuid}'" />
                    	</c:if>
                    </div>
                    <div class="clear"></div>
                </div>
			</div>
			<div class="oneThree">
				<div class="widget">
                    <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/clipboard.png" alt="" class="titleIcon"><h6>内容升级基本信息</h6></div>
                    <div class="body" style="border-bottom:1px solid #E2E2E2">
                        <h4 class="pt5">编号</h4><p><span class="greenBack content-uuid-dom">${contentUpgrade.uuid}</span> </p>
                        <h4 class="pt10">名称</h4><p> <span class="greyBack">${contentUpgrade.name}</span> </p>
                        <c:if test="${! empty contentUpgrade.description}">
	                        <h4 class="pt10">描述</h4><p><span class="blueBack">${contentUpgrade.description }</span></p>
                        </c:if>
                        <h4 class="pt10">创建时间</h4><p><fmt:formatDate value="${contentUpgrade.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                    </div>
                    <div class="formSubmit" style="float:left">
                        <input type="button" value="返回" class="blueB" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/contentupgradeinfo.html?contentUpgradeId=${contentUpgrade.uuid}&filterName=${filterName}&current=${current}'">
                    </div>
                    <div class="clear"></div>
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
<div id="content-reset-dialog-confirm" title="系统对话框" class="myConfirm-dialog">
    <p >
        <span class="ui-icon ui-icon-alert" style="float:left; margin:2px 4px 20px 0;"></span>
        <span class="content">确认移除已选播放内容？</span>
    </p>
</div>
<div id="play-content-choose-dialog" title="配置播放策略" class="myConfirm-dialog">
	 <p>
     <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        只能选取一项内容，请确认你是否要选择此项?
    </p>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript">
var notyf = new Notyf({delay:3000});
var upgradeUuid = '${contentUpgrade.uuid}';
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
};
function contentConfig(obj){
	var dialogId = '#play-content-choose-dialog';
	var $dialog = $(dialogId);
	var $tr= $(obj).parent().parent();
	var uuid = $tr.data('itemuuid')[0];
	var currentUuidDom = $("p.currentUuid");
	var currentUuid = '';
	if(currentUuidDom.length!=0){
		currentUuid = currentUuidDom.data('currentuuid')[0];		
	}
	var confirmButton = {
			'确认': function() {
                $.ajax({
                	type:'post',
                	url:'${pageContext.request.contextPath}/backend/contentupgradeupdateplay.html',
                	data:{contentUpgradeId: upgradeUuid, newContentId: uuid, oldContentId: currentUuid},
                	success:function(data){
                		if((typeof data).toLowerCase() == "object"){
                			if(data.result){
            					notyf.confirm(data.msg?data.msg:'配置成功');
								showPlayContentDom($tr);            					
            	            	initResource();
            				}else{
            					notyf.alert(data.msg?data.msg:'配置失败');
            				}
                		}
                	}
                });
            	$( this ).dialog( "close" );
            }	
	}; 
	dialogInitAndOpen($dialog, confirmButton);
};

function showPlayContentDom($obj){
	if($obj){
		var params= new Array();
		var uuid = $obj.data('itemuuid')[0];
		$obj.find('td').each(function(i,item){
			if(i<=3){
				params.push($(item).text());
			}
		});
		 var html = '<h4 class="pt5">名称</h4><p class="currentUuid" data-currentuuid=\'["'+uuid+'"]\'><span class="greyBack">'+params[0]+'</span> </p>'
         +'<h4 class="pt5">版本</h4><p><span class="blueBack">'+params[1]+'</span></p>'
         +'<h4 class="pt5">数量</h4><p><span class="blueBack">'+params[2]+'</span></p>'
         +'<h4 class="pt5">创建时间</h4><p class="">'+params[3]+'</p>';
         $('div.body.upgrade-content').html(html);
         $('div.formSubmit.upgrade-process').html('<input type="button" value="移除" class="blueB" onclick="resetPlayContent()" />'
        		 +'<input type="button" value="详情" class="blueB" onclick="javascript:window.location.href=\'${pageContext.request.contextPath}/backend/playcontent.html?playContentId='+uuid+'&contentUpgradeId='+upgradeUuid+'\'" />' );
	}
};

function showEmptyPlayContentDom(){
	 var html='<p>已移除内容，请配置！</p>';
	 $('div.body.upgrade-content').html(html);
     $('div.formSubmit.upgrade-process').empty();
}
function resetPlayContent(){
	var dialogId = '#content-reset-dialog-confirm';
	var $dialog = $(dialogId);
	var currentUuidDom = $("p.currentUuid");
	var currentUuid = '';
	if(currentUuidDom.length!=0){
		currentUuid = currentUuidDom.data('currentuuid')[0];		
	}
	var confirmButton = {
			'确认': function() {
				$.ajax({
			    	type:'post',
			    	url:'${pageContext.request.contextPath}/backend/contentupgradeupdateplay.html',
			    	data:{contentUpgradeId: upgradeUuid, newContentId: '', oldContentId: currentUuid},
			    	success:function(data){
			    		if((typeof data).toLowerCase() == "object"){
			    			if(data.result){
								notyf.confirm('重置成功');
								showEmptyPlayContentDom();            					
				            	initResource();
							}else{
								notyf.alert('重置失败');
							}
			    		}
			    	}
			    });
				$( this ).dialog( "close" );
            }	
	}; 
	dialogInitAndOpen($dialog, confirmButton);
};

function contentDelete(playContentId,obj){
	var dialogId = '#content-delete-dialog-confirm';
	var $dialog = $(dialogId);
	var $span = $dialog.find('span.content:eq(0)');
	var $tr = $(obj).parent().parent();
	var name = $tr.find('td:eq(0)').text();
	$span.text('确定要删除名称为 [ '+name+' ] 的播放内容？');
	
	var confirmButton = {
		'删除' : function(){
			//ajax 请求删除
			$.ajax({
				type:'post',
				url:'${pageContext.request.contextPath}/backend/ajaxDeletePlayContent.html',
		    	data:{playContentId:playContentId },
		    	success:function(data){
		    		if((typeof data).toLowerCase() == "object"){
		    			if(data.result){
							notyf.confirm('删除成功');
			            	initResource();
						}else{
							notyf.alert('删除失败');
						}
		    		}else{notyf.alert('删除失败');}
		    	}
			});
			$(this).dialog('close');
		}
	};
	dialogInitAndOpen($dialog, confirmButton);
}

var playContentLoader = {
		search:function(){
			playContentLoader.load(0,5,function(itemcount){
				playContentLoader.initPage(itemcount);
			 });
			},
		load:function(pno,psize,callback){
			var keyword = $("#keyword").val();
			var params = {indexNo:pno,pageSize:psize,keyword:keyword};
			params.contentUpgradeId = upgradeUuid;
			$.ajax({
				type:"post",
				data:params,
				url:'${pageContext.request.contextPath}/backend/ajaxEnablePlayContent.html',
				success:function(data){
					$("#item-tbody").html(data);
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
					playContentLoader.load(pageNo *psize,psize);
				}
			});
		}
	};
	
	function searchKeyword(){
		playContentLoader.search();
	};
	function initResource(){
		playContentLoader.load(0,5,function(itemcount){
			playContentLoader.initPage(itemcount);
		});
	};
	initResource();
</script>
    
</body>
</html>