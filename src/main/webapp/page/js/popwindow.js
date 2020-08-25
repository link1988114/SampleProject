$(function(){
	//openPop(popWinID, maskID, popWidth, popHeight);
	//closePop(popWinID, maskID);

    $(".popclose").click(function (e) {
        closePop(popid, maskid);
    });

});

var popid = "";
var maskid = "";

function openPop(popWinID, maskID, popWidth, popHeight) {
    if (popWidth != null && popHeight != null) {
        var obj = document.getElementById(popWinID);
 
        if (popWidth.replace("%", "") == popWidth) {
            obj.style.width = popWidth + "px";
        }
        else {
            obj.style.width = popWidth;
        }
        if (popHeight.replace("%", "") == popHeight) {
            obj.style.height = popHeight + "px";
        }
        else {
            obj.style.height = popHeight;
        }
        
    }
    $("#" + maskID).show();
    $("#" + popWinID).show();

    popid = popWinID;
    maskid = maskID;
}

function closePop(popWinID, maskID) {
    $("#" + popWinID).hide();
    $("#" + maskID).hide();
}

function setPrevPopID(prevWin, prevMask) {
    popid = prevWin;
    maskid = prevMask;
}