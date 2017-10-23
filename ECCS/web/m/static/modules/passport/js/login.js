define(function(require, exports, module) {
  var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
  require('LIBS/sui/js/sm.min');
  require('LIBS/sui/js/sm-extend.min');
  require('public/js/menu');
  require('public/js/ajaxService');
  $.init();

  //login.html  登录
  // $(document).on("pageInit", "#loginCont", function(e, id, page){

  if( $.fn.cookie('remPhone')!==null){
    $("#phone").val($.fn.cookie('remPhone'));
    $(".sel-this").find("em").addClass("checked");
  }



    $("#phone").blur(function(){
      if (!$("#phone").val().match(/^1[34578]\d{9}$/)) { 
        $(this).siblings("p").text("手机号码格式不正确！").show();
      }else{
        $(this).siblings("p").hide();
      }
    });
          
    $("#pwd").blur(function(){
      if ( !(/^[\u4e00-\u9fa5a\w]{8,32}$/).test($("#pwd").val()) ) {
        $(this).siblings("p").text("8-32位字符！").show();
      }else{
        $(this).siblings("p").hide();
      }
    });
    $(".sel-this").click(function(){
      var phoneN = $("#phone").val();
      $.fn.cookie('remPhone',phoneN,{path:'/'});
      var value = $(this).find("input").attr("checked");
      if(!value){
        $(this).find("em").addClass("checked");
        $.fn.cookie('remPhone',phoneN,{path:'/'});
      }else{
          $(this).find("em").removeClass();
        $.fn.cookie('remPhone', '0', { expires: -1, path:'/' });

      }
    })

  // 点击登录
  /*$('#login-btn').on('click', function(){
    var phoneNo = $("#phone").val();
    var pwd = $("#pwd").val();
    var loginStatus = true;
    if (!phoneNo.match(/^1[34578]\d{9}$/)) {
      $("#phone").siblings("p").text("手机号码格式不正确！").show();
      loginStatus = false;
    }else{
      $("#phone").siblings("p").hide();
    }
    if ( !(/^[\u4e00-\u9fa5a\w]{6,32}$/).test(pwd) ) {
      $("#pwd").siblings("p").text("6-32位字符！").show();
      loginStatus = false;
    }else{
      $("#pwd").siblings("p").hide();
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
        "service":"zm3c.user.login.cn",
        "channel": "m",
        "reqData":{
          "phone":phoneNo,
          "password":pwd
        }
      }),
      success:function(msg){
        var m =(new Function("","return "+msg))();
        if(m.code == 200){
          $.toast(m.msg);
          setTimeout('window.location.href = "/modules/home/html/index.html"', 5000);
        }else{
          $.toast(m.msg);
        }
      },
      error:function(){
        $.alert("系统繁忙请稍后再试");
      }

    });
  })*/

    // 获取sign
    $('#login-btn').on('click', function(){
      var phoneNo = $("#phone").val();
      var pwd = $("#pwd").val();
      var loginStatus = true;
      if (!phoneNo.match(/^1[34578]\d{9}$/)) {
        $("#phone").siblings("p").text("手机号码格式不正确！").show();
        loginStatus = false;
      }else{
        $("#phone").siblings("p").hide();
      }
      if ( !(/^[\u4e00-\u9fa5a\w]{8,32}$/).test(pwd) ) {
        $("#pwd").siblings("p").text("8-32位字符！").show();
        loginStatus = false;
      }else{
        $("#pwd").siblings("p").hide();
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
          "reqData":{
          "phone":phoneNo,
          "password":pwd
          }
        }),
        success:function(msg){
          var m =(new Function("","return "+msg))();
            if(m.code == 200){
              var sign = m.respData.sign;
              loginForLocal(phoneNo,pwd,sign);
             // setTimeout('window.location.href = "/modules/home/html/index.html"', 1000);
            }else if(m.code == 506){
              $.toast("请重新登录");
              self.location.href="/modules/passport/html/login.html";
            }else{
              $.toast(m.msg);
            }
        },
        error:function(){
          $.alert("系统繁忙请稍后再试");
        }

      });
    });

  //登陆
  function loginForLocal(un,pd,sign){
    var sha1Pd = hex_sha1(pd);
    var subSha1Pd= sha1Pd.substring(0,15)+timeStamp;
    var dataForL = {'un':un, 'pd':md5(subSha1Pd),'timestamp':timeStamp,'domainCode':domainCode, 'sign':sign};
    $.ajax({
      type:'GET',
      url:host+"login/api/local/login",
      data:dataForL,
      async:false,
      success: function(data){

        if(data.code == '0'){
          $.fn.cookie('token',data.obj.token,{path:'/'});
          $.fn.cookie('uid',data.obj.user_id,{path:'/'});
          $.fn.cookie('phone',data.obj.user_name,{path:'/'});
          self.location.href="/modules/home/html/index.html";
        }else{
          $.alert(data.msg);
        }
      },
      error : function(jqXHR, textStatus, errorThrown) {
        $.alert("请稍后再试！");
      }
    });
  }

  // });



  // $(document).on("pageInit", "#forgetPwdCont", function(e, id, page){
    $("#phone").blur(function(){
            if (!$("#phone").val().match(/^(((13[0-9]{1})|159|153)+\d{8})$/)) { 
        $(".vCode").next("p").text("手机号码格式不正确！").show();
            }else{
        $(this).parent().siblings("p").hide();
      }
    });
    $("#dxvCode").blur(function(){
            if (!$("#dxvCode").val().match(/^(((13[0-9]{1})|159|153)+\d{8})$/)) { 
        $(this).siblings("p").text("输入验证码").show();
            }else{
        $(this).siblings("p").hide();
      }
    });
