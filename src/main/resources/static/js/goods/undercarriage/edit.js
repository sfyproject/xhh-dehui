$().ready(function() {
	validateRule();
	goodsLabelOption();
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
		url : "/back/api/undercarriage/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.ret == 0) {
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
			goodsTitle:{
				required : true
			},
			goodsLabel:{
				required : true
			},
			goodsInventory : {
				required : true,
				digits:true
			},
			goodsLibrary : {
				required : true,
				number:true
			},
			goodsSales : {
				required : true,
				digits:true
			},
			goodsOriginal : {
				required : true,
				number:true
			},
			goodsPrice : {
				required : true,
				number:true
			},
			goodsSecond : {
				number:true
			},
			goodsGroup : {
				number:true
			},
			goodsType : {
				required : true
			},
			goodsSpec : {
				required : true
			}
			
		},
		messages : {

			goodsTitle:{
				required : icon+"请输入商品标题"
			},
			goodsLabel:{
				required : icon+"请输入商品标签"
			},
			goodsInventory : {
				required : icon+"请输入商品数量",
				digits : icon+"请输入整数"
			},
			goodsLibrary : {
				required : icon+"请输入入库数量",
				number:icon+"请输入入库数字"
			},
			goodsSales : {
				required : icon+"请输入商品销量",
				digits:icon+"请输入整数",
			},
			goodsOriginal : {
				required : icon+"请输入商品原价",
				number:icon+"请输入整数"
			},
			goodsPrice : {
				required : icon+"请输入商品现价",
				number:icon+"请输入整数"
			},
			goodsSecond : {
				number:icon+"请输入整数"
			},
			goodsGroup : {
				number:icon+"请输入整数"
			},
			goodsType : {
				required : icon+"请输入商品类型"
			},
			goodsSpec : {
				required : icon+"请输入商品规格"
			}
		},
		errorPlacement: function(error, element) {
		       var id=element.context.id;
		        if (element.hasClass('selectpicker')) {
		        	var arr=$(".bootstrap-select");
		        	for(var i in arr){
		        		var obj=arr[i].innerHTML;
		        		var patt='';
		        		if(id=="goodsLabel"){
		        			 patt=/goodsLabel/g
		        		}else if(id=="goodsType"){
		        			 patt=/goodsType/g
		        		}else if(id=="goodsImage"){
		        			 patt=/goodsImage/g
		        		}
		        		
		        		var result=patt.test(obj);
		        		if(result){
		        			error.insertAfter(arr[i]);
		        		}
		        	}
		        	
		            
		            
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

$("#goodsLabel").change(function(){
	goodsLabelOption();
});

function goodsLabelOption(){
	var goodsLabel=$("#goodsLabel").val();
	$.ajax({
		cache : true,
		type : "GET",
		url : "/back/api/sort/sortlist",
		data : {"parentId":goodsLabel},// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.ret == '0') {
				var str=""
					var goodsType=$("#goodsTypet").val();
					$.each(data.data,function(index,item){
						if(goodsType==item.sortId){
							str+='<option value='+item.sortId+' selected = "selected"> '+item.sortName+'</option>';
						}else{
							str+='<option value='+item.sortId+' > '+item.sortName+'</option>';
						}
						
						   
					})
					$("#goodsType").html(str);
				$('#goodsType').selectpicker('refresh');

			

			}
		}
	})
	
}

