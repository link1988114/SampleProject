function NumAlphabet(obj) {
    var value = obj.value;
    value = value.replace(/[^a-z0-9\.\-_]/gi, "");
    obj.value = value;
}

function NumCapAlphabet(obj) {
    var value = obj.value;
    value = value.replace(/[^A-Z0-9\.\-_]/gi, "");
    obj.value = value;
}


function beizhuTxt(obj) {
    var value = obj.value;
    value = value.replace(/[^\,\-_\.\a-\z\A-\Z0-9\u4E00-\u9FA5]/gi, "");
    obj.value = value;
}

function Digits(obj,length) {
    var value = obj.value;
    value.substr(0, length);
    value = value.replace(/\D/gi, "");
    obj.value = value;
}

function Digits(obj) {
    var value = obj.value;
    value = value.replace(/\D/gi, "");
    obj.value = value;
}

function DigitsDot(obj) {
    var value = obj.value;
    value = value.replace(/[^0-9\.]/gi, "");
    obj.value = value;
}

function Chinese(obj) {
    var value = obj.value;
    value = value.replace(/[^\u4E00-\u9FA5]/gi, "");
    obj.value = value;
}


function IdCardNo(obj,length) {
    var value = obj.value;
    value = value.replace(/[^\X0-9]/gi, "");
    obj.value = value;
}

function ChaNumAlp(obj){
    var value = obj.value;
    value = value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/gi, "");
    obj.value = value;
}

function Addr(obj) {
    var value = obj.value;
    value = value.replace(/[^\-\,\a-\z\A-\Z0-9\u4E00-\u9FA5]/gi, "");
    obj.value = value;
}

//非空判断不提示
function isEmpty(object) {
	if(object.value == "") {
		return true;
	} else {
		return false;	
	}
}

// 非空判断提示
function isEmptyAlert(object, info) {
	if(object.value == "") {
		//alert("请填写" + info + "！");
		object.focus();
		return true;
	} else {
		return false;	
	}
}

// 等值判断
function isEqual(object, value) {
	if(object.value == value) {
		return true;
	} else {
		return false;
	}
}

// 等值判断提示
function isEqualAlert(object, value) {
	if(object.value == value) {
		return true;
	} else {
		//alert("填写内容不一致！");
		object.focus();
		return false;
	}
}


//去空格
function trim(input) {
	var tempStr = "";
	tempStr = input.replace(/\s+$/g,'');
	tempStr = tempStr.replace(/^\s+/g,'');
	return tempStr;
}

//电话判断
function isPhone(str) {
    var reg = /^0(([1,2]\d)|([3-9]\d{2}))\d{7,8}$/;
	if(!reg.test(str.value)) {
		//alert("请填写正确的电话号码/单位固话！");
		str.focus();
		return false;
	} else {
		return true;	
	}
}

// 手机判断
function isMobile(str) {
	var reg = /^(13[0-9]|147|15[3-9]|15[0-2]|18[0-9]|17[0-9])[0-9]{8}$/;
	if(!reg.test(str.value)) {
		//alert("请填写正确的手机号码！");
		str.focus();
		return false;
	} else {
		return true;	
	}
}

function isPhoneOrMobile(str) {
    var reg1 = /^0(([1,2]\d)|([3-9]\d{2}))\d{7,8}$/;
    var reg2 = /^(13[0-9]|147|15[3-9]|15[0-2]|18[0-9]|17[0-9])[0-9]{8}$/;
    if (!(reg1.test(str.value) || reg2.test(str.value))) {
        //alert("请填写正确的联系方式！");
        str.focus();
        return false;
    }
    else{
      return true;
    }

}

//Email判断
function isEmail(email) {
	var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	if(!reg.test(email.value)) {
		//alert("请填写正确的电子邮箱！");
		email.focus();
		return false;
	} else {
		return true;	
	}
}

// IP判断
function isIp(ip) {
	var check = function(v) {
		try {
			return (v <= 255 && v >= 0);
		} catch (x) {
			return false;
		}
	};
	var re = ip.split(".");
	var res = (re.length == 4) ? (check(re[0]) && check(re[1]) && check(re[2]) && check(re[3]))
			: false;
	return res;
}

// 邮编判断
function isZipcode(zipcode) {
	var reg = /^[1-9]\d{5}$/;
	if(!reg.test(zipcode.value)) {
		//alert("请填写正确的邮政编码！");
		zipcode.focus();
		return false;
	} else {
		return true;	
	}
}

