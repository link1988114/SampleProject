var imgList = null;
var filterKeys = null;
var dateArr = [];
var format = "yyyy-MM-dd";
var filters = {};

var filterStr = "";
var wherestr = "";

var newflag = true;

var dataTable = null;
var currSelectData = null;

$(function () {
    pageInit();
    
    $("#mask").hide();
    
    $("#btn_adduser").click(function(e){
    	fillUpdateForm(null);
    	$("#mask").show();
    	newflag=true;
    	$("#username").removeAttr("disabled");
    	$("#btn_resetpw").hide();
    });
    
    $("#btn_deluser").click(function(e){
    	delUser();
    });
    

    $("#btn_save").click(function (e) {
    	saveUpdate();
    });
    
    $("#btn_close").click(function(e){
    	$("#mask").hide();
    });
    
    $("#btn_resetpw").click(function(e){
    	var url = "../user/reset.do";
    	var data = {
    		username : $("#username").val()
    	}
    	$.post(url, data, function(re){
    		var json = new Function("return"+re)();
    		alert(json.msg);
    	});
    });
});


//页面初始化
function pageInit() {
    dataTable = initTable("viewTable");
    initBrchSelect();
    initRights();
    getDescription();
}

//获取油站选择
function initBrchSelect(){
    var url = "../station/getallstation.do";
    $.get(url,function(re){
        var json = new Function("return"+re)();
        if(json.result=="success"){
            var arr = new Function("return"+json.msg)().data;
            createStationSelect(arr);
        }
    });
}

//初始化权限
function initRights(){
	$.get("../user/getlevelmenus.do", function(re){
		var json = new Function("return"+re)();
		var arr = json.data;
		createRightsSelect(arr);
	});
}

//创建权限选择列表
function createRightsSelect(arr){
	var htmlstr = "";
    for(var i=0; i<arr.length; i++){
        htmlstr += "<option value='"+arr[i].userlevel+"'>"+arr[i].level_name+"</option>";
    }
    $("#select_rights").html("");
    $("#select_rights").html(htmlstr);
}

//创建油站选择列表
function createStationSelect(arr){
    var htmlstr = "";
    for(var i=0; i<arr.length; i++){
        htmlstr += "<option value='"+arr[i].station_id+"'>"+arr[i].jcxx_zm1+"</option>";
    }
    $("#select_station").html("");
    $("#select_station").html(htmlstr);
    $("#select_station").select2();
}

//获取全部字段
function getDescription(){
	var url="../des/getlist.do";//?type="+type;
	$.get(url, function(re){
		var json = new Function("return"+re)();
		if(json.result=="success"){
			var content = json.msg.split("@@")[0];
			var data = new Function("return"+content)().data;
			createCataSetting(data);
		}
		else{
			alert(json.msg);
		}
	});
}

//创建字段选择
function createCataSetting(data){
	var htmlstr = "";
	for(var i=0; i<data.length; i++){
		htmlstr += 
	      "<div class=\"accordion-group widget-box\" style=\"margin:0\">"+
	        "<div class=\"accordion-heading\">"+
	          "<div class=\"widget-title\"> <a data-parent=\"#collapse-group\" href=\"#collapseG" + i + "\" data-toggle=\"collapse\" class=\"\"> <span class=\"icon\"><i class=\"icon-circle-arrow-right\"></i></span>"+
	            "<h5>" + data[i].des_name + "</h5>"+
	            "</a>"+
	            "<div class='cataEditHead'>"+
		        	"<input type='radio' checked='checked' name='radio_all_"+i+"' />无"+
					"<input type='radio' name='radio_all_"+i+"' />查看"+
					"<input type='radio' name='radio_all_"+i+"' />编辑"+
					"<input type='hidden' value='"+data[i].des_keyname+"'>"+
					"<input type='hidden' value='"+data[i].des_id+"'>"+
					"<input type='hidden' value='"+data[i].des_name+"'>"+
				"</div>"+
	          "</div>"+
	        "</div>"+
	        "<div class=\"accordion-body collapse\" id=\"collapseG"+i+"\" style=\"height: 0px;\">"+
	            "<div class=\"widget-content\" style='padding:0;'>"+
	            	createCataDetailView(data[i].child, i) + 
	            "</div>"+
	        "</div>"+
	      "</div>";
	}
	$("#collapse-group").html(htmlstr);
	
	
	$(".cataEditHead input[type='radio']").click(function(e){
		var cataName = this.name;
		var editable = "0"; //0无  1查看 2编辑
		var children = this.parentNode.parentNode;
		
		$(".cataEditHead input[name="+cataName+"]").each(function(index){
			if(this.checked==true){
				editable = index;
			}
		});
		
		$("."+cataName).each(function(index){
			for(var i=0; i<this.children.length; i++){
				this.children[i].checked=false;
				if(i%3==editable){
					this.children[i].checked=true;
				}
			}
		});
	});
	
	$(".cataEdit input[type='radio']").click(function(e){
		var index = 0;
		var parent = this.parentNode;
		for(var i=0; i<parent.children.length; i++){
			if(parent.children[i].checked==true){
				index=i;
				break;
			}
		}
		if(index==1 || index ==2){
			
		}
	});
	
}

