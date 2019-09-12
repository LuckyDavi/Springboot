//js获取当前登陆时间
function showtime(){
	var today;
	var hour;
	var second;
	var minute;
	var year;
	var month;
	var date;
	var strDate;
	today = new Date();
	var n_day = today.getDay();
	// alert(n_day);
	switch(n_day)
	{
		case 1:{ strDate = "星期一"} break;
		case 2:{ strDate = "星期二"} break;
		case 3:{ strDate = "星期三"} break;
		case 4:{ strDate = "星期四"} break;
		case 5:{ strDate = "星期五"} break;
		case 6:{ strDate = "星期六"} break;
		case 7:{ strDate = "星期日"} break;
	}
	// alert("today is "+strDate);
	year = today.getFullYear();
	month = today.getMonth()+1;
	date = today.getDate();
	hour = today.getHours();
	minute = today.getMinutes();
	second = today.getSeconds();
	if(month<10) month = "0" + month;
	if(date<10) date = "0" + date;
	if(hour<10) hour = "0" + hour;
	if(minute<10) minute = "0" + minute;
	if(second<10) second = "0" + second;
	document.getElementById("clock").innerHTML = year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
	setTimeout("showtime();",1000);
}

function getDateNow()
{
	var year;
	var month;
	var date;
	var today = new Date();
	year = today.getFullYear();
	month = today.getMonth()+1;
	date = today.getDate();
	if(month<10) month = "0" + month;
	if(date<10) date = "0" + date;
	return  year + "-" + month + "-" + date;
}

function isNull(value)
{
  value =  (value==undefined || value==null) ? "":value;
  value =  (""+value).replace(/\s/g,"");
  if(value=="")
    return true;
  else
    return false;
}

function isAttNull(value)
{
  if(value==undefined || value==null)
    return true;
  else
    return false;
}

function isNumber(str)
{
    if(str!='')
    {
      if(isNaN(str)){return false;}else{return true;}
    }
    return true;
} 


// 一般用于删除，update由于参数太多，不建议使用
function ajax_url(id,delurl,funobj){
	// if(confirm("确认删除吗?")){
	if(delurl.indexOf("?")>=0)
    {
        delurl+="&t="+randomNumber(1,100000);
    }
    else
    {
       delurl+="?t="+randomNumber(1,100000);
    }
	    var json = {
				"id":id
			};

		$.ajax({
	        type: "POST",
	        dataType: "json",
	        async: false,
	        url: delurl,
	        data: {"json":JSON.stringify(json)},
	        cache: false,
	        beforeSend: function () {
	            return true;
	        },
	        success: function (data) {

	        	funobj(data);

	        },
	        error: function () {
	        	 return true;
	        }
		});

	// }
}


// 提示框
function alert_base(message,gourl)
{
	// var htmlString = "<button type=\"button\" class=\" btn btn-primary\"
	// ezmodal-target=\"#example4\">Example</button>"
    var html = [];
    html.push("<div id=\"mymodal\" class=\"ezmodal\" ezmodal-escclose=\"false\">");
    html.push("<div class=\"ezmodal-container \">");
    html.push("<div class=\"ezmodal-header\">");
    html.push("<div class=\"ezmodal-close\" protein_data-dismiss=\"ezmodal\">x</div>");
    html.push("提示框");
    html.push("</div>");
    html.push("<div class=\"ezmodal-content\">");
	html.push("<p>"+message+"</p>");
    html.push(" </div>");
    html.push("<div class=\"ezmodal-footer alert-success\">");
    html.push("<button type=\"button\" class=\"btn\" protein_data-dismiss=\"ezmodal\">确定</button>");
    html.push("</div>");
    html.push("</div>");
    html.push("</div>");

    $("#message").html(html.join(''));
    
	    if(!isNull(gourl))
	    {
	        $('#mymodal').ezmodal({
	        	    'width': 300,
	                'onClose': function () {
	                	if(gourl=='.')
	                	{
	                	    window.location.reload();
	                	    // window.location.replace(window.location);
	                	}
	                	else
	                	{
	                       window.location=gourl;
	                	}
	                }
	            });
	    }
	    else
	    {
	       $('#mymodal').ezmodal({
	                 'width': 300
	            });
	    }
    
    $('#mymodal').ezmodal('show');
}


