/**
 * Created by Administrator on 2016/11/18.
 */
zhongmai.controller("DevicesListCtrl", function ($scope, $rootScope, $http, $timeout, $location, localStorageService, $state, $window) {
    $scope.datatotal = 0;//总条数
    $scope.itemTotal = 6;//每页显示条数
    $scope.pageTotal = 0;
    $scope.paging = true;
    $scope.waitDevicesHandleList = [];

    $scope.warningmess="";
    //当前页
    $scope.selPage = 1;
    //每页显示3个页码
    $scope.showpages = 10;
    $scope.beginpage = 1;
    $scope.endpage = $scope.showpages;

    $scope.devicecode = '';
    $scope.prodcode = '';
    //取数据
    function getAfterData() {
        $scope.pageList = [];
        var data = {};
        if ($scope.devicecode != null && $scope.devicecode != "") {
            data.devicode = $scope.devicecode;
        }
        if ($scope.prodcode != null && $scope.prodcode != "") {
            data.prodcode = $scope.prodcode;
        }
        data.pageNum = $scope.selPage;
        var promises = $http({
            method: 'GET',
            url: "http://10.12.12.199:38082/proddevice/query",
            params: data
        });
        promises.then(function (result) {
            if (result.status == 200) {
                $scope.waitDevicesHandleList = result.data.data.data;
                $scope.pageTotal = result.data.data.pages;
                $scope.datatotal = result.data.data.total;

                var step = Math.ceil($scope.selPage / $scope.showpages);
                if ($scope.selPage == $scope.endpage) {
                    if ($scope.endpage == $scope.pageTotal) {
                        $scope.beginpage = $scope.endpage - 1;
                    } else {
                        $scope.beginpage = $scope.endpage;
                    }
                    $scope.endpage = $scope.selPage + $scope.showpages - 1;
                } else {
                    $scope.beginpage = step * $scope.showpages - $scope.showpages + 1;
                    $scope.endpage = step * $scope.showpages;
                }
                if ($scope.endpage > $scope.pageTotal) {
                    $scope.endpage = $scope.pageTotal;
                }
                if ($scope.beginpage == 0) {
                    $scope.beginpage = 1;
                }
                for (var i = $scope.beginpage; i <= $scope.endpage; i++) {
                    $scope.pageList.push(i);
                }
            } else {
                $scope.waitDevicesHandleList = [];
                $scope.pageList = [];
                $scope.datatotal = 0;
                $scope.pageTotal = 0;
            }
        }, function (error) {
            console.log(error.error);
        });
    }

    getAfterData();
    $scope.submitDevicesQuery = function () {
        getAfterData();
    }

    $scope.toAddHandle = function () {
        $http({
            method: 'post',
            url: "http://10.12.12.199:38082/proddevice/add",
            data: {"devicode": $scope.idevicecode, "prodcode": $scope.iprodcode}
        }).success(function (result) {
            $scope.warningmess = "新增成功.";
            getAfterData();
        }).error(function (error) {
            $scope.warningmess = "新增失败.";
        });
    }

    $scope.toDeleteHandle = function (item) {
        $http({
            method: 'delete',
            url: "http://10.12.12.199:38082/proddevice/" + item.id
        }).success(function (result) {
            getAfterData();
        }).error(function (error) {
        });
    }

    //打印当前选中页索引
    $scope.selectPage = function (page) {
        $scope.selPage = page;
        //不能小于1大于最大
        if (page < 1 || page > $scope.pageTotal) {
            return false;
        }
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
        if ($scope.selPage <= 1) {
            return false;
        }
        $scope.selectPage($scope.selPage - 1);
    }
    //下一页
    $scope.Next = function () {
        //不能小于1大于最大
        if ($scope.selPage >= $scope.pageTotal) {
            return false;
        }
        $scope.selectPage($scope.selPage + 1);
    };
})