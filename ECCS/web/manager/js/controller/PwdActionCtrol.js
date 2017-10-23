/**
 * Created by dell on 2016/8/30.
 */
zhongmai.controller('PwdActionCtrol',function($scope,$rootScope,$http,$state){

    $scope.forgetP = function(phone,status){
        if($scope.forgotForm.$valid){
            $state.go("changepwd");
        }else{
            $scope.submitted = true;
        }
        /*
        $(".phoneNum").blur();
        $(".regstatus").blur();
        if(!reginerror){
            alert("请完善表单");
        }else{
            window.location.href = "changepasswords.html";
        }*/
    }

    //修改密码
    $scope.pwdTrue = true;
    $scope.changePwdN = function (phone,oldpwd,newpwd) {
        var self = this;
        //修改密码
        if(self.changeForm.$valid){
            var reginData = {
                'phone':$scope.userPhone,
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
                headers:headers
            }).success(function(data) {
                if(data.code==200){
                    alert(data.msg)
                    localStorageService.remove('user');
                    $(".loginItem").show();
                    $(".user").hide();

                    $('#login').modal('show');
                }else if(data.code==500){
                    $(".js-error").text(data.msg);
                    console.log(data.code);
                }
            });
        }else{
            $scope.submitted = true;
        }
    };
    /*
    $scope.paracont = "获取验证码";
    $scope.paraclass = "but_null";
    $scope.paraevent = true;
    $scope.getCode = function(phone){
        if($scope.reg_phone){
            $(".jsphone").text("dsfsdfdsf");
        }else{
            $(".jsphone").text("");
            var second = 60,timePromise = undefined;
            if(second<=0){

            }else{
                $scope.paracont = second + "sdfsdfsdfs";
                $scope.paraclass = "not but_null";
                second--;
            }

             timePromise = $interval(function(){
             if(second<=0){
             $interval.cancel(timePromise);
             timePromise = undefined;
             second = 60;
             $scope.paracont = "sfsfsdfs";
             $scope.paraclass = "but_null";
             $scope.paraevent = true;
             }else{
             $scope.paracont = second + "�����ط�";
             $scope.paraclass = "not but_null";
             second--;

             }
             },1000,100);
        }

    }*/
    /*
     $scope.timer = false;
     $scope.timeout = 60000;
     $scope.timerCount = $scope.timeout / 1000;
     $scope.text = "��ȡ��֤��";
     $scope.getC = function() {
     $scope.showTimer = true;
     $scope.timer = true;
     $scope.text = "������»�ȡ";

     var counter = $interval(function () {
     $scope.timerCount = $scope.timerCount - 1;
     }, 1000);


     $timeout(function () {
     $scope.text = "��ȡ��֤��";
     $scope.timer = false;
     $interval.cancel(counter);
     $scope.showTimer = false;
     $scope.timerCount = $scope.timeout / 1000;
     }, $scope.timeout);

     }*/

    $scope.paracont = "获取验证码";
    $scope.paraclass = "but_null";
    $scope.paraevent = true;
    $scope.getCode = function(phone){
        var sendparams = {"phone":phone,'type':'pageregister','channel':"pc"};
        var json ={
            "service":"zm3c.send.sms",
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
        if(!reg.test(phone)){
            alert('手机格式不对');
            return false;
        }else{
            $http({
                method : 'POST',
                url : posturl,
                data: json,
                headers:headers
            }).success(function(data) {
                if(data.code==200){
                    console.log(data.msg);
                    console.log(data.respData);
                    console.log(data.respData.smssn);
                }else if(data.code==500){
                    console.log(data.code);
                    return false;
                }
            })

        }

    }




})
