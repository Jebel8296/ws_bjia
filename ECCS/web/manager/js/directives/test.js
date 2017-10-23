/**
 * Created by dell on 2016/10/20.
 */
zhongmai.factory('api', function ($http, $localStorage) {
    return {
        init: function (token) {
            $http.defaults.headers.common['Token'] = token || $localStorage.loginForm
        }
    };
});