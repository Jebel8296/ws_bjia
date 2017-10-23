define(function(require, exports, module) {
	var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
	require('LIBS/sui/js/sm.min');
	require('LIBS/sui/js/sm-extend.min');
	require('public/js/ajaxService');

	$.init();

	var today = getToday();
	$("#datetime-picker").calendar({
		    //value: ['2015-12-05']
		value:[today]
		});

	$(function(){
		$(".yzm").click();
	});

var yzCo = "";
 	// 确认提交
    $(document).on('click','#subBtn', function () {
    	var deviceId = $("#deviceId").val();
		//var deviceId = "10223003C4001010002";
    	var dataTime = Date.parse($(".dataTime").val());
		var yzmCode = $("#yzmCode").val().toUpperCase();
    	var reg =  /^[0-9a-zA-Z]{1,19}$/;
		var subSer = true;
		if(deviceId==""){
			$(".deviceId").text("设备编号不能为空！").show();
			subSer = false;
		}else{
			$(".deviceId").hide()
		}
		if(deviceId==""){
			$(".yzmCode").text("验证码不能为空！").show();
			subSer = false;
		}else{
			$(".yzmCode").hide();
		}
		if(!subSer){
			return;
		}
			$.ajax({
				type:"post",
				url:postUrl,
				contentType:"application/json",
				dataType:"JSON",
				data: JSON.stringify({
					"service": "zm3c.aftersale.device",
					"channel": "m",
					"sv": "1.2.300",
					"cv": "1.4.10",
					"pn": "xxx",
					"st": "MD5",
					"sign": $.fn.cookie('token'),
					"sn": dateNum,
					"reqData": {
						"uid": Number( $.fn.cookie('uid')),
						"devicescode": deviceId ,
						"date": dataTime,
						"code": yzmCode
					}
				}),
				success:function(msg){
					var m =(new Function("","return "+msg))();
					if(m.code == 200){
						if(yzCo !=yzmCode){
							$.toast("验证码不正确，请重新输入");
							return;
						}
						if(!(deviceId).match(reg)){
							$.toast("输入设备编号有误，请重新输入");
						}else{
							setTimeout(window.location.href = '/modules/aftersales/html/serviceorders.html?devicescode='+ deviceId +' ',3000);
						}

					}else if(m.code == 506){
						$.toast("请重新登录");
						setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
					}else{
						$.toast(m.msg);
					}
				},
				error:function(){
					$.alert("系统繁忙请稍后再试");
				}
			})

    	

 	})
	getIMGCode();

	function getIMGCode(){
		$.ajax({
			type:"post",
			url:postUrl,
			contentType:"application/json",
			dataType:"JSON",
			data: JSON.stringify({
				"service": "zm3c.generate.code",
				"channel": "m",
				"sv": "1.2.300",
				"cv": "1.4.10",
				"pn": "xxx",
				"st": "MD5",
				"sign": $.fn.cookie('token'),
				"sn": dateNum,
				"reqData": {
					"uid":Number( $.fn.cookie('uid'))
				}
			}),
			success:function(msg){
				var m =(new Function("","return "+msg))();
				if(m.code == 200){
					yzCo = m.respData.code;
					var picBase64 = "data:image/jpeg;base64," + m.respData.url;
					//$.alert(picBase64);
					$('.yzm').attr('src',picBase64);
				}else{
					$.toast(m.msg);
				}
			},
			error:function(){
				$.alert("系统繁忙请稍后再试");
			}
		})


	}

	$(".yzm").on('click',function(){
		getIMGCode();
		/*
		//表单字段
		$.ajax({
			type:"post",
			url:postUrl,
			contentType:"application/json",
			dataType:"JSON",
			data: JSON.stringify({
				"service": "zm3c.generate.code",
				"channel": "m",
				"sv": "1.2.300",
				"cv": "1.4.10",
				"pn": "xxx",
				"st": "MD5",
				"sign": "xxx",
				"sn": "20160905161845672562",
				"reqData": {
					"uid": 39
				}
			}),
			success:function(msg){
				var m =(new Function("","return "+msg))();
				if(m.code == 200){
					console.log(m);
					$.toast(m);
					//$scope.codeData=data.respData;
					//$scope.codeS = $scope.codeData.code;
					//$scope.scode ="data:image/jpeg;base64," +  $scope.codeData.url;

				}else{
					$.toast(m.msg);
				}
			},
			error:function(){
				$.alert("系统繁忙请稍后再试");
			}
		})*/
	})

});