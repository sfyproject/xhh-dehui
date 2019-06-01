var prefix = "/back/api/account"
$(function() {
	load();
});

function load() {
	
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/page", // 服务器数据的加载地址
						cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
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
						pageList : [10, 15, 20, 25],
						pageNumber : 1, // 如果设置了分布，首页页码
						search : false, // 是否显示搜索框
						showColumns : true, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
						// "server"
						 sortable: true,  //是否启用排序
						 uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
						 showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
						 cardView: false,                    //是否显示详细视图
						 trimOnSearch: true,//设置为 true 将自动去掉搜索字符的前后空格。
						queryParams : function(params) {
							return {
								// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit : params.limit,
								offset : params.offset,
								order : params.order,
								orderName : params.sort,
								userName : $("#userName").val(),
								startDate : $("#startDate").val(),
								endDate : $("#endDate").val(),
								userPhone : $("#userPhone").val(),
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
									field : '', // 列字段名
									title : '序号', // 列标题
									visible : true,
									formatter : function(value, row, index) {
										
										return index+1;
									}
								},
								{
									visible : true,
									field : 'userName',
									title : '用户姓名',
									align : 'center'
								},
								{
									visible : true,
									field : 'userPhone',
									title : '联系方式',
									align : 'center'
								},
								{
									visible : true,
									field : 'userWallet',
									title : '账户余额',
									align : 'center'
								},
								
								{
									visible : true,
									field : 'createTime',
									title : '创建时间',
									align : 'center'
								},
								{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var e = '<a class="ml-16 '+s_recharge_h+'" href="#" mce_href="#"  onclick="user_recharge(\''+ row.userId+ '\')"<span class="iconfont">&#xe602;</span> 充值</a> ';
										
										return e;
									}
								}
								 ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	var addPage = layer.open({
		type : 2,
		title : '添加账户',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '40%', '60%' ],
		content : '/back/account/toAdd' // iframe的url
	});
	layer.full(addPage);
}
function user_recharge(id) {
	var rechage = layer.open({
		type : 2,
		title : '充值',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '40%', '60%' ],
		content : '/back/account/toRecharge?userId=' + id // iframe的url
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url :"/back/api/account/toDelete",
			type : "post",
			data : {
				'id' : id
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




function reset(){
	$("#userName").val("");
	$("#phone").val("");
	$("#startDate").val("");
    $("#endDate").val("");
    reLoad();
    load();
} 

