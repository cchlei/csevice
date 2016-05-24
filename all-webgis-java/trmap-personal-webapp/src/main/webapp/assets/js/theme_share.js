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

    ht.addClass("no_cl_loading");
    require.async("__/js/theme_list_left");
    var themeshare;
    var history_array = [];
    $(function(){


        //加载专题分享
        themeshare = avalon.define({
            $id :"theme_share",
            object:[],
            /*专题默认图片*/
            default_img:co.theme_thumb_default,
            /*头像默认图片*/
            default_headimg:co.theme_thumb_headimg,
        	//图片文件路径前缀
            ibpath:co.media_image_path,
            current_index:-1,
            history_dot:history_array,
            history_dot_click:function(el,index){
                themeshare.object = el;
                themeshare.current_index = index;
            },
            change_dozen:function(){
                themeshare.current_index = -1;
                history_array.push(current_data);

                if(history_array.length>5){
                    history_array.shift();
                }

                get_data();

            },

            click_iscollect:function(el){
                if(el.iscollect){
                    //取消收藏
                    u.ajax(
                        u.pagevar("theme_share","../data/success.json"),{id:el.id,collect:false},function(t){
                            if(t.vl()){
                                el.iscollect = false;
                                el.collect--;
                                u.msg("取消收藏")
                            }
                        }
                    )

                }else{
                    //添加收藏
                    u.ajax(
                        u.pagevar("theme_share","../data/success.json"),{id:el.id,collect:true},function(t){
                            if(t.vl()){
                                el.iscollect = true;
                                el.collect++;
                                u.msg("添加收藏")
                            }
                        }
                    )

                }



            }

        })

        var current_data;

        function get_data(){
            u.ajax(
                u.pagevar("theme_share_list","../data/theme_share_list.json"),{},function(t,data){
                    if(t.vl()){
                        //var themeshare_list = [];
                        //$.each(data.rows,function(k,el){
                        //    themeshare_list.push(el)
                        //})
                        //themeshare.object = themeshare_list;

                        themeshare.object   =       data.rows;
                        current_data        =       data.rows;
                    }
                }
            )
        }

        get_data();

        avalon.scan();


        $(document).delegate(".theme_grid a","click",function(){
            var aa = $(this);
            aa.attr("href",function(i,tx){
                var para = u.cl.urlParas(tx);
                para.from = co.getPath(top.location.href);
                var ps = $.param(para);
                return tx.split("?").shift() + "?" + ps;
            });
        });

    })

    return {};
});