// 提示框 参数为一个方法 一般用于刷新分面表格(alert_function('操作成功',table_reload());)
function alert_function(message,funobj)
{
	// var htmlString = "<button type=\"button\" class=\" btn btn-primary\"
	// ezmodal-target=\"#example4\">Example</button>"
    var html = [];
    html.push("<div id=\"mymodal\" class=\"ezmodal\" ezmodal-escclose=\"false\">");
    html.push("<div class=\"ezmodal-container \">");
    html.push("<div class=\"ezmodal-header\">");
    html.push("<div class=\"ezmodal-close\" protein_data-dismiss=\"ezmodal\">x</div>");
    html.push("提示框");
    html.push("</div>");
    html.push("<div class=\"ezmodal-content\">");
	html.push("<p>"+message+"</p>");
    html.push(" </div>");
    html.push("<div class=\"ezmodal-footer alert-success\">");
    html.push("<button type=\"button\" class=\"btn\" protein_data-dismiss=\"ezmodal\">确定</button>");
    html.push("</div>");
    html.push("</div>");
    html.push("</div>");

    $("#message").html(html.join(''));
    
 
 
    $('#mymodal').ezmodal({
	     'width': 300,
	      'onClose': function () {
	          funobj();
	      }
	 });
 
    
    $('#mymodal').ezmodal('show');
}


// 确认框
function alert_confirm(message,id,delurl,funobj)
{
	// alert("id"+id);
	// var htmlString = "<button type=\"button\" class=\" btn btn-primary\"
	// ezmodal-target=\"#example4\">Example</button>"
 
    var html = [];
    html.push("<div id=\"mymodal\" class=\"ezmodal\" ezmodal-escclose=\"false\">");
    html.push("<div class=\"ezmodal-container \">");
    html.push("<div class=\"ezmodal-header\">");
    html.push("<div class=\"ezmodal-close\" protein_data-dismiss=\"ezmodal\">x</div>");
    html.push("确认框");
    html.push("</div>");
    html.push("<div class=\"ezmodal-content\">");
	html.push("<p>"+message+"</p>");
    html.push(" </div>");
    html.push("<div class=\"ezmodal-footer alert-success\" style=\"background-position:center\">");
    html.push("<button type=\"button\" class=\"btn\" onclick=\"$('#mymodal').ezmodal('hide');ajax_url('"+id+"','"+delurl+"',"+funobj+");\">确定</button>");
    html.push("<button type=\"button\" class=\"btn\" onclick=\"$('#mymodal').ezmodal('hide');\">取消</button>");
    html.push("</div>");
    html.push("</div>");
    html.push("</div>");

    $("#message").html(html.join(''));
    $('#mymodal').ezmodal({
                 'width': 300
            });
    
    $('#mymodal').ezmodal('show');
}


function if_alert_confirm(message)
{
	// alert("id"+id);
	// var htmlString = "<button type=\"button\" class=\" btn btn-primary\"
	// ezmodal-target=\"#example4\">Example</button>"
    var html = [];
    html.push("<div id=\"mymodal\" class=\"ezmodal\" ezmodal-escclose=\"false\">");
    html.push("<div class=\"ezmodal-container \">");
    html.push("<div class=\"ezmodal-header\">");
    html.push("<div class=\"ezmodal-close\" protein_data-dismiss=\"ezmodal\">x</div>");
    html.push("确认框");
    html.push("</div>");
    html.push("<div class=\"ezmodal-content\">");
	html.push("<p>"+message+"</p>");
    html.push(" </div>");
    html.push("<div class=\"ezmodal-footer alert-success\" style=\"background-position:center\">");
    html.push("<button type=\"button\" class=\"btn\" onclick=\"$('#mymodal').ezmodal('hide');return true; \">确定</button>");
    html.push("<button type=\"button\" class=\"btn\" onclick=\"$('#mymodal').ezmodal('hide');return false; \">取消</button>");
    html.push("</div>");
    html.push("</div>");
    html.push("</div>");

    $("#message").html(html.join(''));
    $('#mymodal').ezmodal({
                 'width': 300
            });
    
    $('#mymodal').ezmodal('show');
}

