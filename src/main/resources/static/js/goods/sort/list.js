var prefix = "/back/api/sort"
var arr=["拼团商品","秒杀商品","推荐商品","下架商品","普通商品"];
$(document).ready(function() {
	load();
});
var load = function() {
	$('#exampleTable')
			.bootstrapTreeTable(
					{
						id : 'sortId',
						code : 'sortId',
						parentColumn : 'parentId',
						type : "GET", // 请求数据的ajax类型
						url : prefix + '/page', // 请求数据的ajax的url
						ajaxParams : {}, // 请求数据的ajax的data属性
						expandColumn : '2',// 在哪一列上面显示展开按钮
						striped : true, // 是否各行渐变色
						bordered : true, // 是否显示边框
						expandAll : false, // 是否全部展开
						height:500,
					//	toolbar : '#exampleToolbar',
						columns : [
								{
									title : '序号',
									field : 'number',
									align : 'center',
									valign : 'middle',
									width : '50px'
								},
								{
									title : '编号',
									field : 'sortId',
									align : 'center',
									valign : 'middle',
									width : '300px'
								},
								{
									title : '名称',
									align : 'center',
									field : 'sortName'
								},
								{
									title : '类型',
									field : 'grade',
									align : 'center',
									valign : 'middle',
									width : '100px',
									formatter : function(item, index) {
										if (item.grade === '1') {
											return '<span class="label label-success">商品标签</span>';
										}
										if (item.grade === '2') {
											return '<span class="label label-warning">商品类型</span>';
										}
									}
								},
								{
									title : '操作',
									field : 'sortId',
									align : 'center',
									width : '120px',
									formatter : function(item, index) {
										var p = '<a class="btn btn-primary btn-sm '+s_add_h+'" href="#" mce_href="#" title="添加下级" onclick="add(\''
										+ item.sortId
										+ '\')"><i class="fa fa-plus"></i></a> ';
										if(item.grade == '2'){
											p ='';
										}
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ item.sortId
												+ '\')"><i class="fa fa-edit"></i></a> ';
										if(item.sortName=="下架商品"){
											e='';
										}
										
										var d = '<a class="btn btn-danger btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
												+ item.sortId
												+ '\')"><i class="fa fa-remove"></i></a> ';
										
										for(var i in arr){
											if(arr[i]==item.sortName){
												d='';
											}
										}
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
		title : '新增分类',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '50%', '60%' ],
		content :  '/back/sort/add?sortId=' + pId // iframe的url
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
				'sortId' : id
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
		title : '分类编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '50%', '60%' ],
		content : '/back/sort/edit?id=' + id // iframe的url
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