<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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
    <link href="${pageContext.request.contextPath}/css/paging.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadaddatapage/query.js"></script>
     <script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadaddatapage/paging.js"></script>
     <%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadaddatapage/uploaddatashow.js"></script> --%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/notyf/notyf.min.js"></script>    
    <style>
    	.my-table-footer{border:1px solid #cdcdcd; border-left:0px;border-right:0px;height:57px}
    	.table_process{margin-top:16px;float:left;margin-left:18px;}
    	.topTime{height:50px; width: 100%;margin-left: 100px;margin-top: 30px}
    	.startTime{height 50px;width:200px;float: left;}
    	.endTime{height 50px;width:200px;float: left;}
    	.submitDiv{height 50px;width:200px;float: left;}
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

    <div class="topTime">
    <form action="${pageContext.request.contextPath}/backend/getuploadaddata.html?mac=${box.mac}">
    	<div class="startTime">开始时间:<input name="startDate" type="date"></div>
    	<div class="endTime">结束时间:<input name="endDate" type="date"></div>
    	<input name="mac" value="${mac }" type="hidden">
    	<div class="submitDiv"><button type="submit" class="redB" style="height: 30px">提交</button></div>
    </form>
    </div>
    <div id="myChart" style="width:100%;height:400px; margin: auto;"></div>
    <%--结尾部分*******************************************************--%>
    <jsp:include page="/WEB-INF/decorators/footer.jsp"/>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/leftmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/paging/numberPaging.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/BlockUI/BlockUI.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/contextPath.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ch/sta/adtimesta.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
    <script type="text/javascript">
   

	
	option = {
		    title : {
		        text: '总时长和次数',
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['时长','次数']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		           data:${nameList}
		          // data:['1yue','2yue']
		           // data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'时长',
		            type:'bar',
		            data:${lengthList},
		           // data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        },
		        {
		            name:'次数',
		            type:'bar',
		            data:${timesList},
		            //data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
		            markPoint : {
		                data : [
		                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
		                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name : '平均值'}
		                ]
		            }
		        }
		    ]
		};

		window.onload = makebar;

		function makebar()
		{
			var myChart = echarts.init(document.getElementById('myChart')); 
		    myChart.setOption(option, true);   //为echarts对象加载数据
		} 
</script>
</body>
</html>
