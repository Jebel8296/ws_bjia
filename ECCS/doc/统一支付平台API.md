# 统一支付平台 API
	业务操作步骤： 
  		1、对接的业务，需要先向支付平台提出申请
  		2、支付平台会分配domainCode和domainSecret
		3、业务服务端需要记录domainCode和domainSecret，在相应的业务接口中会对所有请求参数在业务系统的服务端做加密，防止参数被篡改
		
## 支付接口

### PC申请支付接口
	接口形式：URL
	样例：http://api6.wwoqu.com/pay.html#/?domainCode=test&payTypeCode=&requestChannel=&money=1&info=%E8%BF%99%E6%98%AF%E4%BD%A0%E4%B9%B0%E7%9A%84%E5%95%86%E5%93%81%E8%AF%A6%E5%8D%95%E4%BF%A1%E6%81%AF&infoEn=&requestKey=1464659622189&currencyType=CNY&pageUrl=http:%2F%2Fwww.baidu.com&bgUrl=http:%2F%2Fwww.sina.com&dialogOkUrl=http:%2F%2Fwww.sohu.com&dialogFailUrl=http:%2F%2Fwww.163.com&rate=6.8&payTypeCodeIn=&sign=ad3b0a76afb7fecb0c64bed6503b7b1e9dcfd5ed
	参数：
		* domainCode ： 支付平台分配的domainCode
		* payTypeCode ： 在支付页面上调用REST服务接口时，传递的支付渠道code，支持的值为：Wechat、Alipay、ChinapayOld、Paypal
		* requestChannel ：为了方便 业务系统扩展用，自行定义即可，也可以为空；如果当前只有一个支付渠道，为了方便后续业务扩展，建议也赋值（如：1）
		* money ： 支付的金额，单位为分
		* info ： 支付的商品信息
		* infoEn : 支付的商品信息（引文）
		* requestKey ： 业务系统自行生成，需要保证该值在业务系统中唯一；用户支付完成后，支付中心向业务系统回调时，会带上该参数，方便业务系统处理逻辑
		* currencyType ： 支付币种，支持：CNY 、 USD
		* pageUrl ： 前台返回的url，客户在支付操作完成后，页面跳转的地址
		* bgUrl ： 后台回调的url，支付中心收到第三方支付系统后台通知后，会向该地址发送GET请求，并加上一串参数，返回“success”代表成功，否则失败，该参数不能带“?”
		* dialogOkUrl ： 后续扩展用，建议和 “pageUrl”参数保持一致
		* dialogFailUrl ： 后续扩展用，建议和 “pageUrl”参数保持一致
		* rate ： 汇率 ，人民币和美元之间的转换汇率；（马拉松系统特殊需求用）
		* payTypeCodeIn ： 后续扩展用，暂时可空
		* sign ： 用支付平台分配的domainSecret对所有非空的参数加密，产生的字符串
		
