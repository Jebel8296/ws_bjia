'use strict';

/* Controllers */
  // signin controller
app.controller('SigninFormController', ['$scope', '$http', '$state','$cookies', '$rootScope',function($scope, $http, $state,$cookies,$rootScope) {
    $scope.user = {};
    $scope.authError = null;
    $scope.login = function() {
      $scope.authError = null;
      // Try to login
      $http.post($rootScope.backsage_url+'/user/login', {username: $scope.user.phone, password: $scope.user.password})
      .then(function(response) {
          //console.log(response);
        if ( response.data.code == 200 ) {
           $cookies.ukey = response.data.respData.uid;
           $cookies.token = response.data.respData.token;
           $state.go('app.dashboard-v1');
        }else{
            $scope.authError = response.data.msg;
        }
      }, function(x) {
        $scope.authError = 'Server Error';
      });
    };
  }])
;