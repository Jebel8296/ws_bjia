/**
 * Created by ZMTX on 2017/5/24.
 */
$(function(){

    var obj={
        logistics:"EMS(免费)",
        sendTime:"周一至周五"
    }
    jsonObj=JSON.stringify(obj)
    window.localStorage.setItem("distributionObj",jsonObj);
    var jsonObj=null;
   // alert("1111")
    $(".options span").click(function(){

        if($(this).hasClass("logistics_blue")){
            $(this).siblings().removeClass("logistics_blue");
            $(this).siblings().removeClass("cur");
            obj.logistics=$(this).html();
        }else{
            $(this).addClass("logistics_blue").siblings().removeClass("logistics_blue");
            $(this).addClass("cur").siblings().removeClass("cur");
            obj.logistics=$(this).html();
        }
        jsonObj=JSON.stringify(obj)
        window.localStorage.setItem("distributionObj",jsonObj);
        console.log(localStorage.getItem("distributionObj"));
    });

    $(".delivery_time span").click(function(){
        if($(this).hasClass("delivery_time_default")){
            $(this).siblings().removeClass("delivery_time_default")
            $(this).siblings().removeClass("cur");
            obj.sendTime=$(this).html()
        }else{
            $(this).addClass("delivery_time_default").siblings().removeClass("delivery_time_default")
            $(this).addClass("cur").siblings().removeClass("cur");
            obj.sendTime=$(this).html()
        }
        jsonObj=JSON.stringify(obj)
        window.localStorage.setItem("distributionObj",jsonObj);
        console.log(localStorage.getItem("distributionObj"));

    })
    console.log(obj);



});