// 身份证判断
function isIdCardNo(idcard) {
	var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
			2, 1);
	var error;
	var varArray = new Array();
	var lngProduct = 0;
	var intCheckDigit;
	var intStrLen = idcard.length;
	var idNumber = idcard;
	var area = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外"
	};
	var S, M, Y, JYM;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	if ((intStrLen != 15) && (intStrLen != 18)) {
		error = "身份证:号码长度不对!";
		//alert(error);
		return false;
	}
	for ( var i = 0; i < intStrLen; i++) {
		varArray[i] = idNumber.charAt(i);
		if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
			error = "身份证:错误的号码!";
			//alert(error);
			return false;
		} else if (i < 17) {
			varArray[i] = varArray[i] * factorArr[i];
		}
	}
	if (area[parseInt(idcard.substr(0, 2))] == null) {
		error = "身份证:错误的地区代码!";
		//alert(error);
		return false;
	}
	if (intStrLen == 18) {
		if (parseInt(idcard.substr(6, 4)) % 4 == 0
				|| (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
						.substr(6, 4)) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}(19[0-9]{2}|20[0-1][0-9])((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
		} else {
			ereg = /^[1-9][0-9]{5}(19[0-9]{2}|20[0-1][0-9])((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
		}
		if (ereg.test(idcard)) {// 测试出生日期的合法性
			// 计算校验位
			S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
					+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11]))
					* 9
					+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
					* 10
					+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
					* 5
					+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
					* 8
					+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
					* 4
					+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
					* 2 + parseInt(idcard_array[7]) * 1
					+ parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9])
					* 3;
			Y = S % 11;
			M = "F";
			JYM = "10X98765432";
			M = JYM.substr(Y, 1);// 判断校验位
			if (M != idcard_array[17]) {
				error = "身份证:号码校验错误!";
				//alert(error);
				return false;
			}
		} else {
			error = "身份证:错误的出生日期1!";
			//alert(error);
			return false;
		}

		if (!ereg.test(idcard)) {
			error = "身份证:错误的出生日期2!";
			//alert(error);
			return false;
		}

		for (i = 0; i < 17; i++) {
			lngProduct = lngProduct + varArray[i];
		}
		intCheckDigit = 12 - lngProduct % 11;
		switch (intCheckDigit) {
		case 10:
			intCheckDigit = 'X';
			break;
		case 11:
			intCheckDigit = 0;
			break;
		case 12:
			intCheckDigit = 1;
			break;
		}
		if (varArray[17].toUpperCase() != intCheckDigit) {
			return false;
		}
	} else {
		if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0
				|| ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard
						.substr(6, 2)) + 1900) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}(19[0-9]{2}|20[0-1][0-9])((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
		} else {
			ereg = /^[1-9][0-9]{5}(19[0-9]{2}|20[0-1][0-9])((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
		}
		if (!ereg.test(idcard)) {
			error = "身份证:错误的出生日期3!";
			//alert(error);
			return false;
		}
	}
	return true;
}


//正整数判断
function isANum(num) {
	var reg = /^[1-9]*[1-9][0-9]*$/;
	if(!reg.test(num.value)) {
		//alert("请填写正确的数值！");
		num.focus();
		return false;
	} else {
		return true;	
	}
}

// 非负整数判断
function isAnNum(num) {
	var reg = /^[0-9]*$/;
	if(!reg.test(num.value)) {
		//alert("请填写正确的数值！");
		num.focus();
		return false;
	} else {
		return true;	
	}
}

// 金额判断
function isMNum(num) {
	var reg = /^[0-9]*(\.[0-9]{1,2})?$/;
	if(!reg.test(num.value)) {
		//alert("请填写正确的数值！");
		num.focus();
		return false;
	} else {
		return true;	
	}
}

/**
 * 只能输入数字[0-9]
 */
function isDigits(str){
    if(str==null||str=="") return false;
    var result=str.match(/^\d+$/);
    if(result==null)return false;
    return true;
}     

/**
 * 匹配money
 */
function isMoney(str){
    if(str==null||str=="") return false;
    var result=str.match(/^(([1-9]\d*)|(([0-9]{1}|[1-9]+)\.[0-9]{1,2}))$/);
    if(result==null)return false;
    return true;
} 
    
/**
 * 匹配English
 */
function isEnglish(str){
    if(str==null||str=="") return false;
    var result=str.match(/^[A-Za-z]+$/);
    if(result==null)return false;
    return true;
}     
  

/**
 * 匹配邮政编码
 */
function isZipCode(str){
    if(str==null||str=="") return false;
    var result=str.match(/^[0-9]{6}$/);
    if(result==null)return false;
    return true;
} 

/**
 * 匹配URL
 */
function isUrl(str){
    if(str==null||str=="") return false;
    var result=str.match(/^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\’:+!]*([^<>\"])*$/);
    if(result==null)return false;
    return true;
} 

/**
 * 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线。
 */
function isPwd(str){
    if(str==null||str=="") return false;
    var result=str.match(/^[a-zA-Z]\\w{6,12}$/);
    if(result==null)return false;
    return true;
} 

/**
 * 判断是否为合法字符(a-zA-Z0-9-_)
 */
function isRightfulString(str){
    if(str==null||str=="") return false;
    var result=str.match(/^[A-Za-z0-9_-]+$/);
    if(result==null)return false;
    return true;
} 

/**
 * 匹配汉字
 */
function isChinese(str){
    if(str==null||str=="") return false;
    var result=str.match(/^[\u4e00-\u9fa5]+$/);
    if(result==null)return false;
    return true;
} 

/**
 * 匹配中文(包括汉字和字符)
 */
function isChineseChar(str){
    if(str==null||str=="") return false;
    var result=str.match(/^[\u0391-\uFFE5]+$/);
    if(result==null)return false;
    return true;
}     

/**
 * 字符验证，只能包含中文、英文、数字、下划线等字符。
 */
function stringCheck(str){
    if(str==null||str=="") return false;
    var result=str.match(/^[a-zA-Z0-9\u4e00-\u9fa5-_]+$/);
    if(result==null)return false;
    return true;
}    

 
