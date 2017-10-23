'use strict'
zhongmai.controller('LoginCtrl',function($scope,$rootScope,$http,$location,$state,localStorageService,$timeout,$interval,$cookieStore,$window,$q){

    if($cookieStore.get("uid") != null){
      //  console.log($cookieStore.get("uid"))
        $scope.jsCart = true;
        $scope.isShow= false;
    }else{
       $scope.isShow= true;
    }
    // 二级导航显示
    $(".mWatch").on("mouseenter",function(){
        $(".secNav1").slideDown(function(){
            $(".secNav1").stop(true);
        })//.stop(true,true);
        return;
    })
    $(".mWatch").on("mouseleave",function(){
        $(".secNav1").slideUp(function(){
            $(".secNav1").stop(true,true);
        });
        return;
    })

    $(".mHeadser").on("mouseenter",function(){
        $(".secNav2").slideDown(function(){
            $(".secNav2").stop(true);
        })//.stop(true,true);
        return;
    })
    $(".mHeadser").on("mouseleave",function(){
        $(".secNav2").slideUp(function(){
            $(".secNav2").stop(true,true);
        });
        return;
    })
    $(".mHeadser").on("mouseenter",function(){
        $(".secNav2").slideDown(function(){
            $(".secNav2").stop(true);
        })//.stop(true,true);
        return;
    })

    //滑过购物车
    $(".cartItem").on("mouseleave",function(){
        $(".carNull").slideUp(function(){
            $(".carNull").stop(true,true);
        });
        return;
    })
    $(".cartItem").on("mouseenter",function(){
        $(".carNull").slideDown(function(){
            $(".carNull").stop(true);
        })//.stop(true,true);
        return;
    })
    $scope.showLR=function(text){
        if(text == "login"){
            $("#logTitil").text("登陆");
            $("#loginForm").show();
            $("#userForm").hide();
        }
        if(text == 'regin'){
            $("#logTitil").text("注册");
            //$(".js-error").text("");
            $("#loginForm").hide();
            $("#userForm").show();
        }
    }

    // jquery 点击登录
    $('#login-btn').on('click', function(){
        var phoneNo = $("#userPhone").val();
        var pwd = $("#userPwd").val();
        var loginStatus = true;
        if (!regPhone.test(phoneNo)) {
            $("#userPhone").siblings("p").text("手机号码格式不正确！").show();
            loginStatus = false;
        }else{
            $("#userPhone").siblings("p").hide();
        }
        if ( !(/^[\u4e00-\u9fa5a\w]{6,32}$/).test(pwd) ) {
            $("#userPwd").siblings("p").text("8-32位字符！").show();
            loginStatus = false;
        }else{
            $("#userPwd").siblings("p").hide();
        }

        if( !loginStatus ){
            return false;
        }

        var sendparams ={"un":phoneNo,"pd":pwd};
        var json ={
            "service":"zm3c.build.sign",
            "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
            "reqData":sendparams
        };
        $http({
            method : 'POST',
            url : posturl,
            data: json,
            header:headers
        }).success(function(data) {
            //console.log(JSON.stringify(data));
            if(data.code==200){
                var sign = data.respData.sign;
                loginForLocal(phoneNo,pwd,sign);
            }else if(data.code==500){
                $(".js-error").text(data.msg);
                return false;
            }
            //调用购物车
            getCartData();
        })
    });

    //页面加载后，input为空
    $scope.reginU={
        "phone":"","code":"","pwd":"","pwd2":""
    }
    $scope.userPhone="";
    if($scope.rebpwd){
        $scope.userPhone=$cookieStore.get("userPhone");
    }
    $scope.userPwd="";
    $scope.submitted = false;


    function loginForLocal(un,pd,sign){
        var sha1Pd = hex_sha1(pd);
        var subSha1Pd= sha1Pd.substring(0,15)+timeStamp;
        var dataForL = {'un':un, 'pd':md5(subSha1Pd),'timestamp':timeStamp,'domainCode':domainCode, 'sign':sign};
        $.ajax({
            type:'GET',
            url:host+"/login/api/local/login",
            data:dataForL,
            async:false,
            success: function(data){
                //console.log(JSON.stringify(data));
                if(data.code == '0'){
                    $('#login').modal('hide');
                    $cookieStore.put("uid",data.obj.user_id);
                    $cookieStore.put("token",data.obj.token);
                    $cookieStore.put("phone",data.obj.user_name);
                    $rootScope.nickname=data.obj.user_name;
                }else{
                    alert(data.msg);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
               alert("网络异常，请稍后再试！");
            }
        });
    }

    $rootScope.waitButTotal=0;
    function getCartData(){
        //表单字段
        var orderList = {'uid':Number($cookieStore.get("uid"))}
        sign = $cookieStore.get("token");
        //所有数据
        var json ={
            "service":"zm3c.cart.list",
            "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
            "reqData":orderList
        };
        //post
        $http({
            method : 'POST',
            url : posturl,
            data: json,
            header:headers
        }).success(function(data) {
            if(data.code==200){
                $cookieStore.put("cartLength",data.respData.data.length);
                location.reload();
                $rootScope.cartLength = data.respData.data.length;
                for(var i=0;i<data.respData.data.length;i++){
                    if(data.respData.data[i].checked){
                        $rootScope.waitButTotal++;
                    }
                }
            }
        })
    }
    //注册
    $scope.addUsers = function (item) {
        //表单正常提交
        if($scope.userForm.$valid){
            //var passwordSha = hex_sha1('mima123465')
            var pwd = $(".pwd").val(), pwd2 = $(".pwd").val();
            if(pwd != pwd2){
                 alert("密码不一致，请重新输入");
                return false
            }
            var sendparams ={ 'phone':item.phone,'password':item.pwd,'domainCode':domainCode,"code": item.code};
            var json ={
                "service":"zm3c.build.sign",
                "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
                "reqData":sendparams
            };
            //post
            $http({
                method : 'POST',
                url : posturl,
                data: json,
                header:headers
            }).success(function(data) {
                if(data.code==200){
                    var sign = data.respData.sign;
                    reginForLocal(item.phone,item.pwd,item.code,sign);
                }else if(data.code==500){
                    alert("系统繁忙请稍后再试");
                    return false;
                }
            })
        }
        else{
            $scope.submitted = true;
        }
    };
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
                    alert("注册成功");
                   // console.log(data);
                    $cookieStore.put("uid",data.obj.user_id);
                    $cookieStore.put("token",data.obj.token);
                    $cookieStore.put("phone",data.obj.user_name);
                 //   $rootScope.nickname=data.obj.user_name;

                   // alert(data.obj.user_id);
                   // alert(data.obj.token);
                    $scope.showLR("login");
                }else{
                    alert(data.msg);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                alert("请稍后再试！");
            }
        });

    }

