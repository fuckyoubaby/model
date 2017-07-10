var select_area_uuid;
var pageSize = 5;
var totalPage;
var currentPage;
var totalItem=0;

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
            refreshBoxInfo();
        });

        jQuery(document).keyup(function(event) {
            if (event.keyCode == 27) {
                closePopup(settings.fadeOutTime);
            }
        });
    });

  function refreshBoxInfo(){
	  $.ajax({
			type:"post",
			data:{areaId:select_area_uuid,
				startPosition:currentPage*pageSize,
				size:pageSize},
			url : getContextPath() + "/backend/ajaxRequestUploadADData.html",
			success:function(data){
				$("#tBody").html(data);
				//TODO itemConfigEventInit();
				//$('.tipS').tipsy({gravity: 's',fade: true});
				//if(callback){
					var itemcount = $("#tBody").find("tr:eq(0)").data("itemcount");
					totalItem = itemcount;
					$("#pageTool").empty();
					 $('#pageTool').Paging({pagesize:pageSize,count:totalItem,callback:function(page,size,count){
							//console.log(arguments)
							currentPage = page-1;
							getItems();
							}});
			}
		});
  }
  function getItems()
  {
	  $.ajax({
			type:"post",
			data:{areaId:select_area_uuid,
				startPosition:currentPage*pageSize,
				size:pageSize},
			url : getContextPath() + "/backend/ajaxRequestUploadADData.html",
			success:function(data){
				$("#tBody").html(data);
				//TODO itemConfigEventInit();
				//$('.tipS').tipsy({gravity: 's',fade: true});
				//if(callback){
					var itemcount = $("#tBody").find("tr:eq(0)").data("itemcount");
					totalItem = itemcount;
					
			}
		});
  }
 