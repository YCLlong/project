

//AJAX提交
var AjaxConn = function (Data, Action) {
	var MyAjax = $.ajax({
		type: 'post',
		cache: false,
		url: Action,
		data: Data,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		async: false
	});
	var obj = JSON.parse(MyAjax.responseText); 
	return obj; //由JSON字符串转换为JSON对象;
	//用法
	//1(表单)： var ReturnDate=AjaxConn($("#MyForm").serialize(), "../user/userSub.action");
	//2(一般用法)： var ReturnDate=AjaxConn({名称:"值",名称:"值"}, "../user/userSub.action");
};


//只能输入数字
//使用方法     onkeyup="numberOnly(this)"
function numberOnly(src) {
    (src.v = function () { src.value = src.value.replace(/[^0-9]+/, ''); }).call(src);
}

//只能输入数字和小数点
//使用方法     onkeyup="floatOnly(this)"
function floatOnly(src) {
	(src.v = function () { src.value = src.value.replace(/[^0-9.]+/, ''); }).call(src);
}

//排除特殊字符
//使用方法     onkeyup="textStr(this)"
function textStr(src) {
    (src.v = function () { src.value = src.value.replace(/[#$<>&?%]+/, ''); }).call(src);
}


//字符串包含方法
function contains(string, substr, isIgnoreCase) {  //主字符串，子字符串，是否忽略大小写
    if (isIgnoreCase) {
        string = string.toLowerCase();
        substr = substr.toLowerCase();
    }
    var startChar = substr.substring(0, 1);
    var strLen = substr.length;
    for (var j = 0; j < string.length - strLen + 1; j++) {
        if (string.charAt(j) == startChar)//如果匹配起始字符,开始查找
        {
            if (string.substring(j, j + strLen) == substr)//如果从j开始的字符与str匹配，那ok
            {
                return true;
            }
        }
    }
    return false;
}

//字符串trim方法
function trim(str) {
    str = str.replace(/^\s+/, '');
    for (var i=str.length-1; i>=0; i--) {
        if (/\S/.test(str.charAt(i))) {
            str = str.substring(0, i + 1);
            break;
        }
    }
    return str;
}

//替换所有字符串方法（  用法： str.replaceAll(old, new);  ）
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
   if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
       return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
   } else {  
       return this.replace(reallyDo, replaceWith);  
   }  
};

//校验身份证号
function IdentityCodeValid(code) { 
    var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
    var tip = "";
    var pass= true;

    if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
        tip = "身份证号格式错误";
        pass = false;
    }

   else if(!city[code.substr(0,2)]){
        tip = "地址编码错误";
        pass = false;
    }
    else{
        //18位身份证需要验证最后一位校验位
        if(code.length == 18){
            code = code.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
            //校验位
            var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++)
            {
                ai = code[i];
                wi = factor[i];
                sum += ai * wi;
            }
            var last = parity[sum % 11];
            if(parity[sum % 11] != code[17]){
                tip = "校验位错误";
                pass =false;
            }
        }
    }
    //if(!pass) alert(tip);
    return pass;
}
/**
 * ajax post请求<br>
 * url 请求地址<br>
 * param 请求参数 json格式<br>
 * callback 成功回调方法<br><br>
 * @return json<br>
 */
$.postJSON = function(url, param, callback) {
	return jQuery.ajax({
		'type' : 'POST',
		'url' : url,
		'data' : param,
		'dataType' : 'json',
		'success' : callback,
		'error' : function(e){
			console.log(e);
		}
	});
};
$.postSyncJSON = function(url, param, callback) {
	return jQuery.ajax({
		'type' : 'POST',
		'async' : false,
		'url' : url,
		'data' : param,
		'dataType' : 'json',
		'success' : callback,
		'error' : function(e){
			console.log(e);
		}
	});
};

/**
 * URL请求
 * 
 * @param url
 *            提交请求地址
 * @param target
 *            更新的div目标，一般使用id来标识，也可以使用jquery的动态选择方式，如：$('.J_reg_form')
 * @param isBack
 *            是否设置后退、前进标识。值为true则设置，允许使用浏览器的前进、后退功能；否则则忽略。
 */
function load(url, target) {
	try {
		$.ajax({
			url : url,
			cache : false,
			async : true,
			success : function(result) {
				try{
					$(target).html(result);
				}catch(e){
					console.error(e);
                }
			},
			error : function() {
				alert("loaded but error!!!!");
			}
		});
	} catch (e) {
		alert(e);
	}
}

function loadPost(url,param, target) {
	try {
		$.ajax({
			url : url,
			cache : false,
			async : true,
			data  : param,
			success : function(result) {
				try{
					$(target).html(result);
				}catch(e){
					console.error(e);
                }
			},
			error : function() {
				alert("loaded but error!!!!");
			}
		});
	} catch (e) {
		alert(e);
	}
}


/**
 * POST提交,成功之后出发回掉函数
 * 
 * @param url
 *            提交请求地址
 * @param params
 *            提交请求的参数，一般使用$(form).serialize()生成，也可以手写，格式类似get参数.
 * @param target
 *            更新的div目标，一般使用id来标识，也可以使用jquery的动态选择方式，如：$('.J_reg_form')
 * @param callBackFunc
 *            请求成功后如果设置了回掉函数就会出发回掉函数。
 */
function loadPostCallBack(url, params, target, successCallBackFunc,errCallBackFunc) {
	try {
		$.ajax({
			cache : false,
			type : "POST",
			url : url,
			data : params,
			async : true,
			success : function(result) {
				try{
					$(target).html(result);
				}catch(e){
					log(e.name + ": " + e.message, "error");
				}
				if (successCallBackFunc != null && typeof successCallBackFunc == "function") {
                    successCallBackFunc.call(this,result);
				}
			},
			error : function() {
				$(".J_Loading").html("loaded but error!!!!");
                if (errCallBackFunc != null && typeof errCallBackFunc == "function") {
                    errCallBackFunc.call(this);
                }
			}
		});
	} catch (e) {
		alert(e);
	}
}


function clearBgPicture(){
	$("body").css("background-image","");
}


function showHouse(houseId){
	window.open("/cms/house/houseShow.htm?houseId=" + houseId);
}
