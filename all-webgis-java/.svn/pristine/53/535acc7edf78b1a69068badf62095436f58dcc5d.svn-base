/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2015/11/5
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    var cl = require("ctool");
    var cj = require("ctooj");


    $(function(){
        var pcont = $("#page_cont");
        var navbar = $(".navbar");
        navbar.height = navbar.height();
        var bott = $(".bott");
        bott.height = bott.height();

        var midcont = $(".midcont");




        cj.winResize(500,function(sw,sh){
            pcont.css({
                minHeight:sh - navbar.height - bott.height
            });

            midcont.css({
                top:(pcont.outerHeight() - midcont.outerHeight())/2
            });

        })
    });


});