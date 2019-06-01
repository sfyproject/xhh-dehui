var page = 1;
var size = 10;
var url_get_express_page = "/back/api/express/page.json";

function show() {
	
	var expressNo = $.trim($("#express_no").val());
	var expressOno = $.trim($("#express_ono").val());
	var startDate = $.trim($("#start_date").val());
	var endDate = $.trim($("#end_date").val());
	
	$.ajax({
		type: "GET",
		url: url_get_express_page,
		data: { 
			page: page, size: size, 
			startDate: startDate, endDate: endDate, 
			storeExpressNo: expressNo, storeExpressOno: expressOno 
		},
		dataType: 'json',
		success: function(json) {
			if (json.ret == '0') {
				handleHttp_page_succ(json.data);
			}
		}
	});
	
}

function handleHttp_page_succ(data) {
	
	var html = template('expresssTable-Template', { expresss: data.list });
	
	$('#datashow').html(html);
	laypage({
		cont: 'page',
		pages: data.pages,
	    skin: 'molv',
	    skip: true,
	    groups: 3,
	    curr: data.prePage + 1,
	    total: data.total,
	    jump: function (obj, first) {
	    	if(!first){
	    		page = obj.curr;
		    	show();
	    	}
	    }
	});
	
}

function express_add(title, url, width, height) {
	layer_show(title, url, width, height);
}

function express_del(obj, storeExpressNo) {
	
	layer.confirm('确认删除', function(index) {
		$.ajax({
			type: 'POST',
			url: '/back/api/express/del.json',
			data: { 'storeExpressNo': storeExpressNo },
			success: function(json) {	
				if (json.ret == "0") {
					$(obj).parents("tr").remove();
					layer.msg('删除成功', { icon: 1, time: 1000 });
				} else {
					layer.msg('删除失败', { icon: 1, time: 1000 });
				}
			}
		});		
	});
	
}

function express_edit(title, url, width, height) {
	layer_show(title, url, width, height);
}

$(document).ready(function(e) {
	show();
})