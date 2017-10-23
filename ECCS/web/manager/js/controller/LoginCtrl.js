'use strict'
zhongmai.controller('LoginCtrl',function($scope,$rootScope,$http,$location,$state,localStorageService,$timeout,$interval){

    if($rootScope.user != null && $rootScope.user.uid!=""){
        $rootScope.uid = $rootScope.user.uid;
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
    //页面加载后，input为空
    $scope.reginU={
        "phone":"","code":"","pwd":"","pwd2":""
    }
    $scope.userPhone="";
    $scope.userPwd="";
    $scope.submitted = false;

    //登陆
    $scope.userlogin = function (phone,pwd) {
        //表单正常提交
        if($scope.loginForm.$valid){
            //提交的数据
            var sendparams ={"phone":phone,"password":pwd};
            var json ={
                "service":"zm3c.user.login.cn",
                "channel":pc,
                "sv":sv,
                "cv":cv,
                "pn": pn,
                "st": st,
                "sign": sign,
                "sn":dateNum,
                "reqData":sendparams,
                "ts":ts
            };
            $http({
                method : 'POST',
                url : posturl,
                data: json,
                headers:headers
            }).success(function(data) {
                if(data.code==200){
                    $rootScope.isLogged = true;
                    $('#login').modal('hide');
                    localStorageService.set('user',data.respData);
                    $rootScope.uid=data.respData.uid;
                    location.reload();
                }else if(data.code==500){
                    $(".js-error").text(data.msg);
                    return false;
                }
            })
        }else{
            $scope.submitted = true;
        }
    };
    $rootScope.waitButTotal=0;
    function getCartData(){
        //表单字段
        var orderList = {'uid':$rootScope.uid}
        //所有数据
        var json ={
            "service":"zm3c.cart.list",
            "reqData":orderList
        };
        //post
        $http({
            method : 'POST',
            url : posturl,
            data: json,
            headers:headers
        }).success(function(data) {
            if(data.code==200){
                localStorageService.set("cartLength",data.respData.data.length);
                localStorageService.set("myCart",JSON.stringify(data.respData.data));
                $rootScope.cartLength = localStorageService.get("cartLength");
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
        var pwd = item.pwd.substring(hex_sha1(item.pwd),0,15);

        //表单正常提交
        if($scope.userForm.$valid){/*
            if($scope.reginU.pwd != $scope.reginU.pwd2){
                $(".pwd2").show();
                return false
            }else{
                $(".pwd2").hide();
            }
            var data = {'domainCode':'test', 'un':item.phone, 'pd':pwd, 'sign':'sign'};
            var url =  host+"login/api/local/regist";
            $http({
                method : 'GET',
                url : url,
                data: data,
                headers:headers
            }).success(function(data) {
                if(data.code == '0'){
                    alert("注册成功");
                    alert(data.obj.user_id);
                    alert(data.obj.token);
                }else{
                   console.log(data.msg);
                }
            }).error(function(data) {
                if(data.code==500){
                    alert("系统繁忙请稍后再试");
                }
            });

*/



            var sendparams ={ 'phone':item.phone,'password':item.pwd,"smssn": "2410152012682903","authcode": "541022"}
            var json ={
                "service":"zm3c.user.register",
                "channel":pc,
                "sv":sv,
                "cv":cv,
                "pn": pn,
                "st": st,
                "sign": sign,
                "sn":dateNum,
                "ts":ts,
                "reqData":sendparams
            };
            //post
            $http({
                method : 'POST',
                url : posturl,
                data: json,
                headers:headers
            }).success(function(data) {
                if(data.code==200){
                    alert("注册成功，请登录");
                    $scope.showLR("login");
                }
            }).error(function(data) {
                if(data.code==500){
                        alert("系统繁忙请稍后再试");
                    }
            });
        }
        else{
            $scope.submitted = true;
        }
    };
    $scope.rebpwd = false;

    //跳转到忘记密码
    $scope.tofindpwd = function(){
        $location.path('/forgetpw');
        $('#login').modal('hide');
    }

    $scope.logout = function(){
        $state.go('home');
        localStorageService.remove('user');
        localStorageService.remove("myCart");
        localStorageService.remove('cartLength');
        location.reload();
    }

    $scope.paracont = "获取验证码";
    $scope.paraclass = "but_null";
    $scope.paraevent = false;
    $scope.getCode = function(phone){
        var sendparams = {"phone":phone,'type':'pageregister','channel':"pc"};
        var json ={
            "service":"zm3c.send.sms",
            "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"ts":ts,//不变
            "reqData":sendparams
        };
        if(!reg.test(phone)){
           alert('手机格式不对');
           return false;
       }else{
            var second = 60, timePromise = undefined;
            timePromise = $interval(function(){
                if(second<=0){
                    $interval.cancel(timePromise);
                    timePromise = undefined;
                    second = 60;
                    $scope.paracont = "重发验证码";
                    $scope.paraevent = false;
                }else{

                    $scope.paracont = second + "秒后可重发";
                    $scope.paraclass = "not but_null";
                    $scope.paraevent = true;
                    second--;


                }
            },1000,100);

            $http({
               method : 'POST',
               url : posturl,
               data: json,
               headers:headers
           }).success(function(data) {
               if(data.code==200){
                   console.log(data.respData);
               }else if(data.code==500){
                   console.log(data.code);
                   return false;
               }
           })
       }
    }
    /*
    $scope.mycartL={};
    $scope.mycart=[];
    getData(0);
  //获取数据
    function getData(pageNum){
        //表单字段
        var orderList = {'uid':$rootScope.uid,"pageNum": 1,"pageSize": 5}
        //所有数据
        var json ={
            "service":"zm3c.cart.list",
            "reqData":orderList
        };
        //post
        $http({
            method : 'POST',
            url : posturl,
            data: json,
            headers:headers
        }).success(function(data) {
            //数据源
            $scope.mycartL = data.respData;
            console.log($scope.mycartL)
            if($scope.mycartL != null){
                $scope.mycart =$scope.mycartL.data;
                console.log($scope.mycart)
                if($scope.mycart.length ==0){
                    $scope.jsCart = false;
                    $scope.isShow2= true;
                }else{
                    $scope.jsCart = true;
                    $scope.isShow2= false;
                }
            }

        })
    }*/



})

