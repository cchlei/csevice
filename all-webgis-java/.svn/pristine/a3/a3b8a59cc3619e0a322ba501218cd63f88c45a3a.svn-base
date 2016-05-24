/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2016/2/26
 * Time: 13:47
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {

    var ht = $("html");
    var u = require("_/tr_util");

    var csso = u.cj.getCJObj();



    var ex = {

        theme_thumb_default: "__/images/illu1.png".p(),

        /*默认头像*/
        theme_thumb_headimg: "__/images/thumb_headimg.jpg".p(),

        /**
         * 图片资源路径前缀
         */
        media_image_path:"http://oss.trmap.cn/rs/download/",


        _media_image_thumb:"http://oss.trmap.cn/thumb/{w}_{h}/",

        getThumbPath:function(w,h){
            if(h === undefined){
                h = w;
            }
            return this._media_image_thumb.replace(/\{w\}/g,w).replace(/\{h\}/g,h);
        },


        /**
         * 当前页面的路径，包括hash和para
         * @param location
         * @returns {T}
         */
        getPath:function(href){
            var ac = arguments.callee;
            if(!ac.cache){
                ac.cache = {};
            }
            if(!href){
                href = location.href;
            }

            if(ac.cache[href]){
                return ac.cache[href];
            }

            var path = href.split(/\/\//).join("").replace(/[^\/]+/,"");

            ac.cache[href] = path;

            return path;
        },

        /**
         * 路由的分割符号
         */
        route_spliter:"|",


        /**
         * 获取缩略图地址
         * @param file_id
         * @param w
         * @param h
         * @returns {*}
         */
        getThumb:function(file_id,w,h,def_img){
            if(!file_id){
                return def_img || ex.theme_thumb_default;
            }
            return ex.getThumbPath(w,h) + file_id;
        },


        /**
         * 自动完成地址
         */
        poiAutoCompleteUrl:"http://117.34.70.41:8180/poi/search?query=%s",
        poiAutoCompleteUrl_2:"http://117.34.70.41:8180/poi/search",


        /**
         * 获取pop单行形式的
         * @param text
         * @returns {*}
         */
        get_line_pop_html:function(text){
            var $pop = $("<div><div class='pop_label'></div></div>");
            $pop.find(">*").text(text).append("<i></i>");
            return $pop.html();
        },

        /**
         * 为a的href附加当前页面的信息
         */
        add_formpara_to_href:function($a){
            $a.attr("href",function(i,tx){
                var para = u.cl.urlParas(tx);
                para.from = co.getPath(top.location.href);
                var ps = $.param(para);
                return tx.split("?").shift() + "?" + ps;
            });
            return $a;
        }



    }

    /**
     * 懒执行
     * 某时间内，多次执行，只有最后一个生效
     */
    Function.prototype.lazyCall = function(dura,scope,para_array){
        var f = this;
        f.killDelayCall();
        f.delayCall(dura,scope,para_array);
    }


    ex.ec = require("little-event-center");


    if(ht.is("._main")){
        u.cj.winResize(function(sw,sh){
            if(sw<csso.mw){
                ht.addClass("scroll_h");
            }else{
                ht.removeClass("scroll_h");
            }

            if(sh<csso.mh){
                ht.addClass("scroll_v");
            }else{
                ht.removeClass("scroll_v");
            }
        });
    }





    ex.filetype_thumb ={
        "txt":"__/images/iconfont-txt.png".p(),
        "zip":"__/images/iconfont-zip.png".p(),
        "rar":"__/images/iconfont-zip.png".p(),
        "7z":"__/images/iconfont-zip.png".p(),
        "doc":"__/images/iconfont_word.png".p(),
        "docx":"__/images/iconfont_word.png".p(),
        "default":"__/images/iconfont-noknow.png".p()
    }



    ex.is_img = function(src){
        return /jpg|JPG|png|PNG|jpeg|JPEG|bmp|BMP$/.test(src)
    }


    
    if(parent == top && window != top){
    	top.document.title = document.title;
    }


    $(document)
        .delegate("form input","keydown",function(e){
    	    if(e.keyCode == 13) e.preventDefault();
        })
    ;

    window.co = ex;




    var nav_current_setter = ({
        index_reg_list:[
            [/\/topic\//,0],
            [/\/tocalendar/,1],
            [/\/attention\//,2],
            [/\/message\//,3],
            [/\/account\//,4]
        ],
        init:function(){
            var m = this;

            if(top.nav) {
                $.each(m.index_reg_list,function(key,ele){
                    if(ele[0].test(location.href)){
                        top.nav.set_current(ele[1])
                    }
                });
            }

            return m;
        }
    }).init();



    return ex;
});