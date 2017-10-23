/**
 * Created by dell on 2016/9/7.
 */


function loginOk(localUrl) {
    var Request = new UrlSearch(decodeURI(localUrl)); //实例化
    if (Request.code == '0') {
        $.cookie('token', Request.token);
        $.cookie('uid', Request.user_id);
        $.cookie('phone', Request.user_name);
        location.reload();
    } else {
        alert(Request.msg);
    }
}


function UrlSearch(userId) {
    var name, value;
    var str = userId; //取得整个地址栏
    var num = str.indexOf("?");
    str = str.substr(num + 1); //取得所有参数   stringvar.substr(start [, length ]
    var arr = str.split("&"); //各个参数放到数组里
    for (var i = 0; i < arr.length; i++) {
        num = arr[i].indexOf("=");
        if (num > 0) {
            name = arr[i].substring(0, num);
            value = arr[i].substr(num + 1);
            this[name] = value;
        }
    }
}


function GetRequest(userId) {
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    console.log(theRequest);
}

function showLR(text) {
    $("#login").modal('show');
    if (text == "login") {
        $("#logTitil").text("登陆");
        $("#loginForm").show();
        $("#userForm").hide();
    }
}

//var posturl = "http://192.168.108.171:30030/gateway.do";
//var posturl = "http://api.bjia.cn/gateway.do";
var posturl = "http://10.12.12.26:20020/gateway.do";

//header ����
var headers = { 'Content-Type': 'application/json', 'withCredentials': false };
var pc = "pc", sv = "1.2.300", cv = "1.4.10", pn = "xxx", st = "MD5", sign = $.cookie('token') == null ? "" : JSON.parse($.cookie('token')), ts = 149203841243, sn = new Date().getTime().toString();

var timeStamp = new Date().getTime().toString();
var host = "http://192.168.108.171:8080/", typeX = "1", domainCode = "test";

//手机正则
var regPhone = /^(((13[0-9]{1})|159|153)+\d{8})$/;

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



