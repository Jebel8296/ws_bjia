define(function(require, exports, module) {
	var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
	//require('LIBS/sui/js/sm.min');
	//require('LIBS/sui/js/sm-extend.min');
	//require('LIBS/sui/js/sm-city-picker');
	require('public/js/ajaxService');

	$.init();

	//address_add.html 
	// 保存收货地址

	$(document).on('click','.check',function(){
		var consigneeObj={}
		var eid=$(this).attr('title');
		var zic=$(this).attr('data-name');
		console.log(zic);
		var consignee=$(this).parent().next().children(".consignee").text();
		var phoneNum=$(this).parent().next().children(".phoneNum").text();
		var siteM=$(this).parent().next().children(".siteM").text();
		$(this).css('background','#FF8A0C');
		consigneeObj['eid']=eid;
		consigneeObj['consignee']=consignee;
		consigneeObj['phoneNum']=phoneNum;
		consigneeObj['siteM']=siteM;
		consigneeObj['zip']=zic;
		console.log(consigneeObj);

		window.localStorage.setItem('consigneeObj',JSON.stringify(consigneeObj));
		setTimeout(function(){
			window.history.go(-1);
		},0)
	})
	 $.ajax({
		 type: "post",
		 url: postUrl,
		 contentType: "application/json",
		 dataType: "JSON",
		 data: JSON.stringify({
			 "service": "zm3c.express.list",
			 "channel": "m",
			 "sv": "1.2.300",
			 "cv": "1.4.10",
			 "pn": "xxx",
			 "st": "MD5",
			 "sign": $.fn.cookie('token'),
			 "sn": dateNum,
			 "reqData": {
				 "uid": Number($.fn.cookie('uid'))

			 }
		 }),
	// <input type="radio" value="" id="1'+ i +'" name="cart-pro" class="regular-radio" data-status="'+ m.respData[i].status +'">\
	//<label class="addr-sel-this"><em></em>默认地址</label>\
	//<span>默认地址</span>
	success: function (msg) {

			 msg=JSON.parse(msg);
			 if(msg.code == 200){
				 locationData= msg.respData;
				 for(var i=0;i<locationData.length;i++){
					 var locationList='<div class="sel_addr">' +
						 '<input type="radio" value="" id="1'+ i +'" name="cart-pro" class="regular-radio" data-status="'+ locationData[i].status +'">' +
						 '<label class="addr-sel-this"><em style="top:20px" data-name="'+locationData[i].zipcode+'" title="'+locationData[i].eid+'" class="check"></em></label>'+
						 '<p style="width: 90%;margin-left: 0.5rem">' +
						 '收货人：<a class="consignee">'+ locationData[i].name +'<a> </br>' +
						 '联系电话：<a class="phoneNum">'+locationData[i].phone+'</a> <br/>' +
						 '地　址：<a class="siteM">'+ locationData[i].provname +' '+ locationData[i].areaname +''+ locationData[i].cityname +' '+ locationData[i].address +'</a></p>'+
						 '</label>'+
						 '</div>'
					 $(".list-block").append(locationList);
				 }

			 }
		 },
		 error:function(e){
			 $.alert("系统繁忙请稍后再试");

		 }


	 })
	function saveRecAddress(){
		var oAddrName = $(".orderAddr_Name").val(); //收货人
		var oAddrarea = $(".orderAddr_area").val(); //所在地区
		var oAddrMsg = $(".orderAddr_Msg").val();  //详细地址
		var oAddrTel = $(".orderAddr_Tel").val();  //手机号码
		var oAddrCode = $(".orderAddr_Code").val(); //邮政编码
		console.log(oAddrTel);
		//收货人
		if(oAddrName==""){
			$(".orderAddr_Name").next().text("不能为空").show();
			return;
		}else if(oAddrName.length>30){
			$(".orderAddr_Name").next().text("收件人长度不能大于30个字符！").show();
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
		if(oAddrTel==""){
			$(".orderAddr_Tel").next().text("请输入手机号！").show();
		}else if (!oAddrTel.match(/^1[34578]\d{9}$/)) {
			$(".orderAddr_Tel").next().text("手机号码格式不正确！").show();
			return;
		}else{
			$(".orderAddr_Tel").next().hide();
		}

		//邮政编码
		if(oAddrCode==""){
			$(".orderAddr_Code").next().text("请填写邮政编码").show();
			return;
		}else if (!oAddrCode.match(/^[\d]{6}$/)) {
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

	// 设置默认地址
	var def = 0;
	$(".sel-this").click(function(){		
	    var value = $("#defaultAddr").prop("checked");
	    if(!value){
	    	$(this).siblings("input").prop("checked",true);
	        $(this).find("em").addClass("checked");
	        def = 1;
	    }else{
	    	$(this).siblings("input").prop("checked",false);
	        $(this).find("em").removeClass();
	        def = 0;
	    }
		 
    })
	//管理界面
	$(document).on('click','#addrManage',function(){
		window.location.href="/modules/purchase/html/address.html"
	})

	// 保存地址
	$('.addrSave').on('click', function () { 
		var oAddrName = $(".orderAddr_Name").val(); //收货人
		var oAddrarea = $(".orderAddr_area").val(); //所在地区
		var oAddrMsg = $(".orderAddr_Msg").val();  //详细地址
		var oAddrTel = $(".orderAddr_Tel").val();  //手机号码
		var oAddrCode = $(".orderAddr_Code").val(); //邮政编码
	 	

 
		var areaStr = oAddrarea.split(' ');//先按照空格分割成数组
		// areaStr.pop();//删除数组最后一个元素
		// areaStr = areaStr.join(' ');//在拼接成字符串
		var province =areaStr[0];
		var city =areaStr[1];
		var area =areaStr[2];		 

		if(saveRecAddress()){
			
			$.ajax({
				type:"post",
		        url:postUrl,
		        contentType:"application/json",
		        dataType:"JSON",
		        data: JSON.stringify({
				    "service": "zm3c.express.add",
          			"channel": "m",
				    "sv": "1.2.300",
				    "cv": "1.4.10",
				    "pn": "xxx",
				    "st": "MD5",
				    "sign": $.fn.cookie('token'),
				    "sn": dateNum,
				    "reqData": {
				        "uid": Number( $.fn.cookie('uid')),
				        "name": oAddrName,
				        "phone": oAddrTel,
				        "zipcode": oAddrCode,
				        "address": oAddrMsg,
				        "province": province,
				        "city": city,
				        "area": area,
				        "def": def
				    }
				}),
				success:function(msg){
		          var m =(new Function("","return "+msg))();

		          console.log(m)
		            if(m.code == 200){
		                setTimeout(function(){
							window.history.go(-1);
						}, 1000);
		            }else if(m.code==506){
						$.toast("请重新登录");
						setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
					}else{
		              $.toast(m.msg);
		            }
		        },
		        error:function(){
		          $.alert("系统繁忙请稍后再试");
		          console.log(data.reqData);
		        }
			})
		}

	})
 	
});