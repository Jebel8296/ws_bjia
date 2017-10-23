zhongmai.factory('basicAjax',function($http,$timeout){
    return {
        getServerDataConfig:function(obj){//暂无Promise
            var sendObj= {
                url: obj.url,
                method:obj.method,
                params: obj.data
            };
            if(obj.params.length==0){
                delete sendObj.params;
            }
            if(obj.method.toLowerCase()=='post'){//传json
                sendObj.data=obj.data;
                delete sendObj.params;
            }
            $http(sendObj).success(function( data,status,headers,config){
               //console.log('factory-所有配置:', data,data.user);
                $timeout(function(){
                    obj.fn(data);
                },30);
            });
        }
    }
});
