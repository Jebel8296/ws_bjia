zhongmai.factory('UserInterceptor', ["$q","$rootScope",function ($q,$rootScope) {
   /* return {
        request:function(config){
            config.headers["TOKEN"] = $rootScope.userPhone.token;
            return config;
        },
        responseError: function (response) {
            var data = response.reqData;
            // �жϴ����룬�����δ��¼
            if(data.code == "500"){
                // ����û�����token�洢����Ϣ�����
                $rootScope.userPhone = {token:""};

                // ȫ���¼�����������view��ȡ���¼�����������Ӧ����ʾ����
                $rootScope.$emit("userIntercepted","notLogin",response);
            }
            // ����ǵ�¼��ʱ
            if(data.code == "500"){
                $rootScope.$emit("userIntercepted","sessionOut",response);
            }
            return $q.reject(response);
        }
    };*/
}]);