// 下拉式菜单赋值方法
function dropdown_menu(menuobj,titleobj,valueobj)
{
     var html = [];
     html.push(menuobj.text()+"<span class=\"caret\"></span>");
     $('#'+titleobj).html(html.join(''));
     $('#'+valueobj).attr('value',menuobj[0].getAttribute('value'));
  
}

 function gethtml()
  {
     document.getElementById('texthtml').value= $('#dropdown-menu').html();
  }
  
 function getDataCode(url,code_type,parent_code_id,fun_addDataCode)
 {
    if(url.indexOf("?")>=0)
    {
        url+="&t="+randomNumber(1,100000);
    }
    else
    {
       url+="?t="+randomNumber(1,100000);
    }
 	 if(isNull(parent_code_id))
 	 {
 	   parent_code_id='';
 	 }

     var json = {
				"code_type":code_type,
				"parent_code_id":parent_code_id
     };

		$.ajax({
	        type: "POST",
	        dataType: "json",
	        async: false,
	        url: url,
	        data: {"json":JSON.stringify(json)},
	        cache: false,
	        beforeSend: function () {
	            return true;
	        },
	        success: function (data) {
	        	  // alert(JSON.stringify(protein_data));
	        	  for(var par in data)
	        	  {
 	        	       fun_addDataCode(data[par].code_displayname,data[par].code_id,data[par].code_value);
	        	  }
	        	 return true;
	        },
	        error: function () {
	        	 return true;
	        }
		});
 }
 
 function CustomActionValues(url,guid,getCustomActionValues){
 	if(url.indexOf("?")>=0)
    {
        url+="&t="+randomNumber(1,100000);
    }
    else
    {
       url+="?t="+randomNumber(1,100000);
    }
	 // alert("guid:"+guid);
     var json = {
				"guid":guid
			};

		$.ajax({
	        type: "POST",
	        dataType: "json",
	        async: false,
	        url: url,
	        data: {"json":JSON.stringify(json)},
	        cache: false,
	        beforeSend: function () {
	        	// alert('beforeSend');
	            return true;
	        },
	        success: function (data) {
	        	  // alert(JSON.stringify(protein_data));
	        	  for(var par in data){
	        		  // param_name":"werwe", "parameter":"rew"
	        		  getCustomActionValues(data[par].param_name,data[par].parameter);
	        	  }
	        	 return true;
	        },
	        error: function () {
	        	// alert('error');
	        	 return true;
	        }
		});
 }
 
 // 一般用于ajax调用，返回路径的返回值
  function json_url(purl,id,fun_obj){
 
  	if(purl.indexOf("?")>=0)
    {
        purl+="&t="+randomNumber(1,100000);
    }
    else
    {
       purl+="?t="+randomNumber(1,100000);
    }
 	    var json = {
	 	   "parent_id":id
	    };
 
		$.ajax({
	        type: "POST",
	        dataType: "json",
	        async: false,
	        url: purl,
	        data: {"json":JSON.stringify(json)},
	        cache: false,
	        // beforeSend: function() {
 	         // return false;
	        // },
	        success: function(data) {
	        	 fun_obj(JSON.stringify(data));
	        	 return true;
	        },
	        error: function() {
	        	 return false;
	        }
		});
}
 
  function menu_json_url(purl,id,user_id,fun_obj){
 
 	    var json = {
	 	   "parent_id":id,
	 	   "user_id":user_id
	    };
 
		$.ajax({
	        type: "POST",
	        dataType: "json",
	        async: false,
	        url: purl,
	        data: {"json":JSON.stringify(json)},
	        cache: false,
	        // beforeSend: function() {
 	         // return false;
	        // },
	        success: function(data) {
	        	 fun_obj(JSON.stringify(data));
	        	 return true;
	        },
	        error: function() {
	        	 return false;
	        }
		});
}




// 加载loading效果
function completeDefalut()
{		
	// 获取浏览器页面可见高度和宽度
	var _PageHeight = document.documentElement.clientHeight,
	    _PageWidth = document.documentElement.clientWidth;
	// 计算loading框距离顶部和左部的距离（loading框的宽度为215px，高度为61px）
	var _LoadingTop = _PageHeight > 60 ? (_PageHeight - 60) / 2 : 0,
	    _LoadingLeft = _PageWidth > 120 ? (_PageWidth - 120) / 2 : 0;
	// 在页面未加载完毕之前显示的loading Html自定义内容
	var _LoadingHtml = '<div id="loadingDiv" style="position:absolute;left:0;width:100%;height:' + _PageHeight + 'px;top:0;background:#ccc;opacity:0.5;filter:alpha(opacity=50);z-index:10000;"><div style="position: absolute; cursor1: wait; left: ' + _LoadingLeft + 'px; top:' + _LoadingTop + 'px; width: auto;width:166px; height: 140px; line-height: 140px; padding-left: 50px; padding-right: 5px; background:  url(/hdca/bootstrap/images/loading.gif) no-repeat scroll 5px 10px; color: #696969; font-family:\'Microsoft YaHei\';"></div></div>';
	// 呈现loading效果
	// document.write(_LoadingHtml);
	$("body").append(_LoadingHtml);
} 

 // 移除loading效果
