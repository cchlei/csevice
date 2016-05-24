/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2016/2/25
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    var u = require("_/tr_util");
    var co = require("__/js/comm");
    var ht = $("html");
    require("_/avalon.min");
    var SR = require("$/splitReq");
    
    var para = u.cl.urlParas();

    var vm;
    $(function(){

        //加载专题数据
        vm = avalon.define({
            $id:"themes",
            default_img:co.theme_thumb_default,
            topictitle:function(){
            	if(para.type === "0"){
            		return "<i class=ico_closetheme></i> 	私密专题";
            	}else if(para.type === undefined){
            		return "<i class=ico_alltheme></i> 		所有专题"
            	}else if(para.type=="1"){
            		return "<i class=ico_opentheme></i> 	公开专题"
            	}else{
            		return "其他专题";
            	}
            }(),
            addthemeurl:function(){
                if(para.type === "0"){
                    return "<a ms-attr-href=ctx+'/topic/toadd?open=0' class='add'>添加专题</a>";
                }else{
                    return "<a ms-attr-href=ctx+'/topic/toadd?open=1' class='add'>添加专题</a>"
                }
            }(),
            object:[],
            //图片文件路径前缀
            ibpath:co.media_image_path,
            dele:function(el){
                u.confirm("确定删除专题",function(cancel){
                    if(!cancel){
                        u.ajax(
                            u.pagevar("theme_del","../data/success.json"),
                            {id:el.id},
                            function(t){
                                if(t.vl()){
                                    vm.object.remove(el);
                                    u.msg("删除成功");
                                    parent.fresh_topic();

                                }
                            }
                        )

                    }
                })
            }
        })


        var $main = $(".main");

        var t = false;
        var srddd = new SR({
            container:"#themepage",
            rows:10,
            firstReqAuto:false,
            reqPath:u.pagevar("theme_list","../data/theme_list.json"),
            onRece:function(d){
                //完善错误提示
            },
            onReq:function(p){
                p.shareflag = para.type;
            },
            onData:function(data){
                var themes_list =[]
                $.each(data.rows,function(k,el){
                    themes_list.push(el);
                })
    			ht.addClass("no_cl_loading");

                vm.object.pushArray(themes_list);
                if($main.height()>=$main.prop("scrollHeight")){
                    srddd.next();
                }
            }
        });

        srddd.doo();



        $main.scrollToEdge(function(s){
            if(s == 1){
                srddd.next();
            }
        })

        avalon.scan();

        $(document).delegate(".theme_grid a","click",function(){
            var aa = $(this);
            co.add_formpara_to_href(aa);
        });


        $("a.add").attr("href",function(i,tx){
            var para = u.cl.urlParas(tx);
            var href = tx.split("?").shift();
            para.from = location.href;
            href  = href + "?" + $.param(para);
            return href;
        });


    })




    return {};
});