### PC支付请求支付类型接口
	接口形式：REST
	样例：
		vm.payDTO.domainCode = "test";
		$.ajax({
                type:'GET',
                dataType : 'JSON',
                contentType:'application/json;charset=utf-8',
                url:"pay/api/pc/pay-types",
                data:{
                	'domainCode':vm.payDTO.domainCode,
                	'paySceneName':'pc'
                },
                async:false,
                success: function(data){
                	if(data.errcode == '0'){
                		vm.payTypes = data.obj;
                	}else{
                		AlertService.error(data.errormsg);
                	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	AlertService.error("获取支付方式失败，请稍后再试！");
                }
            });
	参数：
		* domainCode ： 支付平台分配的domainCode

		
### PC支付请求接口
	接口形式：REST
	样例：
		vm.payDTO = {
            	domainCode : $stateParams.domainCode,
            	payTypeCode : $stateParams.payTypeCode,
            	requestChannel : $stateParams.requestChannel,
            	money : $stateParams.money,
            	info : $stateParams.info,
            	infoEn : $stateParams.infoEn,
            	requestKey : $stateParams.requestKey,
            	currencyType : $stateParams.currencyType,
            	pageUrl : $stateParams.pageUrl,
            	bgUrl : $stateParams.bgUrl,
            	dialogOkUrl : $stateParams.dialogOkUrl,
            	dialogFailUrl : $stateParams.dialogFailUrl,
            	rate : $stateParams.rate,
            	payTypeCodeIn : $stateParams.payTypeCodeIn,
            	sign : $stateParams.sign
           };
		$.ajax({
                type:'POST',
                dataType : 'JSON',
                contentType:'application/json;charset=utf-8',
                url:"pay/api/pc/pay",
                data:JSON.stringify(vm.payDTO),
                //async:false,
                success: function(data){
                	if(data.errcode == '0'){
                		if("Wechat" == vm.payDTO.payTypeCode){
            				$scope.$apply(vm.wechatPayQrcode = data.obj);
            				vm.geneWxpayPollingTask = true;
    			            vm.wxpayPollingTask = $timeout(vm.wxpayPolling,2000,false);
                		}else{
                			angular.element(document.body).append(angular.element(data.obj));
                			vm.isPaying = false;
                		}
                    	vm.isPaying = false;
                	}else{
                		$scope.$apply(AlertService.error(data.errormsg));
                		$scope.$apply(vm.isPaying = false);
                	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	$scope.$apply(AlertService.error("支付失败，请稍后再试！"));
            		$scope.$apply(vm.isPaying = false);
                }
            });
	参数：
		* domainCode ： 支付平台分配的domainCode
		* payTypeCode ： 在支付页面上调用REST服务接口时，传递的支付渠道code，支持的值为：Wechat、Alipay、ChinapayOld、Paypal
		* requestChannel ：为了方便 业务系统扩展用，自行定义即可，也可以为空；如果当前只有一个支付渠道，为了方便后续业务扩展，建议也赋值（如：1）
		* money ： 支付的金额，单位为分
		* info ： 支付的商品信息
         * infoEn : 支付的商品信息(英文)
		* requestKey ： 业务系统自行生成，需要保证该值在业务系统中唯一；用户支付完成后，支付中心向业务系统回调时，会带上该参数，方便业务系统处理逻辑
		* currencyType ： 支付币种，支持：CNY 、 USD
		* pageUrl ： 前台返回的url，客户在支付操作完成后，页面跳转的地址
		* bgUrl ： 后台回调的url，支付中心收到第三方支付系统后台通知后，会向该地址发送GET请求，并加上一串参数，返回“success”代表成功，否则失败，该参数不能带“?”
		* dialogOkUrl ： 后续扩展用，建议和 “pageUrl”参数保持一致
		* dialogFailUrl ： 后续扩展用，建议和 “pageUrl”参数保持一致
		* rate ： 汇率 ，人民币和美元之间的转换汇率；（马拉松系统特殊需求用）
		* payTypeCodeIn ： 后续扩展用，暂时可空
		* sign ： 用支付平台分配的domainSecret对所有非空的参数加密，产生的字符串

### PC微信支付查询结果接口
	接口形式：REST
	样例：
		vm.payDTO = {
            	domainCode : $stateParams.domainCode,
            	payTypeCode : $stateParams.payTypeCode,
            	requestChannel : $stateParams.requestChannel,
            	money : $stateParams.money,
            	info : $stateParams.info,
            	infoEn : $stateParams.infoEn,
            	requestKey : $stateParams.requestKey,
            	currencyType : $stateParams.currencyType,
            	pageUrl : $stateParams.pageUrl,
            	bgUrl : $stateParams.bgUrl,
            	dialogOkUrl : $stateParams.dialogOkUrl,
            	dialogFailUrl : $stateParams.dialogFailUrl,
            	rate : $stateParams.rate,
            	payTypeCodeIn : $stateParams.payTypeCodeIn,
            	sign : $stateParams.sign
           };
		$.ajax({
                type:'GET',
                dataType : 'JSON',
                contentType:'application/json;charset=utf-8',
                url:"pay/api/pc/wechatQuery",
                data:vm.payDTO,
                //async:false,
                success: function(data){
                	if(data.errcode == '0'){
                		parent.location.href = vm.payDTO.dialogOkUrl;
                	}else if(data.errcode != '30000'){
                		parent.location.href = vm.payDTO.dialogFailUrl;
                	}
                	if(vm.geneWxpayPollingTask){
    		        	vm.wxpayPollingTask = $timeout(vm.wxpayPolling,2000,false);
    		        }
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	vm.wxpayPollingTask = $timeout(vm.wxpayPolling,2000,false);
                }
            });
	参数：(建议：弹出微信支付后开始每2秒轮询一次，关闭微信支付后停止)
		* domainCode ： 支付平台分配的domainCode
		* payTypeCode ： 在支付页面上调用REST服务接口时，传递的支付渠道code，支持的值为：Wechat、Alipay、ChinapayOld、Paypal
		* requestChannel ：为了方便 业务系统扩展用，自行定义即可，也可以为空；如果当前只有一个支付渠道，为了方便后续业务扩展，建议也赋值（如：1）
		* money ： 支付的金额，单位为分
		* info ： 支付的商品信息
         * infoEn : 支付的商品信息(英文)
		* requestKey ： 业务系统自行生成，需要保证该值在业务系统中唯一；用户支付完成后，支付中心向业务系统回调时，会带上该参数，方便业务系统处理逻辑
		* currencyType ： 支付币种，支持：CNY 、 USD
		* pageUrl ： 前台返回的url，客户在支付操作完成后，页面跳转的地址
		* bgUrl ： 后台回调的url，支付中心收到第三方支付系统后台通知后，会向该地址发送GET请求，并加上一串参数，返回“success”代表成功，否则失败，该参数不能带“?”
		* dialogOkUrl ： 后续扩展用，建议和 “pageUrl”参数保持一致
		* dialogFailUrl ： 后续扩展用，建议和 “pageUrl”参数保持一致
		* rate ： 汇率 ，人民币和美元之间的转换汇率；（马拉松系统特殊需求用）
		* payTypeCodeIn ： 后续扩展用，暂时可空
		* sign ： 用支付平台分配的domainSecret对所有非空的参数加密，产生的字符串
	返回：
		* errcode ： 0 代表支付成功，可以转向成功页面；非30000 代表支付失败，可以转向失败页面；30000继续轮询

### H5申请支付接口
	接口形式：URL
	样例：http://api6.wwoqu.com:80/mpay.html#/?domainCode=test&payTypeCode=&requestChannel=&money=1&info=这是你买的商品详单信息&infoEn=&requestKey=1465183124626&currencyType=CNY&pageUrl=http://www.baidu.com&bgUrl=http://www.sina.com&dialogOkUrl=http://www.sohu.com&dialogFailUrl=http://www.163.com&rate=6.8&payTypeCodeIn=&sign=cd437788bde98645fc2a738c620ecef069e901bc
	参数：
		* domainCode ： 支付平台分配的domainCode
		* payTypeCode ： 在支付页面上调用REST服务接口时，传递的支付渠道code，支持的值为：Wechat、Alipay、ChinapayOld、Paypal
		* requestChannel ：为了方便 业务系统扩展用，自行定义即可，也可以为空；如果当前只有一个支付渠道，为了方便后续业务扩展，建议也赋值（如：1）
		* money ： 支付的金额，单位为分
		* info ： 支付的商品信息
		* infoEn : 支付的商品信息（引文）
		* requestKey ： 业务系统自行生成，需要保证该值在业务系统中唯一；用户支付完成后，支付中心向业务系统回调时，会带上该参数，方便业务系统处理逻辑
		* currencyType ： 支付币种，支持：CNY 、 USD
		* pageUrl ： 前台返回的url，客户在支付操作完成后，页面跳转的地址
		* bgUrl ： 后台回调的url，支付中心收到第三方支付系统后台通知后，会向该地址发送GET请求，并加上一串参数，返回“success”代表成功，否则失败，该参数不能带“?”
		* dialogOkUrl ： 后续扩展用，建议和 “pageUrl”参数保持一致
		* dialogFailUrl ： 后续扩展用，建议和 “pageUrl”参数保持一致
		* rate ： 汇率 ，人民币和美元之间的转换汇率；（马拉松系统特殊需求用）
		* payTypeCodeIn ： 后续扩展用，暂时可空
		* sign ： 用支付平台分配的domainSecret对所有非空的参数加密，产生的字符串

### H5支付请求接口
	接口形式：REST
	样例：
		vm.payDTO = {
            	domainCode : $stateParams.domainCode,
            	payTypeCode : $stateParams.payTypeCode,
            	requestChannel : $stateParams.requestChannel,
            	money : $stateParams.money,
            	info : $stateParams.info,
            	infoEn : $stateParams.infoEn,
            	requestKey : $stateParams.requestKey,
            	currencyType : $stateParams.currencyType,
            	pageUrl : $stateParams.pageUrl,
            	bgUrl : $stateParams.bgUrl,
            	dialogOkUrl : $stateParams.dialogOkUrl,
            	dialogFailUrl : $stateParams.dialogFailUrl,
            	rate : $stateParams.rate,
            	payTypeCodeIn : $stateParams.payTypeCodeIn,
            	sign : $stateParams.sign,
            	openid : $stateParams.openid
           };
		$.ajax({
                type:'POST',
                dataType : 'JSON',
                contentType:'application/json;charset=utf-8',
                url:"pay/api/h5/pay",
                data:JSON.stringify(vm.payDTO),
                //async:false,
                success: function(data){
                	if(data.errcode == '0'){
                		if("Wechat" == vm.payDTO.payTypeCode){
                			WeixinJSBridge.invoke(
                                    'getBrandWCPayRequest', 
                                    data.obj,
                                    function (res) {
                                        //alert(JSON.stringify(res));
                                        if (res.err_msg == "get_brand_wcpay_request:ok") {
                                            window.location.href = vm.payDTO.dialogOkUrl;
                                        }else if (res.err_msg == "get_brand_wcpay_request:fail"){
                                            alert("微信支付失败！");
                                        }
                                    }
                            );
                		}else if("Alipay" == vm.payDTO.payTypeCode){
                			angular.element(document.body).append(angular.element(data.obj));
                			vm.isPaying = false;
                		}
                    	vm.isPaying = false;
                	}else{
                		$scope.$apply(AlertService.error(data.errormsg));
                		$scope.$apply(vm.isPaying = false);
                	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	$scope.$apply(AlertService.error("支付失败，请稍后再试！"));
            		$scope.$apply(vm.isPaying = false);
                }
            });
	参数：
		* domainCode ： 支付平台分配的domainCode
		* payTypeCode ： 在支付页面上调用REST服务接口时，传递的支付渠道code，支持的值为：Wechat、Alipay、ChinapayOld、Paypal
		* requestChannel ：为了方便 业务系统扩展用，自行定义即可，也可以为空；如果当前只有一个支付渠道，为了方便后续业务扩展，建议也赋值（如：1）
		* money ： 支付的金额，单位为分
		* info ： 支付的商品信息
         * infoEn : 支付的商品信息(英文)
		* requestKey ： 业务系统自行生成，需要保证该值在业务系统中唯一；用户支付完成后，支付中心向业务系统回调时，会带上该参数，方便业务系统处理逻辑
		* currencyType ： 支付币种，支持：CNY 、 USD
		* pageUrl ： 前台返回的url，客户在支付操作完成后，页面跳转的地址
		* bgUrl ： 后台回调的url，支付中心收到第三方支付系统后台通知后，会向该地址发送GET请求，并加上一串参数，返回“success”代表成功，否则失败，该参数不能带“?”
		* dialogOkUrl ： 后续扩展用，建议和 “pageUrl”参数保持一致
		* dialogFailUrl ： 后续扩展用，建议和 “pageUrl”参数保持一致
		* rate ： 汇率 ，人民币和美元之间的转换汇率；（马拉松系统特殊需求用）
		* payTypeCodeIn ： 后续扩展用，暂时可空
		* sign ： 用支付平台分配的domainSecret对所有非空的参数加密，产生的字符串
		* openid ： 微信用户的openid（建议做cookie缓存），如果选择微信支付，必须带入该参数；如果当前没有openid参数，则：页面转向获取openid的页面
		
### H5微信支付请求openid
	接口形式：URL
	样例程序：
		if((!vm.payDTO.openid || vm.payDTO.openid=="" || vm.payDTO.openid=="undefined") && vm.isWeiXin()){
	        	var getOpenidUrl = GET_OPENID_URI;
	        	var domainCode = vm.payDTO.domainCode;
	        	var backUrl = encodeURIComponent($location.absUrl());
	        	var gotoUrl = getOpenidUrl + "?domainCode=" + domainCode + "&backUrl=" + backUrl;
	        	window.location.href = gotoUrl;
	        	return false;
        }
	参数：
		* domainCode ： 支付平台分配的domainCode
		* backUrl ： 当前页面的完整地址，做encodeURIComponent编码

### 申请支付后的回调接口
	接口形式：URL
	样例：http://mls.com/payNotify?money=%d&requestKey=%s&payTime=%d&orderSn=%s&payTypeCode=%s&payTypeName=%s&currencyType=%s&payStatus=%s&domainCode=%s&sign=%s
	参数：（暂定）
		* 地址前部分http://mls.com/payNotify 为业务系统调用支付时，传的参数“bgUrl”
		* money ： 支付的金额，单位为分
		* requestKey ： 业务系统自行生成，需要保证该值在业务系统中唯一；用户支付完成后，支付中心向业务系统回调时，会带上该参数，方便业务系统处理逻辑
		* payTime ： 支付时间
		* orderSn : 支付中心对应的唯一码，方便后续对账中
		* payTypeCode ： 支付渠道code，支持的值为：Wechat、Alipay、ChinapayOld、Paypal
		* payTypeName ： 支付渠道名称，支持的值为：微信支付、支付宝支付、银联支付、PayPal
		* currencyType ： 业务系统传递的支付币种，支持：CNY 、 USD
		* payStatus ： 未支付:NOTPAY;支付成功:SUCCESSPAY;支付失败:FAILUREPAY;NOTREFUND未退款;等待退款:WAITREFUND;退款成功:SUCCESSREFUND;退款失败:FAILUREREFUND;已关闭:CLOSED;
		* domainCode ： 支付平台分配的domainCode
		* sign ： 用支付平台分配的domainSecret对所有非空的参数加密，产生的字符串；业务系统必须验证
		
## 查询接口

### 支付查询接口
	接口形式：URL
	样例：http://api6.wwoqu.com/pay/api/private/pay/query?domainCode=test&requestKey=1464844451098&sign=07c34ea79a804e7c54b0538c45d2015e2f13814e
	参数：
		* domainCode ： 支付平台分配的domainCode
		* requestKey ： 业务系统自行生成，需要保证该值在业务系统中唯一；用户支付完成后，支付中心向业务系统回调时，会带上该参数，方便业务系统处理逻辑
		* sign ： 用支付平台分配的domainSecret对所有非空的参数加密，产生的字符串
	
	返回形式：json
	返回样例：{"errcode":"0","errormsg":"操作成功","obj":false}
	返回参数：
		* errcode ： 返回状态码，0代表成功，其他代表失败，失败原因为errormsg
		* errormsg ： 失败原因
		* obj ： 返回的业务数据：true 代表存在“支付成功”的记录  ； false 代表暂没有“支付成功”的记录
		
### 支付查询接口
	接口形式：URL
	样例：http://api6.wwoqu.com/pay/api/private/pay/queryInfo?domainCode=test&requestKey=1464844451098&sign=07c34ea79a804e7c54b0538c45d2015e2f13814e
	参数：
		* domainCode ： 支付平台分配的domainCode
		* requestKey ： 业务系统自行生成，需要保证该值在业务系统中唯一；用户支付完成后，支付中心向业务系统回调时，会带上该参数，方便业务系统处理逻辑
		* sign ： 用支付平台分配的domainSecret对所有非空的参数加密，产生的字符串
	
	返回形式：json
	返回样例：{"errcode":"0","errormsg":"操作成功","obj":{"currencyType":"CNY","payTypeCode":"Alipay","domainCode":"test","money":2,"requestKey":"88a17e1fbf275c12c77a4b88ce3bf632","payTime":1465955306000,"orderSn":"20160615094750987511","payStatus":"SUCCESSPAY","payTypeName":"支付宝支付"}}
	返回参数：
		* errcode ： 返回状态码，0代表成功，其他代表失败，失败原因为errormsg
		* errormsg ： 失败原因
		* obj ： 返回的业务数据：如果为空，则没有支付成功的记录，否则取第一条支付成功的记录，具体信息如下：
		* obj.money ： 支付的金额，单位为分
		* obj.requestKey ： 业务系统自行生成，需要保证该值在业务系统中唯一；用户支付完成后，支付中心向业务系统回调时，会带上该参数，方便业务系统处理逻辑
		* obj.payTime ： 支付时间
		* obj.orderSn : 支付中心对应的唯一码，方便后续对账中
		* obj.payTypeCode ： 支付渠道code，支持的值为：Wechat、Alipay、ChinapayOld、Paypal
		* obj.payTypeName ： 支付渠道名称，支持的值为：微信支付、支付宝支付、银联支付、PayPal
		* obj.currencyType ： 业务系统传递的支付币种，支持：CNY 、 USD
		* obj.payStatus ： 未支付:NOTPAY;支付成功:SUCCESSPAY;支付失败:FAILUREPAY;NOTREFUND未退款;等待退款:WAITREFUND;退款成功:SUCCESSREFUND;退款失败:FAILUREREFUND;已关闭:CLOSED;
		* obj.domainCode ： 支付平台分配的domainCode
	