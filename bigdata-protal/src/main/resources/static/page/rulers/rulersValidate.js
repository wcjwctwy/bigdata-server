//********************绑定事件************
//没有操作的情况下 按钮不可用
var conditionBtn=$("#mid_button").children("button").eq(0);
var rulerContentBtn=$("#mid_button").children("button").eq(1);
var translationBtn=$("#mid_button").children("button").eq(2);
var allBtn=$("#mid_button").children("button").eq(3);



$(function () {
    $("#translation").bind(' input propertychange ',function () {
        var $this = $(this);

        if($this.val()==null||$this.val()==""){
            //按钮不可用
            translationBtn.attr("disabled","disabled");
            translationBtn.addClass("layui-btn-disabled");

            $("#analyseCommit").attr("disabled",true);
            $("#analyseCommit").addClass("layui-btn-disabled");
        }else{
            translationBtn.removeAttr("disabled");
            translationBtn.removeClass("layui-btn-disabled");
            if(isEmpty($("#condition"))||isEmpty($("#rawEvent"))||isEmpty($("#rulerContent"))){
                $("#analyseCommit").attr("disabled",true);
            }else{
                $("#analyseCommit").attr("disabled",false);
                $("#analyseCommit").removeClass("layui-btn-disabled");
            }
        }
    });

    $("#rawEvent").bind(' input propertychange ',function () {
        var $this = $(this);

        if($this.val()==null||$this.val()==""){
            $("#analyseCommit").attr("disabled",true);
            $("#analyseCommit").addClass("layui-btn-disabled");
        }else{
            if(isEmpty($("#condition"))||isEmpty($("#translation"))||isEmpty($("#rulerContent"))){
                $("#analyseCommit").attr("disabled",true);
            }else{
                $("#analyseCommit").attr("disabled",false);
                $("#analyseCommit").removeClass("layui-btn-disabled");
            }
        }
    });
    //当condition中有值时正则入库按钮可用
    $("#condition").bind(' input propertychange ',function () {
        var $this = $(this);

        if($this.val()==null||$this.val()==""){
            //按钮不可用
            conditionBtn.attr("disabled","disabled");
            conditionBtn.addClass("layui-btn-disabled");

            $("#analyseCommit").attr("disabled",true);
            $("#analyseCommit").addClass("layui-btn-disabled");
        }else{
            conditionBtn.removeAttr("disabled");
            conditionBtn.removeClass("layui-btn-disabled");
            if(isEmpty($("#translation"))||isEmpty($("#rawEvent"))||isEmpty($("#rulerContent"))){
                $("#analyseCommit").attr("disabled",true);
            }else{
                $("#analyseCommit").attr("disabled",false);
                $("#analyseCommit").removeClass("layui-btn-disabled");
            }
        }
    });
    $("#rulerContent").bind(' input propertychange ',function () {
        var $this = $(this);

        if($this.val()==null||$this.val()==""){
            //按钮不可用
            rulerContentBtn.attr("disabled","disabled");
            rulerContentBtn.addClass("layui-btn-disabled");

            $("#analyseCommit").attr("disabled",true);
            $("#analyseCommit").addClass("layui-btn-disabled");
        }else{
            rulerContentBtn.removeAttr("disabled");
            rulerContentBtn.removeClass("layui-btn-disabled");
            if(isEmpty($("#translation"))||isEmpty($("#rawEvent"))||isEmpty($("#condition"))){
                $("#analyseCommit").attr("disabled",true);
            }else{
                $("#analyseCommit").attr("disabled",false);
                $("#analyseCommit").removeClass("layui-btn-disabled");
            }
        }
    });
});

function isEmpty(node) {
    if(node.val()==null||node.val()==""){
        return true;
    }else{
        return false;
    }
}



var rulerId = "";
var fileName = "";
var rulerName = "";
var layedit = null;

function selectRuler() {
    var $this = $(this);
    rulerId=$this.siblings("span").eq(0).text();
    fileName = $this.parent("div").eq(0).siblings("h2").eq(0).text();
    fileName = fileName.split("xml")[0] + "xml";
    $this.parent("div").eq(0).css("background-color", "#3B9EFF");
    $this.parent("div").eq(0).siblings(".layui-colla-content").css("background-color", "#fff");
    rulerName = $this.text();
    $.ajax({
        url: "/ruler/content",

        data:{
            rulerId:rulerId
        },
        success: function (data) {
            if(data.data==null||data.data==""){
                alert("没有数据，出错了！")
            }
            var conditionText = data.data.regex;
            $("#condition").val(conditionText);
            var jsonMsg = JSON.parse(data.msg)
            $("#translation").val(JSON.stringify(jsonMsg, null, "\t"));
            $("#rawEvent").val("");
            $("#result").val("");
            $("#rightResult").val("");
            $("#rulerContent").val(JSON.stringify(JSON.parse(data.data.rulerContent), null, "\t"));
            $("#analyseCommit").attr("disabled",true);
            $("#analyseCommit").addClass("layui-btn-disabled");
            $("#mid_button").children("button").attr("disabled",true);
            $("#mid_button").children("button").addClass("layui-btn-disabled");
        }
    });
}

//提交解析
function analyseCommit(){
    var rawEvent=$("#rawEvent").val();
    var condition=$("#condition").val();
    $.ajax({
        url: "/ruler/analyse",
        type:"post",
        data:{
            translation: $("#translation").val(),
            rulerContent:$("#rulerContent").val(),
            condition:condition,
            rawEvent:rawEvent,
            rulerId:rulerId
        },
        success: function (data) {
            if(data.status==200){
                var json = data.data;
                var result = "";
                for (var key in json) {
                    result = result + key + ':' + json[key] + "\n";
                }
                $("#result").val(result)
            }else{
                $("#result").val(data.msg)
                // alert(data.msg)
            }

        }
    });
}

//重置规则
function resetRulers(){
    $("#condition").val("");
    $("#translation").val("");
    $("#rulerContent").val("");
    $("#rawEvent").val("");
    $("#result").val("");
    $("#rightResult").val("");
    $("#analyseCommit").attr("disabled",true);
    $("#analyseCommit").addClass("layui-btn-disabled");
    $("#mid_button").children("button").attr("disabled",true);
    $("#mid_button").children("button").addClass("layui-btn-disabled");
}

//存储正则
function regexSave(){
    $.ajax({
        url:"/ruler/regex",
        type:"post",
        data:{
            condition:$("#condition").val(),
            rulerId:rulerId
        },
        success:function (data) {
            if(data.status==200){
                alert("正则入库成功！");
            }else{
                alert("正则入库失败！");
            }
        },
        error:function () {
            alert("正则入库失败！");
        }
    })
}
//规则内容入库
function rulerContentSave(){
    $.ajax({
        url:"/ruler/content",
        type:"post",
        data:{
            rulerContent:$("#rulerContent").val(),
            rulerId:rulerId
        },
        success:function (data) {
            if(data.status==200){
                alert("内容入库成功！");
            }else{
                alert(data.msg);
            }
        },
        error:function () {
            alert("内容入库失败！");
        }
    })
}

//translation入库
function rulerTranslationSave(){
    $.ajax({
        url:"/ruler/translation",
        type:"put",
        data:{
            translation: $("#translation").val(),
            rulerId:rulerId
        },
        success:function (data) {
            if(data.status==200){
                alert("translation入库成功！");
            }else{
                alert(data.msg);
            }
        },
        error:function () {
            alert("translation入库失败！");
        }
    })
}