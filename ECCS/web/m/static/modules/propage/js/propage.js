define(function(require, exports, module) {
	// require('/libs/zepto/zepto.min');
	// var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
	require('LIBS/sui/js/sm.min');
	require('LIBS/sui/js/sm-extend.min');
	require('public/js/menu');
	require('public/js/ajaxService');

	$.init();
	initCartLen();
	//var mDataColor=[];
	//var mDataModal=[];
	var mDataModal=[];
	function initCartLen(){
		$.ajax({
			type: "post",
			url: postUrl,
			contentType: "application/json",
			dataType: "JSON",
			data: JSON.stringify({
				"service": "zm3c.cart.list",
				"channel": "m",
				"sv": "1.2.300",
				"cv": "1.4.10",
				"st": "MD5",
				"sign": $.fn.cookie('token'),
				"sn": dateNum,
				"reqData": {
					"uid": Number($.fn.cookie('uid'))
				},

				"ts": 149203841243
			}),
			success:function(m){

				var m = JSON.parse( m );

				var productArray=m.respData.data
				for(var i=0;i<productArray.length;i++){
					mDataModal.push({color:productArray[i].color,modal:productArray[i].model});
				}

				var num  = m.respData.data.length; //获取产品个数
				$('.color1').text(num);
				console.log(mDataModal);

			},
			error:function(e){
				$.alert("系统繁忙请稍后再试"+e);
			}
		})
	}




	//添加到购物车		
	var imgPath = '/modules/propage/img/A750.jpg';
	var imgPath2 = '/modules/propage/img/c750.jpg';

	// 获取产品id
	function GetRequest() {   
	   var url= decodeURIComponent(window.location.search); //获取url中"?"符后的字串

	   var theRequest = new Object();   
	   var module=[];
	   var color=[];
	   if (url.indexOf("?") != -1) {   
	      var str = url.substr(1);   
	      strs = str.split("&");   
	      for(var i = 0; i < strs.length; i ++) {   
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);   
	      }
	   }

		var pdetail = "<div>" +
			"<dl class='standard'><dt>主要芯片</dt>" +
			" <dd> <label>芯片</label> MTK MT2503 </dd>" +
			" <dd><label>Memory</label>128MB</dd>" +
			"<dd><label>GPS</label>支持</dd>" +
			"<dd> <label>G-sensor</label>支持 </dd>" +
			"<dd> <label>心率</label>支持 </dd> " +
			"<dd> <label>蓝牙</label>3.0 </dd>" +
			"<dd><label>NFC</label>支持</dd>" +
			"<dd><label> sensor-hub</label>支持 </dd> </dl> " +
			"<dl class='standard'> <dt>外围配置</dt> " +
			"<dd> <label>屏</label>1.3  240*240 TFT纯圆屏 </dd> " +
			"<dd> <label>TP</label>电容屏 </dd> " +
			"<dd> <label>GSM</label>900/1800 </dd> " +
			"<dd> <label>GPS</label>支持 </dd>" +
			"<dd> <label>NFC公交一卡通</label>支持 </dd>" +
			"<dd> <label>SIM卡</label>支持可取卡 </dd> " +
			"<dd> <label>USB</label>磁吸式 </dd> " +
			"<dd> <label>充电接口</label>磁吸式 </dd> " +
			"<dd> <label>电池</label>大于450mAh </dd> " +
			"<dd> <label>马达</label>支持 </dd> " +
			"<dd> <label>MIC</label>支持 </dd> " +
			"<dd> <label>喇叭</label>支持 </dd> " +
			"</dl></div>"
		console.log(theRequest.code)

	   // return theRequest;  
		var proItemHtml = '<div class="pro_item white-bg" style="min-height: auto">' +
			'<h1 data-title="'+ theRequest.title +'" data-id="'+ theRequest.id +'" data-code="'+ theRequest.code +'"class="f-14">'+ theRequest.title +'</h1>' +
			'<p data-num="'+ theRequest.price +'" class=" redColor f-16" style="padding: 0;margin-bottom: .5rem;margin: 0;"> 即将发售 </p>' +
			'<div id="selmod" class="sel"><h5>选择型号：</h5><a href="javascript:;" data-selmod="A100" class="test1">A100</a><a href="javascript:;" data-selmod="C100" class="test2">C100</a></div>' +
			'<div id="proColor" class="sel"><h5>选择颜色：</h5><a href="javascript:;"  data-color="01" class="cur">全黑</a><a href="javascript:;"  data-color="02">白蓝</a></div>' +
			'</div><div class=" white-bg">' +
			'<div class="buttons-tab">' +
			'<a href="#tab1" class="tab-link active button">产品概述</a><!--a href="#tab2" class="tab-link button"> </a--></div>' +
			'<div class="content-block"><div class="tabs"><div id="tab1" class="tab active"><div class="content-block dataIntr" style="padding:0"><p>'+pdetail+'</p></div></div><div id="tab2" class="tab"><div class="content-block dataPara"><p>产品参数</p></div></div></div></div></div>';
		$(".page_item").append(proItemHtml);

		if(theRequest.code==40){
			$(".test1").addClass("cur");
		}else if(theRequest.code==41){
			$(".test2").addClass("cur");
			$(".t_banner img").attr('src',imgPath2);
		}
	} 
	GetRequest();


	//选择型号
	$('.sel a').on('click', function () {		
		$(this).addClass("cur").siblings("a").removeClass();
		 modelNum =$("#selmod .cur").data("selmod");
		if(modelNum == "A100"){
           $(".t_banner img").attr('src',imgPath);
		}else{
			$(".t_banner img").attr('src',imgPath2);
		}
		 selColor = $("#proColor .cur").data("color");

	}); 

	// 加入购物车
	var count=0;
	var proCode = $("[data-code]").data("code");
	var proTitle = $("[data-title]").data("title");
	var proPrice = $("[data-num]").data("num");
	var modelNum = $("#selmod .cur").data("selmod");

	//var selColor = $("#proColor .cur").data("color");


	$('#addCart').on('click', function () {
		selColor = $("#proColor .cur").data("color");
		// var proNum = $(".icon-cart span").text();
		var proNum = parseInt($(".icon-cart span").text());
		var code = '3C'+ proCode + modelNum +'0'+ selColor + '0001';
		var mColor=selColor==1?"全黑":"白蓝";
		//console.log(code)

		if($.fn.cookie('uid') !=null){
			$.ajax({
				type:"post",
				url:postUrl,
				contentType:"application/json",
				dataType:"JSON",
				data: JSON.stringify({
					"service": "zm3c.cart.add",
					"channel": "m",
					"sv": "1.2.300",
					"cv": "1.4.10",
					"pn": "xxx",
					"st": "MD5",
					"sign": $.fn.cookie('token'),
					"sn": dateNum,
					"reqData": {
						"uid": Number( $.fn.cookie('uid')),
						"code": code,
						"total": 1
					}
				}),

				success:function(msg){
					var m =(new Function("","return "+msg))();
					if(m.code == 200){
						$.toast("加入购物车成功!");
						initCartLen();
						//if(mDataColor.indexOf(mColor)!==-1&&mDataModal.indexOf(modelNum)!==-1){
						//
						//}
						$.toast("加入购物车成功!");
						var obj={
							color:mColor,
							modal:modelNum
						}
						for(var i=0;i<mDataModal.length;i++){
							if(JSON.stringify(obj)!==JSON.stringify(mDataModal[i])){
								$(".icon-cart span").text(proNum+1);
							}
						}

					}else{
						$.toast(m.msg);
					}
				},
				error:function(){
					$.alert("系统繁忙请稍后再试");
				}
			})
		}else{
			window.location.href = '/modules/passport/html/login.html';
		}

	})	

  	//立即购买
	$('#buyNow').on('click', function () {
		selColor = $("#proColor .cur").data("color");
		var code = '3C'+ proCode + modelNum +'0'+ selColor + '0001';
		// console.log(proTitle,proPrice,modelNum,selColor);
		// window.location.href = '/modules/purchase/html/order_Confirm.html?proTitle='+proTitle+'&proPrice='+proPrice+'&modelNum='+modelNum+'&selColor='+selColor;
		if($.fn.cookie('uid')!=null){
			window.location.href = '/modules/purchase/html/order_Confirml.html?id='+ Number($.fn.cookie('uid'))+'&code='+code;
		}else{
			window.location.href = '/modules/passport/html/login.html';
		}

	})



		$('#toCart').on('click', function () {
			var uid = $.fn.cookie('uid');
			console.log(uid)
			if(uid != null && uid != ""){
				setTimeout('window.location.href = "/modules/purchase/html/myCart.html"', 1000);
			}else{
				setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
			}

		})

});

