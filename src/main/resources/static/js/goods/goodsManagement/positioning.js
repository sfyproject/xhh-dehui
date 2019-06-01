var zNodes =[];

$().ready(function() {
	validateRule();
	getTree();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
//错误
function save() {
	
	
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var arr=treeObj.getCheckedNodes(true);
	var list=[];
	for (var i in arr){
		if(arr[i].isParent == false){
			 var obj = new Object();
			 obj.id=arr[i].id;
			 obj.name=arr[i].name;
			list.push(obj);
		}
	}
	if(list.length==0){
		layer.alert('至少选择一个商品', {
			icon: 5,
			title: "错误"
			});
		return false;
	}
	var goodsTreeArr= JSON.stringify(list);
	$.ajax({
		cache : true,
		type : "POST",
		url : "/back/api/lowerLimit/save",
		data : {"goodsTreeArr":goodsTreeArr,"number":$("#lowerLimit").val()},// 你的formid
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
			lowerLimit : {
				required:true,
				digits:true
			}
		},
		messages : {
			lowerLimit : {
				required : icon+ "请输入下限",
				digits : icon + "请输入整数"
			}
			
			
		},

	})
}

function getTree(){
	$.ajax({
		cache : true,
		type : "POST",
		url : "/back/api/goodsManagement/goodsTree",
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(r) {
			if (r.ret == '0') {
				
				zNodes=r.data;
				var setting = {  
					    //check属性放在data属性之后，复选框不起作用  
					    check: {  
					        enable: true  
					    },  
					    data: {  
					        simpleData: {  
					            enable: true  
					        }
					    }  
					};
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				
			} else {
				parent.layer.alert(r.msg);
			}

		}
	});
}







