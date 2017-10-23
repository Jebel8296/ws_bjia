zhongmai.factory('UserInterceptor', ["$q","$rootScope",function ($q,$rootScope) {
   /* return {
        request:function(config){
            config.headers["TOKEN"] = $rootScope.userPhone.token;
            return config;
        },
        responseError: function (response) {
            var data = response.reqData;
            // 判断错误码，如果是未登录
            if(data.code == "500"){
                // 清空用户本地token存储的信息，如果
                $rootScope.userPhone = {token:""};

                // 全局事件，方便其他view获取该事件，并给以相应的提示或处理
                $rootScope.$emit("userIntercepted","notLogin",response);
            }
            // 如果是登录超时
            if(data.code == "500"){
                $rootScope.$emit("userIntercepted","sessionOut",response);
            }
            return $q.reject(response);
        }
    };*/
}]);