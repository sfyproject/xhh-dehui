var idarr=["1"];
var id=2;
$(function() {
   fuzhi();
   change();
});

/*增加行*/
function tradd(){
	var str=
	"<tr>"+
		"<td><select class='selectpicker show-tick form-control' data-style='btn-info' title='请选择一项' data-live-search='true' style='color: blue;'"+
		"id='goodsLabel"+id+"'>"+
		"</select></td>"+	
		"<td><select class='selectpicker show-tick form-control'data-style='btn-info' title='请选择一项' data-live-search='true' style='color: blue;'"+
		"id='goodsType"+id+"'>"+
		"</select></td>"+	
		"<td class='table-bordered'><input class='form-control'"+"id='goodsTitle"+id+"'"+"></td>"+
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

		var goodsLabel = checkInput("goodsLabel"+(id-1),[1],"商品标签");
		if(!goodsLabel){
			$("#addbutton").hide();
			return false;
		}
		var goodsType =  checkInput("goodsType"+(id-1),[1],"商品类型");
		if(!goodsType){
			$("#addbutton").hide();
			return false;
		}
		var goodsTitle = checkInput("goodsTitle"+(id-1),[1],"商品名称");
		if(!goodsTitle){
			$("#addbutton").hide();
			return false;
		}
		var goodsSpec = checkInput("goodsSpec"+(id-1),[1],"商品规格");
		if(!goodsSpec){
			$("#addbutton").hide();
			return false;
		}
		var goodsLibrary = checkInput("goodsLibrary"+(id-1),[1,3],"入库价格");
		if(!goodsLibrary){
			$("#addbutton").hide();
			return false;
		}
		var goodsOriginal = checkInput("goodsOriginal"+(id-1),[1,3],"商品原价");
		if(!goodsOriginal){
			$("#addbutton").hide();
			return false;
		}
		var goodsPrice = checkInput("goodsPrice"+(id-1),[1,3],"商品现价");
		if(!goodsPrice){
			$("#addbutton").hide();
			return false;
		}
		var goodsInventory = checkInput("goodsInventory"+(id-1),[1,3],"商品数量");
		if(!goodsInventory){
			$("#addbutton").hide();
			return false;
		}else{
			$("#addbutton").show();
		}
		
	$('#trMore').before(str);
	$('#goodsLabel'+id).selectpicker('refresh');
	$('#goodsType'+id).selectpicker('refresh');
	idarr.push(id);
	goodsLabelOption(id);
	change();
	id+=1;
};

function trdelelt(c,a){
	 a.parentNode.parentNode.remove();
	 for(var i=0;i<idarr.length;i++){ //获取表格数据,因为列数已经固定，所以直接获取了，其实可以通过循环遍历来的到
       if(idarr[i]==c){
       	idarr.splice(i,1); 
       }
    }
};

function makeJson(){
  	var arr=[];  //json对象容器
    var obj={  "goodsType":"",
    		   "goodsLabel":"",
    		   "goodsTitle":"",     
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
              obj.goodsType=$("#goodsType"+idd).val();
              obj.goodsLabel=$("#goodsLabel"+idd).val();
              obj.goodsTitle=$("#goodsTitle"+idd).val();
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
		 url: '/back/api/goodsManagement/save', 
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
		goodsLabelOption(id);
		
	}
}
function change() {
	for(var i in idarr){
	var id=idarr[i]
	$("#goodsLabel"+id).change(function(){
		goodsTypeOption(id);
	})
	}
}

function goodsLabelOption(id){
	
	$.ajax({
		cache : true,
		type : "post",
		url : "/back/api/sort/sortlist",
		data :{"grade":'1'}, 
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
					$("#goodsLabel"+id).html(str);
				$('#goodsLabel'+id).selectpicker('refresh');
			}
		}
	})
}
function goodsTypeOption(id){
	var goodsLabel = $("#goodsLabel"+id).val();
	$.ajax({
		cache : true,
		type : "post",
		url : "/back/api/sort/sortlist",
		data : 	{"parentId":goodsLabel},
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
					$("#goodsType"+id).html(str);
				$('#goodsType'+id).selectpicker('refresh');
			}
		}
	})
}

/*function checkInput(id,status,msg){
	var str=$("#"+id).val();
	var checkNum = /(^[+]{0,1}(\d+)$)/;
	var checkDoubNum = /(^((0{1}\.\d+)|([1-9]\d*\.{1}\d+)|([1-9]+\d*)|0)$)/;
	$.each(status, function(i,val){
		if(val == 1){
			if(str==null || str == ''){
				layer.tips(msg+"不能为空", $("#"+id), {
				        tips: [2, '#000000'],
				        time: 2000
				    });
				  $("#"+id).focus();
				  return false
			}
		}else if(val ==2){
			if(str != null && str != ''){
				if(!checkNum.test(str)){
					layer.tips(msg+"必须为整数", $("#"+id), {
					        tips: [2, '#000000'],
					        time: 2000
					    });
					  $("#"+id).focus();
					  return false
				}
			}
		}else if(val == 3){
			if(str != null && str != ''){
				if(!checkDoubNum.test(str)){
					layer.tips(msg+"必须为数字", $("#"+id), {
					        tips: [2, '#000000'],
					        time: 2000
					    });
					  $("#"+id).focus();
					  return false
				}
			}
		}
	});
	
}*/


function checkInput(id,status,msg){
	var str=$("#"+id).val();
	var checkNum = /(^[+]{0,1}(\d+)$)/;
	var checkDoubNum = /(^((0{1}\.\d+)|([1-9]\d*\.{1}\d+)|([1-9]+\d*)|0)$)/;
	var retVal = true;
	$.each(status, function(i,item){
		if(item ==1){
			if(str==null || str == ''){
				layer.tips(msg+"不能为空", $("#"+id), {
				        tips: [2, '#000000'],
				        time: 2000
				    });
				  $("#"+id).focus();
				  retVal = false;
				  return retVal;
			}
		}else if(item ==2){
			if(str != null && str != ''){
				if(!checkNum.test(str)){
					layer.tips(msg+"必须为整数", $("#"+id), {
					        tips: [2, '#000000'],
					        time: 2000
					    });
					  $("#"+id).focus();
					  retVal = false;
					  return retVal;
				}
			}
		}else if(item == 3){
			if(str != null && str != ''){
				if(!checkDoubNum.test(str)){
					layer.tips(msg+"必须为数字", $("#"+id), {
					        tips: [2, '#000000'],
					        time: 2000
					    });
					  $("#"+id).focus();
					  retVal = false;
					  return retVal;
				}
			}
		}
	});
	  return retVal;
}





