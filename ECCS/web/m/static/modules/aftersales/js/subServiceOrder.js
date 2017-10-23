define(function (require, exports, module) {
    var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
    require('LIBS/sui/js/sm.min');
    require('LIBS/sui/js/sm-extend.min');
    require('public/js/ajaxService');
    require('purchase/js/addrchoose');

    $.init();

    //选择型号

    function toDecimal2(x) {
        var f = parseFloat(x);
        if (isNaN(f)) {
            return false;
        }
        var f = Math.round(x*100)/100;
        var s = f.toString();
        var rs = s.indexOf('.');
        if (rs < 0) {
            rs = s.length;
            s += '.';
        }
        while (s.length <= rs + 2) {
            s += '0';
        }
        return s;
    }
    var name, phone, address, zipcode, cityname, addr;
    // 获取产品id
    var url = decodeURIComponent(window.location.search); //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }

    var uid = parseInt(theRequest.uid); //用户id
    var orderid = parseInt(theRequest.orderid); //数据库订单id
    var prodcode = theRequest.prodcode; //设备编号
    /*TODO 产品型号*/
    var prodModal = theRequest.prodmodal;
    /*TODO 产品颜色*/
    var prodColor = theRequest.prodcolor;
    /*TODO 产品订单*/
    var pordCourier = parseInt(theRequest.Courier);
    /*TODO 页面类型*/
    var pagestype = parseInt(theRequest.page_type);
    //TODo +-框数量
    var proNum=parseInt(theRequest.proctNum);

    var ordercode;
    var receiptHtml;
    var expobj = {};


    $('.sel button').on('click', function () {
        $(this).addClass("cur").siblings("button").removeClass();
        var cargo_type = $(this).text();
        if (cargo_type == '退 货') {
            $("#show_select").css("display", "block");
            $("#receiptAddress").css("display", "none");
            $(".refund_box").css("display", "block");
            $(".tit").css('display',"none");
            $(".orderMsg_radio").css("display",'none');

        } else if (cargo_type == '换 货') {
            $("#show_select").css("display", "none");
            $("#receiptAddress").css("display", "block");
            $(".refund_box").css("display", "none");
            $(".tit").css('display',"block");
            $(".orderMsg_radio").css("display",'block');

        } else {
            $("#show_select").css("display", "none");
            $("#receiptAddress").css("display", "block");
            $(".refund_box").css("display", "none");
            $(".tit").css('display',"block");
            $(".orderMsg_radio").css("display",'block');

        }
    });
    //console.log("uid:"+uid);
    //console.log("orderid:"+orderid);
    //console.log("prodcode:"+prodcode);
    //console.log("prodModal:"+prodModal);
    //console.log("prodColor:"+prodColor);
    //console.log("pordCourier:"+pordCourier);
    $.ajax({
        type: "post",
        url: postUrl,
        contentType: "application/json",
        dataType: "JSON",
        data: JSON.stringify({
            "service": "zm3c.order.info",
            "channel": "pc",
            "sv": "1.2.300",
            "cv": "1.4.10",
            "st": "MD5",
            "sign": $.fn.cookie('token'),
            "sn": dateNum,
            "reqData": {
                "uid": Number($.fn.cookie('uid')),
                "orderid": orderid,
                "prodcode": prodcode
            },
            "ts": 149203841243
        }),
        success: function (msg) {
            var m = (new Function("", "return " + msg))();
            console.log(m);

            if (m.code == 200) {
                var proListNum = m.respData.prods;
                var orderTime = m.respData.otime;
                if (orderTime) {
                    var orderTime_7 = orderTime + 24 * 7 * 60 * 60 * 1000;
                    var orderTime_15 = orderTime + 24 * 15 * 60 * 60 * 1000;
                    console.log("订单完成时间" + orderTime);
                    console.log("7天后" + orderTime_7);
                    console.log("15天后" + orderTime_15);
                    var currentTime = new Date().getTime();
                    console.log("当前时间是" + currentTime)
                    if (currentTime > orderTime_7) {
                        console.log("不能点击退货了")
                        $(".sel button").eq(0).attr("disabled", true).css('background', '#ddd');
                    } else {
                        console.log("可以点击退货")
                        $(".sel button").eq(0).removeAttr("disabled").css('background', '#fff');
                    }
                    if (currentTime > orderTime_15) {
                        console.log("不能点击换货了")
                        $(".sel button").eq(1).attr("disabled", true).css('background', '#ddd');
                    } else {
                        console.log("可以点击换货了")
                        $(".sel button").eq(1).removeAttr("disabled").css('background', '#fff');
                    }
                } else {
                    $(".sel button").eq(2).attr("disabled", true).css('background', '#ddd');
                }

                var proList;
                var ProPrice;
                for (i = 0; i < proListNum.length; i++) {
                    if(pagestype) {
                         ProPrice=toDecimal2(proListNum[i].price)
                        proList = '<li>\
                        <div class="item-content">\
                                <div class="item-media">\
                                    <img src="/modules/public/img/3/3C40A100010001.png">\
                                </div>\
                                <div class="item-inner">\
                                    <div class="item-title-row">\
                                            <div style="display: block" class="item-title">' + proListNum[i].name + '</div>\
                                            \
                                    </div>\
                                    <div style="display: block" class="item-title">型号：' + prodModal + '<span>颜色：' + prodColor + '</span><span></div>\
                                   \
                                    <div class="item-subtitle">关联订单号：' + pordCourier + '</div>\
                                    <div  class="item-subtitle1"><em class="addNum">+</em><span class="Pro_num1">'+proNum+'</span><em class="subtractNum">-</em></div>\
                                </div>\
 \
 \
                         </div>\
                     <div class="refund_box" style="font-size: 15px;display: none">退款金额\
                     <span style="font-size: 22px" class="color0 prePrice"><span class="_proPprice"> ' + ProPrice + '</span>元</span>\
                     <span  class="refund" style="text-align: right"><a href="#">退款说明</a></span>\
                     </div>\
                         </li>';

                    }else{
                        proList = '<li>\
                                <div class="item-content">\
                                <div class="item-media">\
                                    <img src="/modules/public/img/3/3C40A100010001.png">\
                                </div>\
                                <div class="item-inner">\
                                    <div class="item-title-row">\
                                            <div style="display: block" class="item-title">' + proListNum[i].name + '</div>\
                                            \
                                 </div>\
                                    <div class="item-subtitle">签收日期：2016-09-08</div>\
                                    <div class="item-subtitle">设备编号：' + proListNum[i].code + '</div>\
                                </div>\
                         </div>\
                        </div>\
                         </li>';
                    }

                    $(".bigImgList ul").append(proList);
                }

                ordercode = m.respData.code;
                name = m.respData.express.name;
                phone = m.respData.express.phone;
                addr = m.respData.express.provname + " " + m.respData.express.cityname + " " + m.respData.express.areaname;
                expobj = m.respData.express;
                console.log("*********")
                console.log(name);
                console.log(phone);
                console.log(addr);
                console.log(expobj);
                console.log("*********")

                receiptHtml = '<dd class="exid" data-eid="' + expobj.eid + '">收货人：' + expobj.name + '&nbsp;&nbsp;<br/>联系电话：' + expobj.phone + '<br/>地   址：' + expobj.provname + expobj.areaname + expobj.cityname + expobj.address + '</dd>';
                $(".addItemjs").append(receiptHtml);
                localStorage.removeItem('eid');

                if (eid !== "") {
                    getExpress();

                }

                for (j = 0; j < $(".sel a").length; j++) {
                    var status = $(".sel a").eq(j).text().replace(/\ +/g, "");
                    var defaultStatus = m.respData.aftertype;
                    if (status == defaultStatus) {
                        $(".sel a").eq(j).addClass("cur");

                    }
                }

            } else if (m.code == 506) {
                $.toast("请重新登录");
                setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
            } else {
                // $.toast(m.msg);
            }
        },
        error: function () {
            $.alert("系统繁忙请稍后再试");
        }

    });


    $('.addrEid').on('click', function () {
        var eid = $(this).find(".exid").data("eid");
        var goEditA = "/modules/purchase/html/addrchoose.html";
        setTimeout('window.location.href ="' + goEditA + '"', 1000);
    });

    var eid = localStorage.getItem('eid');
    console.log(eid);
//获取地址信息
    function getExpress() {
        $.ajax({
            type: "post",
            url: postUrl,
            contentType: "application/json",
            dataType: "JSON",
            data: JSON.stringify({
                "service": "zm3c.express.list",
                "channel": "m",
                "sv": "1.2.300",
                "cv": "1.4.10",
                "pn": "xxx",
                "st": "MD5",
                "sign": $.fn.cookie('token'),
                "sn": dateNum,
                "reqData": {
                    "uid": Number($.fn.cookie('uid'))
                }
            }),
            success: function (m) {
                var m = JSON.parse(m);
                if (m.code == 200) {
                    var eData = m.respData;
                    for (var i = 0; i < eData.length; i++) {
                        if (eData[i].eid == eid) {
                            var newHtml = '<dd class="exid" data-eid="' + expobj.eid + '">收货人：' + expobj.name + '&nbsp;&nbsp;<br/>联系电话：' + expobj.phone + '<br/>地   址：' + expobj.provname + expobj.areaname + expobj.cityname + expobj.address + '</dd>';
                            $(".addItemjs").html("")
                            $(".addItemjs").append(newHtml);
                        }
                        if (eid != "") {
                            expobj = eData[i];
                            if (eData[i].eid == eid) {
                                var newHtml = '<dd class="exid" data-eid="' + expobj.eid + '">收货人：' + expobj.name + '&nbsp;&nbsp;<br/>联系电话：' + expobj.phone + '<br/>地   址：' + expobj.provname + expobj.areaname + expobj.cityname + expobj.address + '</dd>';
                                $(".addItemjs").html("")
                                $(".addItemjs").append(newHtml);
                            }
                        } else {
                            $(".addItemjs").append(receiptHtml);
                        }

                    }
                } else if (m.code == 506) {
                    $.toast("请重新登录");
                    setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
                } else {
                    // $.toast(m.msg);
                }
            },
            error: function () {
                $.alert("系统繁忙请稍后再试");
            }

        });
    }


    $(document).on('click', '.orderMsg_radio', function () {
        //$(".orderMsg_radio li").removeClass();
        $(this).addClass("selAfter");
        //  producttype = $(this).find("label").text();
        // console.log(producttype);
        //console.log($(this).val());

    });
    //订单+-
    $(document).on('click', '.addNum', function () {
        //获取商品数量
        var product_num = $('.Pro_num1').text();
        if (product_num >= 1&&product_num<proNum) {
            $('.Pro_num1').text(parseInt(product_num) + 1);
            var price = parseFloat($('._proPprice').text());
            var product_price = price / product_num;
            price += product_price;
            price = price.toFixed(2);
            $('._proPprice').text(price)
        }
    })
    $(document).on('click', '.subtractNum', function () {

        var product_num = $('.Pro_num1').text();
        if (product_num > 1) {
            $('.Pro_num1').text(parseInt(product_num) - 1);
            //var presentNum= $('.Pro_num1').text();

            var price = parseFloat($('._proPprice').text());
            var price = parseFloat($('._proPprice').text());
            var product_price = price / product_num;
            price -= product_price;
            price = price.toFixed(2);
            $('._proPprice').text(price)
            $('._proPprice').text(price)
        }
    })
    /*TODO 多行输入框*/
    var remark;
    $(document).on('keyup', '.txtarea', function () {


        remark = $(".txtarea").val();
        var hasNum;
        var allNum = $(".allNum").text();
        for (hasNum = 0; hasNum < allNum; hasNum++) {
            hasNum = $(".hasNum").text(remark.length);
        }

        if (remark.length > 300) {
            $.toast("不能多于300个字符");
            hasNum = $(".hasNum").text(allNum);
            $(this).val($(this).val().substring(0, 300));
        }
    });
    $(document).on('blur', '.txtarea', function () {
        remark = $(".txtarea").val();
        if (remark.length < 20) {
            $.toast("不能小于20个字符");
            return;
        }
    });

    // $(document).on('click','#selectwhy',function(){
    //     console.log("111")
    // })
    $("#selectwhy").change(function () {
        var selected = $("#selectwhy").val();
        console.log(selected);
        if (selected == "请选择取消原因") {
            alert("您还没选择取消原因");
            return;
        }

        if (selected == "七天无理由退换") {
            $('.uploadImg').css('display', 'none');
        } else {
            $('.uploadImg').css('display', 'block');
        }
        localStorage.setItem("select_why", selected);
    });
    /*TODO 选择原因*/
    var check_val = [];

    function fun() {
        obj = document.getElementsByName("cart-pro");
        check_val = [];
        for (k in obj) {
            if (obj[k].checked)
                check_val.push(obj[k].value);
        }
        return check_val;
    }

    // 提交服务单
    var producttype_obj = {
        v1: 0,
        v2: 0,
        v3: 0,
        v4: 0
    };
    $(document).on('click', '#subOrderBtn', function () {
        var typeNum = $(".sel").find(".cur");//TODO 售后类型
        if (typeNum.length == 0) {
            $.toast("请选择售后类型");
            return;
        }
        var selType = $(".sel").find(".cur").data("type");
        remark = $(".txtarea").val();

        if(selType=="1"){
            if($('#selectwhy').val()=="请选择退货原因"){
                $.toast("请选择退货原因");
                return;
            };
        }

        producttype_id = fun();
        console.log(producttype_id);
        if (producttype_id.indexOf("1") != -1) {
            producttype_obj.v1 = 1
        }
        if (producttype_id.indexOf("2") != -1) {
            producttype_obj.v2 = 1
        }
        if (producttype_id.indexOf("3") != -1) {
            producttype_obj.v3 = 1
        }
        if (producttype_id.indexOf("4") != -1) {
            producttype_obj.v4 = 1
        }
        //var producttype_json=JSON.stringify(producttype_obj);



        console.log(selType);
        var selAfterPro = $(".selAfter").find("label").text();//TODO 售后产品

        var jsarea = $(".jsarea").val();//TODO 退货原因
        var select_why = localStorage.getItem("select_why");// TODO 退货原因
        if(selType==1){
            if (select_why == "请选择退货原因") {
                $.toast("请选择退货类型");
                return;
            }
        }
        if (typeNum.length == 0) {
            $.toast("请选择售后类型");
            return;
        }
        if(selType==2||selType==3) {
            if (selAfterPro == null) {
                $.toast("请选择售后产品");
                return;
            }
        }
        if (remark.length < 20) {
            $.toast("不能小于20个字符");
            return;
        }
        if (jsarea.length == 0) {
            $.toast("请填写售后原因");
            return;
        } else {
            var selType = $(".sel").find(".cur").data("type");// TODO 售后类型
            var exid = $(".exid").data("eid");//TODO 收货类型
            var imgNum = $('#up_pic li').length; //TODO 图片长度
            var imageboxs = [];

            for (i = 0; i < imgNum; i++) {
                var imgsrc = $('#up_pic li').eq(i).find("img").attr("src");
                imageboxs.push(imgsrc);

            }
            imageboxs.pop();
            if(select_why!=="七天无理由退换"&&imageboxs.length<1){
                $.toast("请上传图片");
                return;
            }

            $.ajax({
                type: "post",
                url: postUrl,
                contentType: "application/json",
                dataType: "JSON",
                data: JSON.stringify({
                    "service": "zm3c.aftersale.apply",
                    "channel": "m",
                    "sv": "1.2.300",
                    "cv": "1.4.10",
                    "pn": "xxx",
                    "st": "MD5",
                    "sign": $.fn.cookie('token'),
                    "sn": dateNum,
                    "reqData": {
                        "uid": Number($.fn.cookie('uid')),//OK
                        "type": selType,//OK
                        "ordercode": ordercode,//OK
                        "prodcode": prodcode,//OK
                        "express": exid,//OK
                        "producttype": producttype_obj,//ok
                        "reason": select_why,//ok
                        "remark": remark,//ok
                        //"remark":jsarea,//ok
                        "imageboxs": imageboxs//ok
                    }

                }),
                success: function (msg) {

                    var m = (new Function("", "return " + msg))();
                    if (m.code == 200) {

                        var code = m.respData.code;
                        $.toast("您的售后服务单已提交成功");
                        setTimeout(window.location.href = '/modules/aftersales/html/afterSales_list.html', 3000);
                    } else if (m.code == 506) {
                        $.toast("请重新登录");
                        setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
                    } else {
                        $.toast(m.msg);
                    }
                },
                error: function () {
                    $.alert("系统繁忙请稍后再试");
                }
            });
        }


    });


    // 图片上传
    (function () {
        $('#up_pic li').on('mouseover', function (e) {
            $(this).find(".removeLi").show();
        })
        $('.up_pic li').on('mouseout', function (e) {
            $(this).find(".removeLi").hide();
        })
        function preview(file, that) {
            var reader = new FileReader()
            reader.onload = function (e) {
                var _fileType = that.val().substr(-4).toLowerCase()
                if (_fileType === '.jpg' || _fileType === 'jpeg' || _fileType === '.png' || _fileType === '.gif') {

                    if ($('#up_pic li').length > 8) {
                        $('#up_li').hide();
                    }
                    var $img = $('<img>').attr("src", e.target.result);
                    var $li = $('<li />');
                    $li.append($img).append('<span class="removeLi">×</span>');
                    that.parent().before($li);
                    //that.parent().append($img).find('span').hide();
                    that.after(that.clone());
                    that.remove();
                    /*
                     setTimeout(function(){
                     $li.find('.up_ing').html('上传失败');
                     },5000)*/
                    return;
                }
                $.alert('图片格式不对，<br>请选择jpg、png、gif格式图片');
                if (file.size > 2 * 1024 * 1024) {
                    $.alert('图片大小不能超过2M。');
                    return false;
                }
            }
            reader.readAsDataURL(file);
        }

        $('#up_pic').on('change', '[type=file]', function (e) {
            var file = e.target.files[0]
            preview(file, $(this));
        }).on('click', '.removeLi', function () {
            $(this).parent().remove();
            $('#up_li').css({'display': 'inline-block'});
        });


        //发布提交事件start
        $('#submit').on('touchend', function () {
            if (!$.trim($('#title').val())) {
                $.alert('请填写标题！');
                return;
            } else if (!$.trim($('#describe').val())) {
                $.alert('请填写描述！');
                return;
            } else if (!$.trim($('#price').val()) || !(+$('#price').val() > 0)) {
                $.alert('请正确填写价格！');
                return;
            } else if (!$.trim($('#priceCost').val()) || !(+$('#priceCost').val() > 0)) {
                $.alert('请正确填写原价！');
                return;
            }//以上为必填及填写内容验证
            var imgArr = [];
            var $imgFile = $('#up_pic img');
            for (var i = 0; i < $imgFile.length; i++) {
                imgArr.push($imgFile.attr('src'));
            }
            ;
            console.log(imgArr)
            $.ajax({
                url: 'zzz.json',//接口地址
                type: 'post',
                cache: false,
                beforeSend: function () {
                    $('#submit').html('发布中，请稍后……');
                    $('body').append('<div class="modal-overlay modal-overlay-visible" id="send_ing"></div>');
                },
                data: {
                    title: $('#title').val(),
                    describe: $('#describe').val(),
                    img: imgArr,//imgArr数据类型为数组，数组项为图片base64串
                    price: $('#price').val(),
                    priceCost: $('#priceCost').val(),
                    words: $('#words').val()
                },
                success: function (msg) {

                    // 情况1：请求完全成功——返回数据格式为：
                    //     {
                    //       code:0,
                    //       message:"请求完全成功"
                    //     }
                    // 情况2：请求不完全成功——返回数据格式为demo：
                    //     {
                    //       code:3005,
                    //       message:"您未登录，请重新登录！"
                    //     }
                    // tips：该处逻辑为：code==0，则弹"发布成功"，并刷新页面；否则直接弹返回数据中message值


                    $('#send_ing').remove();
                    $('#submit').html('确定发布');
                    if (msg.code != 0) {
                        $.alert(msg.message);
                        return;
                    }
                    $('#send_ing').remove();
                    $('#submit').html('确定发布');
                    $.alert('发布成功', function () {
                        window.location.reload()
                    });
                },
                error: function () {
                    $('#send_ing').remove();
                    $('#submit').html('确定发布');
                    $.alert('网络错误，请稍后重试');
                }
            });
        });
        //发布提交事件end
    })();

});