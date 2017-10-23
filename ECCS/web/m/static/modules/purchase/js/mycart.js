define(function(require, exports, module) {
	var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
	require('LIBS/sui/js/sm.min');
	require('LIBS/sui/js/sm-extend.min');
	require('public/js/menu');
	require('public/js/ajaxService');

	$.init();

	//myCart.html 
	// $(document).on("pageInit", "#page-myCart", function(e, id, page){
	
 	//遍历购物车中产品
 	function proListMyCart(){
 		$.ajax({
	        type:"post",
	        url:postUrl,
	        contentType:"application/json",
	        dataType:"JSON",
	        data: JSON.stringify({
			    "service": "zm3c.cart.list",
			    "channel": "m",
			    "sv": "1.2.300",
			    "cv": "1.4.10",
			    "st": "MD5",
			    "sign": $.fn.cookie('token'),
			    "sn": dateNum,
			    "reqData": {
			        "uid": Number( $.fn.cookie('uid'))
			    },

			    "ts": 149203841243
	        }),
	        success:function(m){
	        	var m = JSON.parse( m );
	        	var num  = m.respData.data.length; //获取产品个数

				window.sessionStorage.setItem('myCartLen',num);
					if( !num){
						$(".order_msg").append('<div class="no_pro">您的购物车里没有商品</div>')
					}

				var cartItem = m.respData.data;
				for(i=0;i<num;i++){
					m.respData.data[i].prodprice=toDecimal2(m.respData.data[i].prodprice)
					var proHtml = '<li data-id="'+ cartItem[i].instanceid +'"><div class="item-content" data-id="'+cartItem[i].instanceid+'"><div class="item-media"><input type="checkbox" data-check="'+  m.respData.data[i].checked +'" value="" id="" name="cart-pro" class="regular-checkbox"><label for="" class="sel-this"><em></em></label><img src="/modules/public/img/3/3C40A100010001.png"></div><div class="item-inner"><div class="item-title-row item-cartList"><div class="item-title mcDf">'+ m.respData.data[i].prodname +'</div><div class="item-after item-mcNum mcDf">'+ m.respData.data[i].prodtotal +'</div><div class="add-box mcEDf posBOX"><a href="javascript:void(0);" class="num-minus">-</a><input autocomplete="off" type="number" class="itxt color-0" value="'+ m.respData.data[i].prodtotal +'"><a href="javascript:void(0);" class="num-add">+</a></div></div><div class="item-subtitle mcDf">型号：B100 颜色：蓝白</div><div id="item-pos" class="item-text "><span class="mcEDf"  style="margin-right: 2rem;padding-top: 0.3rem">蓝白　</span><span class="color0"">'+ m.respData.data[i].prodprice +'</span></div></div><a class="item-after del_icon mcEDf confirm-ok">删除</a></div></li>';
					
					$("#cart-list ul").append(proHtml);

					var selValue = $("li").eq(i).find("input").data("check");
					if(selValue){
						$("li").eq(i).find("input").prop('checked', true); 
						$("li").eq(i).find("em").addClass("checked");
						proMCartTotalPrice();
						$('#J_SelectAll dfn').show();
					}
					 
				}
				var sel = $('#cart-list input:checked').length;
	    		var allNum = $("#cart-list li").length;
	    		if(sel==allNum){
				 	$("em").addClass("checked");
					$('#cart-list input').prop('checked', true); 
				}

				$(".order_msg h3 span").text('('+num+')');
				$(".item-cartListEdit,.confirm-ok").hide(); 
	            if(m.code == 200){

	                
	            }else if(m.code==506){
					$.toast("请重新登录");
					setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
				}else{
	              $.toast(m.msg);
	            }
				function toDecimal2(x) {
					var f = parseFloat(x);
					if (isNaN(f)) {
						return false;
					}
					var f = Math.round(x*100)/100;
					var s = f.toString();
					var rs = s.indexOf('.');
					if (rs < 0) {
						rs = s.length;
						s += '.';
					}
					while (s.length <= rs + 2) {
						s += '0';
					}
					return s;
				}
	        },
	        error:function(){
	          $.alert("系统繁忙请稍后再试");
	        }
		            
		});			
	};
	proListMyCart();
	function toDecimal2(x) {
		var f = parseFloat(x);
		if (isNaN(f)) {
			return false;
		}
		var f = Math.round(x*100)/100;
		var s = f.toString();
		var rs = s.indexOf('.');
		if (rs < 0) {
			rs = s.length;
			s += '.';
		}
		while (s.length <= rs + 2) {
			s += '0';
		}
		return s;
	}
	//btn 编辑
	$('.edit_or').on('click', function () {
	  	var txt = $(".edit_or").text();
	  	if(txt ==="编辑"){
	  		$(this).text("完成");
	  		$(".mcDf").hide(300);
	  		$(".mcEDf").show(300);
			$(".color0").css(" display","none");

	  	}else{
	  		$(this).text("编辑");
	  		$(".mcDf").show(300);
	  		$(".mcEDf").hide(300);
			$(".color0").css(" display","block");
	  	}
	  		
	});
				
	//计算 产品 总价格
	function proMCartTotalPrice(){	
		var total = 0;
		var checkedLength = $('#cart-list input:checked').length;
		//console.log(checkedLength);
		for( var i=0; i<checkedLength; i++ ){
			var onePro =$('#cart-list input:checked').eq( i ).parents().find('.color0').text();
			var proNum =$('#cart-list input:checked').eq( i ).parents().find('.itxt').val();
			//console.log(onePro+"::"+proNum)
			total += (toDecimal2(parseFloat( onePro)) *toDecimal2(parseFloat(  proNum )));

			var total1=toDecimal2(parseFloat(total));

		}
		//console.log(total);
				if(total1){
					$("#J_SelectAll dfn").text('￥'+total1);
				}


	};

	//购物车商品  加减
	$(document).on('click','.num-add', function () {
		var value = $(this).siblings(".itxt").val() ;
	  	value++;
	  	$(this).siblings(".itxt").attr("value",value);
	  	$(this).parent().siblings(".item-mcNum").text(value);
	  	proMCartTotalPrice();
		var instanceid = $(this).parents(".item-content").data("id");
		$.ajax({
		        type:"post",
		        url:postUrl,
		        contentType:"application/json",
		        dataType:"JSON",
		        data: JSON.stringify({
				    "service": "zm3c.cart.incr",
				    "channel": "m",
				    "sv": "1.2.300",
				    "cv": "1.4.10",
				    "pn": "xxx",
				    "st": "MD5",
				    "sign": $.fn.cookie('token'),
				    "sn": dateNum,
				    "reqData": {
				        "uid": Number( $.fn.cookie('uid')),
				        "instanceid":parseInt(instanceid)
				    }
				}),
		        success:function(m){
		        	var m = JSON.parse( m );
		        	if(m.code == 200){
		        		value = m.respData.total;

		            }else if(m.code==506){
						$.toast("请重新登录");
						setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
					}else{
		              
		            }
		   
		        },
		        error:function(){
		          $.alert("系统繁忙请稍后再试");
		        }
		            
		});			 	   
	});


	$(document).on('click','.num-minus', function () {
	  	var value = $(this).siblings(".itxt").val();
	  	value--;
	  	if(value>0){
	  		$(this).siblings(".itxt").attr("value",value);
	  		$(this).parent().siblings(".item-mcNum").text(value);
	  		var check = $(this).parents(".item-content").find("em").hasClass("checked");
		  	if(check){
				proMCartTotalPrice();
		  	}
	  	}else{
	  		return;
	  	}

	  	var instanceid = $(this).parents(".item-content").data("id");

		$.ajax({
		        type:"post",
		        url:postUrl,
		        contentType:"application/json",
		        dataType:"JSON",
		        data: JSON.stringify({
				    "service": "zm3c.cart.decr",
				    "channel": "m",
				    "sv": "1.2.300",
				    "cv": "1.4.10",
				    "pn": "xxx",
				    "st": "MD5",
				    "sign": $.fn.cookie('token'),
				    "sn": dateNum,
				    "reqData": {
				        "uid": Number( $.fn.cookie('uid')),
				        "instanceid": instanceid
				    }
				}),
		        success:function(m){
		        	var m = JSON.parse( m );
		        	if(m.code == 200){
		        		value = m.respData.total;
		            }else if(m.code==506){
						$.toast("请重新登录");
						setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
					}else{
						$.toast(m.msg)

		            }
		        },
		        error:function(){
		          $.alert("系统繁忙请稍后再试");
		        }
		            
		});	
	});
	
	// input值修改
	$(document).on('change','.itxt', function () {
		var value = $(this).val() ;
	  	$(this).attr("value",value);
	  	$(this).parent().siblings(".item-mcNum").text(value);
	  	

		var insid = $(this).parents(".item-content").data("id");
		console.log(insid);
		$.ajax({
		        type:"post",
		        url:postUrl,
		        contentType:"application/json",
		        dataType:"JSON",
		        data: JSON.stringify({
				    "service": "zm3c.cart.incr",
				    "channel": "m",
				    "sv": "1.2.300",
				    "cv": "1.4.10",
				    "pn": "xxx",
				    "st": "MD5",
				    "sign": $.fn.cookie('token'),
				    "sn": dateNum,
				    "reqData": {
				        "uid": Number( $.fn.cookie('uid')),
				        "instanceid":insid
				    }
				}),
		        success:function(m){
		        	var m = JSON.parse( m );
		        	if(m.code == 200){
		        		value = m.respData.total;
		            }else if(m.code==506){
						$.toast("请重新登录");
						setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
					}else{
		              
		            }
		   
		        },
		        error:function(){
		          $.alert("系统繁忙请稍后再试");
		        }
		            
		});	
	});	

	function noPro(){
		var num =$("#cart-list li").length;
		if( !num){
	    $(".order_msg").append('<div class="no_pro">您的购物车里没有商品</div>')
	     }
	};

	//是否删除
	$(document).on('click','.confirm-ok', function () {
		var instanceid = $(this).parents("li").data("id");  //获取商品id
		var list =$(this).parent().parent("li");  

      	$.confirm('确定将这个宝贝删除吗?', function () {
      		
      		$.ajax({
		        type:"post",
		        url:postUrl,
		        contentType:"application/json",
		        dataType:"JSON",
		        data: JSON.stringify({
				    "service": "zm3c.cart.del",
				    "channel": "m",
				    "sv": "1.2.300",
				    "cv": "1.4.10",
				    "pn": "xxx",
				    "st": "MD5",
				    "sign": $.fn.cookie('token'),
				    "sn": dateNum,
				    "reqData": {
				        "uid": Number( $.fn.cookie('uid')),
				        "instanceid": instanceid
				    }
				}),
		        success:function(msg){
		          	var m =(new Function("","return "+msg))();
		            if(m.code == 200){

		               $(list).remove(); 
			           noPro();
			           proMCartTotalPrice();
			           var num  = $("#cart-list li").length; //获取产品个数
			           $(".order_msg h3 span").text('('+num+')');
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
         
      	});
  	});

	//点击全选按钮	 	
	$(document).on('click','#J_SelectAll em', function (){     
		var sel = $('#J_SelectAll input:checked').length;
		console.log(sel);
		var allselectValue;  
		if(!sel){
		    $("em").addClass("checked");
		    $('#J_SelectAll dfn').show();
		    $('#cart-list input').prop('checked', true); 
		    allselectValue =$('#cart-list input').prop('checked');

		          
		}else{
		    $("em").removeClass();
		    $("#J_SelectAll dfn").text("").hide();		    		
		    $('#cart-list input').prop('checked', false);	
		    allselectValue =$('#cart-list input').prop('checked'); 	
		}
		proMCartTotalPrice(); 	

		$.ajax({
		        type:"post",
		        url:postUrl,
		        contentType:"application/json",
		        dataType:"JSON",
		        data: JSON.stringify({
				    "service": "zm3c.cart.check",
				    "channel": "m",
				    "sv": "1.2.300",
				    "cv": "1.4.10",
				    "pn": "xxx",
				    "st": "MD5",
				    "sign": $.fn.cookie('token'),
				    "sn": dateNum,
				    "reqData": {
				        "uid": Number( $.fn.cookie('uid')),
				        "instanceid": 0,
				        "checked":allselectValue          
				    }
				}),
		        success:function(msg){
		          var m =(new Function("","return "+msg))();
		            if(m.code == 200){
		               // $.toast(m.msg); 

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
	});   

	//点击选择按钮 
	$(document).on('click','.sel-this', function () {

 		var value = $(this).prev("input").prop('checked'); //判断是否选中
 		var selValue; //设置当前选中状态标识
 		var instanceid = $(this).parents("li").data("id");  //获取商品id
 		if(!value){

	 		$(this).prev("input").prop('checked',true);  //按钮选中状态
			$(this).find("em").addClass("checked");

			selValue = $(this).prev("input").prop('checked');  //记录是否选中

			proMCartTotalPrice();				 
			$('#J_SelectAll dfn').show();
 		}else{
 			$(this).prev("input").prop('checked',false); //按钮未选中状态
			$(this).find("em").removeClass();	

			selValue = $(this).prev("input").prop('checked'); //记录是否选中	
			
	    	var allSel =$('#cart-list input:checked').length;
	    	if(!allSel){		    		
 				$("#J_SelectAll dfn").text("").hide();
 			}
 			proMCartTotalPrice();		 
		}
		
		//判断是否全部选中			
		var allSel =$('#cart-list input:checked').length;	  
		var thisSel =$("#cart-list li").length;	
		
		if(thisSel==allSel){ 
			$("#J_SelectAll em").addClass("checked");		
			$('#J_SelectAll input').prop('checked', true);  
		}else{
			$('#J_SelectAll input').prop('checked', false);  
	    	$("#J_SelectAll em").removeClass();  //取消全选样式
		}

		$.ajax({
		        type:"post",
		        url:postUrl,
		        contentType:"application/json",
		        dataType:"JSON",
		        data: JSON.stringify({
				    "service": "zm3c.cart.check",
				    "channel": "m",
				    "sv": "1.2.300",
				    "cv": "1.4.10",
				    "pn": "xxx",
				    "st": "MD5",
				    "sign": $.fn.cookie('token'),
				    "sn": dateNum,
				    "reqData": {
				        "uid": Number( $.fn.cookie('uid')),
				        "instanceid": instanceid,
				        "checked":selValue          
				    }
				}),
		        success:function(msg){
		          var m =(new Function("","return "+msg))();
		            if(m.code == 200){
		               // $.toast(m.msg); 

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
  	});

	// 点击结算按钮
  	$('#myCart_Pay').on('click', function () {

  		var allPicel = $("#J_SelectAll dfn").text();
  		if(allPicel!=="" && allPicel!="￥0" ){
  			 window.location.href = '/modules/purchase/html/order_Confirm.html?id='+Number($.fn.cookie("uid"));
  		// 	$.ajax({
		  //       type:"post",
		  //       url:postUrl,
		  //       contentType:"application/json",
		  //       dataType:"JSON",
		  //       data: JSON.stringify({
				//     "service": "zm3c.cart.account",
				//     "channel": "m",
				//     "sv": "1.2.300",
				//     "cv": "1.4.10",
				//     "pn": "xxx",
				//     "st": "MD5",
				//     "sign": "xxx",
				//     "sn": "20160905161845672562",
				//     "reqData": {
				//         "uid": 2
				//     }
				// }),
		  //       success:function(msg){
		  //         var m =(new Function("","return "+msg))();
		  //           if(m.code == 200){
		  //              $.toast(m.msg); 

		  //              	var proListNum = $("#cart-list li");
		  //               var array = [];
				// 		// for( i=0; i<proListNum.length;i++ ){
				// 		// 	if(proListNum.eq(i).find("em").hasClass("checked")){
				// 		// 		array.push( proListNum.eq(i).data("id") );
				// 		// 		window.location.href = '/modules/purchase/html/order_Confirm.html?&id[]=' + array.join('&id[]=');
				// 		// 		// var prolist =$("#cart-list li").eq(i).html();
				// 		// 		// var newHtml = '<li>'+prolist+'</li>';
								
				// 		// 		 // window.location.href = '/modules/purchase/html/order_Confirm.html';
				// 		// 	}
				// 			// console.log(newHtml);
				// 			// localStorage.a = newHtml;
				// 		// }
						 
		  //           }else{
		  //             $.toast(m.msg);
		  //           }
		  //       },
		  //       error:function(){
		  //         $.alert("系统繁忙请稍后再试");
		  //       }
		            
		  //   });
  		}else{
  			$.toast("请选择商品");
  		}			
	}); 
  	
 
 
});