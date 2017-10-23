function Scroll($scope){
    function init(){
        //焦点图start
        var $banner=$('.banner');
        var $banner_ul=$('.bannerImg');
        var $btn=$('.bannerBtn');
        var $btn_a=$btn.find('a')
        var v_width=$banner.width();
        var times = 5000;
        $banner_ul.find( 'img' ).width( v_width );


        var page=1;

        var timer=null;
        var btnClass=null;

        var page_count=$banner_ul.find('li').length;//把这个值赋给小圆点的个数

        var banner_cir="<li class='selected' href='#'><a></a></li>";
        for(var i=1;i<page_count;i++){
            //动态添加小圆点
            banner_cir+="<li><a href='#'></a></li>";
        }
        $('.bannerCircle').append(banner_cir);

        var cirLeft=$('.bannerCircle').width()*(-0.5);
        $('.bannerCircle').css({'marginLeft':cirLeft});

        $banner_ul.width(page_count*v_width);

        function move(obj,classname){
            //手动及自动播放
            if(!$banner_ul.is(':animated')){
                if(classname=='prevBtn'){
                    if(page==1){
                        $banner_ul.animate({left:-v_width*(page_count-1)});
                        page=page_count;
                        cirMove();
                    }
                    else{
                        $banner_ul.animate({left:'+='+v_width},"slow");
                        page--;
                        cirMove();
                    }
                }
                else{
                    if(page==page_count){
                        $banner_ul.animate({left:0});
                        page=1;
                        cirMove();
                    }else{
                        $banner_ul.animate({left:'-='+v_width},"slow");
                        page++;
                        cirMove();
                    }
                }
            }
        }

        function cirMove(){
            //检测page的值，使当前的page与selected的小圆点一致
            $('.bannerCircle li').eq(page-1).addClass('selected').siblings().removeClass('selected');
        }
        $banner.mouseover(function(){
            $btn.css({'display':'block'});
            clearInterval(timer);
        }).mouseout(function(){
            $btn.css({'display':'none'});
            clearInterval(timer);
            timer=setInterval(move,times);
        }).trigger("mouseout");//激活自动播放

        $btn_a.mouseover(function(){
            //实现透明渐变，阻止冒泡
            $(this).animate({opacity:0.6},'fast');
            $btn.css({'display':'block'});
            return false;
        }).mouseleave(function(){
            $(this).animate({opacity:0.3},'fast');
            $btn.css({'display':'none'});
            return false;
        }).click(function(){
            //手动点击清除计时器
            btnClass=this.className;
            clearInterval(timer);
            timer=setInterval(move,times);
            move($(this),this.className);
        });
        $('.bannerCircle li').on('click',function(){
            var index=$('.bannerCircle li').index(this);
            $banner_ul.animate({left:-v_width*index},'slow');
            page=index+1;
            cirMove();
        });
        //焦点图end
    }

    init();
}