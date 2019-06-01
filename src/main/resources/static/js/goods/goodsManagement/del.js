var idarr=["1"];
var id=2;
$(function() {
   fuzhi();
   change();
});

/*增加行*/
function tradd(){
	var str=
	"<tr class='btr'>"+
		"<td><select class='selectpicker show-tick form-control' data-style='btn-info' title='请选择一项' data-live-search='true' style='color: blue;'"+
		"id='goodsId"+id+"'>"+
		"</select></td>"+	
		"<td ><input class='form-control'"+"id='goodsLabel"+id+"'"+"></td>"+
		"<td ><input class='form-control'"+"id='goodsType"+id+"'"+"></td>"+
		"<td ><input class='form-control'"+"id='goodsSpec"+id+"'"+"></td>"+
		"<td ><input class='form-control'"+"id='goodsLibrary"+id+"'"+"></td>"+
		"<td ><input class='form-control'"+"id='goodsOriginal"+id+"'"+"></td>"+
		"<td ><input class='form-control'"+"id='goodsPrice"+id+"'"+"></td>"+
		"<td ><input class='form-control'"+"id='goodsSecond"+id+"'"+"></td>"+
		"<td ><input class='form-control'"+"id='goodsGroup"+id+"'"+"></td>"+
		"<td ><input class='form-control'"+"id='goodsInventory"+id+"'"+"></td>"+
		"<td>"+
		"<button type='button' class='btn btn-warning' onclick='trdelelt("+id+",this)'>"+
			"<i class='fa fa-trash' aria-hidden='true'>删除</i>"+"</button>"+
		"</td>"+ 
	"<tr>"
	$('#trMore').before(str);
	$('#goodsTitle'+id).selectpicker('refresh');
	idarr.push(id);
	goodsTitleOption(id);
	change();
	id+=1;
};

function trdelelt(c,a){
	var f=$(a).parent();
	var f1=$(f).parent();
	 f1.remove();
	 for(var i=0;i<idarr.length;i++){ //获取表格数据,因为列数已经固定，所以直接获取了，其实可以通过循环遍历来的到
       if(idarr[i]==c){
       	idarr.splice(i,1); 
       }
    }
};

function makeJson(){
	debugger;
  	var arr=[];  //json对象容器
    var obj={  
    			"goodsId":"",
    			"goodsType":"",
    		    "goodsLabel":"",
    		    "goodsSpec":"",
    		    "goodsLibrary":"",
    		    "goodsOriginal":"",
    		    "goodsPrice":"",
    		    "goodsSecond":"",
    		    "goodsGroup":"",
    		    "goodsInventory":"",
	           };
      for(var i=0;i<idarr.length;i++){ //获取表格数据,因为列数已经固定，所以直接获取了，其实可以通过循环遍历来的到
          var obj = new Object();//这里一定要new新的对象，要不然保存的都是一样的数据；都是最后一行的数据
              var idd=idarr[i];
              obj.goodsId=$("#goodsId"+idd).val();
              obj.goodsType=$("#goodsType"+idd).val();
              obj.goodsLabel=$("#goodsLabel"+idd).val();
        	  obj.goodsSpec=$("#goodsSpec"+idd).val();
        	  obj.goodsLibrary=$("#goodsLibrary"+idd).val();
        	  obj.goodsOriginal=$("#goodsOriginal"+idd).val();
        	  obj.goodsPrice=$("#goodsPrice"+idd).val();
        	  obj.goodsSecond=$("#goodsSecond"+idd).val();
        	  obj.goodsGroup=$("#goodsGroup"+idd).val();
        	  obj.goodsInventory=$("#goodsInventory"+idd).val();
              arr.push(obj);//向JSON数组添加JSON对象的方法；很关键
      }
        //格式化数据
       var jsonobj=JSON.stringify(arr);//这一行很关键
       //将数据赋值通过按钮提交到后台
       $.ajax({
		type: 'post',
		 url: '/back/api/goodsManagement/del', 
		data: {'goodsArr':jsonobj},
		success: function(data){
			if(data.ret =='0'){
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
				parent.load();
            }else {
            	layer.msg("提交失败");
            }
		},
        error: function(XmlHttpRequest, textStatus, errorThrown){
		}
	})
  }

function fuzhi(){
	for(var i in idarr){
		var id=idarr[i]
		goodsTitleOption(id);
	}
}
function change() {
	for(var i in idarr){
	var id=idarr[i];
		$("#goodsId"+id).change(function(){
			var goodsId=$("#goodsId"+id).val();
			var flag=true;
			for(var o in idarr){
				var j = idarr[o-1];
				var goodsIdt = $("#goodsId"+j).val();
				if(goodsId == goodsIdt){
					$("#goodsInventory"+j).val( $("#goodsInventory"+j).val()*1+1);
					var obj=$("#goodsInventory"+id);
					trdelelt(idarr[i],obj);
					flag=false;
				}
			}
			if(flag){
				goodsInfoByGoodsId(id);
				flag=true;
			}
		})
	}
}

function goodsTitleOption(id){
	$.ajax({
		cache : true,
		type : "post",
		url : "/back/api/goodsManagement/selectAllGoods",
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.ret == '0') {
				var str=""
					$.each(data.data,function(index,item){
						str+='<option value='+item.goodsId+' > '+item.goodsTitle+'</option>';
					})
					$("#goodsId"+id).html(str);
				$('#goodsId'+id).selectpicker('refresh');
			}
		}
	})
}

function goodsInfoByGoodsId(id){
	var goodsId = $("#goodsId"+id).val();
	$.ajax({
		cache : true,
		type : "post",
		data :{"goodsId" : goodsId},
		url : "/back/api/goodsManagement/selectByGoodsId",
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.ret == '0') {
				$("#goodsSpec"+id).val(data.data.goodsSpec);
				$("#goodsLabel"+id).val(data.data.parentName);
				$("#goodsType"+id).val(data.data.name);
				$("#goodsLibrary"+id).val(data.data.goodsLibrary);
				$("#goodsOriginal"+id).val(data.data.goodsOriginal);
				$("#goodsPrice"+id).val(data.data.goodsPrice);
				$("#goodsSecond"+id).val(data.data.goodsSecond);
				$("#goodsGroup"+id).val(data.data.goodsGroup);
				$("#goodsInventory"+id).val("1");
			}
		}
	})
}
/*function goodsInventoryByGoodsId(id){
	var goodsId = $("#goodsId"+id).val();
	$.ajax({
		cache : true,
		type : "post",
		data :{"goodsId" : goodsId},
		url : "/back/api/goodsManagement/selectByGoodsId",
		async : false,
		error : function(request) {
			parent.layer.alert("此商品不存在");
		},
		success : function(data) {
			if (data.ret == '0') {
				alert(data.data.goodsInventory);
				return data.data.goodsInventory;
			}
		}
	})
}*/

/*function checkInput(){
	for(var i in idarr){
		var id=idarr[i]
		$("#goodsInventory"+id).change(function(){
			var goodsId=$("#goodsId"+id).val();
			var flag=true;
			var goodsInventory = $("#goodsInventory"+id).val;//获取出库商品的数量
			var goodsInventory1 = goodsInventoryByGoodsId(id);
			if(goodsInventory1>goodsInventory){
				
			}
		})
	}
}*/



