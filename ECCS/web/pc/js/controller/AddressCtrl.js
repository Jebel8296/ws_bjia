'use strict'
zhongmai.controller('AddressCtrl', function ($scope, $rootScope, $http, localStorageService, $timeout, $window, $state, $cookieStore, UrlService) {
    if ($cookieStore.get("uid") == null) {
        $state.go('home');
        $("#login").modal('show');
    }
    $scope.division = UrlService.EXPRESS.address();
    $scope.addrlist = [];
    $scope.addressitem = { name: null, provname: null, cityname: null, areaname: null, address: null, phone: null, zipcode: null };
    function init() {
        var param = { uid: Number($cookieStore.get("uid")) };
        UrlService.EXPRESS.get(param).then(function (data) {
            $scope.addrlist = data.respData;
            localStorageService.set('address', $scope.addrlist);
        });
    }
    init();
    $scope.openAddrLayer = function (item) {
        if (item == 'new') {
            $scope.title = "新增地址";
        } else {
            $scope.title = "修改地址";
            if ($scope.addrlist != null) {
                for (var i = 0; i < $scope.addrlist.length; i++) {
                    if (item.eid == $scope.addrlist[i].eid) {
                        $scope.addressitem = $scope.addrlist[i];
                    }
                }
            }
        }
    }
    $scope.addOrUpdAddress = function (item) {
        var self = this;
        if (item.status == true) { item.status = 1; } else { item.status = 0; }
        if (self.addressForm.$valid) {
            if (item.address == null || item.address.length > 20) {
                alert("详细地址过长，重新输入.");
                return false;
            } 
            if (item.name == null || item.name.length > 10) {
                alert("收件人姓名过长，重新输入.");
                return false;
            }
            if (item.eid) {
                var proquery = { uid: Number($cookieStore.get("uid")), eid: item.eid, name: item.name, phone: item.phone, zipcode: item.zipcode, address: item.address, province: item.provname, city: item.cityname, area: item.areaname, def: item.status };
                UrlService.EXPRESS.mod(proquery).then(function (data) {
                    if (data.code == 200) {
                        if (item.status == 1) {
                            for (var i = 0; i < $scope.addrlist.length; i++) {
                                if ($scope.addrlist[i].status == 1 && $scope.addrlist[i].eid != item.eid) {
                                    $scope.addrlist[i].status = 0;
                                    $scope.setDef(item);
                                }
                            }
                        }
                        $('#address').modal('hide');
                        setTimeout(function () {
                            $scope.$apply(function () {
                                init();
                            });
                        }, 200);
                    }
                })
            } else {
                var proquery = { uid: Number($cookieStore.get("uid")), name: item.name, phone: item.phone, zipcode: item.zipcode, address: item.address, province: item.provname, city: item.cityname, area: item.areaname, def: item.status };
                UrlService.EXPRESS.add(proquery).then(function (data) {
                    if (data.code == 200) {
                        $('#address').modal('hide');
                        setTimeout(function () {
                            $scope.$apply(function () {
                                init();
                            });
                        }, 200);
                    }
                });
            }
        } else {
            $scope.submitted = true;
        }
    };
    $scope.deladdr = function (eid) {
        var param = { uid: Number($cookieStore.get("uid")), eid: eid };
        if ($window.confirm("确定要删除吗？")) {
            UrlService.EXPRESS.del(param).then(function (data) {
                if (data.code == 200) {
                    setTimeout(function () {
                        $scope.$apply(function () {
                            init();
                        });
                    }, 200);
                    $scope.isShow = false;
                }
            })
        }
    }
    $scope.setDef = function (item) {
        var param = { uid: Number($cookieStore.get("uid")), eid: item.eid };
        UrlService.EXPRESS.def(param).then(function (data) {
            if (data.code == 200) {
                setTimeout(function () {
                    $scope.$apply(function () {
                        init();
                    });
                }, 200);
            }
        })
    }
    $scope.cancel = function () {
        $scope.address = angular.copy($scope.address);
        $('#address').modal('hide');
    }
})