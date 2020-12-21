function SetWinHeight(obj) {
    var win = obj;
    if (document.getElementById) {
        if (win && !window.opera) {
            if (win.contentDocument && win.contentDocument.body.offsetHeight)
                win.height = win.contentDocument.body.offsetHeight;
            else if (win.Document && win.Document.body.scrollHeight)
                win.height = win.Document.body.scrollHeight;
        }
    }
}

function hi(){
    var hidde=document.getElementById("hi");
    if(hidde.value==0){
        hidde.value=1;
    }else {
        hidde.value=0;
    }
}

function checknull() {
    var check = document.getElementsByName("chbox");
    var radio = document.getElementsByName("radio");
    var count = 0;
    if (radio.length != 0) {
        for (var i = 0; i < radio.length; i++) {
            if(radio[i].checked==true){
                count++;
            }

        }
        if(count==0){
            alert("请先选择选项");
            return false;
        }
    }else {


        for (var i = 0; i < check.length; i++) {
            if(check[i].checked==true){
                count++;
            }

        }
        if(count==0){
            alert("请先选择选项");
            return false;
        }
    }
}

function check() {
    var count = 0;
    $(".bb").each(function() {
        if ($(this).val() == '') {
            count++;
        }
    })
    if (count > 0) {
        alert("表单有内容为空");
        return false;
    }
    var c=document.getElementsByName("option");
    var b,i,j;
    for(i=0;i<c.length;i++)
    {
        b=c[i].value;
        for(j=i+1;j<c.length;j++)
        {
            if(b==c[j].value)
            {
                alert("有相同选项");
                return false;
            }
        }
    }

}

$(function() {
    $("#getStr").click(function() {
        $str = '';
        $str += "<tr>";
        $str += "<td></td>";
        $str += "<td><input type='text' class='form-control bb' name='option' /></td>";
        $str += "<td onClick='getDel(this)'><a>删除</a></td>";
        $str += "</tr>";
        $("#addTr").append($str);
        var obj = parent.document.getElementById("win"); // 取得父页面IFrame对象
        obj.height = window.document.body.scrollHeight + 40; // 调整父页面中IFrame的高度为此页面的高度
    });
});

function getDel(k) {
    $(k).parent().remove();
}