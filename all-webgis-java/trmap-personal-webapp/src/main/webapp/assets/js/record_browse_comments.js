/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2016/2/25
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    require("_/avalon.min");
    var co = require("__/js/comm");
    var u = require("_/tr_util");
    var ht = $("html");


    ht.addClass("no_cl_loading");

    var isopen;
    var browse;
    $(function(){

        browse=avalon.define({
            $id:"record_browse",
            data:[]
        })

        u.ajax(
            u.pagevar("recode_brows","../data/recode_brows.json"),{},function(t,data){
                if(t.vl()){
                    browse.data = data;
                }
            }
        )



        /**
         * 附件管理
         * @type {{init: Function}}
         */
        var filemgr = {
            init:function(){
                var m = this;
                m.el = $(".uploadthing");

                m.vm = avalon.define({
                    $id:"file_man",
                    list:[],
                    //图片文件路径前缀
                    ibpath:co.media_image_path
                });

                u.ajax(
                    u.pagevar("imgs_data","../data/imgs_data.json"),{},function(t,data){
                        if(t.vl()){
                            $.each(data.rows,function(k,el){
                                var f = {
                                    id:el.id,
                                    name:el.name
                                };
                                if(co.is_img(el.ext)){
                                    f.thumb = co.getThumbPath(120) + el.id;
                                }else{
                                    f.thumb = co.filetype_thumb[el.ext] || co.filetype_thumb["default"];
                                }
                                m.vm.list.push(f);
                            })

                        }
                    }
                )


            },
            appendFile:function(file){
                var m = this;
                m.vm.list.push(file);
            }
        };

        filemgr.init();


        avalon.scan();
    })

    return {};
});









