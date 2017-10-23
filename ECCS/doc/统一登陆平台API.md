# 统一登陆平台 API
	业务操作步骤： 
  		1、对接的业务，需要先向登陆平台提出申请
  		2、登陆平台会分配domainCode和domainSecret
		3、业务服务端需要记录domainCode和domainSecret，在相应的业务接口中会对所有请求参数在业务系统的服务端做加密，防止参数被篡改 或 恶意接入
	说明：
		1、domainSecret加密的动作，需要放在业务系统的服务端
		2、用户的注册、登陆，都需要在统一登陆平台处理
		3、登陆成功后，页面每次请求业务系统时，都需要带上user_id、 user_name 和 token
		4、业务系统的服务端，需要判断身份之前，务必到统一登陆平台验证：当前的用户id和token是否合法
		
## 注册接口

### 本地账号是否可用接口
	接口形式：REST
	样例：
		var host = "http://api6.wwoqu.com/";
		$.ajax({
                type:'GET',
                url:host+"login/api/local/isAvailable",
                data:{
                	'domainCode':'test',
                	'un':'13439299101',
                	'sign':'sign'
                },
                async:false,
                success: function(data){
                	if(data.code == '0'){
                		if(data.obj==true || data.obj=='true'){
                			alert("该账号可用");
                		}else{
                			alert("该账号不可用");
                		}
                	}else{
                		AlertService.error(data.msg);
                	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	AlertService.error("请稍后再试！");
                }
            });
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* un ： 注册账号
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串

### 本地账号注册接口：普通账号
	接口形式：REST
	样例：
		var host = "http://api6.wwoqu.com/";
		var password = "12345678";
		$.ajax({
                type:'GET',
                url:host+"login/api/local/regist",
                data:{
                	'domainCode':'test',
                	'un':'xiaoshengfa',
                	'pd':substring(sha1(password),0,15),
                	'sign':'sign'
                },
                async:false,
                success: function(data){
                	if(data.code == '0'){
                		alert("注册成功");
                		alert(data.obj.user_id);
                		alert(data.obj.token);
                	}else{
                		AlertService.error(data.msg);
                	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	AlertService.error("请稍后再试！");
                }
            });
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* un ： 注册账号
		* pd ： 密码，先做sha1加密，再取前15位
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串

### 本地账号注册接口：手机号注册
	接口形式：REST
	样例：
		var host = "http://api6.wwoqu.com/";
		var password = "12345678";
		$.ajax({
                type:'GET',
                url:host+"login/api/local/mobileRegist",
                data:{
                	'domainCode':'test',
                	'un':'13439299101',
                	'pd':substring(sha1(password),0,15),
                	'sign':'sign'
                },
                async:false,
                success: function(data){
                	if(data.code == '0'){
                		alert("注册成功");
                		alert(data.obj.user_id);
                		alert(data.obj.token);
                	}else{
                		AlertService.error(data.msg);
                	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	AlertService.error("请稍后再试！");
                }
            });
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* un ： 注册手机号
		* pd ： 密码，先做sha1加密，再取前15位
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串

### 本地账号注册接口：手机号注册（手机验证码）
	接口形式：REST
	样例：
		var host = "http://api6.wwoqu.com/";
		var password = "12345678";
		$.ajax({
                type:'GET',
                url:host+"login/api/local/mobileRegistWithSms",
                data:{
                	'domainCode':'test',
                	'un':'13439299101',
                	'pd':substring(sha1(password),0,15),
                	'code': '2345',
                	'sign':'sign'
                },
                async:false,
                success: function(data){
                	if(data.code == '0'){
                		alert("注册成功");
                		alert(data.obj.user_id);
                		alert(data.obj.token);
                	}else{
                		AlertService.error(data.msg);
                	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	AlertService.error("请稍后再试！");
                }
            });
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* un ： 注册手机号
		* pd ： 密码，先做sha1加密，再取前15位
		* code：手机验证码
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串

### 本地账号注册接口：邮箱注册
	接口形式：REST
	样例：
		var host = "http://api6.wwoqu.com/";
		var password = "12345678";
		$.ajax({
                type:'GET',
                url:host+"login/api/local/mailRegist",
                data:{
                	'domainCode':'test',
                	'un':'test@163.com',
                	'pd':substring(sha1(password),0,15),
                	'sign':'sign'
                },
                async:false,
                success: function(data){
                	if(data.code == '0'){
                		alert("注册成功");
                		alert(data.obj.user_id);
                		alert(data.obj.token);
                	}else{
                		AlertService.error(data.msg);
                	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	AlertService.error("请稍后再试！");
                }
            });
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* un ： 注册邮箱
		* pd ： 密码，先做sha1加密，再取前15位
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串

### 第三方账号注册接口：微信
	接口形式：URL
	样例：http://api6.wwoqu.com/login/api/third/wechat?domainCode=test&type=regist&callback=http://www.bjia.cn/registResult.html&sign=sign
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* type ： regist，代表注册；如果仅仅是登陆，则无需此参数
		* callback ： 注册完成后，默认已经登陆，并且返回注册成功后的地址；http开头；
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串
	返回成功：http://www.bjia.cn/registResult.html?code=0&user_id=1&token=abcdefg
	返回失败：http://www.bjia.cn/registResult.html?code=80013&msg=该账号已注册！
	返回参数：
		* code：0代表成功，非0代表失败
		* msg：失败时的提示信息
		* user_id：用户的id
		* token：登陆成功后的token

### 第三方账号注册接口：QQ
	接口形式：URL
	样例：http://api6.wwoqu.com/login/api/third/qq?domainCode=test&type=regist&callback=http://www.bjia.cn/registResult.html&sign=sign
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* type ： regist，代表注册；如果仅仅是登陆，则无需此参数
		* callback ： 注册完成后，默认已经登陆，并且返回注册成功后的地址；http开头；
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串
	返回成功：http://www.bjia.cn/registResult.html?code=0&user_id=1&token=abcdefg
	返回失败：http://www.bjia.cn/registResult.html?code=80013&msg=该账号已注册！
	返回参数：
		* code：0代表成功，非0代表失败
		* msg：失败时的提示信息
		* user_id：用户的id
		* token：登陆成功后的token

### 第三方账号注册接口：新浪微博
	接口形式：URL
	样例：http://api6.wwoqu.com/login/api/third/sinaWeibo?domainCode=test&type=regist&callback=http://www.bjia.cn/registResult.html&sign=sign
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* type ： regist，代表注册；如果仅仅是登陆，则无需此参数
		* callback ： 注册完成后，默认已经登陆，并且返回注册成功后的地址；http开头；
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串
	返回成功：http://www.bjia.cn/registResult.html?code=0&user_id=1&token=abcdefg
	返回失败：http://www.bjia.cn/registResult.html?code=80013&msg=该账号已注册！
	返回参数：
		* code：0代表成功，非0代表失败
		* msg：失败时的提示信息
		* user_id：用户的id
		* token：登陆成功后的token
		
## 登陆接口

### 本地账号登陆接口
	接口形式：REST
	样例：
		var host = "http://api6.wwoqu.com/";
		var password = "12345678";
		$.ajax({
                type:'GET',
                url:host+"login/api/local/login",
                data:{
                	'domainCode':'test',
                	'un':'13439299101',
                	'timestamp':时间戳<s单位>,
                	'pd':md5(substring(sha1(password),0,15)+timestamp),
                	'sign':'sign'
                },
                async:false,
                success: function(data){
                	if(data.code == '0'){
                		alert("登陆成功");
                		alert(data.obj.user_id);
                		alert(data.obj.token);
                	}else{
                		AlertService.error(data.msg);
                	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	AlertService.error("请稍后再试！");
                }
            });
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* un ： 登陆账号
		* timestamp：当前时间戳，以秒为单位 
		* pd ： 密码，先做sha1加密，再取前15位，连接timestamp后，做md5加密
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串

### 第三方账号登陆接口：微信
	接口形式：URL
	样例：http://api6.wwoqu.com/login/api/third/wechat?domainCode=test&callback=http://www.bjia.cn/registResult.html&sign=sign
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* callback ： 注册完成后，默认已经登陆，并且返回注册成功后的地址；http开头；
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串
	返回成功：http://www.bjia.cn/registResult.html?code=0&user_id=1&token=abcdefg
	返回失败：http://www.bjia.cn/registResult.html?code=80005&msg=该账号未注册！
	返回参数：
		* code：0代表成功，非0代表失败
		* msg：失败时的提示信息
		* user_id：用户的id
		* token：登陆成功后的token

### 第三方账号登陆接口：QQ
	接口形式：URL
	样例：http://api6.wwoqu.com/login/api/third/qq?domainCode=test&callback=http://www.bjia.cn/registResult.html&sign=sign
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* callback ： 注册完成后，默认已经登陆，并且返回注册成功后的地址；http开头；
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串
	返回成功：http://www.bjia.cn/registResult.html?code=0&user_id=1&token=abcdefg
	返回失败：http://www.bjia.cn/registResult.html?code=80005&msg=该账号未注册！
	返回参数：
		* code：0代表成功，非0代表失败
		* msg：失败时的提示信息
		* user_id：用户的id
		* token：登陆成功后的token

### 第三方账号登陆接口：新浪微博
	接口形式：URL
	样例：http://api6.wwoqu.com/login/api/third/sinaWeibo?domainCode=test&callback=http://www.bjia.cn/registResult.html&sign=sign
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* callback ： 注册完成后，默认已经登陆，并且返回注册成功后的地址；http开头；
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串
	返回成功：http://www.bjia.cn/registResult.html?code=0&user_id=1&token=abcdefg
	返回失败：http://www.bjia.cn/registResult.html?code=80005&msg=该账号未注册！
	返回参数：
		* code：0代表成功，非0代表失败
		* msg：失败时的提示信息
		* user_id：用户的id
		* token：登陆成功后的token

## 验证登陆状态接口
	接口形式：REST 或  URL
	样例：
		var host = "http://api6.wwoqu.com/";
		$.ajax({
                type:'GET',
                url:host+"login/api/local/tokenValid",
                data:{
                	'ui':1,
                	'token':'token'
                },
                async:false,
                success: function(data){
                	if(data.code == '0'){
                		if(data.obj==true || data.obj=='true'){
                			alert("已登录");
                		}else{
                			alert("未登陆");
                		}
                	}else{
                		AlertService.error(data.msg);
                	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	AlertService.error("请稍后再试！");
                }
            });
	参数：
		* ui ： 登陆或注册成功时，登陆平台返回的user_id
		* token ： 登陆或注册成功时，登陆平台返回的token

## 密码修改接口
	接口形式：REST
	样例：
		var host = "http://api6.wwoqu.com/";
		$.ajax({
                type:'GET',
                url:host+"login/api/local/changePassword",
                data:{
                	'domainCode':'test',
                	'ui':1,
                	'token':'token',
                	'timestamp':时间戳<s单位>,
                	'opd':md5(substring(sha1(password),0,15)+timestamp),
                	'pd':substring(sha1(password),0,15),
                	'sign':'sign'
                },
                async:false,
                success: function(data){
                	if(data.code == '0'){
                		alert("修改成功");
                	}else{
                		AlertService.error(data.msg);
                	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	AlertService.error("请稍后再试！");
                }
            });
	参数：
		* domainCode ： 登陆平台分配的domainCode
		* ui ： 登陆或注册成功时，登陆平台返回的user_id
		* token ： 登陆或注册成功时，登陆平台返回的token
		* timestamp：当前时间戳，以秒为单位 
		* opd ： 原密码，先做sha1加密，再取前15位，连接timestamp后，做md5加密
		* pd ： 新密码，先做sha1加密，再取前15位
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串

## 图片验证码
	接口形式：REST
	样例：
		var host = "http://api6.wwoqu.com/";
		$.ajax({
                type:'GET',
                url:host+"base/api/public/captcha",
                data:{},
                async:false,
                success: function(data){
                	if(data.code == '0'){
	            		exp = data.obj.exp;
	            		token = data.obj.token;
	            		var picBase64 = data.obj.pic;
	            	}else{
	            		alert(data.msg);
	            	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	AlertService.error("请稍后再试！");
                }
            });
	返回参数：
		* data.obj.exp ： 过期时间
		* data.obj.token ： 图片验证码token
		* data.obj.pic ： 图片base64编码

## 短信验证码(带图片验证码)
	接口形式：REST
	样例：
		var host = "http://api6.wwoqu.com/";
		$.ajax({
                type:'GET',
                url:host+"login/api/local/sms",
                data:{
                	'captcha':captcha,
	            	'exp':exp,
	            	'token':token,
	            	'un':'13439299101',
	            	'content':'验证码：%verifycode%',
	            	'type':'1',
	            	'domainCode':'test',
	            	'sign':'sign'
                },
                async:false,
                success: function(data){
                	if(data.code == '0'){
	            		alert(data);
	            	}else{
	            		alert(data.msg);
	            	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	AlertService.error("请稍后再试！");
                }
            });
	参数：
		* captcha ： 用户输入的图片验证码
		* exp ： 图片验证码请求时返回的data.obj.exp
		* token ： 图片验证码请求时返回的data.obj.token
		* un： 用户输入的手机号
		* content：短信内容模板，必须包含 %verifycode% ，用来替换短信
		* type：1代表注册短信
		* domainCode ： 登陆平台分配的domainCode
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串

## 短信验证码(不带图片验证码)
	接口形式：REST
	样例：
		var host = "http://api6.wwoqu.com/";
		$.ajax({
                type:'GET',
                url:host+"login/api/local/smsWithNoCaptcha",
                data:{
	            	'un':'13439299101',
	            	'content':'验证码：%verifycode%',
	            	'type':'1',
	            	'domainCode':'test',
	            	'sign':'sign'
                },
                async:false,
                success: function(data){
                	if(data.code == '0'){
	            		alert(data);
	            	}else{
	            		alert(data.msg);
	            	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	AlertService.error("请稍后再试！");
                }
            });
	参数：
		* un： 用户输入的手机号
		* content：短信内容模板，必须包含 %verifycode% ，用来替换短信
		* type：1代表注册短信
		* domainCode ： 登陆平台分配的domainCode
		* sign：用登陆平台分配的domainSecret对所有非空的参数加密，产生的字符串
		

## 账号绑定接口（后续扩展）