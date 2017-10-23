/**
 * Created by dell on 2016/10/14.
 */
'use strict'
zhongmai.controller('MainController',function($scope,$http,localStorageService,$timeout,$rootScope,$state,$cookieStore){
    //判断是否登陆，显示隐藏对应内容


    $scope.isLogin = false;
    if($cookieStore.get("uid")!=null ){
        $scope.isLogin = true;
        $scope.myObj = {"margin-left" : "300px"}
        $rootScope.nickname=JSON.parse($.cookie('phone'));
        //$rootScope.nickname=$cookieStore.get("phone");
        $rootScope.cartLength=$cookieStore.get("cartLength");
    }else{
        $state.go('home');
        $scope.isLogin = false;
        $scope.myObj = {"margin-left" : "0px"}
    }

    //去型号页
    $scope.toproduct = function(type,item){
        $rootScope.prodModel = item;
        $rootScope.modelData=[];
        if(type==40){

            for(var i=0;i<$rootScope.watchList.length;i++){
                if($rootScope.watchList[i].model==item.code){
                    $rootScope.modelData.push($rootScope.watchList[i]);
                }
            }

        }
        if(type==41){

            for(var i=0;i<$rootScope.headsetList.length;i++){
                if($rootScope.headsetList[i].model==item.code){
                    $rootScope.modelData.push($rootScope.headsetList[i]);
                }
            }
        }
        $rootScope.selectModelDataPrice=$rootScope.modelData[0].prodprice;
        $rootScope.selectModelDataName=$rootScope.modelData[0].prodname;
        $rootScope.selectModelDataColor = $rootScope.modelData[0].color;
        $rootScope.selectModelData  = $rootScope.modelData[0];
    
        $state.go('model');
    }

    //初始化页面 add:20161104
    function init() {
        $rootScope.prods = [];
        $rootScope.headsetModel = [];
        $rootScope.watchModel = [];

        var postdata = {"service":"zm3c.product.query","channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,"reqData":{}};

        $http({
            method:'POST',
            url:posturl,
            data:postdata,
            header:headers
        }).success(function (data) {
            if(data.code==200){
                var respdata = data.respData;
                for(var i=0;i<respdata.length;i++){
                    if(respdata[i].code=='40'){
                        $rootScope.watchModel=respdata[i].model;
                    }
                    if(respdata[i].code=='41'){
                        $rootScope.headsetModel=respdata[i].model;
                    }
                }
                $rootScope.prods=respdata;
            }else{
                console.log(data.msg);
            }
        })
        
    }
    init();

    function getProduct(){
        //获取分类产品
        $rootScope.watchList = [];
        $rootScope.headsetList = [];
        $rootScope.allList = [];
        
        var postdata = {"service":"zm3c.product.query","channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,"reqData":{"prod":1}};

        $http({
            method:'POST',
            url:posturl,
            data:postdata,
            header:headers
        }).success(function (data) {
            if(data.code==200){
                var respdata = data.respData;
                for(var i=0;i<respdata.length;i++){
                    if(respdata[i].prodtype==40){
                        $rootScope.watchList.push(respdata[i]);

                    }
                    if(respdata[i].prodtype==41){
                        $rootScope.headsetList.push(respdata[i]);
                    }
                }
                $rootScope.allList=respdata;
                localStorageService.set('watchList',$rootScope.watchList);
                localStorageService.set('headsetList',$rootScope.headsetList);
                localStorageService.set('allList',$rootScope.allList);
            }else{
                console.log(data.msg);
            }
        })
    };
    getProduct();


    //去付款
    $rootScope.toCartPage = function(){
        //if($rootScope.itemtotal != 0){
            $state.go("mycart")
        //}
    }



    $scope.cancel=function(){
        $("#address").modal("hide")
    }
});