$scope.test = function(){
    $scope.userPhone = $cookieStore.get('userPhone');
}


    $scope.rebpwd = false;
    $scope.remeberUser = function(phone){
        if($scope.rebpwd){
            $cookieStore.put('userPhone',phone);
        }else{
            $cookieStore.remove("userPhone");
        }
    }
    $scope.getCookieValue = function (name)
    {
        var name = escape(name);
        var allcookies = document.cookie;
        name += "=";
        var pos = allcookies.indexOf(name);
        if (pos != -1){
            var start = pos + name.length;
            var end = allcookies.indexOf(";",start);
            if (end == -1) end = allcookies.length;
            var value = allcookies.substring(start,end);
            return (value);
        }else{
            return "";
        }
    } ;

    //跳转到忘记密码
    $scope.tofindpwd = function(){
        $location.path('/forgetpw');
        $('#login').modal('hide');
    }

    $scope.logout = function(){
        //$state.go('home');
        $state.go('home');
        localStorageService.remove('cartLength');
        $cookieStore.remove("uid");
        $cookieStore.remove("phone");
        $cookieStore.remove("token");
        $cookieStore.remove("cartLength");
        $rootScope.nickname=null;
        $rootScope.cartLength=null;
        //location.reload();
    }

    $scope.paracont = "获取验证码";
    $scope.paraclass = "but_null";
    $scope.paraevent = false;
    //获取sign
    $scope.getCode = function(phone){
        if(!regPhone.test(phone)){
            alert('请输入11位手机号码！');
            return false;
        }else{
            var dataForJ = {'un':phone};
            var allData ={
                "service":"zm3c.build.sign",
                "reqData":dataForJ
            };
            $http({
                method : 'POST',
                url : posturl,
                data: allData,
                header:headers
            }).success(function(data) {
                if(data.code==200){
                 var sign =  data.respData.sign;
                    postToX(phone,sign);
                }else if(data.code==500){
                    alert("获取验证码失败，请稍后再试！");
                    return false;
                }
            })
        }
    }
    //获取验证码
    function postToX(phone,sign){
        var dataForX = {'un':phone, 'content':'验证码：%verifycode%', 'type':typeX, 'domainCode':domainCode, 'sign':sign};
        var second = 60, timePromise = undefined;
        timePromise = $interval(function(){
            if(second<=0){
                $interval.cancel(timePromise);
                timePromise = undefined;
                second = 60;
                $scope.paracont = "重发验证码";
                $scope.paraevent = false;
                $scope.paraclass = "but_null";
            }else{
                $scope.paracont = second + "秒后可重发";
                $scope.paraclass = "not but_null";
                $scope.paraevent = true;
                second--;
            }
        },1000,100);
        
        $.ajax({
            type:'GET',
            url:host+"login/api/local/smsWithNoCaptcha",
            data:dataForX,
            async:false,
            success: function(data){
                if(data.code == '0'){
                }else{
                    alert(data.msg);
                    $interval.cancel(timePromise);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                alert("请稍后再试！");
                $interval.cancel(timePromise);
            }
        });
    }




    var callBackUrl = "http://localhost:63342/pcbackup/localUrl.html";
    var getS ={ 'domainCode ':domainCode,'callback ':callBackUrl};
      $scope.weiChat = function(){
        var json ={
            "service":"zm3c.build.sign",
            "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
            "reqData":getS
        };
        //post
        $http({
            method : 'POST',
            url : posturl,
            data: json,
            header:headers
        }).success(function(data) {
            if(data.code==200){
                var sign = data.respData.sign;
                var weichartU = "http://api6.wwoqu.com/login/api/third/wechat?domainCode=test&callback="+callBackUrl+"&sign="+sign+"&type=register";
                window.open(weichartU,'newwindow', 'height=100', 'width=300', 'top=0', 'left=0', 'toolbar=no', 'menubar=no', 'scrollbars=no', 'resizable=no','location=no', 'status=no');
                $("#login").modal("hide");
                //location.reload();
            }else if(data.code==500){
                alert("系统繁忙请稍后再试");
                return false;
            }
        })
    }


    $scope.qqLogin = function(){
        //var sendparams ={ 'domainCode ':domainCode,'callback ':callBackUrl};
        var json ={
            "service":"zm3c.build.sign",
            "reqData":getS,
            "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn
        };
        //post
        $http({
            method : 'POST',
            url : posturl,
            data: json,
            header:headers
        }).success(function(data) {
            if(data.code==200){
                var sign = data.respData.sign;
                var weichartU = "http://api6.wwoqu.com/login/api/third/qq?domainCode=test&callback="+callBackUrl+"&sign="+sign;
                window.open(weichartU,'newwindow', 'height=100', 'width=300', 'top=0', 'left=0', 'toolbar=no', 'menubar=no', 'scrollbars=no', 'resizable=no','location=no', 'status=no');
                $("#login").modal("hide");
               // location.reload();
            }else if(data.code==500){
                alert("系统繁忙请稍后再试");
                return false;
            }
        })
    }
    $scope.sinaLogin = function(){
        //var sendparams ={ 'domainCode ':domainCode,'callback ':callBackUrl};
        var json ={
            "service":"zm3c.build.sign",
            "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
            "reqData":getS
        };
        //post
        $http({
            method : 'POST',
            url : posturl,
            data: json,
            header:headers
        }).success(function(data) {
            if(data.code==200){
                var sign = data.respData.sign;
                var weichartU = "http://api6.wwoqu.com/login/api/third/sinaWeibo?domainCode=test&callback="+callBackUrl+"&sign="+sign;
                window.open(weichartU,'newwindow', 'height=100', 'width=300', 'top=0', 'left=0', 'toolbar=no', 'menubar=no', 'scrollbars=no', 'resizable=no','location=no', 'status=no');
                $("#login").modal("hide");
               // location.reload();
            }else if(data.code==500){
                alert("系统繁忙请稍后再试");
                return false;
            }
        })
    }




    $scope.hellos = function(){
        $scope.showRolls = true;
    }
    $scope.hideShowRolls=function(){
        $scope.showRolls = false;
    }



})

