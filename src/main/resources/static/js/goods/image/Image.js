$().ready(function() {
	uploadImg();
});

function gimgs() {
	var arr=[];
	$.ajax({
		cache : true,
		type : "POST",
		url : "/back/api/image/showImage",
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.ret == '0') {
				arr= data.data;
			} else {
				parent.layer.alert(data.msg);
			}

		}
	});
	return arr;

};
var jsonarray =[];

function uploadImg() {	
	
	var arr=gimgs();
	var imgurls=[];
	var con= [];
	for(var i in arr){
		var img= new Object();
		img.caption=arr[i].imageLink.split("/")[5];
		img.width="120px";
		img.url="/back/api/image/deleteImage";
		img.key=arr[i].imageId;
		img.extra={
				"id" :img.key,
				"name": arr[i].imageLink
		};
		con.push(img);
		imgurls.push(arr[i].imageLink);
		
	}
	$(".myfile").fileinput('destroy');  
	$(".myfile").fileinput({
	    //上传的地址
	    uploadUrl:"/back/api/image/uploadImage",
	    uploadAsync : true, //默认异步上传
	    showUpload : true, //是否显示上传按钮,跟随文本框的那个
	    showRemove : false, //显示移除按钮,跟随文本框的那个
	    showCaption : true,//是否显示标题,就是那个文本框
	    showPreview : true, //是否显示预览,不写默认为true
	    dropZoneEnabled : false,//是否显示拖拽区域，默认不写为true，但是会占用很大区域
	    uploadClass: "btn btn-success",//设置上传按钮样式
	    removeClass : "btn btn-success",
	    //minImageWidth: 50, //图片的最小宽度
	    //minImageHeight: 50,//图片的最小高度
	    //maxImageWidth: 1000,//图片的最大宽度
	    //maxImageHeight: 1000,//图片的最大高度
	    maxFileSize: 5120,//单位为kb，如果为0表示不限制文件大小
	    //minFileCount: 0,
	    maxFileCount : 5, //表示允许同时上传的最大文件个数
	    overwriteInitial: false, //不覆盖已存在的图片
        //下面几个就是初始化预览图片的配置    
	    previewSettings: {
            image: {width: "70%", height: "90%"},
        },
         initialPreviewAsData: true,
         initialPreviewFileType: 'image',
         initialPreview:imgurls, //要显示的图片的路径
         initialPreviewConfig:con,
	    enctype : 'multipart/form-data',
	    validateInitialCount : true,
	    previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
	    msgFilesTooMany : "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
	    allowedFileTypes : [ 'image' ],//配置允许文件上传的类型
	    allowedPreviewTypes : [ 'image' ],//配置所有的被预览文件类型
	    allowedPreviewMimeTypes : [ 'jpg', 'png', 'gif' ],//控制被预览的所有mime类型
	    language : 'zh'
	})
	$(".myfile").fileinput('enable');
	
};
	//异步上传返回结果处理
	$('.myfile').on('fileerror', function(event, data, msg) {
	    console.log("fileerror");
	    console.log(data);
	});
	//异步上传返回结果处理
	$(".myfile").on("fileuploaded", function(event, data, previewId, index) {
	        uploadImg(); 
	});	
	//同步上传错误处理
	$('.myfile').on('filebatchuploaderror', function(event, data, msg) {
	    console.log("filebatchuploaderror");
	    console.log(data);
	});
	
	//同步上传返回结果处理
	$(".myfile").on("filebatchuploadsuccess",
	        function(event, data, previewId, index) {
	            console.log("filebatchuploadsuccess");
	            console.log(data);
	        });
	
	//上传前
	$('.myfile').on('filepreupload', function(event, data, previewId, index) {
	    console.log("filepreupload");
	});
	
 
	$('.myfile').on('filedeleted', function(event, key) {  
		gimgs(); 
	});
	