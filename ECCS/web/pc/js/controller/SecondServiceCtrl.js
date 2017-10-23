/**
 * Created by dell on 2016/10/10.
 */
zhongmai.controller('SecondServiceCtrl', function ($scope, $http, $location, $state, $rootScope, localStorageService, $stateParams, $cookieStore, UrlService) {
    $scope.username = $cookieStore.get("phone");
    $scope.logistics = {};
    $scope.sdetaildata = {};
    $scope.reason = null;
    $scope.remark = null;


    $scope.productType = { v1: 0, v2: 0, v3: 0, v4: 0, v5: 0 };
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
    $scope.reader = null;
    $scope.thumb = [];
    $scope.img_upload = function (files) {
        if (files.length > 3) {
            alert("图片不能超过3张!");
            return false;
        }
        angular.forEach(files, function (data, index, array) {
            var file = files[index];
            if (!/image\/\w+/.test(file.type)) {
                return false;
            }
            if (file.size > 2 * 1024 * 1024) {
                alert("图片大小不能超过2M!");
                return false;
            }
            $scope.reader = new FileReader();
            $scope.guid = (new Date()).valueOf();
            $scope.reader.readAsDataURL(file);
            $scope.reader.onload = function (ev) {
                $scope.$apply(function () {
                    if ($scope.thumb.length >= 3) {
                        alert("图片不能超过3张!");
                        return false;
                    }
                    $scope.thumb.push({ imgSrc: ev.target.result });
                });
            };
        });
    };
    $scope.img_del = function (key) {
        $scope.thumb.splice(key, 1)
    };


    $scope.textLen = 300;
    $scope.checkText = function () {
        if ($scope.remark.length > 300) {
            alert("不能超过300字！");
            $scope.remark = $scope.remark.substr(0, 300);
        }
        $scope.textLen = 300 - $scope.remark.length;
    };

    $scope.division = UrlService.EXPRESS.address();
    $scope.addAddress = function (item) {
        var self = this;
        if (item.status == true) { item.status = 1; } else { item.status = 0; }
        if (self.addressForm.$valid) {
            var data = { "uid": Number($cookieStore.get("uid")), "name": item.name, "phone": item.phone, "zipcode": item.zipcode, "address": item.address, "province": item.provname, "city": item.cityname, "area": item.areaname, "def": item.status }
            UrlService.EXPRESS.add(data).then(function (response) {
                if (response.code == 200) {
                    $scope.logistics.name = item.name;
                    $scope.logistics.phone = item.phone;
                    $scope.logistics.provname = item.provname;
                    $scope.logistics.cityname = item.cityname;
                    $scope.logistics.areaname = item.areaname;
                    $scope.logistics.def = item.status;
                    $scope.logistics.zipcode = item.zipcode;
                    $scope.logistics.express = response.respData.eid;
                    $('#address').modal('hide');
                }
            });
        } else {
            $scope.submitted = true;
        }
    }

    $scope.submitRes = function () {
        if ($scope.remark == null) {
            alert("请输入售后服务原因");
            return false;
        }
        if ($scope.remark.length < 20) {
            alert("售后服务原因不少于20个字!");
            return false;
        }
        if (!confirm("确认要提交吗？")) {
            return false;
        }
        var imgArr = "";
        var imageboxs = [];
        for (var i = 0; i < $scope.thumb.length; i++) {
            imgArr = $scope.thumb[i].imgSrc;
            imageboxs.push(imgArr);
        }
        var servReq = {
            uid: Number($cookieStore.get("uid")),
            aftercode: $scope.sdetaildata.aftercode,
            express: $scope.logistics.express,
            producttype: $scope.producttype,
            remark: $scope.remark,
            reason: $scope.reason,
            imageboxs: imageboxs
        };
        UrlService.AFTERSALE.second(servReq).then(function (data) {
            if (data.code == 200) {
                alert("提交成功.");
                $state.go('servicelist');
            } else {
                alert("操作失败，请稍后再试.");
            }
        })
    }

    function init() {
        var afterID = $stateParams.afterid;
        if (afterID != null) {
            var sendparams = { uid: Number($cookieStore.get("uid")), aftercode: afterID };
            UrlService.AFTERSALE.info(sendparams).then(function (data) {
                if (data.code == 200) {
                    $scope.sdetaildata = data.respData;
                    $scope.producttype = JSON.parse(data.respData.producttype);
                    $scope.logistics = data.respData.logistics;
                    $scope.remark = $scope.sdetaildata.remark;
                    $scope.reason = $scope.sdetaildata.reason;
                    var imgs = data.respData.images;
                    if (imgs != null) {
                        angular.forEach(imgs, function (data, index, array) {
                            if (index == 0) {
                                $scope.pic0 = true;
                            }
                            if (index == 1) {
                                $scope.pic1 = true;
                            }
                            if (index == 2) {
                                $scope.pic2 = true;
                            }
                            $scope.thumb.push({ imgSrc: data });
                        });
                    }
                    if ($scope.sdetaildata.status == "待受理") {
                        $scope.showStutas = true;
                        $scope.showStutas2 = false;
                        $scope.showStutas3 = false;
                        $(".flowPic_on2").css({ "width": "9%" });
                    }
                    if ($scope.sdetaildata.status == "已受理") {
                        $scope.showStutas = true;
                        $scope.showStutas2 = true;
                        $scope.showStutas3 = false;
                        $(".flowPic_on2").css({ "width": "55%" });
                    }
                    if ($scope.sdetaildata.status == "已完成") {
                        $scope.showStutas = true;
                        $scope.showStutas2 = true;
                        $scope.showStutas3 = true;
                        $(".flowPic_on2").css({ "width": "100%" });
                    }
                }
            })
        }
    };
    init();
});


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



