$(function() {
    $("#btn_apply").click(function(e) {
        openPop("pop_confirm", "提交", "确认提交申请?");
        disableBtn("#btn_apply", "btn-primary");
    });
});

function gotoPage(url){
	window.location.href = url;
}


function sendApply() {
	
	openPop("pop_loading","","正在提交");
	
    var sendData = {
    	cstm_pw:$("#cstm_pw").val()
    };

    if (checkInput(sendData)) {
        //console.log("sending");
        var url = "../wx/revise.do";
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

function checkInput(json) {
    for (var key in json) {
        var value = json[key] + "";
        if (key == "cstm_pw") {
            if (value == "") {
                openPop("pop_alert", "错误", "请输入查询密码");
                return false;
            }
        }
    }
    return true;
}