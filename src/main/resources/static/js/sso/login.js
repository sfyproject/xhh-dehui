var url_login = '/back/api/sso/login.json';

function login() {
	
	var username = $('#username').val(); 
	var password = $('#password').val(); 
	
	axios.post(url_login, {
		username: username,
		password: password
  	})
	.then(function(resp) {
		var json = resp.data;
		if (json.ret == '0') {
			window.location.href = json.data;
		} else {
			$('#error_container').text(json.msg);
			$('#error_container').show();
		}
	})
	.catch(function(error) {
		console.log(error);
	});
	
}

$(document).ready(function(e) {
	$('#error_container').hide();
});