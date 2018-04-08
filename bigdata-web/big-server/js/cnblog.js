
function copyHtmlData(node1,node2){
    node2.html(node1.html())
}
$(document).on("click","#often_link",function (e) {
    copyHtmlData($("#sidebar_shortcut").children(".catListLink").eq(0).children("ul").eq(0),$("#often_link").siblings("ul").eq(0))
});