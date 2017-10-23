/**
 * Created by dell on 2016/8/16.
 */
function orderPayCtrl($scope,$location,$http,$rootScope,$state,localStorageService,$stateParams,$window,$cookieStore){
    $scope.orderCode = $stateParams.cid + "";
    $scope.pricetotal = $stateParams.payT;//
    $scope.aftercode = $stateParams.aftercode;
    $scope.afterI = true;
    $scope.afterId = 596;
    if($scope.pricetotal){
        $scope.order = true;
        $scope.afterI = true;
    }
    if($scope.aftercode){
        $scope.afterI = false;
    }




//去支付
    $scope.toPayA = function(){
        var realParam ={'uid':Number($cookieStore.get("uid")),'ordercode':$scope.orderCode,'payfee':$scope.pricetotal}
        var json ={
            "service":"zm3c.payment.jumppay",
            "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
            "reqData":realParam
        };
        //post
        $http({
            method : 'POST',
            url : posturl,
            data: json,
            header:headers
        }).success(function(data) {
            if(data.code==200){
                $window.open(data.respData.payurl);
            }else if(data.code==500){
                console.log(data.code + "pay");
            }
        })
    }

//
    $scope.toServiceL = function(){
         $state.go("servicelist");
    }

    //


    $(".showDetail").on("mouseenter",function(){
        $(".showBox").slideDown(function(){
            $(".showBox").stop(true);
        })//.stop(true,true);
        return;
    })
    $(".showDetail").on("mouseleave",function(){
        $(".showBox").slideUp(function(){
            $(".showBox").stop(true,true);
        });
        return;
    })
    getDetailForPay();

    $scope.detailOrder={};
    //获取订单详情
    function getDetailForPay(){
        //所有数据
        var sendparams ={'uid': Number($cookieStore.get("uid")),'ordercode':$scope.orderCode};
        var json ={
            "service":"zm3c.order.info",
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
            if(data.code == 200){
                $scope.detailOrder = data.respData;
              //  console.log(  $scope.detailOrder)
            }else if(data.code==500){
                console.log(data.msg)
            }
        })
    }


};

zhongmai.controller('orderPayCtrl', orderPayCtrl);