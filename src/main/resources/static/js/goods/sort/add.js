var prefix = "/back/api/sort"
$(function() {
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		submit();
	}
});
function submit() {
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
			sortName : {
				required : true,
				remote : {
					url : "/back/api/sort/existSortName", // 后台处理程序
					type : "post", // 数据发送方式
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据
						sortName : function() {
							return $("#sortName").val();
						},
						parentId : $("#parentId").val()
					}
				}
			},
			
			number: {
				digits	:	true,
				required : true
			}
		},
		messages : {
			sortName : {
				required : icon + "请输入菜单名",
				remote : icon+"名称重复"
			},
			number : {
				digits : icon + "请输入整数",
				required : icon + "请输入次序"
			}
		}
	})
}
function returnList(){
	 var index = parent.layer.getFrameIndex(window.name);
	 parent.layer.close(index);
//	parent.layer.close(layer.index);
}