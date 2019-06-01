//var menuTree;
var menuIds;
$(function() {
	getMenuTreeData();
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		getAllSelectNodes();
		save();
	}
});

function getAllSelectNodes() {
	var ref = $('#menuTree').jstree(true);// 获得整个树
	menuIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组
}
function getMenuTreeData() {
	$.ajax({
		type : "GET",
		url : "/back/api/sysRole/tree",
		success : function(data) {
			if (data.ret == '0') {
				loadMenuTree(data.data);
			} 
			
		}
	});
}
function loadMenuTree(menuTree) {
	$('#menuTree').jstree({
		'core' : {
			'data' : menuTree
		},
		"checkbox" : {
			"three_state" : true
		// 不起作用，修改了源代码的默认true--false
		},
		"plugins" : [ "wholerow", "checkbox" ]
	});
	$('#menuTree').jstree().open_all();
}

function save() {
	$('#menuIds').val(menuIds);
	var role = $('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/back/api/sysRole/save",
		data : role,// 你的formid
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(data) {
			if (data.ret == 0) {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
				parent.layer.reLoad();
			} else {
				parent.layer.msg(data.msg);
			}
		}
	});
}


function validateRule() {
	var icon = '<i class="fa fa-times-circle"></i> '
	$("#signupForm").validate({
		rules : {
			roleName : {
				required : true,
				remote : {
					url : "/back/api/sysRole/existRoleName", // 后台处理程序
					type : "post", // 数据发送方式
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据
						roleName : function() {
							return $("#roleName").val();
						}
					}
				}
			}
		},
		messages : {
			roleName : {
				required : icon + "请输入角色名",
				remote : icon + "角色名已存在"
			}
		}
	});
}