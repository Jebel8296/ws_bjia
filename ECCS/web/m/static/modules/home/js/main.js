define(function(require, exports, module) {
	// require('/libs/zepto/zepto.min');
	var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
	require('LIBS/sui/js/sm.min');
	require('LIBS/sui/js/sm-extend.min');
	require('public/js/menu');
	require('public/js/ajaxService');

	$.init();
	initCartLen();
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
				var num  = m.respData.data.length; //获取产品个数
				$(".myCartLen").text(num);

			},
			error:function(e){
				$.alert("系统繁忙请稍后再试"+e);
			}
		})
	}
	loginForThird();
function loginForThird(){
	var Request=new UrlSearch(); //实例化
	if(Request.code == '0'){
		/*$.cookie('token',Request.token,{path:'/'});
		$.cookie('uid',Request.user_id,{path:'/'});
		$.cookie('phone',Request.user_name,{path:'/'});*/

		$.fn.cookie('token',Request.token,{path:'/'});
		$.fn.cookie('uid',Request.user_id,{path:'/'});
		$.fn.cookie('phone',Request.user_name,{path:'/'});

	//
	}else{
		//$.toast(Request.msg);
	}
}

	function UrlSearch(){
		var name,value;
		var str= window.location.search; //获取url中"?"符后的字串
		var num=str.indexOf("?");
		if(num != -1){
			str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]
			var arr=str.split("&"); //各个参数放到数组里
			for(var i=0;i < arr.length;i++){
				num=arr[i].indexOf("=");
				if(num>0){
					name=arr[i].substring(0,num);
					value=arr[i].substr(num+1);
					this[name]=value;
				}
			}
		}else{

		}

	}

	// $.ajax({
 //        type:"post",
 //        url:postUrl,
 //        contentType:"application/json",
 //        dataType:"JSON",
 //        data: JSON.stringify({
	// 	    "service": "zm3c.product.query",
	// 	    "channel": "m",
	// 	    "sv": "1.2.300",
	// 	    "cv": "1.4.10",
	// 	    "st": "MD5",
	// 	    "sign": "xxx",
	// 	    "sn": "20160905161845672562",
	// 	    "reqData": {
	// 	        "uid": "2"
	// 	        // "category": 41
	// 	        // "code": "3C4001010001"
	// 	    },
	// 	    "ts": 149203841243
	// 	}),
 //        success:function(msg){
 //          var m =(new Function("","return "+msg))();
 //            if(m.code == 200){
 //               $.toast(m.msg); 
 //               console.log(m);
 //                //首页产品列表
	// 			var productNum = m.respData;  //获取产品个数
	// 			var proHtml="";
	// 			for(i=0; i<productNum.length;i++){

	// 				proHtml += '<li><a href="/modules/propage/html/proPage.html?id='+ productNum[i].procid +'&title='+ productNum[i].disname +'&price='+ productNum[i].price +'&code='+ productNum[i].code +'" class="item-content"><div class="item-media  w40">\
	// 				<img src=""></div><div class="item-inner"><div class="item-title-row"><div class="item-title">'+ productNum[i].name +'</div>\
	// 				</div><div class="item-subtitle">'+ productNum[i].des +'</div><div class="item-text"><span class="color0">'+ productNum[i].price +'</span></div></div></a></li>';			
					
	// 			}
	// 			$(".list-block ul").append(proHtml);

	// 			$(".order_msg h3 span").text('('+productNum+')');
				

 //            }else{
 //              $.toast(m.msg);
 //            }
 //        },
 //        error:function(){
 //          $.alert("系统繁忙请稍后再试");
 //        }
            
 //    });


});


  	
