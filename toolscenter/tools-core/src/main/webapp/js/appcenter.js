//引入jquery...
document.write("<script language=javascript src='/toolscenter/js/jquery-2.1.4.min.js'></script>");

//使用闭包，创建js命名空间
var NameSpace = window.NameSpace || {};
NameSpace.toolscenter = new function(){
    var self = this;
    var slide_status = {};

    self.slide_toggle = function(element_id, status){
        if(status == 0){
            $("#"+element_id).hide("slow");
        }else{
            $("#"+element_id).show("slow");
        }

    };

    self.confirm_delete = function(target_url)
    {
        var result = confirm("是否确定删除？");
        if (result == true){
            window.location.href = target_url;
        }else{
            return;
        }
    }
};
