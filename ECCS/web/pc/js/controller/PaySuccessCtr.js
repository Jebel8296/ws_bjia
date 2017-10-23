/**
 * Created by dell on 2016/8/16.
 */
function paySuccessCtrl($scope,$location,$http,$rootScope,$state,localStorageService,$stateParams,$window,$cookieStore){
    $scope.payorder = $stateParams.ordercode;
    $scope.paymsg = "您的订单尚未支付，请返回订单列表，在24小时内进行支付！";
    if($cookieStore.get("uid")==null){
        $state.go('home');
        showLR('login');
    }
    //去支付
    function getOrderInfo(){
        var json ={
            "service":"zm3c.order.info",
            "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
            "reqData":{'uid':Number($cookieStore.get("uid")),'ordercode':$scope.payorder}
        };
        $http({
            method : 'POST',
            url : posturl,
            data: json,
            header:headers
        }).success(function(data) {
            if(data.code==200){
                var status = data.respData.status;
                if(status==2){
                    $scope.paymsg = "您的订单支付成功！";
                }
                if(status==3){
                    $scope.paymsg = "您的订单已发货，请注意查收！";
                }
                if(status==4){
                    $scope.paymsg = "您的订单已完成！";
                }

                if(status==5){
                    $scope.paymsg = "您的订单已取消！";
                }
                $scope.haveorder = data.respData.oid;
                $scope.prodpay = data.respData.pay;
                $scope.payprods = data.respData.prods;
                $scope.payexpress=data.respData.express;
            }
        })
    }
    getOrderInfo();
    $scope.toOrderList = function(){
         $state.go("orderlist");
    }

    $(".showDetail").on("mouseenter",function(){
        $(".showBox").slideDown(function(){
            $(".showBox").stop(true);
        })
        return;
    })
    $(".showDetail").on("mouseleave",function(){
        $(".showBox").slideUp(function(){
            $(".showBox").stop(true,true);
        });
        return;
    })
};
zhongmai.controller('PaySuccessCtrl', paySuccessCtrl);