/**
 * Created by ZMTX on 2017/6/5.
 */
define(function(require, exports, module) {
    var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//���Ƶ��������
    require('LIBS/sui/js/sm.min');
    require('LIBS/sui/js/sm-extend.min');
    require('public/js/ajaxService');

    $.init();

    var code_obj=JSON.parse(localStorage.getItem('submit_Again'));

    console.log(code_obj.ordercode);
    //TODO
    function getTise(time) {
        if(time==null){
            return ""
        }
        var oDate = new Date(time);
        var year = oDate.getFullYear();
        var month = oDate.getMonth() + 1;
        var day = oDate.getDate();
        var hour = oDate.getHours();
        var Minutes = oDate.getMinutes();
        Minutes<10?Minutes="0"+Minutes:Minutes;
        var seconds=oDate.getSeconds();
        seconds<10?seconds="0"+seconds:seconds;
        return year+"-"+month+"-"+day+ "  "+hour+ ':' + Minutes+":"+seconds;
    }
    $.ajax({
        type:"post",
        url:postUrl,
        contentType:"application/json",
        dataType:"JSON",
        data: JSON.stringify({
            "service": "zm3c.aftersale.info",
            "channel": "m",
            "sv": "1.2.300",
            "cv": "1.4.10",
            "pn": "xxx",
            "st": "MD5",
            "sign": $.fn.cookie('token'),
            "sn": dateNum,
            "reqData": {
                "uid":Number( $.fn.cookie('uid')),
                "aftercode":code_obj.ordercode
            }

    }),success:function(msg){
            var m =(new Function("","return "+msg))();
            if(m.code=200){
                console.log(m);
                localStorage.setItem('afterType',JSON.stringify( m.respData.aftertype))
                console.log(localStorage.getItem('afterType'));
                localStorage.setItem('prodCode',JSON.stringify( m.respData.prodcode))
                console.log(localStorage.getItem('prodCode'));
                var ordercode=m.respData.prodcode;//TODO 获取设备code
                var ordermodel=ordercode.slice(4,8)//TODO 获取设备型号
                var ordercolor=ordercode.slice(8,10)//TODO 获取设备颜色
                var order_addr_info= m.respData.logistics;//TODO 获取基本的地址信息
                var orderType=JSON.parse(m.respData.producttype);//TODO 产品选择的类型
                var img_group= m.respData.images;//TODO 图片组信息
                //
                //TODO 判断设备颜色
                if(ordercolor=="01"){
                    ordercolor="青色"
                }
                if(ordercolor=="02"){
                    ordercolor="黑色"
                }
                // TODO 设置退货原因
                var selelctD=$('#selectwhy option');
                var selelctDoption=Array.prototype.slice.call(selelctD);
                console.log(selelctDoption.length);
                for(var i=0;i<selelctDoption.length;i++){
                    if(selelctDoption[i].innerHTML== m.respData.reason){
                        selelctDoption[i].selected = true;
                        break;
                    }
                }
                //TODO 判断售后类型 如果是退货就隐藏收货地址
                if(m.respData.aftertype==1){
                    $('#receiptAddress').css("display","none");
                   $('#show_select').css("display","block");


                }
                //TODO 判断售后产品的选择情况

                if(orderType.v1==1){
                        $('.regular-checked1').attr('checked', true);
                }
                if(orderType.v2==1){
                        $('.regular-checked2').attr('checked', true);
                }
                if(orderType.v3==1){
                        $('.regular-checked3').attr('checked', true);
                }
                if(orderType.v4==1){
                        $('.regular-checked4').attr('checked', true);
                }
                //TODO 设置多行文本输入框的值
                $(".jsarea").val(m.respData.remark);
                $(".hasNum").text(m.respData.remark.length);
                var orderMsg='<li>\
                        <div class="item-content">\
                                <div class="item-media">\
                                    <img src="/modules/public/img/3/3C40A100010001.png">\
                                </div>\
                                <div class="item-inner">\
                                    <div class="item-title-row">\
                                            <div style="display: block" class="item-title">'+ m.respData.prodname+'</div>\
                                    </div>\
                                    <div style="display: block" class="item-title">型号：'+ ordermodel +'<span style="margin-left: 1rem">颜色：'+ordercolor+'</span><span></div>\
                                    <div class="item-subtitle">签收日期：2017-09-08</div>\
                                    <div class="item-subtitle">设备编号：'+ m.respData.prodcode +'</div>\
                                </div>\
                         </div>\
                         </li>';
                $(".bigImgList ul").append(orderMsg);
                //TODO 售后反馈
                console.log(m.respData.replyResult);
                if(m.respData.replyResult) {
                    for (var i = 0; i < m.respData.replyResult.length; i++) {
                        var createTime=getTise(m.respData.replyResult[i].createTime);
                        var  AfterAll='<p><span>'+createTime+'</span></br><span>客服反馈：'+m.respData.replyResult[i].reply+'</span></p>'
                    }
                    $(".Anticounter").prepend(AfterAll);
                }
                //TODO 图片信息
                for(var i= 0;i<img_group.length;i++) {

                    var imgINFO = '<li>' +
                                    '<img src="'+img_group[i]+'" alt=""/>' +
                                    '<span class="removeLi">x</span>' +
                                  '</li>'
                    $("#up_pic").prepend(imgINFO);
                }
                //TODO 判断快递信息的值
                console.log(order_addr_info);
                if(order_addr_info.logisticsCode!==null&&order_addr_info.logisticsName!==null&&m.respData.status==1){
                    var Logistics_information=' <dt>退换/维修客户邮寄信息</dt>\
                                            <dd>物流公司：<input placeholder="请填写快递公司名称" class="logistics_name" type="text" value="'+order_addr_info.logisticsName+'"/></dd>\
                                            <dd>货运单号：<input placeholder="请填写快递公司单号" class="logistics_code" type="text" value="'+order_addr_info.logisticsCode+'"/></dd>'
                    $('.Logistics_info').append(Logistics_information);
                }

                //TODO 快递信息
                //'+order_addr_info.logisticsName+'\
                //'+order_addr_info.logisticsCode+'


                //TODO 地址信息
                expobj = m.respData.logistics;
                var orderINFO = '<dd class="exid" data-eid="'+order_addr_info.express +'">' +
                    '收货人：'+ order_addr_info.name + '&nbsp;&nbsp;<br/>' +
                    '联系电话：'+ order_addr_info.phone +'<br/>' +
                    '地   址：'+ order_addr_info.provname + order_addr_info.areaname + order_addr_info.cityname +order_addr_info.address +'' +
                    '</dd>';
                $(".addItemjs").append(orderINFO);
                localStorage.setItem('express_eid',order_addr_info.express);
                if(eid!==""){
                    getExpress();

                }
                for(j=0;j<$(".sel a").length;j++){0
                    var status = $(".sel a").eq(j).text().replace(/\ +/g,"");
                    var defaultStatus = m.respData.aftertype;
                    if(status == defaultStatus){
                        $(".sel a").eq(j).addClass("cur");
                    }
                }
            }else if(m.code==506){
                $.toast("请重新登录");
                setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
            }else{
                $.toast(m.msg);
            }

        },
        error:function(){
            $.alert("服务器内部错误");
        }
    })
    //TODO
    $('.addrEid').on('click', function () {
        var eid = $(this).find(".exid").data("eid");
        console.log(eid);
        var goEditA = "/modules/purchase/html/addrchoose.html";
        setTimeout('window.location.href ="'+goEditA+'"', 1000);
    });
    var eid =localStorage.getItem('eid');
    console.log(eid);

    //TODO 获取地址信息
    function getExpress(){
        $.ajax({
            type:"post",
            url:postUrl,
            contentType:"application/json",
            dataType:"JSON",
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
                    "uid": Number( $.fn.cookie('uid'))
                }
            }),
            success:function(m){
                var m = JSON.parse( m );
                if(m.code == 200){
                    var eData =  m.respData;
                    for(var i=0; i<eData.length;i++){
                        if(eData[i].eid==eid){
                            var newHtml = '<dd class="exid" data-eid="'+expobj.express +'">收货人：'+ expobj.name + '&nbsp;&nbsp;<br/>联系电话：'+ expobj.phone +'<br/>地   址：'+  expobj.provname + expobj.areaname +expobj.cityname +expobj.address +'</dd>';
                            $(".addItemjs").html("")
                            $(".addItemjs").append(newHtml);
                        }
                        if(eid!=""){
                            expobj = eData[i];
                            if(eData[i].eid==eid){
                                var newHtml = '<dd class="exid" data-eid="'+expobj.eid +'">收货人：'+ expobj.name + '&nbsp;&nbsp;<br/>联系电话：'+ expobj.phone +'<br/>地   址：'+  expobj.provname + expobj.areaname +expobj.cityname +expobj.address +'</dd>';
                                $(".addItemjs").html("")
                                $(".addItemjs").append(newHtml);
                            }
                        }else{
                            $(".addItemjs").append(receiptHtml);
                        }

                    }
                }else if(m.code == 506){
                    $.toast("请重新登录");
                    setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
                }else{
                    // $.toast(m.msg);
                }
            },
            error:function(){
                $.alert("系统繁忙请稍后再试");
            }

        });
    }
    //TODO 图片上传
    (function(){
        $('#up_pic li').on('mouseover',function(e){
            $(this).find(".removeLi").show();
        })
        $('.up_pic li').on('mouseout',function(e){
            $(this).find(".removeLi").hide();
        })
        function preview(file,that) {
            var reader = new FileReader()
            reader.onload = function(e) {
                console.log(e);
                var _fileType = that.val().substr(-4).toLowerCase()
                if (_fileType === '.jpg' || _fileType === 'jpeg' || _fileType === '.png' || _fileType === '.gif') {

                    if( $('#up_pic li').length > 8 ){
                        $('#up_li').hide();
                    }
                    var $img = $('<img>').attr("src", e.target.result);
                    var $li = $('<li />');
                    $li.append( $img ).append( '<span class="removeLi">×</span>' );
                    that.parent().before( $li );
                    //that.parent().append($img).find('span').hide();
                    that.after(that.clone());
                    that.remove();/*
                     setTimeout(function(){
                     $li.find('.up_ing').html('上传失败');
                     },5000)*/
                    return;
                }
                $.alert( '图片格式不对，<br>请选择jpg、png、gif格式图片' );
                if(file.size > 2*1024*1024){
                    $.alert( '图片大小不能超过2M。' );
                    return false;
                }
            }
            reader.readAsDataURL(file);
        }
        $('#up_pic').on('change', '[type=file]', function(e) {
            var file = e.target.files[0]
            preview(file, $(this));
        }).on('click', '.removeLi', function(){
            $(this).parent().remove();
            //$('#up_li').css({'display': 'inline-block'});
        });

    })();

 /****************************************************************************************/
 /**************************************提交BTN*******************************************/
    /************************************************************************************/
    var remark;
    $(document).on('keyup','.txtarea', function () {

        remark = $(".txtarea").val();
        var hasNum;
        var allNum = $(".allNum").text();
        for(hasNum=0; hasNum<allNum;hasNum++){
            hasNum = $(".hasNum").text(remark.length);
        }

        if(remark.length>300){
            $.toast("不能多于300个字符");
            hasNum = $(".hasNum").text(allNum);
            $(this).val($(this).val().substring(0,300));
        }
    });
    $(document).on('blur','.txtarea', function () {
        remark = $(".txtarea").val();
        if(remark.length<20){
            $.toast("不能小于20个字符");
        }
    });

    $("#selectwhy").change(function(){
        var selected=$("#selectwhy").val();
        if(selected=="请选择取消原因"){
            alert("您还没选择取消原因");
            return;
        }
        localStorage.setItem("select_again",selected);
    });
    /*TODO 选择原因*/
    var check_val = [];
    function fun(){
        obj = document.getElementsByName("cart-pro");
        check_val = [];
        for(k in obj){
            if(obj[k].checked)
                check_val.push(obj[k].value);
        }
        return check_val;
    }
    // 提交服务单
    var producttype_obj={
        v1:0,
        v2:0,
        v3:0,
        v4:0
    };
    $(document).on('click','#subOrderBtn', function () {
        //TODO textarea字数少于20个字的提醒

        producttype_id=fun();
        console.log(producttype_id);
        if(producttype_id.indexOf("1")!=-1){
            producttype_obj.v1=1
        }
        if(producttype_id.indexOf("2")!=-1){
            producttype_obj.v2=1
        }
        if(producttype_id.indexOf("3")!=-1){
            producttype_obj.v3=1
        }
        if(producttype_id.indexOf("4")!=-1){
            producttype_obj.v4=1
        }
        if(producttype_obj.v1==0&&producttype_obj.v2==0&&producttype_obj.v3==0&&producttype_obj.v4==0){
            $.toast("请选择售后产品")
            return;
        }
        var jsarea = $(".jsarea").val();//TODO 退货原因描述

        var select_why=localStorage.getItem("select_again");// TODO 退货原因
              if($('.logistics_name').val()==""){
            $.toast("请填写快递公司名称");
            return;
        }
        if($('.logistics_name').val()==""){
            $.toast("请填写快递公司单号");
            return;
        }

        if(jsarea.length==0){
            $.toast("请填写售后原因");
            return;
        }else {
            //$(".sel").find(".cur").data("type");// TODO 售后类型
            var selType =JSON.parse(localStorage.getItem('afterType'));
            //var prod_code=JSON.parse(localStorage.getItem('prodCode'));
            var logisticsName=$('.logistics_name').val();
            var logisticsCode=$('.logistics_code').val();
            var exid =  $(".exid").data("eid");//TODO 收货类型
            var imgNum  =$('#up_pic li').length; //TODO 图片长度
            var imageboxs =[];
            for(i=0;i<imgNum;i++){
                var imgsrc = $('#up_pic li').eq(i).find("img").attr("src");
                imageboxs.push(imgsrc);
            }
            imageboxs.pop();

            $.ajax({
                type:"post",
                url:postUrl,
                contentType:"application/json",
                dataType:"JSON",
                data: JSON.stringify({
                    "service": "zm3c.aftersale.second",
                    "channel": "m",
                    "sv": "1.2.300",
                    "cv": "1.4.10",
                    "pn": "xxx",
                    "st": "MD5",
                    "sign": $.fn.cookie('token'),
                    "sn": dateNum,
                    "reqData": {
                        "uid": Number( $.fn.cookie('uid')),//OK
                        "type": selType,//ok
                        "aftercode": code_obj.ordercode,//ok
                        "express": exid,
                        "producttype":producttype_obj,
                        "reason": select_why,
                        "remark":jsarea,
                        //"remark":jsarea,//ok
                        "imageboxs":imageboxs,
                        "logisticsName":logisticsName,
                        "logisticsCode":logisticsCode,
                    }

                }),
                success:function(msg){

                    var m =(new Function("","return "+msg))();
                    if(m.code == 200){

                        var code = m.respData.code;
                        $.toast("您的再次售后服务单已提交成功");
                        setTimeout(window.location.href = '/modules/aftersales/html/afterSales_list.html',3000);
                    }else if(m.code == 506){
                        $.toast("请重新登录");
                        setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
                    }else{
                        $.toast(m.msg);
                    }
                },
                error:function(){
                    $.alert("系统繁忙请稍后再试");
                }
            });
        }


    });
});
