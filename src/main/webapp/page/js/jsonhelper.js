function getJsonlength(json) {
    var count = 0;
    for (itemname in json) {
        count++;
    }
    return count;
}

function isEmptyJson(json) {
    var len = getJsonlength(json);
    if (len == 0) {
        return true;
    } else {
        return false;
    }
}