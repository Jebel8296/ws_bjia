/**
 * Created by dell on 2016/9/7.
 */





/*
(function(){
   var oTop = $("body").find(".info").offset();
   // var oTop = $("body").find(".box").offset().top;
    var sTop = 0;
    $(".box").css({"position":"static"});
    $(window).scroll(function(){
        sTop = $(this).scrollTop();
        if(sTop >= oTop){
            $(".box").css({"position":"static"});
        }else{
            $(".box").css({"position":"fixed","top":"0","z-index":"999"});
        }
    });

    // 二级导航显示
    $(".cartItem").on("mouseenter",function(){
        $(".carNull").slideDown(function(){
            $(".carNull").stop(true);
        })//.stop(true,true);
        return;
    })
    $(".cartItem").on("mouseleave",function(){
        $(".carNull").slideUp(function(){
            $(".carNull").stop(true,true);
        });
        return;
    })

})();
 */

function showLR(text) {
    $("#login").modal('show');
    if (text == "login") {
        $("#logTitil").text("登陆");
        $("#loginForm").show();
        $("#userForm").hide();
    }
}

//�ύURL
//var posturl = "http://10.12.12.159:30030/gateway.do";
//var posturl = "http://10.12.12.226:30030/gateway.do"
var posturl = "http://192.168.108.171:30030/gateway.do";
//header ����
var headers = { 'Content-Type': 'application/json' };
var pc = "pc", sv = "1.2.300", cv = "1.4.10", pn = "xxx", st = "MD5", sign = "xxx", ts = 149203841243;
var host = "http://api6.wwoqu.com/";


//手机正则
var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;

//时间+随机数
function getNumDate() {
    var date = new Date();
    var dnum = "";
    //var seperator1 = "-";
    //var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) { month = "0" + month; }
    if (strDate >= 0 && strDate <= 9) { strDate = "0" + strDate; }
    for (var i = 0; i < 4; i++) { dnum += Math.floor(Math.random() * 10); }
    var currentdate = date.getFullYear() + month + strDate + date.getHours() + date.getMinutes() + date.getSeconds() + date.getMilliseconds();
    return currentdate + dnum;
}
var dateNum = getNumDate();


function appendTransform(defaults, transform) {
    defaults: angular.isArray(defaults) ? defaults : [defaults];
    return defaults.concat(transform);
}

function change(picId, fileId) {
    /*
    if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG|JPEG)$/.test(fileId.value)) {
        alert("��ѡ���׺Ϊgif,jpg,png��ʽ��ͼƬ");
        fileId.value = "";
        return false;
    }
    if(fileId.value.fileSize>0){
        if(fileId.value.fileSize>10*1024){
            alert("ͼƬ������10M��");
            return false;
        }

    }*/

    var pic = document.getElementById(picId);
    var file = document.getElementById(fileId);
    if (window.FileReader) {//chrome,firefox7+,opera,IE10,IE9��IE9Ҳ�������˾���ʵ��
        oFReader = new FileReader();
        oFReader.readAsDataURL(file.files[0]);
        oFReader.onload = function (oFREvent) { pic.src = oFREvent.target.result; };
    } else if (document.all) {//IE8-
        file.select();
        var reallocalpath = document.selection.createRange().text//IE
        if (window.ie6) pic.src = reallocalpath; //IE6
        else {
            pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
            pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';//����img��srcΪbase64�����͸��ͼƬ��Ҫ������ʾ��xx
        }
    } else if (file.files) {//firefox6-
        if (file.files.item(0)) {
            url = file.files.item(0).getAsDataURL();
            pic.src = url;
        }
    }

}



function previewImage(file, prvid) {
    var tip = "只能是图片"; // 设定提示信息
    var filters = {
        "jpeg": "/9j/4",
        "gif": "R0lGOD",
        "png": "iVBORw"
    }
    var prvbox = document.getElementById(prvid);
    prvbox.innerHTML = "";
    if (window.FileReader) { // html5方案
        for (var i = 0, f; f = file.files[i]; i++) {
            var fr = new FileReader();
            fr.onload = function (e) {
                var src = e.target.result;
                if (!validateImg(src)) {
                    alert(tip)
                } else {
                    showPrvImg(src);
                }
            }
            fr.readAsDataURL(f);
        }
    }

    function validateImg(data) {
        var pos = data.indexOf(",") + 1;
        if (data.fileSize > 10 * 1024) {
            alert("图片不大于10M。");
            return false;
        }
        for (var e in filters) {
            if (data.indexOf(filters[e]) === pos) {
                return e;
            }
        }
        return null;
    }

    function showPrvImg(src) {
        var img = document.createElement("img");
        img.src = src;
        prvbox.appendChild(img);
    }
}



function preview(file, that) {
    var reader = new FileReader();
    reader.onload = function (e) {
        var _fileType = that.val().substr(-4).toLowerCase()
        if (_fileType === '.jpg' || _fileType === 'jpeg' || _fileType === '.png' || _fileType === '.gif') {
            if ($('#up_pic li').length > 8) {
                $('#up_li').hide();
            }
            var $img = $('<img>').attr("src", e.target.result);
            var $li = $('<li />');
            $li.append($img).append('<span class="removeLi">×</span><span class="up_ing">上传中……</span>');
            that.parent().before($li);
            that.after(that.clone());
            that.remove();
            setTimeout(function () {
                $li.find('.up_ing').html('上传失败');
            }, 5000)
            return;
        }
        $.alert('图片格式不对，<br>请选择jpg、png、gif格式图片');
    }
    reader.readAsDataURL(file);
}
$('#up_pic').on('change', '[type=file]', function (e) {
    alert("sfsf")
    var file = e.target.files[0]
    preview(file, $(this));
}).on('click', '.removeLi', function () {
    $(this).parent().remove();
    $('#up_li').css({ 'display': 'inline-block' });
});





