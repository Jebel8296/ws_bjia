define(function(require, exports, module) {
	var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
	//require('LIBS/sui/js/sm.min');
	//require('LIBS/sui/js/sm-extend.min');
	//require('LIBS/sui/js/sm-city-picker');
	require('public/js/ajaxService');

	$.init();


	// 收货地址信息
	var eid;
	function GetRequest() {   
	    var url = decodeURIComponent( window.location.search ); //获取url中"?"符后的字串
		console.log(url)
	    var theRequest = new Object();   
	    if (url.indexOf("?") != -1) {   
		    var str = url.substr(1);   
		    strs = str.split("&");   
		    for(var i = 0; i < strs.length; i ++) {   
		        theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);   
		    }   
	    }   
	    eid = parseInt(theRequest.id); //收货人
	    $(".orderAddr_Name").val(theRequest.name); //收货人
		$(".orderAddr_area").val(theRequest.cityname); //所在地区
		$(".orderAddr_Msg").val(theRequest.address);  //详细地址
		$(".orderAddr_Tel").val(theRequest.phone);  //手机号码
		$(".orderAddr_Code").val(theRequest.zipcode); //邮政编码
	} 
	GetRequest();

	function saveRecAddress(){
		var oAddrName = $(".orderAddr_Name").val(); //收货人
		var oAddrarea = $(".orderAddr_area").val(); //所在地区
		var oAddrMsg = $(".orderAddr_Msg").val();  //详细地址
		var oAddrTel = $(".orderAddr_Tel").val();  //手机号码
		var oAddrCode = $(".orderAddr_Code").val(); //邮政编码
		//收货人
		if(oAddrName==""){
			$(".orderAddr_Name").next().text("不能为空").show();
			return;
		}else{
			$(".orderAddr_Name").next().hide();
		}

		//所在地区
		if(oAddrarea==""){
			$(".orderAddr_area").next().text("不能为空").show();
			return;
		}else{
			$(".orderAddr_area").next().hide();
		}

		//详细地址
		if(oAddrMsg==""){
			$(".orderAddr_Msg").next().text("不能为空").show();
			return;
		}else{
			$(".orderAddr_Msg").next().hide();
		}

		//手机号码
		// if (!oAddrTel.match(/^1[34578]\d{9}$/)) { 
		// 	$(".orderAddr_Tel").next().text("手机号码格式不正确！").show();
		// 	return;
		// }else{
		// 	$(".orderAddr_Tel").next().hide();
		// }

		//邮政编码
		if (!oAddrCode.match(/^[\d]{6}$/)) { 
			$(".orderAddr_Code").next().text("请填写正确的邮政编码").show();
			return;
		}else{
			$(".orderAddr_Code").next().hide();
		}
			
		console.log(oAddrName,oAddrarea,oAddrMsg,oAddrTel,oAddrCode);

		return true;
	}

	//所在地区
	$("#city-picker").cityPicker({
		toolbarTemplate: '<header class="bar bar-nav">\
		<button class="button button-link pull-right close-picker">确定</button>\
		<h1 class="title">所在地区</h1>\
		</header>'
	});




	// 修改地址
	$('#addrChange').on('click', function () { 

		if(saveRecAddress()){
			var oAddrName = $(".orderAddr_Name").val(); //收货人
			var oAddrarea = $(".orderAddr_area").val(); //所在地区
			var oAddrMsg = $(".orderAddr_Msg").val();  //详细地址
			var oAddrTel = $(".orderAddr_Tel").val();  //手机号码
			var oAddrCode = $(".orderAddr_Code").val(); //邮政编码

			var areaStr = oAddrarea.split(' ');//先按照空格分割成数组
			var province =areaStr[0];
			var city =areaStr[1];
			var area =areaStr[2];

			$.ajax({
		        type:"post",
		        url:postUrl,
		        contentType:"application/json",
		        dataType:"JSON",

		        data: JSON.stringify({
				    "service": "zm3c.express.mod",
				    "channel": "m",
				    "sv": "1.2.300",
				    "cv": "1.4.10",
				    "pn": "xxx",
				    "st": "MD5",
				    "sign": $.fn.cookie('token'),
				    "sn": "20160905161845672562",
				    "reqData": {
				    	"uid": Number( $.fn.cookie('uid')),
				        "eid": eid,
				        "name": oAddrName,
				        "phone": oAddrTel,
				        "zipcode": oAddrCode,
				        "address": oAddrMsg,
				        "province": province,
				        "city": city,
				        "area": area,
				        "def": 0
				    }
		        }),
		        success:function(msg){
		          var m =(new Function("","return "+msg))();
		            if(m.code == 200){
		               $.toast(m.msg);
						window.history.go(-1);
						//setTimeout('window.location.href = "/modules/aftersales/html/subServiceOrder.html"', 1000);
		            }else if(m.code==506){
						$.toast("请重新登录");
						setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
					}else{
		              $.toast(m.msg);
		            }
		        },
		        error:function(){
		          $.alert("系统繁忙请稍后再试");
		        }           

		
      		});
		
		}
		
	})
	

	
});