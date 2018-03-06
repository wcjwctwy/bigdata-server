//
// layui.config({
//     base: "js/"
// }).use(['form', 'layer', 'jquery', 'laypage'], function () {
//     var form = layui.form(),
//         layer = parent.layer === undefined ? layui.layer : parent.layer,
//         laypage = layui.laypage,
//         $ = layui.jquery;
//
// });

function selectitem(){
    $.ajax({
        dataType : 'jsonp',
        url: "http://10.20.7.23:33332/analyse/stats",
        jsonp : "callback",
        data: {
            entId: entid, assetId: cusid, eventCategory: cate, eventCategoryTechnique: subcate, eventLevel: level, categoryDevice: device
            ,year:year,month:month,day:day
        },
        type: "get",
        success: function (data) {
            if (data.status == 200) {
                var statResult = data.data;
                viewStats(statResult)
            }
        }

    });

}

function viewStats(statResults){
    var total = statResults[0];
    $('#total').text(total['total']);
    var allEnts = statResults[1];
    var allCuss = statResults[2];
    var allCats = statResults[3];
    var allSubs = statResults[4];
    var allLevs = statResults[5];
    var allDevs = statResults[6];
    if(entid==null||entid==''){
        selectView(allEnts,$('#entid'));
        $('#entid-chart').show();
    }else{
        $('#entid-chart').hide();
    }
    if(cusid==null||cusid==''){
        selectView(allCuss,$('#cusid'));
        $('#cusid-chart').show();
    }else{
        $('#cusid-chart').hide();
    }
    if(cate==null||cate==''){
        selectView(allCats,$('#cate'));
        $('#cate-chart').show();
    }else{
        $('#cate-chart').hide();
    }
    if(subcate==null||subcate==''){
        selectView(allSubs,$('#subcate'));
        $('#subcate-chart').show();
    }else{
        $('#subcate-chart').hide();
    }
    if(level==null||level==''){
        selectView(allLevs,$('#level'));
        $('#level-chart').show();
    }else{
        $('#level-chart').hide();
    }
    if(device==null||device==''){
        selectView(allDevs,$('#device'));
        $('#device-chart').show();
    }else{
        $('#device-chart').hide();
    }
    showData('entid-chart',allEnts,'企业视角','所有企业日志');
    showData('cusid-chart',allCuss,'资产视角','所有资产日志');
    showData('cate-chart',allCats,'大类视角','所有大类日志');
    showData('subcate-chart',allSubs,'子类视角','所有子类日志');
    showData('level-chart',allLevs,'级别视角','所有级别日志');
    showData('device-chart',allDevs,'设备视角','所有设备日志');

}

function selectView(data,node){
    node.empty();
    node.append('<option value="">全部</option>')
    $.each(data,function (k, v) {
        node.append('<option value="'+k+'">'+k+'('+v+')</option>')
    })
}

function showData(nodeId,data,text,subtext){
    var a = new Array();
    var b = new Array();
    $.each(data, function (k, v) {
        var alen = a.length;
        a[alen] = k;
        var blen = b.length;
        var tmp = new Object();
        tmp.value = v;
        tmp.name = k;
        b[blen] = tmp;
    });
    circle(nodeId,a, b,text,subtext);

}




function circle(nodeId,legenddata,seriesdata,text,subtext) {
    var myChart = echarts.init(document.getElementById(nodeId));
    var option = {
        title : {
            text: text,
            subtext: subtext,
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:legenddata
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'日志数量',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:seriesdata
            }
        ]
    };
    myChart.setOption(option);
}