function completeLoading() {
    if (document.readyState == "complete") {
      	// var loadingMask = document.getElementById('loadingDiv');
        // loadingMask.parentNode.removeChild(loadingMask);
        $("#loadingDiv").remove();
    }
 }
 

 
 
 /***************************************************************************
	 * function ajax_table_save(path){ //var path=$("#_path").val(); var params =
	 * $.getParamsByValue("save","true"); //
	 * alert("params:"+JSON.stringify(params)); //return false; if(params!=''){
	 * $.ajax({ type: "POST", dataType: "json", async: false, url: path, protein_data:
	 * {"json":JSON.stringify(params)}, cache: false, beforeSend: function () {
	 * return true; }, success: function (protein_data) { //alert("protein_data
	 * json:"+JSON.stringify(protein_data)); //alert_base(protein_data.message());
	 * if(isNull(protein_data.url)) { alert_base(protein_data.message); } else {
	 * alert_base(protein_data.message,protein_data.url); } }, error: function () {
	 * alert("json异常"); } }); } }
	 **************************************************************************/

/* url返回js对象 ，并返js对象 */
function ajax_url_data(path,params){
	/*if(path.indexOf("?")>=0)
    {
        path+="&t="+randomNumber(1,100000);
    }
    else
    {
       path+="?t="+randomNumber(1,100000);
    }*/
    //alert("path:"+path+" params:"+JSON.stringify(params));
	var temp = {};// 空对象
	$.ajax({
        type: "POST",
        dataType: "json",
        async: false,
        url: path,
        data: {"json":JSON.stringify(params)},
        cache: false,
		// processData: false,
		// contentType: false,
        beforeSend: function () {
            return temp;
        },
        success: function (data) {
        	// console.log(protein_data)
        	temp = data;
        },
        error: function () {
        	//alert("json异常:"+path);
            alert(data)
        }
	});
 
	return temp;

}
/*
 	异步请求
 */
function ajax_url_data_async(path,params,beforeSend,success,error){
	if(path.indexOf("?")>=0)
    {
        path+="&t="+randomNumber(1,100000);
    }
    else
    {
       path+="?t="+randomNumber(1,100000);
    }
    // alert("path:"+path+" params:"+JSON.stringify(params));
	var temp = {};// 空对象
	$.ajax({
        type: "POST",
        dataType: "json",
        async: true,
        url: path,
        data: {"json":JSON.stringify(params)},
        cache: false,
        beforeSend: beforeSend,
        success: success,
        error: error
	});
 
	return temp;

}



/*  url返回js对象 ，并返js对象
 *  并发
 * */
function ajax_url_thread(path,params,funObj){
    //alert("path:"+path+"  params:"+JSON.stringify(params));
	if(path.indexOf("?")>=0)
    {
        path+="&t="+randomNumber(1,100000);
    }
    else
    {
       path+="?t="+randomNumber(1,100000);
    }
	$.ajax({
        type: "POST",
        dataType: "json",
        async: true,
        url: path,
        data: {"json":JSON.stringify(params)},
        cache: false,
        //beforeSend: function () {
            //return temp;
        //},
        success: function (data) {
        	funObj(data);
        },
        error: function () {
        	//alert("json异常:"+path);
        }
	});

}
//暂停
function sleep(d)
{
   for(var t=Date.now();Date.now()-t <=d;);
}


function GetUrl(path)
{
	if(!isNull(path))
	{
	   return path;// 自定义路径
	}
	var projectName="/itax/";
	var file_extension=".html";
    var url = location.href;
    if(!isNull(url))
    {
       return url.substr(url.indexOf(projectName),(url.indexOf(file_extension)+5));
    }
    return "";
}

// 验证按钮权限是否在用户权限中
function isUserAuth(userAuth,auth)
{
	// alert("userAuth:"+userAuth+" auth:"+auth+"
	// indexof:"+(","+userAuth+",").indexOf(","+auth+","));
    if((","+userAuth+",").indexOf(","+auth+",")>=0)
    {
       return true;
    }
    return false;
}

