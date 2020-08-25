$(function () {
    $("#btn_apply").click(function (e) {
        disableBtn("#btn_apply", "btn-primary");
        sendApply();
    });

    $("#btn_warning_close").click(function (e) {
        $("#popWarning").hide();
    });
});

function showWarning() {
    $("#popWarning").show();
}

function sendApply() {
    var sendData = {
        promote_code: $("#promote_code").val()
    };

    if (checkInput(sendData)) {
        //alert("sending");
        var errorFlag = false;
        var url = "../wx/getqr.do?promote_code=" + sendData.promote_code;
        $.get(url, function (re) {
            if (re == "error") {
                openPop("pop_alert", "错误", "请输入正确的人力资源编码");
            }
            else {
                //$("#img_qr").attr("src", "../wx/getqr.do?promote_code=" + sendData.promote_code);
                $("#img_qr").attr("src", "data: image / png; base64," + re);
                showWarning();
            }
        });
    }

    activeBtn("#btn_apply", "btn-primary");
}

function checkInput(json) {
    for (var key in json) {
        var value = json[key] + "";
        if (key == "promote_code") {
            if (value == "") {
                openPop("pop_alert", "错误", "请输入人力资源编码");
                return false;
            }
        }
    }
    return true;
}