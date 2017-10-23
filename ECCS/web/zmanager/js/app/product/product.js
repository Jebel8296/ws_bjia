app.controller("DevicesServiceCtrl", function ($scope, $http) {
    $scope.datatotal = 0;//总条数
    $scope.itemTotal = 6;//每页显示条数
    $scope.pageTotal = 0;
    $scope.paging = true;
    $scope.waitDevicesHandleList = [];

    $scope.warningmess = "";
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
        $scope.warningmess = null;
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
            url: "http://192.168.108.171:9900/rest/prodevice",
            params: data
        });
        promises.then(function (result) {
            if (result.status == 200 && result.data.code == 200) {
                $scope.waitDevicesHandleList = result.data.respData.data;
                $scope.pageTotal = result.data.respData.pages;
                $scope.datatotal = result.data.respData.total;

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
        if ($scope.devicecode == null || $scope.devicecode == "") {
            $scope.warningmess = "设备编号不能空.";
            return;
        }
        if ($scope.prodcode == null || $scope.prodcode == "") {
            $scope.warningmess = "产品编号不能空.";
            return;
        }
        $http({
            method: 'post',
            url: "http://192.168.108.171:9900/rest/prodevice/add",
            data: {"devicode": $scope.devicecode, "prodcode": $scope.prodcode}
        }).success(function (result) {
            $scope.warningmess = "新增成功.";
            $scope.devicecode = null;
            $scope.prodcode = null;
            getAfterData();
        }).error(function (error) {
            $scope.warningmess = "新增失败.";
        });
    }

    $scope.toDeleteHandle = function (item) {
        $http({
            method: 'delete',
            url: "http://192.168.108.171:9900/rest/prodevice/" + item.id
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