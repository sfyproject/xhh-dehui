$().ready(function() {
	validateRule();
	// $("#signupForm").validate();
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
		url : "/back/api/admin/resetPwd",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.msg("系统错误，联系管理员");
		},
		success : function(data) {
			if (data.ret == '0') {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
				parent.load();

			} else {
				parent.layer.msg(data.msg);
			}

		}
	});

}

function validateRule() {
	var icon = '<i class="fa fa-times-circle"></i> ';
	$("#signupForm").validate({
		rules : {
			adminPassword : {
				required : true,
				minlength : 5
			},
			confirm_password : {
				required : true,
				minlength : 5,
				equalTo : "#adminPassword"
			}
		},
		messages : {
			adminPassword : {
				required : icon + "请输入您的密码",
				minlength : icon + "密码必须5个字符以上"
			},
			confirm_password : {
				required : icon + "请再次输入密码",
				minlength : icon + "密码必须5个字符以上",
				equalTo : icon + "两次输入的密码不一致"
			}
		}
	})
}
