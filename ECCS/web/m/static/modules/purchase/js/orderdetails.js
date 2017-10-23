define(function(require, exports, module) {
	var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
	require('LIBS/sui/js/sm.min');
	require('LIBS/sui/js/sm-extend.min');
	require('public/js/ajaxService');

	$.init();


		orderDetails();
		//$('#apply-sale').click(function(){
		//		console.log("11111");
        //
		//})
		$(document).on('click','#apply-sale',function(){
			if(!$('#apply-sale').hasClass('item-sale1')) {
				$("#modal").css('display', 'block')
			}

		})
		$(document).on('click','.close',function(){
			$("#modal").css('display','none')

		})
		$(document).on('click','#apply-sale',function(){

			var pNum=$(this).prev().prev().prev().children('.item-after').text();
			var productNum1=pNum.slice(1,pNum.length);
			console.log(productNum1);
			localStorage.setItem('proNumber',productNum1);
		})
		$(document).on('click','#determine',function(){
			if(!$('#apply-sale').hasClass('item-sale1')){
				var url= window.location.search; //获取url中"?"符后的字串
				var orderID =parseInt(url.substr(6));
				var orederNumber=$(".OrderNumber").text().slice(6);

				var slicemodel=$(".item-subtitle").text().slice(3,7);
				var slicecorlor=$(".item-subtitle").text().slice(10,12);
				var prodcode=$(".item-subtitle").attr('data-pro')
				var pagetype=1;
				var productNum=localStorage.getItem('proNumber');


				console.log(productNum);

				window.location.href="/modules/aftersales/html/subServiceOrder.html?orderid="+orderID+"&prodcode="+prodcode+"&prodmodal="+slicemodel+"&prodcolor="+slicecorlor+"&Courier="+orederNumber+"&Courier="+orederNumber+"&page_type="+pagetype+"&proctNum="+productNum+"";

			}

		})
	 	function orderDetails(){

			var url= window.location.search; //获取url中"?"符后的字串
			var code =parseInt(url.substr(6));
			console.log(code);
	 		$.ajax({
			        type:"post",
			        url:postUrl,
			        contentType:"application/json",
			        dataType:"JSON",
			        data: JSON.stringify({
					    "service": "zm3c.order.info",
					    "channel": "m",
					    "sv": "1.2.300",
					    "cv": "1.4.10",
					    "st": "MD5",
					    "sign": $.fn.cookie('token'),
					    "sn": dateNum,
					    "reqData": {
					        "uid": Number( $.fn.cookie('uid')),
					        "orderid": code
					    },
					    "ts": 149203841243
					}),
			        success:function(m){
			        	var m = JSON.parse( m );

						 
			            if(m.code == 200){

							var orderItem = m.respData;
							var prods = orderItem.prods;
							var express = orderItem.express;
							console.log(orderItem)
                            console.log(prods)
                            console.log(express)
							//console.log(m)

							if( orderItem.status == 1){
								orderItem.status = "待支付";
							}else if( orderItem.status ==2){
								orderItem.status = "已支付";
							}else if( orderItem.status ==3){
								orderItem.status = "已发货";
							}else if( orderItem.status ==4){
								orderItem.status = "交易完成";
							}else if( orderItem.status ==5){
								orderItem.status = "已取消";
							}else if(orderItem.status ==6){
								orderItem.status = "已拒收";
							}else{
								orderItem.status = "交易关闭";
							}
							var deliveryTime="";
							if( orderItem.devivers == "weekend"){
								deliveryTime = "节假日";
								 console.log(deliveryTime)
							}else if( orderItem.devivers =="workday"){
								deliveryTime = "工作日";
							}
                            var orderT;/*TODO 创建时间*/
							var pyT;/*TODO 付款时间*/
							var deliveryT;/*TODO 发货时间*/
							var completeT;/*TODO 完成时间	*/
							if(orderItem.ctime!="") {
                                 orderT = getTise(orderItem.ctime);
                            }else{
                                orderT=""
							}
                            if(orderItem.ptime!="") {
                                pyT = getTise(orderItem.ptime);
                            }else{
                                pyT=""
                            }
                            if(orderItem.stime!="") {
                                deliveryT = getTise(orderItem.stime);
                            }else{
                                deliveryT=""
                            }
                            if(orderItem.otime!=""){
                                completeT=getTise(orderItem.otime);
							}else{
                                completeT=""
							}
							/*TODO 当前的发送时间*/
                            //var sendT = getTime(orderItem.ctime);
							var sendT=m.respData.devivers;
                            // if(dataID=="周一至周五"){
                            //     dataID="allday";
                            // }else if(dataID=="工作日"){
                            //     dataID="workday";
                            // }else{
                            //     dataID="holiday";
                            // }
							if(sendT=="allday"){

								sendT="周一至周五"
							}else if(sendT=="workday"){
                                sendT="工作日"
							}else{
                                sendT="节假日"
							}
                            /*TODO 时间转换函数*/
                            function getTise(time) {
                                var oDate = new Date(time);
                                var year = oDate.getFullYear();
                                var month = oDate.getMonth() + 1;
                                var day = oDate.getDate();
                                var hour = oDate.getHours();
                                var Minutes = oDate.getMinutes();
                                var seconds=oDate.getSeconds();
                                return year+"-"+month+"-"+day+ "  "+hour+ ':' + Minutes+":"+seconds;
                            }
                            var logisticsCode=orderItem.logistics_code;
							if(logisticsCode==null){
                            	logisticsCode=""
							}
							//订单状态
							var orderInfo = '<dl>' +
								'<dt>订单信息</dt>' +
								'<dd class="OrderNumber">订 单 号：'+ orderItem.code +'</dd>' +
								'<dd>创建时间：'+ orderT +'</dd>' +
                                '<dd>付款时间：'+ pyT +'</dd>' +
                                '<dd>成交时间：'+ deliveryT +'</dd>' +
                                '<dd>发货时间：'+ completeT +'</dd>' +
								'<dd>订单状态：<span class="color0">'+ orderItem.status +'</span></dd>' +
								'</dl>';
							$(".orderDetail").append(orderInfo);
							//var orderStatus;
							// 商品清单
							//var commodityListHtml = '<div class="order_msg"><dl><dt>商品清单</dt></dl><div class="list-block media-list proItem-list"><ul class="dddd">';

							for( i=0; i<prods.length; i++){
								   // 商品清单
								 	var orderPrice=toDecimal2(prods[i].price);
								    var proListHtml;
									if(orderItem.status=="交易完成"||orderItem.status=="已取消"||orderItem.status=="已拒收") {
										 proListHtml = '<li>' +
											'<div class="item-content">' +
											'<div class="item-media">' +
											'<img  src="/modules/public/img/3/3C40A100010001.png">' +
											'</div><div class="item-inner">' +
											'<div class="item-title-row">' +
											'<div class="item-title">' + prods[i].name + '</div>' +
											'<div class="item-after">x' + prods[i].total + '</div>' +
											'</div><div  data-pro="' + prods[i].code + '"  class="item-subtitle">型号：' + prods[i].model + '颜色：' + prods[i].color + '</div>' +
											'<div class="item-text color0">¥' + orderPrice + '</div>' +
											'<div id="apply-sale"  class="item-sale">申请售后</div>' +
											'</div>' +
											'</div>' +
											'</li>';
									}else if(orderItem.status=="待支付"||orderItem.status=="交易关闭"){
											proListHtml = '<li>' +
											'<div class="item-content">' +
											'<div class="item-media">' +
											'<img  src="/modules/public/img/3/3C40A100010001.png">' +
											'</div><div class="item-inner">' +
											'<div class="item-title-row">' +
											'<div class="item-title">' + prods[i].name + '</div>' +
											'<div class="item-after">x' + prods[i].total + '</div>' +
											'</div><div  data-pro="' + prods[i].code + '"  class="item-subtitle">型号：' + prods[i].model + '颜色：' + prods[i].color + '</div>' +
											'<div class="item-text color0">¥' + orderPrice + '</div>' +
											'</div>' +
											'</div>' +
											'</li>';
									}
									else if(orderItem.status=="待发货"||orderItem.status=="已发货"){
											proListHtml = '<li>' +
											'<div class="item-content">' +
											'<div class="item-media">' +
											'<img  src="/modules/public/img/3/3C40A100010001.png">' +
											'</div><div class="item-inner">' +
											'<div class="item-title-row">' +
											'<div class="item-title">' + prods[i].name + '</div>' +
											'<div class="item-after">x' + prods[i].total + '</div>' +
											'</div><div  data-pro="' + prods[i].code + '"  class="item-subtitle">型号：' + prods[i].model + '颜色：' + prods[i].color + '</div>' +
											'<div class="item-text color0">¥' + orderPrice + '</div>' +
											'<div id="apply-sale" title="确认收货后才可申请售后服务" class=" item-sale1">申请售后</div>' +
											'</div>' +
											'</div>' +
											'</li>';
											'</li>';
									}
								   $(".dddd").append(proListHtml);
							   }
							//var orderTo = '商品总计：¥'+ orderItem.product_pay +'    　 运费：¥'+orderItem.express_pay +' 　　　 金额：¥'+ orderItem.pay;
							var ordermoney=toDecimal2(orderItem.pay);
							orderItem.express_pay=toDecimal2(orderItem.express_pay);
                            var orderTo = '订单总金额：¥'+ ordermoney+"  （包含运费：￥"+orderItem.express_pay+")";
							$(".orderTotal").append(orderTo);
                            $(".orderTotal").css("text-align","right");
						//'</ul><div class="f-12 pd">商品总计：¥'+ orderItem.express_pay +'    　   运费：¥'+orderItem.express_pay +' 　　　     应付金额：¥'+ orderItem.pay +'</div></div></div>';

						//	$(".page_item").append(commodityListHtml);
							function turnString(type){
								if(type == null){
									return  "无"
								}else{
									return type
								}
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
							// 支付方式
							var payment = '<div class="order_msg"><dl><dt>支付信息</dt><dd>支付方式：'+turnString(orderItem.pay_type) +'</dd></dl></div>';
							// 发票信息
							var InvoiceInfoHtml ='<div class="order_msg"><dl><dt>发票信息</dt><dd>发票抬头：'+ turnString(orderItem.invoce) +'</dd></dl></div>';
							//收货信息
							var receivingInfoHtml = '<div class="order_msg"><dl><dt>收货信息</dt><dd>收货人：'+ express.name +'</dd><dd>联系电话：'+ express.phone +'</dd><dd>收货地址：'+ express.address +'</dd><dd>邮      编：'+ express.zipcode +'</dd><dd>送货时间：'+ sendT +'</dd></dl></div>';
							//配送信息
							var distributionInfo ='<div class="order_msg"><dl><dt>配送信息</dt><dd>配送费用：'+ orderItem.express_pay +'元</dd><dd>物流公司：'+ orderItem.logistics_name +'</dd><dd>货运单号：'+ logisticsCode+'</dd><dd>出库时间：'+ deliveryT +'</dd></dl></div>';

							if(orderItem.desc==null){
								orderItem.desc="无";
							}

							var noteinfo='<div class="order_msg"><dl><dt>备注信息</dt><dd>'+ orderItem.desc +'</dd></dl></div>';

							$(".page_item").append(payment,InvoiceInfoHtml,receivingInfoHtml,distributionInfo,noteinfo);


			               
			                
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
			
		};


});