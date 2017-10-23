define(function(require, exports, module) {
  var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
  require('LIBS/sui/js/sm.min');
  require('LIBS/sui/js/sm-extend.min');
  require('public/js/menu');
    require('public/js/ajaxService');
  $.init();

    //点击发送验证码
    $('#sendCode').on('click', function(){
        //发送验证码
        var phoneNo = $("#phone").val();
        var InterValObj; //timer变量，控制时间
        var count = 30; //间隔函数，1秒执行
        var curCount;//当前剩余秒数

       if(phoneNo==0){
          $(this).parent().next("p").text("手机号不能为空").show();
          return;
        }else if(!$("#phone").val().match(/^1[34578]\d{9}$/)) {
           $(this).parent().next("p").text("手机号码格式不正确！").show();
           return;
        }else{
           $(this).parent().next("p").hide();
        }

        //timer处理函数
        function SetRemainTime() {
          if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器
            $("#sendCode").removeAttr("disabled");//启用按钮
            $("#sendCode").val("重新发送验证码");
          }
          else {
            curCount--;
            $("#sendCode").val("请在" + curCount + "秒内输入");
          }
        }

        curCount = count;
      　//设置button效果，开始计时
        $("#sendCode").attr("disabled", "true");
        $("#sendCode").val("请在" + curCount + "秒内输入");
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
      　//向后台发送处理数据
        $.ajax({
            type:"post",
            url:postUrl,
            contentType:"application/json",
            dataType:"JSON",
            data: JSON.stringify({
                "service": "zm3c.build.sign",
                "channel": "m",
                "sv": "1.2.300",
                "cv": "1.4.10",
                "pn": "xxx",
                "st": "MD5",
                "sign": "xxx",
                "sn": "20160905161845672562",
                "reqData": {
                    "phone": phoneNo
                }
            }),
            success:function(msg){
                var m =(new Function("","return "+msg))();
                if(m.code == 200){
                    var sign = m.respData.sign;
                    postToX(phoneNo,sign)
                }else{
                    $.toast(m.msg);
                }
            },
            error:function(){
                $.alert("系统繁忙请稍后再试");
            }

        });
    });

    //获取验证码
    function postToX(phone,sign){
        //发送验证码
        var InterValObj; //timer变量，控制时间
        var count = 30; //间隔函数，1秒执行
        var curCount;//当前剩余秒数

        //timer处理函数
        function SetRemainTime() {
            if (curCount == 0) {
                window.clearInterval(InterValObj);//停止计时器
                $("#sendCode").removeAttr("disabled");//启用按钮
                $("#sendCode").val("重新发送验证码");
            }
            else {
                curCount--;
                $("#sendCode").val("请在" + curCount + "秒内输入");
            }
        }
        // function sendMessage() {
        curCount = count;
        //设置button效果，开始计时
        $("#sendCode").attr("disabled", "true");
        $("#sendCode").val("请在" + curCount + "秒内输入");
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
        var dataForX = {'un':phone, 'content':'验证码：%verifycode%', 'type':5, 'domainCode':domainCode, 'sign':sign};
        $.ajax({
            type:'GET',
            url:host+"login/api/local/smsWithNoCaptcha",
            data:dataForX,
            async:false,
            success: function(data){
                if(data.code == '0'){
                    console.log(data)
                }else{
                    $.alert(data.msg);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                $.alert("请稍后再试！");
            }
        });
    }





    //点击确定按钮
    $('#pwdforgetSubmit').on('click', function(){
        var phone = $("#phone").val();
        var dxvCode = $("#dxvCode").val();
        var pwdNew = $("#pwdNew").val();
        var pwdNew2 = $("#pwdNew2").val();
        var loginStatus = true;
        if (!phone.match(/^1[34578]\d{9}$/)) {
            $(".vCode").next("p").text("手机号码格式不正确！").show();
            loginStatus = false;
        }else{
            $(".vCode").next("p").hide();
        }
        if (dxvCode == "") {
            $("#dxvCode").next("p").text("验证码不能为空！").show();
        }else{
            $("#dxvCode").next("p").hide();
        }
        if ( !(/^[\u4e00-\u9fa5a\w]{8,32}$/).test(pwdNew) ) {
            $("#pwdNew").siblings("p").text("8-32位字符！").show();
            loginStatus = false;
        }else{
            $("#pwdNew").siblings("p").hide();
        }
        if (pwdNew2=="") {
            $("#pwdNew2").next("p").text("再次输入密码！").show();
        }else if(pwdNew2!=pwdNew){
            $("#pwdNew2").next("p").text("两次密码输入不一致，请重新输入！").show();
        }else{
            $("#pwdNew2").next("p").hide();
        }
        if( !loginStatus ){
            return;
        }

        $.ajax({
            type:"post",
            url:postUrl,
            contentType:"application/json",
            dataType:"JSON",
            data: JSON.stringify({
                "service":"zm3c.build.sign",
                "channel": "m",
                "sv": "1.2.300",
                "cv": "1.4.10",
                "st": "MD5",
                "sign": "xxx",
                "sn": dateNum,
                "reqData": {
                    "phone": phone,
                    "password": pwdNew,
                    "domainCode":"test",
                    "code": dxvCode
                },
                "ts": 149203841243

            }),
            success:function(msg){
                var m =(new Function("","return "+msg))();
                if(m.code == 200){
                    var sign = m.respData.sign;
                    postToFroget(phone,pwdNew,dxvCode,sign);
                }else{
                    $.toast(m.msg);
                }
            },
            error:function(){
                $.alert("系统繁忙请稍后再试");
            }

        });

    });
    function postToFroget(phone,npd,code,sign){
        var sha1Pd = hex_sha1(npd);
        var subSha1Pd= sha1Pd.substring(0,15);
        $.ajax({
            type:'GET',
            url:host+"login/api/local/resetPasswordBySms",
            data:{
                'domainCode':domainCode,
                'un':phone,
                'code':code,
                'pd':subSha1Pd,
                'sign':sign
            },
            async:false,
            success: function(data){
                if(data.code == '0'){
                    console.log(data)
                    setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
                }else{
                    $.alert(data.msg);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                $.alert("请稍后再试！");
            }
        });

    }




});