zhongmai.controller("OrderCtrl", function ($scope, $location, $http, $rootScope, $state, localStorageService, $stateParams, $window, $cookieStore, UrlService) {

    if ($cookieStore.get("uid") == null) {
        $state.go('home');
        showLR('login');
    }
    $scope.formatstatus = function (status) {
        if (status == 1) { return "待支付"; }
        if (status == 2) { return "待发货"; }
        if (status == 3) { return "已发货"; }
        if (status == 4) { return "交易完成"; }
        if (status == 5) { return "已取消"; }
        if (status == 6) { return "已拒收"; }
        if (status == 7) { return "交易关闭"; }
    }
    $scope.formatForCom = function (status) {
        if (status == "YZXB") { return "EMS（免费）"; }
        if (status == "SF") { return "顺风速运（18元）"; }
    }
    // 单选按钮事件
    $("#Iradio li").on("click", function () {
        $(".radio_img").attr("src", "images/dunchecked_gr1.png")
        $(this).find(".radio_img").attr("src", "images/dchecked_gr1.png");
        $("#dfd").attr("disabled", false); //设置disabled为false
    })
    var pageNum = 0;
    getData(1);

    //数据源
    $scope.showpage = true;
    $scope.datatotal = 0;
    $scope.ols = {};
    $scope.pageSize = 0;
    $scope.pageList = [];
    function getData(pageNum) {
        var orderList = { uid: Number($cookieStore.get("uid")), pageNum: pageNum, pageSize: 10 };
        UrlService.ORDER.search(orderList).then(function (data) {
            if (data.code == 200) {
                $scope.ols = data.respData;
                if ($scope.ols != null && $scope.ols.data != null) {
                    $scope.datatotal = data.respData.total;
                    if ($scope.datatotal == 0) {
                        $scope.paging = false;
                        $scope.showpage = false;
                    }
                    $scope.pageSize = data.respData.pageSize;
                    $scope.pages = Math.ceil($scope.datatotal / $scope.pageSize);
                    localStorageService.set('pages', $scope.pages);
                } else {
                    $scope.paging = false;
                }
            }
        });
    }
    var pages = localStorageService.get('pages');
    for (var i = 0; i < pages; i++) {
        $scope.pageList.push(i + 1);
    }
    //默认选择
    $scope.selPage = 1;
    //打印当前选中页索引
    $scope.selectPage = function (page) {
        //不能小于1大于最大
        if (page < 1 || page > $scope.pages) {
            return false;
        }
        $scope.selPage = page;
        getData(page);
        $scope.isActivePage(page);
        //console.log("选择的页：" + page);
    };
    //设置当前选中页样式
    $scope.isActivePage = function (page) {
        return $scope.selPage == page;
    };
    //上一页
    $scope.Previous = function () {
        $scope.selectPage($scope.selPage - 1);
    }
    //下一页
    $scope.Next = function () {
        $scope.selectPage($scope.selPage + 1);
    };
    //订单详情
    $scope.orderDetail = function (item) {
        $state.go('orderdetail', { orderId: item.oid });
    }
    $scope.cannelReason = 0;
    $scope.focusItem = null;
    $scope.setItem = function (item) {
        $scope.focusItem = item;
    }
    //取消订单
    $scope.orderCancel = function () {
        var r = $scope.cannelReason;
        var item = $scope.focusItem;
        var reason = "";
        if (r != 0) {
            if (r == 1) {
                reason = "不想买了";
            }
            if (r == 2) {
                reason = "信息填写错误，重新购买";
            }
            if (r == 3) {
                reason = "付款遇到问题";
            }
            if (r == 4) {
                reason = "商品选择错误";
            }
            if (r == 5) {
                reason = "其他原因";
            }
        }else{
            alert("请选择取消订单原因.");
            return;
        }
        var data = { 'uid': Number($cookieStore.get("uid")), 'orderid': item.oid, 'reason': reason };
        if (item.status == 2) {
            UrlService.ORDER.cannel(data).then(function (response) {
                if (response.code == 200) {
                    alert("订单取消成功");
                    location.reload();
                } else {
                    alert("订单取消失败，请稍后再试！");
                }
            });
        } else {
            UrlService.ORDER.close(data).then(function (response) {
                if (response.code == 200) {
                    alert("订单取消成功");
                    location.reload();
                } else {
                    alert("订单取消失败，请稍后再试！");
                }
            });
        }
    }
    //确认收货
    $scope.receiptGoods = function (item) {
        var data = { 'uid': Number($cookieStore.get("uid")), 'orderid': item.oid };
        UrlService.ORDER.receipt(data).then(function (response) {
            if (response.code == 200) {
                alert("确认成功");
                location.reload();
            } else {
                alert("确认失败，请稍后再试！");
            }
        })
    }

    //跳转到提交申请页面
    $scope.serviceDetail = function (oitem, pitem) {
        $state.go('servicereq', { orderId: oitem.oid, servId: pitem.code });
    }
    //去支付
    $scope.toPayinOrderP = function (pitem) {
        var pT = 0;
        angular.forEach($scope.olsdata, function (item, index) {
            if (item.oid == pitem.oid) {
                for (var i = 0; i < item.prods.length; i++) {
                    pT += item.prods[i].fee
                }
            }
        });
        var data = { 'uid': Number($cookieStore.get("uid")), 'orderid': pitem.oid, 'payfee': pT + "" };
        UrlService.PAY.jumppay(data).then(function (response) {
            if (response.code == 200) {
                $window.open(response.respData.payurl);
            }
        })
    }


})
