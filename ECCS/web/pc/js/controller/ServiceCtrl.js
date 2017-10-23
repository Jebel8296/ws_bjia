/**
 * Created by dell on 2016/10/10.
 */
zhongmai.controller('ServiceCtrl', function ($scope, $http, $location, $state, $rootScope, localStorageService, $stateParams, $cookieStore, UrlService) {
    $(".selects label").on("click", function () {
        $(".radio_img").attr("src", "images/dunchecked_gr1.png")
        $(this).find(".radio_img").attr("src", "images/dchecked_gr1.png");
    });

    $(".subReq label em").on("click", function () {
        var aftertype = $(this).children(".input_radio").attr("value");
        if (aftertype == 1 && $scope.thUnused) {
            return;
        }
        if (aftertype == 2 && $scope.hhUnused) {
            return;
        } {
            $("em").css({ "border-color": "#e6e6e6", "color": "#333" });
            $(this).css({ "border-color": "#ff7917", "color": "#ff7917" });
            if ($scope.thUnused) {
                $(".type_class_1").css({ "color": "#C0C0C0" });
            }
            if ($scope.hhUnused) {
                $(".type_class_2").css({ "color": "#C0C0C0" });
            }
        }

    });
    if ($cookieStore.get("uid") == null) {
        $state.go('home');
        showLR('login');
    }
    $scope.division = UrlService.EXPRESS.address();
    $scope.isShow = true;
    $scope.isShow2 = true;
    var reqById = $stateParams.decives, cdate = $stateParams.cdate;
    $scope.dataBy = {}
    function init() {
        getDataBySearch();
    }
    init();
    function getExpById() {
        var sendparams = { 'uid': Number($cookieStore.get("uid")) };
        UrlService.EXPRESS.get(sendparams).then(function (data) {
            var expList = data.respData;
            if (expList.length != null) {
                for (var m = 0; m < expList.length; m++) {
                    $scope.addrIe = {
                        "name": expList[expList.length - 1].name,
                        "phone": expList[expList.length - 1].phone,
                        "eid": expList[expList.length - 1].eid,
                        "provname": expList[expList.length - 1].provname,
                        "zipcode": expList[expList.length - 1].zipcode,
                        "areaname": expList[expList.length - 1].areaname,
                        "cityname": expList[expList.length - 1].cityname,
                        "address": expList[expList.length - 1].address
                    }
                }
            }
        })
    }
    $scope.newServDeital = {};
    $scope.thUnused = false;
    $scope.hhUnused = false;
    $scope.afterSaleCount = 1;
    $scope.fee = 0;
    $scope.price = 0;
    function getDataBySearch() {
        var orderId = parseInt($stateParams.orderId);
        var prodCode = $stateParams.servId;
        var sendparams = { uid: Number($cookieStore.get("uid")), orderid: orderId, prodcode: prodCode };
        UrlService.ORDER.info(sendparams).then(function (data) {
            if (data.code == 200) {
                $scope.newServDeital = data.respData;
                $scope.afterSaleCount = $scope.newServDeital.prods[0].total;
                $scope.price = $scope.newServDeital.prods[0].price;
                $scope.fee = $scope.afterSaleCount * $scope.price;
                var exp = $scope.newServDeital.express;
                var otime = $scope.newServDeital.otime;
                var days = parseInt(Math.abs(new Date().getTime() - otime) / (1000 * 60 * 60 * 24));//天数
                console.log(days);
                if (days > 15) {
                    $scope.thUnused = true;
                    $scope.hhUnused = true;
                    $(".type_class_1").css({ "color": "#C0C0C0" });
                    $(".type_class_2").css({ "color": "#C0C0C0" });
                } if (days > 7) {
                    $scope.thUnused = true;
                    $(".type_class_1").css({ "color": "#C0C0C0" });
                }
                $scope.addrIe = {
                    "name": exp.name,
                    "phone": exp.phone,
                    "eid": exp.eid,
                    "provname": exp.provname,
                    "zipcode": exp.zipcode,
                    "areaname": exp.areaname,
                    "cityname": exp.cityname,
                    "address": exp.address
                }
            }
        })
    }
    $scope.minusCount = function (item) {
        if ($scope.afterSaleCount == 1) {
            alert("数量至少为1.");
            return;
        } else {
            $scope.afterSaleCount = $scope.afterSaleCount - 1;
            $scope.fee = $scope.afterSaleCount * $scope.price;
        }
    }
    $scope.addCount = function (item) {
        if ($scope.afterSaleCount == item.total) {
            alert("数量不能超过" + item.total + ".");
            return;
        } else {
            $scope.afterSaleCount = $scope.afterSaleCount + 1;
            $scope.fee = $scope.afterSaleCount * $scope.price;
        }
    }

    //获取地址
    function getExpById() {
        var sendparams = { 'uid': Number($cookieStore.get("uid")) };
        UrlService.EXPRESS.get(sendparams).then(function (data) {
            $scope.expList = data.respData;
            var exp = $scope.expList;
            var expL = $scope.expList.length;
            for (var n = 0; n < expL; n++) {
                if (exp[n].status == true) {
                    $scope.addrIe = {
                        "name": exp[n].name,
                        "phone": exp[n].phone,
                        "eid": exp[n].eid,
                        "provname": exp[n].provname,
                        "zipcode": exp[n].zipcode,
                        "areaname": exp[n].areaname,
                        "cityname": exp[n].cityname,
                        "address": exp[n].address
                    }
                } else if (!exp[n].status) {
                    $scope.addrIe = {
                        "name": exp[expL - 1].name,
                        "phone": exp[expL - 1].phone,
                        "eid": exp[expL - 1].eid,
                        "provname": exp[expL - 1].provname,
                        "zipcode": exp[expL - 1].zipcode,
                        "areaname": exp[expL - 1].areaname,
                        "cityname": exp[expL - 1].cityname,
                        "address": exp[expL - 1].address
                    }
                }
            }
        })
    }
    $scope.hideDiv = function (company) {
        switch (company) {
            case '1':
                if ($scope.thUnused) {
                    $scope.aftertype = null;
                } else {
                    $scope.isShow = false;
                    $scope.isShow2 = true;
                }
                break;
            case '2':
                if ($scope.hhUnused) {
                    $scope.aftertype = null;
                } else {
                    $scope.isShow2 = false;
                    $scope.isShow = true;
                }
                break;
            case '3':
                $scope.isShow = true;
                $scope.isShow2 = false;
                break;
        }
    }
    $scope.delImg = function (prvid) {
        $("#" + prvid).html("");
    }
    $(".choice-list li").on("mouseleave", function () {
        var findImg = $(this).find("img");
        if (findImg.length > 0) {
            $(this).find(".shut").hide();
        }
    });
    $(".choice-list li").on("mouseover", function () {
        var findImg = $(this).find("img");
        if (findImg.length > 0) {
            $(this).find(".shut").show();
        }
    });
    $scope.pic0 = false;
    $scope.pic1 = false;
    $scope.pic2 = false;
    $scope.hidenOne = function (flag) {
        if (flag == 1) {
            $scope.pic0 = true;
        } else {
            $scope.pic0 = false;
        }
    }
    $scope.hidenTwo = function (flag) {
        if (flag == 1) {
            $scope.pic1 = true;
        } else {
            $scope.pic1 = false;
        }
    }
    $scope.hidenThree = function (flag) {
        if (flag == 1) {
            $scope.pic2 = true;
        } else {
            $scope.pic2 = false;
        }
    }
    $scope.reader = new FileReader();
    $scope.thumb = [];
    $scope.thumb_default = {
        1111: {}
    };
    $scope.img_upload = function (files) {
        var file = files[0];
        if (!/image\/\w+/.test(file.type)) {
            return false;
        }
        if (file.size > 2 * 1024 * 1024) {
            alert("图片大小不能超过2M。");
            return false;
        }
        $scope.guid = (new Date()).valueOf();
        $scope.reader.readAsDataURL(files[0]);
        $scope.reader.onload = function (ev) {
            $scope.$apply(function () {
                $scope.thumb.push({ imgSrc: ev.target.result });
            });
        };
    };
    $scope.img_del = function (key) {
        $scope.thumb.splice(key, 1)
    };
    $scope.textLen = 300;
    $scope.checkText = function () {
        if ($scope.remark.length > 300) {
            alert("反馈信息不能超过300字！");
            $scope.remark = $scope.remark.substr(0, 300);
        }
        $scope.textLen = 300 - $scope.remark.length;
    };

    $scope.refundreason = 0;
    $scope.productType = { v1: 0, v2: 0, v3: 0, v4: 0, v5: 0 };
    $scope.submitRes = function () {
        if ($scope.aftertype == null) {
            alert("请选择售后类型");
            return false;
        }
        var reason = "";
        if ($scope.aftertype == 1) {
            if ($scope.refundreason == 0) {
                alert("请选择退货原因");
                return false;
            } else {
                if ($scope.refundreason == 1) {
                    reason = "七天无理由退货";
                }
                if ($scope.refundreason == 2) {
                    reason = "商品错发/漏发";
                }
                if ($scope.refundreason == 3) {
                    reason = "商品质量问题";
                }
            }
        }
        if ($scope.aftertype != 1 && $scope.productType.v1 == 0 && $scope.productType.v2 == 0 && $scope.productType.v3 == 0 && $scope.productType.v4 == 0 && $scope.productType.v5 == 0) {
            alert("请选择售后产品");
            return false;
        }
        if (!($scope.aftertype == 1 && $scope.refundreason == 1) && $scope.thumb.length < 1) {
            alert("请上传照片信息");
            return false;
        }
        if ($scope.remark == null) {
            alert("请填写售后原因");
            return false;
        }
        var aftertype = parseInt($scope.aftertype);
        if ($scope.remark.length < 20) {
            alert("售后服务原因不少于20个字!");
            return false;
        } else {
            var imgArr = "";
            var imageboxs = [];
            for (var i = 0; i < $scope.thumb.length; i++) {
                imgArr = $scope.thumb[i].imgSrc;
                imageboxs.push(imgArr);
            }
            var servReq = {
                "uid": Number($cookieStore.get("uid")),
                "type": aftertype,
                "ordercode": $scope.newServDeital.code,
                "prodcode": $scope.newServDeital.prods[0].code,
                "express": $scope.addrIe.eid,
                "producttype": $scope.productType,
                "remark": $scope.remark,
                "reason": reason,
                "imageboxs": imageboxs,
                "total": Number($scope.afterSaleCount)
            };
            UrlService.AFTERSALE.apply(servReq).then(function (data) {
                if (data.code == 200) {
                    $scope.afterid = data.respData.code;
                    $state.go("commonPt", { aftercode: $scope.afterid });
                } else if (data.code == 509) {
                    alert("已申请过售后，不能重复申请");
                } else {
                    alert("提交失败，请您稍后再提交");
                }
            })
        }
    }
    $scope.isShow = true;
    $scope.chooseType = function (test) {
        switch (test) {
            case 'tuihuo':
                $scope.isShow = false;
                break;
            case 'huanhuo':
                $scope.isShow = true;
                break;
            case 'weixiu':
                $scope.isShow = true;
                break;
        }
    };
    $scope.addrIe = {}
    function getDataById() {
        var orderList = {
            'uid': Number($cookieStore.get("uid")),
            "devicescode": reqById,
            "date": cdate
        }
        UrlService.AFTERSALE.device(orderList).then(function (data) {
            if (data.code == 200) {
                $scope.dataBy = data.respData.respData;
                console.log($scope.dataBy);
                var exp = $scope.dataBy.express;
                var expL = $scope.dataBy.express.length
                for (var n = 0; n < expL; n++) {
                    if (exp[n].status == 1) {
                        $scope.addrIe = {
                            "name": exp[n].name,
                            "phone": exp[n].phone,
                            "eid": exp[n].eid,
                            "provname": exp[n].provname,
                            "zipcode": exp[n].zipcode,
                            "areaname": exp[n].areaname,
                            "cityname": exp[n].cityname,
                            "address": exp[n].address
                        }
                    } else {
                        $scope.addrIe = {
                            "name": exp[expL - 1].name,
                            "phone": exp[expL - 1].phone,
                            "eid": exp[expL - 1].eid,
                            "provname": exp[expL - 1].provname,
                            "zipcode": exp[expL - 1].zipcode,
                            "areaname": exp[expL - 1].areaname,
                            "cityname": exp[expL - 1].cityname,
                            "address": exp[expL - 1].address
                        }
                    }
                }
            }
        })
    }
    $scope.addAddress = function (item) {
        var self = this;
        if (item.status == true) { item.status = 1; } else { item.status = 0; }
        if (self.addressForm.$valid) {
            var proquery = { "uid": Number($cookieStore.get("uid")), "name": item.name, "phone": item.phone, "zipcode": item.zipcode, "address": item.address, "province": item.provname, "city": item.cityname, "area": item.areaname, "def": item.status }
            UrlService.EXPRESS.add(proquery).then(function (data) {
                if (data.code == 200) {
                    $('#address').modal('hide');
                    getExpById();
                } else if (data.code == 500) {
                    alert(data.msg);
                }
            });
        } else {
            $scope.submitted = true;
        }
    }
    $scope.submitResById = function () {
        if ($scope.aftertype == null) {
            alert("请选择售后类型");
            return false;
        }
        var productType = JSON.stringify($scope.productType);
        var aftertype = parseInt($scope.aftertype);
        var reason = "七天无理由退货";
        if ($scope.refundReason == 2) {
            reason = "商品错发/漏发";
        }
        if ($scope.refundReason == 3) {
            reason = "商品质量问题";
        }
        if ($scope.remark == null) {
            alert("请输入售后服务原因");
            return false;
        } else {
            var imgArr = "";
            var imageboxs = [];
            for (var i = 0; i < $scope.thumb.length; i++) {
                imgArr = $scope.thumb[i].imgSrc;
                imageboxs.push(imgArr);
            }
            var servReq = {
                "uid": Number($cookieStore.get("uid")),
                "type": aftertype,
                "ordercode": "0000",
                "prodcode": $scope.dataBy.code,
                "devicescode": reqById,
                "signtime": Number(cdate),
                "express": $scope.addrIe.eid,
                "producttype": productType,
                "reason": reason,
                "remark": $scope.remark,
                "imageboxs": imageboxs
            };
            UrlService.AFTERSALE.apply(servReq).then(function (data) {
                if (data.code == 200) {
                    $scope.afterid = data.respData.code;
                    $state.go("commonPt", { aftercode: $scope.afterid });
                } else if (data.code == 500 || data.code == 505) {
                    alert("提交失败，请您稍后再提交");
                }
            })
        }
    }
});