//生成具体字段str
function createCataDetailView(arr, parentIndex){
	var htmlstr = "";
	for(var i=0; i<arr.length; i++){
		htmlstr += 
			"<div class='cataItem'>"+
				"<div style='float:left'>"+
					"<span>"+arr[i].des_name+"</span>"+
					"<input type='hidden' value='"+arr[i].des_keyname+"' />"+
					"<input type='hidden' value='"+arr[i].des_id+"' />"+
					"<input type='hidden' value='"+arr[i].des_disp+"' />"+
				"</div>"+
				"<div style='float:right' class='cataEdit radio_all_"+parentIndex+"'>"+
					"<input type='radio' checked='checked' name='radio_" + parentIndex + "_" + i + "' />无"+
					"<input type='radio' name='radio_" + parentIndex + "_" + i + "' />查看"+
					"<input type='radio' name='radio_" + parentIndex + "_" + i + "' />编辑"+
				"</div>"+
				"<div class='space'></div>"+
			"</div>";
	}
	return htmlstr;
}

//初始化日期
function setDatePicker(arr, format) {
    for (var i = 0; i < arr.length; i++) {
        laydate.render({
            elem: arr[i],
            format: format
        });
    }
}

//初始化表格
function initTable(tableID) {
    if (dataTable != null) {
        dataTable.destroy();
    }

    var url = "../user/list.do";
    var options = {
    	scrollX:true,
        scrollY:371,
        processing: true,
        serverSide: true,
        ajax: {
            url: url,
            type: "post"
        },
        autoWidth: false,
        searching: true,
        ordering: false,
        language: { "url": "js/chinese.json" }, 
        select: {
        	style:'single',
        	info:false
        },
        /*
        initComplete: function() {
            iframeResize('homeframe');
        }
        */
        columns: [
        	{title: "修改", searchable: false, width:"10px",
        		"render": function(data, type, row, meta) {
        		     var htmlstr = "";
        		     var params = jsonTostr(row);
        		     htmlstr = "<a href='javascript:void(0)' class='reset-pwd' onclick=\"showEdit()\">修改</a>";
        		     return htmlstr; 
        		 }
        	},
        	{title:"用户名", searchable:false, data:"username", defaultContent: "" },
        	{title:"权限", searchable:false, data:"userlevel", defaultContent: "", visible:false},
        	{title:"用户权限", searchable:false, data:"level_name", defaultContent: "" },
        	{title:"所属油站编号", searchable:false, data:"user_station_id", defaultContent: "" },
        	{title:"所属油站", searchable:false, data:"station_name", defaultContent: "" },
        	{title:"可用字段", searchable:false, data:"content_access", defaultContent: "", visible:false }
        ]
    };
    var renderTable = $('#' + tableID).DataTable(options);
    renderTable.on('select', function (e, dt, type, indexes) {
        if ( type === 'row' ) {
            var data = renderTable.rows(indexes).data()[0];
            var element = renderTable[type](indexes).nodes();
            currSelectData = data;
            // do something with the ID of the selected items
            fillUpdateForm(data);
        }
    } );
    renderTable.on('draw', function(){
    	//var arr = document.getElementById(tableID).getElementsByTagName("tbody")[0].getElementsByTagName("tr");
    	//var node = arr[arr.length-1];
    	//node.style['border-bottom'] = "1px solid black";
    	$("#"+tableID+" tbody tr:last-child").css("border-bottom","1px solid black");
    });
    
    return renderTable;
}

