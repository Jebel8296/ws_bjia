var timeStamp = new Date().getTime().toString();
var host = "http://192.168.108.171:8080/", typeX="1", domainCode="test";
var callBackUrl = "http://localhost/modules/home/html/index.html";
//var postUrl = "http://10.12.12.26:20020/gateway.do";
var postUrl = "http://192.168.108.171:20020/gateway.do";


var uid = 39;


function getNumDate(){
    var date = new Date();
    var dnum="";
    //var seperator1 = "-";
    //var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {month = "0" + month;}
    if (strDate >= 0 && strDate <= 9) {strDate = "0" + strDate;}
    for(var i=0;i<4;i++) {dnum+=Math.floor(Math.random()*10);}
    var currentdate = date.getFullYear() + month  + strDate + date.getHours() + date.getMinutes() + date.getSeconds() + date.getMilliseconds();
    return currentdate + dnum;
}
var dateNum = getNumDate();

function getTime(time){
    var date = new Date(time)
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    var minute = date.getMinutes();
    minute = minute < 10 ? ('0' + minute) : minute;
    //return y + '-' + m + '-' + d+' '+h+':'+minute;
    return y + '-' + m + '-' + d;
}
function getToday(){
    var date = new Date()
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    var minute = date.getMinutes();
    minute = minute < 10 ? ('0' + minute) : minute;
    //return y + '-' + m + '-' + d+' '+h+':'+minute;
    return y + '-' + m + '-' + d;
}
function getAllTime(time){
    var date = new Date(time)
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    var minute = date.getMinutes();
    minute = minute < 10 ? ('0' + minute) : minute;
    return y + '-' + m + '-' + d+' '+h+':'+minute;

}

function toF(t){
    var getT = parseInt(t);
    return (getT).toFixed(2)
}

function checkC(str){
    var uid = $.fn.cookie('uid');
    if(uid != null && uid != ""){
        setTimeout(str, 1000);
    }else{
        setTimeout('window.location.href = "/modules/passport/html/login.html"', 1000);
    }
}
/*
$('#toCart').on('click', function () {
    var str = 'window.location.href = "/modules/purchase/html/myCart.html"';
    checkC(str);
})

$('#psonID').on('click', function () {
    var str = 'window.location.href = "/modules/center/html/profile.html"';
    checkC(str);
});*/
var proCount =0;
//proListMyCart();
//function proListMyCart(){
//    $.ajax({
//        type:"post",
//        url:postUrl,
//        contentType:"application/json",
//        dataType:"JSON",
//        data: JSON.stringify({
//            "service": "zm3c.cart.list",
//            "channel": "m",
//            "sv": "1.2.300",
//            "cv": "1.4.10",
//            "st": "MD5",
//            "sign": $.fn.cookie('token'),
//            "sn": dateNum,
//            "reqData": {
//                "uid": Number( $.fn.cookie('uid'))
//            },
//
//            "ts": 149203841243
//        }),
//        success:function(m){
//            var m = JSON.parse( m );
//            if(m.respData !=null){
//                var num  = m.respData.data.length; //获取产品个数
//                for(var i=0;i<num;i++){
//                    proCount +=m.respData.data[i].prodtotal
//                }
//                $("#toCart span").text(num)
//            }
//        },
//        error:function(){
//            $.alert("系统繁忙请稍后再试");
//        }
//
//    });
//};

function UrlSearch(userId){
    var name,value;
    var str=userId; //取得整个地址栏
    var num=str.indexOf("?");
    str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]
    var arr=str.split("&"); //各个参数放到数组里
    for(var i=0;i < arr.length;i++){
        num=arr[i].indexOf("=");
        if(num>0){
            name=arr[i].substring(0,num);
            value=arr[i].substr(num+1);
            this[name]=value;
        }
    }
}
/*
getCartTotal();
function getCartTotal(){


    $.ajax({
        type:"post",
        url:postUrl,
        contentType:"application/json",
        dataType:"JSON",
        data: JSON.stringify({
            "service": "zm3c.cart.list",
            "channel": "m",
            "sv": "1.2.300",
            "cv": "1.4.10",
            "st": "MD5",
            "sign": $.fn.cookie('token'),
            "sn": dateNum,
            "reqData": {
                "uid": Number( $.fn.cookie('uid'))
            },

            "ts": 149203841243
        }),
        success:function(m){
            //var num  = m.respData.data.length; //获取产品个数
            console.log(m)
           $(".icon-cart").find("span").text("") ;
        }


    });
}*/