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
		url : "/back/api/user/update",
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
			userName : {
				required : true,
				minlength : 2
			},
			userPhone : {
				number:true,
				required : true,
				minlength : 2
			},
			userWallet : {
				number:true
			},
			
			userPassword : {
				required : true,
			},
			createTime : {
				required:true
			}
		},
		messages : {

			userName : {
				required : icon + "请输入您的用户名",
				minlength : icon + "用户名错误",
			},
			userPhone : {
				number : icon + "请输入手机号",
				required : icon + "请输入手机号",
				minlength : icon + "请输入手机号",
			},
			userWallet : {
				number : icon + "请输入数字",
			},
			userPassword : {
				required : icon+ "请输入密码",
			},
			createTime : {
				required : icon + "请输入时间",
			},
			
		}
	})
}
