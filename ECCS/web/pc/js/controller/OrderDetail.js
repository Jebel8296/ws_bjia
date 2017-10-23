/**
 * Created by dell on 2016/10/26.
 */
zhongmai.controller("OrderDetail",function($scope,$location,$http,$rootScope,$state,localStorageService,$stateParams,$cookieStore){
    if($cookieStore.get("uid")==null){
        // $state.go('home');
        showLR('login');
    }
    $scope.today = new Date();
    $scope.formatStatus = function(status) {
        if (status == 1) {return "待支付";}
        if (status == 2){return "已支付待发货";}
        if(status==3){ return "已发货";}
        if(status==4){  return "交易完成";}
        if(status==5){  return "取消";}
    }
    $scope.formatLogistics = function(status) {
        if (status == "YZXB") {return "EMS（免费）";}
        if (status == "SF"){return "顺丰速运（18元）";}
    }
 function init(){
     $scope.datadetail={};
     var stwe = parseInt($stateParams.orderId);
     //所有数据
     var sendparams ={'uid': Number($cookieStore.get("uid")),'orderid':stwe};
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
         //console.log(data)
         if(data.code == 200){
             $scope.datadetail = data.respData;
            // console.log( $scope.datadetail)
            if($scope.datadetail.status ==7){
                 $scope.showStutas1 = true;
                 $scope.showStutas2 = false;
                 $scope.showStutas3 = false;
                 $scope.showStutas4 = false;
                 $scope.showStutas5 = false;
                 $scope.showStutas6 = false;
                 $scope.showStutas7 = true;
                 $(".flowPic_on").css({"width":"100%"});
             }
             if($scope.datadetail.status ==6){
                 $scope.showStutas1 = true;
                 $scope.showStutas2 = true;
                 $scope.showStutas3 = false;
                 $scope.showStutas4 = false;
                 $scope.showStutas5 = false;
                 $scope.showStutas6 = true;
                 $scope.showStutas7 = false;
                 $(".flowPic_on").css({"width":"100%"});
             }
            if($scope.datadetail.status ==5){
                 $scope.showStutas1 = true;
                 $scope.showStutas2 = true;
                 $scope.showStutas3 = false;
                 $scope.showStutas4 = false;
                 $scope.showStutas5 = true;
                 $scope.showStutas6 = false;
                 $scope.showStutas7 = false;
                 $(".flowPic_on").css({"width":"100%"});
             }
             if($scope.datadetail.status ==4){
                 $scope.showStutas1 = true;
                 $scope.showStutas2 = true;
                 $scope.showStutas3 = true;
                 $scope.showStutas4 = true;
                 $scope.showStutas5 = false;
                 $scope.showStutas6 = false;
                 $scope.showStutas7 = false;
                 $(".flowPic_on").css({"width":"100%"});
             }
             if($scope.datadetail.status ==3){
                 $scope.showStutas1 = true;
                 $scope.showStutas2 = true;
                 $scope.showStutas3 = true;
                 $scope.showStutas4 = false;
                 $scope.showStutas5 = false;
                 $scope.showStutas6 = false;
                 $scope.showStutas7 = false;
                 $(".flowPic_on").css({"width":"70%"});
             }
             if($scope.datadetail.status ==2){
                 $scope.showStutas1 = true;
                 $scope.showStutas2 = true;
                 $scope.showStutas3 = false;
                 $scope.showStutas4 = false;
                 $scope.showStutas5 = false;
                 $scope.showStutas6 = false;
                 $scope.showStutas7 = false;
                 $(".flowPic_on").css({"width":"39%"});
             }
             if($scope.datadetail.status ==1){
                 $scope.showStutas1 = true;
                 $scope.showStutas2 = false;
                 $scope.showStutas3 = false;
                 $scope.showStutas4 = false;
                 $scope.showStutas5 = false;
                 $scope.showStutas6 = false;
                 $scope.showStutas7 = false;
                 $(".flowPic_on").css({"width":"8%"});
             }
         }else if(data.code==500){
             console.log(data.msg)
         }
     })
 };
    init();


    $scope.orderItem = [];


})
