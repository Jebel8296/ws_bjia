define(function(require, exports, module) {
    var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
  // require('LIBS/sui/js/sm.min');
   // require('LIBS/sui/js/sm-extend.min');
  //  require('LIBS/sui/js/sm-city-picker');
    require('public/js/ajaxService');
    $.init();


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
                                        地　址:<span class="oAddrMsg">'+ m.respData[i].provname +' '+ m.respData[i].areaname +''+ m.respData[i].cityname +' '+ m.respData[i].address +'</span></div>\
                                    <div class="item-after"><input type="radio"  value="" id="'+ i +'" name="cart-pro" class="regular-radio" data-status="'+ m.respData[i].status +'">\
                                        <label class="addr-sel-this"><em></em></label>\
                                    </div></div></div></li>';
                            $("#addrList").append(proHtml);
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


    //点击选择按钮
    $(document).on('click','.addr-sel-this', function () {
        var value = $(this).prev("input").prop('checked'); //判断是否选中
        var selValue; //设置当前选中状态标识
        var instanceid = $(this).parents("li").data("id");  //获取商品id
         window.history.go(-1);
        if(!value){
            $(this).prev("input").prop('checked',true);  //按钮选中状态
            $(this).find("em").addClass("checked");
              localStorage.setItem('eid',instanceid);
            selValue = $(this).prev("input").prop('checked');  //记录是否选中

        }else{
            $(this).prev("input").prop('checked',false); //按钮未选中状态
            $(this).find("em").removeClass();
            selValue = $(this).prev("input").prop('checked'); //记录是否选中
            var allSel =$('#cart-list input:checked').length;
            if(!allSel){
                $("#J_SelectAll dfn").text("").hide();
            }
        }
    });





});