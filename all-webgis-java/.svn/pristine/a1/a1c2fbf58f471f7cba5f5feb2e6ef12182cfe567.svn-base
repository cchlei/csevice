/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2015/11/9
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {

    var cl = require("ctool");
    var cj = require("ctooj");
    require("./css.css");

    var html = "" +
        "<form class='_sugg'>" +
        "   <h3>意见反馈</h3>" +
        "   <p style='display: none;'><em>表单项1：</em> <input class='title'/></p>" +
        "   <p><em style='display: none;'>请输入：</em> <textarea placeholder='请输入意见/反馈内容' name='sugg'></textarea></p>" +
        "   <em class='submit'>提 交</em>" +
        "   <i class='botline'></i>" +
        "</form>";



    var el = $(html).appendTo(cj.getHideBox());




    var layer_index = -1;

    el.delegate(".submit","click",function(){
    	if(el.find("textarea").val()){
    		$.post(sett.url, {content: el.find("textarea").val()})
	            .fail(function(){
	                throw "建议失败";
	            })
	            .done(function(data){
	                layer.close(layer_index);
	                if(data.result=="success"){
	   				 	layer.msg('提交成功');
	                }else{
	                	layer.msg('提交失败，请一会再试');
	                }
	            })
	        ;
    	}else{
    		layer.msg('请输入意见/反馈内容');
    	}
    })


    var def = { width: 600 };

    var sett;
    return function(config){
        if(!sett){
            sett = $.extend({},def,config);
        }

        el[0].reset();

        cl.layer(function(layer){
            layer_index = layer.open({
                type: 1,
                shade: false,
                area:["auto"],
                title: false, //不显示标题
                content: el, //捕获的元素
                cancel: function(index){
                    layer.close(index);
                    //this.content.show();
                }
            });
        });


    };
});
