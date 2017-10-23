'use strict'
zhongmai.controller('ProductCtrl', function ($scope, $rootScope, $http, localStorageService, $location, $stateParams, $state, $cookieStore, UrlService) {
    menuFixed('info');
    function menuFixed(id) {
        var divBox = document.getElementById(id);
        if (divBox) {
            var _getHeight = divBox.offsetTop;
            window.onscroll = function () {
                changePos(id, _getHeight);
            }
        }
    }
    function changePos(id, height) {
        var divBox = document.getElementById(id);
        var style = { color: "#fcc", position: "absolute" };
        if (divBox) {
            var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
            if (scrollTop < height) {
                divBox.style.position = 'static';
            } else {
                divBox.style.position = 'fixed';
                divBox.style.top = '0';
                divBox.style.zIndex = '999';
            }
        }
    }
    $scope.modalCss = "leftIimgformodel1";
    if ($rootScope.prodModel != null && $rootScope.prodModel.code == "C100") {
        $scope.modalCss = "leftIimgformodel2";
    }
    $scope.isCheced = false;
    $rootScope.colorSelected = false;
    $scope.radioChange = function (colorcode) {
        for (var i = 0; i < $rootScope.modelData.length; i++) {
            if ($rootScope.modelData[i].color == colorcode) {
                $rootScope.selectModelDataPrice = $rootScope.modelData[i].prodprice;
                $rootScope.selectModelDataName = $rootScope.modelData[i].prodname;
                $rootScope.selectModelDataColor = $rootScope.modelData[i].color;
                $rootScope.selectModelData = $rootScope.modelData[i];
                break;
            }
            $rootScope.colorSelected = false;
        }
    }
    function getData() {
        var param = { 'uid': Number($cookieStore.get("uid")) };
        UrlService.CART.list(param).then(function (data) {
            if (data.code == 200) {
                localStorageService.set("myCart", data.respData.data);
                localStorageService.set("cartLength", data.respData.data.length);
                $rootScope.cartLength = localStorageService.get("cartLength");
            }
        })
    }
    $scope.itemcount = 1
    $scope.addToCart = function () {
        if ($cookieStore.get("uid") == null || $cookieStore.get("token") == null) {
            $state.go('home');
            showLR('login');
            return;
        }
        var param = { uid: Number($cookieStore.get("uid")), code: $rootScope.selectModelData.prodcode, total: $scope.itemcount }
        UrlService.CART.add(param).then(function (data) {
            if (data.code == 200) {
                getData();
                $('#tocart').modal('hide');
            } else if (data.code == 506) {
                $state.go('home');
                showLR('login');
            }
        })
    }
    $scope.goBuy = function () {
        $state.go('comfirmby', { comfirmId: $rootScope.selectModelData.prodcode, comfirmTotal: $scope.itemcount });
        $('#tocart').modal('hide');
    }
    $scope.goBuy2 = function () {
        var param = { uid: $rootScope.uid, code: $rootScope.selectModelData.prodcode, total: $scope.itemcount };
        UrlService.CART.add(param).then(function (data) {
            if (data.code == 200) {
                $state.go('comfirm', {});
                $state.go('servicedetail', { afterid: item.aftercode });
                $('#tocart').modal('hide');
            } else if (data.code == 506) {
                $state.go('home');
                showLR('login');
            }
        })
    }
    $scope.addCount = function () {
        $scope.itemcount++;
    }
    $scope.minusCount = function () {
        if ($scope.itemcount == 1) {
            return;
        } else {
            $scope.itemcount--;
        }
    }
    $scope.tabs = [
        { title: '概览', url: 'templete/common/productdetail2.html' },
        //  {title: '概览', url: 'templete/common/productdetail.html'},
        // {title: '', url: 'templete/common/productdetail2.html'}
        //{title: '产品规格', url: 'templete/common/productdetail.html'}
    ];
    $scope.currentTab = 'templete/common/productdetail2.html';
    $scope.onClickTab = function (tab) { $scope.currentTab = tab.url; }
    $scope.isActiveTab = function (tabUrl) { return tabUrl == $scope.currentTab; }
    $scope.toTianmao = function () {
        alert("跳转到天猫的链接，暂时没有")
    }
    //$('.leftIimgformodel').removeClass('leftIimgformodel')
})
