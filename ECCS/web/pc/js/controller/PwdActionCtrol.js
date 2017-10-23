/**
 * Created by dell on 2016/8/30.
 */
zhongmai.controller('PwdActionCtrol',function($scope,$rootScope,$http,$state,localStorageService,$interval,$cookieStore,$location){
    $scope.phonePwd= JSON.parse($.cookie('phone'));

    $scope.forgetPwd = function(phone,code,pd){
        //表单正常提交
        if($scope.forgotForm.$valid){
            var newP = $(".newpwd").val(),  newP2 = $(".newpwd2").val();
            if(newP != newP2){
                alert("两次输入的新密码必须一致");
                return false
            }
            var reginData = {
                'phone':phone,
                'status':code,
                'password':pd
            }
            var json ={
                "service":"zm3c.build.sign",
                "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
                "reqData":reginData
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
                    changePD2(phone,code,pd,sign);
                }else if(data.code==500){
                    $(".js-error").text(data.msg);
                    console.log(data.code);
                }
            });
        }else{
            $scope.submitted = true;
        }
    }

    function changePD2(phone,code,npd,sign){
        var sha1Pd2 = hex_sha1(npd);
        var subSha1Pd2= sha1Pd2.substring(0,15);
        $.ajax({
            type:'GET',
            url:host+"login/api/local/resetPasswordBySms",
            data:{
                'domainCode':domainCode,
                'un':phone,
                'code':code,
                'pd':subSha1Pd2,
                'sign':sign
            },
            async:false,
            success: function(data){
                if(data.code == '0'){
                    alert("重置成功");
                    $rootScope.nickname = null;
                    $location.path('/');
                    $state.go("model");
                    $("#login").modal("show");
                    showLR('login');
                }else{
                    alert(data.msg);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                alert("请稍后再试！");
            }
        });
    }

    //获取sign
    $scope.getCodeForReset = function(phone){
        if(!regPhone.test(phone)){
            alert('请输入11位手机号码！');
            return false;
        }else{
            var dataForJ = {'un':phone};
            var allData ={
                "service":"zm3c.build.sign",
                "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
                "reqData":dataForJ
            };
            $http({
                method : 'POST',
                url : posturl,
                data: allData,
                header:headers
            }).success(function(data) {
                if(data.code==200){
                    var signs =  data.respData.sign;
                    postToX(phone,signs);
                }else if(data.code==500){
                    alert("获取验证码失败，请稍后再试！");
                    return false;
                }
            })
        }
    }
    //获取验证码
    function postToX(phone,sign){
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
        var dataForX = {'un':phone, 'content':'验证码：%verifycode%', 'type':"5", 'domainCode':domainCode, 'sign':sign};
        $.ajax({
            type:'GET',
            url:host+"login/api/local/smsWithNoCaptcha",
            data:dataForX,
            async:false,
            success: function(data){
                if(data.code == '0'){
                    console.log(data)
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

  // $scope.ph = $.cookie("changePhone");
    //修改密码
    $scope.pwdTrue = true;
    /*
    $scope.changePwdN = function (phone,oldpwd,newpwd) {
        var newP = $(".newpwd").val(),  newP2 = $(".newpwd2").val();
        if(newP != newP2){
            alert("两次输入的新密码必须一致");
            return false
        }
        //修改密码
        if($scope.changeForm.$valid){
            var reginData = {
                'phone':phone,
                'password':newpwd,
                "smssn": "2410152012682903",
                "authcode": "541022"
            }
            var json ={
                "service":"zm3c.password.modify",
                "reqData":reginData
            };
            //post
            $http({
                method : 'POST',
                url : posturl,
                data: json,
                header:headers
            }).success(function(data) {
                if(data.code==200){
                    alert("密码修改成功!");
                    location.reload();
                    localStorageService.remove('user');
                    $state.go("home");
                    showLR('login');
                }else if(data.code==500){
                    $(".js-error").text(data.msg);
                    console.log(data.code);
                }
            });
        }else{
            $scope.submitted = true;
        }
    };
*/
    //修改密码
    $scope.changePwdAction = function (phone,newpwd) {
        if($scope.changeForm.$valid){
            var newP = $(".newpwd").val(),  newP2 = $(".newpwd2").val();
            if(newP != newP2){
                alert("两次输入的新密码必须一致");
                return false
            }
            var reginData = {
                'phone':phone,
                'password':newpwd,
                "smssn": "2410152012682903",
                "authcode": "541022"
            }
            var json ={
                "service":"zm3c.build.sign",
                "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
                "reqData":reginData
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
                    changePwdAction2($scope.newpwd,sign);
                }else if(data.code==500){
                    $(".js-error").text(data.msg);
                    console.log(data.code);
                }
            });
        }else{
            $scope.submitted = true;
        }
    };

    $scope.changePwdAction2 = function (phone,newpwd) {
        //修改密码
        if($scope.changeForm.$valid){
            var newP = $(".newpwds").val(),  newP2 = $(".newpwd2s").val();
            if(newP != newP2){ alert("两次输入的新密码必须一致");
                return false
            }
            var reginData = {
                'phone':phone,
                'password':newpwd,
                "smssn": "2410152012682903",
                "authcode": "541022"
            }
            var json ={
                "service":"zm3c.build.sign",
                "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
                "reqData":reginData
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
                    changePD($scope.oldpwd,$scope.newpwd,sign);
                }else if(data.code==500){
                    $(".js-error").text(data.msg);
                    console.log(data.code);
                }
            });
        }else{
            $scope.submitted = true;
        }
    };


    function changePD(opd,npd,sign){
        var sha1Pd = hex_sha1(opd);
        var subSha1Pd= sha1Pd.substring(0,15)+timeStamp;
        var sha1Pd2 = hex_sha1(npd);
        var subSha1Pd2= sha1Pd2.substring(0,15);
        var uidN= parseInt($cookieStore.get("uid"));
        var tokenN = $cookieStore.get('token');
        $.ajax({
            type:'GET',
            url:host+"login/api/local/changePassword",
            data:{
                'domainCode':domainCode,
                'ui':uidN,
                'token':tokenN,
                'timestamp':timeStamp,
                'opd':md5(subSha1Pd),
                'pd':subSha1Pd2,
                'sign':sign
            },
            async:false,
            success: function(data){
                if(data.code == '0'){
                    alert("修改成功");
                    $rootScope.nickname = null;
                    $location.path('/');
                    $state.go("home");
                    $("#login").modal("show");
                    showLR('login');
                }else{
                   $("#oldpwd").text(data.msg);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                alert("请稍后再试！");
            }
        });
    }

    $scope.paracont = "获取验证码";
    $scope.paraclass = "but_null";
    $scope.paraevent = false;
    $scope.getCode = function(phone){
        var sendparams = {"phone":phone,'type':'pageregister','channel':"pc",'sign':sign};
        var json ={
            "service":"zm3c.send.sms",
            "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,//不变
            "reqData":sendparams
        };
        //!phoneNo.match(/^1[34578]\d{9}$/)
        var phoneV = $(".reg_phone").val();
        if(!regPhone.test(phoneV)){
            alert('请输入11位手机号码!');
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
                header:headers
            }).success(function(data) {
                if(data.code==200){
                    console.log(data.respData);
                }else if(data.code==500){
                    //  console.log(data.code);
                    // return false;
                }
            })
        }
    }


    $scope.reset = function(){
        $scope.reg_phone = "";
        $scope.reg_status = "";
        $scope.newpwd = "";
        $scope.newpwd2 = ""
    }




})
