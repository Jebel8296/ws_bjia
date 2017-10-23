'use strict'
zhongmai.controller('UploaderController',function($scope, fileReader){
    $scope.onFileSelect = function(files) {
        var fileIn=files[0];
        var img = new Image();
        var fileType = fileIn.name.substring(fileIn.name.lastIndexOf(".") + 1, fileIn.name.length);
        if(fileIn.size>5242880){//单位是B，此处不允许超过5M
            alert("图片不能超过5M")
            return;
        }
        if(fileType=='JPG' || fileType=='PNG' || fileType=='JPEG ' || fileType=='jpg' || fileType=='png' || fileType=='jpeg'){


        }else{
            alert("图片格式只支持:JPG,PNG,JPEG")
            return;
        }
        fileReader.readAsDataUrl(fileIn, $scope)
            .then(function(result) {
                $scope.imageSrc = result;
                console.log(img.width);
            });
    }


});


zhongmai.factory("fileReader", function($q, $log) {



    var onLoad = function(reader, deferred, scope) {
        return function () {
            scope.$apply(function () {
                deferred.resolve(reader.result);
            });
        };
    };


    var onError = function (reader, deferred, scope) {
        return function () {
            scope.$apply(function () {
                deferred.reject(reader.result);
            });
        };
    };


    var onProgress = function(reader, scope) {
        return function (event) {
            scope.$broadcast("fileProgress",
                {
                    total: event.total,
                    loaded: event.loaded
                });
        };
    };


    var getReader = function(deferred, scope) {
        var reader = new FileReader();
        reader.onload = onLoad(reader, deferred, scope);
        reader.onerror = onError(reader, deferred, scope);
        reader.onprogress = onProgress(reader, scope);
        return reader;
    };


    var readAsDataURL = function (file, scope) {
        var deferred = $q.defer();


        var reader = getReader(deferred, scope);
        reader.readAsDataURL(file);


        return deferred.promise;
    };


    return {
        readAsDataUrl: readAsDataURL
    };
});

