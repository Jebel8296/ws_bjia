define(function(require, exports, module) {
    var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
  // require('LIBS/sui/js/sm.min');
   // require('LIBS/sui/js/sm-extend.min');
  //  require('LIBS/sui/js/sm-city-picker');
    require('public/js/ajaxService');
    $.init();


    function proListMyCart(){
        
        $.ajax({
                type:"post",
                url:postUrl,
                contentType:"application/json",
                dataType:"JSON",
                data: JSON.stringify({
                    "service": "zm3c.express.list",
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
                success:function(m){
                    var m = JSON.parse( m );
                    var num  = m.respData.length; //获取产品个数
                    if(m.code == 200){

                        for(var i=0;i<num;i++){
                            var proHtml = '<li data-id="'+ m.respData[i].eid +'"><div class="item-content">\
                                    <div class="item-inner"><div class="item-title">\
                                       收货人:<span class="oAddrName">'+ m.respData[i].name +'</span> </br>\
                                        联系电话:<span class="oAddrTel">'+ m.respData[i].phone +'</span> </br>\
                                        地　址:<span class="oAddrMsg">'+ m.respData[i].provname +' '+ m.respData[i].areaname +''+ m.respData[i].cityname +' '+ m.respData[i].address +'</span></div><div class="iradio_minimal">\
                                        <input type="radio" value="" id="1'+ i +'" name="cart-pro" class="regular-radio" data-status="'+ m.respData[i].status +'">\
                                        <label class="addr-sel-this"><em></em>默认地址</label>\
                                    </div>\
                                    <div class="item-after">\
                                    <a href="/modules/purchase/html/address_alter.html?id='+m.respData[i].eid+'&name='+m.respData[i].name+'&phone='+m.respData[i].phone+'&address='+m.respData[i].address+'&zipcode='+m.respData[i].zipcode+'&cityname='+m.respData[i].provname+' '+m.respData[i].cityname+' '+m.respData[i].areaname+'" class="color3 addrChange">修改</a>&nbsp;&nbsp;<a href="javascript:;" class="color3 confirm-ok">删除</a></div></div></div></li>';

                            $("#addrList").append(proHtml);
                            if( m.respData[i].status=="1"){
                                $("input[type='radio']").prop("checked","checked");
                            }
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
                            
        });
    }
    proListMyCart();


    // 设置默认地址
    $(document).on('click','.addr-sel-this', function () {
        var eid = $(this).parents("li").data("id");
        var dstatu = $(this).siblings("input").data("status");
        var value = $("#defaultAddr").prop("checked");
        if(!value){
            $(this).siblings("input").prop("checked",true);
            $(this).find("em").addClass("checked");
            $(this).parents("li").siblings().find("em").removeClass();
        }else{
            $(this).siblings("input").prop("checked",false);
            $(this).find("em").removeClass();
        }
        $.ajax({
            type:"post",
            url:postUrl,
            contentType:"application/json",
            dataType:"JSON",

            data: JSON.stringify({
                "service": "zm3c.express.def",
                "channel": "m",
                "sv": "1.2.300",
                "cv": "1.4.10",
                "pn": "xxx",
                "st": "MD5",
                "sign": $.fn.cookie('token'),
                "sn": dateNum,
                "reqData": {
                    "uid": Number( $.fn.cookie('uid')),
                    "eid": eid
                }
            }),
            success:function(msg){
              var m =(new Function("","return "+msg))();
                if(m.code == 200){

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
    })

    //是否删除
    $(document).on('click','.confirm-ok', function () {
        var list =$(this).parents("li");  
        var eid = $(this).parents("li").data("id");
        $.confirm('确定将地址删除吗?', function () {
            $.ajax({
                type:"post",
                url:postUrl,
                contentType:"application/json",
                dataType:"JSON",

                data: JSON.stringify({
                    "service": "zm3c.express.del",
                    "channel": "m",
                    "sv": "1.2.300",
                    "cv": "1.4.10",
                    "pn": "xxx",
                    "st": "MD5",
                    "sign": $.fn.cookie('token'),
                    "sn": dateNum,
                    "reqData": {
                        "eid": eid,
                        "uid": Number( $.fn.cookie('uid'))
                    }    
                }),
                success:function(msg){
                  var m =(new Function("","return "+msg))();
                    if(m.code == 200){
                        // setTimeout('window.location.href = "/modules/purchase/html/address.html"', 5000);
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

            $(list).remove();       
        });
    });
});