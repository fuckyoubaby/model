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
        <p><strong>广告资源统计: </strong>对广告资源容量、数量进行统计</p>
    </div>

    <div class="wrapper">
        <!-- Note -->
		<div class="wdigets">
			<div class="oneThree">
	        	<div class="widget">
		           <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/clipboard.png" alt="" class="titleIcon"><h6>广告文件容量信息</h6></div>
					<div class="body" style="border-bottom:1px solid #E2E2E2">
		                 <h4 class="pt5">记录容量 / 实际容量</h4><p>${recordeAllSize}KB &nbsp;/&nbsp; <fmt:formatNumber type="number" value="${savedResourceSize}" pattern="0.00" maxFractionDigits="2"/> KB </p>
						 <h4 class="pt10">已用资源 / 未用资源</h4><p> ${recordeUsedSize} KB &nbsp;/&nbsp; ${recordeUnusedSize} KB </p>
		                 <h4 class="pt10">垃圾文件容量</h4><p><fmt:formatNumber type="number" value="${savedGarbageSize}" pattern="0.00" maxFractionDigits="2"/>  KB </p>
		                 <h4 class="pt10">无效记录容量</h4><p> ${recordGarbageSize} KB </p>
		             </div>
		             <div class="clear"></div>
		        </div>       
	        </div>
	        <div class="twoOne">
				<div class="widget">
	        		<div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/clipboard.png" alt="" class="titleIcon"><h6>资源容量统计信息</h6></div>
		        	<div class="body" style="padding:45px 14px;">
		        		<div id="main" style="width: 100%;min-height:180px;"></div>
		        	</div>
	        	</div>
	        </div> 
	        <div class="clear"></div> 
		</div>
		
		<div class="wdigets">
			<div class="oneThree">
	        	<div class="widget">
		           <div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/clipboard.png" alt="" class="titleIcon"><h6>广告文件数量信息</h6></div>
					<div class="body" style="border-bottom:1px solid #E2E2E2">
		                 <h4 class="pt5">记录数量/实际数量</h4><p> ${recordeAllAmount} &nbsp;/&nbsp; ${savedResourceAmount}  </p>
						 <h4 class="pt10">已用资源 / 未用资源</h4><p> ${recordeUsedAmount} &nbsp;/&nbsp; ${recordeUnusedAmount}  </p>
		                 <h4 class="pt10">垃圾文件数量</h4><p> ${savedGarbageAmount}</p>
		                 <h4 class="pt10">无效记录数量</h4><p>${recordGarbageAmount}</p>
		             </div>
			         <div class="formSubmit" style="float:left">
		             <c:choose>
		             	<c:when test="${savedGarbageAmount!=0 || recordGarbageAmount!=0}">
			             	<input type="button" value="清理空间" class="blueB" onclick="javascript:window.location.href='${pageContext.request.contextPath}/backend/static/adgarbageclean.html'">
		             	</c:when>
		             	<c:otherwise>
		             		<span>暂无垃圾，无需清理！</span>
		             	</c:otherwise>
		             </c:choose>
			         </div>
		             <div class="clear"></div>
		        </div>
	        </div>
	        <div class="twoOne">
		        <div class="widget">
		        	<div class="title"><img src="${pageContext.request.contextPath}/images/icons/dark/clipboard.png" alt="" class="titleIcon"><h6>资源数量统计信息</h6></div>
		        	<div class="body" style="padding:45px 14px;">
		        		<div id="legend_amount" style="width: 100%;min-height:180px;"></div>
		        	</div>
		        </div>
	        </div> 
	        <div class="clear"></div> 
		</div>
	
    </div>

    <%--开头部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>

</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/BlockUI/BlockUI.js"></script>
<script type="text/javascript">
var sizeArry=new Array();
sizeArry.push(${recordeUsedSize});
sizeArry.push(${recordeUnusedSize});
sizeArry.push(<fmt:formatNumber type="number" value="${savedGarbageSize}" pattern="0.00" maxFractionDigits="2"/>);
var amountArray=new Array();
amountArray.push(${recordeUsedAmount});
amountArray.push(${recordeUnusedAmount});
amountArray.push(${savedGarbageAmount});
var legendDataArray =['已用资源', '未用资源','垃圾资源'];

var myLegendChart =function(){
	return {
		option:{
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
	        toolbox: {
	            show : true,
	            feature : {
	                saveAsImage : {show: true}
	            }
	        },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis:  {
		        type: 'value'
		    }
		},
		initLegend: function(data){
			var jsonObj = {};
			jsonObj.yAxis={
					type:'category',
					data: [data.yAxisName]
			};
			var arrayObj = new Array();
			for(var i=0;i< data.legendDataArray.length;i++){
				arrayObj.push({
		            name: data.legendDataArray[i],
		            type: 'bar',
		            stack: '总量',
		            label: {
		                normal: {
		                    show: data.xAxisValueArray[i]>0 ? true:false,
		                    position: 'inside'
		                }
		            },
		            data: [data.xAxisValueArray[i]]
		        });
			}
			jsonObj.series = arrayObj;
			return jsonObj;
		},
		initChart: function(data){
			var lengendData = this.initLegend(data);
			//基于准备好的dom，初始化echarts实例
			var charDemo = echarts.init(document.getElementById(data.domId));
			var option = this.option;
			// 使用刚指定的配置项和数据显示图表。
			option.legend={ data: data.legendDataArray};
			charDemo.setOption($.extend(option,lengendData));
		}
	};
};
var parameters = {domId:'main', yAxisName:'资源容量(KB)', legendDataArray:legendDataArray, xAxisValueArray: sizeArry};
var parameters2 = {domId:'legend_amount', yAxisName:'资源个数(个)', legendDataArray:legendDataArray, xAxisValueArray: amountArray};

var myChartDemo = new myLegendChart();
myChartDemo.initChart(parameters);
myChartDemo.initChart(parameters2);

</script>
</body>
</html>