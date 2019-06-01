$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/back/api/receiving/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.ret == '0') {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
				parent.load();

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

};
function returnList(){
	 var index = parent.layer.getFrameIndex(window.name);
	 parent.layer.close(index);
//	parent.layer.close(layer.index);
}
function validateRule() {
	var icon = '<i class="fa fa-times-circle"></i> ';
	$("#signupForm").validate({
		rules : {
			vipUid : {
				required : true
			},
			orderStatus : {
				required : true
			},
			orderPrice : {
				required : true,
				number:true
			},
			orderPhone : {
				required : true,
				number:true
			},
			
			orderName : {
				required : true,
			},
			orderAddress : {
				required:true
			},
			orderGroup : {
				required:true
			},
			orderDelivery : {
				required:true
			},
			orderFare : {
				required:true
			}
		},
		messages : {

			vipUid : {
				required : icon + "请选择用户"
			},
			orderStatus : {
				required : icon + "请选择支付状态"
			},
			orderPrice : {
				required : icon + "请输入价格",
				number : icon + "请输入数字"
			},
			orderPhone : {
				required : icon + "请输入手机号",
				number : icon + "请输入数字"
			},
			orderName : {
				required : icon+ "请输入收货人姓名"
			},
			orderAddress : {
				required : icon+ "请输入收货地址"
			},
			orderGroup : {
				required : icon+ "请选择购买方式"
			},
			orderDelivery : {
				required : icon+ "请输入选择送货方式"
			},
			orderFare : {
				required : icon+ "请输入运费"
			}
			
		}
	})
}

