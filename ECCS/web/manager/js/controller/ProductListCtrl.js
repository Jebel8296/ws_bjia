/**
 * Created by Administrator on 2016/11/18.
 */
zhongmai.controller("ProductListCtrl",function ($scope,$rootScope,$http,$timeout,$location,localStorageService,$state,$window) {
    $scope.datatotal =0;//总条数
    $scope.itemTotal = 6;//每页显示条数
    $scope.pageTotal = 0;
    $scope.paging = true;
    $scope.waitHandleList = [];

    //当前页
    $scope.selPage = 1;
    //每页显示3个页码
    $scope.showpages = 10;
    $scope.beginpage =1;
    $scope.endpage = 10;


    $scope.status='0';
    $scope.type='9';
    $scope.code='';
    //取数据
    function getAfterData() {
        $scope.pageList = [];
        var post = {
                "service":"zm3c.aftersale.list",
                "channel":"pc",
                "sn":new Date().getTime().toString(),
                "reqData":{"uid":0,"pageNum":$scope.selPage,"pageSize":$scope.itemTotal,"status":$scope.status,"type":$scope.type,"code":$scope.code}
        };
        $http({method : 'POST', url : posturl, data: post, headers:headers}).success(function (data) {
            if(data.code==200){
                $scope.waitHandleList=data.respData.data;
                $scope.pageTotal=data.respData.pages;
                $scope.datatotal =data.respData.total;

                var step = Math.ceil($scope.selPage/$scope.showpages);
                if($scope.selPage==$scope.endpage){
                    if($scope.endpage==$scope.pageTotal){
                        $scope.beginpage = $scope.endpage-1;
                    }else{
                        $scope.beginpage = $scope.endpage;
                    }
                    $scope.endpage = $scope.selPage+$scope.showpages-1;
                }else{
                    $scope.beginpage =step*$scope.showpages-$scope.showpages+1;
                    $scope.endpage = step*$scope.showpages;
                }
                if($scope.endpage>$scope.pageTotal){
                    $scope.endpage = $scope.pageTotal;
                }
                if($scope.beginpage==0){
                    $scope.beginpage=1;
                }
                for (var i = $scope.beginpage; i <= $scope.endpage; i++) {
                    $scope.pageList.push(i);
                }
            }else{
                $scope.waitHandleList=[];
                $scope.pageList = [];
                $scope.datatotal =0;
                $scope.pageTotal = 0;
            }
        }).error(function (error) {
            console.log(error);
        })
    }

    getAfterData();
    $scope.submitQuery = function () {
        getAfterData();
    }
    //售后详情并处理
    $scope.toHandle = function (sale) {
        $rootScope.waitHandleAftercode = sale.aftercode;
        $state.go('handleinfo');
    }

    //打印当前选中页索引
    $scope.selectPage = function (page) {
        $scope.selPage = page;
        //不能小于1大于最大
        if (page < 1 || page > $scope.pageTotal){return false;}
        getAfterData();
        $scope.isActivePage(page);
    };
    //设置当前选中页样式
    $scope.isActivePage = function (page) {
        return $scope.selPage == page;
    };
    //上一页
    $scope.Previous = function () {
        //不能小于1大于最大
        if ($scope.selPage <= 1){return false;}
        $scope.selectPage($scope.selPage - 1);
    }
    //下一页
    $scope.Next = function () {
        //不能小于1大于最大
        if ($scope.selPage >= $scope.pageTotal){return false;}
        $scope.selectPage($scope.selPage + 1);
    };
})