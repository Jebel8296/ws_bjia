/**
 * Created by Administrator on 2016/11/18.
 */
zhongmai.controller("AfterHandleListCtrl",function ($scope,$rootScope,$http,$timeout,$location,localStorageService,$state,$window,$cookieStore) {
    $scope.datatotal =0;//总条数
    $scope.itemTotal = 6;//每页显示条数
    $scope.pageTotal = 0;
    $scope.paging = true;
    $scope.waitHandleList = [];
    //取数据
    function getAfterData() {
        $scope.pageList = [];
        var post = {
                "service":"zm3c.aftersale.list",
                "channel":pc, "sv":sv, "cv":cv, "pn": pn, "st": st, "sign": sign,"sn":sn,
                "reqData":{"uid":0,"pageNum":$scope.selPage,"pageSize":$scope.itemTotal}
        };
        $http({method : 'POST', url : posturl, data: post, header:headers}).success(function (data) {
            if(data.code==200){
                $scope.waitHandleList=data.respData.data;
                $scope.pageTotal=data.respData.pages;
                $scope.datatotal =data.respData.total;
                for (var i = 1; i < data.respData.pages; i++) {
                    $scope.pageList.push(i);
                }
            }
        }).error(function (error) {
            console.log(error);
        })
    }
    getAfterData();
    //售后详情并处理
    $scope.toHandle = function (sale) {
        $rootScope.waitHandleAftercode = sale.aftercode;
        $state.go('handleinfo');
    }

    //默认选择
    $scope.selPage = 1;
    //打印当前选中页索引
    $scope.selectPage = function (page) {
        //不能小于1大于最大
        if (page < 1 || page > $scope.pageTotal){return false;}
        $scope.selPage = page;
        getAfterData();
        $scope.isActivePage(page);
    };
    //设置当前选中页样式
    $scope.isActivePage = function (page) {
        return $scope.selPage == page;
    };
    //上一页
    $scope.Previous = function () {
        $scope.selectPage($scope.selPage - 1);
    }
    //下一页
    $scope.Next = function () {
        $scope.selectPage($scope.selPage + 1);
    };
})