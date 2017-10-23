app.controller("MonitorServiceCtrl", function ($scope,$http) {
    $scope.services = [];

    function query() {
        $http({
            method: 'GET',
            url: "http://10.12.12.210:9900/rest/monitor/service/test",
            header: {'Content-Type': 'application/json', 'withCredentials': true, languageColumn: 'name_eu'}
        }).success(function (data) {
            if (data.code == 200) {
                $scope.services = data.respData;
            }
        }).error(function (error) {
            console.log(error);
        })
    }
    query();
    $scope.submitQuery = function () {
        query();
    }
});

app.controller("MonitorMetricsCtrl", function ($scope) {
    $scope.metrics = [];
    $scope.eb = null;

    if ($scope.eb != null) {
        $scope.eb.close();
    }
    $scope.eb = new EventBus('http://10.12.12.210:9900/monitor');
    $scope.eb.onopen = function () {
        $scope.eb.registerHandler('zm3c.monitor.metrics', function (err, message) {
            var res = message.body;
            if (res != null) {
                $scope.metrics = res;
                $scope.$apply();
            }
        });
    }
});
app.controller("MonitorLogCtrl", function ($scope) {
    $scope.logs = [];
    $scope.eb = null;

    if ($scope.eb != null) {
        $scope.eb.close();
    }
    $scope.eb = new EventBus('http://10.12.12.210:9900/monitor');
    $scope.eb.onopen = function () {
        $scope.eb.registerHandler('zm3c.monitor.log', function (err, message) {
            var res = message.body;
            if (res != null) {
                $scope.logs = $scope.logs.concat(res);
                $scope.$apply();
            }
        });
    }
})