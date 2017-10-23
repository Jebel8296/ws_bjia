define(function (require, exports, module) {
    var checkRepeat = require('COMMON/plugin/jq_checkRepeat');//检测频繁点击插件
    require('LIBS/sui/js/sm.min');
    require('LIBS/sui/js/sm-extend.min');
    require('public/js/menu');
    require('public/js/ajaxService');

    $.init();


    function getCookie(c_name) {
        if (document.cookie.length > 0) {
            c_start = document.cookie.indexOf(c_name + "=")
            if (c_start != -1) {
                c_start = c_start + c_name.length + 1
                c_end = document.cookie.indexOf(";", c_start)
                if (c_end == -1) c_end = document.cookie.length
                return unescape(document.cookie.substring(c_start, c_end))
            }
        }
        return ""
    }

    function setCookie(c_name, value, expiredays) {
        var exdate = new Date()
        exdate.setDate(exdate.getDate() + expiredays)
        document.cookie = c_name + "=" + escape(value) +
            ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
    }

    function checkCook(){
        var uid = $.fn.cookie('uid');
        if (uid != null && uid != "") {
            $("#nickname").append(unescape($.fn.cookie('phone')));
            //self.location.href="/modules/center/html/profile.html";
        }
        else {
            self.location.href="/modules/passport/html/login.html";

            /**
             username = prompt('Please enter your name:', "")
             if (username != null && username != "") {
                setCookie('username', username, 365)
            }
             */
        }
    }

    var init = function checkCookie() {
        var uid = $.fn.cookie('uid');
        if (uid != null && uid != "") {
            $("#nickname").append(unescape($.fn.cookie('phone')));
            //self.location.href="/modules/center/html/profile.html";
        }
        else {
            self.location.href="/modules/passport/html/login.html";

            /**
            username = prompt('Please enter your name:', "")
            if (username != null && username != "") {
                setCookie('username', username, 365)
            }
             */
        }
    }

    init();


    $("#quit_btn").on("click",function () {
        $.fn.cookie('uid',null,{path:'/'});
        $.fn.cookie('phone',null,{path:'/'});
        $.fn.cookie('token',null,{path:'/'});
        self.location.href="/modules/passport/html/login.html";
    });

});