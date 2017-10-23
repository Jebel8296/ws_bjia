'use strict'
zhongmai.controller('HomeCtrl', function ($scope, $rootScope, $http, localStorageService, $location, $stateParams, $state) {
    var chart = echarts.init(document.getElementById('main'));
    var option = {
        title: {
            text: 'ECharts测试'
        },
        tooltip: {},
        legend: {
            data: ['销量']
        },
        xAxis: {
            data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
        }]
    };
    chart.setOption(option);
})
