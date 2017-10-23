/**
 * Created by dell on 2016/10/26.
 */
zhongmai.controller('ServList', function ($scope, $rootScope, $http, $state, $stateParams, localStorageService, $cookieStore, UrlService) {

    if ($cookieStore.get("uid") == null) {
        $state.go('home');
        showLR('login');
    }

    var pages = 0;
    //数据源
    $scope.serviceList = {};
    $scope.pageList = [];//页码
    $scope.datatotal = 0;//总条数
    $scope.itemTotal = 0;//每页显示条数
    $scope.pageTotal = 0;
    $scope.paging = true;
    var pageNum = 1;

    getData(1);
    function getData(pageNum) {
        var data = { 'uid': Number($cookieStore.get("uid")), "pageNum": pageNum, "pageSize": 10 };
        UrlService.AFTERSALE.search(data).then(function (data) {
            $scope.serviceList = data.respData;
            if ($scope.serviceList != null && $scope.serviceList.data != null) {
                $scope.paging = true;
                $scope.datatotal = $scope.serviceList.total;
                $scope.itemTotal = $scope.serviceList.pageSize;
                $scope.pageTotal = Math.ceil($scope.datatotal / $scope.itemTotal);
                localStorageService.set('pageTotal', $scope.pageTotal);
            } else {
                $scope.paging = false;
            }
        })
    }

    var t = localStorageService.get('pageTotal');
    for (var i = 0; i < t; i++) {
        $scope.pageList.push(i + 1);
    }



    //默认选择
    $scope.selPage = 1;
    //打印当前选中页索引
    $scope.selectPage = function (page) {
        //不能小于1大于最大
        if (page < 1 || page > $scope.pages) { return false; }
        $scope.selPage = page;
        getData(page);
        $scope.isActivePage(page);
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

    //售后详情
    $scope.viewService = function (item) {
        $state.go('servicedetail', { afterid: item.aftercode });
    }

    //再次申请
    $scope.secodeService = function (item) {
        $state.go('secondservice', { afterid: item.aftercode });
    }

    //取消售后
    $scope.cancelSale = function (item) {
        if (confirm("确认取消吗？")) {
            var params = { 'uid': Number($cookieStore.get("uid")), 'aftercode': item.aftercode };
            UrlService.AFTERSALE.cancel(params).then(function (response) {
                if (response.code == 200) {
                    alert("订单取消成功");
                    location.reload();
                } else {
                    alert("订单取消失败，请稍后再试！");
                }
            });
        }
    }
    $scope.focusItem = null;
    $scope.setItem = function (item) {
        $scope.focusItem = item;
    }
    //上传售后物流信息
    $scope.logisticsCode = null;
    $scope.logisticsName = "0";
    $scope.otherLogisticsName = null;
    $scope.uploadSale = function () {
        if ($scope.logisticsName == 0) {
            alert("请选择物流公司");
            return;
        }
        if ($scope.logisticsName == 5 && $scope.otherLogisticsName == null) {
            alert("请输入其他物流公司");
            return;
        }
        if ($scope.logisticsName == 5 && $scope.otherLogisticsName.length > 20) {
            alert("输入的物流公司名称太长，请重新输入.");
            return;
        }
        if ($scope.logisticsCode == null) {
            alert("请输入货运单号.");
            return;
        }
        if ($scope.logisticsCode.length > 20) {
            alert("您输入货运单号太长，请检查货运单号是否正确.");
            return;
        }
        if ($scope.logisticsName == 5) {
            $scope.logisticsName = $scope.otherLogisticsName;
        }
        var params = { uid: Number($cookieStore.get("uid")), aftercode: $scope.focusItem.aftercode, logisticsCode: $scope.logisticsCode, logisticsName: $scope.logisticsName };
        UrlService.AFTERSALE.upload(params).then(function (response) {
            if (response.code == 200) {
                alert("操作成功");
                $scope.focusItem = "0";
                location.reload();
            } else {
                alert("操作失败，请稍后再试！");
            }
        });
    }

    //获取验证码
    $scope.codeData = {};
    $scope.scode = "";
    $scope.codeS = "";
    $scope.getCode = function () {
        var params = { 'uid': Number($cookieStore.get("uid")) };
        UrlService.CODE.generate(params).then(function (response) {
            if (response.code == 200) {
                $scope.codeData = response.respData;
                $scope.codeS = $scope.codeData.code;
                $scope.scode = "data:image/jpeg;base64," + $scope.codeData.url;
            }
        });
    }

    $scope.buyDate = new Date(); // 定义一个属性来接收当天日期


    //转换时间
    function getIntDate() {
        var dataY = $scope.buyDate.getFullYear();
        var dataM = $scope.buyDate.getMonth() + 1;
        var dataD = $scope.buyDate.getDate();
        var dataFu = parseInt(dataY + "" + dataM + dataD + "" + $scope.buyDate.getHours() + $scope.buyDate.getSeconds());
        return dataFu;
    }

    var decives = "10223003C4001010002", deciveDate = $scope.buyDate.getTime();
    //根据设备提交事件
    $scope.reqSeByI = function () {
        if ($scope.reByIdForm.$valid) {
            if ($scope.codeR != $scope.codeS) {
                alert("验证码错误，请重新输入");
                return false;
            }

            //表单字段
            var orderList = {
                'uid': Number($cookieStore.get("uid")),
                "devicescode": $scope.equipmentId,
                "date": deciveDate,
                "code": $scope.codeR,
                "smssn": "7486100202"
            }
            /*
            var jt ={'uid': Number($cookieStore.get("uid")),
                "devicescode": "10223003C4001010002",
                "date": 1477564552000,
                "code": "JNV6",
                "smssn": "7486100202"
            };*/
            var json = {
                "service": "zm3c.aftersale.devicecheck",
                "channel": pc, "sv": sv, "cv": cv, "pn": pn, "st": st, "sign": sign, "sn": sn,//固定
                "reqData": orderList
            };

            //post
            $http({ method: 'POST', url: posturl, data: json, header: headers })
                .success(function (data) {
                    if (data.code == 200) {
                        $state.go('servicereqbyid', { decives: $scope.equipmentId, cdate: deciveDate });
                        $("#reqbyo").modal('hide');
                    } else if (data.code == 500) {
                        alert("系统繁忙，请稍后再试！");
                    } else if (data.code == 504) {
                        alert("未查到数据,请重新输入！");
                    } else if (data.code == 505) {
                        alert("参数不符合规范");
                    } else if (data.code == 506) {
                        alert("参数不符合规范");
                    } else if (data.code == 507) {
                        alert("验证码输入错误，请重新输入");
                    }
                })
        } else {
            $scope.submitted = true;
        }
    }

    //datapicker
    $scope.clear = function () {  //当运行clear的时候将dt置为空
        $scope.buyDate = null;
    }
    $scope.open = function ($event) {  // 创建open方法 。 下面默认行为并将opened 设为true
        $event.preventDefault();
        $event.stopPropagation();
        $scope.opened = true;
        $scope.showWeeks = false;
    }
    $scope.disabled = function (date, mode) {
        return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6))
    }
    $scope.toggleMin = function () {
        $scope.minDate = $scope.minDate ? null : new Date(); //
    }
    $scope.toggleMin();

})