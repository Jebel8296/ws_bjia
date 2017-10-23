/**
 * Created by dell on 2016/8/18.
 */
zhongmai.controller('ConfirmByIdCtrl', function ($scope, $rootScope, $http, $location, $state, $window, localStorageService, $stateParams, $timeout, $cookieStore, UrlService) {
    if ($cookieStore.get("uid") == null) {
        $state.go('home');
        showLR('login');
    };
    $scope.division = UrlService.EXPRESS.address();
    $scope.priceTotal = 0;
    $scope.totalOfCom = 0;
    var confiId = $stateParams.comfirmId;
    $scope.comfirmTotal = parseInt($stateParams.comfirmTotal);
    $scope.confirmData = [];
    $scope.productL = {};
    $scope.addrId = null;
    var proLi = [];
    init();
    function init() {
        var param = { uid: Number($cookieStore.get("uid")) };
        UrlService.EXPRESS.get(param).then(function (data) {
            $scope.addrlist = data.respData;
            if ($scope.addrlist != null) {
                for (var j = 0; j < $scope.addrlist.length; j++) {
                    if ($scope.addrlist[j].status == 1) {
                        $scope.addrId = $scope.addrlist[j].eid;
                    }
                }
            }
        });
        getProdata();
    };
    $scope.addOrUpdAddress = function (item) {
        var self = this;
        if (item.status == true) { item.status = 1; } else { item.status = 0; }
        if (self.addressForm.$valid) {
            if (item.address == null || item.address.length > 30) {
                alert("详细地址过长，重新输入.");
                return false;
            }
            var param = { uid: Number($cookieStore.get("uid")), name: item.name, phone: item.phone, zipcode: item.zipcode, address: item.address, province: item.provname, city: item.cityname, area: item.areaname, def: item.status };
            UrlService.EXPRESS.add(param).then(function (data) {
                if (data.code == 200) {
                    $('#address').modal('hide');
                    setTimeout(function () {
                        $scope.$apply(function () {
                            init();
                        });
                    }, 200);
                } else if (data.code == 500) {
                    alert(data.msg);
                }
            });
        } else {
            $scope.submitted = true;
        }
    };
    $scope.companyPrice = 0;
    $scope.company = "YZXB";
    $scope.sendtime = "allday";
    $scope.changeCompany = function (company) {
        switch (company) {
            case 'YZXB':
                $scope.companyPrice = 0;
                break;
            case 'SF':
                $scope.companyPrice = 18;
                break;
        }
        totalPrize();
    };
    function getProdata() {
        var param = { prod: 1, prodcode: confiId };
        UrlService.PRODUCT.get(param).then(function (data) {
            if (data.code == 200) {
                $scope.productL = data;
                totalPrize();
            }
        })
    };
    function totalPrize() {
        $scope.priceTotal = 0;
        angular.forEach($scope.productL.respData, function (item, index) {
            $scope.priceTotal += item.prodprice * $scope.comfirmTotal;
        })
        $scope.totalOfCom = $scope.priceTotal + $scope.companyPrice;
    };
    $scope.getAddr = function (Iitem) {
        $scope.addrId = Iitem.eid;
        /**
        angular.forEach($scope.addrlist, function (item, index) {
            if (Iitem.eid == item.eid) {
                item.status = 2;
            } else {
                item.status = 0
            }
        }) */
    };
    $scope.flag = 1;
    $scope.addressitem = { name: null, provname: null, cityname: null, areaname: null, address: null, phone: null, zipcode: null };
    $scope.openAddrLayer = function (item, flag) {
        if (flag == 'add') {
            $scope.flag = 1;
            $scope.title = "新增收货地址";
        } else {
            $scope.flag = 0;
            $scope.title = "修改收货地址";
            $scope.addressitem = item;
        }
    };
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
    };
    $scope.addOrUpdAddress = function (item) {
        var self = this;
        if (item.status == true) { item.status = 1; } else { item.status = 0; }
        if (self.addressForm.$valid) {
            if (item.address == null || item.address.length > 30) {
                alert("详细地址过长，重新输入.");
                return false;
            }
            if ($scope.flag == 0) {
                var param = { "uid": Number($cookieStore.get("uid")), "eid": item.eid, "name": item.name, "phone": item.phone, "zipcode": item.zipcode, "address": item.address, "province": item.provname, "city": item.cityname, "area": item.areaname, "def": item.status }
                UrlService.EXPRESS.mod(param).then(function (data) {
                    if (data.code == 200) {
                        $('#address').modal('hide');
                        for (var i = 0; i < $scope.addrlist.length; i++) {
                            if ($scope.addrlist[i].status == 1 && $scope.addrlist[i].eid != item.eid) {
                                $scope.addrlist[i].status = 0;
                                $scope.setDef(item);
                            }
                        }
                    }
                })
            } else {
                var param = { "uid": Number($cookieStore.get("uid")), "name": item.name, "phone": item.phone, "zipcode": item.zipcode, "address": item.address, "province": item.provname, "city": item.cityname, "area": item.areaname, "def": item.status }
                UrlService.EXPRESS.add(param).then(function (data) {
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
    $scope.faxValidate = function () {
        if ($scope.fax) {
            $(".faxTest").text("发票抬头在提交订单后不可变更请谨慎填写");
        } else {
            $(".faxTest").text("");
        }
    };
    $scope.showBill = function () {
        $scope.fax = true;
        $(".faxTest").text("发票抬头在提交订单后不可变更请谨慎填写");

    };
    $scope.agree = true;
    $scope.confirmtBy = function () {
        if ($scope.addrId == null) {
            alert("请选择收货地址");
            return false;
        }
        if ($scope.sendtime == null) {
            alert("请选择配送时间");
            return false;
        }
        if ($scope.invoce != null) {
            if ($scope.invoce.length > 30) {
                alert("发票名称不能大于30个字符！");
                return false;
            }
        }
        if ($scope.agree == false) {
            $(".agreeT").text("请先同意协议!");
            return false;
        } else {
            $scope.showagree = false;
            var product = { code: $scope.productL.respData[0].prodcode, total: $scope.comfirmTotal };
            var param = { uid: Number($cookieStore.get("uid")), expressid: $scope.addrId, logistics: $scope.company, delivers: $scope.sendtime, invoce: $scope.invoce, product: product };
            UrlService.ORDER.add(param).then(function (data) {
                if (data.code == 200) {
                    $scope.ordercode = data.respData.ordercode;
                    $state.go("commonP", { 'uid': Number($cookieStore.get("uid")), cid: $scope.ordercode, payT: $scope.totalOfCom });
                } else if (data.code == 500) {
                    console.log(data.code + "test");
                }
            })
        }
    };
})