/**
 * Created by Administrator on 2016/11/18.
 */
zhongmai.controller("MonitorServiceCtrl", function ($scope, $rootScope, $http, $timeout, $location, localStorageService, $state, $window) {
    $scope.services = [];
    $scope.eb = null;

    if ($scope.eb != null) {
        $scope.eb.close();
    }

    $scope.myfilterForDev = function (item) {
        return item.service.substring(0,5)=='1zm3c'
    }

    $scope.myfilterForTest = function (item) {
        return item.service.substring(0,4)=='zm3c'
    }

    $scope.eb = new EventBus('http://10.12.12.199:9999/monitor');
    $scope.eb.onopen = function () {
        $scope.eb.registerHandler('monitor.service', function (err, message) {
            var res = message.body;
            if (res != null) {
                console.log(res);
                $scope.services = res;
                $scope.$apply();
            }
        });
    }
})