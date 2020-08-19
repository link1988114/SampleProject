/*
 * 电话判断
 */
function isTel(tel) {
	var str = tel;
	var reg = /^((0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
	return reg.test(str);
}

/*
 * 手机判断
 */
function isMobile(mobile) {
	var str = mobile;
	var reg = /^(13[0-9]|147|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])[0-9]{8}$/;
	return reg.test(str);
}

/*
 * 身份证判断
 */
function isIdCardNo(idcard) {
	var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
			2, 1);
	var varArray = new Array();
	var lngProduct = 0;
	var intCheckDigit;
	var intStrLen = idcard.length;
	var idNumber = idcard;
	var area = {
		11 : '北京',
		12 : '天津',
		13 : '河北',
		14 : '山西',
		15 : '内蒙古',
		21 : '辽宁',
		22 : '吉林',
		23 : '黑龙江',
		31 : '上海',
		32 : '江苏',
		33 : '浙江',
		34 : '安徽',
		35 : '福建',
		36 : '江西',
		37 : '山东',
		41 : '河南',
		42 : '湖北',
		43 : '湖南',
		44 : '广东',
		45 : '广西',
		46 : '海南',
		50 : '重庆',
		51 : '四川',
		52 : '贵州',
		53 : '云南',
		54 : '西藏',
		61 : '陕西',
		62 : '甘肃',
		63 : '青海',
		64 : '宁夏',
		65 : '新疆',
		71 : '台湾',
		81 : '香港',
		82 : '澳门',
		91 : '国外'
	};
	var Y, JYM;
	var S, M;
	var idcard_array = new Array();
	idcard_array = idcard.split('');

	if (intStrLen != 18) {
		//$.messager.alert('警告', '请填写18位新身份证号');
		return false;
	}
	for ( var i = 0; i < intStrLen; i++) {
		varArray[i] = idNumber.charAt(i);
		if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
			//$.messager.alert('警告', '身份证号码错误');
			return false;
		} else if (i < 17) {
			varArray[i] = varArray[i] * factorArr[i];
		}
	}
	if (area[parseInt(idcard.substr(0, 2))] == null) {
		//$.messager.alert('警告', '身份证地区代码错误');
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
			M = 'F';
			JYM = '10X98765432';
			M = JYM.substr(Y, 1);// 判断校验位
			if (M != idcard_array[17]) {
				//$.messager.alert('警告', '身份证校验码错误');
				return false;
			}
		} else {
			//$.messager.alert('警告', '身份证出生日期错误');
			return false;
		}

		if (!ereg.test(idcard)) {
			//$.messager.alert('警告', '身份证出生日期错误');
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
			//$.messager.alert('警告', '身份证出生日期错误');
			return false;
		}
	}

	return true;
}

/*
 * 整数判断
 */
function isNum(num) {
	var re = /^(-)?[0-9]*$/;
	return re.test(num);
}

/*
 * 正整数判断
 */
function isANum(num) {
	var re = /^[1-9]*[1-9][0-9]*$/;
	return re.test(num);
}

/*
 * 非负整数判断
 */
function isNANum(num) {
	var re = /^[0-9]*$/;
	return re.test(num);
}

/*
 * 金额判断，不超过百万
 */
function isMNum(je) {
	var re = /^(-)?[0-9]{1,7}(\.[0-9]{1,2})?$/;
	return re.test(je);
}

/*
 * 中文判断
 */
function isNChinese(str) {
	var reg = /[^\u4E00-\u9FA5]/;
	return reg.test(str);
}

/*
 * 协储号判断
 */
function isXCH(xch) {
	var re = /^[0-9]{8}$/;
	return re.test(xch);
}

/*
 * 定期一本通判断
 */
function isDQYBT(acct_no) {
	var re = /^[0-9]{14}-[0-9]{3}$/;
	return re.test(acct_no);
}

/*
 * 本外币一本通判断
 */
function isBWBYBT(acct_no) {
	var re = /^[0-9]{14}$/;
	return re.test(acct_no);
}

/*
 * 绿卡通判断
 */
function isLKT(acct_no) {
	var re = /^[0-9]{19}-[0-9]{4}$/;
	return re.test(acct_no);
}

/*
 * 活期判断
 */
function isCURACC(acct_no) {
	var re = /^[0-9]{18,19}$/;
	return re.test(acct_no);
}

/*
 * 定期、通知存款、定活两便判断
 */
function isFixacc(acct_no) {
	var re = /^[0-9]{19}$/;
	return re.test(acct_no);
}