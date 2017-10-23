/**
 * Created by Administrator on 2016/11/18.
 */
zhongmai.controller("MonitorLogCtrl",function ($scope,$rootScope,$http,$timeout,$location,localStorageService,$state,$window) {
    $scope.itemTotal = 10;//每页显示条数
    $scope.waitHandleList = [];
    //取数据
    function getLogData() {
        $scope.pageList = [];
        var post = {
                "service":"monitor.log",
                "channel":"pc",
                "sn":new Date().getTime().toString(),
                "reqData":{"uid":0,"skip":$scope.selPage,"limit":$scope.itemTotal}
        };
        $http({method : 'POST', url : "http://10.12.12.199:9999/gateway.do", data: post, headers:headers}).success(function (data) {
            if(data.code==200){
                $scope.waitHandleList=data.respData;
            }
        }).error(function (error) {
            console.log(error);
        })
    }
    $scope.freshMonitor = function () {
        getLogData();
    }

    //默认选择
    $scope.selPage = 1;
    //打印当前选中页索引
    $scope.selectPage = function (page) {
        //不能小于1大于最大
        if (page < 1 || page > $scope.pageTotal){return false;}
        $scope.selPage = page;
        getLogData();
        $scope.isActivePage(page);
    };
    //设置当前选中页样式
    $scope.isActivePage = function (page) {
        return $scope.selPage == page;
    };
    //上一页
    $scope.Previous = function () {
        if($scope.selPage>6){
            $scope.selectPage($scope.selPage - 6);
        }else{
            $scope.selPage = 1;
        }
    }
    //下一页
    $scope.Next = function () {
        $scope.selectPage($scope.selPage + 6);
    };
})