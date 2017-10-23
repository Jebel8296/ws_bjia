var zhongmai = angular.module('myApp', ["ngStorage", "ngCookies", "ui.router", 'LocalStorageModule', 'ui.bootstrap']);
//http 拦截
zhongmai.factory("httpInterceptor", ['$q', function () {
    console.log("http begin");
    var httpInterceptor = {
        request: function (req) {
            if (req.method == "POST") {
                //console.log(req);
                return req;
            }
        },
        requestError: function (err) {
            //console.log(err);
            return err;
        },
        response: function (res) {
           // console.log(res);
            return res;
        },
        responseError: function (err) {
            //console.log(err);
            return err;
        }
    };
    return httpInterceptor;
}]);
zhongmai.config(['$stateProvider', '$urlRouterProvider', '$httpProvider', '$locationProvider', 'localStorageServiceProvider',
    function ($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider, localStorageServiceProvider, $rootScope) {
        $httpProvider.interceptors.push(httpInterceptor);
        localStorageServiceProvider.setStorageType('sessionStorage');
        $urlRouterProvider.otherwise("/");
        //$locationProvider.html5Mode(true);
        $stateProvider
            .state('home', {
                title: '管理后台',
                url: '/',
                templateUrl: 'templete/home.html'
            })

            .state('downapp', {
                title: '下载页面',
                url: '/downapp',
                cache: 'false',
                templateUrl: 'templete/appdown.html'
            })
            .state('monitorLoglist', {
                title: '日志监控',
                url: '/log',
                cache: 'false',
                templateUrl: 'templete/monitor/log.html'
            })
            .state('monitorServicelist', {
                title: '服务监控',
                url: '/service',
                cache: 'false',
                templateUrl: 'templete/monitor/service.html'
            })
            .state('metricsServiceCtrl', {
                title: 'VertxMetrics',
                url: '/metrics',
                cache: 'false',
                templateUrl: 'templete/monitor/metrics.html'
            })
            .state('logServiceCtrl', {
                title: 'LogMonitor',
                url: '/logger',
                cache: 'false',
                templateUrl: 'templete/monitor/logger.html'
            })
            .state('deviceProductManager', {
                title: '设备编号管理',
                url: '/devices',
                cache: 'false',
                templateUrl: 'templete/devices/list.html'
            })
            .state('productInfoManager', {
                title: '产品信息管理',
                url: '/product',
                cache: 'false',
                templateUrl: 'templete/product/list.html'
            })


            .state('handlelist', {
                title: '售后受理',
                url: '/handle',
                cache: 'false',
                templateUrl: 'templete/afterhandle/list.html'
            })
            .state('handleinfo', {
                url: '/handle',
                cache: 'false',
                templateUrl: "templete/afterhandle/handle.html"
            })
            .state('changepwd', {
                title: '修改密码',
                url: '/changepwd',
                cache: 'false',
                templateUrl: 'templete/password/changepasswords.html'
            })
            .state('forgetpw', {
                title: '忘记密码',
                url: '/forgetpw',
                cache: 'false',
                templateUrl: 'templete/password/forgetpw.html'

            })
            .state('noticelist', {
                url: '/noticelist',
                cache: 'false',
                templateUrl: 'templete/servicetype.html'
            })
            //购机协议
            .state('protocol', {
                url: '/protocol',
                cache: 'false',
                templateUrl: 'templete/protocol.html'
            })
            .state('systemnotice', {
                url: '/notice',
                cache: 'false',
                templateUrl: 'templete/systemnotice.html'
            })
    }
]);
zhongmai.run(['$location', '$rootScope', function ($location, $rootScope) {
    $rootScope.$on('$stateChangeSuccess', function (event, toState) {
        $rootScope.title = toState.title
    });
}]);
