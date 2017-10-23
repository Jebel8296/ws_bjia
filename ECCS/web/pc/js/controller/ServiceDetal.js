
zhongmai.controller('ServiceDetail', function ($scope, $rootScope, $http, $state, $stateParams, $cookieStore, UrlService) {
    $scope.username = $cookieStore.get("phone");
    $scope.producttype = null;
    $scope.sdetaildata = {};
    function init() {
        var afterID = $stateParams.afterid;
        if (afterID != null) {
            var sendparams = { 'uid': Number($cookieStore.get("uid")), 'aftercode': afterID };
            UrlService.AFTERSALE.info(sendparams).then(function (data) {
                if (data.code == 200) {
                    $scope.sdetaildata = data.respData;
                    $scope.producttype = JSON.parse(data.respData.producttype);
                    if ($scope.sdetaildata.status == "待受理") {
                        $scope.showStutas = true;
                        $scope.showStutas2 = false;
                        $scope.showStutas3 = false;
                        $(".flowPic_on2").css({ "width": "9%" });
                    }
                    if ($scope.sdetaildata.status == "已受理") {
                        $scope.showStutas = true;
                        $scope.showStutas2 = true;
                        $scope.showStutas3 = false;
                        $(".flowPic_on2").css({ "width": "55%" });
                    }
                    if ($scope.sdetaildata.status == "已完成") {
                        $scope.showStutas = true;
                        $scope.showStutas2 = true;
                        $scope.showStutas3 = true;
                        $(".flowPic_on2").css({ "width": "100%" });
                    }
                }
            })
        }
    };
    init();
});
zhongmai.filter("prodType", function () {
    return function (code) {
        if (code != null) {
            return code.substring(4, 8);
        }
    };
});
zhongmai.filter("prodColor", function () {
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