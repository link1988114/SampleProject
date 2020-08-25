$(function() {
    $("#agreement").click(function(e) {
        var obj = $("#agreement input");
        obj[0].checked = !obj[0].checked;
        if (obj[0].checked == true) {
            activeBtn("#btn_apply", "btn-primary");
        } else {
            disableBtn("#btn_apply", "btn-primary");
        }
    });

    $("#btn_apply").click(function(e) {
        openPop("pop_confirm", "提交", "确认提交申请?");
        disableBtn("#btn_apply", "btn-primary");
    });

    $("#btn_sms").click(function(e) {
        var mobile = $("#cstm_mobile").val();
        if (!isMobile(mobile)) {
            openPop("pop_alert", "短信", "请输入正确的手机号码");
            return null;
        }
        disableBtn("#btn_sms", "btn-primary");
        sendSms();
        countingDown();
    });

    $("#btn_warning_close").click(function(e) {
        $("#popWarning").hide();
    });

    
    
    
    $.get("../wx/checkrevise.do",function(re){
    	var json = new Function("return"+re)();
    	if(json.result=="success"){
    		var data = new Function("return"+json.msg)();
    		$("#cstm_name").val(data.cstm_name);
    		$("#cstm_mobile").val(data.cstm_mobile);
    		$("#cstm_company").val(data.cstm_company);
    		$("#identify_no").val(data.identify_no);
    	}
    });

});





function showWarning() {
    $("#popWarning").show();
}

function sendApply() {
	
	openPop("pop_loading","","正在提交");
	
    var sendData = {
        cstm_name: $("#cstm_name").val(),
        identify_no: $("#identify_no").val(),
        cstm_company: $("#cstm_company").val(),
        cstm_mobile: $("#cstm_mobile").val(),
        valid_code: $("#valid_code").val()
    };

    if (checkInput(sendData)) {
        //console.log("sending");
        var url = "../wx/confirm.do";
        $.ajax({
            url: url,
            type: "post",
            cache: false,
            data: sendData,
            success: function(re) {
            	closePop("pop_loading");
            	
                var json = new Function("return" + re)();
                if(json.result=="success"){
                	window.location.href = "applied.html";
                }
                else if(json.result == "redirect"){
                	window.location.href = json.msg;
                }
                else{
                	openPop("pop_alert", "提示", json.msg);
                }
                
                activeBtn("#btn_apply", "btn-primary");
            },
            error: function(e) {
            	closePop("pop_loading");
                openPop("pop_alert", "错误", "网络出现错误,请稍后再试");
                activeBtn("#btn_apply", "btn-primary");
            }
        });
    }
    closePop("pop_confirm");
}

function sendSms() {
    var url = "../sms/send.do";
    var data = {
        phone: $("#cstm_mobile").val()
    };
    $.post(url, data, function(re) {
        if (re == "error") {
            openPop("pop_alert", "错误", "请从微信登陆");
        }
    });
}

function countingDown() {
    var total = 30;
    var timer = setInterval(function() {
        total--;
        if (total <= 0) {
            $("#btn_sms").html("获取验证码");
            activeBtn("#btn_sms", "btn-primary");
            clearInterval(timer);
        } else {
            $("#btn_sms").html("重新发送(" + total + ")");
        }
    }, 1000);
}


function checkInput(json) {
    for (var key in json) {
        var value = json[key] + "";
        if (key == "cstm_name") {
            if (value == "") {
                openPop("pop_alert", "错误", "请输入姓名");
                return false;
            }
        } else if (key == "identify_no") {
            if (!isIdCardNo(value)) {
                openPop("pop_alert", "错误", "请输入正确的身份证号");
                return false;
            }
        } else if (key == "cstm_company") {
            if (value == "") {
                openPop("pop_alert", "错误", "请输入工作单位");
                return false;
            }
        } else if (key == "cstm_mobile") {
            if (!isMobile(value)) {
                openPop("pop_alert", "错误", "请输入正确的手机号码");
                return false;
            }
        } else if (key == "valid_code") {
            if (value == "") {
                openPop("pop_alert", "错误", "请输入验证码");
                return false;
            }
        }

    }
    return true;
}