
$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/back/api/receiving/save",
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
		message : 'This value is not valid',
	    excluded : [':disabled'],//[':disabled', ':hidden', ':not(:visible)']
	    feedbackIcons : {
	        valid : 'glyphicon glyphicon-ok',
	        invalid : 'glyphicon gluphicon-remove',
	        validating : 'glyphicon glyohicon-refresh'
	    },
	
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
			
		},
		 submitHandler: function(form){
		        loading('正在提交，请稍等...');
		        form.submit();
		    },

		    errorPlacement: function(error, element) {
		        if (element.hasClass('selectpicker')) {
		            error.insertAfter('.bootstrap-select');
		        
		            var arr= $("#orderStatus").parentNode;
		        	 
		         
		        }else {
		            error.insertAfter(element);
		        }
		    }
		
		
	})
}
$('#signupForm select').on('change', function(e) {
    $('#signupForm').validate().element($(this));
});

$('.selectpicker').on( 'hide.bs.select', function ( ) {
    $(this).trigger("focusout");
});
	
function returnList(){
	 var index = parent.layer.getFrameIndex(window.name);
	 parent.layer.close(index);
}


