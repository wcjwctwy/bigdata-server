var title,subTitle,legendData,xAxisdata,series;


function resolveData(data) {
    legendData=new Array();
    xAxisdata=[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30];
    series=new Array();
    $.each(data,function (k, v) {
        var len1 = legendData.length;
        var len2 = series.length;
        legendData[len1]=k;
        var obj = new Object();
        obj.name=k;
        obj.type='line';
        obj.data=v;

        series[len2]=obj
    })
}

$(function () {

    resolveData(timeLineStatResultsByMonth);
    showData("11月日志采集量",'折线图',legendData,xAxisdata,series);
});


function showData(title,subTitle,legendData,xAxisdata,series){
    var myChart = echarts.init(document.getElementById('main'));
    legendData.splice(11,0,'');
    var option = {
        title : {
            text: title,
            subtext: subTitle,
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            x:'center',
            data:legendData
            // data:['最高气温','最低气温']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data :xAxisdata
                // data : ['周一','周二','周三','周四','周五','周六','周日']
            }
        ],
        yAxis : [
            {
                type : 'value'
                // axisLabel : {
                //     formatter: '{value} °C'
                // }
            }
        ],
        series :series
        // series : [
        //     {
        //         name:'最高气温',
        //         type:'line',
        //         data:[11, 11, 15, 13, 12, 13, 10],
        //         markPoint : {
        //             data : [
        //                 {type : 'max', name: '最大值'},
        //                 {type : 'min', name: '最小值'}
        //             ]
        //         },
        //         markLine : {
        //             data : [
        //                 {type : 'average', name: '平均值'}
        //             ]
        //         }
        //     },
        //     {
        //         name:'最低气温',
        //         type:'line',
        //         data:[1, -2, 2, 5, 3, 2, 0],
        //         markPoint : {
        //             data : [
        //                 {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
        //             ]
        //         },
        //         markLine : {
        //             data : [
        //                 {type : 'average', name : '平均值'}
        //             ]
        //         }
        //     }
        // ]
    };
    myChart.setOption(option);
}