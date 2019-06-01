
$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	ignore: ":hidden:not(select)",//解决无法校验select 
	submitHandler : function() {
		save();
	}
});

function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/back/api/vip/save",
		data : $('#signupForm').serialize(),// 你的formid
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
	var icon = '<i class="fa fa-times-circle"></i> ';
	$("#signupForm").validate({
		rules : {
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


function returnList(){
	 var index = parent.layer.getFrameIndex(window.name);
	 parent.layer.close(index);
}



