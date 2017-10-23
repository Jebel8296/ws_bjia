define(function(require, exports, module) {
	var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
	require('LIBS/sui/js/sm.min');
	require('LIBS/sui/js/sm-extend.min');
	require('public/js/ajaxService');

	$.init();

	//选择型号
	$('.sel a').on('click', function () {		
		$(this).addClass("cur").siblings("a").removeClass();
	});
	//TODO 关闭模态框
    $(document).on('click','.close',function(){
        $("#modal").css('display','none')
    })
    $(document).on('click','.close',function(){
        $("#modal_logistics").css('display','none')

    })

    $(document).on('click','#btn_box span', function () {
        //TODO 取消
        if($(this).text()=="取消"){
			console.log("取消操作")
			$("#modal").css('display','block');
            var cancelcode=$(this).attr("title");
            localStorage.setItem("cancel_code_num",cancelcode);
        }
        //TODO 填写物流信息
        if($(this).text()=="填写物流信息"){
            console.log("填写物流信息操作")
            $("#modal_logistics").css('display','block');
            $("#selected_box").change(function(){
                var selected=$("#selected_box").val();
                if(selected=="请选择取消原因"){
                    alert("您还没选择取消原因");
                    return;
                }
                localStorage.setItem("logisticsinfo",selected);
            });

        }
        //TODO 再次申请
        if($(this).text()=="再次申请"){
            console.log("再次申请操作");
            var submitAgain=new Object();
            submitAgain.ordercode=$(this).attr("title");
            submitAgain.orderadr=$(this).children().attr("title");
            submitAgain.ordername=$(this).parents().attr('title');
            submitAgain=JSON.stringify(submitAgain);
            localStorage.setItem('submit_Again',submitAgain)

            $(this).children().attr('href','/modules/aftersales/html/againAfterSales.html?');

        }
        var code=$(this).attr("title");
        localStorage.setItem("order_code_num",code);
	});
    $(document).on('click','#determine1', function () {
       	var company= $("#logistics_Order").val();
       	if(company==""){
       		$.toast("单号不能为空")
       		return false;
       	}
       	var logistics_info=localStorage.getItem("logisticsinfo");
        if(logistics_info=="请选择物流公司"){
            $.toast("物流公司不能为空")
            return false;
        }
 		var after_code=localStorage.getItem("order_code_num");

        $.ajax({
            type:"post",
            url:postUrl,
            contentType:"application/json",
            dataType:"JSON",
            data: JSON.stringify({
                "service": "zm3c.aftersale.upload",
                "channel": "m",
                "sv": "1.2.300",
                "cv": "1.4.10",
                "pn": "xxx",
                "st": "MD5",
                "sign": $.fn.cookie('token'),
                "sn": dateNum,
                "reqData": {
                    "uid":Number( $.fn.cookie('uid')),
                    "aftercode":after_code,
                    "logisticsCode":company,
                    "logisticsName":logistics_info,
                }
         	}),
            success:function(msg){
                var m =(new Function("","return "+msg))();
            	if(m.code=200){

                     $("#modal_logistics").css('display','none');
                     $.toast("提交成功");
					window.location.reload();
				}

			},
			error:function(){
                $.alert("系统繁忙请稍后再试");
            }
        })
	});
    $(document).on('click','#determine', function () {
        //var after_code=$(this).$(".line").text().slice(5,11);
        var after_code=localStorage.getItem("cancel_code_num");

        $.ajax({
            type:"post",
            url:postUrl,
            contentType:"application/json",
            dataType:"JSON",
            data: JSON.stringify({
                "service": "zm3c.aftersale.cancel",
                "channel": "m",
                "sv": "1.2.300",
                "cv": "1.4.10",
                "pn": "xxx",
                "st": "MD5",
                "sign": $.fn.cookie('token'),
                "sn": dateNum,
                "reqData": {
                    "uid":Number( $.fn.cookie('uid')),
                    "aftercode":after_code
                }
            }),
            success:function(msg){
                var m =(new Function("","return "+msg))();
                if(m.code=200){

                    $("#modal").css('display','none');
                    $.toast("取消成功");
                    window.location.reload();
                }


            },
            error:function(){
                $.alert("系统繁忙请稍后再试");
            }
        })
            });
    function getAfterSaleData() {
 	$.ajax({
		type:"post",
        url:postUrl,
        contentType:"application/json",
        dataType:"JSON",
        data: JSON.stringify({
		    "service": "zm3c.aftersale.list",
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
		
		success:function(msg){

          	var m =(new Function("","return "+msg))();
          	console.log(m);

            if(m.code == 200){

                var afterItem = m.respData.data;   //获取产品个数
				var status_type;
                var btn_name=null;
                //TODO 时间转换函数
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
				for(i=0;i<afterItem.length;i++){
					var applyT = getTise(afterItem[i].applytime)

					/*TODO 订单类型*/
                    if(afterItem[i].status==1){
                        status_type="待邮递";
                        btn_name="填写物流信息"
                    }
                    if(afterItem[i].status==2){
                        status_type="处理中"
                        btn_name=''
                    }
                    if(afterItem[i].status==3){
                        status_type="售后完成"
                        btn_name=''
                    }
                    if(afterItem[i].status==4){
                        status_type="关闭"
                        btn_name=''
                    }
                    if(afterItem[i].status==5){
                        status_type="待补充"
                        btn_name="再次申请"

                    }
                    if(afterItem[i].status==0){
                        status_type="待受理"
                        btn_name="取消"
                    }
                    console.log(afterItem[i].prodcode);
                    var ordermodel=afterItem[i].prodcode.slice(4,8)//TODO 获取设备型号
                    var ordercolor=afterItem[i].prodcode.slice(8,10)//TODO 获取设备颜色
                    var adr=encodeURI(afterItem[i].customer.address);
                    if(ordercolor=="01"){
                        ordercolor="青色"
                    }
                    if(ordercolor=="02"){
                        ordercolor="黑色"
                    }
					var proHtml = '<div class="d_num line">售后编号：'+ afterItem[i].aftercode +'                   <span>'+ applyT +'</span></div>\
					<div class="list-block media-list"><ul id="exPro"><li><a  href="/modules/aftersales/html/afterSales_detail.html?code='+ afterItem[i].aftercode +'&name='+afterItem[i].customer.name+'&adr='+adr+'&phone='+afterItem[i].customer.phone+'" class="item-content">\
					<div class="item-media"><img src="/modules/public/img/3/3C40A100010001.png"></div><div class="item-inner"><div class="item-title-row">\
					<div class="item-title">'+ afterItem[i].prodname +'</div><div class="item-after">x'+ afterItem[i].total +'</div>\
					</div>\
					<div class="item-subtitle"><div>型号：'+ordermodel+'</div> \
					<div>颜色：'+ordercolor+'</div>\
					<div>价格：￥788.00 <span style="float: right" class="color1">'+ status_type +'</span></div>\
					</div>\
					</div>\
					</a>\
					<div class="d_num topLine">\
					<a href="javascript:;" class="color0">'+ afterItem[i].aftertype +'</a>\
					<div id="btn_box" title="'+afterItem[i].customer.name+'">\
						<span style="width:72px;text-align: right; position: relative;top:-5px;text-decoration:underline" title="'+afterItem[i].aftercode+'"><a title="'+afterItem[i].customer.address+'" style="color:#0a8ddf;">'+btn_name+'</a></span>\
					</div>\
					</div></li>\
					</ul></div>';

					$("#afterMsgList").append(proHtml);

				}

            }else if(m.code==506){
				$.toast("请重新登录");
				setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
			}else{
              $.toast(m.msg);
            }
        },
        error:function(){
          $.alert("系统繁忙请稍后再试");
        }				
	})
   }


   getAfterSaleData();



});