//  $('.btn_org').on('click', function(){  
//    if (!$("#phone").val().match(/^(((13[0-9]{1})|159|153)+\d{8})$/)) { 
//      $(".vCode").next("p").text("手机号码格式不正确！").show();
//          }else{
//      $(".vCode").next("p").hide();
//    }
//    $.ajax({
//      type:"get",
//      url:"login.json",
//      data:{},
//      dataType:"json",
//      success:function(){ 
//        $.toast("修改成功")
//      // window.location="login.html";    
//
//      },
//      error:function(){
//        $.toast("失败")
//      }
//    })
//  });
  // });

function getSign(){
  $.ajax({
    type:"post",
    url:postUrl,
    contentType:"application/json",
    dataType:"JSON",
    data: JSON.stringify({
      "service":"zm3c.build.sign",
      "channel": "m",
      "reqData":{
        'domainCode ':domainCode,
        'callback ':callBackUrl
      }
    }),
    success:function(msg){
      var m =(new Function("","return "+msg))();
      if(m.code == 200){
        var sign = m.respData.sign;
        var weichartU = "http://api6.wwoqu.com/login/api/third/wechat?domainCode=test&callback="+callBackUrl+"&sign="+sign;
        $("#weichart").href=weichartU
      }else{
        $.toast(m.msg);
      }
    },
    error:function(){
      $.alert("系统繁忙请稍后再试");
    }
  });
}


  var getS ={ 'domainCode ':domainCode,'callback ':callBackUrl};
  //第三方登陆
  $("#weichart").on("click",function(){
    $.ajax({
      type:"post",
      url:postUrl,
      contentType:"application/json",
      dataType:"JSON",
      data: JSON.stringify({
        "service":"zm3c.build.sign",
        "channel": "m",
        "reqData":{
          'domainCode ':domainCode,
          'callback ':callBackUrl
        }
      }),
      success:function(msg){
        var m =(new Function("","return "+msg))();
        if(m.code == 200){
          var sign = m.respData.sign;
          var weichartU = "http://api6.wwoqu.com/login/api/third/wechat?domainCode=test&callback="+callBackUrl+"&sign="+sign;
         // location.href(weichartU);
          window.location= weichartU;
          //window.open(weichartU,'newwindow', 'height=100', 'width=300', 'top=0', 'left=0', 'toolbar=no', 'menubar=no', 'scrollbars=no', 'resizable=no','location=no', 'status=no');
        }else{
          $.toast(m.msg);
        }
      },
      error:function(){
        $.alert("系统繁忙请稍后再试");
      }
    });
  })
  /*
  function loginOk(localUrl){
    var Request=new UrlSearch(decodeURI(localUrl)); //实例化
    if(Request.code == '0'){
     // $.cookie('token',Request.token);
     // $.cookie('uid',Request.user_id);
      //$.cookie('phone',Request.user_name);
     // location.reload();
    }else{
      $.alert(Request.msg);
    }
  }
*/
});