define(function(require, exports, module) {
  var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
  require('LIBS/sui/js/sm.min');
  require('LIBS/sui/js/sm-extend.min');
  require('public/js/menu');
    require('public/js/ajaxService');
  $.init();

    if( $.fn.cookie('phone')!==null){
        $(".gainNum").text($.fn.cookie('phone'));
    }


      
  $("#pwd").blur(function(){
    if ( !(/^[\u4e00-\u9fa5a\w]{8,32}$/).test($("#pwd").val()) ) {
      $(this).siblings("p").text("8-32位字符！").show();
    }else{
      $(this).siblings("p").hide();
    }
  });
  $("#pwdNew").blur(function(){
      if ( !(/^[\u4e00-\u9fa5a\w]{8,32}$/).test($("#pwd").val()) ) {
        $(this).siblings("p").text("8-32位字符！").show();
      }else{
        $(this).siblings("p").hide();
      }
    });
  $("#pwdNew2").blur(function(){
      if ($("#pwdNew").val()!=$("#pwdNew2").val()) { 
        $(this).siblings("p").text("两次密码不一致，请重新输入").show();
      }else{
        $(this).siblings("p").hide();
      }
    });

  // 修改密码
  $('#pwdChange').on('click', function(){  
    var phoneNo = $(".gainNum").text();
      var opwd = $("#pwd").val();
    var pwdNew = $("#pwdNew").val();
      var pwdNew2 = $("#pwdNew2").val();
    var loginStatus = true;
      if (opwd=="") {
          $("#pwd").siblings("p").text("8-32位字符！").show();
          loginStatus = false;
          return;
      }else{
          $("#pwd").siblings("p").hide();
      }
    if (pwdNew=="") { 
      $("#pwdNew").siblings("p").text("8-32位字符！").show();
      loginStatus = false;
      return;
    }else{
      $("#pwdNew").siblings("p").hide();
    }
      if (pwdNew2=="") {
          $("#pwdNew2").siblings("p").text("8-32位字符！").show();
          loginStatus = false;
          return;
      }else{
          $("#pwdNew2").siblings("p").hide();

      }
    if (pwdNew!=$("#pwdNew2").val()) { 
        $(this).siblings("p").text("两次密码不一致，请重新输入").show();
        return;
      }else{
        $(this).siblings("p").hide();
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
          "service": "zm3c.build.sign",
          "channel": "m",
          "sv": "1.2.300",
          "cv": "1.4.10",
          "st": "MD5",
          "sign": $.fn.cookie('token'),
          "sn": dateNum,
          "reqData": {
              "phone": phoneNo,
              "password": pwdNew,
              "domainCode":"test"
          },
          "ts": 149203841243
      }),
      success:function(msg){
        var m =(new Function("","return "+msg))();
          if(m.code == 200){
              var sign = m.respData.sign;
              changePD(opwd,pwdNew,sign);
          }else{
            $.toast(m.msg);
          }
      },
      error:function(){
        $.alert("系统繁忙请稍后再试");
      }
          
    });
  })


    function changePD(opd,npd,sign){
        var sha1Pd = hex_sha1(opd);
        var subSha1Pd= sha1Pd.substring(0,15)+timeStamp;
        var sha1Pd2 = hex_sha1(npd);
        var subSha1Pd2= sha1Pd2.substring(0,15);
        var uidN= parseInt($.fn.cookie("uid"));
        var tokenN = $.fn.cookie('token');
        var postData = {
            'domainCode':domainCode,
            'ui':uidN,
            'token':tokenN,
            'timestamp':timeStamp,
            'opd':md5(subSha1Pd),
            'pd':subSha1Pd2,
            'sign':sign
        };
        $.ajax({
            type:'GET',
            url:host+"login/api/local/changePassword",
            data:postData,
            async:false,
            success: function(data){
               // self.location.href="/modules/passport/html/login.html"
                if(data.code == '0'){
                    $.toast("密码修改成功，请重新登录！");
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

  $('#msgClear').on('click', function(){
      $("input").val('');    
  })  
  
  
});