define(function(require, exports, module) {
    var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
    require('LIBS/sui/js/sm.min');
    require('LIBS/sui/js/sm-extend.min');
    require('public/js/ajaxService');

    $.init();

    //选择型号
    $('.sel a').on('click', function () {
        $(this).addClass("cur").siblings("a").removeClass();
    });
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
                "uid": Number( $.fn.cookie('uid'))
            },
            "ts": 149203841243
        }),
        success:function(msg){
            var m =(new Function("","return "+msg))();
            console.log(m);
            if(m.code == 200){
                var proItem = m.respData.data;
                console.log()
                var cartPro=0;
                var oid=[];

                var for_in = function(data, oid, code,Courier_code){
                    console.log(Courier_code);
                    var cartProItem = '';
                    for(j=0;j<data.length;j++){//这个地长度是1，

                        cartProItem += '<div class="item-content"><div class="item-media"><img src="/modules/public/img/3/3C40A100010001.png"></div>\
				        <div class="item-inner">\
				          <div class="item-title-row">\
				          <div class="item-title" data-eid="'+ data[j].code +'">'+ data[j].name +'</div>' + '' +
                            '<a id="after_sales" href="/modules/aftersales/html/subServiceOrder.html?uid='+ Number( $.fn.cookie('uid'))+'&orderid='+ oid +'&prodcode='+ data[j].code
                            +'&prodmodal='+data[j].model+'&prodcolor='+data[j].color+'&Courier='+Courier_code+' " class="item-after smbtn0 item-right-btn jssh">发起售后\
                            </a>\
                           </div>\
				          <div class="item-subtitle">型号：' + data[j].model+'     颜色：'+data[j].color + '</div>\
				          <div class="item-text"></div></div></div>';
                        // $("#after_sales").on('click',function(){
                        //     console.log("1111");
                        //     console.log(data);
                        //
                        // })
                    }
                    return cartProItem;

                    $(".media-list").append(cartProItem);
                }
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

                for(var i=0;i<proItem.length;i++){
                    var applyT = getTise(proItem[i].ctime)
                    cartPro = proItem[i].prods;
                    var oterHtml;
                    for(var m =0; m<cartPro.length;m++){

                        if(proItem[i].apply==1){

                            oterHtml = '<div class="item-content"><div class="item-media"><img src="/modules/public/img/3/3C40A100010001.png"></div>\
				        <div class="item-inner">\
				          <div class="item-title-row"><div class="item-title" data-eid="'+ cartPro[m].code +'">'+ cartPro[m].name +'</div></div>\
				          <div class="item-subtitle">型号：' + cartPro[m].model+'     颜色：'+cartPro[m].color + '</div>\
				          <div class="item-text"></div></div></div>';
                        }else if(proItem[i].apply==0){

                            oterHtml = '<div class="item-content"><div class="item-media"><img src="/modules/public/img/3/3C40A100010001.png"></div>\
				        <div class="item-inner">\
				          <div class="item-title-row"><div class="item-title" data-eid="'+ cartPro[m].code +'">'+ cartPro[m].name +'</div><a  href="/modules/aftersales/html/subServiceOrder.html?' +
                                'uid='+ Number( $.fn.cookie('uid'))+'&orderid='+ proItem[i].oid +'&prodcode='+ cartPro[m].code +'" class="item-after smbtn0 item-right-btn jssh">发起售后</a></div>\
				          <div class="item-subtitle">型号：' + cartPro[m].model+'     颜色：'+cartPro[m].color + '</div>\
				          <div class="item-text"></div></div></div>';
                        }

                    }
                    /*TODO 调用函数的的地方*/
                    var oterHtml = for_in( cartPro, proItem[i].oid, proItem[i].code,proItem[i].code );
					if(proItem[i].apply==1){
					 console.log($(".item-title-row").html());
					 $(".jssh").hide()

					 }else if(proItem[i].apply==0){
					 console.log(proItem[i].apply)
					 $(".jssh").show()
					 }

                    var proHtml= '<div class="list-block media-list" data-proid="'+ proItem[i].oid +'">'+
                        '<h3 class="d_num">订单号：'+ proItem[i].code +
                        '<span>'+ applyT +'</span></h3>'+
                        oterHtml +
                        '</div>';

                    $("#afterSaleList").append(proHtml);
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


    // $('.item-after').on('click', function () {

    // 	$.ajax({
    // 	type:"post",
    //        url:postUrl,
    //        contentType:"application/json",
    //        dataType:"JSON",
    //        data: JSON.stringify({
    // 	    "service": "zm3c.order.info",
    // 	    "channel": "pc",
    // 	    "sv": "1.2.300",
    // 	    "cv": "1.4.10",
    // 	    "st": "MD5",
    // 	    "sign": "xxx",
    // 	    "sn": "20160905161845672562",
    // 	    "reqData": {
    // 	        "uid": 1,
    // 	        "orderid": 260,
    // 	"prodcode":"3C40A100010001"
    // 	    },
    // 	    "ts": 149203841243
    // 	}),

    // 	success:function(msg){
    // 	 	var m =(new Function("","return "+msg))();
    //            if(m.code == 200){
    //                $.toast(m.msg);
    //            }else{
    //              $.toast(m.msg);
    //            }
    //        },
    //        error:function(){
    //          $.alert("系统繁忙请稍后再试");
    //        }
    // });




});