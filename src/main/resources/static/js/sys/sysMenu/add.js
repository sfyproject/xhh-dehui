var prefix = "/back/api/sysMenu"
$(function() {
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		submit01();
	}
});
function submit01() {
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/save",
		data : $('#signupForm').serialize(),
		async : false,
		error : function(request) {
			laryer.alert("Connection error");
		},
		success : function(data) {
			if (data.ret == '0') {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				layer.alert(data.msg)
			}
		}
	});
}

function validateRule() {
	var icon = '<i class="fa fa-times-circle"></i> ';
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			},
			type : {
				required : true
			},
			orderNum : {
				digits: true
			},
			perms: {
				required : true,
				remote : {
					url : "/back/api/sysMenu/exitPerms", // 后台处理程序
					type : "post", // 数据发送方式
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据
						perms : function() {
							return $("#perms").val();
						}
					}
				}
			}
		},
		messages : {
			name : {
				required : icon + "请输入菜单名"
			},
			type : {
				required : icon + "请选择菜单类型"
			},
			orderNum : {
				digits : icon+"请输入整数",
			},
			perms : {
				required : icon + "请权限标识",
				remote : icon + "权限标识已存在"
			}
		},
		errorPlacement: function(error, element) {
		       var id=element.context.id;
		        if (id=="") {
		        	var arr=$(".radio-inline");
		        	error.insertAfter(arr[2]);
		        }else {
		            error.insertAfter(element);
		        }
		    }
	})
}