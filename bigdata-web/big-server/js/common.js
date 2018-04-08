//==========提示弹出框=======
function tip_popup_ok(content) {
    tip_popup(content,"green","glyphicon-ok")
}
function tip_popup_failed(content) {
    tip_popup(content,"red","glyphicon-remove")
}

function tip_popup(content,color,clzss) {
    $.alert({
        title: '',
        content: content,
        autoClose: 'confirm|1000',
        type:color,
        icon:'glyphicon '+clzss,
        buttons: {
            confirm: {
                text: '确认',
                btnClass: 'btn-primary'
            }
        }
    });
}

//========================确认执行窗口=============

function confirm_execution(title,content,confirm_exec,cancel_exec){

    $.confirm({
        title: title,
        columnClass:'col-md-8 col-md-offset-2',
        content: content,
        type:'red',
        icon:'glyphicon glyphicon-question-sign',
        buttons: {
            ok: {
                text: '确认',
                btnClass: 'btn-primary',
                action: confirm_exec
            },
            cancel: {
                text: '取消',
                btnClass: 'btn-primary',
                action: cancel_exec
            }
        }
    });
}

