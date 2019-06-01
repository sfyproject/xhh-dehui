function update() {
	var obj= $("#noticeImage").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/back/api/notice/edit",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.ret == '0') {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
				parent.load();

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

};
			
function returnList(){
	 var index = parent.layer.getFrameIndex(window.name);
	 parent.layer.close(index);
}
