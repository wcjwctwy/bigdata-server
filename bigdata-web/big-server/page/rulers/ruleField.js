var sum = 0; //总数据量
var nums = 17; //每页出现的数据量
var page_sum = 0;
var categoryId = "";
var categoryName = "";
var isParent=true;
var isUpdate=false;

layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;
    categoryId = $(".selectCategory").eq(0).siblings("span").text();

    //首次加载页面
    $(function () {
        // getdata();
        getCategory()
    });
    //绑定选择大类事件 $(document)on('click', '.rulerEdit', function () {
    $(document).on("click", ".selectCategory", function () {
        var $this = $(this);
        $this.parent("td").addClass("active");
        $this.parents("tr").siblings("tr").children("td").removeClass("active");
        categoryId = $this.siblings("span").eq(0).text();
        categoryName= $this.children("span").eq(0).text();
        getdata();
    });

/*======================字段类别加载==================*/
    //加载大类
    function getCategory() {
        $.ajax({
            url: dataUrl + "/field/category",
            type: "get",
            success: function (data) {
                $("#fieldCategory").html(renderCategory(data))
            }
        })

    }

    //渲染大类数据
    function renderCategory(data) {
        var htmlData = "";
        if (data.status == 200) {
            for (var i = 0; i < data.data.length; i++) {
                var ruleField = data.data[i];
                htmlData += "<tr>"
                    + '<td>' +
                    '<a href="javascript:void(0)" class="selectCategory">' +
                    '<span>' + ruleField.fieldName + '</span></a>' +
                    '<span style="display: none">' + ruleField.id + '</span></td>';
            }
        } else {
            htmlData = '<tr><td colspan="8">暂无数据!</td></tr>'
        }
        return htmlData;
    }
/*======================字段信息加载==================*/
    //获取大类的子类数据
    function getdata() {
        $.ajax({
            url: dataUrl + "/field/category/count",
            data: {
                categoryId: categoryId
            },
            type: "get",
            success: function (data) {
                sum = data.data;
                page_sum = sum / nums + 1;
                laypage({
                    cont: 'page'
                    , pages: page_sum //得到总页数
                    , jump: function (obj) {
                        $.ajax({
                            url: dataUrl + "/field/category/sub",
                            type: "get",
                            data: {
                                categoryId: categoryId,
                                page: obj.curr,
                                rows: nums
                            },
                            success: function (data) {
                                $("#fieldInfo").html(render(data))
                            }
                        });
                    }
                });
            }
        });
    }


    //渲染子类数据
    function render(data) {
        var htmlData = "";
        if (data.status == 200) {
            for (var i = 0; i < data.data.length; i++) {
                var datum = data.data[i];
                htmlData += "<tr>"
                    // + '<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose"></td>'
                    + '<td align="left" style="display: none">' + datum.id + '</td>'
                    + '<td align="left">' + datum.fieldName + '</td>'
                    + '<td align="left">' + datum.fieldType + '</td>';
                if (datum.status == 2) {
                    htmlData += '<td>使用中</td>';
                } else if (datum.status == 1) {
                    htmlData += '<td style="color: orange">未使用</td>';
                } else {
                    htmlData += '<td style="color: red">待删除</td>';
                }


                htmlData += '<td align="left">' + datum.fieldDesc + '</td>'
                    + '<td>' + datum.createdTime + '</td>'
                    + '<td>' + datum.updatedTime + '</td>' +
                    '<td>' +
                    '<a href="javascript:void(0)" class="layui-icon edit_field">&#xe642;</a></td>' +
                    '<td>' +
                    '<a href="javascript:void(0)" class="layui-icon del_field">&#xe640;</a></td>' +
                    '</tr>'
            }
        } else {
            htmlData = '<tr><td colspan="8">暂无数据!</td></tr>'
        }
        return htmlData;
    }



/*======================字段类别操作============================*/

function modalOption(header){
    $("#myModal").modal("show");
    $("#myModalLabel").text(header);
    $("#ruleCategory_p").hide();
    $("#status_p").hide();
}
    //增加字段类别
    $(document).on("click", ".add_parent_field", function (e) {
        isParent=true;
        $("#parentId").val(0);
        modalOption("添加字段类别")

    });
    //修改字段类别
    $(document).on("click", ".edit_parent_field", function (e) {
        isParent=true;
        modalOption("修改字段类别")
    });
    //删除字段类别
    $(document).on("click", ".del_parent_field", function (e) {
        isParent=true;
        if(categoryName){
            var msg = '您真的确定要删除'+categoryName+'吗？\n若为类别那么其包含的字段都会被删除？\n请确认！';
            if (confirm(msg) === true){
                submit_form(dataUrl+"/field/category","id="+categoryId+"&isParent="+isParent,"DELETE",getCategory);
                $("#fieldInfo").html('<tr><td colspan="8">选择一个字段类别！！！！</td></tr>')
            }
        }else {
            layer.msg("请选择需要删除的类别！")
        }

    });

/*======================字段操作============================*/
//增加字段类别
    $(document).on("click", ".add_field", function (e) {
        if(categoryName) {
            $("#parentId").append("<option value='"+categoryId+"'>测试5</option>");
            $("#parentId").val(categoryId);
            window.console.info($("#parentId").val(),categoryId);
            modalOption("添加字段到" + categoryName);
            isParent=false
        }else {
            layer.msg("请选择一个类别！")
        }
    });
    //修改字段类别
    $(document).on("click", ".edit_field", function (e) {
        isParent=false;

        $("#myModal").modal("show");
    });
    //删除字段类别
    $(document).on("click", ".del_field", function (e) {
        isParent=false;
        var msg = '您真的确定要删除'+'d'+'吗？\n若为类别那么其包含的字段都会被删除？\n请确认！';
        if (confirm(msg) === true){


        }
    });
/*====================提交表单============================*/
$(document).on("click",".submit_btn",function (e) {
    if(isParent){
        submit_form(dataUrl+"/field",$("#myForm").serialize(),"POST",getCategory)
    }else{
        submit_form(dataUrl+"/field",$("#myForm").serialize(),"POST",getdata)
    }

});
function submit_form(url,data,type,option){
    $.ajax({
        url:url+'?'+data,
        type:type,
        success:function (data) {
            if(data.status==200){
                layer.msg("操作成功！");
                $("#myModal").modal("hide");
                option();
            }else{
                layer.msg("操作失败！")
            }
        },
        error:function () {
            layer.msg("操作失败！")
        }
    });
}
});





