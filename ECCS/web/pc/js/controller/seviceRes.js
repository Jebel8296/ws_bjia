/**
 * Created by dell on 2016/8/16.
 */
function SeviceResCtrl($scope,$rootScope ){
    function init(){
        $(".selects label").on("click",function(){
            $(".radio_img").attr("src", "images/dunchecked_gr1.png")
            $(this).find(".radio_img").attr("src", "images/dchecked_gr1.png");
        });
        $rootScope.PhotoList_Sec = [];
        var CreateList = function(Photos) {
            for (var i = 0; i < Photos.length; i++) {
                $rootScope.PhotoList_Sec.push({
                    ID: Photos[i].ID,
                    PHOTO: Photos[i].PHOTO == '' ? '' : "data:image/jpeg;base64," + Photos[i].PHOTO
                });
            }
        };
    }
init();



    $scope.ShowB = function(item,$event){
        $scope.itemN = item;
        if (item.PHOTO) {
            reviewPic();
        }else{
            choosePic();
        }
    }

    function choosePic(){
        alert("ѡ��ͼƬ")
    }
    function reviewPic(){
        alert("�鿴ͼƬ")
    }


}