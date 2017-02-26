$(function(){
	$("body").keydown(function() {
	    if (event.keyCode == "13") {
	    	login();
	    	return false; 
	    }
	});
	$("#loginBtn").click(function(){
		login();
	});
    loginInfo()
})

function login() {
	var userName = $("#loginForm [name='name']").val()
	var userPass = $("#loginForm [name='password']").val()
	var d = JSON.stringify({
		"Account" : userName,
		"Password" : userPass
	})
	$.ajax({
		type: "POST",
		url: "/login/start",
		dataType: "json",
        cache: false,
		data: d,
		contentType: "application/json,charset=utf-8",
		success: function(r){
			console.info(r)
			if(r.result.FinalResult == "input is null") {
				$("#loginSub").click();
			}else if(r.result.FinalResult == "is null") {
				alert("用户名或密码错误！！！")
			}else if(r != null  && r != ""){
                $.cookie("user_token",r.result.FinalResult.User_Token)
				$.cookie("user_name",r.result.FinalResult.UserName)
                $.cookie("user_auth",r.result.FinalResult.UserAuth)
                $.cookie("token",r.result.FinalResult.Token)
                $.cookie("company_name_ch",r.result.FinalResult.CompanyNameCh)
                $.cookie("company_name_en",r.result.FinalResult.CompanyNameEn)
				location = "index"
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			console.info("Error")
		}
	});
}

function logout() {
	$.cookie("user_token", "", {"path": "/", "expires": -1 });
    $.cookie("user_name", "", {"path": "/", "expires": -1 });
    $.cookie("user_auth", "", {"path": "/", "expires": -1 });
    $.cookie("token", "", {"path": "/", "expires": -1 });
    $.cookie("company_name_ch", "", {"path": "/", "expires": -1 });
    $.cookie("company_name_en", "", {"path": "/", "expires": -1 });
	location = "login"
}

function loginInfo() {
	if($.cookie("user_name") == undefined) {
        $("#Name").text("未知")
        $("#company").text("未知")
	}else {
		$("#Name").text($.cookie("user_name"))
        $("#company").text($.cookie("company_name_ch"))
	}
}
