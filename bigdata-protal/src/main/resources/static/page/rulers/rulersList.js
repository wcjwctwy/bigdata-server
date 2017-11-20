layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;
    //每页显示数量
    var rows = 16;
    var sum = 0;
    var page_sum = 0;
    $(function () {
        $.ajax({
            url: "/ruler/count",
            success: function (data) {
                sum = data.data;
                page_sum = sum / rows + 1;
                laypage({
                    cont: 'page'
                    , pages: page_sum //得到总页数
                    , jump: function (obj) {
                        $.ajax({
                            url: "/ruler/list",
                            type: "get",
                            data: {
                                page: obj.curr,
                                rows: rows
                            },
                            success: function (data) {
                                $(".news_content").html(renderdata(data))
                            }
                        });
                    }
                });
            }
        })
    });

    //渲染数据
    function renderdata(data) {
        var dataHtml = "";
        if (data.status == 200) {
            for (var i = 0; i < data.data.length; i++) {
                var datum = data.data[i];
                dataHtml
                    += '<tr>' +
                    '<td style="display:none;">' + datum.id + '</td>' +
                    '<td align="left">' + datum.groupName + '</td>' +
                    '<td align="left">' + datum.rulerName + '</td>' +
                    '<td align="left">' + datum.regex + '</td>' +
                    '<td align="left">' + datum.rulerContent + '</td>'
                if (datum.status == 2) {
                    dataHtml += '<td>使用中</td>';
                } else if(datum.status == 1){
                    dataHtml += '<td style="color: orange">未使用</td>';
                }else{
                    dataHtml += '<td style="color: red">已删除</td>';
                }
                dataHtml +=
                    '<td>' + datum.createdTime + '</td>' +
                    '<td>' + datum.updatedTime + '</td>' +
                    '<td>' +
                    '<a href="javascript:void(0)" class="layui-icon rulerEdit">&#xe642;</a></td>' +
                    '<td>' +
                    '<a href="javascript:void(0)" class="layui-icon rulerDel" >&#xe640;</a></td>' +
                    '</tr>'
            }
        } else {
            dataHtml = "<td colspan='8'>暂无数据！！</td>";
        }
        return dataHtml;
    }

    $(document).on('click', '.rulerEdit', function () {
        var id = $(this).parent("td").siblings("td").eq(0).text();
        // layer.msg("rulerEdit")
        layer.open({
            type: 2,
            title: '编辑规则'
            , content: '/ruler/'+id,
            area: ['600px', '720px']
        });
    });

    $(document).on('click', '.rulerDel', function () {
        var id = $(this).parent("td").siblings("td").eq(0).text();
        layer.confirm('确定删除id为' + id + '的规则吗?', {icon: 3, title: '提示'}, function (index) {
            //do something
            $.ajax({
                url: '/ruler/'+id,
                type: 'DELETE',
                success:function (data) {
                    if(data.status==200){
                        layer.msg("删除成功！");
                    }else{
                        layer.msg("删除失败！");
                    }

                },
                error:function () {
                    layer.msg("删除失败！");
                }
            });
            layer.close(index);
        });

    });
    //添加规则
    $(".newsAdd_btn").click(function () {
        layer.open({
            type: 2,
            title: '添加规则'
            , content: '/page/ruler/ruler-add.html',
            area: ['600px', '720px']
        });
    });

    //下载xml数据
    $(".downLoadXmlRulers").click(function () {
        $.ajax({
            url: "/ruler/download",
            type: "get"
        })
    });


});
