var prefix = "/back/api/recommend"
var goodsLabel="4";
$(function() {
	load();
	goodsTypeOption();
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
								/*goodsType : "3",*/
								goodsTitle : $("#goodsTitle").val(),
								startDate : $("#startDate").val(),
								endDate : $("#endDate").val(),
								goodsType : $("#goodsType").val(),
								goodsLabel : goodsLabel
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
									align : 'center',
									title : '序号', // 列标题
									visible : true,
									formatter : function(value, row, index) {
										
										return index+1;
									}
								},
								{
									visible : true,
									field : 'goodsTitle',
									align : 'center',
									title : '商品标题'
								},
								{   
									visible : true,
									field : 'parentName',
									align : 'center',
									title : '商品标签',
								},
								{
									visible : true,
									field : 'name',
									align : 'center',
									title : '商品类型'
								},
								{
									visible : true,
									field : 'goodsInventory',
									align : 'center',
									title : '商品库存',
									formatter : function(value, row, index) {
										var lowerLimit=row.lowerLimit;
										var goodsInventory=row.goodsInventory;
										var a=goodsInventory;
										if(lowerLimit!=null&& goodsInventory<=lowerLimit){
											var a='<font style="color: red;font-size: small;">'+goodsInventory+'</font>'
										}
										return a;
									}
								},
								{
									visible : true,
									field : 'goodsSpec',
									align : 'center',
									title : '商品规格'
								},
								{
									visible : true,
									field : 'goodsSales',
									align : 'center',
									title : '商品销量'
								},
								{
									field : 'goodsImage',
									align : 'center',
									title : '商品图片',
									formatter : function(value, row, index) {
										var url = "/img/cdd.jpg";
										if(row.goodsImage != null && row.goodsImage != "" ){
											var url = row.goodsImage;
										 }
										s = '<a class = "view"  href="javascript:void(0)"><img style="width:60px;height:80px;"  src="'+url+'" /></a>';
										return s;
									},
									 events: 'operateEvents'
									         
								},
								
								{
									visible : true,
									field : 'goodsLibrary',
									align : 'center',
									title : '入库价格'
								},
								{
									visible : true,
									field : 'goodsPrice',
									align : 'center',
									title : '商品现价'
								},
								{
									visible : true,
									field : 'goodsOriginal',
									align : 'center',
									title : '商品原价'
								},
								{
									visible : true,
									field : 'goodsSecond',
									align : 'center',
									title : '商品秒杀价'
								},
								{
									visible : true,
									field : 'goodsGroup',
									align : 'center',
									title : '拼团价格' 
								},
								{
									visible : true,
									field : 'goodsDescribe',
									align : 'center',
									title : '商品描述'
								},
								{
									visible : true,
									field : 'createTime',
									align : 'center',
									title : '入库时间'
								},
								{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.goodsId
												+ '\')"><i class="fa fa-edit"></i></a> ';
										var d = '<a class="btn btn-danger btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
												+ row.goodsId
												+ '\')"><i class="fa fa-remove"></i></a> ';
										return e + d;
									}
								} ]
					});
	window.operateEvents = {
		    'click .view': function (e, value, row, index) {
		        layer.open({
		          type: 1,
		          title: false,
		          closeBtn: 0,
		          area:['auto','auto'],
		          skin: 'layui-layer-nobg', //没有背景色
		          shadeClose: true,
		          content: '<img alt="" src="'+row.goodsImage+'">' 
		        });
		    },
		};
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function edit(id) {
	var editPage = layer.open({
		type : 2,
		title : '推荐商品编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '80%', '80%' ],
		content : '/back/recommend/edit?storeGoodsId=' + id // iframe的url
	});
	layer.full(editPage);
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url :"/back/api/recommend/delete",
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


function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			var a=row['goodsId'];
			ids[i] = row['goodsId'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : '/back/api/recommend/batchRemove',
			success : function(r) {
				if (r.ret =='0') {
					layer.msg(r.msg);
					reLoad();
					load();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});
}

function reset(){
	$("#goodsTitle").val("");
	$(".selectpicker").selectpicker('val', "");
	$("#startDate").val("");
    $("#endDate").val("");
    $("#goodsLabel").val("");
    reLoad();
    load();
}


function goodsTypeOption(){
	$.ajax({
		cache : true,
		type : "post",
		url : "/back/api/sort/sortlist",
		data : {"parentId":goodsLabel},// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.ret == '0') {
				var str=""
					$.each(data.data,function(index,item){
						str+='<option value='+item.sortId+' > '+item.sortName+'</option>';
					})
					$("#goodsType").html(str);
				$('#goodsType').selectpicker('refresh');

			

			}
		}
	})
	
}


