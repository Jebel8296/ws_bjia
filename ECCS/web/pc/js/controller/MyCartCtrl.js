/**
 * Created by dell on 2016/7/29.
 */
'use strict'
zhongmai.controller("MyCartCtrl",function($scope,$rootScope,$http,$timeout,$location,localStorageService,$state,$window,$cookieStore){

    if($cookieStore.get("uid")==null){
        $state.go('home');
        showLR('login');
    }

    $scope.showpage = true;
    //购物车部分
    $scope.mycartL= {};
    $scope.cartTopayList = [];
    $scope.allStatus = true;
    //页面加载购物车数据
    getData(0);
    function getData(pageNum){
        //表单字段
        var orderList = {'uid':Number($cookieStore.get("uid")),"pageNum": 1,"pageSize": 10}
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
            $rootScope.waitButTotal=0;
            //数据源
            $scope.mycartL = data.respData;
            if(data.respData.data.length == 0){
                $scope.showpage=false;
            }
            if($scope.mycartL != null){
                $cookieStore.put("cartLength",$scope.mycartL.data.length);
                $rootScope.cartLength = $scope.mycartL.data.length;
                var selectAll = true;
                for (var i = 0; i <$scope.mycartL.data.length; i++) {
                    if (!$scope.mycartL.data[i].checked) {
                        selectAll = false;
                        break;
                    }
                }
                $scope.selectMain = selectAll;
                totalPrize();
            }
        })
    }

    //总价
    function totalPrize(){
        $rootScope.total=0;
        $rootScope.itemtotal=0;
        angular.forEach($scope.mycartL.data,function(item,index){
            $rootScope.headerTotal += item.prodprice*item.prodtotal;
            $rootScope.itemtotal++;
            if(item.checked){
                $rootScope.total += item.prodprice*item.prodtotal;
                $rootScope.waitButTotal++;

            }
        })
    }
    //全选
    $scope.checkAll = function (m) {
        var json ={
            "service":"zm3c.cart.check",
            "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
            "reqData":{"uid":Number($cookieStore.get("uid")), "instanceid": 0, "checked": !m}
        };
        $http({
            method : 'POST',
            url : posturl,
            data: json,
            header:headers
        }).success(function(data) {
            if(data.code==200){
            }else if(data.code==500){
            }
        })
        getData(0);
    }
    //单选
    $scope.checkItem = function (item) {
        var json ={
            "service":"zm3c.cart.check",
            "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
            "reqData":{"uid":Number($cookieStore.get("uid")), "instanceid": item.instanceid, "checked": !item.checked}
        };
        $http({
            method : 'POST',
            url : posturl,
            data: json,
            header:headers
        }).success(function(data) {
            if(data.code==200){
                getData(0);
            }else if(data.code==500){
            }
        })
    }

    //
    $scope.toShopping = function(){
        $state.go('home');
    }

    //去付款
    $scope.cartToPay = function(){
        var comfirmId = Number($cookieStore.get("uid"));
      //  console.log(comfirmId)
        if($rootScope.waitButTotal ==0){
            alert("请选择结算商品") ;
            return false;
        }else{
            $state.go('comfirm', {comfirmId:comfirmId});
        }

    }

    //购物车数量减
    $rootScope.minus = function($index,instanceid) {
        angular.forEach($scope.mycartL.data,function(item,index){
            if(index == $index && item.prodtotal !=1){
                var json ={
                    "service":"zm3c.cart.decr",
                    "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
                    "reqData":{"uid":Number($cookieStore.get("uid")),"instanceid":instanceid}
                };
                $http({
                    method : 'POST',
                    url : posturl,
                    data: json,
                    header:headers
                }).success(function(data) {
                    getData(0);
                })
            }
            if(item.prodtotal==1){
                return false;
            }
        })
    }

    //购物车数量增
    $rootScope.add = function($index,instanceid) {
        angular.forEach($scope.mycartL.data,function(item,index){
            if(index == $index){
                var json ={
                    "service":"zm3c.cart.incr",
                    "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
                    "reqData":{"uid":Number($cookieStore.get("uid")),"instanceid":instanceid}
                };
                $http({
                    method : 'POST',
                    url : posturl,
                    data: json,
                    header:headers
                }).success(function(data) {
                    getData(0);
                });
            }
        })
    }

    //购物车删除
    $rootScope.delItem = function(instanceid) {
        var json ={
            "service":"zm3c.cart.del",
            "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
            "reqData":{"uid":Number($cookieStore.get("uid")),"instanceid":instanceid}
        };
        if($window.confirm("确定要删除吗？")){
            $http({
                method : 'POST',
                url : posturl,
                data: json,
                header:headers
            }).success(function(data) {
                if(data.code==200){
                    getData(0);
                }else if(data.code==500){
                    $(".js-error").text(data.msg);
                    return false;
                }
            })
        }
    }
})