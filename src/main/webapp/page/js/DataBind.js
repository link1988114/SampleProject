var DataBind = {
	createNew:function(dataset){
		var dBind = {};
		
		var valuestr0 = "|input|"
		var valuestr1 = "|select|";
		var valuestr2 = "|textarea|";
		var valuestr3 = "|div|p|button|h1|h2|h3|h4|h5|group|h6|label|li|strong|td|th|title|";
		var valuestr4 = "|img|";
		var valuestr5 = "|option|";
		
		dBind.data = {};
		
		dBind.setData=function(json){
			for(var key in json){
				dBind.data[key] = json[key];
				updateView(key,json[key]);
			}
		}
		
		dBind.getData=function(){
			updateData();
			return dBind.data;
		}
		
		init(dataset);
		
		/////////////////////////////////////////////////
		
		function init(dataset){
			dBind.setData(dataset);
		}

		
		function updateView(id, value){
			//data change			
			var obj = document.getElementById(id);
			var tag = "|"+obj.tagName.toLowerCase()+"|";
			
			if(valuestr0.replace(tag) != valuestr0){
				//input
				var type = obj.type;
				if(type == "checkbox"){
					var els = document.getElementsByName(obj.name);
					var arr = value.length>2 ? value.substring(1,value.length-1).split("|") : [];
					for(var i=0; i<els.length; i++){
						els[i].checked = false;
						for(var j=0; j<arr.length; j++){
							if(els[i].value == arr[j]){
								els[i].checked=true;
								break;
							}
						}
					}
				}
				else if(type == "radio"){
					var els = document.getElementsByName(obj.name);
					for(var i=0; i<els.length; i++){
						if(els[i].value == value){
							els[i].checked = true;
						}
						else{
							els[i].checked = false;
						}
					}
				}
				else{
					obj.value = value;
				}
			}
			else if(valuestr1.replace(tag) != valuestr1){
				//select
				obj.value = value;
			}
			else if(valuestr2.replace(tag) != valuestr2){
				obj.textContent = value;
			}
			else if(valuestr3.replace(tag) != valuestr3){
				obj.innerHTML = value;
			}
			else if(valuestr4.replace(tag) != valuestr4){
				obj.src = value;
			}
			else if(valuestr5.replace(tag) != valuestr5){
				obj.value = value;
				obj.innerHTML = value;
			}
		}
		
		function updateData(){
			//view change
			for(var id in dBind.data){
				var obj = document.getElementById(id);
				var tag = "|"+obj.tagName.toLowerCase()+"|";
				
				if(valuestr0.replace(tag) != valuestr0){
					//input
					var type = obj.type;
					if(type == "checkbox"){
						var temp = "";
						var els = document.getElementsByName(obj.name);
						for(var i=0; i<els.length; i++){
							if(els[i].checked==true){
								temp += els[i].value+"|";
							}
						}
						temp = temp.length>0 ? "|"+temp : "";
						dBind.data[id]=temp;
					}
					else if(type == "radio"){
						var els = document.getElementsByName(obj.name);
						dBind.data[id] = "";
						for(var i=0; i<els.length; i++){
							if(els[i].checked == true){
								dBind.data[id] = els[i].value;
								break;
							}
						}
					}
					else{
						dBind.data[id] = obj.value;
					}
				}
				else if(valuestr1.replace(tag) != valuestr1){
					//select
					dBind.data[id] = obj.value;
				}
				else if(valuestr2.replace(tag) != valuestr2){
					dBind.data[id] = obj.textContent;
				}
				else if(valuestr3.replace(tag) != valuestr3){
					dBind.data[id] = obj.innerHTML;
				}
				else if(valuestr4.replace(tag) != valuestr4){
					dBind.data[id] = obj.src;
				}
				else if(valuestr5.replace(tag) != valuestr5){
					dBind.data[id] = obj.value;
				}
			}
		}
		
		
		/////////////////////////////////////////////////
		
		return dBind;
	}
	
};