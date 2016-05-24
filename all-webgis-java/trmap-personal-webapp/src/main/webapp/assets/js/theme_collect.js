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
    require.async("__/js/theme_list_left");
    ht.addClass("no_cl_loading");

    var mycollect

    $(function(){

        //加载我的收藏
        var $main = $(".main");
        mycollect = avalon.define({
            $id:'mycollect',
            collentdata:[],
            /*专题默认图片*/
            default_img:co.theme_thumb_default,
            /*头像默认图片*/
            default_headimg:co.theme_thumb_headimg,
            //图片文件路径前缀
            ibpath:co.media_image_path,
            total:'',
            click_qxcollect:function(el){
                if(el.iscollect){
                    u.ajax(
                        u.pagevar("theme_collect_share","../data/success.json"),{id:el.topic.id,collect:false},function(t){
                            if(t.vl){
                                el.iscollect = false;
                                el.collect--;
                                mycollect.total--;
                                u.msg("取消收藏")
                            }
                        }
                    )
                }else{
                    u.ajax(
                        u.pagevar("theme_collect_share","../data/success.json"),{id:el.topic.id,collect:true},function(t){
                            if(t.vl){
                                el.iscollect = true;
                                el.collect++;
                                mycollect.total++;
                                u.msg("收藏成功")
                            }
                        }
                    )
                }
            }

        })
        var sr = new SR({
            container:"#theme_collect_page",
            rows:6,
            firstReqAuto:false,
            reqPath:u.pagevar("theme_collect_list","../data/theme_collect_list.json"),
            onRece:function(d){
                //完善错误提示
            },
            onData:function(data){
                var theme_collect_list=[]
                mycollect.total = data.total;
                $.each(data.rows,function(k,el){
                    theme_collect_list.push(el);
                })
                mycollect.collentdata.pushArray(theme_collect_list);

                if($main.height()>=$main.prop("scrollHeight")){
                    sr.next();
                }
            }
        });
        sr.doo();
        $main.scrollToEdge(function(s){
            if(s == 1){
                sr.next();
            }
        })

        avalon.scan();


        $(document).delegate(".theme_grid a","click",function(){
            var aa = $(this);
            co.add_formpara_to_href(aa);
        });




    })

    return {};
});