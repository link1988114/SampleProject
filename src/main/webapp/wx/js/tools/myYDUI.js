function toggleBtn(selector, normalClass, disableClass, OnOff) {
    if (OnOff == "on") {
        $(selector).removeClass(disableClass);
        $(selector).addClass(normalClass);
    }
    else {
        $(selector).removeClass(normalClass);
        $(selector).addClass(disableClass);
    }
}

function openWarning(id, title, content) {
    try {
        var objBody = $("#" + id + " .confirm-bd");
        objBody[0].innerHTML = content;
    }
    catch(e){
    }
    try{
        var objTitle = $("#" + id + " .confirm-title");
        objTitle[0].innerHTML = title;
    }
    catch(e){
    }
    try{
        var objTxt = $("#" + id + " .loading-txt");
        objTxt[0].innerHTML = content;
    }
    catch(e){
    }
    $("#" + id).show();
}

function closeWarning(id) {
    $("#" + id).hide();
}