function saveUpdate() {
    var sendData = {
    	username : $("#username").val(),
    	userlevel : $("#select_rights").val(),
    	user_station_id : $("#select_station").val(),
    	content_access : JSON.stringify(getRestrictData())
    };
     
    if(sendData.username==""){
    	alert("请输入用户名");
    	return false;
    }
    
    var url = "";
    if(newflag==true){
    	url = "../user/add.do";
    }
    else{
    	url = "../user/update.do";
    }
    
    $.post(url, sendData, function(re){
    	var json = new Function("return"+re)();
    	if(json.result=="success"){
    		alert(json.msg);
    		dataTable.draw(false);
    		$("#mask").hide();
    	}
    	else{
    		alert(json.msg);
    	}
    });

}

function delUser(){
	if(currSelectData==null){
		alert("请选择用户");
	}
	else{
		var url = "../user/del.do";
		var data = {
			username:currSelectData.username
		};
		$.post(url, data, function(re){
			var json = new Function("return"+re)();
			if(json.result=="success"){
				dataTable.draw(false);
				alert(json.msg);
			}
			else{
				alert(json.msg);
			}
		});
	}
}







///////////////////////////////////////////////////////////////////////

function fillUpdateForm(data) {
	if(data != null){
		$("#username").val(data.username);
		$("#select_rights").val(data.userlevel);
		$("#select_station").select2("val", data.user_station_id);
		
		var json = new Function("return"+data.content_access)();
		var dataJson = reFormJson(json);
		var count = 0;
		for(var key in dataJson){
			count++;
		}
		if(count==0){
			setRestrictData(null);
		}
		else{
			setRestrictData(dataJson);
		}
	}
	else{
		$("#username").val("");
		$("#select_rights").val(0);
		$("#select_station").select2("val", "");
		setRestrictData(null);
	}

}

function reFormJson(json){
	var result = {};
	for(var i=0; i<json.length; i++){
		var item = json[i];
		result[item.des_keyname]=item.child;
	}
	return result;
}

function getRestrictData() {
    var sendData = [];
    var headArr = $(".cataEditHead");
    for(var headI=0; headI<headArr.length; headI++){
    	var editable = 0;
    	var cataName = headArr[headI].children[0].name;
    	var cataKey = headArr[headI].children[3].value;
    	var cataID = headArr[headI].children[4].value;
    	var cataNameCN = headArr[headI].children[5].value;
    	for(var j=0; j<headArr[headI].children.length; j++){
    		if(headArr[headI].children[j].checked==true){
    			editable=j;
    			break;
    		}
    	}
    	
    	var dataarr = [];
    	if(editable==1 || editable==2){
    		var nodeArr = $("."+cataName);
    		for(var k=0; k<nodeArr.length; k++){
    			var temp = {
    				des_name:"",
    				des_keyname:"",
    				des_id:"",
    				des_disp:"",
    				editable:"0"
    			};
    			var infoNode = nodeArr[k].parentNode.children[0];
    			temp.des_name = infoNode.children[0].innerHTML;
    			temp.des_keyname = infoNode.children[1].value;
    			temp.des_id = infoNode.children[2].value;
    			temp.des_disp = infoNode.children[3].value;
    			
    	    	for(var l=0; l<nodeArr[k].children.length; l++){
    	    		if(nodeArr[k].children[l].checked==true){
    	    			temp.editable=l;
    	    			break;
    	    		}
    	    	}
    	    	if(temp.editable==1 || temp.editable==2){
    	    		dataarr.push(temp);
    	    	}
    		}
    	}
    	
		if(dataarr.length>0){
			var obj = {
				des_id:cataID,
				des_keyname:cataKey,
				des_name:cataNameCN,
				child:dataarr
			}
			sendData.push(obj);//[cataKey]=dataarr;
		}
    }

	return sendData;
}

function setRestrictData(json){
	var headArr = $(".cataEditHead");
	for(var headI=0; headI<headArr.length; headI++){
		var cataName = headArr[headI].children[0].name;
    	var cataKey = headArr[headI].children[3].value;
    	
    	//reset head
    	var headlabel = 0;
    	for(var j=0; j<headArr[headI].children.length; j++){
    		headArr[headI].children[j].checked = false;
    	}
    	headArr[headI].children[headlabel].checked = true;
    	
    	//setting
    	var nodeArr = $("."+cataName);
		for(var k=0; k<nodeArr.length; k++){
			var infoNode = nodeArr[k].parentNode.children[0];
			var des_keyname = infoNode.children[1].value;
			var editable = "0";
			if(json != null){
				editable = getEditIndex(json[cataKey],des_keyname);
			}

			if(editable>headlabel){
				headArr[headI].children[headlabel].checked = false;
				headArr[headI].children[editable].checked = true;
				headlabel = editable;
			}
			
	    	for(var l=0; l<nodeArr[k].children.length; l++){
	    		nodeArr[k].children[l].checked==false;
	    	}
	    	nodeArr[k].children[editable].checked = true;
		}
	}
}

