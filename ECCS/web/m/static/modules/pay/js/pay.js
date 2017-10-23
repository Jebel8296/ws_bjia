define(function(require, exports, module) {
  var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
  require('LIBS/sui/js/sm.min');
  require('LIBS/sui/js/sm-extend.min');
  require('public/js/ajaxService');
  $.init();


  // 金额


  	var url = decodeURIComponent( window.location.search ); //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
	    var str = url.substr(1);
	    strs = str.split("&");
	    for(var i = 0; i < strs.length; i ++) {
	        theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
	    }
    }
    var code = theRequest.code; //订单编号
    var pay = theRequest.pay; //支付金额

	console.log(pay);
    $(".orderAmt").text(pay);
  	$(".orderNum").text(code);
	$(".pro_Total span").text(pay);
	$(".pro_Payable span").text(pay);
	$('.payOrder_btn').on('click', function(){

		$.ajax({
	        type:"post",
	        url:postUrl,
	        contentType:"application/json",
	        dataType:"JSON",
	        data: JSON.stringify({
			    "service": "zm3c.payment.jumppay",
			    "channel": "m",
			    "sv": "1.2.300",
			    "cv": "1.4.10",
			    "pn": "xxx",
			    "st": "MD5",
			    "sign": $.fn.cookie('token'),
			    "sn": dateNum,
			    "reqData": {
			        "uid": Number($.fn.cookie('uid')),
			        "ordercode": code,
			        "payfee": pay +"",
			        "channel": "m"
			    }
			}),
	        success:function(msg){
	          var m =(new Function("","return "+msg))();
	            if(m.code == 200){

	               setTimeout('window.location.href = "'+ m.respData.payurl +'"', 5000);
	            }else{
	              $.toast(m.msg);
	            }
	        },
	        error:function(){
	          $.alert("系统繁忙请稍后再试");
	        }
	    });

	});

});