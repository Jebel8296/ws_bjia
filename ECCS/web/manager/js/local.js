'use stric'
zhongmai.factory('locals',['$window',function($window){
        return{
           //�洢��������
            set :function(key,value){
                $window.localStorage[key]=value;
            },
            //��ȡ��������
            get:function(key,defaultValue){
                return  $window.localStorage[key] || defaultValue;
            },
             //�洢������JSON��ʽ�洢
            setObject:function(key,value){
                $window.localStorage[key]=JSON.stringify(value);
            },
             //��ȡ����
            getObject: function (key) {
                return JSON.parse($window.localStorage[key] || '{}');
            }

        }
}]);