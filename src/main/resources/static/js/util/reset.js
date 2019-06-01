function reset() {
	
	$("#username").val("");
	$("#phone").val("");
	$("#order_no").val("");
	$("#status").val("");
	$("#express_no").val("");
	$("#express_ono").val("");
	$("#goods_title").val("");
	$("#goods_price").val("");
	$("#goods_type").val("");
	$("#start_date").val("");
	$("#end_date").val("");
	
	show();
	
}

function uploadImg(event) {
	
	if (event.target.files.length <= 0) {
		return;
	}
	
    formdata = new FormData();
    formdata.append('file', event.target.files[0]);
    
    axios({
        url: '/back/api/img/uploadImg.json',
        method: 'POST',
        data: formdata,
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    })
    .then((result)=> {
    	var json = result.data;
    	if(json.ret == "0") {
    		$('#path').attr('src', json.data);
    		$('#path').show();
    	}
    })
    
}