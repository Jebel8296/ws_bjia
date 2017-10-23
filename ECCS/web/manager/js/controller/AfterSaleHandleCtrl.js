/**
 * Created by Administrator on 2016/11/18.
 */
zhongmai.controller("AfterSaleHandleCtrl",function ($scope,$rootScope,$http,$timeout,$location,localStorageService,$state,$window) {
    //取数据
    $scope.waitHandleData={};
    function getAfterInfoData() {
        var post = {
            "service":"zm3c.aftersale.info",
            "channel":"pc",
            "sn":new Date().getTime().toString(),
            "reqData":{"uid":0,"aftercode":$rootScope.waitHandleAftercode}
        };
        $http({method : 'POST', url : posturl, data: post, headers:headers}).success(function (data) {
            if(data.code==200){
                $scope.waitHandleData=data.respData;
                $scope.reply = data.respData.reply;
            }
        }).error(function (error) {
            console.log(error);
        })
    }
    getAfterInfoData();

    //提交反馈
    $scope.submitAfterReply = function () {
        if($scope.reply != null && $scope.reply.length>300){
            alert("已超过300个字数限制。");
            return;
        }
        var post = {
            "service":"zm3c.aftersale.accept",
            "channel":"pc",
            "sn":new Date().getTime().toString(),
            "reqData":{"uid":$rootScope.uid,"aftercode":$rootScope.waitHandleAftercode,"type":2,"reply":$scope.reply}
        };
        $http({method : 'POST', url : posturl, data: post, headers:headers}).success(function (data) {
            if(data.code==200){
                alert("提交成功.");
                $state.go('handlelist');
            }
        }).error(function (error) {
            console.log(error);
        })
    }
    //受理
    $scope.acceptHandle = function () {
        var post = {
            "service":"zm3c.aftersale.accept",
            "channel":"pc",
            "sn":new Date().getTime().toString(),
            "reqData":{"uid":$rootScope.uid,"aftercode":$rootScope.waitHandleAftercode,"type":1}
        };
        $http({method : 'POST', url : posturl, data: post, headers:headers}).success(function (data) {
            if(data.code==200){
                alert("受理成功.");
                $state.go('handlelist');
            }
        }).error(function (error) {
            console.log(error);
        })
    }
    //拒绝受理
    $scope.refuseHandle = function () {
        var post = {
            "service":"zm3c.aftersale.accept",
            "channel":"pc",
            "sn":new Date().getTime().toString(),
            "reqData":{"uid":$rootScope.uid,"aftercode":$rootScope.waitHandleAftercode,"type":3}
        };
        $http({method : 'POST', url : posturl, data: post, headers:headers}).success(function (data) {
            if(data.code==200){
                alert("已取消.");
                $state.go('handlelist');
            }
        }).error(function (error) {
            console.log(error);
        })
    }
})