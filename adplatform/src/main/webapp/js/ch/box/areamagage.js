var select_area_uuid;
var current_page;
var settings = {
    align : 'center',									//Valid values, left, right, center
    top : 150, 											//Use an integer (in pixels)
    width : 600, 										//Use an integer (in pixels)
    padding : 10,										//Use an integer (in pixels)
    backgroundColor : 'white', 						    //Use any hex code
    source : '', 				    					//Refer to any page on your server, external pages are not valid e.g. http://www.google.co.uk
    borderColor : '#333333', 							//Use any hex code
    borderWeight : 4,									//Use an integer (in pixels)
    borderRadius : 5, 									//Use an integer (in pixels)
    fadeOutTime : 300, 									//Use any integer, 0 : no fade
    disableColor : '#666666', 							//Use any hex code
    disableOpacity : 40, 								//Valid range 0-100
    loadingImage : ''
};
jQuery(function() {
    settings.loadingImage = getContextPath() + '/js/popup/loading.gif';
    init_numberPaging(7, 7, refreshCommunities);
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
            items: customMenu
        },
        "plugins" : ["themes","json_data","ui","core","crrm", "cookies","contextmenu"]
    }).bind("select_node.jstree", function (event, data) {
        select_area_uuid = data.rslt.obj.attr("id");
        jQuery("#areaName").html('地区&nbsp;&nbsp;:&nbsp;&nbsp;' + data.rslt.obj.attr("path"));
        refreshCommunities(1, tableRows);
    });

    jQuery(document).keyup(function(event) {
        if (event.keyCode == 27) {
            closePopup(settings.fadeOutTime);
        }
    });

});
function openAreaDialog(uuid, opt) {
    settings.top = 150;
    settings.source = 'areaform.html?method=' + opt + '&uuid=' + uuid;
    openModalPopup(settings);
}

function openModalPopup(obj) {
    modalPopup(obj.align, obj.top, obj.width, obj.padding, obj.disableColor, obj.disableOpacity, obj.backgroundColor, obj.borderColor, obj.borderWeight, obj.borderRadius, obj.fadeOutTime, obj.source, obj.loadingImage);
}

function customMenu(node) {
    var items = {
        "createItem": {
            "label": "创建目录",
            "action": function () {
                openAreaDialog(node.attr("id"), "add");
            }
        },
        "editItem": {
            "label": "修改目录",
            "action": function (node) {
                openAreaDialog(node.attr("id"), "edit");
            }
        },
        "deleteItem": {
            "label": "删除目录",
            "action": function (node) {
                $.ajax({
                    type: "post",
                    url: 'areadeletecheck.html',
                    data: {uuid:node.attr("id")},
                    success:function(result){
                        if("true" == result.message) {
                            if(confirm("你要删除该目录吗?")) {
                                $.ajax({
                                    type: "post",
                                    url: 'areadelete.html',
                                    data: {uuid:node.attr("id")},
                                    success:function(result){
                                        if("true" == result.message) {
                                            var tree = jQuery.jstree._reference("#tree");
                                            var currentNode = tree._get_node(null, false);
                                            var parentNode = tree._get_parent(currentNode);
                                            tree.refresh(parentNode);
                                        } else {
                                            alert("删除失败!");
                                        }
                                    }
                                });
                            }
                        } else {
                            alert("对不起，该地区包含子地区或者小区，不能删除!");
                        }
                    }
                });
            }
        }
    };

    if ($(node).hasClass("folder")) {
        delete items.deleteItem;
    }

    return items;
}

function addCommunityDialog() {
    settings.top = 100;
    settings.source = 'communityform.html?method=add&areaUuid=' + select_area_uuid;
    openModalPopup(settings);
}

function updateCommunityDialog(uuid){
	 settings.top = 100;
	 settings.source = 'communityform.html?method=update&uuid=' + uuid;
	 openModalPopup(settings);
}

function saveArea(form) {
    var areaName = jQuery("#name").val();
    if (areaName == null || areaName == '') {
        jQuery("#areaName_help").css("display", "block");
    } else {
        jQuery("#areaName_help").css("display", "none");
        form.submit();
    }
}

function saveCommunity() {
    var communityUuid = jQuery("#communityUuid").val();
    var areaUuid = jQuery("#areaUuid").val();
    var communityName = jQuery("#communityName").val();
    var abbreviation = jQuery("#abbreviation").val();
    var comment = jQuery("#comment").val();
    if (communityName == null || communityName == '') {
        jQuery("#communityName_help").css("display", "block");
    } else {
        jQuery("#communityName_help").css("display", "none");
        $.ajax({
            url : 'communityform.html',
            type : "POST",
            data : {
                uuid : communityUuid ? communityUuid : '',
                areaUuid : areaUuid ? areaUuid : '',
                name : communityName ? communityName : '',
                abbreviation : abbreviation ? abbreviation : '',
                comment : comment ? comment : ''
            },
            dataType : "json",
            cache : false,
            success : function(result) {
                if (result == null || typeof(result.result) == 'undefined' || result.result == 'false') {
                    alert("saveCommunity 失败！");
                } else {
                    refreshCommunities(current_page, tableRows);
                }
                closePopup(settings.fadeOutTime);
            }
        });
    }
}

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
                        var checkDetails = "<a class=\"button blueB\" href=\"boxinfomanagement.html?communityUuid=" + list[i].uuid + "\"><span>查看</span></a>";
                        var modifyInfo = "<a class=\"button blueB\" onclick=\"updateCommunityDialog('"+list[i].uuid+"');\"><span>编辑</span></a>";

                        var checkBox = "<input type='checkbox' value='"+list[i].uuid+"' name='selectBoxInfo'/>";
                        tableHtml += '<tr>';
                        tableHtml += '<td>' + checkBox + '</td>';
                        tableHtml += '<td>' + list[i].num + '</td>';
                        tableHtml += '<td>' + list[i].timestamp + '</td>';
                        tableHtml += '<td>' + list[i].name + '</td>';
                        tableHtml += '<td>' + abbreviation + '</td>';
                        tableHtml += '<td>' + comment + '</td>';
                        tableHtml += '<td>' + checkDetails +'<span>&nbsp;&nbsp;</span>'+modifyInfo + '</td>';
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
}
