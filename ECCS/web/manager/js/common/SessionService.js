/**
 * Created by dell on 2016/10/17.
 */


zhongmai.service("SessionService",function($scope,$localStorage, $rootScope, parametersJsonService){
    this.rootScope = $rootScope;
    this.storage = $localStorage;

    SessionService.prototype.setAgent = function(AgentInfo) {
        this.rootScope.$$AgentInfo = AgentInfo;
    };

});



