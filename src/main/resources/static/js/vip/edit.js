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
		url : "/back/api/vip/update",
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
			vipType : {
				required : true
			},
			vipPrice : {
				required : true,
				number:true
			},
			
			vipDiscount : {
				required : true,
				number:true
			},
			vipStart : {
				required:true
			},
			vipEnd : {
				required:true
			}
		},
		messages : {

			vipUid : {
				required : icon + "请选择用户"
			},
			vipType : {
				required : icon + "请选择会员类型"
			},
			vipPrice : {
				required : icon + "请输入价格",
				number : icon + "请输入数字"
			},
			vipDiscount : {
				required : icon+ "请输入折扣",
				number : icon + "请输入数字"
			},
			vipStart : {
				required : icon+ "请输入开始时间"
			},
			vipEnd : {
				required : icon+ "请输入结束时间"
			}
			
		}
	})
}
