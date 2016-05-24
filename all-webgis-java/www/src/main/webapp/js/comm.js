/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2015/11/9
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    var cl = require("ctool");
    var cj = require("ctooj");

    var root = exports.root = require.resolve("__/#");
    $(function(){
        /**
         * 导航相关
         */
        var nav = ({
            init:function(){
                var m = this;
                m.el = $(".navbar");

                //todo:临时导航。换为jsp之后请删掉或者注释掉
                m.el.find("a:contains(个人)").    attr("href","theme_personal.html");
                m.el.find("a:contains(企业)").    attr("href","theme_enterprise.html");
                /*m.el.find("a:contains(云服务)").  attr("href","theme_cloud_server.html");*/
                m.el.find(".logo").attr("href","/");
                return m;
            }
        }).init();

        //todo:临时导航。换为jsp之后请删掉或者注释掉
        //首页第四屏//更多行业链接
        $("#sec4 .gdhy>a").attr('src',"theme_enterprise.html");
        
        
    });


});