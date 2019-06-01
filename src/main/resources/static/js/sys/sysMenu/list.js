var prefix = "/back/api/sysMenu"
$(document).ready(function() {
	load();
});
var load = function() {
	$('#exampleTable')
			.bootstrapTreeTable(
					{
						id : 'menuId',
						parentColumn : 'parentId',
						type : "GET", // 请求数据的ajax类型
						url : prefix + '/page', // 请求数据的ajax的url
						ajaxParams : {}, // 请求数据的ajax的data属性
						expandColumn : '1',// 在哪一列上面显示展开按钮
						striped : true, // 是否各行渐变色
						bordered : true, // 是否显示边框
						expandAll : false, // 是否全部展开
						height:500,
					//	toolbar : '#exampleToolbar',
						columns : [
								{
									title : '编号',
									field : 'menuId',
									visible : false,
									align : 'center',
									valign : 'middle',
									width : '50px'
								},
								{
									title : '名称',
									align : 'center',
									field : 'name'
								},

								{
									title : '图标',
									field : 'icon',
									align : 'center',
									valign : 'middle',
									width : '50px',
									formatter : function(item, index) {
										return item.icon == null ? ''
												: '<i class="' + item.icon
														+ ' fa-lg"></i>';
									}
								},
								{
									title : '类型',
									field : 'type',
									align : 'center',
									valign : 'middle',
									width : '80px',
									formatter : function(item, index) {
										if (item.type === 0) {
											return '<span class="label label-primary">目录</span>';
										}
										if (item.type === 1) {
											return '<span class="label label-success">菜单</span>';
										}
										if (item.type === 2) {
											return '<span class="label label-warning">按钮</span>';
										}
									}
								},
								{
									title : '地址',
									align : 'center',
									width : '150px',
									field : 'url'
								},
								{
									title : '权限标识',
									align : 'center',
									width : '160px',
									field : 'perms'
								},
								{
									title : '操作',
									field : 'id',
									align : 'center',
									width : '120px',
									formatter : function(item, index) {
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ item.menuId
												+ '\')"><i class="fa fa-edit"></i></a> ';
										var p = '<a class="btn btn-primary btn-sm '+s_add_h+'" href="#" mce_href="#" title="添加下级"  onclick="add(\''
												+ item.menuId
												+ '\')"><i class="fa fa-plus"></i></a> ';
										var d = '<a class="btn  btn-danger btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
												+ item.menuId
												+ '\')"><i class="fa fa-remove"></i></a> ';
										return e + d + p;
									}
								} ]
					});
}
function reLoad() {
	load();
}
function add(pId) {
	layer.open({
		type : 2,
		title : '新增菜单',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '60%', '60%' ],
		content :  '/back/sysMenu/add?pId=' + pId // iframe的url
	});
}
function dl(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/delete",
			type : "post",
			data : {
				'id' : id
			},
			success : function(data) {
				if (data.ret == 0) {
					layer.msg("删除成功");
					reLoad();
				} else {
					layer.msg(data.msg);
				}
			}
		});
	})
}

function edit(id) {
	layer.open({
		type : 2,
		title : '菜单编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '60%', '60%' ],
		content : '/back/sysMenu/edit?id=' + id // iframe的url
	});
}

function remove(id) {
		$.ajax({
			url : prefix + "/childNode",
			type : "post",
			data : {
				'parentId' : id
			},
			success : function(data) {
				if (data.ret == 0) {
					dl(id);
				} else {
					layer.msg(data.msg);
					return false;
				}
			}
		});
}