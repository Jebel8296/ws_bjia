/**
 * Created by dell on 2016/8/16.
 */
function orderPayCtrl($scope,$location,$http,$rootScope,$state,localStorageService,$stateParams){
    $scope.orderCode = $stateParams.cid + "";
    $scope.pricetotal = $stateParams.payT;
    $scope.uid = $stateParams.uid;
    $scope.afterI = true;

    $scope.afterId = 596;
    if($scope.orderId){
        $scope.order = true;
        $scope.afterI = false;
    }
    if($scope.afterId){
        $scope.afterI = false;
    }




//去支付
    $scope.toPayA = function(){
        var json ={
            "service":"zm3c.payment.jumppay",
            "reqData":{'uid':$scope.uid,'ordercode':$scope.orderCode,'payfee':$scope.pricetotal,'channel':'pc'}
        };
        //post
        $http({
            method : 'POST',
            url : posturl,
            data: json,
            headers:headers
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


};

zhongmai.controller('orderPayCtrl', orderPayCtrl);