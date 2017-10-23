/**
 * Created by dell on 2016/10/14.
 */
'use strict'
zhongmai.controller('MainController',function($scope,$http,localStorageService,$timeout,$rootScope,$state){
    //判断是否登陆，显示隐藏对应内容
    $rootScope.user = localStorageService.get('user');
    $rootScope.isLogin = false;
    if($rootScope.user != null && $rootScope.user.uid!=""){
        $scope.isLogin = true;
        $scope.myObj = {"margin-left" : "300px"}
        $rootScope.uid = $rootScope.user.uid;
    }else{
        $state.go('home');
        $scope.isLogin = false;
        $scope.myObj = {"margin-left" : "0px"}
    }
});
