/**
 * Created by dell on 2016/8/18.
 */
zhongmai.controller('ConfirmCtrl', function ($scope, $rootScope, $http, $location, $state, $window, localStorageService, $stateParams, $cookieStore, UrlService) {
    if ($cookieStore.get("uid") == null) {
        $state.go('home');
        showLR('login');
    }
    $scope.autoSelected = false;
    $scope.division = UrlService.EXPRESS.address();
    $scope.priceTotal = 0;
    $scope.comfirmTotal = parseInt($stateParams.comfirmTotal);
    $scope.confirmData = [];
    $scope.productL = {};
    $scope.addressL = [];
    $scope.addrId = null;
    var proLi = [];
    init();
    function init() {
        var param = { 'uid': Number($cookieStore.get("uid")) };
        UrlService.CART.account(param).then(function (data) {
            if (data.code == 200) {
                $scope.confirmData = data.respData;
                $scope.addressL = $scope.confirmData.address;
                if ($scope.addressL != null) {
                    for (var j = 0; j < $scope.addressL.length; j++) {
                        if ($scope.addressL[j].status == 1) {
                            $scope.addrId = $scope.addressL[j].eid;
                        }
                    }
                }
                totalPrize();
            }
        });
    }
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
    }

    $scope.totalOfCom = 0;
    function totalPrize() {
        $scope.priceTotal = 0;
        $scope.someTotal = 0;
        angular.forEach($scope.confirmData.product, function (item, index) {
            $scope.priceTotal += item.prodprice * item.prodtotal;
        })
        $scope.totalOfCom = $scope.priceTotal + $scope.companyPrice;

    }
    $scope.getAddr = function (Iitem) {
        $scope.addrId = Iitem.eid;
        /**
        angular.forEach($scope.addressL, function (item, index) {
            if (Iitem.eid == item.eid) {
                item.status = 1;
            } else {
                item.status = 0
            }
        }) */
    }
    $scope.faxValidate = function () {
        if ($scope.fax) {
            $(".faxTest").text("发票抬头在提交订单后不可变更请谨慎填写");
        } else {
            $(".faxTest").text("");
        }
    }
    $scope.showBill = function () {
        $scope.fax = true;
        $(".faxTest").text("发票抬头在提交订单后不可变更请谨慎填写");

    }
    $scope.agree = true;
    $scope.confirmt = function () {
        console.log($scope.addrId);
        var faxT = $("#invoce").val();
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
        if ($scope.fax == true && faxT.length == 0) {
            alert("发票信息不能为空!");
            return false;
        }
        if ($scope.confirmData.product == null) {
            alert("商品信息不能为空！");
            return false;
        }

        if ($scope.agree == false) {
            $(".agreeT").text("请先同意协议!");
            return false;
        } else {
            $(".agreeT").text("");
            var param = { uid: Number($cookieStore.get("uid")), expressid: $scope.addrId, logistics: $scope.company, delivers: $scope.sendtime, invoce: $scope.invoce };
            UrlService.ORDER.add(param).then(function (data) {
                if (data.code == 200) {
                    $scope.ordercode = data.respData.ordercode;
                    $state.go("commonP", { uid: Number($cookieStore.get("uid")), cid: $scope.ordercode, payT: $scope.totalOfCom });
                }
            })
        }
    }
    $scope.confirmSetDef = function (item) {
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
                        for (var i = 0; i < $scope.addressL.length; i++) {
                            if ($scope.addressL[i].status == 1 && $scope.addressL[i].eid != item.eid) {
                                $scope.addressL[i].status = 0;
                                $scope.setDef(item);
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
                var param = { "uid": Number($cookieStore.get("uid")), "name": item.name, "phone": item.phone, "zipcode": item.zipcode, "address": item.address, "province": item.provname, "city": item.cityname, "area": item.areaname, "def": item.status }
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
            }
        } else {
            $scope.submitted = true;
        }
    };
})
zhongmai.filter("prodType", function () {
    return function (code) {
        if (code != null) {
            return code.substring(4, 8);
        }
    };
});
zhongmai.filter("prodColor", function () {
    return function (code) {
        var colorName;
        if (code != null) {
            switch (code.substring(8, 10)) {
                case "01":
                    colorName = "经典黑";
                    break;
                case "02":
                    colorName = "白蓝色";
                    break;
                default:
                    colorName = "经典黑";
            }
        }
        return colorName;
    };
});