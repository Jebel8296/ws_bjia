define(function(require, exports, module) {
	var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
	require('LIBS/sui/js/sm.min');
	require('LIBS/sui/js/sm-extend.min');
	require('public/js/ajaxService');

	$.init();
	orderList();
	function orderList(){
	 		$.ajax({
			        type:"post",
			        url:postUrl,
			        contentType:"application/json",
			        dataType:"JSON",
			        data: JSON.stringify({
					    "service": "zm3c.order.list",
					    "channel": "m",
					    "sv": "1.2.300",
					    "cv": "1.4.10",
					    "st": "MD5",
					    "sign": $.fn.cookie('token'),
					    "sn": dateNum,
					    "reqData": {
					        "uid": Number( $.fn.cookie('uid')),
					        "pageNum": 1,
					        "pageSize": 10
					    },
					    "ts": 149203841243
					}),
			        success:function(m){
			        	var m = JSON.parse( m );
						console.log(m);

						 
			            if(m.code == 200){


				        	// var orderNumber  = m.respData.total.length; //获取个数 
	 						var orderList = m.respData.data;
	 			        	var orderNumber  = orderList.length;
							var $list_html = $('<div />');
							for(i=0; i<orderNumber; i++){
								var orderItem = orderList[i];
								console.log(m.respData.data[i].oid);
								if(orderItem.status ==1){
									orderItem.status = '<em class="color0">待支付</em>\
								<a href="javascript:;" class="btn_yel btn_small payBtn">去支付</a><a class="order_cancel">取消订单</a>';
								}else if(orderItem.status ==2){
									orderItem.status = '<em class="color4">已支付待发货</em>';
								}else if(orderItem.status ==3){
									orderItem.status = '<em class="color4">已发货</em>' +
									'<a href="javascript:;" class="btn_yel btn_small payBtn">确认收货</a>';
								}else if(orderItem.status ==4){
									orderItem.status = '<em class="color4">交易完成</em>';
								}else if(orderItem.status ==5){
									orderItem.status = '<em class="color4">取消订单</em>';
								}else if(orderItem.status ==6){
									orderItem.status = '<em class="color4">拒收</em>';
								}else if(orderItem.status ==7){
									orderItem.status = '<em class="color4">交易关闭 </em>';
								}
								var ctime =getAllTime(orderItem.ctime)
								//var Total=orderItem.pay+orderItem.express_pay;

								Total=toDecimal2(orderItem.pay);
								orderItem.express_pay=toDecimal2(orderItem.express_pay);
								var orderHtml = '<div class="order_msg" data-order="'+ orderItem.oid +'">\
								<dl><dt class="d_num">订单号：<em class="orderCodejs">'+ orderItem.code +'</em><span>'+ ctime+'</span></dt></dl><div class="list-block media-list"><ul class="prol"></ul></div><div class="d_num"  title="'+ orderItem.oid +'"  >合计金额：¥<em class="payP">'+ Total+'</em><em class="payP">（含运费￥'+orderItem.express_pay+'）</em><span class="orderStauts"> '+ orderItem.status +'</span></div></div>';
								var $order_item_html = $( orderHtml );
                                var productList = orderItem.prods;

								for( var k=0; k<productList.length; k++ ){
									var productItem = productList[k];
									var productPrice=toDecimal2(productItem.price);
									var productHtml = '<li><a href="/modules/purchase/html/order_Details.html?code='+ orderItem.oid +'" class="item-content"><div class="item-content"  data-pro="'+ productItem.code +'">\
									<div class="item-media"><img src="/modules/public/img/3/3C40A100010001.png"></div>\
									<div class="item-inner"><div class="item-title-row"><div class="item-title"  >'+ productItem.name +'</div>\
									<div class="item-after ptotal mar_top">x'+ productItem.total +'</div></div>\
									<div class="item-subtitle">型号：'+ productItem.model+'     颜色：'+productItem.color+'</div><div class="item-text"><span class="color0 pPrice">'+ productPrice +'</span></div>\
									</div></div></a></li>';
									//$(".prol").append(productHtml)
									var $order_product_html = $( productHtml );
									$order_item_html.find('ul').append( $order_product_html );



								}
								$list_html.append( $order_item_html );

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
							$(".page_item").append( $list_html.html() );

			                
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
			            
			});		
			
		};
    	$(document).on('click','.close',function(){
			$("#modal").css('display','none')
        })
		$(document).on('click','.order_cancel',function(){
            $("#modal").css('display','block')
            //TODO 当前的订单ID
            var orderID=$(this).parents().parents('.d_num').attr('title');
            localStorage.setItem("orderid",orderID);
            $("#select_box").change(function(){
                var selected=$("#select_box").val();
				console.log(selected);

					localStorage.setItem("select",selected);



            });
		})
		$("#determine").on('click',function(){

			console.log("111");

        var userorderID=localStorage.getItem("orderid");
		var userselect=localStorage.getItem("select");
			if(userselect=="请选择取消原因") {
				alert("您还没选择取消原因");
				return;
			}


            $.ajax({
                type:"post",
                url:postUrl,
                contentType:"application/json",
                dataType:"JSON",

                data: JSON.stringify({
                    "service":"zm3c.order.cancel",
                    "channel": "m",
                    "sv": "1.2.300",
                    "cv": "1.4.10",
                    "pn": "xxx",
                    "st": "MD5",
                    "sign": $.fn.cookie('token'),
                    "sn": dateNum,
                    "reqData": {
                        'uid': Number( $.fn.cookie('uid')),
                        'orderid': Number(userorderID),
                        'channel': 'm',
                        'reason':userselect,
                    }
                }),success:function(msg){
                    var m =(new Function("","return "+msg))();
                  if(m.code=200){
                      $("#modal").css('display','none');
                      window.location.reload();
				  }
                },
                error:function(){
                    $.alert("系统繁忙请稍后再试");
                }
            })


		})
		$(document).on('click','.payBtn', function () {

			var payfee = $(this).parents(".d_num").find("em").text();
			var ordercode = $(this).parents(".d_num").siblings().find(".orderCodejs").text();
			//console.log(payfee+"支付");
			//console.log(ordercode+"支付");

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
				        "uid": Number( $.fn.cookie('uid')),
				        "ordercode": ordercode,
				        "payfee": payfee,
				        "channel": "m"
				    }
				}),
				success:function(msg){
		          var m =(new Function("","return "+msg))();
		            if(m.code == 200){
		               setTimeout('window.location.href = "'+ m.respData.payurl +'"', 5000);
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
			})	
		});


});