function getEditIndex(arr, des_keyname){
	if(arr==null || arr==undefined){
		return "0";
	}
	for(var i=0; i<arr.length; i++){
		if(arr[i].des_keyname==des_keyname){
			return arr[i].editable+"";
		}
	}
	return "0";
}



function showEdit(row){
	$("#mask").show();
	newflag=false;
	$("#username").attr("disabled","disabled");
	$("#btn_resetpw").show();
}





function jsonTostr(json) {
    var str = "";
    for (var key in json) {
        str += key + "@@@" + json[key] + "$$$";
    }
    if (str == "") {
        return str;
    }
    else {
        return str.substr(0, str.length - 3);
    }
}

function strTojson(str) {
    var json = {};
    var arr = str.split("$$$");
    for (var i = 0; i < arr.length; i++) {
        var temp = arr[i].split("@@@");
        var key = temp[0];
        var value = temp[1];
        json[key] = value;
    }
    return json;
}




function uploadImg(formid,formfileID, dispid){
	doUpload("../upload/upload.do",formid, formfileID, dispid);
}

//上传文件
function doUpload(action, formid, formfileID, dispid) {
    if ($("#"+formfileID).val() == "") {
        alert("请选择一个文件");
        return null;
    }
    var formObj = document.getElementById(formid);
    formObj.action = action;
    var options = {
        success: function(re) {
            var json = new Function("return" + re)();
            if(json.result=="success"){
            	var labelobj = document.getElementById(dispid);
            	var filename = new Function("return"+json.msg)().data[0]
            	var url = "../upload/"+filename;
            	labelobj.href="javascript:window.open('"+url+"')";
            	$("#"+dispid.replace("form_img_","viewupdate_input_")).val(filename);
            	alert("上传成功");
            }
            else{
            	alert(json.msg);
            }
        },
        error:function(e){
        	alert("出现错误,请重试");
        	//hideLoading();
        }
    };
    $("#"+formid).ajaxSubmit(options);
    //showLoading();
}




////////////////////////////////////////////////////////////////////////////

function editCustomer(str) {
    var json = strTojson(str);
    fillPopWin(json);
    openPop("pop1", "mask", 450, 440);
}


function fillPopWin(json) {
    $("#customerName").val(json.company_name);
    $("#district").val(json.district);
    $("#rank").val(json.rank);
    $("#taxCode").val(json.tax_code);
    $("#customerInfo").html(json.company_info);
}


function updateCustomerInfo() {
    var data = {
        company_name: $("#customerName").val(),
        district: $("#district").val(),
        rank: $("#rank").val(),
        tax_code: $("#taxCode").val(),
        company_info: $("#customerInfo").val()
    };

    var url = "../customer/update.do";
    $.post(url, data, function (re) {
        var json = new Function("return" + re)();
        if (json.result == "success") {
            dataTable.draw(false);
            closePop("pop1","mask");
        }
        else {
            alert(json.msg);
        }
    });
}

function updateAllRank() {
    var url = "../customer/updateall.do";
    $.get(url, function (re) {
        var json = new Function("return" + re)();
        if (json.result == "success") {
            dataTable.draw(false);
            alert(json.msg);
        }
        else {
            alert(json.msg);
        }
    });
}










function numOnly(obj){
	var value = obj.value;
	value = value.replace(/[^0-9.]/g,"");
	obj.value = value;
}


function getParam(param) { //param为要获取的参数名 注:获取不到是为null
    var currentUrl = window.location.href; //获取当前链接
    var arr = currentUrl.split("?");//分割域名和参数界限
    if (arr.length > 1) {
        arr = arr[1].split("&");//分割参数
        for (var i = 0; i < arr.length; i++) {
            var tem = arr[i].split("="); //分割参数名和参数内容
            if (tem[0] == param) {
                return tem[1];
            }
        }
        return null;
    }
    else {
        return null;
    }
}