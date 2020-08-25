var clickflag = false;


$(window).keydown(function(e){
    if(e.keyCode==13){
        if($("#username").val()==""){
            $("#username").focus();
        }
        else{
            if($("#password").val()==""){
                $("#password").focus();
            }
            else{
                confirm();
            }
        }
    }
});


function confirm() {
    disableBtn();

    var url = "../user/login.do";
    var data = {
        username: $("#username").val(),
        password: $("#password").val()
    };
    if (checkinput(data) && clickflag == false) {
        clickflag = true;
        data.password = hex_md5(data.password);
        $.post(url, data, function (re) {
            var json = new Function("return" + re)();
            if (json.result == "success") {
                window.location.href = "main.html";
            }
            else {
                alert(json.msg);
            }
            enableBtn();
            clickflag = false;
        });
    }
}


function enableBtn() {
    $("#btn").html("确定");
    $("#btn").removeAttr("disabled");
    $("#username").focus();
}

function disableBtn() {
    $("#btn").html("正在处理...");
    $("#btn").attr("disabled", "disabled");
}



function checkinput(data) {
    for (keyname in data) {
        var value = data[keyname];
        if (keyname == "username" && value == "") {
            alert("请输入用户名");
            $("#username").focus();
            enableBtn();
            return false;
        }
        if (keyname == "password" && value == "") {
            alert("请输入密码");
            $("#password").focus();
            enableBtn();
            return false;
        }
    }
    return true;
}