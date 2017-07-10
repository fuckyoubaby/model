<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<html>
<head>
    <title>电梯广告资源管理平台</title><meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <c:import url="/WEB-INF/decorators/csspart.jsp"/>
    <c:import url="/WEB-INF/decorators/jspart.jsp"/> 
    <link href="${pageContext.request.contextPath}/css/htmlstyle.css" rel="stylesheet" type="text/css" />
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
        <p><strong>设备MAC管理: </strong> 设备MAC批量添加</p>
    </div>

    <div class="wrapper">
        <!-- Note -->
      <form  class="form"  method="post">
       	<fieldset>
      	<input type="hidden" name="boxmacUuid" value="${boxmac.uuid}"/>
        <div class="widget">
             <div class="title">
             	<img src="${pageContext.request.contextPath}/images/icons/dark/list.png" alt="" class="titleIcon">
             	<h6>批量添加</h6>
             	<a class="button blueB" title="" href="${pageContext.request.contextPath}/backend/boxmactemplatedownload.html" style="float: right;margin: 3px 10px 0 0;" target="_blank">
                    <img class="icon" alt="" src="/adplatform/images/icons/light/download3.png">
                    <span>下载导入模板</span>
                </a>
             </div>
             <div class="formRow">
                 <label>MAC文件:</label>
                 <div class="formRight">
                	 <input type="file" id="file"  />
                 </div>
                 <div class="clear"></div>
             </div>
             <div class="formRow" >
                <label>上传进度:</label>
                <div class="formRight" >
                    <div id="upload-progress" class="ui-progressbar ui-widget ui-widget-content ui-corner-all" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="80">
                    </div>
                </div>
                <div class="clear"></div>
            </div>
            <div class="formRow" >
                <label>处理进度:</label>
                <div class="formRight" id="progressLine">
                    	请选择文件，进行 MAC 批量添加 ...
                </div>
                <div class="clear"></div>
            </div>
             <div class="formSubmit">
                 <input type="button" value="返回" class="greenB" onclick="window.location.href='${pageContext.request.contextPath}/backend/boxmacindex.html'"/>
                 <input id="uploadButton" type="button" value="上传" class="blueB" />
             </div>
            <div class="clear"></div>
         </div>
         </fieldset>
       </form>
         
    </div>

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>

</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript">
var uploadTimer;
var uploadFile = function(){
	if(uploadTimer){
		clearTimeout(uploadTimer);
	}
	uploadTimer = setTimeout(uploadFileTimer, 1000);
};

function strToJson(str){ 
return (new Function("return " + str))(); 
};

 var uploadFileTimer = function(){
	 $('#uploadButton').unbind("click");
	 var files = document.getElementById('file').files;
	 var file = files[0];
	 var $progressline = $('#progressLine');
	 //H5的form表单
	 var form = new FormData();
	 form.append("file",file);
	 
	 var xhr = new XMLHttpRequest();
	 xhr.open("post","${pageContext.request.contextPath}/backend/ajaxboxmacfileupload.html",true);
	 xhr.onreadystatechange = function(){
		 if(this.readyState == 4 && this.status ==200){
			 var data = this.responseText;
			 console.log(data);
			 if((typeof data).toLowerCase() == 'object'){
				 if(data.result){
					 $progressline.text('处理成功！'+data.msg);
				 }else{
					 $progressline.text('处理失败！'+data.msg); 
				 }
			 }else{
				 var obj = strToJson(data);
				 if((typeof obj).toLowerCase() == 'object'){
					 if(obj.result){
						 $progressline.text('处理成功！'+obj.msg);
					 }else{
						 $progressline.text('处理失败！'+obj.msg); 
					 }
				 }else{
					 $progressline.text('处理失败，请稍后刷新后重试！'); 
				 }
			 } 
		 }
	 };
	 
	 xhr.upload.onprogress = progressFunction;
	 xhr.send(form);
 };
 
 $('#uploadButton').bind("click", uploadFile);
 
 
 var progressFunction =function(){
	 return function(evt){
		 if(evt.lengthComputable){
			 var p = evt.loaded / evt.total;
			 var pNum = Math.floor(p*100);
			 displayProgress(pNum);
		 }
	 };
 }(); 

 $( "#upload-progress" ).progressbar({
     value: false
   });
 
 var displayProgress = function(){
	 var $progressbar = $('#upload-progress');
	 var $progressline = $('#progressLine');
	 return function(progress){
		 $progressbar.progressbar("value", progress);
		 $progressline.text('正在上传文档，进度: '+progress+'% ...');
		 if(progress==100){
			 $progressline.text('文档上传完成，开始解析 ...');
		 }
	 };
 }();

</script>
</body>
</html>