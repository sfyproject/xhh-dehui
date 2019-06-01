var prefix = "/back/api/notice"
var channelId;
$(function() {
	load();
});
function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/page", // 服务器数据的加载地址
				showRefresh : true,
				showToggle : true,
				showColumns : true,
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				search : false, // 是否显示搜索框
				showColumns : true, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
					// username:$('#searchName').val()
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : [
						{
							checkbox : true
						},
							{
							field : '', // 列字段名
							title : '序号', // 列标题
							visible : true,
							align : 'center',
							formatter : function(value, row, index) {
								return index+1;
							}
						},
						{
							visible : true,
							field : 'noticeTitle',
							title : '公告标题',
							align : 'center'
						},
						{
							visible : true,
							field : 'noticeContent',
							title : '公告内容',
							align : 'center'
						},
						{
							visible : true,
							field : 'noticeEnding',
							title : '公告结尾',
							align : 'center'
						},
						{
							field : 'noticeImage',
							title : '公告图片',
							formatter : function(value, row, index) {
							if(row.noticeImage!=null){
								var url = row.noticeImage;
								var s = '<a class = "view"  href="javascript:void(0)"><img style="width:60px;height:80px;"  src="'+url+'" /></a>';
								return s;
							  }
							}
						},
						{
							visible : true,
							field : 'noticeStatus',
							title : '状态',
							align : 'center',
							formatter: function(value, row, index) {
								if(row.noticeStatus == 0){
									return '关';
								}
								if(row.noticeStatus == 1){
									return '开';
								}
					        }
						},
						{
							visible : true,
							field : 'createTime',
							title : '创建时间',
							align : 'center',
						},
						
						{
							title : '操作',
							field : 'id',
							align : 'center',
							formatter : function(value, row, index) {
								var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
										+ row.noticeId
										+ '\')"><i class="fa fa-edit"></i></a> ';
								var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
										+ row.noticeId
										+ '\')"><i class="fa fa-remove"></i></a> ';
								return e + d;
							}
						} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	layer.open({
		type : 2,
		title : '添加公告',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '80%', '80%' ],
		content : '/back/notice/add' // iframe的url
	});
}

function edit(noticeId) {
	var editPage = layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '80%', '80%' ],
		content : '/back/notice/edit?noticeId=' + noticeId // iframe的url
	});
	layer.full(editPage);
}

function remove(noticeId) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url :"/back/api/notice/del",
			type : "post",
			data : {
				'noticeId' : noticeId
			},
			success : function(r) {
				if (r.ret == '0') {
					layer.msg(r.msg);
					reLoad();
					load();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}



