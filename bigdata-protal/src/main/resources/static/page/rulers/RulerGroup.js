var sum = 0; //总数据量
var nums = 17; //每页出现的数据量
var page_sum = 0;
var categoryId = "";
var groupId="";

layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;
    categoryId = $(".selectCategory").eq(0).siblings("span").text();

    //*************************分组管理**********************************
    $(".add-group").click(function () {
        layer.open({
            type: 2,
            title: '添加规则'
            , content: '/page/rulers/group.html',
            area: ['600px', '720px']
        });

    });
 $(".delete-group").click(function () {
        layer.open({
            title: '删除规则'
            , content: '确定删除' + groupId + '这个组吗？',
           //删除组
        });

    });

    //首次加载页面
    $(function () {
        // getdata();
    });
    //绑定选择分组
    $(".selectGroup").click(function () {
        var $this = $(this);
        $this.parent("td").addClass("active");
        $this.parents("tr").siblings("tr").children("td").removeClass("active");
        groupId = $this.siblings("span").eq(0).text();
        // getdata();
    });

    function showalldata() {
        if (this.offsetWidth < this.scrollWidth) {
            var that = this;
            var text = $(this).text();
            layer.tips(text, that, {
                tips: 1,
                time: 2000
            });
        }
    };

    //获取大类的子类数据
    function getdata() {
        $.ajax({
            url: "/ruler/classify/subcount",
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
                            url: "/ruler/classify/sub",
                            type: "get",
                            data: {
                                categoryId: categoryId,
                                page: obj.curr,
                                rows: nums
                            },
                            success: function (data) {
                                $("#sub_categories").html(render(data))
                            }
                        });
                    }
                });
            }
        });
    }


    //渲染数据
    function render(data) {
        var htmlData = "";
        if (data.status == 200) {
            for (var i = 0; i < data.data.length; i++) {
                var datum = data.data[i];
                htmlData += "<tr>"
                    // + '<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose"></td>'
                    + '<td align="left">' + datum.name + '</td>'
                    + '<td align="left">' + datum.zhName + '</td>';
                if (datum.status == 0) {
                    htmlData += '<td>使用中</td>'
                } else {
                    htmlData += '<td type="color:red">未使用</td>'
                }


                htmlData += '<td align="left">' + datum.description + '</td>'
                    + '<td>' + datum.createtime + '</td>'
                    + '<td>' + datum.updatetime + '</td>'+
                    '<td>' +
                    '<a href="javascript:void(0)" class="layui-icon">&#xe642;</a></td>' +
                    '<td>' +
                    '<a href="javascript:void(0)" class="layui-icon">&#xe640;</a></td>'+
                    '</tr>'
            }
        } else {
            htmlData = '<tr><td colspan="8">暂无数据!</td></tr>'
        }
        return htmlData;
    }


});





