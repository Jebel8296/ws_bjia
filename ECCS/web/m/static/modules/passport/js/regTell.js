define(function(require, exports, module) {
  var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
  require('LIBS/sui/js/sm.min');
  require('LIBS/sui/js/sm-extend.min');
  require('public/js/menu');
    require('public/js/ajaxService');
  $.init();


  var reg_name=/^[\u4e00-\u9fa5]{2,8}$/;//姓名
  var reg_idCard = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;//身份证号
  var reg_tel = /^(((13[0-9]{1})|159|153)+\d{8})$/; //手机号
  var reg_zipCode = /^[\d]{6}$/;//邮政编码
  var reg_pwd = /^[\u4e00-\u9fa5a\w]{8,32}$/;//密码8-32位
  var reg_mail = /^(\w+@\w+){1,1}(\.\w+){1,2}$/;//邮箱
 
  //register.html  注册
  // $(document).on("pageInit", "#regCont", function(e, id, page){
        
        $("#phone").blur(function(){
            if (!$("#phone").val().match(/^1[34578]\d{9}$/)) { 
            $(this).siblings("p").text("手机号码格式不正确！").show();
                }else{
            $(this).siblings("p").hide();
        }
      });

        $("#yzID").blur(function(){
            if (!$("#yzID").val().match(/^\d{4}$/)) { 
        $(this).parent().next("p").text("请输入正确的验证码！").show();
            }else{
        $(this).parent().next("p").hide();
      }
        });
          
        $("#pwd").blur(function(){
          if ( !(/^[\u4e00-\u9fa5a\w]{8,32}$/).test($("#pwd").val()) ) {
            $(this).siblings("p").text("8-32位字符！").show();
                }else{
            $(this).siblings("p").hide();
          }
        });

        $("#pwd2").blur(function(){
          if ($("#pwd2").val()=="") { 
            $("#pwd2").next("p").text("再次输入密码！").show();
          }else if($("#pwd2").val()!=$("#pwd").val()){
            $("#pwd2").next("p").text("两次密码输入不一致，请重新输入！").show();
          }else{
            $("#pwd2").next("p").hide();
          }
        });
      $(".sel-this").click(function(){
      var value = $(".sel-this").siblings("input").attr("checked");
      if(!value){
        $(this).find("em").addClass("checked");
        $(".btn_Tg").addClass("btn_org");
      }else{
        $(this).find("em").removeClass();
        $(".btn_Tg").removeClass("btn_org");
        $.toast("请选择同意阅读条款");
       }
      })
     
      //点击发送验证码
      $('#sendCode').on('click', function(){
          var phoneNo = $("#phone").val();
          var sendS = true;
          if(phoneNo == ""){
              $("#phone").siblings("p").text("手机号码不能为空！").show();
              sendS = false;
          }else if (!phoneNo.match(/^1[34578]\d{9}$/)) {
              $("#phone").siblings("p").text("手机号码格式不正确！").show();
              sendS = false;
          }else{
              $("#phone").siblings("p").hide();
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
                      "sn": dateNum,
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
          }

          // }
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
        var dataForX = {'un':phone, 'content':'验证码：%verifycode%', 'type':1, 'domainCode':domainCode, 'sign':sign};
        $.ajax({
            type:'GET',
            url:host+"login/api/local/smsWithNoCaptcha",
            data:dataForX,
            async:false,
            success: function(data){
                if(data.code == '0'){
                }else{
                    $.alert(data.msg);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                $.alert("请稍后再试！");
            }
        });
    }


    //点击注册
    $('#reg-btn').on('click', function(){
       /* var isStyle =  $("#iradio_minimal .sel-this").hasClass(".checked");
        console.log(isStyle)
        if(!isStyle){
            $.toast("请选择同意阅读条款");
            loginStatus = false;
        }else{
            loginStatus = true
        }*/

        var phoneNo = $("#phone").val();
        var yzID = $("#yzID").val();
        var pwd = $("#pwd").val();
        var pwd2 = $("#pwd2").val();
       // var value = $(".sel-this").siblings("input").attr("checked");
        var loginStatus = true;
        if (!phoneNo.match(/^1[34578]\d{9}$/)) {
            $("#phone").siblings("p").text("手机号码格式不正确！").show();
            loginStatus = false;
        }else{
            $("#phone").siblings("p").hide();
        }
        if (!yzID.match(/^\d{4}$/)) {
            $(".vCode").next("p").text("请输入正确的验证码！").show();
        }else{
            $(".vCode").next("p").hide();
        }
        if ( !(/^[\u4e00-\u9fa5a\w]{8,32}$/).test(pwd) ) {
            $("#pwd").siblings("p").text("8-32位字符！").show();
            loginStatus = false;
        }else{
            $("#pwd").siblings("p").hide();
        }
        if (pwd2=="") {
            $("#pwd2").next("p").text("再次输入密码！").show();
        }else if(pwd2!=pwd){
            $("#pwd2").next("p").text("两次密码输入不一致，请重新输入！").show();
        }else{
            $("#pwd2").next("p").hide();
        }
        var value = $(".sel-this").siblings("input").attr("checked");
        if(!value){
            $.toast("请选择同意阅读条款");
            return false;
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
                    "phone": phoneNo,
                    "password": pwd,
                    "domainCode":"test",
                    "code": yzID
                },
                "ts": 149203841243

            }),
            success:function(msg){
                var m =(new Function("","return "+msg))();
                if(m.code == 200){
                    var sign = m.respData.sign;
                    reginForLocal(phoneNo,pwd,yzID,sign);
                }else{
                    $.toast(m.msg);
                }
            },
            error:function(){
                $.alert("系统繁忙请稍后再试");
            }

        });

    });
    //统一注册之本地
    function reginForLocal(un,pd,code,sign){
        var sha1Pd = hex_sha1(pd);
        var subSha1Pd= sha1Pd.substring(0,15);
        var dataForX = {'un':un, 'pd':subSha1Pd,'code':code,'domainCode':domainCode, 'sign':sign};
        $.ajax({
            type:'GET',
            url:host+"login/api/local/mobileRegistWithSms",
            data:dataForX,
            async:false,
            success: function(data){
                if(data.code == '0'){
                    //$.alert("注册成功");
                   // $.alert(data.obj.user_id);
                    //$.alert(data.obj.token);
                    $.toast("注册成功");
                    $.fn.cookie('token',data.obj.token,{path:'/'});
                    $.fn.cookie('uid',data.obj.user_id,{path:'/'});
                    $.fn.cookie('phone',data.obj.user_name,{path:'/'});
                    setTimeout("window.location.href = '/modules/passport/html/login.html'",1000);
                }else{
                    $.alert(data.msg);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                $.alert("请稍后再试！");
            }
        });

    }
      //点击注册
    /*$('#reg-btn').on('click', function(){
        var phoneNo = $("#phone").val();
        var yzID = $("#yzID").val();
        var pwd = $("#pwd").val();
        var pwd2 = $("#pwd2").val();
        var loginStatus = true;
        if (!phoneNo.match(/^1[34578]\d{9}$/)) {
            $("#phone").siblings("p").text("手机号码格式不正确！").show();
            loginStatus = false;
        }else{
            $("#phone").siblings("p").hide();
        }
        if (!yzID.match(/^\d{4}$/)) {
            $(".vCode").next("p").text("请输入正确的验证码！").show();
        }else{
            $(".vCode").next("p").hide();
        }
        if ( !(/^[\u4e00-\u9fa5a\w]{6,32}$/).test(pwd) ) {
            $("#pwd").siblings("p").text("6-32位字符！").show();
            loginStatus = false;
        }else{
            $("#pwd").siblings("p").hide();
        }
        if (pwd2=="") {
            $("#pwd2").next("p").text("再次输入密码！").show();
        }else if(pwd2!=pwd){
            $("#pwd2").next("p").text("两次密码输入不一致，请重新输入！").show();
        }else{
            $("#pwd2").next("p").hide();
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
                "service":"zm3c.user.register",
                "channel": "m",
                "sv": "1.2.300",
                "cv": "1.4.10",
                "st": "MD5",
                "sign": "xxx",
                "sn": "20160905161845672562",
                "reqData": {
                    "phone": phoneNo,
                    "password": pwd,
                    "smssn": "2410152012682903",
                    "authcode": "541022"
                },
                "ts": 149203841243

            }),
            success:function(msg){
                var m =(new Function("","return "+msg))();
                if(m.code == 200){
                    $.toast(m.msg);
                    console.log(m)
                    setTimeout('window.location.href = "/modules/passport/html/login.html"', 5000);
                }else{
                    $.toast(m.msg);
                }
            },
            error:function(){
                $.alert("系统繁忙请稍后再试");
            }

        });

    });
  // });*/
});

  