// 获取用户dg以及auth
function getDgAuthList(menu_path)
{
	var ajax_path_auth =  "/itax/auth/getActionJson.do";
	var params_auth = {
			"menuPath" : menu_path
	};
	var AuthList = ajax_url_data(ajax_path_auth,params_auth);// 权限list
	// alert(JSON.stringify(AuthList));
	return AuthList;
}

// 获取dg 对应的操作权限
function getAuthStrByUserMenuDg(menu_path,dg)
{
	var ajax_path_auth =  "/itax/auth/getAuthStrByUserMenuDg.do";
	var params_auth = {
			"menuPath" : menu_path,
			"dg":dg
	};
	var authStr = ajax_url_data(ajax_path_auth,params_auth);// 权限list
	// alert(JSON.stringify(AuthList));
	return  authStr;
}

//获取该用户对应的操作权限
function getAuthStrByUserMenu(menu_path)
{	
	//alert(menu_path);
	var ajax_path_auth_Set =  "/itax/auth/getAuthStrByUserMenu.do";
	var params_authSet = {
			"menuPath" : menu_path
	};
	var authStr = ajax_url_data(ajax_path_auth_Set,params_authSet);// 权限list
	//alert(JSON.stringify(authStr));
	return  authStr.actionSet;
}
function isContains(str,substr)
{
	return str.indexOf(substr)>=0;
}
/* url返回js对象 ，并返js对象 */
function ajax_url_type(path,params,type){
    // alert("path:"+path+" params:"+JSON.stringify(params));
	if(path.indexOf("?")>=0)
    {
        path+="&t="+randomNumber(1,100000);
    }
    else
    {
       path+="?t="+randomNumber(1,100000);
    }
	var temp = {};// 空对象
	$.ajax({
        type: type,
        dataType: "json",
        async: false,
        url: path,
        cache: false,
        beforeSend: function () {
            return temp;
        },
        success: function (data) {
        	
        	temp = data;
        },
        error: function () {
        	//alert("json异常");
        }
	});
 
	return temp;

}



/*  url返回js对象 ，并返js对象 上传文件传用
 *  
 *  必须引用  public/js/ajaxfileupload.js
 * 
 * */
function ajax_fileUpload(path,params,fileObjId,funObj){
    //alert("path:"+path+"  params:"+JSON.stringify(params));
	//alert("ajax_fileUpload:2222");
    if(path.indexOf("?")>=0)
    {
        path+="&t="+randomNumber(1,100000);
    }
    else
    {
       path+="?t="+randomNumber(1,100000);
    }
	$.ajaxFileUpload({
        type: "post",
        dataType: "json",
        url: path,
        async: false,
        secureuri: false,
        data: {"json":JSON.stringify(params)},
        fileElementId : fileObjId,
        success: function(data,status) {
        	//alert("success protein_data json:"+JSON.stringify(protein_data));
        	funObj(data);
        },
        error: function(data,status,e) {
        	//alert("ajaxFileUpload json异常:"+e);
        }
	});
 
	//return temp;

}

 function randomNumber(min, max) {
	        return Math.floor(Math.random() * (max - min + 1) + min);
};
//获取cookie中保存的值
function getCookie(name){
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg)){
		//alert("----------"+arr[2]);
		return arr[2];
	}else{
		return null;
	}	
}
//设置带过期时间的cookie
function setCookie(name,value,time){
	var strsec = getsec(time);
	var exp = new Date();
	exp.setTime(exp.getTime() + strsec*1);
	document.cookie = name + "="+ value + ";expires=" + exp.toGMTString()+";path=/";
}
function getsec(str){
	//alert(str);
	var str1=str.substring(1,str.length)*1;
	var str2=str.substring(0,1);
	if (str2=="s"){
		return str1*1000;
	}else if (str2=="h"){
		return str1*60*60*1000;
	}else if (str2=="d"){
		return str1*24*60*60*1000;
	}
}


//下拉选方法

function select_load(objmenu,dataJson,init) {
	   var html=[];
	   $('#'+objmenu).html('');
	   if (init=="yes")
	   {
	   		html.push("<option value=\"\">请选择...</option>");
	   }
	  for(var par in dataJson){
		  html.push("<option value=\""+dataJson[par].selectValue+"\">"+dataJson[par].selectName+"</option>");
	  }
	  $('#'+objmenu).html(html.join(''));
}