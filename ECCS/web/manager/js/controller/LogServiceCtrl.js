/**
 * Created by Administrator on 2016/11/18.
 */
zhongmai.controller("LogServiceCtrl", function ($scope, $rootScope, $http, $timeout, $location, localStorageService, $state, $window) {
    $scope.logs = [];
    $scope.eb = null;

    if ($scope.eb != null) {
        $scope.eb.close();
    }
    $scope.eb = new EventBus('http://10.12.12.199:9999/monitor');
    $scope.eb.onopen = function () {
        $scope.eb.registerHandler('monitor.logger', function (err, message) {
            var res = message.body;
            if (res != null) {
                $scope.logs = $scope.logs.concat(res);
                $scope.$apply();
            }
        });
    }
})