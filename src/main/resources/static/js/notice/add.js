
$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	var a=$('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/back/api/notice/add",
		data : $('#signupForm').serialize(),
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(r) {
			if (r.ret == '0') {
				parent.layer.msg(r.msg);
				parent.reLoad();
				 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				 parent.layer.close(index);
				 parent.load();
			} else {
				parent.layer.alert(r.msg);
			}
		}
	});
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			title : "required",
			author : "required",
			content :"required"
		},
		messages : {
			title : "请填写文章标题",
			author : "请填写文章作者",
			content :"请填写文章内容"
		}
	});
}

function returnList(){
	 var index = parent.layer.getFrameIndex(window.name);
	 parent.layer.close(index);
}






