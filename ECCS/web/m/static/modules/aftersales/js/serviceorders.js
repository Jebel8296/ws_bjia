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

	// 获取产品id
	var url = decodeURIComponent( window.location.search ); //获取url中"?"符后的字串   
    var theRequest = new Object();   
    if (url.indexOf("?") != -1) {   
	    var str = url.substr(1);   
	    strs = str.split("&");   
	    for(var i = 0; i < strs.length; i ++) {   
	        theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);   
	    }   
    }   
    var uid = parseInt(theRequest.uid); //用户id
    // var orderid = parseInt(theRequest.orderid); //订单id
 	var devicescode = theRequest.devicescode; //订单编号
    var prodcode;
 	

	$.ajax({
        type:"post",
        url:postUrl,
        contentType:"application/json",
        dataType:"JSON",
        data: JSON.stringify({
            "service": "zm3c.aftersale.device",
            "channel": "m",
            "sv": "1.2.300",
            "cv": "1.4.10",
            "pn": "xxx",
            "st": "MD5",
            "sign": $.fn.cookie('token'),
            "sn": dateNum,
            "reqData": {
                "uid": Number( $.fn.cookie('uid')),
                "devicescode": devicescode,
                "date": 1477564552000,
                "code": "JNV6",
                "smssn": "7486100202"
            }
        }),
        success:function(msg){
          var m =(new Function("","return "+msg))();
            if(m.code == 200){


                var proListNum = m.respData.respData; 

                var proList = '<li data-code="'+ proListNum.code +'"><div class="item-content">\
                      <div class="item-media"><img src="/modules/public/img/3/3C40A100010001.png"></div>\
                      <div class="item-inner">\
                        <div class="item-title-row">\
                          <div class="item-title">'+ proListNum.name +'</div>\
                        </div>\
                        <div class="color0">￥'+ proListNum.price +'</div>\
                        <div class="item-subtitle">设备编号：'+ proListNum.code +'</div>\
                      </div></div></li>';
                $(".bigImgList ul").append(proList);    

                prodcode = proListNum.code;
                 for(i=0;i<proListNum.express.length;i++){

                    if(proListNum.express[i].status==1){
                        
                        var receiptHtml = '<dl><dt>收货地址</dt>\
                            <dd>收货人：'+ proListNum.express[i].name + '&nbsp;&nbsp;联系电话：'+ proListNum.express[i].phone +'<br/>地   址：'+ proListNum.express[i].provname + proListNum.express[i].areaname + proListNum.express[i].address +'</dd>\
                        </dl> ';
                        $("#receiptAddress").append(receiptHtml);
                    }
                     
                   
                 }   
                

             	for(j=0;j<$(".sel a").length;j++){
             		var status = $(".sel a").eq(j).text().replace(/\ +/g,"");
	             	var defaultStatus = m.respData.aftertype;
					
	             	if(status == defaultStatus){
	             		$(".sel a").eq(j).addClass("cur");
	             	}
             	}
             	
            }else if(m.code == 506){
                $.toast("请重新登录");
                setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
            }else{
              $.toast(m.msg);
            }
        },
        error:function(){
          
        }
            
    });
    
    
        
    var producttype;
    var reason="";
    $(document).on('click','.orderMsg_radio li', function () {
        $(".orderMsg_radio li").removeClass();
        $(this).addClass("selAfter");
        producttype = $(this).find("label").text();
       
    });  


    $(document).on('keyup','.txtarea', function () {

        reason = $(".txtarea").val();
            
            var hasNum;
            var allNum = $(".allNum").text();
            for(hasNum=0; hasNum<allNum;hasNum++){
                hasNum = $(".hasNum").text(reason.length);          
            }
            if(reason.length>allNum){
                hasNum = $(".hasNum").text(allNum);
                $(this).val($(this).val().substring(0,300));
            }

    }); 

    
    // 提交服务单
    $(document).on('click','#subOrderBtn', function () {       
        var typeNum = $(".sel").find(".cur");
        var selAfterPro = $(".selAfter").length;
 
        decives = $("li").data("code");

        if(typeNum.length==0){
            $.toast("请选择售后类型");
            return;
        }else if(selAfterPro==0){
            $.toast("请选择售后产品");
            return;
        }else {
            var selType = $(".sel").find(".cur").data("type");
            var imgNum  =$('#up_pic li').length;
            var imageboxs =[];
            for(i=0;i<imgNum;i++){
                var imgsrc = $('#up_pic li').eq(i).find("img").attr("src");
                imageboxs.push(imgsrc);
                
            }
            imageboxs.pop();
             // console.log(imageboxs);
            
            $.ajax({
                type:"post",
                url:postUrl,
                contentType:"application/json",
                dataType:"JSON",
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
                        "uid": Number( $.fn.cookie('uid')),
                        "type": selType,
                        "ordercode": "0000",
                        "prodcode": prodcode,
                        "devicescode":devicescode, 
                        "signtime":1479443403446, 
                        "express": 52,
                        "producttype":producttype,
                        "reason": reason,
                        "imageboxs": [
                            "uri1",
                            "uri2"
                        ]
                    }
                }),
                success:function(msg){
                  var m =(new Function("","return "+msg))();
                    if(m.code == 200){
                        
                        // var code = m.respData.code;
                        $.toast("您的售后服务单已提交成功");
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


    // 图片上传
    (function(){
        function preview(file,that) {
            var reader = new FileReader()
            reader.onload = function(e) {
                var _fileType = that.val().substr(-4).toLowerCase()
                if (_fileType === '.jpg' || _fileType === 'jpeg' || _fileType === '.png' || _fileType === '.gif') {

                    if( $('#up_pic li').length > 8 ){
                        $('#up_li').hide();
                    }
                    var $img = $('<img>').attr("src", e.target.result);
                    var $li = $('<li />');
                   // $li.append( $img ).append( '<span class="removeLi">×</span><span class="up_ing">上传中……</span>' );
                    $li.append( $img ).append( '<span class="removeLi">×</span>' );
                    that.parent().before( $li );
                    //that.parent().append($img).find('span').hide();
                    that.after(that.clone());
                    that.remove();
                    /*
                    setTimeout(function(){
                        $li.find('.up_ing').html('上传失败');
                    },5000)*/
                    return;
                }
                $.alert( '图片格式不对，<br>请选择jpg、png、gif格式图片' );
            }
            reader.readAsDataURL(file);
        }
        $('#up_pic').on('change', '[type=file]', function(e) {
            var file = e.target.files[0]
            preview(file, $(this));
        }).on('click', '.removeLi', function(){
            $(this).parent().remove();
            $('#up_li').css({'display': 'inline-block'});
        });
        //发布提交事件start
        $('#submit').on('touchend', function(){
            if( !$.trim($('#title').val()) ){
                $.alert('请填写标题！');
                return;
            }else if( !$.trim($('#describe').val()) ){
                $.alert('请填写描述！');
                return;
            }else if( !$.trim($('#price').val()) || !(+$('#price').val()>0) ){
                $.alert('请正确填写价格！');
                return;
            }else if( !$.trim($('#priceCost').val()) || !(+$('#priceCost').val()>0) ){
                $.alert('请正确填写原价！');
                return;
            }//以上为必填及填写内容验证
            var imgArr = [];
            var $imgFile = $('#up_pic img');
            for( var i=0; i<$imgFile.length; i++ ){
                imgArr.push( $imgFile.attr('src') );
            };
            $.ajax({
                url: 'zzz.json',//接口地址
                type: 'post',
                cache: false,
                beforeSend:function(){
                  $('#submit').html('发布中，请稍后……');
                  $('body').append( '<div class="modal-overlay modal-overlay-visible" id="send_ing"></div>' );
                },
                data: {
                    title: $('#title').val(),
                    describe: $('#describe').val(),
                    img: imgArr,//imgArr数据类型为数组，数组项为图片base64串
                    price: $('#price').val(),
                    priceCost: $('#priceCost').val(),
                    words: $('#words').val()
                },
                success: function(msg) {
                  /*
                  情况1：请求完全成功——返回数据格式为：
                      {
                        code:0,
                        message:"请求完全成功"
                      }
                  情况2：请求不完全成功——返回数据格式为demo：
                      {
                        code:3005,
                        message:"您未登录，请重新登录！"
                      }
                  tips：该处逻辑为：code==0，则弹"发布成功"，并刷新页面；否则直接弹返回数据中message值
                  */
                    $('#send_ing').remove();
                    $('#submit').html('确定发布');
                    if( msg.code != 0 ){
                      $.alert( msg.message );
                      return;
                    }
                    $('#send_ing').remove();
                    $('#submit').html('确定发布');
                    $.alert('发布成功', function(){window.location.reload()});
                },
                error: function(){
                    $('#send_ing').remove();
                    $('#submit').html('确定发布');
                    $.alert('网络错误，请稍后重试');
                }
            });
        });
        //发布提交事件end
    })();
 
});