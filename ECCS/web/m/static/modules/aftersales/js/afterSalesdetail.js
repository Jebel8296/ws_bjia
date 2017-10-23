define(function(require, exports, module) {
	var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
	require('LIBS/sui/js/sm.min');
	require('LIBS/sui/js/sm-extend.min');
	require('public/js/ajaxService');

	$.init();
	//var code
	//var info_name
	//var info_ad
	//var info_phone
	//选择型号
	$('.sel a').on('click', function () {		
		$(this).addClass("cur").siblings("a").removeClass();
	});

	var url= decodeURIComponent(window.location.search); //获取url中"?"符后的字串
	//TODO 再次申请应该走的线路
	if(url==""){
		console.log("再次申请")
		var parmeobjs=JSON.parse(localStorage.getItem('submit_Again'));
		 code =parmeobjs.ordercode;
		 info_name =parmeobjs.ordername;
		 info_ad =parmeobjs.orderadr;
		 info_phone=$.fn.cookie('phone');
	}else{
		var theRequest = new Object();
		if (url.indexOf("?") != -1) {
			var str = url.substr(1);
			strs = str.split("&");

			for(var i = 0; i < strs.length; i ++) {
				theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
			}
		}
		code = theRequest.code; //TODO 用户code
		info_name = theRequest.name;//TODO 用户名称
		info_ad = decodeURI(theRequest.adr);//TODO 用户地址
		info_phone= theRequest.phone;//TODO 用户电话
	}

	console.log(info_name+"..."+info_ad+'...'+info_phone);
 	$.ajax({
		type:"post",
        url:postUrl,
        contentType:"application/json",
        dataType:"JSON",
        data: JSON.stringify({
		    "service": "zm3c.aftersale.info",
		    "channel": "m",
		    "sv": "1.2.300",
		    "cv": "1.4.10",
		    "pn": "xxx",
		    "st": "MD5",
		    "sign": $.fn.cookie('token'),
		    "sn": dateNum,
		    "reqData": {
		        "uid":Number( $.fn.cookie('uid')),
		        "aftercode": code
		    }
		}),
		
		success:function(msg){
          	var m =(new Function("","return "+msg))();
			var Courier_obj
            var imgobj;
			var aftersalesStaus;
			var aftersalesType;
			var otherMSG;
			var other_type={};
            if(m.code == 200){
				console.log(m);
                imgobj=m.respData.images
				Courier_obj= m.respData.logistics;
				otherMSG= JSON.parse(m.respData.producttype);

				console.log(Courier_obj);
				//TODO 售后进度
				if(m.respData.status==0){
					aftersalesStaus="待受理"
				}else if(m.respData.status==1){
					aftersalesStaus="待邮递"
				}else if(m.respData.status==2){
					aftersalesStaus="处理中"
				}else if(m.respData.status==3){
					aftersalesStaus="售后完成"
				}else if(m.respData.status==4){
					aftersalesStaus="关闭"
				}else if(m.respData.status==5){
					aftersalesStaus="待补充"
				}
				//TODO 售后类型
				if(m.respData.aftertype==1){
					aftersalesType="退货"
					$('.other_msg').css("display","none")
				}else if(m.respData.aftertype==2){
					aftersalesType="换货"
				}else if(m.respData.aftertype==3){
					aftersalesType="维修"
				}
				//TODO 判断快递公司和快递号是否为空
				if(Courier_obj.logisticsName==null){
					Courier_obj.logisticsName="";
				}
				if(Courier_obj.logisticsCode==null){
					Courier_obj.logisticsCode="";
				}
				//TODO 其他信息的退货类型判断
				if(otherMSG.v1=="1"){
					otherMSG.v1="电子手表"
				}else {
					otherMSG.v1=""
				}
				if(otherMSG.v2=="1"){
					otherMSG.v2="耳机"
				}else{
					otherMSG.v2=""
				}
				if(otherMSG.v3=="1"){
					otherMSG.v3="数据线"
				}else{
					otherMSG.v3=""
				}
				if(otherMSG.v4=="1"){
					otherMSG.v4="充电器"
				}else{
					otherMSG.v4=""
				}

                var salesCode = '<div>售后编号：'+ code + '<span class="account">账号：'+$.fn.cookie('phone')+'</span></div> '+'<div>售后类型：<span class="color0 ">'+ aftersalesType +'</span><span  class="salesType" >处理进度：<span style="color:red">'+aftersalesStaus+'</span></span></div>';
			   	$(".text-center").append(salesCode);

			   	//TODO 判断颜色的进渡，估计是要删除的
			   	// $(".detailBarMsg li").addClass("dtl_on");
			   	// if(m.respData.status == "待受理"){
			   	// 	$(".detailBarMsg li").eq(0).addClass("dtl_on");
					// $(".detailBarMsg li").eq(1).removeClass("dtl_on");
					// $(".detailBarMsg li").eq(2).removeClass("dtl_on");
			   	// }else if(m.respData.status == "已受理"){
			   	// 	$(".detailBarMsg li").eq(0).addClass("dtl_on");
			   	// 	$(".detailBarMsg li").eq(1).addClass("dtl_on");
			   	// }else if(m.respData.status == "已取消"){
			   	// 	$(".detailBarMsg li").eq(0).addClass("dtl_on");
			   	// 	$(".detailBarMsg li").eq(1).addClass("dtl_on");
			   	// 	$(".detailBarMsg li").eq(1).text("已取消");
			   	// }else if(m.respData.status == "已完成"){
			   	// 	$(".detailBarMsg li").addClass("dtl_on");
			   	// }
				//
				//TODO 时间转换函数
                function getTise(time) {
                	if(time==null){
                		return ""
					}
                    var oDate = new Date(time);
                    var year = oDate.getFullYear();
                    var month = oDate.getMonth() + 1;
                    var day = oDate.getDate();
                    var hour = oDate.getHours();
                    var Minutes = oDate.getMinutes();
					Minutes<10?Minutes="0"+Minutes:Minutes;
                    var seconds=oDate.getSeconds();
					seconds<10?seconds="0"+seconds:seconds;
                    return year+"-"+month+"-"+day+ "  "+hour+ ':' + Minutes+":"+seconds;
                }
                // TODO 进度时间对象
                progressTime={};
				//TODO 给对象增加属性
                progressTime['applyTime']=getTise(m.respData.applytime);
                //progressTime['auditTime']=getTise(m.respData.applytime);
                //progressTime['handleTime']=getTise(m.respData.logisticstime);
                //progressTime['finishTime']=getTise(m.respData.finishtime);

               //var  progress='<ul>' +
				//   					'<li>申请时间：'+progressTime.applyTime+'</span></li>' +
               //    					//'<li>审核时间：'+progressTime.auditTime+'</span></li>' +
               //    					//'<li>审核时间：'+progressTime.audidetTime+'</span></li>' +
               //    					//'<li>处理时间：'+progressTime.handleTime+'</span></li>' +
               //    					//'<li>完成时间：'+progressTime.finishTime+'</span></li>' +
				//   			'</ul> '
               //
				//$(".detailprogress").append(progress);
				var applyTimes="待 受 理："+progressTime.applyTime;
				$('.ApplicationTime').text(applyTimes);
				if(m.respData.replyResult){
					for(var i=0;i< m.respData.replyResult.length;i++){
						var applitionStatus;
						var times=getTise(m.respData.replyResult[i].createTime);
						switch(m.respData.replyResult[i].to)
						{

							case 1:
								applitionStatus="待 邮 递"
								break;
							case 2:
								applitionStatus="处 理 中"
								break;
							case 3:
								applitionStatus="售后完成"
								break;
							case 4:
								applitionStatus="关    闭"
								break;
							case 5:
								applitionStatus="待 补 充"
								break;
						}
						var  progress='<li>'+applitionStatus+'：'+times+'</span></li>'
						$(".detailprogress ul").append(progress);
					}
				}

                var proItem = m.respData.products;
				var signT;
				if(m.respData.signtime == null){
					signT = " ";
				}else{
					signT =getTime(m.respData.signtime);
				}
				var ordermodel=m.respData.prodcode.slice(4,8)//TODO 获取设备型号
				var ordercolor=m.respData.prodcode.slice(8,10)//TODO 获取设备颜色

				if(ordercolor=="01"){
					ordercolor="青色"
				}
				if(ordercolor=="02"){
					ordercolor="黑色"
				}
				if(m.respData.reason==null){
					m.respData.reason=""
				}
                // TODO 商品信息
                var proMsg = '<li><div class="item-content">\
			          <div class="item-media"><img src="/modules/public/img/3/3C40A100010001.png"></div>\
			          <div class="item-inner">\
			            <div class="item-title-row">\
			              <div class="item-title">'+ m.respData.prodname +'</div><div class="item-after">x'+  m.respData.total +'</div>\
			            </div>\
			            <div><span>型号：'+ordermodel+'</span>&nbsp;&nbsp;<span></span>颜色：'+ordercolor+'</span> </div>\
			            <div class="item-subtitle">签收日期：'+ signT +' <br/>商品编号：'+m.respData.prodcode   +'<br/>\<div>关联订单号：</div>\
			            </div>\
			            </div>\
			            </div>\
			            <div>退款金额:<span>299元</span>&nbsp;&nbsp;&nbsp;&nbsp;<a style="margin-left:10px;font-size:0.7rem;color:#0000FF;text-decoration:underline " href="/modules/aboutus/html/services.html">退款金额说明</a><span></div>\
			            </li>';
			    $(".bigImgList ul").append(proMsg);
				//TODO 图片信息
				if(imgobj!=null) {
                    for (var i = 0, len = imgobj.length; i < len; i++) {
                        var imgInfo = '<img style="width:30%;border:1px solid #ddd;height: 106px;margin-right:3.3% " src="' + imgobj[i] + '" alt="图片无法显示">'
                        $('.img_box').append(imgInfo);
                    }
                }else{

					$('.img_box').append("您没有上传图片");
				}
				//TODO 其他信息
				var ohterMsg=' <dt>其他信息</dt>\
							   <dd class="tit">请选择售后产品： <span>'+otherMSG.v1+" "+otherMSG.v2+" "+otherMSG.v3+" "+otherMSG.v4+'</span></dd>\
				               <dd class="tit">售后服务原因：<span>'+m.respData.reason+'</span></dd>\
							   <dd class="tit">售后服务描述：<span>'+m.respData.remark+'</span></dd>'
				$('.other_info').append(ohterMsg);

				//TODO 快递信息

				var Courier_Info=' <dt>退货/维修客户邮递信息</dt>\
					<dd class="tit Logisticscompany">物流公司： '+Courier_obj.logisticsName+'</dd>\
					<dd class="tit Logisticsnumber">货运单号：'+Courier_obj.logisticsCode+'</dd>'
				$('.CourierInfo').append(Courier_Info);
				if(Courier_obj.name&&Courier_obj.phone&&Courier_obj.provname&&Courier_obj.cityname&&Courier_obj.address&&Courier_obj.zipcode){
					var cargoInfo='<dd><span>收货人：'+Courier_obj.name+'</span></dd>\
                   			   <dd><span>手机号码：'+Courier_obj.phone+'</span></dd>\
                			   <dd><span>收货地址：'+Courier_obj.provname+Courier_obj.cityname+Courier_obj.address+'</span></dd>\
                			   <dd><span>邮政编码：'+Courier_obj.zipcode+'</span></dd>\ '

					$(".info_box").append(cargoInfo);
				}else{
					var cargoInfo='<dd><span>收货人：'+""+'</span></dd>\
                   			   <dd><span>手机号码：'+""+'</span></dd>\
                			   <dd><span>收货地址：'+""+'</span></dd>\
                			   <dd><span>邮政编码：'+""+'</span></dd>\ '

					$(".info_box").append(cargoInfo);
				}

				//TODO 客服反馈消息
				if(m.respData.replyResult){
					for(var i=0;i< m.respData.replyResult.length;i++){
						var times=getTise(m.respData.replyResult[i].createTime);
						var AntiCounter ='<div>'+times+'<br/>客服反馈：'+m.respData.replyResult[i].reply+'</span></div>'
						$(".afterSre dd").append(AntiCounter);
					}
				}

				//var backMsg = m.respData.reply;
				//if(backMsg != null){
				//	$(".afterSre dd").append(backMsg);
				//}else{
				//	$(".afterSre dd").append(" ");
				//}


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