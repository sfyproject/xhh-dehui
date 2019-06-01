var prefix = "/back/api/order"
var channelId;
$(function() {
	load();
});
function load() {
	$('#exampleTable')
			.bootstrapTable(
				{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/orderPage", // 服务器数据的加载地址
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
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						userPhone : $('#userPhone').val(),
						userName : $('#userName').val(),
						orderName : $('#orderName').val(),
						orderPhone : $('#orderPhone').val(),
						orderNo : $('#orderNo').val(),
						orderStatus : $('#orderStatus').val(),
						startDate : $('#startDate').val(),
						endDate : $('#endDate').val()
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
							align : 'center',
							title : '序号', // 列标题
							visible : true,
							formatter : function(value, row, index) {
								return index+1;
							}
						},
						{
							visible : true,
							align : 'center',
							field : 'orderNo',
							title : '订单编号'
						},
						{
							visible : true,
							align : 'center',
							field : 'userName',
							title : '下单人'
						},
						{
							visible : true,
							align : 'center',
							field : 'userPhone',
							title : '下单人电话'
						},
						{
							visible : true,
							align : 'center',
							field : 'orderPrice',
							title : '订单金额'
						},
						{
							visible : true,
							field : 'orderStatus',
							align : 'center',
							title : '订单状态',
							formatter: function(value, row, index) {
								if(row.orderStatus == 0){
									return '已退款';
								}
								if(row.orderStatus == 1){
									return '未支付';
								}
								if(row.orderStatus == 2){
									return '已支付';
								}
								if(row.orderStatus == 3){
									return '已发货';
								}
								if(row.orderStatus == 3){
									return '已完成';
								}
					        }
						},
						{
							visible : true,
							align : 'center',
							field : 'orderName',
							title : '收货人'
						},
						{
							visible : true,
							align : 'center',
							field : 'orderPhone',
							title : '收货电话'
						},
						{
							visible : true,
							align : 'center',
							field : 'orderGroup',
							title : '团购' ,
							formatter: function(value, row, index) {
								if(row.orderGroup == 0){
									return '非团购';
								}
								if(row.orderGroup == 1){
									return '团购';
								}
					        }
						},
						{
							visible : true,
							align : 'center',
							field : 'groupStatus',
							title : '团购状态',
							formatter: function(value, row, index) {
								if(row.groupStatus == 0){
									return '拼团进行中';
								}
								if(row.groupStatus == 1){
									return '拼团成功';
								}
					        }
						},
						{
							visible : true,
							align : 'center',
							field : 'orderAddress',
							title : '收货地址'
						},
						{
							visible : true,
							align : 'center',
							field : 'orderDelivery',
							title : '配送方式',
							formatter: function(value, row, index) {
								if(row.orderDelivery == 0){
									return '自提';
								}
								if(row.orderDelivery == 1){
									return '配送';
								}
					        }
						},
						{
							visible : true,
							align : 'center',
							field : 'orderFare',
							title : '运费'
						},
						{
							visible : true,
							align : 'center',
							field : 'updateTime',
							title : '更新时间'
						},
						{
							visible : true,
							align : 'center',
							field : 'createTime',
							title : '创建时间'
						}
						/*{
							title : '操作',
							field : 'id',
							align : 'center',
							formatter : function(value, row, index) {
								var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
										+ row.orderNo
										+ '\')"><i class="fa fa-edit"></i></a> ';
								var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
										+ row.orderNo
										+ '\')"><i class="fa fa-remove"></i></a> ';
								return e + d;
							}
						}*/ ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}


/*function edit(orderNo) {
	var editPage = layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '80%', '80%' ],
		content : '/back/notice/edit?noticeId=' + noticeId // iframe的url
	});
	layer.full(editPage);
}*/





