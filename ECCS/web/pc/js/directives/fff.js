/**
 * Created by dell on 2016/10/21.
 */
zhongmai.directive('checkUser', ['$rootScope', '$location', 'userSrv', function ($root, $location, UserService) {
    return {
        link: function (scope, elem, attrs, ctrl) {
            $root.$on('$routeChangeStart', function(event, currRoute, prevRoute){
                if (!prevRoute.access.isFree && !UserService.isLogged) {
                    alert("sdfsdfsdf")
                }
                /*
                 * IMPORTANT:
                 * It's not difficult to fool the previous control,
                 * so it's really IMPORTANT to repeat the control also in the backend,
                 * before sending back from the server reserved information.
                 */
            });
        }
    }
}]);