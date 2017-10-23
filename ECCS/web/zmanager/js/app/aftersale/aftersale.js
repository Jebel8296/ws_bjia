app.controller('AfterSaleCtrl', ['$scope', '$http', '$state', '$cookies', '$rootScope', function ($scope, $http, $state, $cookies, $rootScope) {
    $scope.datatotal = 0;//总条数
    $scope.itemTotal = 15;//每页显示条数
    $scope.pageTotal = 0;
    $scope.paging = true;
    $scope.waitHandleList = [];

    //当前页
    $scope.selPage = 1;
    //每页显示3个页码
    $scope.showpages = 10;
    $scope.beginpage = 1;
    $scope.endpage = 10;

    $scope.status = '9';
    $scope.type = '9';
    $scope.code = '';
    $scope.beginDate = new Date();
    $scope.endDate = new Date();
    //取数据
    function getAfterData() {
        $scope.pageList = [];
        var post = {
            "uid": 0,
            "pageNum": $scope.selPage,
            "pageSize": $scope.itemTotal,
            "status": $scope.status,
            "type": $scope.type,
            "code": $scope.code,
            "token": $cookies.token,
            "beginDate": $scope.beginDate,
            "endDate": $scope.endDate
        };
        $http({
            method: 'GET',
            url: $rootScope.backsage_url + "/aftersale",
            params: post
        }).success(function (data) {
            console.log(data);
            if (data.code == 200) {
                $scope.waitHandleList = data.respData.data;
                $scope.pageTotal = data.respData.pages;
                $scope.datatotal = data.respData.total;

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
                $scope.waitHandleList = [];
                $scope.pageList = [];
                $scope.datatotal = 0;
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
        $state.go('app.aftersalehandle', { handleid: sale.aftercode });
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
}
]);
app.controller('AfterSaleHandleCtrl', ['$scope', '$rootScope', '$http', '$state', '$stateParams', '$cookies', '$rootScope', function ($scope, $rootScope, $http, $state, $stateParams, $cookies, $rootScope) {
    //取数据
    $scope.waitHandleAftercode = $stateParams.handleid;
    $scope.waitHandleData = {};
    $scope.producttype = {};
    $scope.handleResult = 0;
    $scope.resultReply = null;
    function getAfterInfoData() {
        $http({
            method: 'GET',
            url: $rootScope.backsage_url + "/aftersale/" + $scope.waitHandleAftercode,
            params: { "token": $cookies.token }
        }).success(function (data) {
            if (data.code == 200) {
                console.log(data.respData);
                $scope.waitHandleData = data.respData;
                $scope.producttype = JSON.parse(data.respData.producttype);
            }
        }).error(function (error) {
            console.log(error);
        })
    }

    getAfterInfoData();

    //提交反馈
    $scope.submitAfterReply = function () {
        if ($scope.handleResult == 0) {
            alert("请选择客服处理结果。");
            return;
        }
        if ($scope.waitHandleData.status == $scope.handleResult) {
            alert("处理结果已提交过，不能重复提交。");
            return;
        }
        if ($scope.reply != null && $scope.reply.length > 300) {
            alert("已超过300个字数限制。");
            return;
        }
        var post = {
            "uid": 0,
            "aftercode": $scope.waitHandleAftercode,
            "result": $scope.handleResult,
            "reply": $scope.resultReply,
            "token": $cookies.token
        };
        $http({
            method: 'POST',
            url: $rootScope.backsage_url + "/aftersale/handle",
            data: JSON.stringify(post)
        }).success(function (data) {
            if (data.code == 200) {
                alert("提交成功.");
                $state.go('app.aftersalelist');
            }
        }).error(function (error) {
            alert("提交失败，请稍后再提交.");
        })
    }
    //受理
    $scope.acceptHandle = function () {
        var post = { "uid": 0, "aftercode": $scope.waitHandleAftercode, "type": 1, "token": $cookies.token };
        $http({
            method: 'POST',
            url: $rootScope.backsage_url + "/aftersale/handle",
            data: JSON.stringify(post)
        }).success(function (data) {
            if (data.code == 200) {
                alert("受理成功.");
                $state.go('app.aftersalelist');
            }
        }).error(function (error) {
            console.log(error);
        })
    }
    //拒绝受理
    $scope.refuseHandle = function () {
        var post = { "uid": 0, "aftercode": $scope.waitHandleAftercode, "type": 3, "token": $cookies.token };
        $http({
            method: 'POST',
            url: $rootScope.backsage_url + "/aftersale/handle",
            data: JSON.stringify(post)
        }).success(function (data) {
            if (data.code == 200) {
                alert("已取消.");
                $state.go('app.aftersalelist');
            }
        }).error(function (error) {
            console.log(error);
        })
    }
}]);

app.filter("prodType", function () {
    return function (code) {
        if (code != null) {
            return code.substring(4, 8);
        }
    };
});
app.filter("prodColor", function () {
    return function (code) {
        var colorName;
        if (code != null) {
            switch (code.substring(8, 10)) {
                case "01":
                    colorName = "经典黑";
                    break;
                case "02":
                    colorName = "白蓝色";
                    break;
                default:
                    colorName = "经典黑";
            }
        }
        return colorName;
    };
});