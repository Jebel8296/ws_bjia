define(function(require, exports, module) {
	var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
	//require('LIBS/sui/js/sm.min');
	//require('LIBS/sui/js/sm-extend.min');
	//require('LIBS/sui/js/sm-city-picker');
	require('public/js/ajaxService');

	$.init();

    // <dd id="logistics">物流公司：<span></span></dd>
    // <dd id="delivery">送货时间：<span></span></dd>

    //var a=$("#logistics_box").html("EMS(免费)");
    //var b=$("#delivery_box").html("周一至周五");
    getlocaldata()
	//$("#picker").val("EMS免费");
	//$("#dataID").val("周一至周五");
	$("#receiptName").blur(function(){
		var revN = $("#receiptName").val();
		if (revN.length>30) {
			$.alert("发票抬头不超过30个字符！")
		}
	});
	//确认订单 - 商品清单
	//order_Confirm.html
 	var url= window.location.search; //获取url中"?"符后的字串 
	var id =url.substr(4);

	$.ajax({
        type:"post",
        url:postUrl,
        contentType:"application/json",
        dataType:"JSON",
        data: JSON.stringify({
		    "service": "zm3c.cart.account",
		    "channel": "m",
		    "sv": "1.2.300",
		    "cv": "1.4.10",
		    "pn": "xxx",
		    "st": "MD5",
		    "sign": $.fn.cookie('token'),
		    "sn": dateNum,
		    "reqData": {
		        "uid": Number( $.fn.cookie('uid'))
		    }
		}),
        success:function(msg){
          var m =(new Function("","return "+msg))();
            if(m.code == 200){

               $.toast(m.msg);

               	var productItem =m.respData.product;
				var addressItem =m.respData.address;

				console.log(productItem);
				freightSum();
				function freightSum(){
					var CourierObj=JSON.parse(localStorage.getItem("distributionObj"));
					var t=18;
					t=(toDecimal2(t));
					if(CourierObj){
						if(CourierObj.logistics==="顺丰(收费)"){
							$(".freight").text(t);
						}

					}
				}

				var prodNum=m.respData.product;
				var prodNumTotal=0;

				console.log(selectINfo);
				var sumPrice=0;
				for(var i=0;i<prodNum.length;i++){

					prodNumTotal+=parseFloat(prodNum[i].prodtotal);
					sumPrice+=parseFloat(prodNum[i].payfee);
				}

				sumPrice=toDecimal2(sumPrice);
				$(".Items-total-num").text(prodNumTotal);
				$(".sumPrice").text(sumPrice);
				// 判断是否有收货信息
				var selectINfo=JSON.parse(window.localStorage.getItem('consigneeObj'));
				if(selectINfo){
					$("#yAddr").show();
					$("#noAddr").hide();
					var dfAddr1 = '<div id="defaultAddr" class="item-title" data-eid="'+ selectINfo.eid +'">收货人： <span class="recInfo_name">'+ selectINfo.consignee +'</span><br/>联系电话：<span class="recInfo_tel">'+ selectINfo.phoneNum +'</span> <br/>\
	                  地   址：<span class="recInfo_addr" data-code="'+ selectINfo.zip +'>'+ selectINfo.eid +'">'+ selectINfo.siteM+'</span></div>';
					console.log("进入了这个条件")

					$("#yAddr .item-title-row").append(dfAddr1);

				}else {
					if (addressItem != "") {
						$("#yAddr").show();
						$("#noAddr").hide();
						for (var n = 0; n < addressItem.length; n++) {
							if (addressItem[n].status == 1) {

								var dfAddr = '<div id="defaultAddr" class="item-title" data-eid="' + addressItem[n].eid + '">收货人： <span class="recInfo_name">' + addressItem[n].name + '</span> <br/>联系电话：<span class="recInfo_tel">' + addressItem[n].phone + '</span> <br/>\
							  地   址：<span class="recInfo_addr" data-code="' + addressItem[n].eid + '">' + addressItem[n].provname + addressItem[n].cityname + addressItem[n].address + '</span></div>';
							}
						}
						$(".item-title-row").append(dfAddr);
					} else {
						$("#noAddr").show();
						$("#yAddr").hide();
					}
				}
				var total = 0;
				//产品信息
				if(productItem !=""){
					for(var j=0; j<productItem.length; j++){
						
						var proHtml = '<li><div class="item-content" data-prodcode = "'+ productItem[j].prodcode +'"><div class="item-media"><img src="/modules/public/img/3/3C40A100010001.png"></div><div class="item-inner"><div class="item-title-row"><div class="item-title">'+ productItem[j].prodname +'</div><div class="item-after">x'+ productItem[j].prodtotal +'</div></div><div class="item-subtitle">型号：B100 颜色：蓝白</div><div class="item-text"><span class="color0">'+ productItem[j].prodprice +'</span></div></div></div></li>';
						$(".commodityList ul").append(proHtml);
						total += +(productItem[j].prodtotal * productItem[j].prodprice)
						total=toDecimal2(parseInt(total));
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


				//商品清单 总计信息
				//var pro_Total = 10;  //商品总计
				//var pro_Transport = 5.00 //运费
				//var pro_Payable = 15.00//应付金额
				var pro_Total=parseFloat($(".sumPrice").text())+parseFloat( $(".freight").text());
				pro_Total=toDecimal2(pro_Total);
				$(".allTotal").text(pro_Total);
				$(".txtbox").on("change",function(){
					var textVal = $(this).val();
					var t = 12;
					if(textVal=="EMS免费"){
						$(".allTotal").text(total)
					}else if(textVal=="顺丰(收费)") {
                        $(".allTotal").text(t + total);
                        $(".freight").text(t)
                    }
				});

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



	//收货信息

	$(".orderAddr_Name").blur(function(){
		var oAddrName = $(".orderAddr_Name").val(); //收货人
		if(oAddrName==""){
			$(".orderAddr_Name").next().text("不能为空").show();
		}else{
			$(".orderAddr_Name").next().hide();
		}
	})

	$(".orderAddr_area").blur(function(){
		var oAddrarea = $(".orderAddr_area").val(); //所在地区
		// console.log($(".orderAddr_area").val())
		if(oAddrarea==""){
			$(this).next().text("不能为空").show();
		}else{
			$(this).next().hide();
		}
	});

	$(".orderAddr_Msg").blur(function(){
		var oAddrMsg = $(".orderAddr_Msg").val(); //详细地址
		if(oAddrMsg==""){
			$(this).next().text("不能为空").show();
		}else{
			$(this).next().hide();
		}
	});

	$(".orderAddr_Tel").blur(function(){
		var oAddrTel = $(".orderAddr_Tel").val();  //手机号码
        if (!oAddrTel.match(/^(((13[0-9]{1})|159|153)+\d{8})$/)) { 
			$(this).next().text("手机号码格式不正确！").show();
        }else{
			$(this).next().hide();
		}
    });

    $(".orderAddr_Code").blur(function(){
		var oAddrCode = $(".orderAddr_Code").val();  //邮政编码
        if (!oAddrCode.match(/^[\d]{6}$/)) { 
			$(this).next().text("请填写邮政编码").show();
        }else{
			$(this).next().hide();
		}
    });
	//保存收货地址

	
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
		if (!oAddrTel.match(/^(((13[0-9]{1}))+\d{8})$/)) { 
			$(".orderAddr_Tel").next().text("手机号码格式不正确！").show();
			return;
		}else{
			$(".orderAddr_Tel").next().hide();
		}

		//邮政编码
		if (!oAddrCode.match(/^[\d]{6}$/)) { 
			$(".orderAddr_Code").next().text("请填写邮政编码").show();
			return;
		}else{
			$(".orderAddr_Code").next().hide();
		}
			
		// console.log(oAddrName,oAddrarea,oAddrMsg,oAddrTel,oAddrCode);

		return true;
	}
	
	//所在地区
	$("#city-picker").cityPicker({
		toolbarTemplate: '<header class="bar bar-nav">\
		<button class="button button-link pull-right close-picker">确定</button>\
		<h1 class="title">所在地区</h1>\
		</header>'
	});



	//点击保存  收货地址
	$('.save_addr').on('click', function(){
		var oAddrName = $(".orderAddr_Name").val(); //收货人
		var oAddrarea = $(".orderAddr_area").val(); //所在地区
		var oAddrMsg = $(".orderAddr_Msg").val();  //详细地址
		var oAddrTel = $(".orderAddr_Tel").val();  //手机号码
		var oAddrCode = $(".orderAddr_Code").val(); //邮政编码

	 
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
				        "provid": 1,
				        "cityid": 2,
				        "areaid": 4,
				        "def": 0
				    }
				}),
				success:function(msg){
		          var m =(new Function("","return "+msg))();
		            if(m.code == 200){

						$("#noAddr").hide();
						$("#yAddr").show();
						$(".recInfo_name").text(msg.data.oAddrName);
						$(".recInfo_tel").text(msg.data.oAddrTel);
						$(".recInfo_addr").text(msg.data.oAddrMsg); 
		            }else{
		              $.toast(m.msg);
		            }
		        },
		        error:function(){
					$.alert("系统繁忙请稍后再试");
		          // console.log(data.reqData);
		        }
			})
		}


			
	});



	//确认订单 - 发票信息
	$(".sel-this").click(function(){
		saveRecAddress();
        var sel = $('.sel-this').find("em").hasClass("checked");
		if(!sel){
			$(".sel-this em").addClass("checked");
			$('#fap').data("sel",true);
			$("#invoice").addClass("show_content").removeClass("hide_content");
			console.log($('#fap').data("sel"))
		}else{
			$(".sel-this em").removeClass();
			$('#fap').data("sel",false);
			$("#invoice").removeClass("show_content").addClass("hide_content");
			 console.log($('#fap').data("sel"))
		}

    })


	$(".sel-this-ohther").click(function(){
		saveRecAddress();
		var sel = $('.sel-this-ohther').find("em").hasClass("checked");
		if(!sel){
			$(".sel-this-ohther em").addClass("checked");
			$('#fap').data("sel",true);
			console.log($('#fap').data("sel"))
		}else{
			$(".sel-this-ohther em").removeClass();
			$('#fap').data("sel",false);
			console.log($('#fap').data("sel"))
		}

	})


		
	//address_add.html

	//确认订单
	//物流公司


	$("#picker").picker({
		toolbarTemplate: '<header class="bar bar-nav">\
		  <button class="button button-link pull-left"></button>\
		  <button class="button button-link pull-right close-picker">确定</button>\
		  <h1 class="title">物流公司</h1>\
		  </header>',
		cols: [
		    {
		      textAlign: 'center',
		      values: ['EMS免费', '顺丰收费']
		    }
		  ]
	});
	
	//送货时间	
	$("#dataID").picker({
		  toolbarTemplate: '<header class="bar bar-nav">\
		  <button class="button button-link pull-left"></button>\
		  <button class="button button-link pull-right close-picker">确定</button>\
		  <h1 class="title">送货时间</h1>\
		  </header>',
		  cols: [
		    {
		      textAlign: 'center',
		      values: ['周一至周五', '工作日', '节假日']
		    }
		  ]
	});
	/*TODO 最新改动的地方*/
	function getlocaldata(){


        var Courier=JSON.parse(localStorage.getItem("distributionObj"));
        console.log(Courier);
			if(Courier==null){
            $("#logistics>span").html("EMS(免费)")
			}else{
            $("#logistics>span").html(Courier.logistics)
			}
            if(Courier==null){
                $("#delivery>span").html("周一至周五")
		    } else {
              $("#delivery>span").html(Courier.sendTime)
            }
    }

 	//提交订单
	$(".orderSubmit").on('click', function(){
        getlocaldata();
		if($('#fap').data("sel")==false){
			$.toast("您还没有勾选用户协议");
			return;
		}
		var eid =$("#defaultAddr").data("eid");  //id
		var oderName = $(".recInfo_name").text();  //收货名称
		var oderTel = $(".recInfo_tel").text();   //电话 
		var address = $(".recInfo_addr").text();   //地址
		var oderCode = $(".recInfo_addr").data("code"); //订单编号
		//var fapmsg = $('#fap').data("sel");
		var sel = $('.sel-this').find("em").hasClass("checked");
		var invoce = $("#receiptName").val();// 发票
		var company = $("#logistics>span").text();  // 物流
		console.log(company)
		var dataID = $("#delivery>span").text(); //送货时间
		console.log(dataID)
		var payF =$(".allTotal").text();//金额
		console.log(payF)

			if(company=="EMS(免费)"){
				company="YZXB";
			}else{
				company="SF";
			}

			if(dataID=="周一至周五"){
				dataID="allday";
			}else if(dataID=="工作日"){
				dataID="workday";
			}else{
				dataID="holiday";
			}

			if(oderName.length==0){
				saveRecAddress();
			}else{

			if(sel){
				if(invoce==""){
					$.toast("请填写发票抬头！");
					return false;
				}else if(invoce.length>30){
					$.toast("发票名称不能大于30个字符！");
					return false;
				}
			}
			if($("#picker").val()==""){
				$.toast("请选择物流公司！");
				return;
			}
			if($("#dataID").val()==""){
				$.toast("请选择送货时间");
				return;
			}


			var sel_aid = $("#commodityList li"); //那么..首先获取选中的checkbox值    
		    var arr = new Array();    
		    $.each(sel_aid,function(i,obj){
		        arr[i] =   $(obj).val();
		        // console.log(arr)
		    }); 


		    var proNum = $(".commodityList li");
		    var code;
		    var products = [];    
			for (var i = 0; i < proNum.length; i++) {  

				
				 
				var code = $(".commodityList li").eq(i).find(".item-content").data("prodcode");
			    var name = $(".commodityList li").eq(i).find(".item-title").text();
			    var price = $(".commodityList li").eq(i).find(".item-text span").text();
			    var total = 1;
			    var reducefee = "0";

			    //products[i] = {code,name,price,total,reducefee};
			} 
			// console.log(products);

			$.ajax({
		        type:"post",
		        url:postUrl,
		        ContentType: "application/json",
		        dataType:"JSON",
	        	data: JSON.stringify({				    
				    "service": "zm3c.order.add",
				    "channel": "m",
				    "sv": "1.2.300",
				    "cv": "1.4.10",
				    "st": "MD5",
				    "sign": $.fn.cookie('token'),
				    "sn": dateNum,
				    "reqData": {
				        "uid": Number( $.fn.cookie('uid')),
				        "expressid": eid,
				        "logistics": company,
				        "delivers": dataID,
				        "invoce":invoce
				    },
				    "ts": 149203841243
				}),

		        success:function(msg){

		          var m =(new Function("","return "+msg))();
		          // console.log(m.respData.ordercode,m.respData.payfee);
		            if(m.code == 200){
						//console.log(m.respData.ordercode)

	               // window.location.href='/modules/pay/html/order_PayComfirm.html?code='+ code +'&pay='+ m.respData.payfee;
						window.location.href='/modules/pay/html/order_PayComfirm.html?code='+ m.respData.ordercode +'&pay='+ payF;
	               
		            }else if(m.code == 200){
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
	 
			
		}
	})

	$('#addrManage').on('click', function () { 
		window.location.href = "address.html";
	 
	})

	$('.addrChange').on('click', function () { 
		window.location.href = "address_add.html";
	})

	$('.sel-this').on('click', function () { 
		$(this).siblings("input").attr("checked","checked");
	})

 
	
});