/* play upgrade configuration 3 : area info choose */
var select_area_uuid;
var current_page;
jQuery(function() {
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
        "checkbox" : {"three_state": false},
        "plugins" : ["themes","json_data","ui","core","crrm", "cookies", "checkbox"]
    })
    /*.bind("select_node.jstree", function (event, data) {
        select_area_uuid = data.rslt.obj.attr("id");
        jQuery("#areaName").html('地区&nbsp;&nbsp;:&nbsp;&nbsp;' + data.rslt.obj.attr("path"));
        refreshCommunities(1, tableRows);
    })*/
    ;
});

