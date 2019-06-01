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
		url : "/back/api/discountManage/edit",
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
		message : 'This value is not valid',
	    excluded : [':disabled'],//[':disabled', ':hidden', ':not(:visible)']
	    feedbackIcons : {
	        valid : 'glyphicon glyphicon-ok',
	        invalid : 'glyphicon gluphicon-remove',
	        validating : 'glyphicon glyohicon-refresh'
	    },
	
		rules : {
			couponName : {
				required : true
			},
			facePrice : {
				required : true
			},
			
			startTime : {
				required : true
			},
			endTime : {
				required : true
			},
			
		},
		messages : {

			couponName : {
				required : icon + "请输入优惠券名称"
			},
			facePrice : {
				required : icon + "请输入面值"
			},
			startTime : {
				required : icon + "请输入生效时间"
			},
			endTime : {
				required : icon + "请输入失效时间"
			},
			
		}
	})
}

