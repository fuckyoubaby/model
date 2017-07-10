var select_area_uuid;

    jQuery(function() {
        jQuery.ajaxSetup({cache:false});
        jQuery("#tree").jstree({
            "json_data":{
                "ajax": {
                    "method": "POST",
                    "url" : 'areatree.html',
                    "data":function (n) {
                        return {
                            "uuid" : n.attr ? n.attr("id") : ''
                        };
                    }
                }
            },
            "themes" : {
                "theme" : "classic",
                "dots" : true,
                "icons" : true
            },
            contextmenu: {
                items: ''
            },
            "plugins" : ["themes","json_data","ui","core","crrm", "cookies","contextmenu"]
        }).bind("select_node.jstree", function (event, data) {
            select_area_uuid = data.rslt.obj.attr("id");
            jQuery("#areaName").html('地区&nbsp;&nbsp;:&nbsp;&nbsp;' + data.rslt.obj.attr("path"));

            refreshBoxStatisNumber();
        });

        jQuery(document).keyup(function(event) {
            if (event.keyCode == 27) {
                closePopup(settings.fadeOutTime);
            }
        });
    });

    var option = {
        tooltip : {
            trigger: 'axis'
        },
//         color : [
//            '#B0D961','#FF6666','black','#CC99FF','#B0D961',
//            '#99CCCC','#BEBBD8','#FFCC99','#8DD3C8','#FF9999',
//            '#CCEAC4','#BB81BC','#FBCCEC','#CCFF66','#99CC66',
//            '#66CC66','#FF6666','#FFED6F','#ff7f50','#87cefa'
//        ],
        legend: {
            data:['在线版本']
        },
        toolbox: {
            show : true,
            feature : {
                saveAsImage : {show: true}
            }
        },
        xAxis : [
            {
                type : 'value',
                splitNumber: 2,
                axisLabel : {
                    formatter: function (value) {
                        return value
                    }
                },
                splitLine : {
                    show: true
                }
            }
        ],
        yAxis : [
            {
                type : 'category',
                position: 'left',
                boundaryGap: true,
                data : ['1月','2月','3月','4月']
            }
        ],
        series : [
            {
                name: '在线版本',
                type: 'bar',
                stack: '总量',
                data:[2.0, 4.9, 7.0, 23.2],
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            position: 'insideRight'
                        }
                    }
                }
            }   
        ]
    };

    function refreshBoxStatisNumber() {
        $.blockUI();

        $.ajax({
            type : "POST",
            url : getContextPath() + "/backend/getAppVersionstatisticdata.html",
            data:{areaUuid:select_area_uuid},
            dataType : "json",
            success : function(result) {
            	//获得数据
                var datas = result.items;
                var length = datas.length;
                
                //设定高度
                var height = length * 60;
                jQuery("#barStack").height(height);

                //组装数据
                var xNames = new Array();
                
                var number = new Array();
                for(var i=0; i<length; i++) {
                    xNames.push(datas[i].name);
                    number.push(datas[i].number);
                }
                //设置标题
                option.yAxis[0].data = xNames;

                //设置数据
                option.series[0].data = number;

                // 使用刚指定的配置项和数据显示图表。
                var myChart = echarts.init(document.getElementById('barStack'));
                myChart.setOption(option);

                $.unblockUI();
            }
        });

    }