define(function(require, exports, module) {
	// menu 列表
    var menu_list ='<div class="panel-overlay"></div><div class="panel panel-left panel-reveal"><div class="content-block"><div class="list_menu"><ul><li><a href="/modules/home/html/index.html">首页</a></li><li><a href="/modules/propage/html/proPage.html?code=40&price=0.01&title=智能手表">智能手表</a></li>' +
		'<li><a href="/modules/propage/html/proPage.html?code=41&price=0.01&title=运动耳机">运动耳机</a></li>' +
		'<!--li><a href="/modules/propage/html/proPage.html?code=41&price=0.01&title=运动耳机">运动耳机</a></li-->' +
		'<li style="display:none;"><a href="#">论坛</a></li>' +
		'<li><a href="/modules/download/html/download.html">应用下载</a></li>' +
		'<!--li><a href="/modules/download/html/download.html">应用下载</a></li-->' +
		'</ul><p><a href="#" class="close-panel">关闭</a></p></div></div></div>';
    $("body").append(menu_list);    

	// icon 进入个人中心

	$("#psonID").click(function(){
		 window.location.href = "/modules/center/html/profile.html"
	});



})
    